package me.apex.hades.processor.impl;

import me.apex.hades.processor.Processor;
import me.apex.hades.user.User;
import me.apex.hades.util.MathUtil;
import org.bukkit.Bukkit;

public class OptifineProcessor extends Processor {
    public OptifineProcessor(User user) {
        super(user);
    }

    private double lastRotation, average = 200;
    //lastAverageSensitivity = 200;
    //private Deque<Double> rotations = new LinkedList<>();

    public void process(User user) {
        double rotation = Math.round(user.getDeltaYaw() + user.getDeltaPitch());
        double lastRotation = this.lastRotation;
        this.lastRotation = rotation;

        double lcd = MathUtil.lcd((long)rotation * 1677721, (long)lastRotation * 1677721) % 200;
        if(lcd != 0.0) {
            average = ((average * 19) + Math.round(lcd)) / 20;
            Bukkit.broadcastMessage("" + Math.round(average) + "% sensitivity");
        }
    }
}
