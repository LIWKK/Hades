package me.apex.hades.processor.impl;

import me.apex.hades.processor.Processor;
import me.apex.hades.user.User;
import me.apex.hades.util.PacketUtil;

import java.util.Random;

//Credits to OilSlug (ExslodingDogs) for idea
public class VelocityProcessor extends Processor {
    public VelocityProcessor(User user) {
        super(user);
    }

    public void process() {
        if (!user.isTakingVelocity()) {
            if ((user.getTick() - user.getVelocityTick()) < 1) {
                Random random = new Random();
                user.setVerifyingVelocity(true);
                user.setLastVelocityId(random.nextInt());
                PacketUtil.sendKeepAlive(user, user.getLastVelocityId());
            }
        } else {
            user.setVelocityTick(user.getTick());
            user.setTakingVelocity((user.getTick() - user.getVelocityTick()) < 20);
        }
    }
}
