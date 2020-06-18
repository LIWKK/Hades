package me.apex.hades.user;

import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class UserManager {

    public static List<User> users = new ArrayList<>();

    public static User getUser(Player player) {
        return users.stream().filter(user -> user.player == player).findFirst().orElse(null);
    }

}
