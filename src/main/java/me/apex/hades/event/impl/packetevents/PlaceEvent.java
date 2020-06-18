package me.apex.hades.event.impl.packetevents;

import me.apex.hades.event.AnticheatEvent;
import me.apex.hades.tinyprotocol.packet.types.BaseBlockPosition;
import org.bukkit.inventory.ItemStack;

public class PlaceEvent extends AnticheatEvent {

    private final BaseBlockPosition blockPos;
    private final ItemStack itemStack;

    public PlaceEvent(BaseBlockPosition blockPos, ItemStack itemStack) {
        this.blockPos = blockPos;
        this.itemStack = itemStack;
    }

    public BaseBlockPosition getBlockPos() {
        return blockPos;
    }

    public ItemStack getItemStack() {
        return itemStack;
    }

}
