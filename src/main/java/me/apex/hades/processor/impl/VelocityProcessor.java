package me.apex.hades.processor.impl;

import me.apex.hades.processor.Processor;
import me.apex.hades.user.User;

public class VelocityProcessor extends Processor {
    public VelocityProcessor(User user) {
        super(user);
    }

    public void process() {
        if((user.getTick() - user.getVelocityTick()) <= 1) {
            
        }
    }
}
