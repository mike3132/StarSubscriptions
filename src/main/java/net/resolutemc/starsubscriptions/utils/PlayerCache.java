package net.resolutemc.starsubscriptions.utils;

import net.resolutemc.starsubscriptions.StarSubscriptions;
import net.resolutemc.starsubscriptions.config.ConfigCreator;
import org.bukkit.configuration.file.FileConfiguration;

public class PlayerCache {

    private final String prefix = "Cached Players";

    public void setConfig() {
        FileConfiguration config = ConfigCreator.CACHED_PLAYERS.get();
        config.set(prefix, StarSubscriptions.getInstance().getCachedPlayers());
        ConfigCreator.CACHED_PLAYERS.save(config);
    }

    public void getConfig() {
        FileConfiguration config = ConfigCreator.CACHED_PLAYERS.get();
        for (String uuid : config.getStringList(prefix)) {
            if (StarSubscriptions.getInstance().getCachedPlayers().contains(uuid)) return;
            StarSubscriptions.getInstance().getCachedPlayers().add(uuid);
        }
    }
}
