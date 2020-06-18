package me.apex.hades.event.impl.bukkitevents;

import me.apex.hades.event.Event;
import org.bukkit.inventory.ItemStack;

public class ItemConsumeEvent extends Event {

    private ItemStack item;

    public ItemConsumeEvent(ItemStack item) {
        this.item = item;
    }

    public ItemStack getItem() {
        return item;
    }

}
