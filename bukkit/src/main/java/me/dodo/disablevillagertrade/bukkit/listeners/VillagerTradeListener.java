package me.dodo.disablevillagertrade.bukkit.listeners;

import me.dodo.disablevillagertrade.bukkit.DisableVillagerTrade;
import me.dodo.disablevillagertrade.bukkit.config.BukkitConfig;
import me.dodo.disablevillagertrade.common.Constants;
import me.dodo.disablevillagertrade.common.TradeBlocker;
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
        BukkitConfig config = plugin.getPluginConfig();
        
        boolean shouldBlock = tradeBlocker.shouldBlockTrade(
            true,
            villager.getProfession().name(),
            villager.hasAI(),
            villager.hasGravity(),
            player.getWorld().getName(),
            config.getDisabledWorlds(),
            player.hasPermission(Constants.PERMISSION_BYPASS)
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

