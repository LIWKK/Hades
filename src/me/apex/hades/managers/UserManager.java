package me.apex.hades.managers;

import me.apex.hades.data.User;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public enum UserManager {
    INSTANCE;

    public List<User> users = new ArrayList<>();

    public User getUser(UUID playerUUID)
    {
        for(User user : users)
        {
            if(user.getPlayer().getUniqueId() == playerUUID)
            {
                return user;
            }
        }
        return null;
    }

}
