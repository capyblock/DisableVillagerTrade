package me.dodo.disabletrade;

import me.dodo.disabletrade.events.VillagerAcquireTrade;
import org.bukkit.plugin.java.JavaPlugin;

public final class DisableTrade extends JavaPlugin {
    @Override
    public void onEnable() {
        getServer().getPluginManager().registerEvents(new VillagerAcquireTrade(), this);
    }
}
