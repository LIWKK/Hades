package me.apex.hades.utils.other;

import me.apex.hades.HadesPlugin;

public class TaskUtils {

    public static void run(Runnable runnable) {
        HadesPlugin.getInstance().getServer().getScheduler().runTask(HadesPlugin.getInstance(), runnable);
    }

    public static void runAsync(Runnable runnable) {
        HadesPlugin.getInstance().getServer().getScheduler().runTaskAsynchronously(HadesPlugin.getInstance(), runnable);
    }

}
