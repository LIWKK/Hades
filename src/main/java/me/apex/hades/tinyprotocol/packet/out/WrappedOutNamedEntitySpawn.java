package me.apex.hades.tinyprotocol.packet.out;

import me.apex.hades.tinyprotocol.api.NMSObject;
import me.apex.hades.tinyprotocol.api.ProtocolVersion;
import me.apex.hades.utils.math.MathUtil;

public class WrappedOutNamedEntitySpawn extends NMSObject {
    private static final String packet = NMSObject.Server.NAMED_ENTITY_SPAWN;

    public WrappedOutNamedEntitySpawn(ProtocolVersion version, int id, Object gameProfile, double x, double y, double z, Object dataWatcher, Object watchables) {
        if (version.isBelow(ProtocolVersion.V1_8)) {
       //     setPacket(packet,id, gameProfile, MathUtil.floor(x * 32.0D), MathUtil.floor(y * 32.0D), MathUtil.floor(z * 32.0D), null, null, null, dataWatcher);
        } else {
        //    WrappedGameProfile profile = new WrappedGameProfile(gameProfile);
         //   setPacket(packet,id, profile.getId(), profile.getName(), profile.getPropertyMap(), MathUtil.floor(x * 32.0D), MathUtil.floor(y * 32.0D), MathUtil.floor(z * 32.0D), null, null, null, dataWatcher, watchables);
        }
        setPacket(packet,id, gameProfile, MathUtil.floor(x * 32.0D), MathUtil.floor(y * 32.0D), MathUtil.floor(z * 32.0D), null, null, null, dataWatcher);
    }

    @Override
    public void updateObject() {

    }
}