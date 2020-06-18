package me.apex.hades;

import io.github.retrooper.packetevents.PacketEvents;
import lombok.Getter;
import me.apex.hades.listener.BukkitListener;
import me.apex.hades.listener.NetworkListener;
import me.apex.hades.util.MathUtil;
import me.apex.hades.util.reflection.VersionUtil;
import me.apex.hades.util.text.ChatUtil;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

@Getter
public class HadesPlugin extends JavaPlugin {
    private static HadesPlugin instance;

    public static HadesPlugin getInstance() {
        return instance;
    }

    private VersionUtil versionUtil;
    public static String bukkitVersion;

    private ScheduledExecutorService executorService;

    @Override
    public void onEnable() {
        //Register Instance
        instance = this;

        //Version
        bukkitVersion = Bukkit.getServer().getClass().getPackage().getName().substring(23);

        //Register Fields
        executorService = Executors.newSingleThreadScheduledExecutor();

        //Utils
        new MathUtil();
        versionUtil = new VersionUtil();

        //Save Config
        saveDefaultConfig();

        //Register Listeners
        Bukkit.getPluginManager().registerEvents(new BukkitListener(), this);

        //Register PacketEvents
        PacketEvents.start(this);
        PacketEvents.getEventManager().registerListener(new NetworkListener());
    }

    @Override
    public void onDisable() {
        PacketEvents.stop();
        executorService.shutdownNow();
    }

    public static String getPrefix(){
        return ChatUtil.color(instance.getConfig().getString("lang.prefix"));
    }
}
