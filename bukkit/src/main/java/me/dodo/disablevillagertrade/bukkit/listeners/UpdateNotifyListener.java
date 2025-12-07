package me.dodo.disablevillagertrade.bukkit.listeners;

import me.dodo.disablevillagertrade.bukkit.DisableVillagerTrade;
import me.dodo.disablevillagertrade.bukkit.config.BukkitConfig;
import me.dodo.disablevillagertrade.bukkit.update.BukkitUpdateChecker;
import me.dodo.disablevillagertrade.common.Constants;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.scheduler.BukkitRunnable;

/**
 * Notifies players with the update permission when they join and an update is available.
 */
public class UpdateNotifyListener implements Listener {
    
    private final DisableVillagerTrade plugin;
    private final BukkitUpdateChecker updateChecker;
    private final BukkitConfig config;

    /**
     * Creates a new update notify listener.
     * @param plugin the plugin instance
     * @param updateChecker the update checker
     * @param config the plugin config
     */
    public UpdateNotifyListener(DisableVillagerTrade plugin, BukkitUpdateChecker updateChecker, BukkitConfig config) {
        this.plugin = plugin;
        this.updateChecker = updateChecker;
        this.config = config;
    }

    /**
     * Handles player join events.
     * Notifies the player if they have permission and an update is available.
     * 
     * @param event the player join event
     */
    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        
        // Check permission
        if (!player.hasPermission(Constants.PERMISSION_UPDATE)) {
            return;
        }
        
        // Delay the message slightly so it appears after join messages
        new BukkitRunnable() {
            @Override
            public void run() {
                if (!player.isOnline()) {
                    return;
                }
                
                if (updateChecker.isUpdateAvailable()) {
                    String message = config.getUpdateMessage()
                        .replace("%current%", updateChecker.getCurrentVersion())
                        .replace("%latest%", updateChecker.getLatestVersion());
                    
                    player.sendMessage(ChatColor.translateAlternateColorCodes('&', message));
                }
            }
        }.runTaskLater(plugin, 40L); // 2 second delay
    }
}

