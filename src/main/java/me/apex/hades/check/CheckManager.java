package me.apex.hades.check;

import me.apex.hades.check.impl.combat.aim.AimA;
import me.apex.hades.check.impl.combat.autoclicker.AutoClickerA;
import me.apex.hades.check.impl.combat.autoclicker.AutoClickerB;
import me.apex.hades.check.impl.combat.aura.*;
import me.apex.hades.check.impl.combat.reach.ReachA;
import me.apex.hades.check.impl.combat.velocity.VelocityA;
import me.apex.hades.check.impl.movement.fly.FlyA;
import me.apex.hades.check.impl.movement.fly.FlyB;
import me.apex.hades.check.impl.movement.fly.FlyC;
import me.apex.hades.check.impl.movement.smallhop.SmallHopA;
import me.apex.hades.check.impl.movement.scaffold.ScaffoldA;
import me.apex.hades.check.impl.movement.speed.SpeedA;
import me.apex.hades.check.impl.movement.speed.SpeedB;
import me.apex.hades.check.impl.movement.speed.SpeedC;
import me.apex.hades.check.impl.player.badpackets.*;
import me.apex.hades.check.impl.player.timer.TimerA;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public enum CheckManager {
    INSTANCE;

    private Class[] CHECKS = new Class[]{
            AimA.class,
            AutoClickerA.class,
            AutoClickerB.class,
            AuraA.class,
            AuraB.class,
            AuraC.class,
            AuraD.class,
            AuraE.class,
            AuraF.class,
            ReachA.class,
            VelocityA.class,
            FlyA.class,
            FlyB.class,
            FlyC.class,
            ScaffoldA.class,
            SmallHopA.class,
            SpeedA.class,
            SpeedB.class,
            SpeedC.class,
            BadPacketsA.class,
            BadPacketsB.class,
            BadPacketsC.class,
            BadPacketsD.class,
            BadPacketsE.class,
            TimerA.class,
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
