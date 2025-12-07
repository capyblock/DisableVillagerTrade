package me.dodo.disablevillagertrade.update;

import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.java.JavaPlugin;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
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
@DisplayName("UpdateChecker Tests")
class UpdateCheckerTest {

    @Mock
    private JavaPlugin mockPlugin;
    
    @Mock
    private PluginDescriptionFile mockDescription;
    
    @Mock
    private Logger mockLogger;

    private UpdateChecker updateChecker;

    @BeforeEach
    void setUp() {
        when(mockPlugin.getDescription()).thenReturn(mockDescription);
        when(mockPlugin.getLogger()).thenReturn(mockLogger);
        when(mockDescription.getVersion()).thenReturn("1.12.0");
        
        updateChecker = new UpdateChecker(mockPlugin);
    }

    @Nested
    @DisplayName("Version Comparison Tests")
    class VersionComparisonTests {

        @Test
        @DisplayName("Should detect newer major version")
        void shouldDetectNewerMajorVersion() {
            assertTrue(updateChecker.isNewerVersion("1.12.0", "2.0.0"));
        }

        @Test
        @DisplayName("Should detect newer minor version")
        void shouldDetectNewerMinorVersion() {
            assertTrue(updateChecker.isNewerVersion("1.12.0", "1.13.0"));
        }

        @Test
        @DisplayName("Should detect newer patch version")
        void shouldDetectNewerPatchVersion() {
            assertTrue(updateChecker.isNewerVersion("1.12.0", "1.12.1"));
        }

        @Test
        @DisplayName("Should not flag same version as update")
        void shouldNotFlagSameVersionAsUpdate() {
            assertFalse(updateChecker.isNewerVersion("1.12.0", "1.12.0"));
        }

        @Test
        @DisplayName("Should not flag older version as update")
        void shouldNotFlagOlderVersionAsUpdate() {
            assertFalse(updateChecker.isNewerVersion("1.12.0", "1.11.0"));
            assertFalse(updateChecker.isNewerVersion("2.0.0", "1.12.0"));
        }

        @Test
        @DisplayName("Should handle dev versions - release is newer than dev")
        void shouldHandleDevVersions() {
            assertTrue(updateChecker.isNewerVersion("1.12.0-dev.123", "1.12.0"));
        }

        @Test
        @DisplayName("Should not flag dev version as newer than release")
        void shouldNotFlagDevAsNewerThanRelease() {
            assertFalse(updateChecker.isNewerVersion("1.12.0", "1.12.0-dev.123"));
        }

        @Test
        @DisplayName("Should handle different version lengths")
        void shouldHandleDifferentVersionLengths() {
            assertTrue(updateChecker.isNewerVersion("1.12", "1.12.1"));
            assertFalse(updateChecker.isNewerVersion("1.12.1", "1.12"));
        }

        @Test
        @DisplayName("Should handle null versions gracefully")
        void shouldHandleNullVersions() {
            assertFalse(updateChecker.isNewerVersion(null, "1.12.0"));
            assertFalse(updateChecker.isNewerVersion("1.12.0", null));
            assertFalse(updateChecker.isNewerVersion(null, null));
        }

        @Test
        @DisplayName("Should compare versions with prefixes")
        void shouldCompareVersionsWithPrefixes() {
            assertTrue(updateChecker.isNewerVersion("1.12.0-dev.100", "1.13.0-dev.50"));
        }
    }

    @Nested
    @DisplayName("State Tests")
    class StateTests {

        @Test
        @DisplayName("Should initially have no update available")
        void shouldInitiallyHaveNoUpdateAvailable() {
            assertFalse(updateChecker.isUpdateAvailable());
        }

        @Test
        @DisplayName("Should initially have null latest version")
        void shouldInitiallyHaveNullLatestVersion() {
            assertNull(updateChecker.getLatestVersion());
        }

        @Test
        @DisplayName("Should return current version from plugin")
        void shouldReturnCurrentVersionFromPlugin() {
            assertEquals("1.12.0", updateChecker.getCurrentVersion());
        }
    }
}

