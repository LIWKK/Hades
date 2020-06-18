package me.apex.hades.check.impl.player.nofall;

import me.apex.hades.check.Check;
import me.apex.hades.check.CheckInfo;
import me.apex.hades.event.AnticheatEvent;
import me.apex.hades.event.impl.packetevents.FlyingPacketEvent;
import me.apex.hades.user.User;
import me.apex.hades.utils.other.PlayerUtils;

@CheckInfo(name = "NoFall", type = "A")
public class NoFallA extends Check {
    @Override
    public void onHandle(User user, AnticheatEvent e) {
        if (e instanceof FlyingPacketEvent){
            if (user.isClientGround() && !user.onGround && !PlayerUtils.isOnGround(user.getPlayer())){
                if (++preVL >= 4){
                    flag(user, "client: " + user.isClientGround() + " server: " + user.onGround);
                }
            }else preVL = 0;
        }
    }
}
