package me.apex.hades.check.impl.combat.autoclicker;

import io.github.retrooper.packetevents.event.PacketEvent;
import me.apex.hades.HadesPlugin;
import me.apex.hades.check.Check;
import me.apex.hades.check.CheckInfo;
import me.apex.hades.event.impl.packetevents.SwingEvent;
import me.apex.hades.user.User;

@CheckInfo(name = "AutoClicker", type = "C")
public class AutoClickerC extends Check {

    private long start;

    @Override
    public void onHandle(PacketEvent e, User user) {
        if (e instanceof SwingEvent){
            if (user.isDigging() || user.isRightClickingBlock())return;
            if(time() - start >= 1000L) {
                if(user.getCPS() > HadesPlugin.getInstance().getConfig().getInt("Max-CPS")) {
                    flag(user, "CPS: " + user.getCPS());
                }
                user.setCPS(0);
                start = time();
            } else user.setCPS(user.getCPS() + 1);
        }
    }
}
