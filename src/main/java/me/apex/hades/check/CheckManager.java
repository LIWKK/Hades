package me.apex.hades.check;

import me.apex.hades.check.impl.combat.aim.AimA;
import me.apex.hades.check.impl.combat.aura.*;
import me.apex.hades.check.impl.combat.autoclicker.AutoClickerA;
import me.apex.hades.check.impl.combat.autoclicker.AutoClickerB;
import me.apex.hades.check.impl.combat.autoclicker.AutoClickerC;
import me.apex.hades.check.impl.combat.criticals.CriticalsA;
import me.apex.hades.check.impl.combat.reach.ReachA;
import me.apex.hades.check.impl.combat.reach.ReachB;
import me.apex.hades.check.impl.combat.velocity.VelocityA;
import me.apex.hades.check.impl.movement.fastladder.FastLadderA;
import me.apex.hades.check.impl.movement.fly.FlyA;
import me.apex.hades.check.impl.movement.fly.FlyB;
import me.apex.hades.check.impl.movement.fly.FlyC;
import me.apex.hades.check.impl.movement.invalid.InvalidA;
import me.apex.hades.check.impl.movement.invalid.InvalidB;
import me.apex.hades.check.impl.movement.noslow.NoSlowA;
import me.apex.hades.check.impl.movement.scaffold.ScaffoldA;
import me.apex.hades.check.impl.movement.scaffold.ScaffoldB;
import me.apex.hades.check.impl.movement.speed.SpeedA;
import me.apex.hades.check.impl.movement.speed.SpeedB;
import me.apex.hades.check.impl.movement.speed.SpeedC;
import me.apex.hades.check.impl.movement.speed.SpeedD;
import me.apex.hades.check.impl.other.vape.Vape;
import me.apex.hades.check.impl.player.badpackets.*;
import me.apex.hades.check.impl.player.fastuse.FastUseA;
import me.apex.hades.check.impl.player.interactreach.InteractReachA;
import me.apex.hades.check.impl.player.invmove.InvMoveA;
import me.apex.hades.check.impl.player.nofall.NoFallA;
import me.apex.hades.check.impl.player.timer.TimerA;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CheckManager {

    private static final Class[] checks = new Class[]{
            AimA.class,
            AuraA.class,
            AuraB.class,
            AuraC.class,
            AuraD.class,
            AuraE.class,
            AutoClickerA.class,
            AutoClickerB.class,
            AutoClickerC.class,
            CriticalsA.class,
            ReachA.class,
            ReachB.class,
            VelocityA.class,
            FlyA.class,
            FlyB.class,
            FlyC.class,
            InvalidA.class,
            InvalidB.class,
            NoSlowA.class,
            ScaffoldA.class,
            ScaffoldB.class,
            SpeedA.class,
            SpeedB.class,
            SpeedC.class,
            SpeedD.class,
            FastLadderA.class,
            BadPacketsA.class,
            BadPacketsB.class,
            BadPacketsC.class,
            BadPacketsD.class,
            BadPacketsE.class,
            InvMoveA.class,
            NoFallA.class,
            TimerA.class,
            InteractReachA.class,
            FastUseA.class,
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
