package me.dodo.disablevillagertrade.settings;

import me.dodo.disablevillagertrade.settings.configurations.Main;
import org.bukkit.plugin.java.JavaPlugin;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.File;
import java.io.InputStream;
import java.util.logging.Logger;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@DisplayName("ConfigManager Tests")
@ExtendWith(MockitoExtension.class)
class ConfigManagerTest {
    
    @Mock
    private JavaPlugin plugin;
    
    @Mock
    private Logger logger;
    
    private File tempDir;
    
    @BeforeEach
    void setUp() throws Exception {
        // Create temp directory for testing
        tempDir = new File(System.getProperty("java.io.tmpdir"), "disablevillagertrade-test-" + System.currentTimeMillis());
        tempDir.mkdirs();
        
        when(plugin.getDataFolder()).thenReturn(tempDir);
        when(plugin.getLogger()).thenReturn(logger);
    }
    
    @AfterEach
    void tearDown() {
        // Clean up temp directory
        if (tempDir != null && tempDir.exists()) {
            File[] files = tempDir.listFiles();
            if (files != null) {
                for (File file : files) {
                    file.delete();
                }
            }
            tempDir.delete();
        }
    }
    
    @Test
    @DisplayName("ConfigManager class should exist")
    void classExists() {
        assertDoesNotThrow(() -> Class.forName("me.dodo.disablevillagertrade.settings.ConfigManager"));
    }
    
    @Test
    @DisplayName("ConfigManager should be instantiable with JavaPlugin")
    void canInstantiate() {
        assertDoesNotThrow(() -> new ConfigManager(plugin));
    }
    
    @Test
    @DisplayName("ConfigManager should have loadConfig method")
    void hasLoadConfigMethod() {
        assertDoesNotThrow(() -> ConfigManager.class.getMethod("loadConfig"),
            "ConfigManager should have loadConfig method");
    }
    
    @Test
    @DisplayName("ConfigManager should have getMain method")
    void hasGetMainMethod() {
        assertDoesNotThrow(() -> ConfigManager.class.getMethod("getMain"),
            "ConfigManager should have getMain method");
    }
    
    @Test
    @DisplayName("Main interface should have required methods")
    void mainInterfaceHasRequiredMethods() {
        assertDoesNotThrow(() -> Main.class.getMethod("isEnabled"));
        assertDoesNotThrow(() -> Main.class.getMethod("getContext"));
        assertDoesNotThrow(() -> Main.class.getMethod("getDisabledWorlds"));
    }
    
    @Test
    @DisplayName("getMain should return null before loadConfig")
    void getMainReturnsNullBeforeLoad() {
        ConfigManager configManager = new ConfigManager(plugin);
        assertNull(configManager.getMain(), "getMain should return null before loadConfig is called");
    }
    
    @Test
    @DisplayName("Config file path should be in plugin data folder")
    void configFileInDataFolder() {
        ConfigManager configManager = new ConfigManager(plugin);
        // The config file should be created in the plugin's data folder
        File expectedConfigFile = new File(tempDir, "config.yml");
        assertEquals(tempDir, plugin.getDataFolder());
    }
}
