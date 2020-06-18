package me.apex.hades.listeners;

import me.apex.hades.HadesPlugin;
import me.apex.hades.tinyprotocol.api.TinyProtocolHandler;
import me.apex.hades.user.User;
import org.bukkit.GameMode;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.player.*;

import static org.bukkit.event.EventPriority.LOWEST;

public class BukkitEvents implements Listener {

    @EventHandler(priority = LOWEST)
    public void onJoin(PlayerJoinEvent e) {
        HadesPlugin.userManager.addUser(new User(e.getPlayer()));
        TinyProtocolHandler.getInstance().addChannel(e.getPlayer());
    }

    @EventHandler
    public void onQuit(PlayerQuitEvent e) {
        User user = HadesPlugin.userManager.getUser(e.getPlayer().getUniqueId());
        if (user != null) {
            user.getCheckList().forEach(check -> HadesPlugin.getInstance().getEventManager().unregisterListener(check));
            TinyProtocolHandler.getInstance().removeChannel(e.getPlayer());
        }
    }

    @EventHandler
    public void onGamemodeChange(PlayerGameModeChangeEvent e) {
        User user = HadesPlugin.userManager.getUser(e.getPlayer().getUniqueId());
        if (user != null) {
            if (e.getNewGameMode() != GameMode.CREATIVE) {
                user.setMovementVerifyStage(0);
                user.setWaitingForMovementVerify(true);
            } else if (e.getNewGameMode() == GameMode.CREATIVE) {
                user.setMovementVerifyStage(0);
                user.setWaitingForMovementVerify(false);
            }
            user.setLastGamemodeSwitch(System.currentTimeMillis());
            user.setSwitchedGamemodes(true);
        }
    }

    @EventHandler(priority = EventPriority.MONITOR)
    public void onFlightToggle(PlayerToggleFlightEvent e) {
        User user = HadesPlugin.userManager.getUser(e.getPlayer().getUniqueId());
        if (user != null) {
            user.setSwitchedGamemodes(true);
        }
    }
    @EventHandler
    public void onTeleport(PlayerTeleportEvent e) {
        User user = HadesPlugin.userManager.getUser(e.getPlayer().getUniqueId());
        if (user != null) {

            if (e.getCause() == PlayerTeleportEvent.TeleportCause.UNKNOWN && Math.abs(user.getTo().getY() - user.getFrom().getY()) == 0.0 && user.isOnGround() && user.isClientGround()) {
                user.setDidUnknownTeleport(true);
                user.setUnknownTeleportTick(user.getConnectedTick());
            }

            if (e.getCause() != PlayerTeleportEvent.TeleportCause.UNKNOWN) {
                user.setWaitingForMovementVerify(true);
            }

            if (e.getCause() == PlayerTeleportEvent.TeleportCause.UNKNOWN && Math.abs(user.getTo().getY() - user.getFrom().getY()) == 0.0) {
                user.setLastUnknownTeleport(System.currentTimeMillis());
            }

            if (e.getCause() == PlayerTeleportEvent.TeleportCause.UNKNOWN && (e.getTo().getY() - e.getFrom().getY()) == 0.0 && user.isOnGround() && user.isLastOnGround()) {
                user.lastUnknownValidTeleport = System.currentTimeMillis();
            }

            if (e.getCause() != PlayerTeleportEvent.TeleportCause.UNKNOWN) {
                user.setLastTeleport(System.currentTimeMillis());
            }
            user.setLastFullTeleport(System.currentTimeMillis());
        }
    }
    @EventHandler
    public void onEntityDamage(EntityDamageByEntityEvent e) {


        if (e.getDamager() instanceof Arrow) {
            User user = HadesPlugin.userManager.getUser(e.getEntity().getUniqueId());
            if (user != null) {
                Arrow arrow = (Arrow) e.getDamager();
                user.setLastBowDamage(System.currentTimeMillis());
                user.setLastBowStrength(arrow.getKnockbackStrength());
            }
        }

        if (e.getEntity() instanceof Player && e.getDamager() instanceof Player) {
            User user = HadesPlugin.userManager.getUser(e.getEntity().getUniqueId());
            if (user != null) {


                if (e.getCause() == EntityDamageEvent.DamageCause.ENTITY_ATTACK) {
                    int ticks = user.getCancelTicks();
                    if (e.isCancelled()) {
                        ticks += (ticks < 20 ? 1 : 0);
                    } else {
                        ticks -= (ticks > 0 ? 5 : 0);
                    }
                    user.setCancelTicks(ticks);
                }
            }

            User damageUser = HadesPlugin.userManager.getUser(e.getEntity().getUniqueId());
            if (damageUser != null) {

                if (e.getCause() == EntityDamageEvent.DamageCause.ENTITY_ATTACK) {
                    int ticks = damageUser.getNoDamageTicks();
                    if (e.isCancelled()) {
                        ticks += (ticks < 20 ? 1 : 0);
                    } else {
                        ticks -= (ticks > 0 ? 5 : 0);
                    }
                    damageUser.setNoDamageTicks(ticks);
                }

                if (e.getCause() == EntityDamageEvent.DamageCause.ENTITY_ATTACK) damageUser.setLastEntityDamageAttack(System.currentTimeMillis());
                damageUser.setLastEntityDamage(System.currentTimeMillis());
            }
        }
    }

    @EventHandler
    public void onDamage(EntityDamageEvent e) {
        if (e.getEntity() instanceof Player) {
            User user = HadesPlugin.userManager.getUser(e.getEntity().getUniqueId());
            if (user != null) {
                if (e.getCause() != EntityDamageEvent.DamageCause.FALL) {
                    user.setLastRandomDamage(System.currentTimeMillis());
                }

                switch (e.getCause()) {

                    case ENTITY_ATTACK:
                        user.setLastEntityDamage(System.currentTimeMillis());
                        break;

                    case SUFFOCATION:
                        user.lastUnknownValidTeleport = System.currentTimeMillis();
                        break;

                    case FALL:
                        user.setLastFallDamage(System.currentTimeMillis());
                        break;

                    case FIRE:
                    case FIRE_TICK:
                        user.setLastFireDamage(System.currentTimeMillis());
                        break;

                    case PROJECTILE:
                        user.setLastBowDamage(System.currentTimeMillis());
                        break;

                    case ENTITY_EXPLOSION:
                        user.setLastExplode(System.currentTimeMillis());
                        if (!user.isExplode()) {
                            user.setExplode(true);
                        }
                        break;
                }
            }
        }
    }


    @EventHandler(priority = EventPriority.MONITOR)
    public void onBlockBreak(BlockBreakEvent e) {
        User user = HadesPlugin.userManager.getUser(e.getPlayer().getUniqueId());
        if (user != null) {
            user.setLastBlockBreakCancel(System.currentTimeMillis());
        }
    }

    @EventHandler(priority = EventPriority.MONITOR)
    public void onBlockPlace(BlockPlaceEvent e) {
        User user = HadesPlugin.userManager.getUser(e.getPlayer().getUniqueId());
        if (user != null) {
            if (e.isCancelled()) {
                user.setLastBlockCancel(System.currentTimeMillis());
            }
            user.setLastBlockPlace(System.currentTimeMillis());
            user.setLastBlockPlaceTick(user.getConnectedTick());
        }
    }
}
