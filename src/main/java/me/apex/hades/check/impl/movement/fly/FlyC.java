package me.apex.hades.check.impl.movement.fly;

import io.github.retrooper.packetevents.event.PacketEvent;
import me.apex.hades.check.Check;
import me.apex.hades.check.CheckInfo;
import me.apex.hades.event.impl.packetevents.FlyingEvent;
import me.apex.hades.user.User;
import me.apex.hades.util.MathUtil;
import me.apex.hades.util.PlayerUtil;

import java.util.Deque;
import java.util.LinkedList;

@CheckInfo(name = "Fly", type = "C")
public class FlyC extends Check {

    private Deque<Double> distList = new LinkedList();
    private double lastDeviation;

    @Override
    public void onHandle(PacketEvent e, User user) {
        if (e instanceof FlyingEvent){
            distList.add(user.getDeltaY());

            if (distList.size() == 10) {
                double deviation = MathUtil.getStandardDeviation(distList.stream().mapToDouble(d -> d).toArray());
                double lastDeviation = this.lastDeviation;
                this.lastDeviation = deviation;

                if(!user.isOnGround() && !PlayerUtil.isClimbableBlock(user.getLocation().getBlock()) && !PlayerUtil.isOnGround(user.getPlayer()) && elapsed(user.getTick(), user.getFlyingTick()) > 40 && !user.getPlayer().isInsideVehicle() && !user.isInLiquid() && !user.isInWeb()) {
                    if(deviation == 0.0D) {
                        flag(user, "deviation = " + deviation);
                    }
                }

                distList.clear();
            }
        }
    }
}
