package me.apex.hades.check.impl.player.badpackets;

import me.apex.hades.check.Check;
import me.apex.hades.check.Type;
import me.apex.hades.event.AnticheatEvent;
import me.apex.hades.event.impl.packetevents.EntityActionEvent;
import me.apex.hades.event.impl.packetevents.FlyingPacketEvent;
import me.apex.hades.tinyprotocol.packet.in.WrappedInEntityActionPacket;
import me.apex.hades.user.User;
import org.bukkit.event.Listener;

public class BadPacketsB extends Check implements Listener {
    public BadPacketsB(String checkName, String letter, Type type, boolean enabled) {
        super(checkName, letter, type, enabled);
    }
    int sprintTicks;
    @Override
    public void onHandle(User user, AnticheatEvent e) {
        if (e instanceof FlyingPacketEvent) {
            sprintTicks = 0;
        }else if (e instanceof EntityActionEvent) {
            if (((EntityActionEvent) e).getAction() == WrappedInEntityActionPacket.EnumPlayerAction.START_SPRINTING) {
                sprintTicks++;
                if (sprintTicks >= 2) {
                    flag(user, "Sprint Sent Packet Twice");
                }
            }
        }
    }
}
