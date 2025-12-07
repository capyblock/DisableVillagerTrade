package me.dodo.disablevillagertrade.bukkit.commands;

import me.dodo.disablevillagertrade.bukkit.DisableVillagerTrade;
import me.dodo.disablevillagertrade.bukkit.config.BukkitConfig;
import org.bukkit.Server;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
class DisableTradeCommandTest {

    @Mock
    private DisableVillagerTrade plugin;
    
    @Mock
    private BukkitConfig pluginConfig;
    
    @Mock
    private Command command;
    
    @Mock
    private CommandSender sender;
    
    @Mock
    private Player playerSender;
    
    @Mock
    private Server server;

    private DisableTradeCommand commandExecutor;

    @BeforeEach
    void setUp() {
        commandExecutor = new DisableTradeCommand(plugin);
        when(plugin.getPluginConfig()).thenReturn(pluginConfig);
        when(plugin.getServer()).thenReturn(server);
        
        // Default config values
        when(pluginConfig.isMessageEnabled()).thenReturn(true);
        when(pluginConfig.getMessage()).thenReturn("&cTest message");
        when(pluginConfig.getDisabledWorlds()).thenReturn(Collections.emptyList());
        when(pluginConfig.isUpdateCheckerEnabled()).thenReturn(true);
        when(pluginConfig.getUpdateCheckInterval()).thenReturn(24);
        when(pluginConfig.isNotifyOnJoin()).thenReturn(true);
    }

    @Nested
    @DisplayName("Permission Tests")
    class PermissionTests {
        
        @Test
        @DisplayName("Should deny access without admin permission")
        void shouldDenyWithoutPermission() {
            when(sender.hasPermission("disabletrade.admin")).thenReturn(false);
            
            boolean result = commandExecutor.onCommand(sender, command, "disabletrade", new String[]{});
            
            assertTrue(result);
            verify(sender).sendMessage(anyString());
        }
        
        @Test
        @DisplayName("Should allow access with admin permission")
        void shouldAllowWithPermission() {
            when(sender.hasPermission("disabletrade.admin")).thenReturn(true);
            
            boolean result = commandExecutor.onCommand(sender, command, "disabletrade", new String[]{});
            
            assertTrue(result);
        }
    }

    @Nested
    @DisplayName("Help Command Tests")
    class HelpCommandTests {
        
        @Test
        @DisplayName("Should show help when no args")
        void shouldShowHelpNoArgs() {
            when(sender.hasPermission("disabletrade.admin")).thenReturn(true);
            
            boolean result = commandExecutor.onCommand(sender, command, "disabletrade", new String[]{});
            
            assertTrue(result);
            verify(sender, atLeast(1)).sendMessage(anyString());
        }
        
        @Test
        @DisplayName("Should show help with help arg")
        void shouldShowHelpWithArg() {
            when(sender.hasPermission("disabletrade.admin")).thenReturn(true);
            
            boolean result = commandExecutor.onCommand(sender, command, "disabletrade", new String[]{"help"});
            
            assertTrue(result);
            verify(sender, atLeast(1)).sendMessage(anyString());
        }
    }

    @Nested
    @DisplayName("Reload Command Tests")
    class ReloadCommandTests {
        
        @Test
        @DisplayName("Should reload config")
        void shouldReloadConfig() {
            when(sender.hasPermission("disabletrade.admin")).thenReturn(true);
            
            boolean result = commandExecutor.onCommand(sender, command, "disabletrade", new String[]{"reload"});
            
            assertTrue(result);
            verify(plugin).reloadPluginConfig();
            verify(sender).sendMessage(anyString());
        }
    }

    @Nested
    @DisplayName("Status Command Tests")
    class StatusCommandTests {
        
        @Test
        @DisplayName("Should show status")
        void shouldShowStatus() {
            when(sender.hasPermission("disabletrade.admin")).thenReturn(true);
            
            boolean result = commandExecutor.onCommand(sender, command, "disabletrade", new String[]{"status"});
            
            assertTrue(result);
            verify(sender, atLeast(4)).sendMessage(anyString());
        }
        
        @Test
        @DisplayName("Should show update checker details when enabled")
        void shouldShowUpdateCheckerDetails() {
            when(sender.hasPermission("disabletrade.admin")).thenReturn(true);
            when(pluginConfig.isUpdateCheckerEnabled()).thenReturn(true);
            
            boolean result = commandExecutor.onCommand(sender, command, "disabletrade", new String[]{"status"});
            
            assertTrue(result);
            verify(pluginConfig).getUpdateCheckInterval();
            verify(pluginConfig).isNotifyOnJoin();
        }
    }

    @Nested
    @DisplayName("Toggle Command Tests")
    class ToggleCommandTests {
        
        @Test
        @DisplayName("Should require player name from console")
        void shouldRequirePlayerFromConsole() {
            when(sender.hasPermission("disabletrade.admin")).thenReturn(true);
            
            boolean result = commandExecutor.onCommand(sender, command, "disabletrade", new String[]{"toggle"});
            
            assertTrue(result);
            verify(sender).sendMessage(contains("Console must specify a player"));
        }
        
        @Test
        @DisplayName("Should check self when no player specified")
        void shouldCheckSelfWhenNoPlayer() {
            when(playerSender.hasPermission("disabletrade.admin")).thenReturn(true);
            when(playerSender.hasPermission("disabletrade.bypass")).thenReturn(false);
            
            boolean result = commandExecutor.onCommand(playerSender, command, "disabletrade", new String[]{"toggle"});
            
            assertTrue(result);
            verify(playerSender).hasPermission("disabletrade.bypass");
        }
        
        @Test
        @DisplayName("Should report player not found")
        void shouldReportPlayerNotFound() {
            when(sender.hasPermission("disabletrade.admin")).thenReturn(true);
            when(server.getPlayer("nonexistent")).thenReturn(null);
            
            boolean result = commandExecutor.onCommand(sender, command, "disabletrade", new String[]{"toggle", "nonexistent"});
            
            assertTrue(result);
            verify(sender).sendMessage(contains("Player not found"));
        }
        
        @Test
        @DisplayName("Should check specified player bypass status")
        void shouldCheckSpecifiedPlayer() {
            Player targetPlayer = mock(Player.class);
            when(sender.hasPermission("disabletrade.admin")).thenReturn(true);
            when(server.getPlayer("testplayer")).thenReturn(targetPlayer);
            when(targetPlayer.hasPermission("disabletrade.bypass")).thenReturn(true);
            when(targetPlayer.getName()).thenReturn("testplayer");
            
            boolean result = commandExecutor.onCommand(sender, command, "disabletrade", new String[]{"toggle", "testplayer"});
            
            assertTrue(result);
            verify(targetPlayer).hasPermission("disabletrade.bypass");
        }
    }

    @Nested
    @DisplayName("Unknown Command Tests")
    class UnknownCommandTests {
        
        @Test
        @DisplayName("Should handle unknown command")
        void shouldHandleUnknownCommand() {
            when(sender.hasPermission("disabletrade.admin")).thenReturn(true);
            
            boolean result = commandExecutor.onCommand(sender, command, "disabletrade", new String[]{"unknown"});
            
            assertTrue(result);
            verify(sender).sendMessage(contains("Unknown command"));
        }
    }

    @Nested
    @DisplayName("Tab Complete Tests")
    class TabCompleteTests {
        
        @Test
        @DisplayName("Should return empty list without permission")
        void shouldReturnEmptyWithoutPermission() {
            when(sender.hasPermission("disabletrade.admin")).thenReturn(false);
            
            List<String> completions = commandExecutor.onTabComplete(sender, command, "disabletrade", new String[]{""});
            
            assertTrue(completions.isEmpty());
        }
        
        @Test
        @DisplayName("Should return subcommands for first arg")
        void shouldReturnSubcommands() {
            when(sender.hasPermission("disabletrade.admin")).thenReturn(true);
            
            List<String> completions = commandExecutor.onTabComplete(sender, command, "disabletrade", new String[]{""});
            
            assertEquals(4, completions.size());
            assertTrue(completions.contains("reload"));
            assertTrue(completions.contains("status"));
            assertTrue(completions.contains("toggle"));
            assertTrue(completions.contains("help"));
        }
        
        @Test
        @DisplayName("Should filter subcommands")
        void shouldFilterSubcommands() {
            when(sender.hasPermission("disabletrade.admin")).thenReturn(true);
            
            List<String> completions = commandExecutor.onTabComplete(sender, command, "disabletrade", new String[]{"re"});
            
            assertEquals(1, completions.size());
            assertTrue(completions.contains("reload"));
        }
        
        @Test
        @DisplayName("Should return player names for toggle")
        void shouldReturnPlayerNamesForToggle() {
            when(sender.hasPermission("disabletrade.admin")).thenReturn(true);
            
            Player player1 = mock(Player.class);
            when(player1.getName()).thenReturn("Player1");
            Player player2 = mock(Player.class);
            when(player2.getName()).thenReturn("Player2");
            
            List<Player> players = new ArrayList<>();
            players.add(player1);
            players.add(player2);
            when(server.getOnlinePlayers()).thenReturn((java.util.Collection) players);
            
            List<String> completions = commandExecutor.onTabComplete(sender, command, "disabletrade", new String[]{"toggle", ""});
            
            assertEquals(2, completions.size());
            assertTrue(completions.contains("Player1"));
            assertTrue(completions.contains("Player2"));
        }
        
        @Test
        @DisplayName("Should return empty list for other args")
        void shouldReturnEmptyForOtherArgs() {
            when(sender.hasPermission("disabletrade.admin")).thenReturn(true);
            
            List<String> completions = commandExecutor.onTabComplete(sender, command, "disabletrade", new String[]{"reload", "extra"});
            
            assertTrue(completions.isEmpty());
        }
    }
    
    // Helper method for verifying message contains text
    private static String contains(String substring) {
        return argThat(s -> s != null && s.contains(substring));
    }
}

