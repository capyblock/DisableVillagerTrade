package me.dodo.disablevillagertrade.config;

import org.junit.jupiter.api.*;

import java.lang.reflect.Constructor;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("PluginConfig Tests")
class PluginConfigTest {
    
    @Test
    @DisplayName("Should have constructor with JavaPlugin parameter")
    void hasConstructorWithJavaPlugin() {
        Constructor<?>[] constructors = PluginConfig.class.getConstructors();
        boolean hasJavaPluginConstructor = false;
        
        for (Constructor<?> constructor : constructors) {
            Class<?>[] paramTypes = constructor.getParameterTypes();
            if (paramTypes.length == 1 && 
                paramTypes[0].getSimpleName().equals("JavaPlugin")) {
                hasJavaPluginConstructor = true;
                break;
            }
        }
        
        assertTrue(hasJavaPluginConstructor);
    }
    
    @Test
    @DisplayName("Should have isMessageEnabled method")
    void hasIsMessageEnabledMethod() throws NoSuchMethodException {
        var method = PluginConfig.class.getMethod("isMessageEnabled");
        assertEquals(boolean.class, method.getReturnType());
    }
    
    @Test
    @DisplayName("Should have getMessage method")
    void hasGetMessageMethod() throws NoSuchMethodException {
        var method = PluginConfig.class.getMethod("getMessage");
        assertEquals(String.class, method.getReturnType());
    }
    
    @Test
    @DisplayName("Should have getDisabledWorlds method")
    void hasGetDisabledWorldsMethod() throws NoSuchMethodException {
        var method = PluginConfig.class.getMethod("getDisabledWorlds");
        assertEquals(List.class, method.getReturnType());
    }
}

