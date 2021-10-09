package me.dodo.disablevillagertrade.events;

import me.dodo.disablevillagertrade.DisableVillagerTrade;
import org.bukkit.ChatColor;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Villager;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEntityEvent;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class PlayerInteractEntity implements Listener {
    private static DisableVillagerTrade plugin;
    private static String message;
    private static List<String> disabledWorlds = new ArrayList<>();

    public PlayerInteractEntity(DisableVillagerTrade _plugin) {
        plugin = _plugin;
        if (plugin.getConfig().getBoolean("message.enabled"))
            message = ChatColor.translateAlternateColorCodes('&', Objects.requireNonNull(plugin.getConfig().getString("message.context")));
        disabledWorlds = (List<String>) _plugin.getConfig().getList("disabled-worlds");
    }

    @EventHandler
    public void onPlayerInteractEntity(PlayerInteractEntityEvent event) {
        if (event.getRightClicked().getType() != EntityType.VILLAGER)
            return;
        if (disabledWorlds.contains(event.getPlayer().getWorld().getName()))
            return;
        if (event.getPlayer().hasPermission("disabletrade.bypass"))
            return;

        Villager villager = (Villager) event.getRightClicked();
        if (villager.hasAI()) {
            if (plugin.getConfig().getBoolean("message.enabled"))
                event.getPlayer().sendMessage(message);
            event.setCancelled(true);
        }
    }
}
