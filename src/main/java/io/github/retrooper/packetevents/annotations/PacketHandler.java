package io.github.retrooper.packetevents.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
/**
 * Just like Bukkit's @EventHandler, but for packets.
 * Make sure your class implements the PacketListener interface, and is registered!
 */
public @interface PacketHandler {


}
