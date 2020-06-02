package me.apex.hades;

import org.bukkit.plugin.java.JavaPlugin;

import me.apex.hades.command.api.CommandManager;
import me.apex.hades.command.impl.HadesCommand;
import me.apex.hades.listeners.HadesListener;
import me.apex.hades.listeners.LagListener;
import me.apex.hades.listeners.NetworkListener;
import me.apex.hades.listeners.VelocityListener;
import me.apex.hades.menu.api.GuiManager;
import me.apex.hades.menu.impl.HomeMenu;
import me.apex.hades.objects.UserManager;
import me.apex.hades.utils.ChatUtils;
import me.purplex.packetevents.PacketEvents;

public class Hades extends JavaPlugin {
    private static Hades instance;

    public String baseCommand = "", basePermission = "";

    public void onEnable() {
        //Register Instance
        instance = this;
        saveDefaultConfig();
        
        //Register Listeners
        new VelocityListener();

        //Register Network
        PacketEvents.setup(this, false);
        PacketEvents.getEventManager().registerListener(new LagListener());
        PacketEvents.getEventManager().registerListener(new HadesListener());
        PacketEvents.getEventManager().registerListener(new NetworkListener());

        //Register System Variables
        baseCommand = getConfig().getString("system.base-command");
        basePermission = getConfig().getString("system.base-permission");

        //Register Commands
        registerCommands();

        //Register Menus
        GuiManager.INSTANCE.registerGui(new HomeMenu());
    }

    public void registerCommands() {
        CommandManager.INSTANCE.register(new HadesCommand());
    }

    public boolean reloadPlugin() {
        try {
            reloadConfig();
            baseCommand = getConfig().getString("system.base-command");
            basePermission = getConfig().getString("system.base-permission");
            CommandManager.INSTANCE.getAdapters().clear();
            registerCommands();
            return true;
        } catch (Exception ex) {
            ex.printStackTrace();
            return false;
        }
    }

    public String getPrefix() {
        return ChatUtils.color(getConfig().getString("lang.prefix"));
    }

    public static Hades getInstance() {
        return instance;
    }

    public String getBaseCommand() {
        return baseCommand;
    }

    public String getBasePermission() {
        return basePermission;
    }

    @Override
    public void onDisable() {
        UserManager.INSTANCE.getUsers().clear();
        PacketEvents.cleanup();
    }
}
