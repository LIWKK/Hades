package io.github.retrooper.packetevents.enums;

public enum EntityUseAction {
    INTERACT, INTERACT_AT, ATTACK, INVALID;

    public static EntityUseAction get(final byte i) {
        return values()[i];
    }
}
