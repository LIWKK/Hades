package me.apex.hades.listeners;

import cc.funkemunky.api.events.AtlasListener;
import cc.funkemunky.api.events.Listen;
import cc.funkemunky.api.events.impl.PacketReceiveEvent;
import cc.funkemunky.api.events.impl.PacketSendEvent;
import cc.funkemunky.api.tinyprotocol.api.Packet;
import cc.funkemunky.api.utils.Init;
import me.apex.hades.data.User;
import me.apex.hades.data.UserManager;
import me.apex.hades.processors.MovementProcessor;

@Init
public class PacketListener implements AtlasListener {

    @Listen
    public void onPacketReceived(PacketReceiveEvent e)
    {
        if(UserManager.INSTANCE.getUser(e.getPlayer().getUniqueId()) != null)
        {
            User user = UserManager.INSTANCE.getUser(e.getPlayer().getUniqueId());

            //Process Movement
            MovementProcessor.INSTANCE.processMovement(e, user);

            //Check User
            if(e.getPlayer().isDead()) return;
            if(user == null || user.getPlayer() == null) return;
            if(e.getTimeStamp() - user.getLastJoin() < 2000) return;

            //Call Checks
            user.getChecks().stream().filter(check -> check.isEnabled()).forEach(check -> check.onPacket(e, user));
        }
    }

    @Listen
    public void onPacketSend(PacketSendEvent e)
    {
        if(UserManager.INSTANCE.getUser(e.getPlayer().getUniqueId()) != null)
        {
            User user = UserManager.INSTANCE.getUser(e.getPlayer().getUniqueId());
            if(e.getType().equalsIgnoreCase(Packet.Server.POSITION))
                user.setLastServerPosition(e.getTimeStamp());
        }
    }

}
