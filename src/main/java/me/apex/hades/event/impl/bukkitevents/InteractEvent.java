package me.apex.hades.event.impl.bukkitevents;

import me.apex.hades.event.Event;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.event.block.Action;
import org.bukkit.inventory.ItemStack;

public class InteractEvent extends Event {

    private final Action action;
    private final ItemStack item;
    private final Block blockClicked;
    private final BlockFace blockFace;
    private final org.bukkit.event.Event.Result useItemInHand;
    private final org.bukkit.event.Event.Result useClickedBlock;

    public InteractEvent(Action action, ItemStack item, Block blockClicked, BlockFace blockFace, org.bukkit.event.Event.Result useItemInHand, org.bukkit.event.Event.Result useClickedBlock) {
        this.action = action;
        this.item = item;
        this.blockClicked = blockClicked;
        this.blockFace = blockFace;
        this.useItemInHand = useItemInHand;
        this.useClickedBlock = useClickedBlock;
    }

    public Action getAction() {
        return action;
    }

    public ItemStack getItem() {
        return item;
    }

    public Block getBlockClicked() {
        return blockClicked;
    }

    public BlockFace getBlockFace() {
        return blockFace;
    }

    public org.bukkit.event.Event.Result getUseItemInHand() {
        return useItemInHand;
    }

    public org.bukkit.event.Event.Result getUseClickedBlock() {
        return useClickedBlock;
    }

}
