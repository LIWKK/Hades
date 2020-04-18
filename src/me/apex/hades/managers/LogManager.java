package me.apex.hades.managers;

import me.apex.hades.data.User;
import me.apex.hades.utils.TextFile;

import java.util.Date;

public enum LogManager {
    INSTANCE;

    public void addLog(User user, String info)
    {
        TextFile logFile = user.data.logFile;
        logFile.addLine("(" + new Date().toString() + ") " + info);
        logFile.write();
    }
}
