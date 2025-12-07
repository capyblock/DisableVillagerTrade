package me.dodo.disablevillagertrade.bukkit.listeners;

import me.dodo.disablevillagertrade.common.Constants;
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
    @DisplayName("Constants.PERMISSION_BYPASS should have correct value")
    void bypassPermissionValue() {
        assertEquals("disabletrade.bypass", Constants.PERMISSION_BYPASS);
    }

    @Test
    @DisplayName("Should have constructor with DisableVillagerTrade parameter")
    void hasConstructorWithPlugin() {
        var constructors = VillagerTradeListener.class.getConstructors();
        boolean hasPluginConstructor = false;

        for (var constructor : constructors) {
            var paramTypes = constructor.getParameterTypes();
            if (paramTypes.length == 1 &&
                paramTypes[0].getSimpleName().equals("DisableVillagerTrade")) {
                hasPluginConstructor = true;
                break;
            }
        }

        assertTrue(hasPluginConstructor);
    }
}
