package me.apex.hades.check.api;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import me.apex.hades.check.impl.combat.aim.AimA;
import me.apex.hades.check.impl.combat.aim.AimB;
import me.apex.hades.check.impl.combat.aim.AimC;
import me.apex.hades.check.impl.combat.aim.AimD;
import me.apex.hades.check.impl.combat.aim.AimE;
import me.apex.hades.check.impl.combat.aim.AimF;
import me.apex.hades.check.impl.combat.aim.AimG;
import me.apex.hades.check.impl.combat.aim.AimH;
import me.apex.hades.check.impl.combat.aura.AuraA;
import me.apex.hades.check.impl.combat.aura.AuraB;
import me.apex.hades.check.impl.combat.aura.AuraC;
import me.apex.hades.check.impl.combat.aura.AuraD;
import me.apex.hades.check.impl.combat.aura.AuraE;
import me.apex.hades.check.impl.combat.aura.AuraF;
import me.apex.hades.check.impl.combat.aura.AuraG;
import me.apex.hades.check.impl.combat.aura.AuraH;
import me.apex.hades.check.impl.combat.aura.AuraI;
import me.apex.hades.check.impl.combat.aura.AuraJ;
import me.apex.hades.check.impl.combat.autoclicker.AutoClickerA;
import me.apex.hades.check.impl.combat.autoclicker.AutoClickerB;
import me.apex.hades.check.impl.combat.autoclicker.AutoClickerC;
import me.apex.hades.check.impl.combat.autoclicker.AutoClickerD;
import me.apex.hades.check.impl.combat.criticals.CriticalsA;
import me.apex.hades.check.impl.combat.reach.ReachA;
import me.apex.hades.check.impl.combat.velocity.VelocityA;
import me.apex.hades.check.impl.movement.fly.FlyA;
import me.apex.hades.check.impl.movement.fly.FlyB;
import me.apex.hades.check.impl.movement.fly.FlyC;
import me.apex.hades.check.impl.movement.motion.MotionA;
import me.apex.hades.check.impl.movement.scaffold.ScaffoldA;
import me.apex.hades.check.impl.movement.scaffold.ScaffoldB;
import me.apex.hades.check.impl.movement.smallhop.SmallHopA;
import me.apex.hades.check.impl.movement.speed.SpeedA;
import me.apex.hades.check.impl.movement.speed.SpeedB;
import me.apex.hades.check.impl.movement.speed.SpeedC;
import me.apex.hades.check.impl.movement.speed.SpeedD;
import me.apex.hades.check.impl.packet.badpackets.BadPacketsA;
import me.apex.hades.check.impl.packet.badpackets.BadPacketsB;
import me.apex.hades.check.impl.packet.badpackets.BadPacketsC;
import me.apex.hades.check.impl.packet.badpackets.BadPacketsD;
import me.apex.hades.check.impl.packet.badpackets.BadPacketsE;
import me.apex.hades.check.impl.packet.badpackets.BadPacketsF;
import me.apex.hades.check.impl.packet.badpackets.BadPacketsG;
import me.apex.hades.check.impl.packet.badpackets.BadPacketsH;
import me.apex.hades.check.impl.packet.nofall.NoFallA;
import me.apex.hades.check.impl.packet.timer.TimerA;
import me.apex.hades.check.impl.packet.timer.TimerB;

public enum CheckManager {
    INSTANCE;

    private Class[] CHECKS = new Class[]{
            AimA.class,
            AimB.class,
            AimC.class,
            AimD.class,
            AimE.class,
            AimF.class,
            AimG.class,
            AimH.class,
            AuraA.class,
            AuraB.class,
            AuraC.class,
            AuraD.class,
            AuraE.class,
            AuraF.class,
            AuraG.class,
            AuraH.class,
            AuraI.class,
            AuraJ.class,
            CriticalsA.class,
            AutoClickerA.class,
            AutoClickerB.class,
            AutoClickerC.class,
            AutoClickerD.class,
            ReachA.class,
            VelocityA.class,
            FlyA.class,
            FlyB.class,
            FlyC.class,
            MotionA.class,
            ScaffoldA.class,
            ScaffoldB.class,
            SpeedA.class,
            SpeedB.class,
            SpeedC.class,
            SpeedD.class,
            SmallHopA.class,
            BadPacketsA.class,
            BadPacketsB.class,
            BadPacketsC.class,
            BadPacketsD.class,
            BadPacketsE.class,
            BadPacketsF.class,
            BadPacketsG.class,
            BadPacketsH.class,
            NoFallA.class,
            TimerA.class,
            TimerB.class
    };

    public List<Check> loadChecks() {
        List<Check> add = new ArrayList<>();
        Arrays.asList(CHECKS).forEach(check -> {
            try {
                add.add((Check) check.getConstructor().newInstance());
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        });
        return add;
    }

    public CheckInfo getCheckInfo(Check check) {
        return check.getClass().getAnnotation(CheckInfo.class);
    }

}
