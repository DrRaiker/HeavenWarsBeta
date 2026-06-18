package drraiker.heavenWarsBeta.Util;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.Plugin;

import java.io.File;

public class Configs {

    private static File configFile;
    public static FileConfiguration config;

    private static File dataFile;
    public static FileConfiguration data;

    public void load(Plugin plugin) {

        configFile = new File(plugin.getDataFolder(), "config.yml");
        config = YamlConfiguration.loadConfiguration(configFile);

        saveConfig();

        ConfigLoader.update();

        dataFile = new File(plugin.getDataFolder(), "data.yml");
        data = YamlConfiguration.loadConfiguration(dataFile);

        saveData();
    }

        public static void saveConfig() {
        try {
            config.save(configFile);
        } catch (Exception e) {}
    }

    public static void saveData() {
        try {
            data.save(dataFile);
        } catch (Exception e) {}
    }

}
