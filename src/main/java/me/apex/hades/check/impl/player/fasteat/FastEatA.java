package me.apex.hades.check.impl.player.fasteat;

import io.github.retrooper.packetevents.event.impl.PacketReceiveEvent;
import me.apex.hades.Hades;
import me.apex.hades.check.api.Check;
import me.apex.hades.check.api.CheckInfo;
import me.apex.hades.objects.User;
import me.apex.hades.objects.UserManager;
import me.apex.hades.utils.ItemUtils;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerItemConsumeEvent;

import java.util.HashMap;

@CheckInfo(name = "FastEat", type = "A")
public class FastEatA extends Check implements Listener {

    public FastEatA(){
        Bukkit.getServer().getPluginManager().registerEvents(this, Hades.getInstance());
    }
    @Override
    public void onPacket(PacketReceiveEvent e, User user) { }

    private final HashMap<String, Long> startEat = new HashMap<String, Long>();

    public void handle(Player p){
        this.startEat.put(p.getPlayer().getName(), System.currentTimeMillis());
    }

    public void checkFastEat(Player player, PlayerItemConsumeEvent event){
        if (!enabled) return;
        if (this.startEat.containsKey(player.getName())) {
            long diff = (System.currentTimeMillis() - this.startEat.get(player.getName()));
            User user = UserManager.INSTANCE.getUser(player.getUniqueId());
            if (diff <= 1200) {
                flag(user, "ate food faster! Time taken to eat: " + diff);
                if(shouldMitigate()) event.setCancelled(true);
            }
        }
    }

    @EventHandler
    public void onConsume(PlayerItemConsumeEvent event){
        checkFastEat(event.getPlayer(), event);
    }

    @EventHandler
    public void onInteract(PlayerInteractEvent event) {
        if (event.hasItem()) {
            if (event.getAction() == Action.RIGHT_CLICK_AIR || event.getAction() == Action.RIGHT_CLICK_BLOCK) {
                Material m = event.getItem().getType();
                if (ItemUtils.isFood(m)) {
                    handle(event.getPlayer());
                }
            }
        }
    }
}
