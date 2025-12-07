package me.dodo.disablevillagertrade.common;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URI;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.CompletableFuture;
import java.util.function.Consumer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Platform-agnostic update checker that fetches version info from Modrinth API.
 * Platforms should wrap this and handle the async scheduling.
 */
public class UpdateChecker {
    
    private static final String MODRINTH_API_URL = 
        "https://api.modrinth.com/v2/project/disable-villager-trade/version?featured=true&limit=1";
    private static final Pattern VERSION_PATTERN = Pattern.compile("\"version_number\"\\s*:\\s*\"([^\"]+)\"");
    
    private final String currentVersion;
    private final String userAgent;
    private final Consumer<String> logger;
    
    private String latestVersion;
    private boolean updateAvailable;
    
    /**
     * Creates a new update checker.
     * @param currentVersion the current mod/plugin version
     * @param userAgent the user agent string for API requests
     * @param logger consumer for log messages
     */
    public UpdateChecker(String currentVersion, String userAgent, Consumer<String> logger) {
        this.currentVersion = currentVersion;
        this.userAgent = userAgent;
        this.logger = logger;
        this.latestVersion = null;
        this.updateAvailable = false;
    }
    
    /**
     * Checks for updates asynchronously.
     * @return CompletableFuture that completes when check is done
     */
    public CompletableFuture<Void> checkForUpdates() {
        return CompletableFuture.runAsync(() -> {
            try {
                String response = fetchLatestVersion();
                if (response != null) {
                    String fetchedVersion = parseVersion(response);
                    if (fetchedVersion != null) {
                        latestVersion = fetchedVersion;
                        updateAvailable = isNewerVersion(currentVersion, latestVersion);
                        
                        if (updateAvailable) {
                            logger.accept("A new version is available: " + latestVersion 
                                + " (current: " + currentVersion + ")");
                        }
                    }
                }
            } catch (Exception e) {
                logger.accept("Failed to check for updates: " + e.getMessage());
            }
        });
    }
    
    /**
     * Fetches the latest version from the Modrinth API.
     * @return the API response as a string, or null if failed
     */
    private String fetchLatestVersion() {
        HttpURLConnection connection = null;
        try {
            URI uri = URI.create(MODRINTH_API_URL);
            connection = (HttpURLConnection) uri.toURL().openConnection();
            connection.setRequestMethod("GET");
            connection.setRequestProperty("User-Agent", userAgent);
            connection.setConnectTimeout(5000);
            connection.setReadTimeout(5000);
            
            if (connection.getResponseCode() == HttpURLConnection.HTTP_OK) {
                try (BufferedReader reader = new BufferedReader(
                        new InputStreamReader(connection.getInputStream(), StandardCharsets.UTF_8))) {
                    StringBuilder response = new StringBuilder();
                    String line;
                    while ((line = reader.readLine()) != null) {
                        response.append(line);
                    }
                    return response.toString();
                }
            }
        } catch (Exception e) {
            // Silently fail - update check is not critical
        } finally {
            if (connection != null) {
                connection.disconnect();
            }
        }
        return null;
    }
    
    /**
     * Parses the version from the Modrinth API response.
     * @param response the API response JSON
     * @return the version string, or null if not found
     */
    public String parseVersion(String response) {
        Matcher matcher = VERSION_PATTERN.matcher(response);
        if (matcher.find()) {
            return matcher.group(1);
        }
        return null;
    }
    
    /**
     * Compares two semantic versions.
     * @param current the current version
     * @param latest the latest version
     * @return true if latest is newer than current
     */
    public boolean isNewerVersion(String current, String latest) {
        if (current == null || latest == null) {
            return false;
        }
        
        // Remove any suffixes like -dev.123 for comparison
        String cleanCurrent = current.replaceAll("-.*", "");
        String cleanLatest = latest.replaceAll("-.*", "");
        
        String[] currentParts = cleanCurrent.split("\\.");
        String[] latestParts = cleanLatest.split("\\.");
        
        int maxLength = Math.max(currentParts.length, latestParts.length);
        
        for (int i = 0; i < maxLength; i++) {
            int currentPart = i < currentParts.length ? parseVersionPart(currentParts[i]) : 0;
            int latestPart = i < latestParts.length ? parseVersionPart(latestParts[i]) : 0;
            
            if (latestPart > currentPart) {
                return true;
            } else if (latestPart < currentPart) {
                return false;
            }
        }
        
        // Versions are equal - check if current is a dev build
        return current.contains("-dev") && !latest.contains("-dev");
    }
    
    private int parseVersionPart(String part) {
        try {
            return Integer.parseInt(part.replaceAll("[^0-9]", ""));
        } catch (NumberFormatException e) {
            return 0;
        }
    }
    
    /**
     * Checks if an update is available.
     * @return true if a newer version is available
     */
    public boolean isUpdateAvailable() {
        return updateAvailable;
    }
    
    /**
     * Gets the latest version string.
     * @return the latest version, or null if not yet checked
     */
    public String getLatestVersion() {
        return latestVersion;
    }
    
    /**
     * Gets the current version.
     * @return the current version
     */
    public String getCurrentVersion() {
        return currentVersion;
    }
}

