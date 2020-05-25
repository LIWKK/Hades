package me.apex.hades.listeners;

import me.apex.hades.Hades;
import me.apex.hades.objects.UserManager;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerVelocityEvent;

public class VelocityListener implements Listener {

    public VelocityListener() {
        Bukkit.getPluginManager().registerEvents(this, Hades.getInstance());
    }

    @EventHandler
    public void onVelocity(PlayerVelocityEvent e) {
        if (UserManager.INSTANCE.getUser(e.getPlayer().getUniqueId()) != null) {
            UserManager.INSTANCE.getUser(e.getPlayer().getUniqueId()).setLastVelocity(System.currentTimeMillis());
            UserManager.INSTANCE.getUser(e.getPlayer().getUniqueId()).setLastVelX(e.getVelocity().getX());
            UserManager.INSTANCE.getUser(e.getPlayer().getUniqueId()).setLastVelY(e.getVelocity().getY());
            UserManager.INSTANCE.getUser(e.getPlayer().getUniqueId()).setLastVelZ(e.getVelocity().getZ());
        }
    }

}
