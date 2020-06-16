package me.apex.hades.check;

import me.apex.hades.check.impl.combat.aim.AimA;
import me.apex.hades.check.impl.combat.aura.AuraA;
import me.apex.hades.check.impl.combat.aura.AuraC;
import me.apex.hades.check.impl.combat.autoclicker.AutoClickerA;
import me.apex.hades.check.impl.combat.pattern.PatternA;
import me.apex.hades.check.impl.movement.fly.FlyA;
import me.apex.hades.check.impl.movement.scaffold.ScaffoldA;
import me.apex.hades.check.impl.movement.speed.SpeedA;
import me.apex.hades.check.impl.movement.speed.SpeedB;
import me.apex.hades.check.impl.player.badpackets.BadPacketsA;
import me.apex.hades.check.impl.movement.invalid.InvalidA;
import me.apex.hades.check.impl.combat.aura.AuraB;
import me.apex.hades.check.impl.player.nofall.NoFallA;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CheckManager {

    private static final Class[] checks = new Class[]{
            AimA.class,
            AuraA.class,
            AuraB.class,
            AuraC.class,
            AutoClickerA.class,
            PatternA.class,
            FlyA.class,
            InvalidA.class,
            ScaffoldA.class,
            SpeedA.class,
            SpeedB.class,
            BadPacketsA.class,
            NoFallA.class
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

}
