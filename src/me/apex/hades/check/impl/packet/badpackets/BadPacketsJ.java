package me.apex.hades.check.impl.packet.badpackets;

import me.apex.hades.check.Check;
import me.apex.hades.data.User;
import me.apex.hades.event.Event;
import me.apex.hades.event.impl.PacketEvent;
import me.apex.archive.network.packets.PacketPlayInBlockPlace;
import me.apex.archive.network.packets.PacketPlayInHeldItemSlot;
import me.apex.archive.network.packets.PacketPlayInUseEntity;

public class BadPacketsJ extends Check {

    public BadPacketsJ(User user) {
        super(user);
        name = "BadPackets (J)";
        type = CheckType.PACKET;
    }

    private long lastSlot;
    private boolean block, attackedBeforeThis;

    @Override
    public void handle(Event event) {
        if(event instanceof PacketEvent)
        {
            PacketEvent e = (PacketEvent) event;
            if(e.getPacket() instanceof PacketPlayInBlockPlace)
            {
                PacketPlayInBlockPlace packet = (PacketPlayInBlockPlace) e.getPacket();
                if(user.getPlayer().getItemInHand().getType().toString().contains("SWORD") && packet.getFace() == 255 && packet.getBlockPos().getX() == -1 && packet.getBlockPos().getY() == -1 && packet.getBlockPos().getZ() == -1)
                {
                    block = true;
                    if(attackedBeforeThis)
                    {
                        if(vl++ > 1)
                            flag("blocked after attack");
                    }else {
                        vl = 0;
                        attackedBeforeThis = false;
                    }
                }
            }else if(e.getPacket() instanceof PacketPlayInUseEntity)
            {
                PacketPlayInUseEntity packet = (PacketPlayInUseEntity) e.getPacket();
                if(packet.getAction() == PacketPlayInUseEntity.Action.ATTACK)
                {
                    if(!block && Math.abs(System.currentTimeMillis() - lastSlot) > 50)
                        attackedBeforeThis = true;
                    else
                        block = false;
                    if(attackedBeforeThis)
                        attackedBeforeThis = false;
                }
            }else if(e.getPacket() instanceof PacketPlayInHeldItemSlot)
            {
                lastSlot = System.currentTimeMillis();
            }
        }
    }
}
