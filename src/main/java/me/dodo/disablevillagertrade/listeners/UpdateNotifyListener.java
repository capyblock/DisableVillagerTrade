package me.dodo.disablevillagertrade.listeners;

import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import me.dodo.disablevillagertrade.config.PluginConfig;
import me.dodo.disablevillagertrade.update.UpdateChecker;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.plugin.java.JavaPlugin;

/**
 * Notifies players with permission about available updates on join.
 */
public class UpdateNotifyListener implements Listener {
    
    private static final String UPDATE_PERMISSION = "disabletrade.update";
    
    private final JavaPlugin plugin;
    private final UpdateChecker updateChecker;
    private final PluginConfig config;

    /**
     * Creates a new update notify listener.
     * @param plugin the plugin instance
     * @param updateChecker the update checker instance
     * @param config the plugin configuration
     */
    @SuppressFBWarnings(value = "EI_EXPOSE_REP2", 
        justification = "Plugin reference is intentionally stored for Bukkit API access")
    public UpdateNotifyListener(JavaPlugin plugin, UpdateChecker updateChecker, PluginConfig config) {
        this.plugin = plugin;
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
            plugin,
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

