package me.dodo.disablevillagertrade.bukkit.update;

import me.dodo.disablevillagertrade.common.UpdateChecker;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

/**
 * Bukkit wrapper for the common UpdateChecker.
 * Handles Bukkit-specific scheduling.
 */
public class BukkitUpdateChecker {
    
    private final JavaPlugin plugin;
    private final UpdateChecker checker;
    private int taskId = -1;

    /**
     * Creates a new Bukkit update checker.
     * @param plugin the plugin instance
     */
    public BukkitUpdateChecker(JavaPlugin plugin) {
        this.plugin = plugin;
        this.checker = new UpdateChecker(
            plugin.getDescription().getVersion(),
            "DisableVillagerTrade-Bukkit/" + plugin.getDescription().getVersion(),
            msg -> plugin.getLogger().info(msg)
        );
    }

    /**
     * Starts the periodic update check.
     * @param intervalHours how often to check for updates (in hours)
     */
    public void startChecking(int intervalHours) {
        // Check immediately on startup
        checker.checkForUpdates();
        
        // Schedule periodic checks
        long intervalTicks = intervalHours * 60L * 60L * 20L; // hours to ticks
        taskId = Bukkit.getScheduler().runTaskTimerAsynchronously(plugin, 
            () -> checker.checkForUpdates(), intervalTicks, intervalTicks).getTaskId();
    }

    /**
     * Stops the periodic update check.
     */
    public void stopChecking() {
        if (taskId != -1) {
            Bukkit.getScheduler().cancelTask(taskId);
            taskId = -1;
        }
    }

    /**
     * Checks if an update is available.
     * @return true if a newer version is available
     */
    public boolean isUpdateAvailable() {
        return checker.isUpdateAvailable();
    }

    /**
     * Gets the latest version string.
     * @return the latest version, or null if not yet checked
     */
    public String getLatestVersion() {
        return checker.getLatestVersion();
    }

    /**
     * Gets the current plugin version.
     * @return the current version
     */
    public String getCurrentVersion() {
        return checker.getCurrentVersion();
    }
}

