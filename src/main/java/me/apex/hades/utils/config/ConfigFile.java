package me.apex.hades.utils.config;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginDescriptionFile;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ConfigFile {

    private ConfigFile() { }

    static ConfigFile instance = new ConfigFile();

    public static ConfigFile getInstance() {
        return instance;
    }

    Plugin p;

    FileConfiguration config;
    File cfile;

    FileConfiguration data;
    File dfile;

    public void setup(Plugin p) {
        config = p.getConfig();
        if (!p.getDataFolder().exists()) {
            p.getDataFolder().mkdir();
        }
        dfile = new File("plugins/Hades/config.yml");

        if (!dfile.exists()) {
            try {
                dfile.createNewFile();
            }
            catch (IOException e) {
            }
        }

        data = YamlConfiguration.loadConfiguration(dfile);

    }

    public FileConfiguration getData() {
        return data;
    }


    public void writeDefaults() {

        if (!data.contains("Lag.maxTime")) data.set("Lag.maxTime", 5000L);
        if (!data.contains("Lag.maxDisableSeconds")) data.set("Lag.maxDisableSeconds", 10);

        if (!data.contains("Messages.Alert")) data.set("Messages.Alert", "&c[Demon] // &f%PLAYER% &7has failed &f%CHECK% &f%TYPE% &c(x%VL%)");

        if (!data.contains("Punishment.enabled")) data.set("Punishment.enabled", false);
        if (!data.contains("Punishment.maxViolation")) data.set("Punishment.maxViolation", 20);
        if (!data.contains("Punishment.broadcast")) data.set("Punishment.broadcast", true);
        List<String> broadcastMessages = new ArrayList<>();
        broadcastMessages.add("%LINE%");
        broadcastMessages.add("&cDemon // &f%PLAYER% was removed for using Unfair Advantages.");
        broadcastMessages.add("%LINE%");
        if (!data.contains("Punishment.broadcastMessage")) data.set("Punishment.broadcastMessage", broadcastMessages);

        List<String> banCommands = new ArrayList<>();
        banCommands.add("ban %PLAYER% cheating");
        if (!data.contains("Punishment.banCommands")) data.set("Punishment.banCommands", banCommands);

        saveData();
    }

    public void saveData() {
        try {
            data.save(dfile);
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void reloadData() {
        data = YamlConfiguration.loadConfiguration(dfile);
    }

    public FileConfiguration getConfig() {
        return config;
    }

    public void saveConfig() {
        try {
            config.save(cfile);
        }
        catch (IOException e) {
        }
    }

    public void reloadConfig() {
        config = YamlConfiguration.loadConfiguration(cfile);
    }

    public PluginDescriptionFile getDesc() {
        return p.getDescription();
    }
}