package me.dodo.disablevillagertrade;

import be.seeseemelk.mockbukkit.MockBukkit;
import be.seeseemelk.mockbukkit.ServerMock;
import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("DisableVillagerTrade Plugin Tests")
class DisableVillagerTradeTest {
    
    private ServerMock server;
    private DisableVillagerTrade plugin;
    
    @BeforeEach
    void setUp() {
        server = MockBukkit.mock();
        plugin = MockBukkit.load(DisableVillagerTrade.class);
    }
    
    @AfterEach
    void tearDown() {
        MockBukkit.unmock();
    }
    
    @Test
    @DisplayName("Plugin should load successfully")
    void pluginLoads() {
        assertNotNull(plugin, "Plugin should not be null");
        assertTrue(plugin.isEnabled(), "Plugin should be enabled");
    }
    
    @Test
    @DisplayName("Plugin should have correct name")
    void pluginHasCorrectName() {
        assertEquals("DisableVillagerTrade", plugin.getName());
    }
    
    @Test
    @DisplayName("ConfigManager should be initialized")
    void configManagerInitialized() {
        assertNotNull(DisableVillagerTrade.getConfigManager(), "ConfigManager should not be null");
    }
    
    @Test
    @DisplayName("Main config should be loaded")
    void mainConfigLoaded() {
        assertNotNull(DisableVillagerTrade.getConfigManager().getMain(), "Main config should not be null");
    }
    
    @Test
    @DisplayName("Config should have default message enabled")
    void configHasDefaultMessageEnabled() {
        assertTrue(DisableVillagerTrade.getConfigManager().getMain().isEnabled(), 
            "Message should be enabled by default");
    }
    
    @Test
    @DisplayName("Config should have default message context")
    void configHasDefaultMessageContext() {
        String context = DisableVillagerTrade.getConfigManager().getMain().getContext();
        assertNotNull(context, "Context should not be null");
        assertFalse(context.isEmpty(), "Context should not be empty");
    }
    
    @Test
    @DisplayName("Config should have disabled worlds list")
    void configHasDisabledWorldsList() {
        assertNotNull(DisableVillagerTrade.getConfigManager().getMain().getDisabledWorlds(),
            "Disabled worlds list should not be null");
    }
}

