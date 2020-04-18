package me.apex.hades.utils.reflection.reflect;

import me.apex.hades.utils.BlockUtils;
import org.bukkit.World;
import org.bukkit.block.Block;

public class BlockPosition {

    public static final BlockPosition ZERO = new BlockPosition(0, 0, 0);

    private int x;
    private int y;
    private int z;

    public BlockPosition(int x, int y, int z)
    {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public int getX()
    {
        return x;
    }

    public int getY()
    {
        return y;
    }

    public int getZ()
    {
        return z;
    }

    public Block getBlock(World world)
    {
        return BlockUtils.getBlockAsync(new org.bukkit.Location(world, x, y, z));
    }

}
