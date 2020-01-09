package me.apex.hades.check.api;

import cc.funkemunky.api.events.impl.PacketReceiveEvent;
import lombok.Getter;
import lombok.Setter;
import me.apex.hades.Hades;
import me.apex.hades.data.User;
import me.apex.hades.listeners.event.HadesFlagEvent;
import org.bukkit.Bukkit;
import java.util.ArrayList;
import java.util.List;

public abstract class Check {

    public enum CheckType { COMBAT, MOVEMENT, PACKET }

    //Info
    @Getter
    private List<Violation> violations;

    @Getter
    @Setter
    private boolean enabled, punishable;

    @Getter
    public double vl, maxViolations;

    public Check()
    {
        violations = new ArrayList();

        enabled = Hades.getInstance().getConfig().getBoolean("checks." + getCheckType().toString().toLowerCase() + "." + getName().toLowerCase().split(" ")[0] + "." + getName().toLowerCase().split("\\(")[1].replace(")", "") + ".enabled");
        punishable = Hades.getInstance().getConfig().getBoolean("checks." + getCheckType().toString().toLowerCase() + "." + getName().toLowerCase().split(" ")[0] + "." + getName().toLowerCase().split("\\(")[1].replace(")", "") + ".punishable");
        maxViolations = Hades.getInstance().getConfig().getDouble("checks." + getCheckType().toString().toLowerCase() + "." + getName().toLowerCase().split(" ")[0] + "." + getName().toLowerCase().split("\\(")[1].replace(")", "") + ".max-vl");
    }

    protected void flag(User user, String information)
    {
        if(user == null || user.getPlayer() == null) return;
        violations.add(new Violation(information));
        Bukkit.getPluginManager().callEvent(new HadesFlagEvent(user.getPlayer(), this, information));
    }

    public abstract void onPacket(PacketReceiveEvent e, User user);

    //Get Check Info
    public String getName()
    {
        return CheckManager.INSTANCE.getCheckInfo(this).Name();
    }

    public CheckType getCheckType()
    {
        return CheckManager.INSTANCE.getCheckInfo(this).Type();
    }

    public boolean isExperimental()
    {
        return CheckManager.INSTANCE.getCheckInfo(this).Experimental();
    }

}
