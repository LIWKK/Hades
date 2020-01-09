package me.apex.hades.check.impl.combat.aura;

import cc.funkemunky.api.events.impl.PacketReceiveEvent;
import cc.funkemunky.api.tinyprotocol.api.Packet;
import cc.funkemunky.api.tinyprotocol.packet.in.WrappedInUseEntityPacket;
import me.apex.hades.check.api.Check;
import me.apex.hades.check.api.CheckInfo;
import me.apex.hades.data.User;
import me.apex.hades.utils.MathUtils;

@CheckInfo(Name = "Aura (E)", Type = Check.CheckType.COMBAT, Experimental = false)
public class AuraE extends Check {

    private double lastFixedGcd;

    @Override
    public void onPacket(PacketReceiveEvent e, User user) {
        if(e.getType().equalsIgnoreCase(Packet.Client.USE_ENTITY))
        {
            WrappedInUseEntityPacket packet = new WrappedInUseEntityPacket(e.getPacket(), e.getPlayer());
            if(packet.getAction() == packet.getAction().ATTACK)
            {
                double yawDiff = user.getDeltaYaw();
                double lastYawDiff = user.getLastDeltaYaw();

                double gcd = MathUtils.gcd((long)yawDiff, (long)lastYawDiff) * (yawDiff % lastYawDiff);
                double fixedGcd = gcd / Math.PI;
                double lastFixedGcd = this.lastFixedGcd;
                this.lastFixedGcd = fixedGcd;

                double diff = Math.abs(fixedGcd - lastFixedGcd);

                if((diff < 1.0 || diff > 100.0D) && yawDiff > 2.0D)
                {
                    if(vl++ > 3)
                        flag(user, "diff = " + diff);
                }else vl = 0;
            }
        }
    }

}
