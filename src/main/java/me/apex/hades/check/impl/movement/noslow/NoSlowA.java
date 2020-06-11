package me.apex.hades.check.impl.movement.noslow;

import io.github.retrooper.packetevents.enums.ClientVersion;
import io.github.retrooper.packetevents.event.impl.PacketReceiveEvent;
import me.apex.hades.check.api.Check;
import me.apex.hades.check.api.CheckInfo;
import me.apex.hades.objects.User;
import me.apex.hades.utils.PacketUtils;
import org.bukkit.potion.PotionEffectType;

@CheckInfo(name = "NoSlow", type = "A")
public class NoSlowA extends Check {
    int preVL;

    @Override
    public void onPacket(PacketReceiveEvent e, User user) {
        if (PacketUtils.isFlyingPacket(e.getPacketName())){
            //Patches vanilla noslow and badpackets detect NCP bypass so yea on blocking noslow were very fine!
            if (user.clientVersion == ClientVersion.v_1_8){
                if (user.isSprinting() && user.getPlayer().isBlocking()){
                    if (++preVL >= 3) flag(user, "not slowing down while blocking sword.");
                }else preVL = 0;
            }
            //While blind sprinting is impossible but some clients do it.
            if(user.isSprinting() && user.getPlayer().hasPotionEffect(PotionEffectType.BLINDNESS)){
                flag(user,"is sprinting while blinded.");
            }
            //You cant sprint while sneaking on old versions.
            if (user.clientVersion == ClientVersion.v_1_8){
                if(user.isSprinting() && user.isSneaking()){
                    flag(user, "is sprinting while sneaking.");
                }
            }
        }
    }
}
