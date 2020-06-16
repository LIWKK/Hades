package me.apex.hades.listener;

import me.apex.hades.event.impl.bukkitevents.InteractEvent;
import me.apex.hades.event.impl.bukkitevents.ItemConsumeEvent;
import me.apex.hades.event.impl.bukkitevents.MoveEvent;
import me.apex.hades.event.impl.bukkitevents.TeleportEvent;
import me.apex.hades.user.User;
import me.apex.hades.user.UserManager;
import me.apex.hades.util.TaskUtil;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.*;

public class BukkitListener implements Listener {

    @EventHandler
    public void onJoin(PlayerJoinEvent e) {
        UserManager.users.add(new User(e.getPlayer()));
    }

    @EventHandler
    public void onQuit(PlayerQuitEvent e) {
        UserManager.users.remove(UserManager.getUser(e.getPlayer()));
    }

    @EventHandler
    public void onMove(PlayerMoveEvent e) {
        User user = UserManager.getUser(e.getPlayer());
        if (user != null) {
            TaskUtil.runAsync(() -> {
                user.checks.stream().filter(check -> check.enabled).forEach(check -> check.onEvent(new MoveEvent(e.getTo(), e.getFrom()), user));
            });
        }
    }

    @EventHandler
    public void onTeleport(PlayerTeleportEvent e) {
        User user = UserManager.getUser(e.getPlayer());
        if (user != null) {
            TaskUtil.runAsync(() -> {
                user.checks.stream().filter(check -> check.enabled).forEach(check -> check.onEvent(new TeleportEvent(e.getTo(), e.getFrom(), e.getCause()), user));
            });
        }
    }

    @EventHandler
    public void onInteract(PlayerInteractEvent e) {
        User user = UserManager.getUser(e.getPlayer());
        if (user != null) {
            TaskUtil.runAsync(() -> {
                user.checks.stream().filter(check -> check.enabled).forEach(check -> check.onEvent(new InteractEvent(e.getAction(), e.getItem(), e.getClickedBlock(), e.getBlockFace(), e.useItemInHand(), e.useInteractedBlock()), user));
            });
        }
    }

    @EventHandler
    public void onConsume(PlayerItemConsumeEvent e) {
        User user = UserManager.getUser(e.getPlayer());
        if (user != null) {
            TaskUtil.runAsync(() -> {
                user.checks.stream().filter(check -> check.enabled).forEach(check -> check.onEvent(new ItemConsumeEvent(e.getItem()), user));
            });
        }
    }

}
