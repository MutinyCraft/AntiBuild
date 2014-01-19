package com.mutinycraft.jigsaw.AntiBuild.Util;

import com.mutinycraft.jigsaw.AntiBuild.AntiBuild;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

/**
 * Author: Jigsaw
 * *
 * Date: 1/19/14
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

public class WorldHandler {

    private FileConfiguration worldData;
    private File worldDataFile;
    private AntiBuild plugin;

    public WorldHandler(AntiBuild p){
        plugin = p;
        reloadWorldData();
    }

    /**
     *  Loads the locked worlds file from disk.
     */
    private void reloadWorldData() {
        if (worldDataFile == null) {
            worldDataFile = new File(plugin.getDataFolder(), "lockedWorlds.yml");
        }
        worldData = YamlConfiguration.loadConfiguration(worldDataFile);

        InputStream defConfigStream = plugin.getResource("lockedWorlds.yml");
        if (defConfigStream != null) {
            YamlConfiguration defConfig = YamlConfiguration.loadConfiguration(defConfigStream);
            worldData.setDefaults(defConfig);
        }
    }

    /**
     * Provides access to the locked worlds configuration file.
     * @return FileConfiguration representation of the locked worlds.
     */
    public FileConfiguration getCustomConfig() {
        if (worldData == null) {
            reloadWorldData();
        }
        return worldData;
    }

    /**
     * Saves the locked world data to disk.
     */
    public void saveWorldData() {
        if (worldData == null || worldDataFile == null) {
            return;
        }
        try {
            getCustomConfig().save(worldDataFile);
        } catch (IOException ex) {
            plugin.getLogger().info( "Could not save data to " + worldDataFile);
        }
    }

    /**
     * Check to see if a world is currently locked.
     * @param worldName to check lock status of.
     * @return true if locked, false otherwise.
     */
    public boolean isLockedWorld(String worldName){
        return worldData.getStringList("Locked-Worlds").contains(worldName);
    }

    /**
     * Adds a locked world to the locked world data and saves it to disk.
     * @param worldName to add to the locked world list.
     */
    public void addLockedWorld(String worldName){
        if(!isLockedWorld(worldName)){
            worldData.getStringList("Locked-Worlds").add(worldName);
            saveWorldData();
        }
    }

    /**
     * Removes a locked world from the locked world data and saves it to disk.
     * @param worldName to remove from teh locked world list.
     */
    public void removeLockedWorld(String worldName){
        if(isLockedWorld(worldName)){
            worldData.getStringList("Locked-Worlds").remove(worldName);
            saveWorldData();
        }
    }

}
