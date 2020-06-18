package me.apex.hades.user;

import me.apex.hades.HadesPlugin;
import me.apex.hades.check.Check;
import me.apex.hades.check.CheckManager;
import me.apex.hades.processor.impl.MovementProcessor;
import me.apex.hades.util.LogUtils;
import me.apex.hades.util.PlayerUtil;
import me.apex.hades.util.ReflectionUtil;
import org.bukkit.Location;
import org.bukkit.block.Block;
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
    public boolean alerts = true, digging, isSprinting, isSneaking;
    //Location
    public Location location, lastLocation, lastOnGroundLocation;
    //Ticks
    public int tick, digTick, iceTick, slimeTick, velocityTick, underBlockTick, teleportTick, airTick, airTicks, groundTick, groundTicks;
    //Deltas
    public double deltaY, lastDeltaY, deltaXZ, lastDeltaXZ;
    public float deltaYaw, lastDeltaYaw, deltaPitch, lastDeltaPitch;
    //Ints
    public int CPS;
    //Interact
    public boolean rightClickingBlock, rightClickingAir, leftClickingBlock, leftClickingAir;
    public Block interactedBlock;
    //Velocity
    public long lastVelocity, timeStamp;
    public double velocityX, velocityY, velocityZ;
    //Log
    public LogUtils.TextFile logFile;

    public User(Player player) {
        this.player = player;
        this.playerUUID = player.getUniqueId();
        this.checks = CheckManager.loadChecks();
        this.timeStamp = System.currentTimeMillis();
        if (HadesPlugin.instance.getConfig().getBoolean("system.logging.file.enabled")) {
            logFile = new LogUtils.TextFile("" + playerUUID, HadesPlugin.instance.getConfig().getString("system.logging.file.path"));
        }
    }

    public boolean onGround() {
        return PlayerUtil.isOnGround(player);
    }

    public boolean hasBlocksArround(){
        if (PlayerUtil.hasBlocksAround(location) && PlayerUtil.hasBlocksAround(location.add(0,1,0))){
            return true;
        }else return false;
    }

    public boolean isOnClimbableBlock(){
        if (PlayerUtil.isClimbableBlock(location.getBlock()) && PlayerUtil.isClimbableBlock(location.add(0,1,0).getBlock())){
            return true;
        }else return false;
    }

    public boolean isInLiquid(){
        if (PlayerUtil.isInLiquid(player)){
            return true;
        }else return false;
    }

    public boolean isInWeb(){
        if (PlayerUtil.isInWeb(player)){
            return true;
        }else return false;
    }

    public boolean isOnGroundVanilla(){ return player.isOnGround(); }

    //Cant do this without reflection!
    public int ping() {
        int ping = 0;
        try{
            ping = ReflectionUtil.getPlayerPing(player);
        }catch (Exception e) {
            e.printStackTrace();
        }
        return ping;
    }
}
