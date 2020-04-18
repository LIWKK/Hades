package me.apex.hades.utils;

import org.bukkit.block.Block;

public class BlockUtils {

    //Credits to IslandScout
    public static Block getBlockAsync(org.bukkit.Location loc)
    {
        if (loc.getWorld().isChunkLoaded(loc.getBlockX() >> 4, loc.getBlockZ() >> 4))
            return loc.getBlock();
        return null;
    }

}
