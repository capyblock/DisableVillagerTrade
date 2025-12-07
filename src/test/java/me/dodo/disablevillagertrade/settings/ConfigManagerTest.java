package me.dodo.disablevillagertrade.settings;

import me.dodo.disablevillagertrade.settings.configurations.Main;
import org.junit.jupiter.api.*;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("ConfigManager Structure Tests")
class ConfigManagerTest {
    
    @Test
    @DisplayName("Should have loadConfig method")
    void hasLoadConfigMethod() {
        assertDoesNotThrow(() -> ConfigManager.class.getMethod("loadConfig"));
    }
    
    @Test
    @DisplayName("Should have getMain method returning Main interface")
    void hasGetMainMethod() throws NoSuchMethodException {
        assertEquals(Main.class, ConfigManager.class.getMethod("getMain").getReturnType());
    }
    
    @Nested
    @DisplayName("Main Interface Tests")
    class MainInterfaceTests {
        
        @Test
        @DisplayName("Main should be an interface")
        void mainIsInterface() {
            assertTrue(Main.class.isInterface());
        }
        
        @Test
        @DisplayName("Main should have isEnabled method")
        void hasIsEnabledMethod() throws NoSuchMethodException {
            assertEquals(boolean.class, Main.class.getMethod("isEnabled").getReturnType());
        }
        
        @Test
        @DisplayName("Main should have getContext method")
        void hasGetContextMethod() throws NoSuchMethodException {
            assertEquals(String.class, Main.class.getMethod("getContext").getReturnType());
        }
        
        @Test
        @DisplayName("Main should have getDisabledWorlds method")
        void hasGetDisabledWorldsMethod() throws NoSuchMethodException {
            assertEquals(List.class, Main.class.getMethod("getDisabledWorlds").getReturnType());
        }
    }
}
