package me.dodo.disablevillagertrade.bukkit;

import me.dodo.disablevillagertrade.bukkit.commands.DisableTradeCommand;
import me.dodo.disablevillagertrade.bukkit.config.ConfigMigrator;
import me.dodo.disablevillagertrade.bukkit.config.BukkitConfig;
import me.dodo.disablevillagertrade.bukkit.listeners.UpdateNotifyListener;
import me.dodo.disablevillagertrade.bukkit.listeners.VillagerTradeListener;
import me.dodo.disablevillagertrade.bukkit.update.BukkitUpdateChecker;
import org.bukkit.command.PluginCommand;
import org.bukkit.plugin.java.JavaPlugin;

/**
 * Main plugin class for DisableVillagerTrade (Bukkit/Spigot/Paper).
 * Prevents players from trading with villagers.
 */
public final class DisableVillagerTrade extends JavaPlugin {
    
    private static DisableVillagerTrade instance;
    private BukkitConfig pluginConfig;
    private BukkitUpdateChecker updateChecker;

    @Override
    public void onEnable() {
        instance = this;
        
        // Load configuration
        saveDefaultConfig();
        
        // Migrate old config if needed
        ConfigMigrator migrator = new ConfigMigrator(this);
        migrator.migrateIfNeeded();
        
        // Load config after migration
        this.pluginConfig = new BukkitConfig(this);
        
        // Register event listeners
        getServer().getPluginManager().registerEvents(new VillagerTradeListener(this), this);
        
        // Register commands
        registerCommands();
        
        // Initialize update checker if enabled
        if (pluginConfig.isUpdateCheckerEnabled()) {
            this.updateChecker = new BukkitUpdateChecker(this);
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
    public static DisableVillagerTrade getInstance() {
        return instance;
    }

    /**
     * Gets the plugin configuration.
     * @return the plugin configuration
     */
    public BukkitConfig getPluginConfig() {
        return pluginConfig;
    }
    
    /**
     * Reloads the plugin configuration.
     */
    public void reloadPluginConfig() {
        reloadConfig();
        this.pluginConfig = new BukkitConfig(this);
        getLogger().info("Configuration reloaded!");
    }
    
    /**
     * Registers plugin commands.
     */
    private void registerCommands() {
        PluginCommand command = getCommand("disabletrade");
        if (command != null) {
            DisableTradeCommand commandExecutor = new DisableTradeCommand(this);
            command.setExecutor(commandExecutor);
            command.setTabCompleter(commandExecutor);
        }
    }
}

