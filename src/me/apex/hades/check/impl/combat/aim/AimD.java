package me.apex.hades.check.impl.combat.aim;

import cc.funkemunky.api.events.impl.PacketReceiveEvent;
import me.apex.hades.check.api.Check;
import me.apex.hades.check.api.CheckInfo;
import me.apex.hades.data.User;
import me.apex.hades.utils.MathUtils;
import me.apex.hades.utils.PacketUtils;

@CheckInfo(Name = "Aim (D)", Type = Check.CheckType.COMBAT, Experimental = false)
public class AimD extends Check {

    private double lastPitchOffset;

    @Override
    public void onPacket(PacketReceiveEvent e, User user) {
        if(PacketUtils.isFlyingPacket(e.getType()))
        {
            double pitchDiff = user.getDeltaPitch();
            double lastPitchDiff = user.getLastDeltaPitch();

            double pitchOffset = Math.abs(pitchDiff - lastPitchDiff);
            double lastPitchOffset = this.lastPitchOffset;
            this.lastPitchOffset = pitchOffset;

            double gcd = MathUtils.gcd((long)pitchDiff, (long)lastPitchDiff) / (pitchOffset + lastPitchOffset);
            if(Double.isNaN(gcd)) return;

            if(pitchOffset < 0.1D && gcd < 0.07D && gcd > 0.0D)
                flag(user, "offset = " + pitchOffset + ", gcd = " + gcd);
        }
    }

}
