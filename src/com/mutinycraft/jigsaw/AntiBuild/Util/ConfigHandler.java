package com.mutinycraft.jigsaw.AntiBuild.Util;

import com.mutinycraft.jigsaw.AntiBuild.AntiBuild;
import org.bukkit.ChatColor;

import java.util.List;

/**
 * Author: Jigsaw
 * *
 * Date: 1/1/14
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
 * along with .AntiBuild.  If not, see <http://www.gnu.org/licenses/>.
 */

public class ConfigHandler {

    private AntiBuild plugin;

    /**
     * Creates a ConfigHandler for use with the config.yml
     *
     * @param p plugin which is calling the constructor.
     */
    public ConfigHandler(AntiBuild p) {
        this.plugin = p;
    }

    /**
     * Saves the current config.yml to disk that is residing in memory.
     */
    public void saveToDisk() {
        this.plugin.saveConfig();
    }

    /**
     * Reloads the current config.yml from disk and overwrites the one residing in memory.
     */
    public void reloadFromDisk() {
        this.plugin.reloadConfig();
    }

    /**
     * Retrieves the message that is displayed when a player is denied building permission.
     *
     * @return message to display to player.
     */
    public String getNoBuildMessage() {
        return ChatColor.translateAlternateColorCodes('&', (plugin.getConfig().getString("Message-Build",
                "&cYou are not allowed to build!")));
    }

    /**
     * Retrieves the message that is displayed when a player is denied breaking permission.
     *
     * @return message to display to player.
     */
    public String getNoBreakMessage() {
        return ChatColor.translateAlternateColorCodes('&', (plugin.getConfig().getString("Message-Break", "&cYou are " +
                "not allowed to break blocks!")));
    }

    /**
     * Retrieves the message that is displayed when a player is denied bucket permission.
     *
     * @return message to display to player.
     */
    public String getNoBucketMessage() {
        return ChatColor.translateAlternateColorCodes('&', (plugin.getConfig().getString("Message-Bucket",
                "&cYou are not allowed to use buckets!")));
    }

    /**
     * Retrieves the message that is displayed when a player is denied chest access permission.
     *
     * @return message to display to player.
     */
    public String getNoChestAccessMessage() {
        return ChatColor.translateAlternateColorCodes('&', (plugin.getConfig().getString("Message-Chest",
                "&cYou are not allowed to access chests!")));
    }

    /**
     * Retrieves the message that is displayed when a player is denied interaction permission.
     *
     * @return message to display to player.
     */
    public String getNoInteractMessage() {
        return ChatColor.translateAlternateColorCodes('&', (plugin.getConfig().getString("Message-Interact",
                "&cYou are not allowed to interact with this!")));
    }

    /**
     * Retrieves the message that is displayed when a player is denied dropping item permission.
     *
     * @return message to display to player.
     */
    public String gtNoDropItemsMessage() {
        return ChatColor.translateAlternateColorCodes('&', (plugin.getConfig().getString("Message-Drop-Items",
                "&cYou are not allowed to drop items!")));
    }

    /**
     * Retrieves the message that is displayed when a player is denied by the blacklist.
     *
     * @return message to display to player.
     */
    public String getBlacklistMessage() {
        return ChatColor.translateAlternateColorCodes('&', (plugin.getConfig().getString("Message-Blacklist",
                "&cYou may not place or brehis type of block!")));
    }

    /**
     * Retrieves the message that is displayed when a player is trying to build in a locked world.
     *
     * @return message to display to player.
     */
    public String getLockedWorldMessage() {
        return ChatColor.translateAlternateColorCodes('&', (plugin.getConfig().getString("Message-Locked-World",
                "&cThis world is currently locked and cannot be built")));
    }

    /**
     * Notifies caller whether or not the feature for per-block permissions is enabled.
     *
     * @return true if enabled, false otherwise.
     */
    public boolean isPerBlockPermissionEnabled() {
        return plugin.getConfig().getBoolean("Per-Block-Permission", false);
    }

    /**
     * Notifies the caller whether the blacklist is enabled.
     *
     * @return true if enabled, false otherwise.
     */
    public boolean isBlackListEnabled() {
        return plugin.getConfig().getBoolean("Blacklist-On", false);
    }

    /**
     * Provides access to the list of blacklisted blocks as defined in the config.yml
     *
     * @return List of integers which represent item IDs.
     */
    public List<Integer> getBlackListedBlocks() {
        return plugin.getConfig().getIntegerList("Blacklisted-Block");
    }
}
