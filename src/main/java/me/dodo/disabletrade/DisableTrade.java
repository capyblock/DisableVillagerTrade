package me.dodo.disabletrade;

import me.dodo.disabletrade.events.PlayerInteractEntity;
import org.bukkit.plugin.java.JavaPlugin;

public final class DisableTrade extends JavaPlugin {
    @Override
    public void onEnable() {
        saveDefaultConfig();
        getServer().getPluginManager().registerEvents(new PlayerInteractEntity(this), this);
    }
}
