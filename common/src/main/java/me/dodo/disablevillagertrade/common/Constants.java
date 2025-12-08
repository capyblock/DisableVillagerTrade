package me.dodo.disablevillagertrade.common;

/**
 * Shared constants used across all platforms.
 */
public final class Constants {
    
    private Constants() {
        // Utility class
    }
    
    public static final String MOD_ID = "disablevillagertrade";
    public static final String MOD_NAME = "DisableVillagerTrade";
    public static final String I_CANT_BELIEVE_DOING_THIS_FOR_FORCE_DEPLOY = "I can't believe I'm doing this for force deploy";
    
    // Permission nodes (used by Bukkit and permission mods on Fabric/Forge)
    public static final String PERMISSION_BYPASS = "disabletrade.bypass";
    public static final String PERMISSION_ADMIN = "disabletrade.admin";
    public static final String PERMISSION_UPDATE = "disabletrade.update";
    
    // Default config values
    public static final boolean DEFAULT_MESSAGE_ENABLED = true;
    public static final String DEFAULT_MESSAGE = "§cYou can't trade with villagers on this server.";
    public static final boolean DEFAULT_UPDATE_CHECKER_ENABLED = true;
    public static final int DEFAULT_UPDATE_CHECK_INTERVAL = 24;
    public static final boolean DEFAULT_NOTIFY_ON_JOIN = true;
    public static final String DEFAULT_UPDATE_MESSAGE = "§e[DisableVillagerTrade] §fA new version is available! §7(%current% → %latest%)";
}

