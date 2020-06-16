package me.apex.hades;

import io.github.retrooper.packetevents.PacketEvents;
import me.apex.hades.listener.BukkitListener;
import me.apex.hades.listener.NetworkListener;
import me.apex.hades.util.ChatUtil;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public class HadesPlugin extends JavaPlugin {
    public static HadesPlugin instance;

    @Override
    public void onEnable() {
        //Register Instance
        instance = this;

        //Save Config
        saveDefaultConfig();

        //Start PacketEvents
        PacketEvents.start(this);

        //Register Listeners
        Bukkit.getPluginManager().registerEvents(new BukkitListener(), this);
        PacketEvents.getEventManager().registerListener(new NetworkListener());
    }

    @Override
    public void onDisable() {
        PacketEvents.stop();
    }

    public static String getPrefix(){
        return ChatUtil.color("&8[&c&lHades&8] &7");
    }
}
