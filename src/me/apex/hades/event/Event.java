package me.apex.hades.event;

public class Event {

    public long timeStamp;

    private boolean cancelled;

    public boolean isCancelled() { return cancelled; }

    public void setCancelled(boolean cancelled) { this.cancelled = cancelled; }

}
