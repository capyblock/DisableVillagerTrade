package me.dodo.disablevillagertrade.update;

import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URI;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.CompletableFuture;
import java.util.logging.Level;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Checks for plugin updates via the Modrinth API.
 */
public class UpdateChecker {
    
    private static final String MODRINTH_API_URL = 
        "https://api.modrinth.com/v2/project/disable-villager-trade/version?featured=true&limit=1";
    private static final Pattern VERSION_PATTERN = Pattern.compile("\"version_number\"\\s*:\\s*\"([^\"]+)\"");
    
    private final JavaPlugin plugin;
    private String latestVersion;
    private boolean updateAvailable;
    private int taskId = -1;

    /**
     * Creates a new update checker.
     * @param plugin the plugin instance
     */
    @SuppressFBWarnings(value = "EI_EXPOSE_REP2", 
        justification = "Plugin reference is intentionally stored for Bukkit API access")
    public UpdateChecker(JavaPlugin plugin) {
        this.plugin = plugin;
        this.latestVersion = null;
        this.updateAvailable = false;
    }

    /**
     * Starts the periodic update check.
     * @param intervalHours how often to check for updates (in hours)
     */
    public void startChecking(int intervalHours) {
        // Check immediately on startup
        checkForUpdates();
        
        // Schedule periodic checks
        long intervalTicks = intervalHours * 60L * 60L * 20L; // hours to ticks
        taskId = Bukkit.getScheduler().runTaskTimerAsynchronously(plugin, this::checkForUpdates, 
            intervalTicks, intervalTicks).getTaskId();
    }

    /**
     * Stops the periodic update check.
     */
    public void stopChecking() {
        if (taskId != -1) {
            Bukkit.getScheduler().cancelTask(taskId);
            taskId = -1;
        }
    }

    /**
     * Checks for updates asynchronously.
     */
    public void checkForUpdates() {
        CompletableFuture.runAsync(() -> {
            try {
                String response = fetchLatestVersion();
                if (response != null) {
                    String fetchedVersion = parseVersion(response);
                    if (fetchedVersion != null) {
                        latestVersion = fetchedVersion;
                        String currentVersion = plugin.getDescription().getVersion();
                        updateAvailable = isNewerVersion(currentVersion, latestVersion);
                        
                        if (updateAvailable) {
                            plugin.getLogger().info("A new version is available: " + latestVersion 
                                + " (current: " + currentVersion + ")");
                        }
                    }
                }
            } catch (Exception e) {
                plugin.getLogger().log(Level.WARNING, "Failed to check for updates: " + e.getMessage());
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
            connection.setRequestProperty("User-Agent", "DisableVillagerTrade/" + plugin.getDescription().getVersion());
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
            plugin.getLogger().log(Level.FINE, "Error fetching update info", e);
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
    String parseVersion(String response) {
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
    boolean isNewerVersion(String current, String latest) {
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

    /**
     * Parses a version part to an integer.
     * @param part the version part string
     * @return the integer value, or 0 if invalid
     */
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
     * Gets the current plugin version.
     * @return the current version
     */
    public String getCurrentVersion() {
        return plugin.getDescription().getVersion();
    }
}

