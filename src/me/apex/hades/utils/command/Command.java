package me.apex.hades.utils.command;

import me.apex.hades.data.User;

public abstract class Command {

    private String name;

    public Command(String name)
    {
        this.name = name;
    }

    public String getName()
    {
        return name;
    }

    public abstract void onCommand(User user, String[] args);

}
