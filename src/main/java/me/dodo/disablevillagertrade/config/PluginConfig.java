package me.dodo.disablevillagertrade.config;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Collections;
import java.util.List;

/**
 * Handles plugin configuration loading and access.
 */
public class PluginConfig {
    
    private final boolean messageEnabled;
    private final String message;
    private final List<String> disabledWorlds;
    
    // Update checker settings
    private final boolean updateCheckerEnabled;
    private final int updateCheckInterval;
    private final boolean notifyOnJoin;
    private final String updateMessage;

    /**
     * Creates a new plugin configuration from the plugin's config.yml.
     * @param plugin the plugin instance
     */
    public PluginConfig(JavaPlugin plugin) {
        FileConfiguration config = plugin.getConfig();
        
        this.messageEnabled = config.getBoolean("message.enabled", true);
        this.message = config.getString("message.text", "&cYou can't trade with villagers on this server.");
        this.disabledWorlds = config.getStringList("disabled-worlds");
        
        // Update checker settings
        this.updateCheckerEnabled = config.getBoolean("update-checker.enabled", true);
        this.updateCheckInterval = config.getInt("update-checker.check-interval", 24);
        this.notifyOnJoin = config.getBoolean("update-checker.notify-on-join", true);
        this.updateMessage = config.getString("update-checker.message", 
            "&e[DisableVillagerTrade] &fA new version is available! &7(%current% → %latest%)");
    }

    /**
     * Checks if the blocked message should be shown to players.
     * @return true if message is enabled
     */
    public boolean isMessageEnabled() {
        return messageEnabled;
    }

    /**
     * Gets the message to show when trading is blocked.
     * @return the blocked trade message (with color codes)
     */
    public String getMessage() {
        return message;
    }

    /**
     * Gets the list of worlds where villager trading is allowed.
     * @return list of world names where trading is NOT blocked
     */
    public List<String> getDisabledWorlds() {
        return disabledWorlds != null ? disabledWorlds : Collections.emptyList();
    }
    
    /**
     * Checks if the update checker is enabled.
     * @return true if update checker is enabled
     */
    public boolean isUpdateCheckerEnabled() {
        return updateCheckerEnabled;
    }
    
    /**
     * Gets the update check interval in hours.
     * @return the check interval in hours
     */
    public int getUpdateCheckInterval() {
        return updateCheckInterval;
    }
    
    /**
     * Checks if players should be notified on join.
     * @return true if notify on join is enabled
     */
    public boolean isNotifyOnJoin() {
        return notifyOnJoin;
    }
    
    /**
     * Gets the update notification message.
     * @return the update message (with color codes and placeholders)
     */
    public String getUpdateMessage() {
        return updateMessage;
    }
}

