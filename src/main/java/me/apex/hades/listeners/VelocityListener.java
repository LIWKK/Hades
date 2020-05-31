package me.apex.hades.listeners;

import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerVelocityEvent;

import me.apex.hades.Hades;
import me.apex.hades.objects.User;
import me.apex.hades.objects.UserManager;

public class VelocityListener implements Listener {

    public VelocityListener() {
        Bukkit.getPluginManager().registerEvents(this, Hades.getInstance());
    }

    @EventHandler
    public void onVelocity(PlayerVelocityEvent e) {
        User user = UserManager.INSTANCE.getUser(e.getPlayer().getUniqueId());

        assert user != null;
        assert e.getPlayer() != null;
        user.setVelocityTicks(0);
        user.setLastVelX(e.getVelocity().getX());
        user.setLastVelY(e.getVelocity().getY());
        user.setLastVelZ(e.getVelocity().getZ());
    }
}
