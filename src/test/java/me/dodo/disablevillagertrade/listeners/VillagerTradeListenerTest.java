package me.dodo.disablevillagertrade.listeners;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.junit.jupiter.api.*;

import java.lang.reflect.Method;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("VillagerTradeListener Tests")
class VillagerTradeListenerTest {
    
    @Test
    @DisplayName("Should implement Listener interface")
    void implementsListener() {
        assertTrue(Listener.class.isAssignableFrom(VillagerTradeListener.class));
    }
    
    @Test
    @DisplayName("Should have @EventHandler annotated method")
    void hasEventHandlerAnnotation() throws NoSuchMethodException {
        Method method = VillagerTradeListener.class.getMethod("onPlayerInteractEntity", PlayerInteractEntityEvent.class);
        assertTrue(method.isAnnotationPresent(EventHandler.class));
    }
    
    @Test
    @DisplayName("Should have BYPASS_PERMISSION constant")
    void hasBypassPermissionConstant() throws NoSuchFieldException {
        var field = VillagerTradeListener.class.getField("BYPASS_PERMISSION");
        assertEquals(String.class, field.getType());
    }
    
    @Test
    @DisplayName("BYPASS_PERMISSION should be correct value")
    void bypassPermissionValue() {
        assertEquals("disabletrade.bypass", VillagerTradeListener.BYPASS_PERMISSION);
    }
}

