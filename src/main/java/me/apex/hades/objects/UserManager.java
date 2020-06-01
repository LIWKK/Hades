package me.apex.hades.objects;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public enum UserManager {
    INSTANCE;

    private final List<User> users = new ArrayList();

    public void register(User user) {
        users.add(user);
    }

    public void unregister(User user) {
        users.remove(user);
    }

    public User getUser(UUID playerUUID) {
        return users.stream().filter(user -> user.getPlayerUUID() == playerUUID).findFirst().orElse(null);
    }

    public List<User> getUsers() {
        return users;
    }
}
