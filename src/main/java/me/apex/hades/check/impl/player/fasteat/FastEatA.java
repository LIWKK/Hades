package me.apex.hades.check.impl.player.fasteat;

import io.github.retrooper.packetevents.event.PacketEvent;
import me.apex.hades.HadesPlugin;
import me.apex.hades.check.Check;
import me.apex.hades.check.CheckInfo;
import me.apex.hades.user.User;
import me.apex.hades.user.UserManager;
import me.apex.hades.util.MiscUtil;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerItemConsumeEvent;

import java.util.HashMap;

//Add interact + Consume packet events for this check
@CheckInfo(name = "FastEat", type = "A")
public class FastEatA extends Check implements Listener {
    public FastEatA() {
        Bukkit.getPluginManager().registerEvents(this, HadesPlugin.instance);
    }

    @Override
    public void onEvent(PacketEvent e, User user) { }

    private HashMap<String, Long> startEat = new HashMap<>();

    @EventHandler
    public void onConsume(PlayerItemConsumeEvent event){
        if (this.startEat.containsKey(event.getPlayer().getName())) {
            long diff = (time() - this.startEat.get(event.getPlayer().getName()));
            User user = UserManager.getUser(event.getPlayer());
            if (diff <= 1200) {
                flag(user, "ate food faster! Time taken to eat: " + diff);
            }
        }
    }

    @EventHandler
    public void onInteract(PlayerInteractEvent event) {
        if (event.hasItem()) {
            if (event.getAction() == Action.RIGHT_CLICK_AIR || event.getAction() == Action.RIGHT_CLICK_BLOCK) {
                Material m = event.getItem().getType();
                if (MiscUtil.isFood(m)) {
                    this.startEat.put(event.getPlayer().getName(), time());
                }
            }
        }
    }
}
