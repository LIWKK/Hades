package me.apex.hades.user;

import me.apex.hades.check.Check;
import me.apex.hades.check.CheckManager;
import me.apex.hades.processor.impl.MovementProcessor;
import me.apex.hades.util.PlayerUtil;
import org.bukkit.Location;
import org.bukkit.entity.Player;

import java.util.List;
import java.util.UUID;

public class User {

    //Player
    public final Player player;
    public final UUID playerUUID;

    //Checks
    public final List<Check> checks;
    //Processors
    public final MovementProcessor movementProcessor = new MovementProcessor(this);
    //Booleans
    public boolean alerts = true, digging;
    //Location
    public Location location, lastLocation;
    //Ticks
    public int tick, digTick, iceTick, slimeTick, velocityTick, underBlockTick, teleportTick;
    //Deltas
    public double deltaY, lastDeltaY, deltaXZ, lastDeltaXZ;
    public float deltaYaw, lastDeltaYaw, deltaPitch, lastDeltaPitch;

    public User(Player player) {
        this.player = player;
        this.playerUUID = player.getUniqueId();
        this.checks = CheckManager.loadChecks();
    }

    public boolean onGround() {
        return PlayerUtil.isOnGround(location);
    }

}
