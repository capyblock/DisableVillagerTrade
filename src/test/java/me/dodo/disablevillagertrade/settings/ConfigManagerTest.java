package me.dodo.disablevillagertrade.settings;

import be.seeseemelk.mockbukkit.MockBukkit;
import be.seeseemelk.mockbukkit.ServerMock;
import me.dodo.disablevillagertrade.DisableVillagerTrade;
import me.dodo.disablevillagertrade.settings.configurations.Main;
import org.junit.jupiter.api.*;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("ConfigManager Tests")
class ConfigManagerTest {
    
    private ServerMock server;
    private DisableVillagerTrade plugin;
    private ConfigManager configManager;
    
    @BeforeEach
    void setUp() {
        server = MockBukkit.mock();
        plugin = MockBukkit.load(DisableVillagerTrade.class);
        configManager = DisableVillagerTrade.getConfigManager();
    }
    
    @AfterEach
    void tearDown() {
        MockBukkit.unmock();
    }
    
    @Test
    @DisplayName("ConfigManager should not be null after plugin load")
    void configManagerNotNull() {
        assertNotNull(configManager, "ConfigManager should be initialized");
    }
    
    @Test
    @DisplayName("Main config should not be null")
    void mainConfigNotNull() {
        Main main = configManager.getMain();
        assertNotNull(main, "Main config should not be null");
    }
    
    @Test
    @DisplayName("isEnabled should return boolean")
    void isEnabledReturnsBoolean() {
        Main main = configManager.getMain();
        // Should not throw exception
        boolean enabled = main.isEnabled();
        // Default should be true
        assertTrue(enabled, "Message should be enabled by default");
    }
    
    @Test
    @DisplayName("getContext should return non-empty string")
    void getContextReturnsString() {
        Main main = configManager.getMain();
        String context = main.getContext();
        
        assertNotNull(context, "Context should not be null");
        assertFalse(context.isEmpty(), "Context should not be empty");
    }
    
    @Test
    @DisplayName("getContext should contain default message")
    void getContextContainsDefaultMessage() {
        Main main = configManager.getMain();
        String context = main.getContext();
        
        // Default message contains "can't trade" or similar
        assertTrue(context.toLowerCase().contains("trade") || context.toLowerCase().contains("villager"),
            "Default context should mention trading or villagers");
    }
    
    @Test
    @DisplayName("getDisabledWorlds should return list")
    void getDisabledWorldsReturnsList() {
        Main main = configManager.getMain();
        List<String> disabledWorlds = main.getDisabledWorlds();
        
        assertNotNull(disabledWorlds, "Disabled worlds should not be null");
    }
    
    @Test
    @DisplayName("Default disabled worlds should contain example-world")
    void defaultDisabledWorldsContainsExample() {
        Main main = configManager.getMain();
        List<String> disabledWorlds = main.getDisabledWorlds();
        
        assertTrue(disabledWorlds.contains("example-world"),
            "Default disabled worlds should contain 'example-world'");
    }
}

