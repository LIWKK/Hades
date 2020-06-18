package me.apex.hades.event.impl.bukkitevents;

import me.apex.hades.event.AnticheatEvent;
import org.bukkit.inventory.ItemStack;

public class ItemConsumeEvent extends AnticheatEvent {

    private ItemStack item;

    public ItemConsumeEvent(ItemStack item) {
        this.item = item;
    }

    public ItemStack getItem() {
        return item;
    }

}
