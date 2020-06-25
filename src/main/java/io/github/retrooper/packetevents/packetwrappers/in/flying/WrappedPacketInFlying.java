package io.github.retrooper.packetevents.packetwrappers.in.flying;


import io.github.retrooper.packetevents.enums.ServerVersion;
import io.github.retrooper.packetevents.packetwrappers.api.WrappedPacket;
import io.github.retrooper.packetevents.utils.NMSUtils;

import java.lang.reflect.Field;

public class WrappedPacketInFlying extends WrappedPacket {
    private static Class<?> flyingClass;
    private static final Field[] fields = new Field[8]; //x, y, z, yaw pitch, onGround, isPositionPacket, isLookPacket

    static {

        try {
            flyingClass = NMSUtils.getNMSClass("PacketPlayInFlying");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }


        try {
            fields[0] = flyingClass.getDeclaredField("x");
            fields[1] = flyingClass.getDeclaredField("y");
            fields[2] = flyingClass.getDeclaredField("z");
            fields[3] = flyingClass.getDeclaredField("yaw");
            fields[4] = flyingClass.getDeclaredField("pitch");
            fields[5] = flyingClass.getDeclaredField(getOnGroundFieldName());
            fields[6] = flyingClass.getDeclaredField("hasPos");
            fields[7] = flyingClass.getDeclaredField("hasLook");
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        }

        for (Field f : fields) {
            if (f != null) {
                f.setAccessible(true);
            }
        }
    }

    private double x;
    private double y;
    private double z;
    private float yaw;
    private float pitch;
    private boolean onGround;
    private boolean isPosition;
    private boolean isLook;

    public WrappedPacketInFlying(Object packet) {
        super(packet);
    }

    private static String getOnGroundFieldName() {
        if (version == ServerVersion.v_1_7_10) {
            return "g";
        }
        return "f";
    }

    @Override
    protected void setup() {
        try {
            this.x = fields[0].getDouble(packet);
            this.y = fields[1].getDouble(packet);
            this.z = fields[2].getDouble(packet);

            this.yaw = fields[3].getFloat(packet);
            this.pitch = fields[4].getFloat(packet);

            this.onGround = fields[5].getBoolean(packet);

            this.isPosition = fields[6].getBoolean(packet);
            this.isLook = fields[7].getBoolean(packet);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    public final double getX() {
        return x;
    }

    public final double getY() {
        return y;
    }

    public final double getZ() {
        return z;
    }

    public final float getYaw() {
        return yaw;
    }

    public final float getPitch() {
        return pitch;
    }

    public final boolean isOnGround() {
        return onGround;
    }

    public boolean isPosition() {
        return isPosition;
    }

    public boolean isLook() {
        return isLook;
    }

    public static class WrappedPacketInPosition extends WrappedPacketInFlying {
        public WrappedPacketInPosition(Object packet) {
            super(packet);
        }

        @Override
        public boolean isPosition() {
            return true;
        }

        @Override
        public boolean isLook() {
            return false;
        }
    }

    public static class WrappedPacketInPosition_Look extends WrappedPacketInFlying {
        public WrappedPacketInPosition_Look(Object packet) {
            super(packet);
        }

        @Override
        public boolean isLook() {
            return true;
        }

        @Override
        public boolean isPosition() {
            return false;
        }
    }

}
