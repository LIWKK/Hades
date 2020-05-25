package me.apex.hades.utils;

import cc.funkemunky.api.utils.BlockUtils;
import me.apex.hades.objects.User;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffectType;

public class PlayerUtils {

    /*
    Block Utils
     */

    public static boolean onGround(Player player) {
        double offset = 0.3;
        for (double x = -offset; x <= offset; x += offset) {
            for (double z = -offset; z <= offset; z += offset) {
                if (player.getLocation().clone().add(x, -0.1, z).getBlock().getType() != Material.AIR
                        || player.getLocation().clone().add(x, -0.5005, z).getBlock().getType().toString().contains("FENCE")
                        || player.getLocation().clone().add(x, -0.5005, z).getBlock().getType().toString().contains("WALL")) {
                    return true;
                }
            }
        }
        return false;
    }

    public static boolean blockNearHead(Player player) {
        double expand = 0.3;
        for (double x = -expand; x <= expand; x += expand) {
            for (double z = -expand; z <= expand; z += expand) {
                if (player.getLocation().clone().add(z, 2, x).getBlock().getType() != Material.AIR) {
                    return true;
                }
                if (player.getLocation().clone().add(z, 1.5001, x).getBlock().getType() != Material.AIR) {
                    return true;
                }
            }
        }
        return false;
    }

    public static boolean isOnIce(Player p) {
        if (p.getLocation().getBlock().getRelative(BlockFace.DOWN).getType().toString().contains("ICE")
                || p.getLocation().clone().add(0, -0.5, 0).getBlock().getRelative(BlockFace.DOWN).getType().toString().contains("ICE")) {
            return true;
        }
        return false;
    }

    public static boolean isInLiquid(Player player) {
        if (BlockUtils.isLiquid(player.getLocation().getBlock())) return true;
        double expand = 0.3;
        for (double x = -expand; x <= expand; x += expand) {
            for (double z = -expand; z <= expand; z += expand) {
                if (player.getLocation().clone().add(z, 0, x).getBlock().isLiquid()) {
                    return true;
                }
                if (player.getLocation().clone().add(z, player.getEyeLocation().getY(), x).getBlock().isLiquid()) {
                    return true;
                }
            }
        }
        return false;
    }

    public static boolean isOnLilyOrCarpet(Player player) {
        Location loc = player.getLocation();
        double expand = 0.3;
        for (double x = -expand; x <= expand; x += expand) {
            for (double z = -expand; z <= expand; z += expand) {
                if (loc.clone().add(z, 0, x).getBlock().getType().toString().contains("LILY")
                        || loc.clone().add(z, -0.001, x).getBlock().getType().toString().contains("CARPET")) {
                    return true;
                }
            }
        }
        return false;
    }

    public static boolean isInWeb(Player player) {
        double expand = 0.3;
        for (double x = -expand; x <= expand; x += expand) {
            for (double z = -expand; z <= expand; z += expand) {
                if (player.getLocation().clone().add(z, 0, x).getBlock().getType() == Material.WEB) {
                    return true;
                }
                if (player.getLocation().clone().add(z, player.getEyeLocation().getY(), x).getBlock().getType() == Material.WEB) {
                    return true;
                }
            }
        }
        return false;
    }

    /*
    Movement Utils
     */

    public static double getBaseMovementSpeed(User user, double conveinentMax, boolean blockAmplifiers) {
        conveinentMax += cc.funkemunky.api.utils.PlayerUtils.getPotionEffectLevel(user.getPlayer(), PotionEffectType.SPEED) * (conveinentMax / 2);
        conveinentMax *= (user.getPlayer().getWalkSpeed() / 0.2D);

        if (user.getPlayer().isFlying())
            conveinentMax *= (user.getPlayer().getFlySpeed() / 0.2D);

        if (blockAmplifiers) {
            if (blockNearHead(user.getPlayer()) && (System.currentTimeMillis() - user.getLastOnIce() < 2000
                    || System.currentTimeMillis() - user.getLastOnSlime() < 2500)) {
                conveinentMax += 2D * (conveinentMax / 1.2);
            } else {
                if (blockNearHead(user.getPlayer())) {
                    conveinentMax += 0.63D * conveinentMax;
                }
                if (System.currentTimeMillis() - user.getLastOnIce() < 2500) {
                    conveinentMax += 0.63D * conveinentMax;
                }
                if (System.currentTimeMillis() - user.getLastOnSlime() < 2500) {
                    conveinentMax += 0.63D * conveinentMax;
                }
            }
        }

        return conveinentMax;
    }

}
