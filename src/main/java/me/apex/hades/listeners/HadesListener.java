package me.apex.hades.listeners;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;

import io.github.retrooper.packetevents.event.PacketHandler;
import io.github.retrooper.packetevents.event.PacketListener;
import io.github.retrooper.packetevents.event.impl.PlayerInjectEvent;
import io.github.retrooper.packetevents.event.impl.PlayerUninjectEvent;
import me.apex.hades.Hades;
import me.apex.hades.command.api.CommandManager;
import me.apex.hades.command.api.UserInput;
import me.apex.hades.menu.api.GuiManager;
import me.apex.hades.objects.User;
import me.apex.hades.objects.UserManager;
import me.apex.hades.processors.VPNProcessor;
import org.bukkit.event.player.PlayerJoinEvent;

public class HadesListener implements Listener, PacketListener {

    public HadesListener() {
        Bukkit.getPluginManager().registerEvents(this, Hades.getInstance());
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent e) {
        User user = new User(e.getPlayer().getUniqueId());

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
    public void onClick(InventoryClickEvent e) {
        if (e.getCurrentItem() == null || e.getCurrentItem().getType() == Material.AIR || !e.getCurrentItem().hasItemMeta())
            return;
        if (GuiManager.INSTANCE.onClick((Player) e.getWhoClicked(), e.getCurrentItem(), e.getClickedInventory()))
            e.setCancelled(true);
    }

}
