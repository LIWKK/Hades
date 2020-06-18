package me.apex.hades.tinyprotocol.packet.out;

import lombok.Getter;
import me.apex.hades.tinyprotocol.api.NMSObject;
import me.apex.hades.tinyprotocol.api.ProtocolVersion;
import me.apex.hades.tinyprotocol.reflection.FieldAccessor;
import org.bukkit.entity.Player;

@Getter
public class WrappedOutHeldItemSlot extends NMSObject {
    private static String packet = Server.HELD_ITEM;
    private FieldAccessor<Integer> slotField = fetchField(packet, int.class, 0);

    private int slot;

    public WrappedOutHeldItemSlot(Object object, Player player) {
        super(object, player);
    }

    @Override
    public void updateObject() {

    }

    public WrappedOutHeldItemSlot(int slot) {
        this.slot = slot;

        setObject(construct(packet, slot));
    }

    @Override
    public void process(Player player, ProtocolVersion version) {
        slot = fetch(slotField);
    }

    @Override
    public Object getObject() {
        return super.getObject();
    }
}
