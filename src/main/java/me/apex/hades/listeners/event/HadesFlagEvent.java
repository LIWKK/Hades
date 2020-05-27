package me.apex.hades.listeners.event;

import me.apex.hades.check.api.Check;
import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class HadesFlagEvent extends Event implements Cancellable {

    private static final HandlerList handlers = new HandlerList();
    private boolean cancelled;

    private Player player;
    private Check check;
    private String information;

    public HadesFlagEvent(Player player, Check check, String information) {
        this.player = player;
        this.check = check;
        this.information = information;
    }

    public Player getPlayer() {
        return player;
    }

    @Override
    public HandlerList getHandlers() {
        return handlers;
    }

    public static HandlerList getHandlerList() {
        return handlers;
    }

    @Override
    public boolean isCancelled() {
        return cancelled;
    }

    @Override
    public void setCancelled(boolean cancelled) {
        this.cancelled = cancelled;
    }

    public Check getCheck() {
        return check;
    }

    public String getInformation() {
        return information;
    }

}
