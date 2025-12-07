package me.dodo.disablevillagertrade.bukkit.listeners;

import me.dodo.disablevillagertrade.bukkit.config.BukkitConfig;
import me.dodo.disablevillagertrade.bukkit.update.BukkitUpdateChecker;
import org.bukkit.Server;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitScheduler;
import org.bukkit.scheduler.BukkitTask;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
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
    private JavaPlugin mockPlugin;

    @Mock
    private BukkitUpdateChecker mockUpdateChecker;

    @Mock
    private BukkitConfig mockConfig;

    @Mock
    private PlayerJoinEvent mockEvent;

    @Mock
    private Player mockPlayer;

    @Mock
    private Server mockServer;

    @Mock
    private BukkitScheduler mockScheduler;

    @Mock
    private BukkitTask mockTask;

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
    @DisplayName("Notification Logic Tests")
    class NotificationLogicTests {

        @Test
        @DisplayName("Should not notify when notify-on-join is disabled")
        void shouldNotNotifyWhenDisabled() {
            when(mockConfig.isNotifyOnJoin()).thenReturn(false);

            listener.onPlayerJoin(mockEvent);

            verify(mockEvent, never()).getPlayer();
        }

        @Test
        @DisplayName("Should not notify player without permission")
        void shouldNotNotifyWithoutPermission() {
            when(mockConfig.isNotifyOnJoin()).thenReturn(true);
            when(mockEvent.getPlayer()).thenReturn(mockPlayer);
            when(mockPlayer.hasPermission("disabletrade.update")).thenReturn(false);

            listener.onPlayerJoin(mockEvent);

            verify(mockUpdateChecker, never()).isUpdateAvailable();
        }

        @Test
        @DisplayName("Should not notify when no update available")
        void shouldNotNotifyWhenNoUpdate() {
            when(mockConfig.isNotifyOnJoin()).thenReturn(true);
            when(mockEvent.getPlayer()).thenReturn(mockPlayer);
            when(mockPlayer.hasPermission("disabletrade.update")).thenReturn(true);
            when(mockUpdateChecker.isUpdateAvailable()).thenReturn(false);

            listener.onPlayerJoin(mockEvent);

            verify(mockPlayer, never()).getServer();
        }

        @Test
        @DisplayName("Should schedule notification when update available")
        void shouldScheduleNotificationWhenUpdateAvailable() {
            when(mockConfig.isNotifyOnJoin()).thenReturn(true);
            when(mockEvent.getPlayer()).thenReturn(mockPlayer);
            when(mockPlayer.hasPermission("disabletrade.update")).thenReturn(true);
            when(mockUpdateChecker.isUpdateAvailable()).thenReturn(true);
            when(mockPlayer.getServer()).thenReturn(mockServer);
            when(mockServer.getScheduler()).thenReturn(mockScheduler);
            when(mockScheduler.runTaskLater(eq(mockPlugin), any(Runnable.class), eq(40L))).thenReturn(mockTask);

            listener.onPlayerJoin(mockEvent);

            verify(mockScheduler).runTaskLater(eq(mockPlugin), any(Runnable.class), eq(40L));
        }

        @Test
        @DisplayName("Should send message with version placeholders replaced")
        void shouldSendMessageWithPlaceholders() {
            when(mockConfig.isNotifyOnJoin()).thenReturn(true);
            when(mockEvent.getPlayer()).thenReturn(mockPlayer);
            when(mockPlayer.hasPermission("disabletrade.update")).thenReturn(true);
            when(mockUpdateChecker.isUpdateAvailable()).thenReturn(true);
            when(mockPlayer.getServer()).thenReturn(mockServer);
            when(mockServer.getScheduler()).thenReturn(mockScheduler);

            ArgumentCaptor<Runnable> runnableCaptor = ArgumentCaptor.forClass(Runnable.class);
            when(mockScheduler.runTaskLater(eq(mockPlugin), runnableCaptor.capture(), eq(40L))).thenReturn(mockTask);

            listener.onPlayerJoin(mockEvent);

            // Simulate the scheduled task running
            when(mockPlayer.isOnline()).thenReturn(true);
            when(mockConfig.getUpdateMessage()).thenReturn("Update: %current% -> %latest%");
            when(mockUpdateChecker.getCurrentVersion()).thenReturn("1.0.0");
            when(mockUpdateChecker.getLatestVersion()).thenReturn("1.1.0");

            runnableCaptor.getValue().run();

            verify(mockPlayer).sendMessage("Update: 1.0.0 -> 1.1.0");
        }

        @Test
        @DisplayName("Should not send message if player went offline")
        void shouldNotSendMessageIfPlayerOffline() {
            when(mockConfig.isNotifyOnJoin()).thenReturn(true);
            when(mockEvent.getPlayer()).thenReturn(mockPlayer);
            when(mockPlayer.hasPermission("disabletrade.update")).thenReturn(true);
            when(mockUpdateChecker.isUpdateAvailable()).thenReturn(true);
            when(mockPlayer.getServer()).thenReturn(mockServer);
            when(mockServer.getScheduler()).thenReturn(mockScheduler);

            ArgumentCaptor<Runnable> runnableCaptor = ArgumentCaptor.forClass(Runnable.class);
            when(mockScheduler.runTaskLater(eq(mockPlugin), runnableCaptor.capture(), eq(40L))).thenReturn(mockTask);

            listener.onPlayerJoin(mockEvent);

            // Simulate the scheduled task running when player is offline
            when(mockPlayer.isOnline()).thenReturn(false);

            runnableCaptor.getValue().run();

            verify(mockPlayer, never()).sendMessage(anyString());
        }
    }
}

