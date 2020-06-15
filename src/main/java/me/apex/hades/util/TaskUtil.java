package me.apex.hades.util;

import me.apex.hades.HadesPlugin;

public class TaskUtil {

    public static void run(Runnable runnable) {
        HadesPlugin.instance.getServer().getScheduler().runTask(HadesPlugin.instance, runnable);
    }

    public static void runAsync(Runnable runnable) {
        HadesPlugin.instance.getServer().getScheduler().runTaskAsynchronously(HadesPlugin.instance, runnable);
    }

}
