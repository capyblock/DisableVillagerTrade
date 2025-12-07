package me.dodo.disablevillagertrade.bukkit.config;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.List;
import java.util.logging.Level;

/**
 * Handles migration of old config formats to the current version.
 */
public class ConfigMigrator {
    
    private static final int CURRENT_VERSION = 3;
    
    private final JavaPlugin plugin;

    public ConfigMigrator(JavaPlugin plugin) {
        this.plugin = plugin;
    }

    /**
     * Checks and performs config migration if needed.
     */
    public void migrateIfNeeded() {
        FileConfiguration config = plugin.getConfig();
        int version = config.getInt("config-version", 1);
        
        if (version < CURRENT_VERSION) {
            backupConfig();
            
            if (version < 2) {
                migrateToV2(config);
            }
            if (version < 3) {
                migrateToV3(config);
            }
            
            config.set("config-version", CURRENT_VERSION);
            plugin.saveConfig();
            plugin.getLogger().info("Configuration migrated to version " + CURRENT_VERSION);
        }
    }

    private void backupConfig() {
        File configFile = new File(plugin.getDataFolder(), "config.yml");
        if (configFile.exists()) {
            File backupFile = new File(plugin.getDataFolder(), "config.yml.backup");
            try {
                Files.copy(configFile.toPath(), backupFile.toPath());
                plugin.getLogger().info("Created config backup at config.yml.backup");
            } catch (IOException e) {
                plugin.getLogger().log(Level.WARNING, "Failed to create config backup", e);
            }
        }
    }

    void migrateToV2(FileConfiguration config) {
        // V2 introduced message.enabled and message.text structure
        String oldMessage = config.getString("message");
        if (oldMessage != null && !config.contains("message.text")) {
            config.set("message.enabled", true);
            config.set("message.text", oldMessage);
            config.set("message", null);
        }
        
        plugin.getLogger().info("Migrated config to version 2");
    }

    void migrateToV3(FileConfiguration config) {
        // V3 introduced update-checker settings
        if (!config.contains("update-checker")) {
            config.set("update-checker.enabled", true);
            config.set("update-checker.check-interval", 24);
            config.set("update-checker.notify-on-join", true);
            config.set("update-checker.message", 
                "&e[DisableVillagerTrade] &fA new version is available! &7(%current% → %latest%)");
        }
        
        // Migrate world-blacklist to disabled-worlds if present
        List<String> blacklist = config.getStringList("world-blacklist");
        if (!blacklist.isEmpty() && !config.contains("disabled-worlds")) {
            config.set("disabled-worlds", blacklist);
            config.set("world-blacklist", null);
        }
        
        plugin.getLogger().info("Migrated config to version 3");
    }
}

