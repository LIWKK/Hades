package me.apex.hades.check.impl.combat.aim;

import io.github.retrooper.packetevents.event.PacketEvent;
import me.apex.hades.check.Check;
import me.apex.hades.check.CheckInfo;
import me.apex.hades.event.impl.packetevents.FlyingEvent;
import me.apex.hades.user.User;

@CheckInfo(name =  "Aim", type = "A")
public class AimA extends Check {

    @Override
    public void onEvent(PacketEvent e, User user) {
        if(e instanceof FlyingEvent) {
            if(((FlyingEvent) e).hasMoved()) {

            }
        }
    }

}
