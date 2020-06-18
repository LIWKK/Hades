package me.apex.hades.user;

import com.google.common.collect.EvictingQueue;
import lombok.Getter;
import lombok.Setter;
import me.apex.hades.HadesPlugin;
import me.apex.hades.check.Check;
import me.apex.hades.check.Type;
import me.apex.hades.check.impl.combat.aimassist.*;
import me.apex.hades.check.impl.combat.autoclicker.*;
import me.apex.hades.check.impl.combat.killaura.*;
import me.apex.hades.check.impl.combat.reach.*;
import me.apex.hades.check.impl.combat.velocity.*;
import me.apex.hades.check.impl.movement.fly.*;
import me.apex.hades.check.impl.movement.invalid.*;
import me.apex.hades.check.impl.movement.scaffold.*;
import me.apex.hades.check.impl.movement.speed.*;
import me.apex.hades.check.impl.player.badpackets.*;
import me.apex.hades.check.impl.player.timer.*;
import me.apex.hades.processors.CombatProcessor;
import me.apex.hades.processors.LagProcessor;
import me.apex.hades.processors.MovementProcessor;
import me.apex.hades.user.sub.BlockData;
import me.apex.hades.utils.boundingbox.block.BlockAssesement;
import me.apex.hades.utils.boundingbox.block.BlockUtil;
import me.apex.hades.utils.boundingbox.box.BoundingBox;
import me.apex.hades.utils.location.CustomLocation;
import me.apex.hades.utils.location.NewLocation;
import me.apex.hades.utils.location.PastLocation;
import me.apex.hades.utils.math.MathUtil;
import me.apex.hades.utils.version.VersionUtil;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffectType;

import java.util.ArrayList;
import java.util.List;
import java.util.Queue;
import java.util.UUID;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

@Getter
@Setter
public class User {
    public Player player;
    private UUID uuid;
    private Entity lastEntityAttacked;
    private User targetUser;
    public BlockData blockData;
    private List<Check> checkList = new ArrayList<>();
    private BoundingBox boundingBox;
    public NewLocation location, previousLocation;
    public Queue<NewLocation> locationDataQueue = EvictingQueue.create(10);
    public PastLocation hitBoxPastLocations = new PastLocation();
    public CustomLocation lastGroundLocation, to = new CustomLocation(0, 0, 0), lastSlimeLocation = new CustomLocation(0,0,0), from = to, fromFrom = from;
    public boolean breakingOrPlacingBlock, sprinting, sneaking, switchedGamemodes, safe, hasVerify, waitingForMovementVerify, explode, clientGround, lastClientGround, chunkLoaded, onGround, lastOnGround, wasFlying, collidedGround, collidesHorizontally, collidesVertically, lastCollidedVrtically, hasJumpPotion, hasSpeedPotion, dead, didUnknownTeleport;
    public int clientGroundTicks, clientAirTicks, sprintingTicks, cancelTicks, noDamageTicks, violation, lastServerPostionTick, constantEntityTicks, lastBlockPlaceTick, movementVerifyStage, totalBlocksCheck, lastCheckBlockTick, flyingTick, velocityTicks, movementVerifyBlocks, invalidTeleportMovementVerbose, unknownTeleportTick, totalBlockUpdates, lastBlockGroundTick, connectedTick, mountedTicks, jumpPotionTicks, speedPotionTicks, collidedGroundTicks, groundTicks, airTicks, lastDeadTick;
    public long lastMovePacket, lastEntityDamageAttack, breakingOrPlacingTime, lastUseEntityPacket, lastVelocity, lastRandomDamage, lastEntityDamage, lastFallDamage, lastFireDamage, lastBowDamage, lastTeleport, lastFullTeleport, lastUnknownTeleport, lastUnknownValidTeleport, lastBlockPlace, lastBlockCancel, lastBlockBreakCancel, lastGamemodeSwitch, lastFullBlockMoved, lastBlockJump, lastBlockFall, lastPos, timestamp, lastExplode, lastCollidedGround, lastMoutUpdate, lastMount;
    public double packetsFromLag, horizontalVelocity, verticalVelocity, lastBowStrength, deltaXZ, lastGroundPrediction, groundYPredict, lastFallJumpPrediction, movementSpeed, walkSpeed;
    public float speedPotionEffectLevel, jumpPotionMultiplyer;
    public String clientBrand;
    public Block serverBlockBelow;
    public Location bukkitTo, bukkitFrom;
    public MovementProcessor movementProcessor;
    public CombatProcessor combatProcessor;
    public LagProcessor lagProcessor;
    private Executor executorService;

    public User(Player player) {
        this.player = player;
        this.uuid = player.getUniqueId();

        executorService = Executors.newSingleThreadScheduledExecutor();

        blockData = new BlockData(this);

        setTo(new CustomLocation(0.0, 0.0, 0.0));
        setFrom(getFrom());
        setFromFrom(getFromFrom());
        location = new NewLocation(0, 0, 0, 0.0f, 0.0f);
        boundingBox = new BoundingBox(0f, 0f, 0f, 0f, 0f, 0f);


        setupProcessors();

        registerChecks();

    }

    private void addCheck(Check check) {
        if (!checkList.contains(check)) checkList.add(check);
    }


    public void registerChecks() {

        addCheck(new AimAssistA("AimAssist", "A", Type.COMBAT, true));

        addCheck(new AutoClickerA("AutoClicker", "A", Type.COMBAT, true));
        addCheck(new AutoClickerB("AutoClicker", "B", Type.COMBAT, true));

        addCheck(new KillauraA("Killaura", "A", Type.COMBAT, true));
        addCheck(new KillauraB("Killaura", "B", Type.COMBAT, true));
        addCheck(new KillauraC("Killaura", "C", Type.COMBAT, true));
        addCheck(new KillauraD("Killaura", "D", Type.COMBAT, true));
        addCheck(new KillauraE("Killaura", "E", Type.COMBAT, true));
        addCheck(new KillauraF("Killaura", "F", Type.COMBAT, true));

        addCheck(new ReachA("Reach", "A", Type.COMBAT, true));

        addCheck(new VelocityA("Velocity", "A", Type.COMBAT, true));


        addCheck(new FlyA("Fly", "A", Type.MOVEMENT, true));
        addCheck(new FlyB("Fly", "B", Type.MOVEMENT, true));
        addCheck(new FlyC("Fly", "C", Type.MOVEMENT, true));
        addCheck(new FlyD("Fly", "D", Type.MOVEMENT, true));
        addCheck(new FlyE("Fly", "E", Type.MOVEMENT, true));
        addCheck(new FlyF("Fly", "F", Type.MOVEMENT, true));

        addCheck(new InvalidA("Invalid", "A", Type.MOVEMENT, true));

        addCheck(new ScaffoldA("Scaffold", "A", Type.MOVEMENT, true));

        addCheck(new SpeedA("Speed", "A", Type.MOVEMENT, true));
        addCheck(new SpeedB("Speed", "B", Type.MOVEMENT, true));
        addCheck(new SpeedC("Speed", "C", Type.MOVEMENT, true));


        addCheck(new BadPacketsA("BadPackets", "A", Type.PLAYER, true));
        addCheck(new BadPacketsB("BadPackets", "B", Type.PLAYER, true));
        addCheck(new BadPacketsC("BadPackets", "C", Type.PLAYER, true));
        addCheck(new BadPacketsD("BadPackets", "D", Type.PLAYER, true));
        addCheck(new BadPacketsE("BadPackets", "E", Type.PLAYER, true));

        addCheck(new TimerA("Timer", "A", Type.PLAYER, true));

        checkList.forEach(check -> {
            HadesPlugin.getInstance().getEventManager().registerListeners(check, HadesPlugin.getInstance());
        });
    }

    private void setupProcessors() {

        movementProcessor = new MovementProcessor();
        movementProcessor.setUser(this);

        combatProcessor = new CombatProcessor();
        combatProcessor.setUser(this);

        lagProcessor = new LagProcessor();
        lagProcessor.setUser(this);

}
    public void update(BlockAssesement blockAssesement) {


        /*
            Update ticks here for mostly anything
         */

        if ((getPlayer().isFlying() || getPlayer().getAllowFlight()) && !wasFlying) {
            wasFlying = true;
        } else if (wasFlying && !(getPlayer().isFlying() || getPlayer().getAllowFlight())) {
            if (isOnGround() && isLastOnGround()) {
                wasFlying = false;
            }
        }


        setCollidesHorizontally(blockAssesement.isCollidesHorizontally());

        setLastCollidedVrtically(isCollidesVertically());
        setCollidesVertically(blockAssesement.isCollidesVertically());

        setWalkSpeed(getPlayer().getWalkSpeed());


        if (getBoundingBox() != null && getBukkitTo() != null) {

            Block block = BlockUtil.getBlock(getBukkitTo().clone().add(0, -1, 0));
            if (block != null) {

                if (blockAssesement.isPresurePlate()) {
                    if (blockData.presurePlateTicks < 20) blockData.presurePlateTicks++;
                    blockData.lastPresurePlateTick = getConnectedTick();
                } else {
                    if (blockData.presurePlateTicks > 0) blockData.presurePlateTicks--;
                }

                if (blockAssesement.isEnderFrame()) {
                    if (getBlockData().enderFrameTick < 20) getBlockData().enderFrameTick++;
                } else {
                    if (getBlockData().enderFrameTick > 0) getBlockData().enderFrameTick--;
                }

                if (blockAssesement.isBed()) {
                    if (getBlockData().bedTicks < 20) getBlockData().bedTicks++;
                } else {
                    if (getBlockData().bedTicks > 0) getBlockData().bedTicks--;
                }

                if (blockAssesement.isLeaves()) {
                    if (getBlockData().leaveTicks < 50) getBlockData().leaveTicks++;
                } else {
                    if (getBlockData().leaveTicks > 0) getBlockData().leaveTicks--;
                }


                setServerBlockBelow(BlockUtil.getBlock(getTo().toLocation(getPlayer().getWorld()).clone().add(0, -1f, 0)));

                if (HadesPlugin.versionUtil.getVersion() != VersionUtil.Version.V1_7) {
                    if (isOnGround()) {

                        if (block.getType() == Material.SLIME_BLOCK) {
                            if (!getBlockData().slime) {
                                getBlockData().slime = true;
                            }
                            setLastSlimeLocation(getTo().clone());
                            //setLastSlimeLocation(new CustomLocation(getTo().getX(), getTo().getY(), getTo().getZ()));
                        }

                        if (getBlockData().slime && block.getType() != Material.AIR && block.getType() != Material.SLIME_BLOCK) {
                            getBlockData().slime = false;
                            setLastSlimeLocation(null);
                        }
                    } else {
                        if (getLastSlimeLocation() != null && getBlockData().slime) {
                            Location loc = getLastSlimeLocation().toLocation(getPlayer().getWorld()), currentLoc = getTo().toLocation(getPlayer().getWorld()).clone();
                            loc.setY(0.0f);
                            currentLoc.setY(0.0f);

                            double distance = loc.distance(currentLoc);

                            if (distance > 5 && Math.abs(getTo().getY() - getFrom().getY()) == 0.0) {
                                getBlockData().slime = false;
                                setLastSlimeLocation(null);
                            }

                            if (distance > 10) {
                                getBlockData().slime = false;
                                setLastSlimeLocation(null);
                            }
                        }
                    }
                }
            }
        }

        if (blockAssesement.isChests()) {
            if (getBlockData().chestTicks < 20) getBlockData().chestTicks++;
            blockData.lastChestTick = getConnectedTick();
        } else {
            if (getBlockData().chestTicks > 0) getBlockData().chestTicks--;
        }

        if (blockAssesement.isTrapDoor()) {
            if (getBlockData().trapDoorTicks < 20) getBlockData().trapDoorTicks++;
        } else {
            if (getBlockData().trapDoorTicks > 0) getBlockData().trapDoorTicks--;
        }

        if (getMountedTicks() > 0) {
           setLastMoutUpdate(System.currentTimeMillis());
        }

        if (getPlayer().getVehicle() != null) {
            setLastMount(System.currentTimeMillis());
            if (getMountedTicks() < 20) setMountedTicks(getMountedTicks() + 1);
        } else {
            if (getMountedTicks() > 0) setMountedTicks(getMountedTicks() - 1);
        }

        boolean hasSpeed = getPlayer().hasPotionEffect(PotionEffectType.SPEED), hasJump = getPlayer().hasPotionEffect(PotionEffectType.JUMP);

        setSpeedPotionEffectLevel(MathUtil.getPotionEffectLevel(getPlayer(), PotionEffectType.SPEED));

        setHasSpeedPotion(hasSpeed);
        setHasJumpPotion(hasJump);

        if (hasJump) {
            if (getJumpPotionTicks() < 20) setJumpPotionTicks(getJumpPotionTicks() + 1);
            setJumpPotionMultiplyer(MathUtil.getPotionEffectLevel(getPlayer(), PotionEffectType.JUMP));
        } else {
            if (getJumpPotionTicks() > 0) setJumpPotionTicks(getJumpPotionTicks() - 1);
        }

        if (hasSpeed) {
            if (getSpeedPotionTicks() < 20) setSpeedPotionTicks(getSpeedPotionTicks() + 1);
        } else {
            if (getSpeedPotionTicks() > 0) setSpeedPotionTicks(getSpeedPotionTicks() - 1);
        }


        if (isCollidedGround()) {
            if (getCollidedGroundTicks() < 20) setCollidedGroundTicks(getCollidedGroundTicks() + 1);
        } else {
            if (getCollidedGroundTicks() > 0) setCollidedGroundTicks(getCollidedGroundTicks() - 1);
        }

        int groundTicks = getGroundTicks();
        int airTicks = getAirTicks();

        if (blockAssesement.isHopper()) {
            if (getBlockData().hopperTicks < 50) getBlockData().hopperTicks++;
        } else {
            if (getBlockData().hopperTicks > 0) getBlockData().hopperTicks--;
        }

        if (blockAssesement.isWall()) {
            if (getBlockData().wallTicks < 20) getBlockData().wallTicks++;
        } else {
            if (getBlockData().wallTicks > 0) getBlockData().wallTicks--;
        }


        if (blockAssesement.isLillyPad()) {
            if (getBlockData().lillyPadTicks < 50) getBlockData().lillyPadTicks++;
        } else {
            if (getBlockData().lillyPadTicks > 0) getBlockData().lillyPadTicks--;
        }

        if (blockAssesement.isOnGround()) {

            if (blockAssesement.isAnvil()) {
                if (getBlockData().anvilTicks < 20) getBlockData().anvilTicks++;
            } else {
                if (getBlockData().anvilTicks > 0) getBlockData().anvilTicks--;
            }

            if (groundTicks < 20) groundTicks++;
            airTicks = 0;
        } else {
            if (airTicks < 20) airTicks++;
            groundTicks = 0;
        }

        if (getPlayer().isOnGround()) {

            if (clientGroundTicks < 20) clientGroundTicks++;
            clientAirTicks = 0;
        } else {
            if (clientAirTicks < 20) clientAirTicks++;
            clientGroundTicks = 0;
        }

        if (isSprinting()) {
            if (getSprintingTicks() < 50) setSprintingTicks(getSprintingTicks() + 1);
        } else {
            if (getSprintingTicks() > 0) setSprintingTicks(0);
        }

        if (blockAssesement.isBlockAbove()) {
            if (blockData.blockAboveTicks < 20) blockData.blockAboveTicks++;
            blockData.lastBlockAbove = System.currentTimeMillis();
            blockData.lastBlockAboveTick = getConnectedTick();
        } else {
            if (blockData.blockAboveTicks > 0) blockData.blockAboveTicks--;
        }

        if (blockAssesement.isSnow()) {
            if (blockData.snowTicks < 20) blockData.snowTicks++;
        } else {
            if (blockData.snowTicks > 0) blockData.snowTicks--;
        }

        getBlockData().climable = blockAssesement.isClimbale();

        if (blockAssesement.isClimbale()) {
            if (blockData.climbableTicks < 20) blockData.climbableTicks++;
        } else {
            if (blockData.climbableTicks > 0) blockData.climbableTicks--;
        }

        if (blockAssesement.isWeb()) {
            if (blockData.webTicks < 20) blockData.webTicks++;
        } else {
            if (blockData.webTicks > 0) blockData.webTicks--;
        }

        if (blockAssesement.isSoulSand()) {
            if (blockData.soulSandTicks < 20) blockData.soulSandTicks++;
            getBlockData().lastSoulSand = System.currentTimeMillis();
        } else {
            if (blockData.soulSandTicks > 0) blockData.soulSandTicks--;
        }

        if (blockAssesement.isHalfblock()) {
            if (blockData.halfBlockTicks < 20) blockData.halfBlockTicks++;
            blockData.lastHalfBlockTick = getConnectedTick();
        } else {
            if (blockData.halfBlockTicks > 0) blockData.halfBlockTicks--;
        }

        if (blockAssesement.isLiquid()) {
            if (blockData.liquidTicks < 100) blockData.liquidTicks++;
        } else {
            if (blockData.liquidTicks > 0) blockData.liquidTicks--;
        }


        getBlockData().ice = blockAssesement.isOnIce();

        if (blockAssesement.isOnIce() || blockAssesement.isOnNearIce()) {
            if (blockData.iceTicks < 20) blockData.iceTicks++;
            blockData.lastIce = System.currentTimeMillis();
            blockData.lastIceTick = getConnectedTick();
        } else {
            if (blockData.iceTicks > 0) blockData.iceTicks--;
        }

        if (blockAssesement.isStair()) {
            if (blockData.stairTicks < 20) blockData.stairTicks++;
            blockData.lastStairTicks = getConnectedTick();
        } else {
            if (blockData.stairTicks > 0) blockData.stairTicks--;
        }

        if (blockAssesement.isSlab()) {
            if (blockData.slabTicks < 20) blockData.slabTicks++;
            blockData.lastSlabTick = getConnectedTick();
        } else {
            if (blockData.slabTicks > 0) blockData.slabTicks--;
        }

        if (blockAssesement.isFence()) {
            if (blockData.fenceTicks < 20) blockData.fenceTicks++;
        } else {
            if (blockData.fenceTicks > 0) blockData.fenceTicks--;
        }


        if (blockAssesement.isRail()) {
            if (blockData.railTicks < 20) blockData.railTicks++;
        } else {
            if (blockData.railTicks > 0) blockData.railTicks--;
        }

        if (blockAssesement.isSlime()) {
            blockData.lastSline = System.currentTimeMillis();
            if (blockData.slimeTicks < 20) blockData.slimeTicks++;
            blockData.lastSlimeTick = getConnectedTick();
        } else {
            if (blockData.slimeTicks > 0) blockData.slimeTicks--;
        }


        if (blockAssesement.isHalfGlass()) {
            if (blockData.glassPaneTicks < 20) blockData.glassPaneTicks++;
        } else {
            if (blockData.glassPaneTicks > 0) blockData.glassPaneTicks--;
        }

        if (blockAssesement.isDoor()) {
            if (blockData.doorTicks < 20) blockData.doorTicks++;
        } else {
            if (blockData.doorTicks > 0) blockData.doorTicks--;
        }



        setAirTicks(airTicks);
        setGroundTicks(groundTicks);
    }
}
