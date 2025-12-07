package me.dodo.disablevillagertrade.forge.config;

import me.dodo.disablevillagertrade.common.Constants;
import me.dodo.disablevillagertrade.common.ModConfig;
import me.dodo.disablevillagertrade.forge.DisableVillagerTradeForge;
import net.minecraftforge.fml.loading.FMLPaths;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Properties;

/**
 * Forge implementation of the configuration using simple Properties file.
 * Avoids ForgeConfigSpec due to API changes in Forge 60.x.
 */
public class ForgeConfig implements ModConfig {
    
    private static final String CONFIG_FILE_NAME = Constants.MOD_ID + "-server.properties";
    
    // Config keys
    private static final String KEY_MESSAGE_ENABLED = "message.enabled";
    private static final String KEY_MESSAGE_TEXT = "message.text";
    private static final String KEY_DISABLED_DIMENSIONS = "dimensions.disabled";
    private static final String KEY_UPDATE_CHECKER_ENABLED = "update_checker.enabled";
    private static final String KEY_UPDATE_CHECK_INTERVAL = "update_checker.check_interval";
    private static final String KEY_NOTIFY_ON_JOIN = "update_checker.notify_on_join";
    private static final String KEY_UPDATE_MESSAGE = "update_checker.message";
    
    private final Properties properties;
    private final Path configPath;
    
    public ForgeConfig() {
        this.properties = new Properties();
        this.configPath = FMLPaths.CONFIGDIR.get().resolve(CONFIG_FILE_NAME);
        loadOrCreate();
    }
    
    private void loadOrCreate() {
        if (Files.exists(configPath)) {
            try (InputStream is = Files.newInputStream(configPath)) {
                properties.load(is);
            } catch (IOException e) {
                DisableVillagerTradeForge.LOGGER.error("Failed to load config file", e);
                setDefaults();
            }
        } else {
            setDefaults();
            save();
        }
    }
    
    private void setDefaults() {
        properties.setProperty(KEY_MESSAGE_ENABLED, String.valueOf(Constants.DEFAULT_MESSAGE_ENABLED));
        properties.setProperty(KEY_MESSAGE_TEXT, Constants.DEFAULT_MESSAGE);
        properties.setProperty(KEY_DISABLED_DIMENSIONS, "");
        properties.setProperty(KEY_UPDATE_CHECKER_ENABLED, String.valueOf(Constants.DEFAULT_UPDATE_CHECKER_ENABLED));
        properties.setProperty(KEY_UPDATE_CHECK_INTERVAL, String.valueOf(Constants.DEFAULT_UPDATE_CHECK_INTERVAL));
        properties.setProperty(KEY_NOTIFY_ON_JOIN, String.valueOf(Constants.DEFAULT_NOTIFY_ON_JOIN));
        properties.setProperty(KEY_UPDATE_MESSAGE, Constants.DEFAULT_UPDATE_MESSAGE);
    }
    
    private void save() {
        try {
            Files.createDirectories(configPath.getParent());
            try (OutputStream os = Files.newOutputStream(configPath)) {
                properties.store(os, "DisableVillagerTrade Configuration\n" +
                    "message.enabled - Whether to show a message when trading is blocked\n" +
                    "message.text - The message to show (supports § color codes)\n" +
                    "dimensions.disabled - Comma-separated list of dimensions where trading is ALLOWED\n" +
                    "update_checker.enabled - Whether to check for updates\n" +
                    "update_checker.check_interval - Hours between update checks\n" +
                    "update_checker.notify_on_join - Notify ops when they join if update available\n" +
                    "update_checker.message - Update notification message");
            }
        } catch (IOException e) {
            DisableVillagerTradeForge.LOGGER.error("Failed to save config file", e);
        }
    }
    
    @Override
    public boolean isMessageEnabled() {
        return Boolean.parseBoolean(properties.getProperty(KEY_MESSAGE_ENABLED, String.valueOf(Constants.DEFAULT_MESSAGE_ENABLED)));
    }
    
    @Override
    public String getMessage() {
        return properties.getProperty(KEY_MESSAGE_TEXT, Constants.DEFAULT_MESSAGE);
    }
    
    @Override
    public List<String> getDisabledWorlds() {
        String value = properties.getProperty(KEY_DISABLED_DIMENSIONS, "");
        if (value == null || value.trim().isEmpty()) {
            return Collections.emptyList();
        }
        return new ArrayList<>(Arrays.asList(value.split(",")));
    }
    
    @Override
    public boolean isUpdateCheckerEnabled() {
        return Boolean.parseBoolean(properties.getProperty(KEY_UPDATE_CHECKER_ENABLED, String.valueOf(Constants.DEFAULT_UPDATE_CHECKER_ENABLED)));
    }
    
    @Override
    public int getUpdateCheckInterval() {
        try {
            return Integer.parseInt(properties.getProperty(KEY_UPDATE_CHECK_INTERVAL, String.valueOf(Constants.DEFAULT_UPDATE_CHECK_INTERVAL)));
        } catch (NumberFormatException e) {
            return Constants.DEFAULT_UPDATE_CHECK_INTERVAL;
        }
    }
    
    @Override
    public boolean isNotifyOnJoin() {
        return Boolean.parseBoolean(properties.getProperty(KEY_NOTIFY_ON_JOIN, String.valueOf(Constants.DEFAULT_NOTIFY_ON_JOIN)));
    }
    
    @Override
    public String getUpdateMessage() {
        return properties.getProperty(KEY_UPDATE_MESSAGE, Constants.DEFAULT_UPDATE_MESSAGE);
    }
    
    @Override
    public void reload() {
        loadOrCreate();
    }
}
