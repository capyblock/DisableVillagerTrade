package me.dodo.disablevillagertrade.bukkit.commands;

import me.dodo.disablevillagertrade.bukkit.DisableVillagerTrade;
import me.dodo.disablevillagertrade.common.Constants;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Command executor for /disabletrade commands.
 */
public class DisableTradeCommand implements CommandExecutor, TabCompleter {

    private static final String PREFIX = ChatColor.GOLD + "[DisableVillagerTrade] " + ChatColor.RESET;
    
    private final DisableVillagerTrade plugin;

    public DisableTradeCommand(DisableVillagerTrade plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!sender.hasPermission(Constants.PERMISSION_ADMIN)) {
            sender.sendMessage(PREFIX + ChatColor.RED + "You don't have permission to use this command.");
            return true;
        }

        if (args.length == 0) {
            sendHelp(sender);
            return true;
        }

        String subCommand = args[0].toLowerCase();
        
        switch (subCommand) {
            case "reload":
                return handleReload(sender);
            case "status":
                return handleStatus(sender);
            case "toggle":
                return handleToggle(sender, args);
            case "help":
                sendHelp(sender);
                return true;
            default:
                sender.sendMessage(PREFIX + ChatColor.RED + "Unknown command. Use /disabletrade help");
                return true;
        }
    }

    private boolean handleReload(CommandSender sender) {
        plugin.reloadPluginConfig();
        sender.sendMessage(PREFIX + ChatColor.GREEN + "Configuration reloaded successfully!");
        return true;
    }

    private boolean handleStatus(CommandSender sender) {
        var config = plugin.getPluginConfig();
        
        sender.sendMessage(ChatColor.GOLD + "=== DisableVillagerTrade Status ===");
        sender.sendMessage(ChatColor.YELLOW + "Platform: " + ChatColor.WHITE + "Bukkit/Spigot/Paper");
        sender.sendMessage(ChatColor.YELLOW + "Message enabled: " + ChatColor.WHITE + config.isMessageEnabled());
        sender.sendMessage(ChatColor.YELLOW + "Message: " + ChatColor.WHITE + 
            ChatColor.translateAlternateColorCodes('&', config.getMessage()));
        sender.sendMessage(ChatColor.YELLOW + "Disabled worlds: " + ChatColor.WHITE + 
            String.join(", ", config.getDisabledWorlds()));
        sender.sendMessage(ChatColor.YELLOW + "Update checker: " + ChatColor.WHITE + config.isUpdateCheckerEnabled());
        
        if (config.isUpdateCheckerEnabled()) {
            sender.sendMessage(ChatColor.YELLOW + "Check interval: " + ChatColor.WHITE + 
                config.getUpdateCheckInterval() + " hours");
            sender.sendMessage(ChatColor.YELLOW + "Notify on join: " + ChatColor.WHITE + config.isNotifyOnJoin());
        }
        
        return true;
    }

    private boolean handleToggle(CommandSender sender, String[] args) {
        Player target;
        
        if (args.length >= 2) {
            // Toggle for specified player
            target = plugin.getServer().getPlayer(args[1]);
            if (target == null) {
                sender.sendMessage(PREFIX + ChatColor.RED + "Player not found: " + args[1]);
                return true;
            }
        } else {
            // Toggle for self
            if (!(sender instanceof Player)) {
                sender.sendMessage(PREFIX + ChatColor.RED + "Console must specify a player: /disabletrade toggle <player>");
                return true;
            }
            target = (Player) sender;
        }
        
        // Toggle bypass permission
        boolean hasBypass = target.hasPermission(Constants.PERMISSION_BYPASS);
        
        if (hasBypass) {
            sender.sendMessage(PREFIX + ChatColor.YELLOW + target.getName() + 
                " already has bypass permission. Remove it via your permissions plugin.");
        } else {
            sender.sendMessage(PREFIX + ChatColor.YELLOW + target.getName() + 
                " doesn't have bypass permission. Add it via your permissions plugin.");
        }
        
        sender.sendMessage(PREFIX + ChatColor.GRAY + "Tip: Use a permissions plugin (LuckPerms, etc.) to manage bypass.");
        
        return true;
    }

    private void sendHelp(CommandSender sender) {
        sender.sendMessage(ChatColor.GOLD + "=== DisableVillagerTrade Commands ===");
        sender.sendMessage(ChatColor.YELLOW + "/disabletrade reload " + ChatColor.GRAY + "- Reload configuration");
        sender.sendMessage(ChatColor.YELLOW + "/disabletrade status " + ChatColor.GRAY + "- Show plugin status");
        sender.sendMessage(ChatColor.YELLOW + "/disabletrade toggle [player] " + ChatColor.GRAY + "- Check bypass status");
        sender.sendMessage(ChatColor.YELLOW + "/disabletrade help " + ChatColor.GRAY + "- Show this help");
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        if (!sender.hasPermission(Constants.PERMISSION_ADMIN)) {
            return new ArrayList<>();
        }

        if (args.length == 1) {
            List<String> subCommands = Arrays.asList("reload", "status", "toggle", "help");
            return subCommands.stream()
                .filter(s -> s.toLowerCase().startsWith(args[0].toLowerCase()))
                .collect(Collectors.toList());
        }

        if (args.length == 2 && args[0].equalsIgnoreCase("toggle")) {
            return plugin.getServer().getOnlinePlayers().stream()
                .map(Player::getName)
                .filter(name -> name.toLowerCase().startsWith(args[1].toLowerCase()))
                .collect(Collectors.toList());
        }

        return new ArrayList<>();
    }
}

