package me.apex.hades.utils.config;

import me.apex.hades.HadesPlugin;

public class ConfigLoader {

    public void load() {
        ConfigFile.getInstance().setup(HadesPlugin.getInstance());
        ConfigFile.getInstance().writeDefaults();


        HadesPlugin.getInstance().getConfigManager().setAlertMessage(convertColor(ConfigFile.getInstance().getData().getString("Messages.Alert")));

        HadesPlugin.getInstance().getConfigManager().setPunishmentsEnabled(ConfigFile.getInstance().getData().getBoolean("Punishment.enabled"));
        HadesPlugin.getInstance().getConfigManager().setMaxPunishmentVL(ConfigFile.getInstance().getData().getInt("Punishment.maxViolation"));
        HadesPlugin.getInstance().getConfigManager().setPunishmentBroadcast(ConfigFile.getInstance().getData().getBoolean("Punishment.broadcast"));
        HadesPlugin.getInstance().getConfigManager().setBanMessages(ConfigFile.getInstance().getData().getStringList("Punishment.broadcastMessage"));
        HadesPlugin.getInstance().getConfigManager().setBanCommands(ConfigFile.getInstance().getData().getStringList("Punishment.banCommands"));


    }

    public String convertColor(String in) {
        return in.replace("&", "§");
    }
}
