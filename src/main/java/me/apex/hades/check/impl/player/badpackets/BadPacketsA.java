package me.apex.hades.check.impl.player.badpackets;

import me.apex.hades.check.Check;
import me.apex.hades.check.ClassInterface;
import me.apex.hades.check.Type;
import me.apex.hades.event.AnticheatEvent;
import me.apex.hades.event.impl.packetevents.FlyingPacketEvent;
import me.apex.hades.user.User;

public class BadPacketsA extends Check implements ClassInterface {
    public BadPacketsA(String checkName, String letter, Type type, boolean enabled) {
        super(checkName, letter, type, enabled);
    }
    @Override
    public void onHandle(User user, AnticheatEvent e) {
        if (e instanceof FlyingPacketEvent) {
            if (Math.abs(user.getPlayer().getLocation().getPitch()) > 90.0) {
                flag(user, "Invalid Pitch: "+user.getPlayer().getLocation().getPitch());
            }
        }
    }
}
