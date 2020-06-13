package me.apex.hades.check.impl.movement.scaffold;

import io.github.retrooper.packetevents.event.impl.PacketReceiveEvent;
import me.apex.hades.Hades;
import me.apex.hades.check.api.Check;
import me.apex.hades.check.api.CheckInfo;
import me.apex.hades.objects.User;
import me.apex.hades.objects.UserManager;
import me.apex.hades.utils.PlayerUtils;
import org.bukkit.Bukkit;
import org.bukkit.block.Block;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;

@CheckInfo(name = "Scaffold", type = "A")
public class ScaffoldA extends Check implements Listener {
    public ScaffoldA() {
        Bukkit.getPluginManager().registerEvents(this, Hades.getInstance());
    }
    int preVL = 0;
    @Override
    public void onPacket(PacketReceiveEvent e, User user) { }

    @EventHandler
    public void onPlace(BlockPlaceEvent event){
        Block block = event.getBlockPlaced();
        User user = UserManager.INSTANCE.getUser(event.getPlayer().getUniqueId());

        if (user.isSneaking()
                || !PlayerUtils.isOnGround(user.getPlayer())
                || user.getDeltaXZ() < .2) return;

        if (user.getLocation().subtract(0, 1, 0).getBlock().getType().isSolid()
                && !user.getLocation().subtract(0, 2, 0).getBlock().getType().isSolid() && user.getLocation().getBlockY() > block.getY()) {
            if (preVL++ >= 2){
                flag(user, "using scaffold.");
                if (shouldMitigate()){
                    lagBack(user);
                    event.setCancelled(true);
                }
            }
        }
    }
}
