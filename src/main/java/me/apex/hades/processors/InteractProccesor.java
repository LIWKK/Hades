package me.apex.hades.processors;

import me.apex.hades.Hades;
import me.apex.hades.objects.User;
import me.apex.hades.objects.UserManager;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;

//Couldn't put it in the movement processor so had to do this.
public enum InteractProccesor implements Listener {
    INSTANCE;
    InteractProccesor(){ Bukkit.getServer().getPluginManager().registerEvents(this, Hades.getInstance()); }

    @EventHandler
    public void onInteract(PlayerInteractEvent event){
        User user = UserManager.INSTANCE.getUser(event.getPlayer().getUniqueId());
        if (event.getAction() == Action.RIGHT_CLICK_AIR || event.getAction() == Action.RIGHT_CLICK_BLOCK){
            user.setRightClicking(true);
        }else if(event.getAction() != Action.RIGHT_CLICK_AIR && event.getAction() == Action.RIGHT_CLICK_BLOCK){
            user.setRightClicking(false);
        }
    }
}
