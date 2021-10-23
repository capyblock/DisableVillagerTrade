package me.dodo.disablevillagertrade;

import me.dodo.disablevillagertrade.events.PlayerInteractEntity;
import me.dodo.disablevillagertrade.settings.ConfigManager;
import org.bukkit.plugin.java.JavaPlugin;

public final class DisableVillagerTrade extends JavaPlugin {
    private static ConfigManager configManager;

    @Override
    public void onEnable() {
        configManager = new ConfigManager(this);
        configManager.loadConfig();
        getServer().getPluginManager().registerEvents(new PlayerInteractEntity(), this);
    }

    public static ConfigManager getConfigManager() {
        return configManager;
    }
}
