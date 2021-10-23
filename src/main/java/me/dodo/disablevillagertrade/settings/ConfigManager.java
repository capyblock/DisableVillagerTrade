package me.dodo.disablevillagertrade.settings;

import me.dodo.disablevillagertrade.settings.configurations.Main;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.*;
import java.util.List;
import java.util.Objects;

public class ConfigManager {
    private Main main;

    private final File configFile;
    private final File configDirectory;
    private final JavaPlugin javaPlugin;

    public ConfigManager(JavaPlugin javaPlugin) {
        this.javaPlugin = javaPlugin;
        this.configFile = new File(this.javaPlugin.getDataFolder(), "config.yml");
        this.configDirectory = new File(this.javaPlugin.getDataFolder().getPath());
    }

    public void loadConfig() {
        if (!this.configFile.exists()) {
            this.writeDefaultConfig();
        }
        try {
            YamlConfiguration yamlConfiguration = new YamlConfiguration();
            yamlConfiguration.loadFromString(FileUtils.readFileToString(this.configFile, "UTF-8"));
            this.main = new Main() {
                @Override
                public boolean isEnabled() {
                    return Objects.requireNonNull(yamlConfiguration.getConfigurationSection("message")).getBoolean("enabled");
                }

                @Override
                public String getContext() {
                    return Objects.requireNonNull(yamlConfiguration.getConfigurationSection("message")).getString("context");
                }

                @Override
                public List<String> getDisabledWorlds() {
                    return yamlConfiguration.getStringList("disabled-worlds");
                }
            };
        } catch (IOException | InvalidConfigurationException e) {
            e.printStackTrace();
        }
    }

    private void writeDefaultConfig() {
        this.javaPlugin.getLogger().info("Creating the default config.");
        InputStream inputStream = this.javaPlugin.getResource("config.yml");
        if (this.configDirectory.mkdirs()) {
            this.javaPlugin.getLogger().info("Created the plugin directory.");
        }
        try {
            if (this.configFile.createNewFile()) {
                this.javaPlugin.getLogger().info("Created the default config.");
            }
        } catch (IOException e) {
            e.printStackTrace();
            return;
        }
        try (BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(this.configFile))) {
            assert inputStream != null;
            IOUtils.copy(inputStream, bufferedWriter, "UTF-8");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Main getMain() {
        return main;
    }
}
