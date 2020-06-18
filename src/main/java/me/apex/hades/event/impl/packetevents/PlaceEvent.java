package me.apex.hades.event.impl.packetevents;

import io.github.retrooper.packetevents.utils.vector.Vector3i;
import lombok.Getter;
import me.apex.hades.event.Event;
import org.bukkit.inventory.ItemStack;

@Getter
public class PlaceEvent extends Event {

    private final Vector3i blockPos;
    private final ItemStack itemStack;

    public PlaceEvent(Vector3i blockPos, ItemStack itemStack) {
        this.blockPos = blockPos;
        this.itemStack = itemStack;
    }

}
