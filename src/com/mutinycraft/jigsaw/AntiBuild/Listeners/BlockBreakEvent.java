package com.mutinycraft.jigsaw.AntiBuild.Listeners;

import com.mutinycraft.jigsaw.AntiBuild.AntiBuild;
import org.bukkit.event.Listener;

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

public class BlockBreakEvent implements Listener {

    private AntiBuild plugin;

    public BlockBreakEvent(AntiBuild p) {
        plugin = p;
    }
}
