package me.apex.hades.processor.impl;

import me.apex.hades.HadesPlugin;
import me.apex.hades.user.User;
import me.apex.hades.user.UserManager;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerVelocityEvent;

public class VelocityListener implements Listener {
    public VelocityListener() {
        Bukkit.getPluginManager().registerEvents(this, HadesPlugin.instance);
    }

    @EventHandler
    public void onVelocity(PlayerVelocityEvent event){
        User user = UserManager.getUser(event.getPlayer());
        user.lastVelocity = (System.nanoTime() / 1000000);

        user.velocityX = event.getVelocity().getX();
        user.velocityY = event.getVelocity().getY();
        user.velocityZ = event.getVelocity().getZ();

        user.velocityTick = user.tick;
    }
}
