package me.purplex.packetevents.event.impl;

import org.bukkit.entity.Player;

import me.purplex.packetevents.event.PacketEvent;

public class PlayerUninjectEvent extends PacketEvent {
    private final Player player;

    public PlayerUninjectEvent(final Player player) {
        this.player = player;
    }

    public Player getPlayer() {
        return player;
    }
}
