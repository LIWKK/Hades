package me.apex.hades.processor.impl;

import me.apex.hades.processor.Processor;
import me.apex.hades.user.User;
import me.apex.hades.util.MathUtil;
import org.bukkit.Bukkit;

//Working on this...
public class VelocityProcessor extends Processor {
    public VelocityProcessor(User user) {
        super(user);
    }

    public void process() {
        if((user.getTick() - user.getVelocityTick()) <= 1) {
            if((MathUtil.isRoughlyEqual(user.getDeltaY(), user.getLastDeltaY() + user.getVelocityY(), 0.1))
                    && (MathUtil.isRoughlyEqual(user.getDeltaXZ(), user.getLastDeltaXZ() + (user.getVelocityX() * user.getVelocityX() + user.getVelocityZ() * user.getVelocityZ()), 0.1))) {
                user.setTakingVelocity(true);
            }
        }else {
            if((!MathUtil.isRoughlyEqual(user.getDeltaY(), user.getLastDeltaY() + user.getVelocityY(), 0.1))
                    && (!MathUtil.isRoughlyEqual(user.getDeltaXZ(), user.getLastDeltaXZ() + (user.getVelocityX() * user.getVelocityX() + user.getVelocityZ() * user.getVelocityZ()), 0.1))) {
                user.setTakingVelocity(false);
            }
        }
        Bukkit.broadcastMessage("" + user.isTakingVelocity());
    }
}
