package me.dodo.disablevillagertrade.bukkit.config;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.java.JavaPlugin;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;

import java.util.logging.Logger;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
@DisplayName("ConfigMigrator Tests")
class ConfigMigratorTest {

    @Mock
    private JavaPlugin mockPlugin;

    @Mock
    private FileConfiguration mockConfig;

    @Mock
    private Logger mockLogger;

    @BeforeEach
    void setUp() {
        when(mockPlugin.getConfig()).thenReturn(mockConfig);
        when(mockPlugin.getLogger()).thenReturn(mockLogger);
    }

    @Nested
    @DisplayName("No Migration Needed Tests")
    class NoMigrationNeededTests {

        @Test
        @DisplayName("Should not migrate when config version is current")
        void shouldNotMigrateWhenVersionIsCurrent() {
            when(mockConfig.getInt("config-version", 1)).thenReturn(3);

            ConfigMigrator migrator = new ConfigMigrator(mockPlugin);
            boolean result = migrator.migrateIfNeeded();

            assertFalse(result);
            verify(mockPlugin, never()).saveConfig();
        }

        @Test
        @DisplayName("Should not migrate when config version is higher")
        void shouldNotMigrateWhenVersionIsHigher() {
            when(mockConfig.getInt("config-version", 1)).thenReturn(4);

            ConfigMigrator migrator = new ConfigMigrator(mockPlugin);
            boolean result = migrator.migrateIfNeeded();

            assertFalse(result);
            verify(mockPlugin, never()).saveConfig();
        }
    }

    @Nested
    @DisplayName("V1 to V2 Migration Tests")
    class V1ToV2MigrationTests {

        @Test
        @DisplayName("Should migrate message.context to message.text")
        void shouldMigrateContextToText() {
            when(mockConfig.getInt("config-version", 1)).thenReturn(1);
            when(mockConfig.contains("message.context")).thenReturn(true);
            when(mockConfig.getString("message.context")).thenReturn("&cOld message");
            when(mockConfig.contains("update-checker")).thenReturn(true);

            ConfigMigrator migrator = new ConfigMigrator(mockPlugin);
            boolean result = migrator.migrateIfNeeded();

            assertTrue(result);
            verify(mockConfig).set("message.text", "&cOld message");
            verify(mockConfig).set("message.context", null);
            verify(mockConfig).set("config-version", 3);
            verify(mockPlugin).saveConfig();
        }

        @Test
        @DisplayName("Should not migrate when message.context does not exist")
        void shouldNotMigrateWhenContextDoesNotExist() {
            when(mockConfig.getInt("config-version", 1)).thenReturn(1);
            when(mockConfig.contains("message.context")).thenReturn(false);
            when(mockConfig.contains("update-checker")).thenReturn(true);

            ConfigMigrator migrator = new ConfigMigrator(mockPlugin);
            boolean result = migrator.migrateIfNeeded();

            assertFalse(result);
            verify(mockConfig, never()).set(eq("message.text"), anyString());
            verify(mockConfig).set("config-version", 3);
            verify(mockPlugin).saveConfig();
        }
    }

    @Nested
    @DisplayName("V2 to V3 Migration Tests")
    class V2ToV3MigrationTests {

        @Test
        @DisplayName("Should add update-checker settings when not present")
        void shouldAddUpdateCheckerSettings() {
            when(mockConfig.getInt("config-version", 1)).thenReturn(2);
            when(mockConfig.contains("update-checker")).thenReturn(false);

            ConfigMigrator migrator = new ConfigMigrator(mockPlugin);
            boolean result = migrator.migrateIfNeeded();

            assertTrue(result);
            verify(mockConfig).set("update-checker.enabled", true);
            verify(mockConfig).set("update-checker.check-interval", 24);
            verify(mockConfig).set("update-checker.notify-on-join", true);
            verify(mockConfig).set(eq("update-checker.message"), anyString());
            verify(mockConfig).set("config-version", 3);
            verify(mockPlugin).saveConfig();
        }

        @Test
        @DisplayName("Should not add update-checker when already present")
        void shouldNotAddUpdateCheckerWhenPresent() {
            when(mockConfig.getInt("config-version", 1)).thenReturn(2);
            when(mockConfig.contains("update-checker")).thenReturn(true);

            ConfigMigrator migrator = new ConfigMigrator(mockPlugin);
            boolean result = migrator.migrateIfNeeded();

            assertFalse(result);
            verify(mockConfig, never()).set(eq("update-checker.enabled"), anyBoolean());
            verify(mockConfig).set("config-version", 3);
            verify(mockPlugin).saveConfig();
        }
    }

    @Nested
    @DisplayName("Full Migration Tests")
    class FullMigrationTests {

        @Test
        @DisplayName("Should perform full migration from v1 to v3")
        void shouldPerformFullMigration() {
            when(mockConfig.getInt("config-version", 1)).thenReturn(1);
            when(mockConfig.contains("message.context")).thenReturn(true);
            when(mockConfig.getString("message.context")).thenReturn("&cOld message");
            when(mockConfig.contains("update-checker")).thenReturn(false);

            ConfigMigrator migrator = new ConfigMigrator(mockPlugin);
            boolean result = migrator.migrateIfNeeded();

            assertTrue(result);
            // V1 to V2
            verify(mockConfig).set("message.text", "&cOld message");
            verify(mockConfig).set("message.context", null);
            // V2 to V3
            verify(mockConfig).set("update-checker.enabled", true);
            verify(mockConfig).set("config-version", 3);
            verify(mockPlugin).saveConfig();
            verify(mockLogger).info("Config migration complete!");
        }
    }
}

