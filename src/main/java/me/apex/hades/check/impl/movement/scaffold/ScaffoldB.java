package me.apex.hades.check.impl.movement.scaffold;

import io.github.retrooper.packetevents.event.PacketEvent;
import me.apex.hades.check.Check;
import me.apex.hades.check.CheckInfo;
import me.apex.hades.event.impl.bukkitevents.InteractEvent;
import me.apex.hades.user.User;
import me.apex.hades.util.TaskUtil;

@CheckInfo(name = "Scaffold", type = "B")
public class ScaffoldB extends Check {
    @Override
    public void onHandle(PacketEvent e, User user){
        if (e instanceof InteractEvent){
            TaskUtil.task(() -> {
                if (((InteractEvent) e).getBlockFace() != null){
                    
                }
            });
        }
    }
}
