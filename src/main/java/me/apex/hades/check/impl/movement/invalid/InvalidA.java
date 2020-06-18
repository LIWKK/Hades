package me.apex.hades.check.impl.movement.invalid;

import me.apex.hades.check.Check;
import me.apex.hades.check.ClassInterface;
import me.apex.hades.check.Type;
import me.apex.hades.event.AnticheatEvent;
import me.apex.hades.event.impl.packetevents.FlyingPacketEvent;
import me.apex.hades.user.User;

public class InvalidA extends Check implements ClassInterface {
    public InvalidA(String checkName, String letter, Type type, boolean enabled) {
        super(checkName, letter, type, enabled);
    }

    private double lastDeltaY;

    @Override
    public void onHandle(User user, AnticheatEvent e) {
        if(e instanceof FlyingPacketEvent) {
            if(((FlyingPacketEvent) e).isClientMoved()) {
                double deltaY = user.getLocation().getY() - user.getPreviousLocation().getY();
                double lastDeltaY = this.lastDeltaY;
                this.lastDeltaY = deltaY;

                if(deltaY == -lastDeltaY && deltaY != 0.0) {
                    if(++preVL > 1) {
                        flag(user, "repetitive vertical motions, m: " + deltaY);
                    }
                }else preVL = 0;
            }
        }
    }
}
