package me.apex.hades.utils.other;

import org.bukkit.Material;

public class ItemUtils {
    public static boolean isFood(Material m) {

        return (m == Material.COOKED_BEEF || m == Material.COOKED_CHICKEN || m == Material.COOKED_FISH
                || m == Material.GRILLED_PORK || m == Material.PORK || m == Material.MUSHROOM_SOUP
                || m == Material.RAW_BEEF || m == Material.RAW_CHICKEN || m == Material.RAW_FISH
                || m == Material.APPLE || m == Material.GOLDEN_APPLE || m == Material.MELON
                || m == Material.COOKIE || m == Material.BREAD || m == Material.SPIDER_EYE
                || m == Material.ROTTEN_FLESH || m == Material.POTATO_ITEM);

    }
}
