package me.apex.hades.processors;


import lombok.Setter;
import me.apex.hades.HadesPlugin;
import me.apex.hades.tinyprotocol.api.Packet;
import me.apex.hades.tinyprotocol.packet.in.WrappedInBlockDigPacket;
import me.apex.hades.tinyprotocol.packet.in.WrappedInEntityActionPacket;
import me.apex.hades.tinyprotocol.packet.in.WrappedInFlyingPacket;
import me.apex.hades.user.User;
import me.apex.hades.utils.boundingbox.block.BlockAssesement;
import me.apex.hades.utils.boundingbox.block.BlockUtil;
import me.apex.hades.utils.boundingbox.box.BoundingBox;
import me.apex.hades.utils.location.CustomLocation;
import me.apex.hades.utils.math.MathUtil;
import me.apex.hades.utils.time.TimeUtils;
import org.bukkit.block.Block;

import java.util.List;

@Setter
public class MovementProcessor {
    private User user;

    public void update(Object packet, String type) {
        if (user != null) {

            if (type.equalsIgnoreCase(Packet.Client.ENTITY_ACTION)) {
                WrappedInEntityActionPacket wrappedInEntityActionPacket = new WrappedInEntityActionPacket(packet, user.getPlayer());
                if (wrappedInEntityActionPacket.getAction() == WrappedInEntityActionPacket.EnumPlayerAction.START_SPRINTING) {
                    user.setSprinting(true);
                } else if (wrappedInEntityActionPacket.getAction() == WrappedInEntityActionPacket.EnumPlayerAction.STOP_SPRINTING) {
                    user.setSprinting(false);
                }

                if (wrappedInEntityActionPacket.getAction() == WrappedInEntityActionPacket.EnumPlayerAction.START_SNEAKING) {
                    user.setSneaking(true);
                } else if (wrappedInEntityActionPacket.getAction() == WrappedInEntityActionPacket.EnumPlayerAction.STOP_SPRINTING) {
                    user.setSneaking(false);
                }
            }

            if (type.equalsIgnoreCase(Packet.Client.BLOCK_DIG)) {

                user.setLastCheckBlockTick(user.getConnectedTick());

                WrappedInBlockDigPacket wrappedInBlockDigPacket = new WrappedInBlockDigPacket(packet, user.getPlayer());

                if (wrappedInBlockDigPacket.getAction() == WrappedInBlockDigPacket.EnumPlayerDigType.START_DESTROY_BLOCK) {
                    user.setBreakingOrPlacingBlock(true);
                    user.setBreakingOrPlacingTime(System.currentTimeMillis());
                } else if (wrappedInBlockDigPacket.getAction() == WrappedInBlockDigPacket.EnumPlayerDigType.STOP_DESTROY_BLOCK) {
                    user.setBreakingOrPlacingBlock(false);
                } else if (wrappedInBlockDigPacket.getAction() == WrappedInBlockDigPacket.EnumPlayerDigType.ABORT_DESTROY_BLOCK) {
                    user.setBreakingOrPlacingBlock(false);
                }
            }
            if (type.equalsIgnoreCase(Packet.Server.POSITION)) {

                user.setLastCheckBlockTick(user.getConnectedTick());

                user.lastServerPostionTick = user.getConnectedTick();
            }

            if (type.equalsIgnoreCase(Packet.Client.BLOCK_PLACE)) {

                user.setLastCheckBlockTick(user.getConnectedTick());

                user.setBreakingOrPlacingBlock(true);
                user.setBreakingOrPlacingTime(System.currentTimeMillis());
            }

            if (type.equalsIgnoreCase(Packet.Client.POSITION) || type.equalsIgnoreCase(Packet.Client.POSITION_LOOK) || type.equalsIgnoreCase(Packet.Client.LOOK) || type.equalsIgnoreCase(Packet.Client.FLYING)) {
                WrappedInFlyingPacket wrappedInFlyingPacket = new WrappedInFlyingPacket(packet, user.getPlayer());


                if (user.getPlayer().isDead()) {
                    user.setDead(true);
                    user.setLastDeadTick(user.getConnectedTick());
                } else {
                    user.setDead(false);
                }


                if (user.isDidUnknownTeleport()) {

                    if (Math.abs(user.getConnectedTick() - user.getUnknownTeleportTick()) > 20) {

                        if ((user.getTo().getY() - user.getFrom().getY() >= 0.0)) {

                            if (user.getMovementSpeed() > 0.66 && (System.currentTimeMillis() - user.getLastEntityDamage()) > 1000L && (System.currentTimeMillis() - user.getLastBowDamage()) > 1000L) {
                                user.setDidUnknownTeleport(false);
                            }
                        }

                        if (Math.abs(user.getConnectedTick() - user.getUnknownTeleportTick()) > 5 && (user.isOnGround() || user.isClientGround())) {
                            user.setDidUnknownTeleport(false);
                        }

                    }
                }


                if ((System.currentTimeMillis() - user.getLastExplode()) > 1000L && user.isExplode() && user.isOnGround() && user.isLastOnGround()) {
                    user.setExplode(false);
                }

                if (user.isWaitingForMovementVerify()) {

                    if (user.movementVerifyBlocks > 5) {
                        user.movementVerifyBlocks = 0;
                        user.setWaitingForMovementVerify(false);
                    }

                    double x = Math.floor(user.getFrom().getX());
                    double z = Math.floor(user.getFrom().getZ());
                    if (Math.floor(user.getTo().getX()) != x || Math.floor(user.getTo().getZ()) != z) {
                        user.movementVerifyBlocks++;
                    }
                }


                if (user.getFlyingTick() < 20) {
                    user.setFlyingTick(user.getFlyingTick() + 1);
                } else if (user.getFlyingTick() >= 20) {
                    user.setFlyingTick(0);
                }

                user.setConnectedTick(user.getConnectedTick() + 1);
                user.setVelocityTicks(user.getVelocityTicks() + 1);

                user.setSafe(TimeUtils.secondsFromLong(user.getTimestamp()) > 2L || user.isHasVerify());

                if (!user.isHasVerify()) user.setHasVerify(user.isSafe());


                if (user.isSafe()) {

                    user.setLastClientGround(user.isClientGround());
                    user.setClientGround(wrappedInFlyingPacket.isGround());

                    if (user.getFrom() != null) {
                        user.setFromFrom(user.getFrom().clone());
                    }

                    if (user.getTo() != null) {
                        user.setFrom(user.getTo().clone());

                    }


                  //  DevLocation lastLocation = user.getLocation();
                    if (wrappedInFlyingPacket.isPos()) {

                        user.getTo().setX(wrappedInFlyingPacket.getX());
                        user.getTo().setY(wrappedInFlyingPacket.getY());
                        user.getTo().setZ(wrappedInFlyingPacket.getZ());
                        user.getTo().setClientGround(wrappedInFlyingPacket.isGround());
                        user.setLastPos(System.currentTimeMillis());

                        if (user.getTo() != null && user.getFrom() != null) {

                            user.setLastOnGround(user.isOnGround());
                            if (user.isSafe() && user.getBoundingBox() != null) {
                                this.updateBlockCheck();
                            } else {
                                user.setOnGround(wrappedInFlyingPacket.isGround());
                            }
                        }


                        CustomLocation customLocation = new CustomLocation(wrappedInFlyingPacket.getX(), wrappedInFlyingPacket.getY(), wrappedInFlyingPacket.getZ());

                        if (user.getLastGroundLocation() != null && user.isOnGround()) {

                            if (Math.abs((user.getTo().getY() - user.getFrom().getY())) > 0.0f && user.getTo().getY() < user.getFrom().getY()) {
                                double totalPrediction = MathUtil.round(user.getTo().getY(), 0) + user.getGroundYPredict();

                                if (totalPrediction < user.getLastFallJumpPrediction()) {
                                    user.setLastBlockFall(System.currentTimeMillis());
                                }

                                user.setLastFallJumpPrediction(totalPrediction);
                            }

                            if ((user.getTo().getY() - user.getFrom().getY()) > 0.4f && user.getTo().getY() > user.getFrom().getY()) {
                                double totalPrediction = MathUtil.round(user.getTo().getY(), 0) + user.getGroundYPredict();

                                if (totalPrediction > user.getLastGroundPrediction()) {
                                    user.setLastBlockJump(System.currentTimeMillis());
                                }

                                user.setLastGroundPrediction(totalPrediction);
                            }
                        }

                        if (user.isOnGround() && user.getTo() != null && user.getFrom() != null) {
                            if (user.isLastOnGround()) {
                                user.setGroundYPredict(user.getTo().getY());
                            }
                            user.setLastGroundLocation(customLocation);
                        }

                    }

                    user.setDeltaXZ((Math.hypot(user.getTo().getX() - user.getFrom().getX(), user.getTo().getZ() - user.getFrom().getZ())));

                    boolean badVector = Math.abs(user.getTo().toVector().length() - user.getFrom().toVector().length()) >= 1;

                    user.setBoundingBox(new BoundingBox((badVector ? user.getTo().toVector() : user.getFrom().toVector()), user.getTo().toVector()).grow(0.3f, 0, 0.3f).add(0, 0, 0, 0, 1.84f, 0));


                    if (wrappedInFlyingPacket.isLook()) {

                        user.getTo().setPitch(wrappedInFlyingPacket.getPitch());
                        user.getTo().setYaw(wrappedInFlyingPacket.getYaw());
                    }

                    if (user.getTo() != null && user.getFrom() != null) {

                        if (Math.abs(user.getTo().getY() - user.getFrom().getY()) > 0.0f) {
                            user.setLastCheckBlockTick(user.getConnectedTick());
                        }

                        double x = Math.floor(user.getFrom().getX());
                        double z = Math.floor(user.getFrom().getZ());

                        if (Math.floor(user.getTo().getX()) != x || Math.floor(user.getTo().getZ()) != z) {

                            user.setLastCheckBlockTick(user.getConnectedTick());

                            if (user.totalBlocksCheck < 100) user.totalBlocksCheck++;

                            user.setLastFullBlockMoved(System.currentTimeMillis());

                        }
                    }

                    if (user.isSwitchedGamemodes() && user.isOnGround()) {
                        user.setSwitchedGamemodes(false);
                    }

                    if (user.getTo() != null && user.getFrom() != null) {
                        user.setBukkitTo(user.getTo().toLocation(user.getPlayer().getWorld()));
                        user.setBukkitFrom(user.getFrom().toLocation(user.getPlayer().getWorld()));

                        double x = Math.abs(Math.abs(user.getTo().getX()) - Math.abs(user.getFrom().getX()));
                        double z = Math.abs(Math.abs(user.getTo().getZ()) - Math.abs(user.getFrom().getZ()));
                        user.setMovementSpeed(Math.sqrt(x * x + z * z));
                    }
                }
            }
        }
    }
    private void updateBlockCheck() {

        BlockAssesement blockAssesement = new BlockAssesement(user.getBoundingBox(), user);

        List<BoundingBox> boxes = HadesPlugin.getInstance().getBlockBoxManager().getBlockBox().getCollidingBoxes(user.getPlayer().getWorld(), user.getBoundingBox().grow(0.5f, 0.35f, 0.5f).subtract(0, 0.5f, 0, 0, 0, 0));

        boxes.parallelStream().forEach(boundingBox -> {
            Block block = BlockUtil.getBlock(boundingBox.getMinimum().toLocation(user.getPlayer().getWorld()));
            if (block != null) {
                user.setChunkLoaded(true);
                blockAssesement.update(boundingBox, block, user.getPlayer().getWorld());
            } else {
                user.setChunkLoaded(false);
            }
        });

        user.setOnGround(blockAssesement.isOnGround());

        user.setCollidedGround(blockAssesement.isCollidedGround());

        if (blockAssesement.isCollidedGround()) {
            user.setLastCollidedGround(System.currentTimeMillis());
        }

        user.getBlockData().isGroundWater = blockAssesement.isLiquidGround();
        user.update(blockAssesement);
    }
}
