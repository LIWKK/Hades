package net.badlion.blccpsapibukkit;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import me.apex.hades.Hades;
import net.badlion.blccpsapibukkit.listener.PlayerListener;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.logging.Level;

public class BlcCpsApiBukkit {

    public static BlcCpsApiBukkit instance;

    public static final Gson GSON_NON_PRETTY = new GsonBuilder().enableComplexMapKeySerialization().disableHtmlEscaping().create();
    private static final Gson GSON_PRETTY = new GsonBuilder().enableComplexMapKeySerialization().disableHtmlEscaping().setPrettyPrinting().create();

    private Conf conf;
    private Hades plugin;

    public void load(Hades plugin)
    {
        instance = this;
        this.plugin = plugin;
        try {
            this.conf = loadConf(new File(plugin.getDataFolder(), "config.json"));

            // Register channel
            plugin.getServer().getMessenger().registerOutgoingPluginChannel(plugin, "badlion:cps");

            // Only register the listener if the config loads successfully
            plugin.getServer().getPluginManager().registerEvents(new PlayerListener(plugin), plugin);

            plugin.getLogger().log(Level.INFO, "Successfully setup BlcCpsApi.");
        } catch (Exception ex) {
            plugin.getLogger().log(Level.SEVERE, "Error with config for BlcCpsApi : " + ex.getMessage(), ex);
        }
    }

    private Conf loadConf(File file) {
        FileReader fileReader = null;

        try {
            fileReader = new FileReader(file);
            return BlcCpsApiBukkit.GSON_NON_PRETTY.fromJson(fileReader, Conf.class);
        } catch (FileNotFoundException ex) {
            plugin.getLogger().log(Level.INFO,"No Config Found: Saving default...");
            Conf conf = new Conf();
            this.saveConf(conf, file);
            return conf;
        } finally {
            if (fileReader != null) {
                try {
                    fileReader.close();
                } catch (IOException ignored) {

                }
            }
        }
    }

    private void saveConf(Conf conf, File file) {
        FileWriter fileWriter = null;

        try {
            fileWriter = new FileWriter(file);
            BlcCpsApiBukkit.GSON_PRETTY.toJson(conf, fileWriter);
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            if (fileWriter != null) {
                try {
                    fileWriter.close();
                } catch (IOException ignored) {

                }
            }
        }
    }

    public Conf getConf() {
        return this.conf;
    }
}
