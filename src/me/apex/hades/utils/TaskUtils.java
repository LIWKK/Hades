package me.apex.hades.utils;

import me.apex.hades.Hades;

public class TaskUtils {

    public static void run(Runnable runnable) {
        Hades.getInstance().getServer().getScheduler().runTask(Hades.getInstance(), runnable);
    }

    public static void runAsync(Runnable runnable) {
        Hades.getInstance().getServer().getScheduler().runTaskAsynchronously(Hades.getInstance(), runnable);
    }

}
