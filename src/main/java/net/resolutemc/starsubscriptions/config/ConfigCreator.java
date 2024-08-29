package net.resolutemc.starsubscriptions.config;

import net.resolutemc.starsubscriptions.StarSubscriptions;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.util.Locale;

public enum ConfigCreator {

    MESSAGES, PLAYER_DATA, CACHED_PLAYERS, SAVED_DATE, GUI_CONFIG;

    public File getFile() {
        return new File(StarSubscriptions.getInstance().getDataFolder(), this.toString().toLowerCase(Locale.ROOT) + ".yml");
    }

    public FileConfiguration get() {
        return YamlConfiguration.loadConfiguration(getFile());
    }

    public void save(FileConfiguration configuration) {
        try {
            configuration.save(getFile());
        }catch (IOException exception) {
            exception.printStackTrace();
        }
    }

    public void create() {
        StarSubscriptions.getInstance().saveResource(this.toString().toLowerCase(Locale.ROOT) + ".yml", false);
    }
}
