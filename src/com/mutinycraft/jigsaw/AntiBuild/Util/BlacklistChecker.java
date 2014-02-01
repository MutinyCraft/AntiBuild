package com.mutinycraft.jigsaw.AntiBuild.Util;

import com.mutinycraft.jigsaw.AntiBuild.AntiBuild;

/**
 * Author: Jigsaw
 * *
 * Date: 1/18/14
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

public class BlacklistChecker {

    /**
     * Static method which will check to see if the provided block is currently blacklisted.
     *
     * @param plugin which is accessing the method.
     * @param id     of block to check for in blacklist.
     * @return true if block is blacklisted, false otherwise.
     */
    public static boolean isBlockBlackListed(AntiBuild plugin, int id) {
        return plugin.getConfigHandler().getBlackListedBlocks().contains(id);
    }
}
