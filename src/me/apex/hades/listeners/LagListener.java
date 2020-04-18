package me.apex.hades.listeners;

import cc.funkemunky.api.events.AtlasListener;
import cc.funkemunky.api.events.Listen;
import cc.funkemunky.api.events.impl.PacketReceiveEvent;
import cc.funkemunky.api.events.impl.PacketSendEvent;
import cc.funkemunky.api.tinyprotocol.api.Packet;
import cc.funkemunky.api.utils.Init;
import me.apex.hades.Hades;
import me.apex.hades.data.User;
import me.apex.hades.data.UserManager;
import me.apex.hades.utils.ChatUtils;
import me.apex.hades.utils.MathUtils;
import me.apex.hades.utils.PacketUtils;
import me.apex.hades.utils.TaskUtils;
import org.bukkit.scheduler.BukkitRunnable;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.util.UUID;

@Init
public class LagListener implements AtlasListener {

    @Listen
    public void onPacketReceived(PacketReceiveEvent e)
    {
        if(UserManager.INSTANCE.getUser(e.getPlayer().getUniqueId()) != null)
        {
            User user = UserManager.INSTANCE.getUser(e.getPlayer().getUniqueId());
            if(PacketUtils.isFlyingPacket(e.getType()))
            {
                double diff = e.getTimeStamp() - user.getLastPacket();
                double value = 50;
                value -= diff;

                if(value < -80)
                {
                    double lastLag = e.getTimeStamp() - user.getLastLagPacket();

                    if(lastLag < 900) {
                        user.setLagging(true);
                        user.setLastLagSet(e.getTimeStamp());
                    } else
                        user.setLagging(false);

                    user.setLastLagPacket(e.getTimeStamp());
                }

                if(e.getTimeStamp() - user.getLastLagSet() > 1000)
                    user.setLagging(false);

                user.setLastPacket(e.getTimeStamp());
                PacketUtils.sendTransaction(user);
            }else if(e.getType().equalsIgnoreCase(Packet.Client.KEEP_ALIVE))
            {
                user.setLastKeepAlive(e.getTimeStamp());
                user.setPing((int)Math.abs(e.getTimeStamp() - user.getLastServerKeepAlive()));
                getConnectionSpeed(user);
                if(user.getPing() > user.getConnectionDelay() + 5)
                {
                    TaskUtils.run(() -> {
                       user.getPlayer().kickPlayer(ChatUtils.color("&4invalid.ping.index{" + user.getPing() + " > " + user.getConnectionDelay() + 20 + "}"));
                    });
                }
            }else if(e.getType().equalsIgnoreCase(Packet.Client.TRANSACTION))
            {
                user.getTransactionQueue().add(e.getTimeStamp());
                if(user.getTransactionQueue().size() == 50)
                {
                    double deviation = MathUtils.getStandardDeviation(user.getTransactionQueue().stream().mapToLong(l -> l).toArray());

                    if(deviation <= 720 && user.isLagging())
                        user.setLagging(false);

                    user.getTransactionQueue().clear();
                }
            }
        }
    }

    @Listen
    public void onPacketSend(PacketSendEvent e)
    {
        if(UserManager.INSTANCE.getUser(e.getPlayer().getUniqueId()) != null)
        {
            User user = UserManager.INSTANCE.getUser(e.getPlayer().getUniqueId());
            if(e.getType().equalsIgnoreCase(Packet.Server.KEEP_ALIVE))
                user.setLastServerKeepAlive(e.getTimeStamp());
        }
    }

    private void getConnectionSpeed(User user)
    {
        try{
            long start = System.currentTimeMillis();

            String[] command = { "cmd.exe", "/C", "ping " + user.getAddress() };
            Process commandProcess = Runtime.getRuntime().exec(command);
            BufferedReader buf = new BufferedReader(new InputStreamReader(commandProcess.getInputStream()));
            new BukkitRunnable()
            {
                @Override
                public void run()
                {
                    try {
                        String readline;
                        if ((readline = buf.readLine()) != null) {
                            if(readline.contains("reply"))
                            {
                                long ping = Math.abs(System.currentTimeMillis() - start);
                                cancel();
                                user.setConnectionDelay((int)ping);
                            }
                        }
                    }catch (Exception ex) { }
                }
            }.runTaskTimer(Hades.getInstance(), 0L, 0L);
        }catch (Exception ex) { }
    }

}
