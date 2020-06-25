package io.github.retrooper.packetevents.enums;

public enum PlayerAction {
    START_SNEAKING,

    STOP_SNEAKING,

    STOP_SLEEPING,

    START_SPRINTING,

    STOP_SPRINTING,

    START_RIDING_JUMP,

    STOP_RIDING_JUMP,

    OPEN_INVENTORY,

    START_FALL_FLYING;

    public static PlayerAction get(final byte i) {
        return values()[i];
    }
}
