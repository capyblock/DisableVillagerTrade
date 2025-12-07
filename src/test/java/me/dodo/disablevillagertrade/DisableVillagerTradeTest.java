package me.dodo.disablevillagertrade;

import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("DisableVillagerTrade Plugin Structure Tests")
class DisableVillagerTradeTest {
    
    @Test
    @DisplayName("Plugin should extend JavaPlugin")
    void pluginExtendsJavaPlugin() {
        assertEquals("JavaPlugin", DisableVillagerTrade.class.getSuperclass().getSimpleName());
    }
    
    @Test
    @DisplayName("Plugin should have getConfigManager static method")
    void hasGetConfigManagerMethod() {
        assertDoesNotThrow(() -> DisableVillagerTrade.class.getMethod("getConfigManager"));
    }
}
