package me.apex.hades.processor.impl;

import me.apex.hades.HadesPlugin;
import me.apex.hades.user.User;
import me.apex.hades.user.UserManager;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;

public class InteractProcessor implements Listener {
    public InteractProcessor() { Bukkit.getPluginManager().registerEvents(this, HadesPlugin.instance);}

    @EventHandler
    public void onInteract(PlayerInteractEvent event){
        User user = UserManager.getUser(event.getPlayer());

        if (event.getAction() == Action.LEFT_CLICK_AIR){
            user.leftClickingAir = true;
        }else user.leftClickingAir = false;
        if (event.getAction() == Action.LEFT_CLICK_BLOCK){
            user.leftClickingBlock = true;
            user.interactedBlock = event.getClickedBlock();
        }else user.leftClickingBlock = false;
        if (event.getAction() == Action.RIGHT_CLICK_AIR){
            user.rightClickingAir = true;
        }else user.rightClickingAir = false;
        if (event.getAction() == Action.RIGHT_CLICK_BLOCK){
            user.rightClickingBlock = true;
            user.interactedBlock = event.getClickedBlock();
        }else user.rightClickingBlock = false;
    }
}
