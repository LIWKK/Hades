package me.apex.hades.check;

import me.apex.hades.event.AnticheatEvent;
import me.apex.hades.user.User;

public interface ClassInterface {
    void onHandle(User user, AnticheatEvent e);
}
