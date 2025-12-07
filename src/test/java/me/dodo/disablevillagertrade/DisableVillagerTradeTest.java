package me.dodo.disablevillagertrade;

import org.junit.jupiter.api.*;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("DisableVillagerTrade Plugin Structure Tests")
class DisableVillagerTradeTest {
    
    @Test
    @DisplayName("Plugin should extend JavaPlugin")
    void pluginExtendsJavaPlugin() {
        assertEquals("JavaPlugin", DisableVillagerTrade.class.getSuperclass().getSimpleName());
    }
    
    @Test
    @DisplayName("Plugin should have getInstance static method")
    void hasGetInstanceMethod() throws NoSuchMethodException {
        Method method = DisableVillagerTrade.class.getMethod("getInstance");
        assertTrue(Modifier.isStatic(method.getModifiers()));
        assertEquals(DisableVillagerTrade.class, method.getReturnType());
    }
    
    @Test
    @DisplayName("Plugin should have getPluginConfig method")
    void hasGetPluginConfigMethod() {
        assertDoesNotThrow(() -> DisableVillagerTrade.class.getMethod("getPluginConfig"));
    }
    
    @Test
    @DisplayName("Plugin should have onEnable method")
    void hasOnEnableMethod() {
        assertDoesNotThrow(() -> DisableVillagerTrade.class.getMethod("onEnable"));
    }
    
    @Test
    @DisplayName("Plugin should have onDisable method")
    void hasOnDisableMethod() {
        assertDoesNotThrow(() -> DisableVillagerTrade.class.getMethod("onDisable"));
    }
    
    @Test
    @DisplayName("Plugin should have reloadPluginConfig method")
    void hasReloadPluginConfigMethod() {
        assertDoesNotThrow(() -> DisableVillagerTrade.class.getMethod("reloadPluginConfig"));
    }
    
    @Test
    @DisplayName("Plugin class should be final")
    void pluginClassIsFinal() {
        assertTrue(Modifier.isFinal(DisableVillagerTrade.class.getModifiers()));
    }
}
