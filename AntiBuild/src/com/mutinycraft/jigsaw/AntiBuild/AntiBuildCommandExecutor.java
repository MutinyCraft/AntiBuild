package com.mutinycraft.jigsaw.AntiBuild;

import org.bukkit.ChatColor;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.List;

public class AntiBuildCommandExecutor implements CommandExecutor {

    AntiBuild plugin;

    public AntiBuildCommandExecutor(AntiBuild pl) {
        plugin = pl;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label,
                             String[] args) {
        if (cmd.getName().equalsIgnoreCase("antibuild")) {
            if (args.length == 1) {
                if (args[0].equalsIgnoreCase("reload")) {
                    commandReload(sender);
                    return true;
                } else if (args[0].equalsIgnoreCase("list")) {
                    commandLockList(sender);
                    return true;
                } else {
                    sender.sendMessage(usageMessage());
                    return true;
                }
            } else if (args.length == 2) {
                if (args[0].equalsIgnoreCase("lock")) {
                    commandLock(sender, args);
                    return true;
                } else if (args[0].equalsIgnoreCase("unlock")) {
                    commandUnlock(sender, args);
                    return true;
                } else {
                    sender.sendMessage(usageMessage());
                    return true;
                }
            } else {
                if (sender instanceof Player) {
                    if (sender.hasPermission("antibuild.reload")) {
                        sender.sendMessage(usageMessage());
                        return true;
                    } else {
                        sender.sendMessage(noPermsMessage());
                        return true;
                    }
                } else {
                    sender.sendMessage(usageMessage());
                    return true;
                }
            }
        }
        return false;
    }

    private void commandReload(CommandSender sender) {
        if (sender instanceof Player) {
            if (sender.hasPermission("antibuild.reload")) {
                plugin.reloadConfig();
                sender.sendMessage(ChatColor.RED + "AntiBuild config reloaded!");
            } else {
                sender.sendMessage(noPermsMessage());
            }
        } else {
            plugin.reloadConfig();
            sender.sendMessage(ChatColor.RED + "AntiBuild config reloaded!");
        }
    }

    private void commandLockList(CommandSender sender) {
        if (sender instanceof Player) {
            if (sender.hasPermission("antibuild.lock")) {
                lockedWorldsMessage(sender);
            } else {
                sender.sendMessage(noPermsMessage());
            }
        } else {
            lockedWorldsMessage(sender);
        }
    }

    private void commandLock(CommandSender sender, String[] args) {
        if (sender.hasPermission("antibuild.lock")) {
            String worldName = getValidWorld(args[1]);
            if (!worldName.isEmpty() && plugin.isLockedWorld(worldName)) {
                sender.sendMessage(ChatColor.GREEN + args[1] + ChatColor.RED
                        + " has already been locked by AntiBuild!");
            } else if (!worldName.isEmpty()) {
                plugin.addLockedWorld(worldName);
                sender.sendMessage(ChatColor.GREEN + worldName + ChatColor.RED
                        + " has been locked by AntiBuild!");
            } else {
                sender.sendMessage(ChatColor.GREEN + args[1] + ChatColor.RED
                        + " is not a valid world!");
            }
        } else {
            sender.sendMessage(noPermsMessage());
        }
    }

    private void commandUnlock(CommandSender sender, String[] args) {
        if (sender.hasPermission("antibuild.lock")) {
            String worldName = getValidWorld(args[1]);
            if (!worldName.isEmpty()) {
                if (!plugin.isLockedWorld(worldName)) {
                    sender.sendMessage(ChatColor.GREEN + worldName
                            + ChatColor.RED + " is not currently locked!");
                } else {
                    plugin.removeLockedWorld(worldName);
                    sender.sendMessage(ChatColor.GREEN + worldName
                            + ChatColor.RED
                            + " has been unlocked by AntiBuild!");
                }
            } else {
                sender.sendMessage(ChatColor.GREEN + args[1]
                        + " is not a valid world!");
            }
        } else {
            sender.sendMessage(noPermsMessage());
        }
    }

    private String getValidWorld(String worldName) {
        List<World> worlds = plugin.getServer().getWorlds();
        String validName = worldName;

        for (World world : worlds) {
            validName = world.getName();
            if (validName.equalsIgnoreCase(worldName)) {
                return validName;
            }
        }
        return "";
    }

    private String usageMessage() {
        return (ChatColor.RED + "AntiBuild Command Usage: \n"
                + ChatColor.GREEN
                + "/antibuild reload \n/antibuild lock [world] \n"
                + "/antibuild unlock [world]\n" + "/antibuild list");
    }

    private String noPermsMessage() {
        return (ChatColor.RED + "You don't have permission to do this!");

    }

    private void lockedWorldsMessage(CommandSender sender) {
        List<String> worlds = plugin.getLockedWorlds();
        if (worlds != null) {
            sender.sendMessage(ChatColor.RED
                    + "Current worlds locked by Antibuild:");
            for (String world : worlds) {
                sender.sendMessage(ChatColor.GREEN + world);
            }
        } else {
            sender.sendMessage(ChatColor.RED
                    + "There are currently no worlds locked.");
        }

    }

}
