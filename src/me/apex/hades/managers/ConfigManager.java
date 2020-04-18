package me.apex.hades.managers;

import me.apex.hades.Hades;

import java.io.File;

public class ConfigManager {

    public ConfigManager() {
        checkConfig();
    }

    private void checkConfig() {
        // Don't need to check if config exist there
        Hades.instance.saveDefaultConfig();
    }

    public void saveConfig() {
        Hades.instance.saveConfig();
    }

    public void resetConfig() {
        File config = new File(Hades.instance.getDataFolder(), "config.yml");
        config.delete();
        Hades.instance.saveDefaultConfig();
        Hades.instance.reloadConfig();
    }
}
