package me.apex.hades;

import cc.funkemunky.api.Atlas;
import lombok.Getter;
import me.apex.hades.command.api.CommandManager;
import me.apex.hades.command.impl.HadesCommand;
import me.apex.hades.listeners.BukkitListener;
import me.apex.hades.listeners.VelocityListener;
import me.apex.hades.utils.ChatUtils;
import org.bukkit.plugin.java.JavaPlugin;

public class Hades extends JavaPlugin {

    @Getter
    private static Hades instance;

    @Getter
    public String baseCommand = "", basePermission = "";

    public void onEnable()
    {
        //Register Instance
        instance = this;
        saveDefaultConfig();

        //Register Listeners
        new BukkitListener();
        new VelocityListener();

        //Register Atlas
        Atlas.getInstance().initializeScanner(getClass(), this, true, true);

        //Register System Variables
        baseCommand = getConfig().getString("system.base-command");
        basePermission = getConfig().getString("system.base-permission");

        //Register Commands
        registerCommands();
    }

    public void registerCommands()
    {
        CommandManager.INSTANCE.register(new HadesCommand());
    }

    public boolean reloadPlugin()
    {
        try{
            reloadConfig();
            baseCommand = getConfig().getString("system.base-command");
            basePermission = getConfig().getString("system.base-permission");
            CommandManager.INSTANCE.getAdapters().clear();
            registerCommands();
            return true;
        }catch (Exception ex) { ex.printStackTrace(); return false; }
    }

    public String getPrefix()
    {
        return ChatUtils.color(getConfig().getString("lang.prefix"));
    }

}
