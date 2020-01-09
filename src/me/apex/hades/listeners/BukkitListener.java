package me.apex.hades.listeners;

import me.apex.hades.Hades;
import me.apex.hades.command.api.CommandManager;
import me.apex.hades.command.api.UserInput;
import me.apex.hades.data.User;
import me.apex.hades.data.UserManager;
import me.apex.hades.listeners.event.HadesFlagEvent;
import me.apex.hades.utils.ChatUtils;
import me.apex.hades.utils.LogUtils;
import me.apex.hades.utils.TaskUtils;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class BukkitListener implements Listener {

    public BukkitListener()
    {
        Bukkit.getPluginManager().registerEvents(this, Hades.getInstance());
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent e)
    {
        String address = "";
        try {
            Object handle = e.getPlayer().getClass().getMethod("getHandle").invoke(e.getPlayer());
            Object playerConnection = handle.getClass().getField("playerConnection").get(handle);
            Object networkManager = playerConnection.getClass().getField("networkManager").get(playerConnection);
            Object ip = e.getPlayer().getClass().getMethod("getAddress").invoke(e.getPlayer());
            address = ip.toString().split(":")[0].replace("/", "");
        } catch (Exception x) {
            address = "N/A";
        }

        User user = new User(e.getPlayer().getUniqueId(), address);

        user.setLastJoin(System.currentTimeMillis());

        UserManager.INSTANCE.register(user);
    }

    @EventHandler
    public void onQuit(PlayerQuitEvent e)
    {
        User user = UserManager.INSTANCE.getUser(e.getPlayer().getUniqueId());
        UserManager.INSTANCE.unregister(user);
    }

    @EventHandler
    public void onFlag(HadesFlagEvent e)
    {
        User user = UserManager.INSTANCE.getUser(e.getPlayer().getUniqueId());
        ChatUtils.informStaff(Hades.getInstance().getConfig().getString("lang.flag-format").replace("%prefix%", Hades.getInstance().getPrefix()).replace("%player%", user.getPlayer().getName()).replace("%check%", e.getCheck().getName().split(" ")[0]).replace("%checktype%", e.getCheck().getName().split("\\(")[1].replace(")", "") + (e.getCheck().isExperimental() ? Hades.getInstance().getConfig().getString("lang.experimental-notation") : "")).replace("%vl%", String.valueOf(e.getCheck().getViolations().size())).replace("%info%", e.getInformation()), e.getCheck().getViolations().size());
        if(Hades.getInstance().getConfig().getBoolean("system.logging.file.enabled"))
            LogUtils.logToFile(user.getLogFile(), Hades.getInstance().getConfig().getString("system.logging.log-format").replace("%player%", user.getPlayer().getName()).replace("%check%", e.getCheck().getName().split(" ")[0]).replace("%checktype%", e.getCheck().getName().split("\\(")[1].replace(")", "") + (e.getCheck().isExperimental() ? Hades.getInstance().getConfig().getString("lang.experimental-notation") : "")).replace("%vl%", String.valueOf(e.getCheck().getViolations())).replace("%info%", e.getInformation()));

        if(e.getCheck().getViolations().size() == e.getCheck().getMaxViolations())
        {
            if(e.getCheck().isPunishable())
            {
                if(Hades.getInstance().getConfig().getBoolean("system.logging.file.enabled"))
                    LogUtils.logToFile(user.getLogFile(), "%time% > [ACTION] " + Hades.getInstance().getConfig().getString("checks.punish-command").replace("%player%", user.getPlayer().getName()).replace("%check%", e.getCheck().getName().split(" ")[0]).replace("%checktype%", e.getCheck().getName().split("\\(")[1]) + (e.getCheck().isExperimental() ? Hades.getInstance().getConfig().getString("lang.experimental-notation") : ""));
                if(Hades.getInstance().getConfig().getBoolean("checks.broadcast-punishments"))
                    Bukkit.broadcastMessage(ChatUtils.color(Hades.getInstance().getConfig().getString("lang.broadcast-message").replace("%prefix%", Hades.getInstance().getPrefix()).replace("%player%", user.getPlayer().getName()).replace("%check%", e.getCheck().getName().split(" ")[0]).replace("%newline%", "\n")));
                TaskUtils.run(() -> {
                    Bukkit.dispatchCommand(Bukkit.getConsoleSender(), Hades.getInstance().getConfig().getString("checks.punish-command").replace("%prefix%", Hades.getInstance().getPrefix()).replace("%player%", user.getPlayer().getName()).replace("%check%", e.getCheck().getName().split(" ")[0]).replace("%checktype%", e.getCheck().getName().split("\\(")[1]) + (e.getCheck().isExperimental() ? Hades.getInstance().getConfig().getString("lang.experimental-notation") : ""));
                });
            }
        }
    }

    @EventHandler
    public void onCommand(PlayerCommandPreprocessEvent e)
    {
        User user = UserManager.INSTANCE.getUser(e.getPlayer().getUniqueId());
        if(e.getMessage().contains(" "))
        {
            String[] command = e.getMessage().split(" ");
            String name = command[0].replace("/", "");
            String[] args = new String[command.length - 1];
            for(int i = 0; i < command.length - 1; i++)
            {
                args[i] = command[i + 1];
            }
            if(CommandManager.INSTANCE.handleCommand(user, new UserInput() {
                @Override
                public String label() {
                    return name;
                }

                @Override
                public String[] args() {
                    return args;
                }
            })) e.setCancelled(true);
        }else
        {
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

}
