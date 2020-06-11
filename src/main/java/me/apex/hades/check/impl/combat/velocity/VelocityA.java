package me.apex.hades.check.impl.combat.velocity;

import io.github.retrooper.packetevents.enums.EntityUseAction;
import io.github.retrooper.packetevents.event.impl.PacketReceiveEvent;
import io.github.retrooper.packetevents.packet.Packet;
import io.github.retrooper.packetevents.packetwrappers.in.useentity.WrappedPacketInUseEntity;
import me.apex.hades.Hades;
import me.apex.hades.check.api.Check;
import me.apex.hades.check.api.CheckInfo;
import me.apex.hades.objects.User;
import me.apex.hades.objects.UserManager;
import me.apex.hades.utils.PlayerUtils;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

@CheckInfo(name = "Velocity", type = "A")
public class VelocityA extends Check {
    @Override
    public void onPacket(PacketReceiveEvent e, User user) {
        if (e.getPacketName().equalsIgnoreCase(Packet.Client.USE_ENTITY)) {
            WrappedPacketInUseEntity packet = new WrappedPacketInUseEntity(e.getPacket());
            if (packet.getAction() == EntityUseAction.ATTACK && packet.getEntity() instanceof Player){
                Player entity = (Player)packet.getEntity();
                Location lastLocation = entity.getLocation();
                if(entity.getGameMode() != GameMode.SURVIVAL || e.isCancelled())return;
                new BukkitRunnable(){
                    @Override
                    public void run() {
                        if (!entity.isOnline() || e.isCancelled())return;
                        if (lastLocation.distance(entity.getLocation()) < 0.100 && !PlayerUtils.hasBlocksAround(entity.getLocation()) && !PlayerUtils.hasBlocksAround(entity.getLocation().add(0,1,0))){
                            flag(UserManager.INSTANCE.getUser(entity.getUniqueId()), "taken less knockback! Knockback distance:" + lastLocation.distance(entity.getLocation()));
                        }
                    }
                }.runTaskLater(Hades.getInstance(), 5);
            }
        }
    }

}
