package me.apex.hades.listener;

import me.apex.hades.HadesPlugin;
import me.apex.hades.event.impl.bukkitevents.InteractEvent;
import me.apex.hades.event.impl.bukkitevents.ItemConsumeEvent;
import me.apex.hades.user.User;
import me.apex.hades.user.UserManager;
import me.apex.hades.util.TaskUtil;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerItemConsumeEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

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
    public void onInteract(PlayerInteractEvent e) {
        User user = UserManager.getUser(e.getPlayer());
        if (user != null) {
            TaskUtil.taskAsync(() -> {
                user.getExecutorService().execute(() -> user.getChecks().stream().filter(check -> check.enabled).forEach(check -> check.onHandle(new InteractEvent(e.getAction(), e.getItem(), e.getClickedBlock(), e.getBlockFace(), e.useItemInHand(), e.useInteractedBlock()), user)));
            }, HadesPlugin.getInstance());
        }
    }

    @EventHandler
    public void onConsume(PlayerItemConsumeEvent e) {
        User user = UserManager.getUser(e.getPlayer());
        if (user != null) {
            TaskUtil.taskAsync(() -> {
                user.getExecutorService().execute(() -> user.getChecks().stream().filter(check -> check.enabled).forEach(check -> check.onHandle(new ItemConsumeEvent(e.getItem()), user)));
            }, HadesPlugin.getInstance());
        }
    }

}
