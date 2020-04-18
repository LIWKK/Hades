package me.apex.hades.processor;

import cc.funkemunky.api.events.AtlasListener;
import cc.funkemunky.api.events.Listen;
import cc.funkemunky.api.events.impl.PacketReceiveEvent;
import cc.funkemunky.api.events.impl.PacketSendEvent;
import cc.funkemunky.api.tinyprotocol.api.Packet;
import cc.funkemunky.api.tinyprotocol.packet.out.WrappedOutVelocityPacket;
import cc.funkemunky.api.utils.Init;
import me.apex.hades.data.User;
import me.apex.hades.event.impl.PacketEvent;
import me.apex.hades.managers.EventManager;
import me.apex.hades.managers.UserManager;
import me.apex.hades.utils.PacketUtils;

@Init
public class PacketProcessor implements AtlasListener {

    @Listen
    public void onPacketReceived(PacketReceiveEvent e)
    {
        User user = UserManager.INSTANCE.getUser(e.getPlayer().getUniqueId());
        PacketUtils.updateData(e, user);
        EventManager.INSTANCE.call(new PacketEvent(e), user);
    }

    @Listen
    public void onPacketSend(PacketSendEvent e)
    {
        User user = UserManager.INSTANCE.getUser(e.getPlayer().getUniqueId());
        if(e.getType() == Packet.Server.POSITION)
        {
            user.data.lastTeleport = e.getTimeStamp();
        }else if(e.getType() == Packet.Server.KEEP_ALIVE)
        {
            user.data.lastServerKeepAlive = e.getTimeStamp();
        }else if(e.getType() == Packet.Server.ENTITY_VELOCITY)
        {
            WrappedOutVelocityPacket packet = new WrappedOutVelocityPacket(e.getPacket(), e.getPlayer());
            if(packet.getId() == user.getPlayer().getEntityId())
                user.data.lastVelocity = e.getTimeStamp();
        }
    }

}
