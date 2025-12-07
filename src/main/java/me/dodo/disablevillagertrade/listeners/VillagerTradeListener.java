package me.dodo.disablevillagertrade.listeners;

import me.dodo.disablevillagertrade.DisableVillagerTrade;
import me.dodo.disablevillagertrade.config.PluginConfig;
import me.dodo.disablevillagertrade.logic.TradeBlocker;
import org.bukkit.ChatColor;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.entity.Villager;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEntityEvent;

/**
 * Listens for player interactions with villagers and blocks trading when configured.
 */
public class VillagerTradeListener implements Listener {
    
    /**
     * Permission that allows players to bypass the trade block.
     */
    public static final String BYPASS_PERMISSION = "disabletrade.bypass";
    
    private final DisableVillagerTrade plugin;
    private final TradeBlocker tradeBlocker;

    /**
     * Creates a new villager trade listener.
     * @param plugin the plugin instance
     */
    public VillagerTradeListener(DisableVillagerTrade plugin) {
        this.plugin = plugin;
        this.tradeBlocker = new TradeBlocker();
    }

    /**
     * Handles player interactions with entities.
     * Cancels the event if the player is trying to trade with a villager
     * and doesn't have bypass permission.
     * 
     * @param event the player interact entity event
     */
    @EventHandler(priority = EventPriority.HIGH, ignoreCancelled = true)
    public void onPlayerInteractEntity(PlayerInteractEntityEvent event) {
        // Only handle villager interactions
        if (event.getRightClicked().getType() != EntityType.VILLAGER) {
            return;
        }
        
        Villager villager = (Villager) event.getRightClicked();
        Player player = event.getPlayer();
        PluginConfig config = plugin.getPluginConfig();
        
        boolean shouldBlock = tradeBlocker.shouldBlockTrade(
            true,
            villager.getProfession().name(),
            villager.hasAI(),
            villager.hasGravity(),
            player.getWorld().getName(),
            config.getDisabledWorlds(),
            player.hasPermission(BYPASS_PERMISSION)
        );
        
        if (shouldBlock) {
            event.setCancelled(true);
            
            if (config.isMessageEnabled()) {
                String message = ChatColor.translateAlternateColorCodes('&', config.getMessage());
                player.sendMessage(message);
            }
        }
    }
}

