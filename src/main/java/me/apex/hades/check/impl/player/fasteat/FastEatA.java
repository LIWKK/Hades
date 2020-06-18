package me.apex.hades.check.impl.player.fasteat;

import me.apex.hades.check.Check;
import me.apex.hades.check.CheckInfo;
import me.apex.hades.event.AnticheatEvent;
import me.apex.hades.user.User;

@CheckInfo(name = "FastEat", type = "A")
public class FastEatA extends Check {

    @Override
    public void onHandle(User user, AnticheatEvent e){

    }

    long startEat;

    /*@EventHandler
    public void onEvent(Event e){
        if (e instanceof PlayerInteractEvent){
            PlayerInteractEvent event = (PlayerInteractEvent)e;
            if (event.hasItem()) {
                if (event.getAction() == Action.RIGHT_CLICK_AIR || event.getAction() == Action.RIGHT_CLICK_BLOCK) {
                    Material m = event.getItem().getType();
                    if (ItemUtils.isFood(m)) {
                        startEat = System.currentTimeMillis();
                    }
                }
            }
        }else if (e instanceof PlayerItemConsumeEvent){
            long diff = (System.currentTimeMillis() - startEat);
            User user = UserManager.INSTANCE.getUser(((PlayerItemConsumeEvent) e).getPlayer().getUniqueId());
            if (diff <= 1400) {
                flag(user, "ate food faster! Time taken to eat: " + diff);
            }
        }
    }*/
}
