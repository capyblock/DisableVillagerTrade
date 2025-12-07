package me.dodo.disablevillagertrade;

import me.dodo.disablevillagertrade.config.ConfigMigrator;
import me.dodo.disablevillagertrade.listeners.VillagerTradeListener;
import me.dodo.disablevillagertrade.config.PluginConfig;
import org.bukkit.plugin.java.JavaPlugin;

/**
 * Main plugin class for DisableVillagerTrade.
 * Prevents players from trading with villagers on Spigot/Paper servers.
 */
public final class DisableVillagerTrade extends JavaPlugin {
    
    private static DisableVillagerTrade instance;
    private PluginConfig pluginConfig;

    @Override
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
        
        getLogger().info("DisableVillagerTrade has been enabled!");
    }

    @Override
    public void onDisable() {
        getLogger().info("DisableVillagerTrade has been disabled!");
        instance = null;
    }

    /**
     * Gets the plugin instance.
     * @return the plugin instance
     */
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
