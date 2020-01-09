package me.apex.hades.command.api;

import me.apex.hades.data.User;

public abstract class CommandAdapter {

    public abstract boolean onCommand(User user, UserInput input);

}
