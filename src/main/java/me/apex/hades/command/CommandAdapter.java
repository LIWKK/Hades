package me.apex.hades.command;

import org.bukkit.entity.Player;

public abstract class CommandAdapter {

    public abstract boolean onCommand(Player player, UserInput input);

}
