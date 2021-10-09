package me.dodo.disablevillagertrade;

import me.dodo.disablevillagertrade.events.PlayerInteractEntity;
import org.bukkit.plugin.java.JavaPlugin;

public final class DisableVillagerTrade extends JavaPlugin {
    @Override
    public void onEnable() {
        saveDefaultConfig();
        getServer().getPluginManager().registerEvents(new PlayerInteractEntity(this), this);
    }
}
