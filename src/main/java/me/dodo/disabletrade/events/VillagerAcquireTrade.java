package me.dodo.disabletrade.events;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.VillagerAcquireTradeEvent;

public class VillagerAcquireTrade implements Listener {

    @EventHandler
    public void OnVillagerAcquireTrade(VillagerAcquireTradeEvent event) {
        event.setCancelled(true);
    }
}
