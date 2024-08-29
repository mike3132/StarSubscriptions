package net.resolutemc.starsubscriptions;

import net.resolutemc.starsubscriptions.chat.ColorTranslate;
import net.resolutemc.starsubscriptions.commands.SubscriptionCommand;
import net.resolutemc.starsubscriptions.config.ConfigCreator;
import net.resolutemc.starsubscriptions.events.PlayerEvent;
import net.resolutemc.starsubscriptions.hook.PapiHook;
import net.resolutemc.starsubscriptions.utils.DateUtils;
import net.resolutemc.starsubscriptions.utils.PlayerCache;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;

public final class StarSubscriptions extends JavaPlugin {

    private static StarSubscriptions INSTANCE;
    private final List<String> cachedPlayers = new ArrayList<>();
    private final DateUtils dateUtils = new DateUtils();
    private final PlayerCache playerCache = new PlayerCache();
    private final String logMessage = ColorTranslate.chatColor("&a> &5Background saving player data");
    private final int saveInterval = getConfig().getInt("Global-Save-Interval");

    public List<String> getCachedPlayers() {
        return cachedPlayers;
    }

    @Override
    public void onEnable() {
        // Plugin startup logic
        INSTANCE = this;

        if (Bukkit.getPluginManager().getPlugin("PlaceholderAPI") == null) {
            getServer().getConsoleSender().sendMessage(ColorTranslate.chatColor("&4ERROR: &cPlaceholder API not found disabling"));
            Bukkit.getPluginManager().disablePlugin(this);
            return;
        }

        new PapiHook().register();

        // Debug code
        getServer().getConsoleSender().sendMessage(ColorTranslate.chatColor("&6Star &eSubscriptions &a> &2Enabled"));
        getServer().getConsoleSender().sendMessage(ColorTranslate.chatColor("&4WARNING: &cThis is a development version of Star Subscriptions"));

        // Event loaders
        Bukkit.getPluginManager().registerEvents(new PlayerEvent(), this);

        // Command registers
        registerCommands();

        // Config loader
        ConfigCreator.MESSAGES.create();
        ConfigCreator.PLAYER_DATA.create();
        ConfigCreator.SAVED_DATE.create();
        ConfigCreator.CACHED_PLAYERS.create();
        ConfigCreator.GUI_CONFIG.create();
        saveDefaultConfig();
        dateUtils.setConfigDate();
        playerCache.getConfig();


        new BukkitRunnable() {
            @Override
            public void run() {
                getLogger().log(Level.INFO, logMessage);
                playerCache.setConfig();
            }
        }.runTaskTimer(this, 20, saveInterval);

    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic

        getServer().getConsoleSender().sendMessage(ColorTranslate.chatColor("&6Star &eSubscriptions &a> &4Disabled"));

        playerCache.setConfig();
    }

    public static StarSubscriptions getInstance() {
        return INSTANCE;
    }

    private void registerCommands() {
        new SubscriptionCommand();
    }

}
