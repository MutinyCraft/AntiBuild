package com.mutinycraft.jigsaw.AntiBuild;

import com.mutinycraft.jigsaw.AntiBuild.Listeners.*;
import com.mutinycraft.jigsaw.AntiBuild.Util.ConfigHandler;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.logging.Logger;

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

public class AntiBuild extends JavaPlugin {

    private Logger logger;
    private ConfigHandler configHandler;

    @Override
    public void onEnable(){
        logger = this.getLogger();

        /* Load the config.yml from disk */
        logger.info("Loading the config.yml from disk...");
        configHandler = new ConfigHandler(this);
        logger.info("Successfully loaded config.yml");

        /* Load the world.yml from disk */

        /* Register commands */

        /* Register listeners */
        logger.info("Registering listeners...");
        getServer().getPluginManager().registerEvents(new BlockBreakEvent(this), this);
        getServer().getPluginManager().registerEvents(new BlockPlace(this), this);
        getServer().getPluginManager().registerEvents(new BucketEmpty(this), this);
        getServer().getPluginManager().registerEvents(new BucketFill(this), this);
        getServer().getPluginManager().registerEvents(new ChestAccess(this), this);
        getServer().getPluginManager().registerEvents(new EntityInteraction(this), this);
        getServer().getPluginManager().registerEvents(new HangingBreak(this), this);
        getServer().getPluginManager().registerEvents(new HangingPlace(this), this);
        getServer().getPluginManager().registerEvents(new InventoryAccess(this), this);
        getServer().getPluginManager().registerEvents(new ItemDrop(this), this);
        getServer().getPluginManager().registerEvents(new ItemPickup(this), this);
        logger.info("Successfully registered all listeners.");

    }

    public ConfigHandler getConfigHandler(){
        return configHandler;
    }

}
