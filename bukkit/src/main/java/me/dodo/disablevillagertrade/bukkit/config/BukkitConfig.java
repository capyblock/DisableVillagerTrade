package me.dodo.disablevillagertrade.bukkit.config;

import me.dodo.disablevillagertrade.common.ModConfig;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Collections;
import java.util.List;

/**
 * Bukkit implementation of the configuration.
 */
public class BukkitConfig implements ModConfig {
    
    private final JavaPlugin plugin;
    private boolean messageEnabled;
    private String message;
    private List<String> disabledWorlds;
    private boolean updateCheckerEnabled;
    private int updateCheckInterval;
    private boolean notifyOnJoin;
    private String updateMessage;

    /**
     * Creates a new plugin configuration from the plugin's config.yml.
     * @param plugin the plugin instance
     */
    public BukkitConfig(JavaPlugin plugin) {
        this.plugin = plugin;
        loadConfig();
    }
    
    private void loadConfig() {
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

    @Override
    public boolean isMessageEnabled() {
        return messageEnabled;
    }

    @Override
    public String getMessage() {
        return message;
    }

    @Override
    public List<String> getDisabledWorlds() {
        return disabledWorlds != null ? disabledWorlds : Collections.emptyList();
    }

    @Override
    public boolean isUpdateCheckerEnabled() {
        return updateCheckerEnabled;
    }

    @Override
    public int getUpdateCheckInterval() {
        return updateCheckInterval;
    }

    @Override
    public boolean isNotifyOnJoin() {
        return notifyOnJoin;
    }

    @Override
    public String getUpdateMessage() {
        return updateMessage;
    }
    
    @Override
    public void reload() {
        plugin.reloadConfig();
        loadConfig();
    }
}

