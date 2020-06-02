package me.apex.hades.command.api;

import java.util.ArrayList;
import java.util.List;

import me.apex.hades.objects.User;

public enum CommandManager {
    INSTANCE;

    private final List<CommandAdapter> adapters = new ArrayList<>();

    public void register(CommandAdapter adapter) {
        adapters.add(adapter);
    }

    public void unregister(CommandAdapter adapter) {
        adapters.remove(adapter);
    }

    public List<CommandAdapter> getAdapters() {
        return adapters;
    }

    public boolean handleCommand(User user, UserInput userInput) {
        for (CommandAdapter adapter : adapters) {
            if (adapter.onCommand(user, userInput))
                return true;
        }
        return false;
    }
}
