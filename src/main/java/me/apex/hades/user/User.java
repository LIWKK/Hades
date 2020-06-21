package me.apex.hades.user;

import lombok.Getter;
import lombok.Setter;
import me.apex.hades.HadesPlugin;
import me.apex.hades.check.Check;
import me.apex.hades.check.CheckManager;
import me.apex.hades.processor.Processor;
import me.apex.hades.processor.impl.BlockProcessor;
import me.apex.hades.processor.impl.MovementProcessor;
import me.apex.hades.processor.impl.OptifineProcessor;
import me.apex.hades.util.PlayerUtil;
import me.apex.hades.util.reflection.ReflectionUtil;
import me.apex.hades.util.text.LogUtils;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;

import java.util.List;
import java.util.UUID;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

@Getter
@Setter
public class User {

    //Player
    private final Player player;
    private final UUID playerUUID;

    //Checks
    private final List<Check> checks;
    //Processors
    private final Processor movementProcessor = new MovementProcessor(this), blockProcessor = new BlockProcessor(this), optifineProcessor = new OptifineProcessor(this);
    //Booleans
    private boolean alerts = true, usingLunarClient, onGround, collidedGround, digging, isSprinting, isSneaking, chunkLoaded;
    //Location
    private Location location, lastLocation, lastOnGroundLocation;
    //Ticks
    private int tick, digTick, iceTick, iceTicks, slimeTick, slimeTicks, velocityTick, underBlockTick, teleportTick, liquidTick, liquidTicks, airTick, airTicks, groundTick, groundTicks, totalBlockUpdates, solidLiquidTicks, climbableTick, climbableTicks, serverGroundTick, optifineTick;
    //Deltas
    private double deltaY, lastDeltaY, deltaXZ, lastDeltaXZ, mouseSensitivity;
    private float deltaYaw, lastDeltaYaw, deltaPitch, lastDeltaPitch, deltaAngle;
    //Ints
    private int CPS;
    //Interact
    private boolean rightClickingBlock, rightClickingAir, leftClickingBlock, leftClickingAir;
    private Block interactedBlock;
    //Velocity
    private long timeStamp, lastAnyBlockWithLiquid;
    private double velocityX, velocityY, velocityZ;
    //Log
    private LogUtils.TextFile logFile;
    //Thread
    private Executor executorService;
    private long joinTime;

    Vector direction;

    public User(Player player) {
        this.player = player;
        this.location = player.getLocation();
        this.playerUUID = player.getUniqueId();
        this.checks = CheckManager.loadChecks();
        this.timeStamp = System.currentTimeMillis();
        this.executorService = Executors.newSingleThreadExecutor();
        if (HadesPlugin.getInstance().getConfig().getBoolean("system.logging.file.enabled")) {
            logFile = new LogUtils.TextFile("" + playerUUID, HadesPlugin.getInstance().getConfig().getString("system.logging.file.path"));
        }
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
