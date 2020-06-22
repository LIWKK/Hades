package me.apex.hades.user;

import lombok.Getter;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

@Getter
public class UserManager {

    public static List<User> users = new ArrayList<>();

    public static User getUser(String name) {
        return users.stream().filter(user -> user.getPlayer().getName().equalsIgnoreCase(name)).findFirst().orElse(null);
    }

    public static User getUser(Player player) {
        return users.stream().filter(user -> user.getPlayer() == player).findFirst().orElse(null);
    }

}
