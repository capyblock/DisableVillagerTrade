package me.dodo.disablevillagertrade.forge.events;

import me.dodo.disablevillagertrade.common.Constants;
import me.dodo.disablevillagertrade.forge.DisableVillagerTradeForge;
import me.dodo.disablevillagertrade.forge.config.ForgeConfig;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.npc.Villager;
import net.minecraft.world.entity.npc.VillagerProfession;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.eventbus.api.Event;
import net.minecraftforge.eventbus.api.SubscribeEvent;

/**
 * Handles villager interaction events for Forge.
 */
public class VillagerTradeHandler {
    
    @SubscribeEvent
    public void onPlayerInteractEntity(PlayerInteractEvent.EntityInteract event) {
        // Only process villager interactions
        if (!(event.getTarget() instanceof Villager villager)) {
            return;
        }
        
        // Only process on server side
        if (event.getLevel().isClientSide()) {
            return;
        }
        
        // Only process for server players
        if (!(event.getEntity() instanceof ServerPlayer player)) {
            return;
        }
        
        // Get profession name
        VillagerProfession profession = villager.getVillagerData().getProfession();
        String professionName = profession.name().toUpperCase();
        
        // Get dimension name
        String dimensionName = player.level().dimension().location().toString();
        
        // Check bypass permission (op level 2+)
        boolean hasBypass = player.hasPermissions(2);
        
        // Check if trade should be blocked
        boolean shouldBlock = DisableVillagerTradeForge.getTradeBlocker().shouldBlockTrade(
            true,
            professionName,
            !villager.isNoAi(),      // hasAI is inverted
            !villager.isNoGravity(), // hasGravity is inverted
            dimensionName,
            ForgeConfig.DISABLED_DIMENSIONS.get().stream()
                .map(Object::toString)
                .toList(),
            hasBypass
        );
        
        if (shouldBlock) {
            // Cancel the interaction
            event.setCanceled(true);
            event.setResult(Event.Result.DENY);
            
            // Send message to player
            if (ForgeConfig.MESSAGE_ENABLED.get()) {
                player.sendSystemMessage(Component.literal(ForgeConfig.MESSAGE_TEXT.get()));
            }
        }
    }
}

