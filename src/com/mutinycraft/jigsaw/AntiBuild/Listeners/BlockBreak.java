package com.mutinycraft.jigsaw.AntiBuild.Listeners;

import com.mutinycraft.jigsaw.AntiBuild.AntiBuild;
import com.mutinycraft.jigsaw.AntiBuild.Util.BlacklistChecker;
import com.mutinycraft.jigsaw.AntiBuild.Util.PlayerMessenger;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;

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

public class BlockBreak implements Listener {

    private AntiBuild plugin;

    public BlockBreak(AntiBuild p) {
        plugin = p;
    }

    /**
     * Checks if a player has permission to break blocks.
     *
     * @param event that triggered listener.
     */
    @EventHandler(priority = EventPriority.LOW)
    public void NoBreak(BlockBreakEvent event) {

        Player player = event.getPlayer();

        if (!player.hasPermission("antibuild.bypass")) {
            // Break check
            if (!player.hasPermission("antibuild.break")) {
                event.setCancelled(true);
            }

            // Block Type Check
            if (plugin.getConfigHandler().isPerBlockPermissionEnabled()) {
                Block block = event.getBlock();
                if (player.hasPermission("antibuild.break." + block.getTypeId()) || player.hasPermission("antibuild" +
                        ".break." + block.getTypeId() + "." + block.getData())) {
                    event.setCancelled(false);
                }
            }

            if (event.isCancelled()) {
                PlayerMessenger.messageHandler(plugin.getConfigHandler().getNoBreakMessage(), player);
            }
        }

        // Blacklist check
        if (!event.isCancelled() && plugin.getConfigHandler().isBlackListEnabled()) {
            int id = event.getBlock().getTypeId();
            if (!player.hasPermission("antibuild.blacklist")
                    && BlacklistChecker.isBlockBlackListed(plugin, id)) {
                String perBlockPermission = "antibuild.blacklist."
                        + String.valueOf(id);
                if (!player.hasPermission(perBlockPermission)) {
                    event.setCancelled(true);
                    PlayerMessenger.messageHandler(plugin.getConfigHandler().getBlacklistMessage(), player);
                }
            }
        }
    }

//        // World lock check
//        if (!event.isCancelled() && plugin.isUsingLock()) {
//            if (plugin.isLockedWorld(player.getWorld().getName())
//                    && !player.hasPermission("antibuild.lock.bypass") && !player.hasPermission("antibuild.lock
// .bypass" +
//                    "." + player.getWorld().getName())) {
//                event.setCancelled(true);
//                messageHandler(plugin.getLockedWorldMessage(), player);
//            }
//        }
//    }
}
