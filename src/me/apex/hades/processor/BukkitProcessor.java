package me.apex.hades.processor;

import me.apex.hades.Hades;
import me.apex.hades.data.User;
import me.apex.hades.event.impl.CommandEvent;
import me.apex.hades.event.impl.TeleportEvent;
import me.apex.hades.managers.EventManager;
import me.apex.hades.managers.GuiManager;
import me.apex.hades.managers.UserManager;
import me.apex.hades.utils.ChatUtils;
import me.apex.hades.utils.TextFile;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryOpenEvent;
import org.bukkit.event.player.*;

import java.util.Objects;

public class BukkitProcessor implements Listener {

    public BukkitProcessor() {
        Bukkit.getPluginManager().registerEvents(this, Hades.instance);
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent e) {
        User user = new User(e.getPlayer().getUniqueId());
        UserManager.INSTANCE.users.add(user);


        if (Hades.instance.getConfig().getBoolean("vpn-protection")) VPNProcessor.checkForVPN(user);

        user.data.lastJoin = System.currentTimeMillis();

        try {
            //Get Client IP
            Object handle = user.getPlayer().getClass().getMethod("getHandle").invoke(user.getPlayer());
            Object playerConnection = handle.getClass().getField("playerConnection").get(handle);
            Object networkManager = playerConnection.getClass().getField("networkManager").get(playerConnection);
            Object address = user.getPlayer().getClass().getMethod("getAddress").invoke(user.getPlayer());
            user.data.ip = address.toString();
        } catch (Exception x) {
            user.getPlayer().kickPlayer(ChatUtils.color("&cFailed to secure connection!"));
            x.printStackTrace();
        }

        //Register Log File
        user.data.logFile = new TextFile("" + e.getPlayer().getUniqueId(), "\\logs");

        //Check for Cracked Vape
        user.getPlayer().sendMessage("§1 §6 §2 §3 §9 §9 §1 §3 §9");
        user.getPlayer().sendMessage("§8 §8 §1 §3 §3 §7 §8 ");
    }

    @EventHandler
    public void onKick(PlayerKickEvent e) {
        UserManager.INSTANCE.users.remove(UserManager.INSTANCE.getUser(e.getPlayer().getUniqueId()));
    }

    @EventHandler
    public void onQuit(PlayerQuitEvent e) {
        UserManager.INSTANCE.users.remove(UserManager.INSTANCE.getUser(e.getPlayer().getUniqueId()));
    }

    @EventHandler
    public void onDamage(EntityDamageEvent e) {
        if (e.getEntity() instanceof Player) {
            Player p = (Player) e.getEntity();
            User user = UserManager.INSTANCE.getUser(p.getUniqueId());
            if (user != null) {
                if (e.getCause() == EntityDamageEvent.DamageCause.ENTITY_ATTACK || e.getCause() == EntityDamageEvent.DamageCause.BLOCK_EXPLOSION || e.getCause() == EntityDamageEvent.DamageCause.ENTITY_EXPLOSION) {
                    user.data.lastHit = System.currentTimeMillis();
                }
            }
        }
    }

    @EventHandler
    public void onOpen(InventoryOpenEvent e) {
        User user = UserManager.INSTANCE.getUser(e.getPlayer().getUniqueId());
        if (user != null) {
            user.data.inventoryOpen = true;
        }
    }

    @EventHandler
    public void onClose(InventoryCloseEvent e) {
        User user = UserManager.INSTANCE.getUser(e.getPlayer().getUniqueId());
        if (user != null) {
            user.data.inventoryOpen = false;
        }
    }

    @EventHandler
    public void onCommand(PlayerCommandPreprocessEvent e) {
        CommandEvent commandEvent = new CommandEvent(e.getMessage());
        EventManager.INSTANCE.call(commandEvent, Objects.requireNonNull(UserManager.INSTANCE.getUser(e.getPlayer().getUniqueId())));

        if (commandEvent.isCancelled())
            e.setCancelled(true);
    }

    @EventHandler
    public void onInventoryClick(InventoryClickEvent e) {
        User user = UserManager.INSTANCE.getUser(e.getWhoClicked().getUniqueId());
        if (GuiManager.INSTANCE.handleClick(user, e.getInventory(), e.getCurrentItem()))
            e.setCancelled(true);
    }

    @EventHandler
    public void onTeleport(PlayerTeleportEvent e) {
        User user = UserManager.INSTANCE.getUser(e.getPlayer().getUniqueId());
        TeleportEvent teleportEvent = new TeleportEvent(e.getCause());
        EventManager.INSTANCE.call(teleportEvent, user);
    }
}
