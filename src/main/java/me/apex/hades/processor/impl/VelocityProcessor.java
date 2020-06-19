package me.apex.hades.processor.impl;

import me.apex.hades.HadesPlugin;
import me.apex.hades.user.User;
import me.apex.hades.user.UserManager;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerVelocityEvent;

public class VelocityProcessor implements Listener {
    public VelocityProcessor(){
        Bukkit.getServer().getPluginManager().registerEvents(this, HadesPlugin.getInstance());
    }

    @EventHandler
    public void onVelocity(PlayerVelocityEvent event){
        User user = UserManager.getUser(event.getPlayer());
        if (user != null){
            user.setLastVelocity(System.nanoTime() / 1000000);
        }
    }
}
