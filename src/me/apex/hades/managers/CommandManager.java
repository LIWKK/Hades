package me.apex.hades.managers;

import me.apex.hades.utils.command.Command;
import me.apex.hades.utils.command.impl.*;

import java.util.ArrayList;
import java.util.List;

public enum CommandManager {
    INSTANCE;

    public List<Command> commands = new ArrayList<>();

    public void addDefaultCommands()
    {
        commands.add(new AnticheatCommand());
        commands.add(new InventoryCommand());
    }

}
