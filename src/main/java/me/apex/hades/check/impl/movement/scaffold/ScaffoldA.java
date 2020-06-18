package me.apex.hades.check.impl.movement.scaffold;

import me.apex.hades.check.Check;
import me.apex.hades.check.ClassInterface;
import me.apex.hades.check.Type;
import me.apex.hades.event.AnticheatEvent;
import me.apex.hades.event.impl.packetevents.FlyingEvent;
import me.apex.hades.event.impl.packetevents.PlaceEvent;
import me.apex.hades.user.User;

public class ScaffoldA extends Check implements ClassInterface {
    public ScaffoldA(String checkName, String letter, Type type, boolean enabled) {
        super(checkName, letter, type, enabled);
    }

    private long lastFlying;

    @Override
    public void onHandle(User user, AnticheatEvent e) {
        if(e instanceof PlaceEvent) {
            long timeDiff = time() - lastFlying;

            if(timeDiff < 5) {
                if(++preVL > 10) {
                    flag(user, "low flying delay, d: " + timeDiff);
                }
            }else preVL = 0;
        }else if(e instanceof FlyingEvent) {
            lastFlying = time();
        }
    }
}
