package me.apex.hades.check;

import me.apex.hades.check.impl.combat.aura.AuraA;
import me.apex.hades.check.impl.combat.aura.AuraB;
import me.apex.hades.check.impl.combat.aura.AuraC;
import me.apex.hades.check.impl.combat.autoclicker.*;
import me.apex.hades.check.impl.combat.criticals.CriticalsA;
import me.apex.hades.check.impl.combat.pattern.PatternA;
import me.apex.hades.check.impl.combat.reach.ReachA;
import me.apex.hades.check.impl.movement.fly.FlyA;
import me.apex.hades.check.impl.movement.fly.FlyB;
import me.apex.hades.check.impl.movement.invalid.InvalidA;
import me.apex.hades.check.impl.movement.noslow.NoSlowA;
import me.apex.hades.check.impl.movement.speed.SpeedA;
import me.apex.hades.check.impl.movement.speed.SpeedB;
import me.apex.hades.check.impl.other.vape.Vape;
import me.apex.hades.check.impl.player.badpackets.*;
import me.apex.hades.check.impl.player.fasteat.FastEatA;
import me.apex.hades.check.impl.player.nofall.NoFallA;
import me.apex.hades.check.impl.player.timer.TimerA;
import me.apex.hades.check.impl.player.timer.TimerB;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CheckManager {

    private static final Class[] checks = new Class[]{
            AuraA.class,
            AuraB.class,
            AuraC.class,
            CriticalsA.class,
            ReachA.class,
            AutoClickerA.class,
            AutoClickerB.class,
            AutoClickerC.class,
            AutoClickerD.class,
            AutoClickerE.class,
            PatternA.class,
            FlyA.class,
            FlyB.class,
            InvalidA.class,
            SpeedA.class,
            SpeedB.class,
            NoSlowA.class,
            BadPacketsA.class,
            BadPacketsB.class,
            BadPacketsC.class,
            BadPacketsD.class,
            BadPacketsE.class,
            BadPacketsF.class,
            NoFallA.class,
            TimerA.class,
            TimerB.class,
            FastEatA.class,
            Vape.class
    };

    public static List<Check> loadChecks() {
        List<Check> checklist = new ArrayList<>();
        Arrays.asList(checks).forEach(check -> {
            try {
                checklist.add((Check) check.getConstructor().newInstance());
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        return checklist;
    }

    public static CheckInfo getCheckInfo(Check check) {
        return check.getClass().getAnnotation(CheckInfo.class);
    }
}
