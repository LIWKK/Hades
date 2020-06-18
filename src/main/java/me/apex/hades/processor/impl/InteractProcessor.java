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
    public InteractProcessor() { Bukkit.getPluginManager().registerEvents(this, HadesPlugin.getInstance());}

    @EventHandler
    public void onInteract(PlayerInteractEvent event){
        User user = UserManager.getUser(event.getPlayer());

        if (event.getAction() == Action.LEFT_CLICK_AIR){
            user.setLeftClickingAir(true);
        }else user.setLeftClickingAir(false);
        if (event.getAction() == Action.LEFT_CLICK_BLOCK){
            user.setLeftClickingBlock(true);
            user.setInteractedBlock(event.getClickedBlock());
        }else user.setLeftClickingBlock(false);
        if (event.getAction() == Action.RIGHT_CLICK_AIR){
            user.setRightClickingAir(true);
        }else user.setRightClickingAir(false);
        if (event.getAction() == Action.RIGHT_CLICK_BLOCK){
            user.setRightClickingBlock(true);
            user.setInteractedBlock(event.getClickedBlock());
        }else user.setRightClickingBlock(true);
    }
}
