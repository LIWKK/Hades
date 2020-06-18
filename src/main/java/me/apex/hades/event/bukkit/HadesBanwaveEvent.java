package me.apex.hades.event.bukkit;

import lombok.Getter;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

import java.util.List;

@Getter
public class HadesBanwaveEvent extends Event {
    private static final HandlerList handlers = new HandlerList();

    private List<Player> playerList;

    public HadesBanwaveEvent(List<Player> playerList) {
        this.playerList = playerList;
    }

    public HandlerList getHandlers() {
        return handlers;
    }

    public static HandlerList getHandlerList() {
        return handlers;
    }
}