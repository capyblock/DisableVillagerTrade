package me.dodo.disablevillagertrade.common;

import java.util.List;

/**
 * Platform-agnostic configuration interface.
 * Each platform (Bukkit, Fabric, Forge) implements this to provide config values.
 */
public interface ModConfig {
    
    /**
     * Checks if the blocked message should be shown to players.
     * @return true if message is enabled
     */
    boolean isMessageEnabled();
    
    /**
     * Gets the message to show when trading is blocked.
     * @return the blocked trade message (may contain formatting codes)
     */
    String getMessage();
    
    /**
     * Gets the list of worlds/dimensions where villager trading is allowed.
     * @return list of world names where trading is NOT blocked
     */
    List<String> getDisabledWorlds();
    
    /**
     * Checks if the update checker is enabled.
     * @return true if update checker is enabled
     */
    boolean isUpdateCheckerEnabled();
    
    /**
     * Gets the update check interval in hours.
     * @return the check interval in hours
     */
    int getUpdateCheckInterval();
    
    /**
     * Checks if players should be notified on join.
     * @return true if notify on join is enabled
     */
    boolean isNotifyOnJoin();
    
    /**
     * Gets the update notification message.
     * @return the update message (may contain placeholders)
     */
    String getUpdateMessage();
    
    /**
     * Reloads the configuration from disk.
     */
    void reload();
}

