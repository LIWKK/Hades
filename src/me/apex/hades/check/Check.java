package me.apex.hades.check;

import me.apex.hades.Hades;
import me.apex.hades.check.violation.Violation;
import me.apex.hades.data.User;
import me.apex.hades.event.Event;
import me.apex.hades.event.EventHandler;
import me.apex.hades.event.impl.UserViolationEvent;
import me.apex.hades.managers.EventManager;
import me.apex.hades.managers.UserManager;

import java.util.ArrayList;
import java.util.List;

public abstract class Check extends EventHandler {

    public enum CheckType { COMBAT, MOVEMENT, PACKET }

    //Check
    public String name;
    public CheckType type;
    public List<Violation> violations;
    public int maxViolations;
    public boolean enabled, experimental;
    public double vl;

    public Check(User user)
    {
        super(user);
        this.maxViolations = Hades.instance.getConfig().getInt("max-violations");
        violations = new ArrayList<>();
        enabled = true;
    }

    protected void debug(String message)
    {
        // Could make this more compact by putting it into another lambda statement.
        for(User user : UserManager.INSTANCE.users)
        {
            user.debuggingChecks
                    .stream()
                    .filter(check -> check.name.equalsIgnoreCase(this.name))
                    .forEach(check -> user.sendMessage("&c[" + name + "] &7" + message + " &8: &6" + this.user.getPlayer().getName()));
        }
    }

    protected void flag(String message)
    {
        violations.add(new Violation(message));
        EventManager.INSTANCE.call(new UserViolationEvent(this, message), user);

        // Could make this more compact by putting it into another lambda statement.
        for(User user : UserManager.INSTANCE.users)
        {
            user.debuggingChecks
                    .stream()
                    .filter(check -> check.name.equalsIgnoreCase(this.name))
                    .forEach(check -> user.sendMessage("&4[" + name + "] &7" + message + " &8: &6" + this.user.getPlayer().getName()));
        }
    }

    @Override
    public abstract void handle(Event event);
}
