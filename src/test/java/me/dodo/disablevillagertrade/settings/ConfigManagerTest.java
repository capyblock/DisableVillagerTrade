package me.dodo.disablevillagertrade.settings;

import me.dodo.disablevillagertrade.settings.configurations.Main;
import org.junit.jupiter.api.*;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("ConfigManager Tests")
class ConfigManagerTest {
    
    @Test
    @DisplayName("ConfigManager class should exist")
    void classExists() {
        assertDoesNotThrow(() -> Class.forName("me.dodo.disablevillagertrade.settings.ConfigManager"));
    }
    
    @Test
    @DisplayName("ConfigManager should have constructor with JavaPlugin parameter")
    void hasConstructorWithJavaPlugin() {
        Constructor<?>[] constructors = ConfigManager.class.getConstructors();
        boolean hasJavaPluginConstructor = false;
        
        for (Constructor<?> constructor : constructors) {
            Class<?>[] paramTypes = constructor.getParameterTypes();
            if (paramTypes.length == 1 && 
                paramTypes[0].getSimpleName().equals("JavaPlugin")) {
                hasJavaPluginConstructor = true;
                break;
            }
        }
        
        assertTrue(hasJavaPluginConstructor, 
            "ConfigManager should have constructor with JavaPlugin parameter");
    }
    
    @Test
    @DisplayName("ConfigManager should have loadConfig method")
    void hasLoadConfigMethod() {
        assertDoesNotThrow(() -> ConfigManager.class.getMethod("loadConfig"),
            "ConfigManager should have loadConfig method");
    }
    
    @Test
    @DisplayName("ConfigManager should have getMain method")
    void hasGetMainMethod() throws NoSuchMethodException {
        Method method = ConfigManager.class.getMethod("getMain");
        assertEquals(Main.class, method.getReturnType(),
            "getMain should return Main type");
    }
    
    @Test
    @DisplayName("Main interface should exist")
    void mainInterfaceExists() {
        assertDoesNotThrow(() -> Class.forName("me.dodo.disablevillagertrade.settings.configurations.Main"));
    }
    
    @Test
    @DisplayName("Main interface should have isEnabled method")
    void mainHasIsEnabledMethod() throws NoSuchMethodException {
        Method method = Main.class.getMethod("isEnabled");
        assertEquals(boolean.class, method.getReturnType(),
            "isEnabled should return boolean");
    }
    
    @Test
    @DisplayName("Main interface should have getContext method")
    void mainHasGetContextMethod() throws NoSuchMethodException {
        Method method = Main.class.getMethod("getContext");
        assertEquals(String.class, method.getReturnType(),
            "getContext should return String");
    }
    
    @Test
    @DisplayName("Main interface should have getDisabledWorlds method")
    void mainHasGetDisabledWorldsMethod() throws NoSuchMethodException {
        Method method = Main.class.getMethod("getDisabledWorlds");
        assertEquals(List.class, method.getReturnType(),
            "getDisabledWorlds should return List");
    }
    
    @Test
    @DisplayName("Main should be an interface")
    void mainIsInterface() {
        assertTrue(Main.class.isInterface(), "Main should be an interface");
    }
}
