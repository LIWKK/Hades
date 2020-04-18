package me.apex.hades.check.impl.movement.jesus;

import me.apex.hades.check.Check;
import me.apex.hades.data.User;
import me.apex.hades.event.Event;
import me.apex.hades.event.impl.PacketEvent;
import me.apex.hades.utils.PacketUtils;
import me.apex.hades.utils.PlayerUtils;
import org.bukkit.potion.PotionEffectType;

public class JesusB extends Check {

    public JesusB(User user) {
        super(user);
        name = "Jesus (B)";
        type = CheckType.MOVEMENT;
    }

    private boolean lastInLiquid, lastLastInLiquid;

    @Override
    public void handle(Event event) {
        if (event instanceof PacketEvent) {
            PacketEvent e = (PacketEvent) event;
            if (PacketUtils.isFlyingPacket(e.getPacketHandler().getType())) {
                //Exceptions
                if (PlayerUtils.hasSpeedBypass(user.getPlayer()) || PlayerUtils.hasJesusBypass(user.getPlayer())) return;

                double dist = user.data.deltaXZ;
                double lastDist = user.data.lastDeltaXZ;

                double prediction = lastDist * 0.699999988079071D;
                double diff = dist - prediction;
                double scaledDist = diff * 100;

                debug("dist = " + scaledDist);

                double max = 6.2;
                max += (PlayerUtils.getPotionEffectLevel(PotionEffectType.SPEED, user.getPlayer()) * max);

                boolean inLiquid = PlayerUtils.isNearLiquid(user.getPlayer()) || PlayerUtils.isNearLiquidOffset(user.getPlayer());
                boolean lastInLiquid = this.lastInLiquid;
                boolean lastLastInLiquid = this.lastLastInLiquid;

                this.lastInLiquid = inLiquid;
                this.lastLastInLiquid = lastInLiquid;

                if (scaledDist > max && inLiquid && lastInLiquid && lastLastInLiquid) {
                    if (vl++ > 4)
                        flag("dist = " + scaledDist);
                } else vl = 0;
            }
        }
    }
}