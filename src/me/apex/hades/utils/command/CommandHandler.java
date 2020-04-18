package me.apex.hades.utils.command;

import me.apex.hades.data.User;
import me.apex.hades.event.Event;
import me.apex.hades.event.EventHandler;
import me.apex.hades.event.impl.CommandEvent;
import me.apex.hades.managers.CommandManager;

public class CommandHandler extends EventHandler {

    public CommandHandler(User user) {
        super(user);
    }

    @Override
    public void handle(Event event) {
        if(event instanceof CommandEvent)
        {
            CommandEvent e = (CommandEvent) event;
            if(e.getMessage().contains(" "))
            {
                String[] command = e.getMessage().split(" ");
                String name = command[0].replace("/", "");
                String[] args = new String[command.length - 1];
                for(int i = 0; i < command.length - 1; i++)
                {
                    args[i] = command[i + 1];
                }
                for(Command cmd : CommandManager.INSTANCE.commands)
                {
                    if(cmd.getName().equalsIgnoreCase(name))
                    {
                        cmd.onCommand(user, args);
                        e.setCancelled(true);
                        break;
                    }
                }
            }else
            {
                String name = e.getMessage().replace("/", "");
                for(Command cmd : CommandManager.INSTANCE.commands)
                {
                    if(cmd.getName().equalsIgnoreCase(name))
                    {
                        cmd.onCommand(user, new String[0]);
                        e.setCancelled(true);
                        break;
                    }
                }
            }
        }
    }
}
