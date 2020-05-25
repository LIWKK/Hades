package me.apex.hades.objects;

import lombok.Getter;
import lombok.Setter;
import me.apex.hades.Hades;
import me.apex.hades.check.api.Check;
import me.apex.hades.check.api.CheckManager;
import me.apex.hades.utils.ChatUtils;
import me.apex.hades.utils.LogUtils;
import me.apex.hades.utils.PlayerUtils;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;

import java.util.*;

@Getter
@Setter
public class User {

    private List<Check> checks;
    private UUID playerUUID;
    private Location location, lastLocation;
    private boolean alerts, lagging, digging;
    private double deltaY, lastDeltaY, deltaXZ, lastDeltaXZ, lastVelX, lastVelY, lastVelZ, optifineTicks;
    private float deltaYaw, lastDeltaYaw, deltaPitch, lastDeltaPitch, lastYawDiff, lastPitchDiff;
    private long lastOnIce, lastHit, lastOnSlime, lastVelocity, lastServerPosition, lastKeepAlive, lastServerKeepAlive, lastJoin, lastPacket, lastLagPacket, lastLagSet;
    private String address;
    private int ping, flagDelay, airTicks;
    private LogUtils.TextFile logFile;
    private Deque<Long> transactionQueue = new LinkedList();
    public boolean banned;

    public User(UUID playerUUID, String address) {
        this.playerUUID = playerUUID;
        this.address = address;
        checks = new ArrayList();
        checks.clear();
        checks = CheckManager.INSTANCE.loadChecks();
        if (Hades.getInstance().getConfig().getBoolean("system.logging.file.enabled")) {
            logFile = new LogUtils.TextFile("" + playerUUID, Hades.getInstance().getConfig().getString("system.logging.file.path"));
        }
    }

    public Player getPlayer() {
        return Bukkit.getPlayer(playerUUID);
    }

    //Global
    public boolean onGround() {
        return PlayerUtils.onGround(getPlayer());
    }

    public boolean onIce() {
        return PlayerUtils.isOnIce(getPlayer());
    }

    public boolean underBlock() {
        return PlayerUtils.blockNearHead(getPlayer());
    }

    public void sendMessage(String message) {
        getPlayer().sendMessage(ChatUtils.color(message));
    }

    public boolean isUsingOptifine() {
        return optifineTicks > 0.0D;
    }
}
