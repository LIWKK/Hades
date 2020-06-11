package me.apex.hades.check.impl.combat.autoclicker;

import io.github.retrooper.packetevents.event.impl.PacketReceiveEvent;
import io.github.retrooper.packetevents.packet.Packet;
import me.apex.hades.Hades;
import me.apex.hades.check.api.Check;
import me.apex.hades.check.api.CheckInfo;
import me.apex.hades.objects.User;

@CheckInfo(name = "AutoClicker", type = "E")
public class AutoClickerE extends Check {
    @Override
    public void onPacket(PacketReceiveEvent e, User user) {
        if (e.getPacketName().equalsIgnoreCase(Packet.Client.ARM_ANIMATION)){
            if (user.isDigging() || user.isRightClicking())return;
            if((System.nanoTime() / 1000000) - user.getAutoClickerEStart() >= 1000L) {
                if(user.getClicks() > Hades.getInstance().getConfig().getInt("Max-CPS")) {
                    flag(user, "CPS: " + user.getClicks());
                }
                user.setClicks(0);
                user.setAutoClickerEStart(System.nanoTime() / 1000000);
            } else user.setClicks(user.getClicks() + 1);
        }
    }
}
