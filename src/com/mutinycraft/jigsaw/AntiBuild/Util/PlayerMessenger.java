package com.mutinycraft.jigsaw.AntiBuild.Util;

import org.bukkit.entity.Player;

/**
 * Author: Jigsaw
 * *
 * Date: 1/17/14
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

public class PlayerMessenger {

    /**
     * Handle messages that are displayed to players being denied permission for
     * a specified action. This allows server admins to use an empty string in
     * the config if they wish for no message to be sent.
     *
     * @param message to send to player.
     * @param player  to send the message to.
     */
    public static void messageHandler(String message, Player player) {
        if (!message.isEmpty()) {
            player.sendMessage(message);
        }

    }
}
