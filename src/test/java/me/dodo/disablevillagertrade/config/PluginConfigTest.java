package me.dodo.disablevillagertrade.config;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.java.JavaPlugin;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
@DisplayName("PluginConfig Tests")
class PluginConfigTest {

    @Mock
    private JavaPlugin mockPlugin;

    @Mock
    private FileConfiguration mockConfig;

    @BeforeEach
    void setUp() {
        when(mockPlugin.getConfig()).thenReturn(mockConfig);
    }

    @Nested
    @DisplayName("Message Settings Tests")
    class MessageSettingsTests {

        @Test
        @DisplayName("Should read message.enabled as true")
        void shouldReadMessageEnabledTrue() {
            when(mockConfig.getBoolean("message.enabled", true)).thenReturn(true);
            when(mockConfig.getString(eq("message.text"), anyString())).thenReturn("test");
            when(mockConfig.getStringList("disabled-worlds")).thenReturn(Collections.emptyList());
            when(mockConfig.getBoolean("update-checker.enabled", true)).thenReturn(false);
            when(mockConfig.getInt("update-checker.check-interval", 24)).thenReturn(24);
            when(mockConfig.getBoolean("update-checker.notify-on-join", true)).thenReturn(false);
            when(mockConfig.getString(eq("update-checker.message"), anyString())).thenReturn("test");

            PluginConfig config = new PluginConfig(mockPlugin);

            assertTrue(config.isMessageEnabled());
        }

        @Test
        @DisplayName("Should read message.enabled as false")
        void shouldReadMessageEnabledFalse() {
            when(mockConfig.getBoolean("message.enabled", true)).thenReturn(false);
            when(mockConfig.getString(eq("message.text"), anyString())).thenReturn("test");
            when(mockConfig.getStringList("disabled-worlds")).thenReturn(Collections.emptyList());
            when(mockConfig.getBoolean("update-checker.enabled", true)).thenReturn(false);
            when(mockConfig.getInt("update-checker.check-interval", 24)).thenReturn(24);
            when(mockConfig.getBoolean("update-checker.notify-on-join", true)).thenReturn(false);
            when(mockConfig.getString(eq("update-checker.message"), anyString())).thenReturn("test");

            PluginConfig config = new PluginConfig(mockPlugin);

            assertFalse(config.isMessageEnabled());
        }

        @Test
        @DisplayName("Should read custom message text")
        void shouldReadCustomMessageText() {
            String customMessage = "&cCustom blocked message!";
            when(mockConfig.getBoolean("message.enabled", true)).thenReturn(true);
            when(mockConfig.getString(eq("message.text"), anyString())).thenReturn(customMessage);
            when(mockConfig.getStringList("disabled-worlds")).thenReturn(Collections.emptyList());
            when(mockConfig.getBoolean("update-checker.enabled", true)).thenReturn(false);
            when(mockConfig.getInt("update-checker.check-interval", 24)).thenReturn(24);
            when(mockConfig.getBoolean("update-checker.notify-on-join", true)).thenReturn(false);
            when(mockConfig.getString(eq("update-checker.message"), anyString())).thenReturn("test");

            PluginConfig config = new PluginConfig(mockPlugin);

            assertEquals(customMessage, config.getMessage());
        }
    }

    @Nested
    @DisplayName("Disabled Worlds Tests")
    class DisabledWorldsTests {

        @Test
        @DisplayName("Should read disabled worlds list")
        void shouldReadDisabledWorldsList() {
            List<String> worlds = Arrays.asList("world_nether", "world_the_end");
            when(mockConfig.getBoolean("message.enabled", true)).thenReturn(true);
            when(mockConfig.getString(eq("message.text"), anyString())).thenReturn("test");
            when(mockConfig.getStringList("disabled-worlds")).thenReturn(worlds);
            when(mockConfig.getBoolean("update-checker.enabled", true)).thenReturn(false);
            when(mockConfig.getInt("update-checker.check-interval", 24)).thenReturn(24);
            when(mockConfig.getBoolean("update-checker.notify-on-join", true)).thenReturn(false);
            when(mockConfig.getString(eq("update-checker.message"), anyString())).thenReturn("test");

            PluginConfig config = new PluginConfig(mockPlugin);

            assertEquals(worlds, config.getDisabledWorlds());
            assertEquals(2, config.getDisabledWorlds().size());
        }

        @Test
        @DisplayName("Should return empty list when no disabled worlds")
        void shouldReturnEmptyListWhenNoDisabledWorlds() {
            when(mockConfig.getBoolean("message.enabled", true)).thenReturn(true);
            when(mockConfig.getString(eq("message.text"), anyString())).thenReturn("test");
            when(mockConfig.getStringList("disabled-worlds")).thenReturn(Collections.emptyList());
            when(mockConfig.getBoolean("update-checker.enabled", true)).thenReturn(false);
            when(mockConfig.getInt("update-checker.check-interval", 24)).thenReturn(24);
            when(mockConfig.getBoolean("update-checker.notify-on-join", true)).thenReturn(false);
            when(mockConfig.getString(eq("update-checker.message"), anyString())).thenReturn("test");

            PluginConfig config = new PluginConfig(mockPlugin);

            assertTrue(config.getDisabledWorlds().isEmpty());
        }

        @Test
        @DisplayName("Should handle null disabled worlds list")
        void shouldHandleNullDisabledWorldsList() {
            when(mockConfig.getBoolean("message.enabled", true)).thenReturn(true);
            when(mockConfig.getString(eq("message.text"), anyString())).thenReturn("test");
            when(mockConfig.getStringList("disabled-worlds")).thenReturn(null);
            when(mockConfig.getBoolean("update-checker.enabled", true)).thenReturn(false);
            when(mockConfig.getInt("update-checker.check-interval", 24)).thenReturn(24);
            when(mockConfig.getBoolean("update-checker.notify-on-join", true)).thenReturn(false);
            when(mockConfig.getString(eq("update-checker.message"), anyString())).thenReturn("test");

            PluginConfig config = new PluginConfig(mockPlugin);

            assertNotNull(config.getDisabledWorlds());
            assertTrue(config.getDisabledWorlds().isEmpty());
        }
    }

    @Nested
    @DisplayName("Update Checker Settings Tests")
    class UpdateCheckerSettingsTests {

        @Test
        @DisplayName("Should read update checker enabled")
        void shouldReadUpdateCheckerEnabled() {
            when(mockConfig.getBoolean("message.enabled", true)).thenReturn(true);
            when(mockConfig.getString(eq("message.text"), anyString())).thenReturn("test");
            when(mockConfig.getStringList("disabled-worlds")).thenReturn(Collections.emptyList());
            when(mockConfig.getBoolean("update-checker.enabled", true)).thenReturn(true);
            when(mockConfig.getInt("update-checker.check-interval", 24)).thenReturn(24);
            when(mockConfig.getBoolean("update-checker.notify-on-join", true)).thenReturn(true);
            when(mockConfig.getString(eq("update-checker.message"), anyString())).thenReturn("Update!");

            PluginConfig config = new PluginConfig(mockPlugin);

            assertTrue(config.isUpdateCheckerEnabled());
        }

        @Test
        @DisplayName("Should read update check interval")
        void shouldReadUpdateCheckInterval() {
            when(mockConfig.getBoolean("message.enabled", true)).thenReturn(true);
            when(mockConfig.getString(eq("message.text"), anyString())).thenReturn("test");
            when(mockConfig.getStringList("disabled-worlds")).thenReturn(Collections.emptyList());
            when(mockConfig.getBoolean("update-checker.enabled", true)).thenReturn(true);
            when(mockConfig.getInt("update-checker.check-interval", 24)).thenReturn(12);
            when(mockConfig.getBoolean("update-checker.notify-on-join", true)).thenReturn(true);
            when(mockConfig.getString(eq("update-checker.message"), anyString())).thenReturn("Update!");

            PluginConfig config = new PluginConfig(mockPlugin);

            assertEquals(12, config.getUpdateCheckInterval());
        }

        @Test
        @DisplayName("Should read notify on join setting")
        void shouldReadNotifyOnJoin() {
            when(mockConfig.getBoolean("message.enabled", true)).thenReturn(true);
            when(mockConfig.getString(eq("message.text"), anyString())).thenReturn("test");
            when(mockConfig.getStringList("disabled-worlds")).thenReturn(Collections.emptyList());
            when(mockConfig.getBoolean("update-checker.enabled", true)).thenReturn(true);
            when(mockConfig.getInt("update-checker.check-interval", 24)).thenReturn(24);
            when(mockConfig.getBoolean("update-checker.notify-on-join", true)).thenReturn(false);
            when(mockConfig.getString(eq("update-checker.message"), anyString())).thenReturn("Update!");

            PluginConfig config = new PluginConfig(mockPlugin);

            assertFalse(config.isNotifyOnJoin());
        }

        @Test
        @DisplayName("Should read update message")
        void shouldReadUpdateMessage() {
            String updateMsg = "&eNew version available!";
            when(mockConfig.getBoolean("message.enabled", true)).thenReturn(true);
            when(mockConfig.getString(eq("message.text"), anyString())).thenReturn("test");
            when(mockConfig.getStringList("disabled-worlds")).thenReturn(Collections.emptyList());
            when(mockConfig.getBoolean("update-checker.enabled", true)).thenReturn(true);
            when(mockConfig.getInt("update-checker.check-interval", 24)).thenReturn(24);
            when(mockConfig.getBoolean("update-checker.notify-on-join", true)).thenReturn(true);
            when(mockConfig.getString(eq("update-checker.message"), anyString())).thenReturn(updateMsg);

            PluginConfig config = new PluginConfig(mockPlugin);

            assertEquals(updateMsg, config.getUpdateMessage());
        }
    }
}
