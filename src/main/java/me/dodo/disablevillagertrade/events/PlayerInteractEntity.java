package me.dodo.disablevillagertrade.events;

import me.dodo.disablevillagertrade.DisableVillagerTrade;
import me.dodo.disablevillagertrade.settings.ConfigManager;
import me.dodo.disablevillagertrade.settings.configurations.Main;
import org.bukkit.ChatColor;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Villager;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEntityEvent;

public class PlayerInteractEntity implements Listener {
    private static Main main;

    public PlayerInteractEntity() {
        ConfigManager configManager = DisableVillagerTrade.getConfigManager();
        main = configManager.getMain();
    }

    @EventHandler
    public void onPlayerInteractEntity(PlayerInteractEntityEvent event) {
        if (event.getRightClicked().getType() != EntityType.VILLAGER)
            return;
        Villager villager = (Villager) event.getRightClicked();
        if (villager.getProfession() == Villager.Profession.NONE)
            return;
        if(main.getDisabledWorlds().contains(event.getPlayer().getWorld().getName()))
            return;
        if (event.getPlayer().hasPermission("disabletrade.bypass"))
            return;

        if (villager.hasAI() && villager.hasGravity()) {
            if (main.isEnabled())
                event.getPlayer().sendMessage(ChatColor.translateAlternateColorCodes('&', main.getContext()));
            event.setCancelled(true);
        }
    }
}
