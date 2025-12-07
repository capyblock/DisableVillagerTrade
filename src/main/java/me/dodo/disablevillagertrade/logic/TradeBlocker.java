package me.dodo.disablevillagertrade.logic;

import java.util.List;

/**
 * Core business logic for determining if a villager trade should be blocked.
 * This class is separated from Bukkit dependencies to enable proper unit testing.
 */
public class TradeBlocker {
    
    /**
     * Determines if a trade interaction should be blocked.
     *
     * @param isVillager         Whether the entity is a villager
     * @param profession         The villager's profession name (e.g., "NONE", "FARMER")
     * @param hasAI              Whether the villager has AI
     * @param hasGravity         Whether the villager has gravity
     * @param worldName          The name of the world
     * @param disabledWorlds     List of worlds where trading is disabled (allowed in these worlds)
     * @param hasBypassPermission Whether the player has bypass permission
     * @return true if the trade should be blocked, false otherwise
     */
    public boolean shouldBlockTrade(
            boolean isVillager,
            String profession,
            boolean hasAI,
            boolean hasGravity,
            String worldName,
            List<String> disabledWorlds,
            boolean hasBypassPermission
    ) {
        // Not a villager - don't block
        if (!isVillager) {
            return false;
        }
        
        // NONE profession villagers can be interacted with
        if ("NONE".equals(profession)) {
            return false;
        }
        
        // World is in disabled list - don't block (trading allowed in disabled worlds)
        if (disabledWorlds != null && disabledWorlds.contains(worldName)) {
            return false;
        }
        
        // Player has bypass permission - don't block
        if (hasBypassPermission) {
            return false;
        }
        
        // Only block if villager has AI and gravity (normal villager)
        return hasAI && hasGravity;
    }
}

