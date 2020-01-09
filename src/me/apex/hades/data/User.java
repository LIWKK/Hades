package me.apex.hades.data;

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

public class User {

    @Getter
    @Setter
    private List<Check> checks;
    @Getter
    private UUID playerUUID;
    @Getter
    @Setter
    private Location location, lastLocation;
    @Getter
    @Setter
    private boolean alerts, lagging, digging;
    @Getter
    @Setter
    private double deltaY, lastDeltaY, deltaXZ, lastDeltaXZ;
    @Getter
    @Setter
    private float deltaYaw, lastDeltaYaw, deltaPitch, lastDeltaPitch;
    @Getter
    @Setter
    private long lastOnIce, lastOnSlime, lastVelocity, lastServerPosition, lastKeepAlive, lastServerKeepAlive, lastJoin, lastPacket, lastLagPacket, lastLagSet;
    @Getter
    private String address;
    @Getter
    @Setter
    private int ping, flagDelay;

    @Getter
    private LogUtils.TextFile logFile;

    @Getter
    @Setter
    private Deque<Long> transactionQueue = new LinkedList();

    public User(UUID playerUUID, String address)
    {
        this.playerUUID = playerUUID;
        this.address = address;
        checks = new ArrayList();
        checks.clear();
        checks = CheckManager.INSTANCE.loadChecks();
        if(Hades.getInstance().getConfig().getBoolean("system.logging.file.enabled"))
        {
            logFile = new LogUtils.TextFile("" + playerUUID, Hades.getInstance().getConfig().getString("system.logging.file.path"));
        }
    }

    public Player getPlayer()
    {
        return Bukkit.getPlayer(playerUUID);
    }

    //Global
    public boolean onGround() { return PlayerUtils.onGround(getPlayer()); }

    public boolean onIce() { return PlayerUtils.isOnIce(getPlayer()); }

    public boolean underBlock() { return PlayerUtils.blockNearHead(getPlayer()); }

    public void sendMessage(String message)
    {
        getPlayer().sendMessage(ChatUtils.color(message));
    }

}
