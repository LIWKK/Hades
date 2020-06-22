package me.apex.hades.processor.impl;

import io.github.retrooper.packetevents.PacketEvents;
import io.github.retrooper.packetevents.packetwrappers.out.transaction.WrappedPacketOutTransaction;
import me.apex.hades.processor.Processor;
import me.apex.hades.user.User;

import java.util.Random;

//Credits to OilSlug (ExslodingDogs) for idea
public class VelocityProcessor extends Processor {
    public VelocityProcessor(User user) {
        super(user);
    }

    public void process() {
        if(!user.isTakingVelocity()) {
            if((user.getTick() - user.getVelocityTick()) < 1) {
                Random random = new Random();
                user.setVerifyingVelocity(true);
                user.setLastVelocityId(random.nextInt());
                PacketEvents.sendPacket(user.getPlayer(), new WrappedPacketOutTransaction(user.getLastVelocityId(), (short)0, false));
            }
        }else {
            user.setVelocityTick(user.getTick());
            if((user.getTick() - user.getVelocityTick()) < 20) {
                user.setTakingVelocity(true);
            }else user.setTakingVelocity(false);
        }
    }
}
