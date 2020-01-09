package me.apex.hades.listeners;

import me.apex.hades.Hades;
import me.apex.hades.data.UserManager;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerVelocityEvent;

public class VelocityListener implements Listener {

    public VelocityListener()
    {
        Bukkit.getPluginManager().registerEvents(this, Hades.getInstance());
    }

    @EventHandler
    public void onVelocity(PlayerVelocityEvent e)
    {
        if(UserManager.INSTANCE.getUser(e.getPlayer().getUniqueId()) != null)
        {
            UserManager.INSTANCE.getUser(e.getPlayer().getUniqueId()).setLastVelocity(System.currentTimeMillis());
        }
    }

}
