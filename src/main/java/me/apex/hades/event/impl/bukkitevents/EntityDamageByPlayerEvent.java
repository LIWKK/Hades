package me.apex.hades.event.impl.bukkitevents;

import me.apex.hades.event.Event;
import me.apex.hades.user.User;
import me.apex.hades.user.UserManager;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

public class EntityDamageByPlayerEvent extends Event {

    private User damager;
    private Entity damaged;
    private double damage;
    private double finalDamage;
    private boolean isCancelled;

    public EntityDamageByPlayerEvent(EntityDamageByEntityEvent event) {
        this.damager = UserManager.getUser((Player)event.getDamager());
        this.damaged = event.getEntity();
        this.damage = event.getDamage();
        this.finalDamage = event.getFinalDamage();
        this.isCancelled = event.isCancelled();
    }

    public User getDamager() {
        return damager;
    }

    public Entity getDamaged() {
        return damaged;
    }

    public double getDamage() {
        return damage;
    }

    public double getFinalDamage() {
        return finalDamage;
    }

    public boolean isCancelled() {
        return isCancelled;
    }
}
