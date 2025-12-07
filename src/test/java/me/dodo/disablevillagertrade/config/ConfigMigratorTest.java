package me.dodo.disablevillagertrade.config;

import org.junit.jupiter.api.*;

import java.lang.reflect.Method;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("ConfigMigrator Tests")
class ConfigMigratorTest {
    
    @Test
    @DisplayName("Should have migrateIfNeeded method")
    void hasMigrateIfNeededMethod() throws NoSuchMethodException {
        Method method = ConfigMigrator.class.getMethod("migrateIfNeeded");
        assertEquals(boolean.class, method.getReturnType());
    }
    
    @Test
    @DisplayName("Should have constructor with JavaPlugin parameter")
    void hasConstructorWithJavaPlugin() {
        var constructors = ConfigMigrator.class.getConstructors();
        boolean hasJavaPluginConstructor = false;
        
        for (var constructor : constructors) {
            var paramTypes = constructor.getParameterTypes();
            if (paramTypes.length == 1 && 
                paramTypes[0].getSimpleName().equals("JavaPlugin")) {
                hasJavaPluginConstructor = true;
                break;
            }
        }
        
        assertTrue(hasJavaPluginConstructor);
    }
}

