package me.dodo.disabletrade.events;

import me.dodo.disabletrade.DisableTrade;
import org.bukkit.ChatColor;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Villager;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEntityEvent;

import java.util.Objects;

public class PlayerInteractEntity implements Listener {
    private static DisableTrade plugin;
    private static String message;

    public PlayerInteractEntity(DisableTrade _plugin) {
        plugin = _plugin;
        message = ChatColor.translateAlternateColorCodes('&', Objects.requireNonNull(plugin.getConfig().getString("message")));
    }

    @EventHandler
    public void onPlayerInteractEntity(PlayerInteractEntityEvent event) {
        if (event.getRightClicked().getType() != EntityType.VILLAGER)
            return;
        if (event.getPlayer().hasPermission("disabletrade.bypass"))
            return;
        Villager villager = (Villager) event.getRightClicked();
        if (villager.hasAI()) {
            event.getPlayer().sendMessage(message);
            event.setCancelled(true);
        }
    }
}
