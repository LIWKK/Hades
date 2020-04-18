package me.apex.hades.command.api;

import me.apex.hades.objects.User;

public abstract class CommandAdapter {

    public abstract boolean onCommand(User user, me.apex.hades.command.api.UserInput input);

}
