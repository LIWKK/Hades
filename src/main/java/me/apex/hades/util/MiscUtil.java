package me.apex.hades.util;

import me.apex.hades.util.boundingbox.BoundingBox;
import me.apex.hades.util.reflection.ReflectionUtil;
import org.bukkit.Material;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class MiscUtil {
    public static Map<EntityType, Vector> entityDimensions = new HashMap<>();;

    public static boolean isFood(Material m) {

        return (m == Material.COOKED_BEEF || m == Material.COOKED_CHICKEN || m == Material.COOKED_FISH
                || m == Material.GRILLED_PORK || m == Material.PORK || m == Material.MUSHROOM_SOUP
                || m == Material.RAW_BEEF || m == Material.RAW_CHICKEN || m == Material.RAW_FISH
                || m == Material.APPLE || m == Material.GOLDEN_APPLE || m == Material.MELON
                || m == Material.COOKIE || m == Material.BREAD || m == Material.SPIDER_EYE
                || m == Material.ROTTEN_FLESH || m == Material.POTATO_ITEM || m.toString().contains("POTION"));

    }

    //Credits to funkemunky:)
    public static BoundingBox getEntityBoundingBox(LivingEntity entity) {
        if (entityDimensions.containsKey(entity.getType())) {
            Vector entityVector = entityDimensions.get(entity.getType());

            float minX = (float) Math.min(-entityVector.getX() + entity.getLocation().getX(), entityVector.getX() + entity.getLocation().getX());
            float minY = (float) Math.min(entity.getLocation().getY(), entityVector.getY() + entity.getLocation().getY());
            float minZ = (float) Math.min(-entityVector.getZ() + entity.getLocation().getZ(), entityVector.getZ() + entity.getLocation().getZ());
            float maxX = (float) Math.max(-entityVector.getX() + entity.getLocation().getX(), entityVector.getX() + entity.getLocation().getX());
            float maxY = (float) Math.max(entity.getLocation().getY(), entityVector.getY() + entity.getLocation().getY());
            float maxZ = (float) Math.max(-entityVector.getZ() + entity.getLocation().getZ(), entityVector.getZ() + entity.getLocation().getZ());
            return new BoundingBox(minX, minY, minZ, maxX, maxY, maxZ);
        }
        return toBoundingBox(ReflectionUtil.getBoundingBox((Player) entity));
    }

    //Credits to funkemunky:)
    public static BoundingBox toBoundingBox(Object aaBB) {
        Vector min = getBoxMin(aaBB);
        Vector max = getBoxMax(aaBB);

        return new BoundingBox((float) min.getX(), (float) min.getY(), (float) min.getZ(), (float) max.getX(), (float) max.getY(), (float) max.getZ());
    }

    //Credits to funkemunky:)
    private static Vector getBoxMin(Object box) {
        if (hasField(box.getClass(), "a")) {
            double x = (double) getFieldValue(getFieldByName(box.getClass(), "a"), box);
            double y = (double) getFieldValue(getFieldByName(box.getClass(), "b"), box);
            double z = (double) getFieldValue(getFieldByName(box.getClass(), "c"), box);
            return new Vector(x, y, z);
        } else {
            double x = (double) getFieldValue(getFieldByName(box.getClass(), "minX"), box);
            double y = (double) getFieldValue(getFieldByName(box.getClass(), "minY"), box);
            double z = (double) getFieldValue(getFieldByName(box.getClass(), "minZ"), box);
            return new Vector(x, y, z);
        }
    }
    //Credits to funkemunky:)
    public static Field getFieldByName(Class<?> clazz, String fieldName) {
        try {
            Field field = clazz.getDeclaredField(fieldName) != null ? clazz.getDeclaredField(fieldName) : clazz.getSuperclass().getDeclaredField(fieldName);
            field.setAccessible(true);

            return field;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    //Credits to funkemunky:)
    private static Vector getBoxMax(Object box) {
        if (hasField(box.getClass(), "d")) {
            double x = (double) getFieldValue(getFieldByName(box.getClass(), "d"), box);
            double y = (double) getFieldValue(getFieldByName(box.getClass(), "e"), box);
            double z = (double) getFieldValue(getFieldByName(box.getClass(), "f"), box);
            return new Vector(x, y, z);
        } else {
            double x = (double) getFieldValue(getFieldByName(box.getClass(), "maxX"), box);
            double y = (double) getFieldValue(getFieldByName(box.getClass(), "maxY"), box);
            double z = (double) getFieldValue(getFieldByName(box.getClass(), "maxZ"), box);
            return new Vector(x, y, z);
        }
    }

    //Credits to funkemunky:)
    public static boolean hasField(Class<?> object, String fieldName) {
        return Arrays.stream(object.getFields()).anyMatch(field -> field.getName().equalsIgnoreCase(fieldName));
    }

    //Credits to funkemunky:)
    public static Object getFieldValue(Field field, Object object) {
        try {
            field.setAccessible(true);
            return field.get(object);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}