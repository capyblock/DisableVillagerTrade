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

    /**
     * Creates a new plugin configuration from the plugin's config.yml.
     * @param plugin the plugin instance
     */
    public PluginConfig(JavaPlugin plugin) {
        FileConfiguration config = plugin.getConfig();
        
        this.messageEnabled = config.getBoolean("message.enabled", true);
        this.message = config.getString("message.text", "&cYou can't trade with villagers on this server.");
        this.disabledWorlds = config.getStringList("disabled-worlds");
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
}

