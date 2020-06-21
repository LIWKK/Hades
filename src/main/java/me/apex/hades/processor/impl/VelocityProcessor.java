package me.apex.hades.processor.impl;

import me.apex.hades.processor.Processor;
import me.apex.hades.user.User;

//Working on this...
public class VelocityProcessor extends Processor {
    public VelocityProcessor(User user) {
        super(user);
    }

    private double lastMultiplierY, lastMultiplierXZ;

    public void process() {
        double multiplierY = user.getDeltaY() / user.getVelocityY();
        double multiplierXZ = user.getDeltaXZ() / (user.getVelocityX() * user.getVelocityX() + user.getVelocityZ() * user.getVelocityZ());

        double lastMultiplierY = this.lastMultiplierY;
        this.lastMultiplierY = multiplierY;

        double lastMultiplierXZ = this.lastMultiplierXZ;
        this.lastMultiplierXZ = multiplierXZ;

        double diffY = multiplierY - lastMultiplierY;
        double diffXZ = multiplierXZ - lastMultiplierXZ;

        if((user.getTick() - user.getVelocityTick()) <= 1) {
            user.setTakingVelocity(true);
        }
    }
}
