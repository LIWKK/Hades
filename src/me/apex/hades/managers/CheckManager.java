package me.apex.hades.managers;

import me.apex.hades.check.impl.combat.aim.*;
import me.apex.hades.check.impl.combat.aura.*;
import me.apex.hades.check.impl.combat.aura.AuraD;
import me.apex.hades.check.impl.combat.autoclicker.*;
import me.apex.hades.check.impl.combat.reach.*;
import me.apex.hades.check.impl.movement.fly.*;
import me.apex.hades.check.impl.movement.jesus.*;
import me.apex.hades.check.impl.movement.motion.*;
import me.apex.hades.check.impl.movement.sneak.*;
import me.apex.hades.check.impl.movement.scaffold.*;
import me.apex.hades.check.impl.movement.speed.*;
import me.apex.hades.check.impl.ClientCheck;
import me.apex.hades.check.impl.packet.badpackets.*;
import me.apex.hades.check.impl.packet.pingspoof.*;
import me.apex.hades.check.impl.packet.nofall.*;
import me.apex.hades.check.impl.packet.timer.*;
import me.apex.hades.data.User;
import me.apex.hades.event.EventHandler;

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
            FlyA.class,
            FlyB.class,
            FlyC.class,
            JesusA.class,
            JesusB.class,
            MotionA.class,
            MotionB.class,
            MotionC.class,
            MotionD.class,
            ScaffoldA.class,
            ScaffoldB.class,
            SneakA.class,
            SpeedA.class,
            SpeedB.class,
            SpeedC.class,
            BadPacketsA.class,
            BadPacketsB.class,
            BadPacketsC.class,
            BadPacketsD.class,
            BadPacketsE.class,
            BadPacketsF.class,
            BadPacketsG.class,
            BadPacketsH.class,
            BadPacketsI.class,
            BadPacketsJ.class,
            BadPacketsK.class,
            NoFallA.class,
            PingSpoofA.class,
            TimerA.class,
            ClientCheck.class,
    };

    public List<EventHandler> getChecks(User user) {
        List<EventHandler> checks = new ArrayList<>();
        Arrays.asList(CHECKS).forEach(check -> {
            try {
                checks.add((EventHandler) check.getConstructor(User.class).newInstance(user));
            } catch (Exception e) {
                throw new RuntimeException("There was an error with the check. Please contact the plugin developer");
            }
        });
        return checks;
    }
}