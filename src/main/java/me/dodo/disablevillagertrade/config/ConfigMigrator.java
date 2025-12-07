package me.dodo.disablevillagertrade.config;

import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.logging.Logger;

/**
 * Handles migration of old config formats to the current version.
 */
public class ConfigMigrator {
    
    private static final int CURRENT_CONFIG_VERSION = 3;
    
    private final JavaPlugin plugin;
    private final Logger logger;
    
    @SuppressFBWarnings(value = "EI_EXPOSE_REP2", 
        justification = "Plugin reference is intentionally stored for Bukkit API access")
    public ConfigMigrator(JavaPlugin plugin) {
        this.plugin = plugin;
        this.logger = plugin.getLogger();
    }
    
    /**
     * Checks if config needs migration and performs it if necessary.
     * @return true if migration was performed, false otherwise
     */
    public boolean migrateIfNeeded() {
        FileConfiguration config = plugin.getConfig();
        int configVersion = config.getInt("config-version", 1);
        
        if (configVersion >= CURRENT_CONFIG_VERSION) {
            return false;
        }
        
        logger.info("Detected old config version " + configVersion + ", migrating to version " + CURRENT_CONFIG_VERSION + "...");
        
        boolean migrated = false;
        
        // Migration from v1 to v2: "context" -> "text"
        if (configVersion < 2) {
            migrated = migrateV1ToV2(config) || migrated;
        }
        
        // Migration from v2 to v3: add update-checker settings
        if (configVersion < 3) {
            migrated = migrateV2ToV3(config) || migrated;
        }
        
        // Update config version
        config.set("config-version", CURRENT_CONFIG_VERSION);
        plugin.saveConfig();
        
        if (migrated) {
            logger.info("Config migration complete!");
        }
        
        return migrated;
    }
    
    /**
     * Migrates config from v1 (context) to v2 (text).
     */
    private boolean migrateV1ToV2(FileConfiguration config) {
        // Check if old format exists
        if (config.contains("message.context")) {
            String oldMessage = config.getString("message.context");
            
            // Set new format
            config.set("message.text", oldMessage);
            
            // Remove old format
            config.set("message.context", null);
            
            logger.info("Migrated 'message.context' -> 'message.text'");
            return true;
        }
        return false;
    }
    
    /**
     * Migrates config from v2 to v3 (adds update-checker settings).
     */
    private boolean migrateV2ToV3(FileConfiguration config) {
        // Add update-checker settings if they don't exist
        if (!config.contains("update-checker")) {
            config.set("update-checker.enabled", true);
            config.set("update-checker.check-interval", 24);
            config.set("update-checker.notify-on-join", true);
            config.set("update-checker.message", 
                "&e[DisableVillagerTrade] &fA new version is available! &7(%current% → %latest%)");
            
            logger.info("Added 'update-checker' settings");
            return true;
        }
        return false;
    }
}

