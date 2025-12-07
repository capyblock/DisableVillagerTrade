package me.dodo.disablevillagertrade.listeners;

import me.dodo.disablevillagertrade.config.PluginConfig;
import me.dodo.disablevillagertrade.update.UpdateChecker;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

/**
 * Notifies players with permission about available updates on join.
 */
public class UpdateNotifyListener implements Listener {
    
    private static final String UPDATE_PERMISSION = "disabletrade.update";
    
    private final UpdateChecker updateChecker;
    private final PluginConfig config;

    /**
     * Creates a new update notify listener.
     * @param updateChecker the update checker instance
     * @param config the plugin configuration
     */
    public UpdateNotifyListener(UpdateChecker updateChecker, PluginConfig config) {
        this.updateChecker = updateChecker;
        this.config = config;
    }

    /**
     * Notifies players with permission about available updates.
     * @param event the player join event
     */
    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        if (!config.isNotifyOnJoin()) {
            return;
        }
        
        Player player = event.getPlayer();
        
        if (!player.hasPermission(UPDATE_PERMISSION)) {
            return;
        }
        
        if (!updateChecker.isUpdateAvailable()) {
            return;
        }
        
        // Send update notification with a small delay for better visibility
        player.getServer().getScheduler().runTaskLater(
            player.getServer().getPluginManager().getPlugin("DisableVillagerTrade"),
            () -> sendUpdateNotification(player),
            40L // 2 seconds delay
        );
    }

    /**
     * Sends the update notification to a player.
     * @param player the player to notify
     */
    private void sendUpdateNotification(Player player) {
        if (!player.isOnline()) {
            return;
        }
        
        String message = config.getUpdateMessage()
            .replace("%current%", updateChecker.getCurrentVersion())
            .replace("%latest%", updateChecker.getLatestVersion());
        
        player.sendMessage(ChatColor.translateAlternateColorCodes('&', message));
    }
}

