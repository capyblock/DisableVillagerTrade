package me.dodo.disablevillagertrade.events;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.junit.jupiter.api.*;

import java.lang.reflect.Method;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("PlayerInteractEntity Structure Tests")
class PlayerInteractEntityTest {
    
    @Test
    @DisplayName("Should implement Listener interface")
    void implementsListener() {
        assertTrue(Listener.class.isAssignableFrom(PlayerInteractEntity.class));
    }
    
    @Test
    @DisplayName("Should have @EventHandler annotated method")
    void hasEventHandlerAnnotation() throws NoSuchMethodException {
        Method method = PlayerInteractEntity.class.getMethod("onPlayerInteractEntity", PlayerInteractEntityEvent.class);
        assertTrue(method.isAnnotationPresent(EventHandler.class));
    }
}
