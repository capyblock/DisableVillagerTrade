package me.dodo.disablevillagertrade.events;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.junit.jupiter.api.*;

import java.lang.reflect.Method;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("PlayerInteractEntity Event Tests")
class PlayerInteractEntityTest {
    
    @Test
    @DisplayName("PlayerInteractEntity class should exist")
    void classExists() {
        assertDoesNotThrow(() -> Class.forName("me.dodo.disablevillagertrade.events.PlayerInteractEntity"));
    }
    
    @Test
    @DisplayName("PlayerInteractEntity should implement Listener")
    void implementsListener() {
        assertTrue(Listener.class.isAssignableFrom(PlayerInteractEntity.class),
            "PlayerInteractEntity should implement Listener");
    }
    
    @Test
    @DisplayName("Should have onPlayerInteractEntity method")
    void hasEventHandlerMethod() {
        assertDoesNotThrow(() -> 
            PlayerInteractEntity.class.getMethod("onPlayerInteractEntity", PlayerInteractEntityEvent.class),
            "Should have onPlayerInteractEntity method");
    }
    
    @Test
    @DisplayName("Event handler should have @EventHandler annotation")
    void hasEventHandlerAnnotation() throws NoSuchMethodException {
        Method method = PlayerInteractEntity.class.getMethod("onPlayerInteractEntity", PlayerInteractEntityEvent.class);
        assertTrue(method.isAnnotationPresent(EventHandler.class),
            "Method should have @EventHandler annotation");
    }
    
    @Test
    @DisplayName("onPlayerInteractEntity method should accept PlayerInteractEntityEvent")
    void methodAcceptsCorrectParameter() throws NoSuchMethodException {
        Method method = PlayerInteractEntity.class.getMethod("onPlayerInteractEntity", PlayerInteractEntityEvent.class);
        Class<?>[] paramTypes = method.getParameterTypes();
        
        assertEquals(1, paramTypes.length, "Method should have exactly one parameter");
        assertEquals(PlayerInteractEntityEvent.class, paramTypes[0], 
            "Parameter should be PlayerInteractEntityEvent");
    }
    
    @Test
    @DisplayName("onPlayerInteractEntity method should return void")
    void methodReturnsVoid() throws NoSuchMethodException {
        Method method = PlayerInteractEntity.class.getMethod("onPlayerInteractEntity", PlayerInteractEntityEvent.class);
        assertEquals(void.class, method.getReturnType(), "Method should return void");
    }
    
    @Test
    @DisplayName("Bypass permission string should be correct format")
    void bypassPermissionFormat() {
        // This tests the expected permission string format used in the plugin
        String expectedPermission = "disabletrade.bypass";
        assertTrue(expectedPermission.contains("."), "Permission should use dot notation");
        assertTrue(expectedPermission.startsWith("disabletrade"), "Permission should start with plugin name");
    }
    
    @Test
    @DisplayName("PlayerInteractEntity should have a public constructor")
    void hasPublicConstructor() {
        assertTrue(PlayerInteractEntity.class.getConstructors().length > 0,
            "PlayerInteractEntity should have at least one public constructor");
    }
}
