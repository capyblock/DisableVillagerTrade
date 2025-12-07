package me.dodo.disablevillagertrade;

import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("DisableVillagerTrade Plugin Tests")
class DisableVillagerTradeTest {
    
    @Test
    @DisplayName("Plugin class should exist")
    void pluginClassExists() {
        assertDoesNotThrow(() -> Class.forName("me.dodo.disablevillagertrade.DisableVillagerTrade"));
    }
    
    @Test
    @DisplayName("Plugin should extend JavaPlugin")
    void pluginExtendsJavaPlugin() {
        assertTrue(org.bukkit.plugin.java.JavaPlugin.class.isAssignableFrom(DisableVillagerTrade.class),
            "DisableVillagerTrade should extend JavaPlugin");
    }
    
    @Test
    @DisplayName("Plugin should have getConfigManager method")
    void pluginHasGetConfigManagerMethod() {
        assertDoesNotThrow(() -> DisableVillagerTrade.class.getMethod("getConfigManager"),
            "Plugin should have getConfigManager method");
    }
    
    @Test
    @DisplayName("Plugin should have onEnable method")
    void pluginHasOnEnableMethod() {
        assertDoesNotThrow(() -> DisableVillagerTrade.class.getMethod("onEnable"),
            "Plugin should have onEnable method");
    }
}
