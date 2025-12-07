package me.dodo.disablevillagertrade;

import org.junit.jupiter.api.*;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;

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
        Class<?> pluginClass = DisableVillagerTrade.class;
        Class<?> superClass = pluginClass.getSuperclass();
        assertEquals("JavaPlugin", superClass.getSimpleName(),
            "DisableVillagerTrade should extend JavaPlugin");
    }
    
    @Test
    @DisplayName("Plugin should have static getConfigManager method")
    void pluginHasGetConfigManagerMethod() throws NoSuchMethodException {
        Method method = DisableVillagerTrade.class.getMethod("getConfigManager");
        assertTrue(Modifier.isStatic(method.getModifiers()), 
            "getConfigManager should be static");
        assertTrue(Modifier.isPublic(method.getModifiers()), 
            "getConfigManager should be public");
    }
    
    @Test
    @DisplayName("Plugin should have onEnable method")
    void pluginHasOnEnableMethod() {
        assertDoesNotThrow(() -> DisableVillagerTrade.class.getMethod("onEnable"),
            "Plugin should have onEnable method");
    }
    
    @Test
    @DisplayName("Plugin class should be final")
    void pluginClassIsFinal() {
        assertTrue(Modifier.isFinal(DisableVillagerTrade.class.getModifiers()),
            "Plugin class should be final");
    }
}
