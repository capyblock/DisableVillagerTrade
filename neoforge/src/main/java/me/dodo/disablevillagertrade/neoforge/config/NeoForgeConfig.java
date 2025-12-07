package me.dodo.disablevillagertrade.neoforge.config;

import me.dodo.disablevillagertrade.common.Constants;
import me.dodo.disablevillagertrade.common.ModConfig;
import net.neoforged.neoforge.common.ModConfigSpec;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * NeoForge implementation of the configuration using ModConfigSpec.
 */
public class NeoForgeConfig implements ModConfig {
    
    public static final ModConfigSpec SPEC;
    
    // Message settings
    public static final ModConfigSpec.BooleanValue MESSAGE_ENABLED;
    public static final ModConfigSpec.ConfigValue<String> MESSAGE_TEXT;
    public static final ModConfigSpec.ConfigValue<List<? extends String>> DISABLED_DIMENSIONS;
    
    // Update checker settings
    public static final ModConfigSpec.BooleanValue UPDATE_CHECKER_ENABLED;
    public static final ModConfigSpec.IntValue UPDATE_CHECK_INTERVAL;
    public static final ModConfigSpec.BooleanValue NOTIFY_ON_JOIN;
    public static final ModConfigSpec.ConfigValue<String> UPDATE_MESSAGE;
    
    static {
        ModConfigSpec.Builder builder = new ModConfigSpec.Builder();
        
        builder.comment("Message Settings").push("message");
        MESSAGE_ENABLED = builder
            .comment("Whether to show a message when trading is blocked")
            .define("enabled", Constants.DEFAULT_MESSAGE_ENABLED);
        MESSAGE_TEXT = builder
            .comment("The message to show when a player tries to trade (supports § color codes)")
            .define("text", Constants.DEFAULT_MESSAGE);
        builder.pop();
        
        builder.comment("Dimension Settings").push("dimensions");
        DISABLED_DIMENSIONS = builder
            .comment("Dimensions where villager trading is ALLOWED (not blocked)",
                    "Use full dimension IDs like 'minecraft:overworld', 'minecraft:the_nether'")
            .defineListAllowEmpty("disabled_dimensions", ArrayList::new, 
                () -> "", obj -> obj instanceof String);
        builder.pop();
        
        builder.comment("Update Checker Settings").push("update_checker");
        UPDATE_CHECKER_ENABLED = builder
            .comment("Whether to check for updates")
            .define("enabled", Constants.DEFAULT_UPDATE_CHECKER_ENABLED);
        UPDATE_CHECK_INTERVAL = builder
            .comment("How often to check for updates (in hours)")
            .defineInRange("check_interval", Constants.DEFAULT_UPDATE_CHECK_INTERVAL, 1, 168);
        NOTIFY_ON_JOIN = builder
            .comment("Whether to notify operators when they join and an update is available")
            .define("notify_on_join", Constants.DEFAULT_NOTIFY_ON_JOIN);
        UPDATE_MESSAGE = builder
            .comment("Message shown when an update is available",
                    "Placeholders: %current% (current version), %latest% (latest version)")
            .define("message", Constants.DEFAULT_UPDATE_MESSAGE);
        builder.pop();
        
        SPEC = builder.build();
    }
    
    @Override
    public boolean isMessageEnabled() {
        return MESSAGE_ENABLED.get();
    }
    
    @Override
    public String getMessage() {
        return MESSAGE_TEXT.get();
    }
    
    @Override
    public List<String> getDisabledWorlds() {
        List<? extends String> dimensions = DISABLED_DIMENSIONS.get();
        return dimensions != null ? new ArrayList<>(dimensions) : Collections.emptyList();
    }
    
    @Override
    public boolean isUpdateCheckerEnabled() {
        return UPDATE_CHECKER_ENABLED.get();
    }
    
    @Override
    public int getUpdateCheckInterval() {
        return UPDATE_CHECK_INTERVAL.get();
    }
    
    @Override
    public boolean isNotifyOnJoin() {
        return NOTIFY_ON_JOIN.get();
    }
    
    @Override
    public String getUpdateMessage() {
        return UPDATE_MESSAGE.get();
    }
    
    @Override
    public void reload() {
        // NeoForge configs are automatically synced
    }
}

