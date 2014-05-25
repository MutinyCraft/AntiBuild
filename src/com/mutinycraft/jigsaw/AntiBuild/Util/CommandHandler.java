package com.mutinycraft.jigsaw.AntiBuild.Util;

import com.mutinycraft.jigsaw.AntiBuild.AntiBuild;
import com.mutinycraft.jigsaw.AntiBuild.Commands.*;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

/**
 * Author: Jigsaw
 * *
 * Date: 2/1/14
 * *
 * Copyright 2014 MutinyCraft
 * *
 * This file is part of AntiBuild.
 * *
 * AntiBuild is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * *
 * AntiBuild is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * *
 * You should have received a copy of the GNU General Public License
 * along with AntiBuild.  If not, see <http://www.gnu.org/licenses/>.
 */

public class CommandHandler implements CommandExecutor {

    private AntiBuild plugin;
    private Lock lockCommand;
    private List listCommand;
    private Reload reloadCommand;
    private Unlock unlockCommand;
    private Help helpCommand;

    public CommandHandler(AntiBuild p) {
        plugin = p;
        lockCommand = new Lock(p);
        listCommand = new List(p);
        reloadCommand = new Reload(p);
        unlockCommand = new Unlock(p);
        helpCommand = new Help(p);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (cmd.getName().equalsIgnoreCase("antibuild") && args.length > 1) {
            String command = args[0].toLowerCase();
            switch (command) {
                case "reload":
                    reloadCommand.execute();
                    break;
                case "list":
                    listCommand.execute();
                    break;
                case "lock":
                    lockCommand.execute();
                    break;
                case "unlock":
                    unlockCommand.execute();
                    break;
                default:
                    helpCommand.execute(sender);
                    break;
            }
        }
        return false;
    }
}
