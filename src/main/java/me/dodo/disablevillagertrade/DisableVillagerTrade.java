package me.dodo.disablevillagertrade;

import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import me.dodo.disablevillagertrade.config.ConfigMigrator;
import me.dodo.disablevillagertrade.listeners.UpdateNotifyListener;
import me.dodo.disablevillagertrade.listeners.VillagerTradeListener;
import me.dodo.disablevillagertrade.config.PluginConfig;
import me.dodo.disablevillagertrade.update.UpdateChecker;
import org.bukkit.plugin.java.JavaPlugin;

/**
 * Main plugin class for DisableVillagerTrade.
 * Prevents players from trading with villagers on Spigot/Paper servers.
 */
public final class DisableVillagerTrade extends JavaPlugin {
    
    private static DisableVillagerTrade instance;
    private PluginConfig pluginConfig;
    private UpdateChecker updateChecker;

    @Override
    @SuppressFBWarnings(value = "ST_WRITE_TO_STATIC_FROM_INSTANCE_METHOD", 
        justification = "Singleton pattern required for Bukkit plugin lifecycle")
    public void onEnable() {
        instance = this;
        
        // Load configuration
        saveDefaultConfig();
        
        // Migrate old config if needed
        ConfigMigrator migrator = new ConfigMigrator(this);
        migrator.migrateIfNeeded();
        
        // Load config after migration
        this.pluginConfig = new PluginConfig(this);
        
        // Register event listeners
        getServer().getPluginManager().registerEvents(new VillagerTradeListener(this), this);
        
        // Initialize update checker if enabled
        if (pluginConfig.isUpdateCheckerEnabled()) {
            this.updateChecker = new UpdateChecker(this);
            updateChecker.startChecking(pluginConfig.getUpdateCheckInterval());
            
            // Register update notification listener if notify-on-join is enabled
            if (pluginConfig.isNotifyOnJoin()) {
                getServer().getPluginManager().registerEvents(
                    new UpdateNotifyListener(this, updateChecker, pluginConfig), this);
            }
        }
        
        getLogger().info("DisableVillagerTrade has been enabled!");
    }

    @Override
    @SuppressFBWarnings(value = "ST_WRITE_TO_STATIC_FROM_INSTANCE_METHOD", 
        justification = "Singleton pattern required for Bukkit plugin lifecycle")
    public void onDisable() {
        // Stop update checker
        if (updateChecker != null) {
            updateChecker.stopChecking();
        }
        
        getLogger().info("DisableVillagerTrade has been disabled!");
        instance = null;
    }

    /**
     * Gets the plugin instance.
     * @return the plugin instance
     */
    @SuppressFBWarnings(value = "MS_EXPOSE_REP", 
        justification = "Intentional singleton access for Bukkit plugin")
    public static DisableVillagerTrade getInstance() {
        return instance;
    }

    /**
     * Gets the plugin configuration.
     * @return the plugin configuration
     */
    public PluginConfig getPluginConfig() {
        return pluginConfig;
    }
    
    /**
     * Reloads the plugin configuration.
     */
    public void reloadPluginConfig() {
        reloadConfig();
        this.pluginConfig = new PluginConfig(this);
        getLogger().info("Configuration reloaded!");
    }
}
