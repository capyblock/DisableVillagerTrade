package me.dodo.disablevillagertrade.fabric.config;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import me.dodo.disablevillagertrade.common.Constants;
import me.dodo.disablevillagertrade.common.ModConfig;
import net.fabricmc.loader.api.FabricLoader;

import java.io.IOException;
import java.io.Reader;
import java.io.Writer;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Fabric implementation of the configuration.
 * Uses JSON file format for configuration.
 */
public class FabricConfig implements ModConfig {
    
    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();
    private static final Path CONFIG_PATH = FabricLoader.getInstance()
        .getConfigDir()
        .resolve(Constants.MOD_ID + ".json");
    
    private ConfigData data;
    
    public FabricConfig() {
        load();
    }
    
    private void load() {
        if (Files.exists(CONFIG_PATH)) {
            try (Reader reader = Files.newBufferedReader(CONFIG_PATH)) {
                data = GSON.fromJson(reader, ConfigData.class);
                if (data == null) {
                    data = new ConfigData();
                }
            } catch (IOException e) {
                data = new ConfigData();
            }
        } else {
            data = new ConfigData();
            save();
        }
    }
    
    private void save() {
        try {
            Files.createDirectories(CONFIG_PATH.getParent());
            try (Writer writer = Files.newBufferedWriter(CONFIG_PATH)) {
                GSON.toJson(data, writer);
            }
        } catch (IOException e) {
            // Log error silently
        }
    }
    
    @Override
    public boolean isMessageEnabled() {
        return data.messageEnabled;
    }
    
    @Override
    public String getMessage() {
        return data.message;
    }
    
    @Override
    public List<String> getDisabledWorlds() {
        return data.disabledDimensions != null ? data.disabledDimensions : Collections.emptyList();
    }
    
    @Override
    public boolean isUpdateCheckerEnabled() {
        return data.updateCheckerEnabled;
    }
    
    @Override
    public int getUpdateCheckInterval() {
        return data.updateCheckInterval;
    }
    
    @Override
    public boolean isNotifyOnJoin() {
        return data.notifyOnJoin;
    }
    
    @Override
    public String getUpdateMessage() {
        return data.updateMessage;
    }
    
    @Override
    public void reload() {
        load();
    }
    
    /**
     * Internal config data class for JSON serialization.
     */
    private static class ConfigData {
        boolean messageEnabled = Constants.DEFAULT_MESSAGE_ENABLED;
        String message = Constants.DEFAULT_MESSAGE;
        List<String> disabledDimensions = new ArrayList<>();
        boolean updateCheckerEnabled = Constants.DEFAULT_UPDATE_CHECKER_ENABLED;
        int updateCheckInterval = Constants.DEFAULT_UPDATE_CHECK_INTERVAL;
        boolean notifyOnJoin = Constants.DEFAULT_NOTIFY_ON_JOIN;
        String updateMessage = Constants.DEFAULT_UPDATE_MESSAGE;
    }
}

