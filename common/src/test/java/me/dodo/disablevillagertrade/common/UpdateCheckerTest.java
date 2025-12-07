package me.dodo.disablevillagertrade.common;

import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("UpdateChecker Tests")
class UpdateCheckerTest {

    private UpdateChecker updateChecker;

    @BeforeEach
    void setUp() {
        updateChecker = new UpdateChecker("1.12.0", "TestAgent/1.0", msg -> {});
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
        @DisplayName("Should return current version")
        void shouldReturnCurrentVersion() {
            assertEquals("1.12.0", updateChecker.getCurrentVersion());
        }
    }

    @Nested
    @DisplayName("Version Parsing Tests")
    class VersionParsingTests {

        @Test
        @DisplayName("Should parse version from JSON response")
        void shouldParseVersionFromJson() {
            String json = "[{\"version_number\": \"1.13.0\", \"other\": \"field\"}]";
            assertEquals("1.13.0", updateChecker.parseVersion(json));
        }

        @Test
        @DisplayName("Should parse version with spaces in JSON")
        void shouldParseVersionWithSpaces() {
            String json = "{\"version_number\"  :  \"2.0.0\"}";
            assertEquals("2.0.0", updateChecker.parseVersion(json));
        }

        @Test
        @DisplayName("Should return null for invalid JSON")
        void shouldReturnNullForInvalidJson() {
            String json = "{\"invalid\": \"json\"}";
            assertNull(updateChecker.parseVersion(json));
        }

        @Test
        @DisplayName("Should return null for empty string")
        void shouldReturnNullForEmptyString() {
            assertNull(updateChecker.parseVersion(""));
        }

        @Test
        @DisplayName("Should parse dev version")
        void shouldParseDevVersion() {
            String json = "[{\"version_number\": \"1.14.0-dev.123\"}]";
            assertEquals("1.14.0-dev.123", updateChecker.parseVersion(json));
        }
    }

    @Nested
    @DisplayName("Version Comparison Edge Cases")
    class VersionComparisonEdgeCases {

        @Test
        @DisplayName("Should handle version with letters")
        void shouldHandleVersionWithLetters() {
            // parseVersionPart strips non-numeric chars
            assertTrue(updateChecker.isNewerVersion("1.0.0", "1.0.1a"));
        }

        @Test
        @DisplayName("Should handle empty version parts")
        void shouldHandleEmptyVersionParts() {
            // Empty string becomes version 0 due to regex stripping non-digits
            assertTrue(updateChecker.isNewerVersion("", "1.0.0"));
            assertFalse(updateChecker.isNewerVersion("1.0.0", ""));
        }

        @Test
        @DisplayName("Should handle single digit versions")
        void shouldHandleSingleDigitVersions() {
            assertTrue(updateChecker.isNewerVersion("1", "2"));
            assertFalse(updateChecker.isNewerVersion("2", "1"));
        }

        @Test
        @DisplayName("Should handle four-part versions")
        void shouldHandleFourPartVersions() {
            assertTrue(updateChecker.isNewerVersion("1.0.0.0", "1.0.0.1"));
            assertFalse(updateChecker.isNewerVersion("1.0.0.1", "1.0.0.0"));
        }

        @Test
        @DisplayName("Should handle version comparison where current has more parts")
        void shouldHandleCurrentWithMoreParts() {
            assertFalse(updateChecker.isNewerVersion("1.0.0.1", "1.0.0"));
        }

        @Test
        @DisplayName("Should handle both versions as dev")
        void shouldHandleBothAsDev() {
            assertFalse(updateChecker.isNewerVersion("1.0.0-dev.1", "1.0.0-dev.2"));
        }
    }
}

