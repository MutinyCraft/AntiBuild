package com.mutinycraft.jigsaw.AntiBuild.Listeners;

import com.mutinycraft.jigsaw.AntiBuild.AntiBuild;
import com.mutinycraft.jigsaw.AntiBuild.Util.PlayerMessenger;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerDropItemEvent;

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

public class ItemDrop implements Listener {

    private AntiBuild plugin;

    public ItemDrop(AntiBuild p) {
        plugin = p;
    }

    /**
     * Checks if player has proper permission to drop items.
     *
     * @param event that triggers listener.
     */
    @EventHandler(priority = EventPriority.LOW)
    private void DropItem(PlayerDropItemEvent event) {
        Player player = event.getPlayer();

        // Drop item check.
        if (player != null && !player.hasPermission("antibuild.bypass")) {
            if (!player.hasPermission("antibuild.dropitems")) {
                event.setCancelled(true);
                PlayerMessenger.messageHandler(plugin.getConfigHandler().getNoDropItemsMessage(), player);
            }
        }

//        // World Check
//        if (!event.isCancelled() && plugin.isUsingLock()) {
//            if (plugin.isLockedWorld(player.getWorld().getName())
//                    && !player.hasPermission("antibuild.lock.bypass") && !player.hasPermission("antibuild.lock
// .bypass" +
//                    "." + player.getWorld().getName())) {
//                event.setCancelled(true);
//                messageHandler(plugin.getLockedWorldMessage(), player);
//            }
//        }

    }
}
