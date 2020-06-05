package io.github.retrooper.packetevents.utils;
import java.lang.reflect.InvocationTargetException;

import javax.annotation.Nullable;

public class TPSUtils {
    @Nullable
    public static double[] getRecentTPS() {
        try {
            return NMSUtils.recentTPS();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
        return null;
    }
}
