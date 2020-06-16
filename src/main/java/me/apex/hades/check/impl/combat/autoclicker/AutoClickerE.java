package me.apex.hades.check.impl.combat.autoclicker;

import io.github.retrooper.packetevents.event.PacketEvent;
import me.apex.hades.HadesPlugin;
import me.apex.hades.check.Check;
import me.apex.hades.check.CheckInfo;
import me.apex.hades.event.impl.packetevents.SwingEvent;
import me.apex.hades.user.User;

@CheckInfo(name = "AutoClicker", type = "E")
public class AutoClickerE extends Check{
    long start;

    @Override
    public void onEvent(PacketEvent e, User user) {
        if (e instanceof SwingEvent){
            if (user.digging || user.rightClickingBlock || user.rightClickingAir)return;
            if(time() - start >= 1000L) {
                if(user.CPS > HadesPlugin.instance.getConfig().getInt("Max-CPS")) {
                    flag(user, "CPS: " + user.CPS);
                }
                user.CPS = 0;
                start = time();
            } else user.CPS++;
        }
    }
}
