package net.resolutemc.starsubscriptions.chat;

import net.resolutemc.starsubscriptions.StarSubscriptions;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import java.io.File;

public class PlayerMessage {

    public static void sendMessage(Player player, String key) {
        File messagesConfig = new File(StarSubscriptions.getInstance().getDataFolder(), "messages.yml");
        YamlConfiguration configMessages = YamlConfiguration.loadConfiguration(messagesConfig);
        String message = configMessages.getString("Messages.Prefix") + configMessages.getString("Messages." + key);
        player.sendMessage(ChatColor.translateAlternateColorCodes('&', message));
    }

    public static void sendPlayerPlaceholderMessage(Player player, String key, String amount) {
        File messagesConfig = new File(StarSubscriptions.getInstance().getDataFolder(), "messages.yml");
        YamlConfiguration configMessages = YamlConfiguration.loadConfiguration(messagesConfig);
        String message = configMessages.getString("Messages.Prefix") + configMessages.getString("Messages." + key);
        message = message.replace("%StarSubscriptions_Days%", amount);
        player.sendMessage(ChatColor.translateAlternateColorCodes('&', message));
    }


}
