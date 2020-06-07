package me.apex.hades.check.impl.combat.aim;

import io.github.retrooper.packetevents.event.impl.PacketReceiveEvent;
import me.apex.hades.check.api.Check;
import me.apex.hades.check.api.CheckInfo;
import me.apex.hades.objects.User;
import me.apex.hades.utils.MathUtils;
import me.apex.hades.utils.PacketUtils;
import org.bukkit.Bukkit;

//Credits to Killeurdu2b
@CheckInfo(name = "Aim", type = "H")
public class AimH extends Check {

    private float lastDiff;

    @Override
    public void init() {
        dev = true;
        enabled = true;
    }

    @Override
    public void onPacket(PacketReceiveEvent e, User user) {
        if (PacketUtils.isFlyingPacket(e.getPacketName())) {
            float diff = Math.abs(user.getDeltaPitch()) % 180F;
            float lastDiff = this.lastDiff;
            this.lastDiff = diff;

            double gcd = MathUtils.getGcd((long) (diff*16777216), (long) (lastDiff*16777216));
            double val = gcd / 16777216;

            if(Math.abs(diff - lastDiff) > 0){
                if(Math.abs(diff - lastDiff) < 15 && !user.isUsingOptifine()){
                    if(val < 0.01){
                        //Bukkit.broadcastMessage("§7user= " + user + " val= " + val + " Math= " + (Math.abs(diff - lastDiff)) + " diff= " + (Math.abs(val - Math.abs(diff - lastDiff))));
                        if(vl++ > 1){
                            flag(user, "val = " + val + " Math= " + (Math.abs(diff - lastDiff)));
                        }
                    }

                } else vl = 0;

            }

        }
    }

}