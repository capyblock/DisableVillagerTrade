package me.dodo.disablevillagertrade.bukkit.listeners;

import me.dodo.disablevillagertrade.bukkit.DisableVillagerTrade;
import me.dodo.disablevillagertrade.bukkit.config.BukkitConfig;
import me.dodo.disablevillagertrade.bukkit.update.BukkitUpdateChecker;
import me.dodo.disablevillagertrade.common.Constants;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
@DisplayName("UpdateNotifyListener Tests")
class UpdateNotifyListenerTest {

    @Mock
    private DisableVillagerTrade mockPlugin;

    @Mock
    private BukkitUpdateChecker mockUpdateChecker;

    @Mock
    private BukkitConfig mockConfig;

    @Mock
    private PlayerJoinEvent mockEvent;

    @Mock
    private Player mockPlayer;

    private UpdateNotifyListener listener;

    @BeforeEach
    void setUp() {
        listener = new UpdateNotifyListener(mockPlugin, mockUpdateChecker, mockConfig);
    }

    @Nested
    @DisplayName("Interface Tests")
    class InterfaceTests {

        @Test
        @DisplayName("Should implement Listener interface")
        void implementsListener() {
            assertTrue(Listener.class.isAssignableFrom(UpdateNotifyListener.class));
        }

        @Test
        @DisplayName("Should have @EventHandler annotated method")
        void hasEventHandlerAnnotation() throws NoSuchMethodException {
            var method = UpdateNotifyListener.class.getMethod("onPlayerJoin", PlayerJoinEvent.class);
            assertTrue(method.isAnnotationPresent(EventHandler.class));
        }
    }

    @Nested
    @DisplayName("Permission Check Tests")
    class PermissionCheckTests {

        @Test
        @DisplayName("Should not schedule notification when player lacks permission")
        void shouldNotScheduleWhenNoPermission() {
            when(mockEvent.getPlayer()).thenReturn(mockPlayer);
            when(mockPlayer.hasPermission(Constants.PERMISSION_UPDATE)).thenReturn(false);

            listener.onPlayerJoin(mockEvent);

            // No BukkitRunnable should be scheduled if player has no permission
            verify(mockUpdateChecker, never()).isUpdateAvailable();
        }
    }

    @Nested
    @DisplayName("Constructor Tests")
    class ConstructorTests {

        @Test
        @DisplayName("Should accept DisableVillagerTrade, BukkitUpdateChecker, and BukkitConfig")
        void shouldAcceptCorrectParameters() {
            var constructors = UpdateNotifyListener.class.getConstructors();
            boolean hasCorrectConstructor = false;

            for (var constructor : constructors) {
                var paramTypes = constructor.getParameterTypes();
                if (paramTypes.length == 3 &&
                    paramTypes[0] == DisableVillagerTrade.class &&
                    paramTypes[1] == BukkitUpdateChecker.class &&
                    paramTypes[2] == BukkitConfig.class) {
                    hasCorrectConstructor = true;
                    break;
                }
            }

            assertTrue(hasCorrectConstructor);
        }
    }
}
