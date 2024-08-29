package net.resolutemc.starsubscriptions.utils;

import net.resolutemc.starsubscriptions.StarSubscriptions;
import net.resolutemc.starsubscriptions.chat.ColorTranslate;
import net.resolutemc.starsubscriptions.chat.PlayerMessage;
import net.resolutemc.starsubscriptions.config.ConfigCreator;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.logging.Level;

public class DateUtils {

    private String date;
    private final String dateString = "Current Date:";

    PlayerData playerData = new PlayerData();
    PlayerCache playerCache = new PlayerCache();
    SubscriptionGui subscriptionGui = new SubscriptionGui();

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    private void dateFormatter() {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("MM/dd/yyyy");
        LocalDateTime now = LocalDateTime.now();
        String currentDate = dtf.format(now);
        setDate(currentDate);
    }

    public void setConfigDate() {
        FileConfiguration config = ConfigCreator.SAVED_DATE.get();

        dateFormatter();
        config.set(dateString, getDate());
        ConfigCreator.SAVED_DATE.save(config);
    }

    public String getConfigDate() {
        FileConfiguration config = ConfigCreator.SAVED_DATE.get();
        return config.getString(dateString);
    }

    public void dateCheck(Player player) {
        boolean guiEnabled = StarSubscriptions.getInstance().getConfig().getBoolean("Daily-Gui-Enabled");

        dateFormatter();
        if (!getDate().equals(getConfigDate())) {
            String logMessage = ColorTranslate.chatColor("&5It's a new day, Setting config date + removing automatic amount from players");
            StarSubscriptions.getInstance().getLogger().log(Level.INFO, logMessage);
            setConfigDate();
            playerData.removeAmountAutomatic();
            PlayerMessage.sendMessage(player, "Player-Login-New-Day");
            StarSubscriptions.getInstance().getCachedPlayers().clear();
            playerCache.setConfig();
            StarSubscriptions.getInstance().getCachedPlayers().add(player.getUniqueId().toString());

            // Gui stuffs
            if (!guiEnabled) return;
            subscriptionGui.getGui(player).show(player);
            return;
        }
        PlayerMessage.sendMessage(player, "Player-Login-Same-Day");
        if (StarSubscriptions.getInstance().getCachedPlayers().contains(player.getUniqueId().toString())) return;
        StarSubscriptions.getInstance().getCachedPlayers().add(player.getUniqueId().toString());
    }
}
