package me.dodo.disablevillagertrade.events;

import me.dodo.disablevillagertrade.DisableVillagerTrade;
import me.dodo.disablevillagertrade.logic.TradeBlocker;
import me.dodo.disablevillagertrade.settings.ConfigManager;
import me.dodo.disablevillagertrade.settings.configurations.Main;
import org.bukkit.ChatColor;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Villager;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEntityEvent;

public class PlayerInteractEntity implements Listener {
    private final Main main;
    private final TradeBlocker tradeBlocker;

    public PlayerInteractEntity() {
        ConfigManager configManager = DisableVillagerTrade.getConfigManager();
        this.main = configManager.getMain();
        this.tradeBlocker = new TradeBlocker();
    }

    @EventHandler
    public void onPlayerInteractEntity(PlayerInteractEntityEvent event) {
        if (event.getRightClicked().getType() != EntityType.VILLAGER)
            return;
        
        Villager villager = (Villager) event.getRightClicked();
        
        boolean shouldBlock = tradeBlocker.shouldBlockTrade(
            true,
            villager.getProfession().name(),
            villager.hasAI(),
            villager.hasGravity(),
            event.getPlayer().getWorld().getName(),
            main.getDisabledWorlds(),
            event.getPlayer().hasPermission("disabletrade.bypass")
        );
        
        if (shouldBlock) {
            if (main.isEnabled()) {
                event.getPlayer().sendMessage(ChatColor.translateAlternateColorCodes('&', main.getContext()));
            }
            event.setCancelled(true);
        }
    }
}
