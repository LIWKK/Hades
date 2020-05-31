package me.apex.hades.listeners;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import me.apex.hades.Hades;
import me.apex.hades.command.api.CommandManager;
import me.apex.hades.command.api.UserInput;
import me.apex.hades.menu.api.GuiManager;
import me.apex.hades.objects.User;
import me.apex.hades.objects.UserManager;
import me.apex.hades.processors.VPNProcessor;
import me.purplex.packetevents.event.handler.PacketHandler;
import me.purplex.packetevents.event.impl.PlayerInjectEvent;
import me.purplex.packetevents.event.impl.PlayerUninjectEvent;
import me.purplex.packetevents.event.listener.PacketListener;

public class HadesListener implements Listener, PacketListener {

    public HadesListener() {
        Bukkit.getPluginManager().registerEvents(this, Hades.getInstance());
    }

    @PacketHandler
    public void onInject(PlayerInjectEvent e) {
        String address = e.getPlayer().getAddress().toString();

        User user = new User(e.getPlayer().getUniqueId(), address);

        user.setLastJoin((System.nanoTime() / 1000000));

        UserManager.INSTANCE.register(user);

        if (Hades.getInstance().getConfig().getBoolean("anti-vpn.enabled")) {
            if (VPNProcessor.INSTANCE.ProcessVPN(user)) {
                Bukkit.dispatchCommand(Bukkit.getConsoleSender(), Hades.getInstance().getConfig().getString("anti-vpn.punish-command").replace("%player%", user.getPlayer().getName()));
            }
        }
    }

    @PacketHandler
    public void onUninject(PlayerUninjectEvent e) {
        User user = UserManager.INSTANCE.getUser(e.getPlayer().getUniqueId());
        UserManager.INSTANCE.unregister(user);
    }

    @EventHandler
    public void onCommand(PlayerCommandPreprocessEvent e) {
        User user = UserManager.INSTANCE.getUser(e.getPlayer().getUniqueId());
        if (e.getMessage().contains(" ")) {
            String[] command = e.getMessage().split(" ");
            String name = command[0].replace("/", "");
            String[] args = new String[command.length - 1];
            for (int i = 0; i < command.length - 1; i++) {
                args[i] = command[i + 1];
            }
            if (CommandManager.INSTANCE.handleCommand(user, new UserInput() {
                @Override
                public String label() {
                    return name;
                }

                @Override
                public String[] args() {
                    return args;
                }
            })) e.setCancelled(true);
        } else {
            String name = e.getMessage().replace("/", "");
            if (CommandManager.INSTANCE.handleCommand(user, new UserInput() {
                @Override
                public String label() {
                    return name;
                }

                @Override
                public String[] args() {
                    return new String[0];
                }
            })) e.setCancelled(true);
        }
    }

    @EventHandler
    public void onClick(InventoryClickEvent e) {
        if (e.getCurrentItem() == null || e.getCurrentItem().getType() == Material.AIR || !e.getCurrentItem().hasItemMeta())
            return;
        if (GuiManager.INSTANCE.onClick((Player) e.getWhoClicked(), e.getCurrentItem(), e.getClickedInventory()))
            e.setCancelled(true);
    }

}
