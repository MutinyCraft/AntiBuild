package com.mutinycraft.jigsaw.AntiBuild.Listeners;

import com.mutinycraft.jigsaw.AntiBuild.AntiBuild;
import com.mutinycraft.jigsaw.AntiBuild.Util.PlayerMessenger;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryOpenEvent;

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

public class InventoryAccess implements Listener {

    private AntiBuild plugin;

    public InventoryAccess(AntiBuild p) {
        plugin = p;
    }

    /**
     * Checks if player has proper permission to interact with inventory types.
     *
     * @param event
     */
    @EventHandler(priority = EventPriority.LOW)
    public void NoInventoryAccess(InventoryOpenEvent event) {
        Player player = null;

        if (event.getPlayer() instanceof Player) {
            player = (Player) event.getPlayer();
        }
        if (player != null && !player.hasPermission("antibuild.bypass") && !player.hasPermission("antibuild" +
                ".interact")) {
            switch (event.getInventory().getType()) {
                case ANVIL:
                    if (!player.hasPermission("antibuild.anvil")) {
                        event.setCancelled(true);
                        PlayerMessenger.messageHandler(plugin.getConfigHandler().getNoInteractMessage(), player);
                    }
                    break;
                case BEACON:
                    if (!player.hasPermission("antibuild.beacon")) {
                        event.setCancelled(true);
                        PlayerMessenger.messageHandler(plugin.getConfigHandler().getNoInteractMessage(), player);
                    }
                    break;
                case BREWING:
                    if (!player.hasPermission("antibuild.brewing")) {
                        event.setCancelled(true);
                        PlayerMessenger.messageHandler(plugin.getConfigHandler().getNoInteractMessage(), player);
                    }
                    break;
                case CHEST:
                    if (!player.hasPermission("antibuild.chest")) {
                        event.setCancelled(true);
                        PlayerMessenger.messageHandler(plugin.getConfigHandler().getNoInteractMessage(), player);
                    }
                    break;
                case CRAFTING:
                    if (!player.hasPermission("antibuild.crafting")) {
                        event.setCancelled(true);
                        PlayerMessenger.messageHandler(plugin.getConfigHandler().getNoInteractMessage(), player);
                    }
                    break;
                case DISPENSER:
                    if (!player.hasPermission("antibuild.dispenser")) {
                        event.setCancelled(true);
                        PlayerMessenger.messageHandler(plugin.getConfigHandler().getNoInteractMessage(), player);
                    }
                    break;
                case ENCHANTING:
                    if (!player.hasPermission("antibuild.enchanting")) {
                        event.setCancelled(true);
                        PlayerMessenger.messageHandler(plugin.getConfigHandler().getNoInteractMessage(), player);
                    }
                    break;
                case ENDER_CHEST:
                    if (!player.hasPermission("antibuild.enderchest")) {
                        event.setCancelled(true);
                        PlayerMessenger.messageHandler(plugin.getConfigHandler().getNoInteractMessage(), player);
                    }
                    break;
                case HOPPER:
                    if (!player.hasPermission("antibuild.hopper")) {
                        event.setCancelled(true);
                        PlayerMessenger.messageHandler(plugin.getConfigHandler().getNoInteractMessage(), player);
                    }
                    break;
                case FURNACE:
                    if (!player.hasPermission("antibuild.furnace")) {
                        event.setCancelled(true);
                        PlayerMessenger.messageHandler(plugin.getConfigHandler().getNoInteractMessage(), player);
                    }
                    break;
                case WORKBENCH:
                    if (!player.hasPermission("antibuild.workbench")) {
                        event.setCancelled(true);
                        PlayerMessenger.messageHandler(plugin.getConfigHandler().getNoInteractMessage(), player);
                    }
                    break;
                default:
                    break;
            }
        }
    }
}
