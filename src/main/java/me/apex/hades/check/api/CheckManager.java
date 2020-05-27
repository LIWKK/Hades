package me.apex.hades.check.api;

import me.apex.hades.check.impl.combat.aim.*;
import me.apex.hades.check.impl.combat.aura.*;
import me.apex.hades.check.impl.combat.autoclicker.*;
import me.apex.hades.check.impl.combat.reach.*;
import me.apex.hades.check.impl.combat.velocity.*;
import me.apex.hades.check.impl.movement.fly.*;
import me.apex.hades.check.impl.movement.motion.*;
import me.apex.hades.check.impl.movement.scaffold.*;
import me.apex.hades.check.impl.movement.speed.*;
import me.apex.hades.check.impl.packet.badpackets.*;
import me.apex.hades.check.impl.packet.nofall.*;
import me.apex.hades.check.impl.packet.timer.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

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
            AuraA.class,
            AuraB.class,
            AuraC.class,
            AuraD.class,
            AuraE.class,
            AuraF.class,
            AuraG.class,
            AuraH.class,
            AutoClickerA.class,
            AutoClickerB.class,
            AutoClickerC.class,
            AutoClickerD.class,
            AutoClickerE.class,
            ReachA.class,
            ReachB.class,
            VelocityA.class,
            VelocityB.class,
            FlyA.class,
            FlyB.class,
            MotionA.class,
            ScaffoldA.class,
            ScaffoldB.class,
            SpeedA.class,
            SpeedB.class,
            SpeedC.class,
            SpeedD.class,
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
