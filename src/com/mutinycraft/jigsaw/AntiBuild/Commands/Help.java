package com.mutinycraft.jigsaw.AntiBuild.Commands;

import com.mutinycraft.jigsaw.AntiBuild.AntiBuild;
import com.mutinycraft.jigsaw.AntiBuild.Util.Messages;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;

/**
 * Author: Jigsaw
 * *
 * Date: 5/24/2014
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

public class Help {

    private AntiBuild plugin;

    public Help(AntiBuild p) {
        plugin = p;
    }

    public boolean execute(CommandSender sender) {
        sender.sendMessage(Messages.HELP);
        return true;
    }
}
