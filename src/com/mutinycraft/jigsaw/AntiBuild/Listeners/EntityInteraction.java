package com.mutinycraft.jigsaw.AntiBuild.Listeners;

import com.mutinycraft.jigsaw.AntiBuild.AntiBuild;
import com.mutinycraft.jigsaw.AntiBuild.Util.BlacklistChecker;
import com.mutinycraft.jigsaw.AntiBuild.Util.PlayerMessenger;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;

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

public class EntityInteraction implements Listener {

    private AntiBuild plugin;

    public EntityInteraction(AntiBuild p) {
        plugin = p;
    }

    /**
     * Special check to see if an Entity (boat/minecart) is blacklisted since
     * the normal blacklist will not check entities.
     *
     * @param event that triggers listener.
     */
    @EventHandler(priority = EventPriority.LOW)
    private void EntityBlacklistCheck(PlayerInteractEvent event) {
        if (event.isCancelled()) {
            return;
        }

        Player player = event.getPlayer();
        int blockID = event.getPlayer().getItemInHand().getTypeId();

        if (plugin.getConfigHandler().isBlackListEnabled() && BlacklistChecker.isBlockBlackListed(plugin, blockID)) {
            if (!player.hasPermission("antibuild.blacklist") && !player.hasPermission("antibuild.blacklist." +
                    blockID)) {
                event.setCancelled(true);
                PlayerMessenger.messageHandler(plugin.getConfigHandler().getBlacklistMessage(), player);
            }
        }
    }
}
