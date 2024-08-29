package net.resolutemc.starsubscriptions.chat;

import me.clip.placeholderapi.PlaceholderAPI;
import net.resolutemc.starsubscriptions.StarSubscriptions;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import java.io.File;

public class ConsoleMessage {

    public static void sendMessage(CommandSender sender, String key) {
        File messagesConfig = new File(StarSubscriptions.getInstance().getDataFolder(), "messages.yml");
        YamlConfiguration configMessages = YamlConfiguration.loadConfiguration(messagesConfig);
        String message = configMessages.getString("Messages.Prefix") + configMessages.getString("Messages." + key);
        sender.sendMessage(ChatColor.translateAlternateColorCodes('&', message));
    }

    public static void sendPlayerWithAmountPlaceholderMessage(CommandSender sender, String key, String target, String amount) {
        File messagesConfig = new File(StarSubscriptions.getInstance().getDataFolder(), "messages.yml");
        YamlConfiguration configMessages = YamlConfiguration.loadConfiguration(messagesConfig);
        String message = configMessages.getString("Messages.Prefix") + configMessages.getString("Messages." + key);
        message = message.replace("%StarSubscriptions_Days%", amount);
        message = message.replace("%playerName%", target);
        sender.sendMessage(ChatColor.translateAlternateColorCodes('&', message));
    }

    public static void sendPlayerPlaceholderMessage(CommandSender sender, String key, String target) {
        File messagesConfig = new File(StarSubscriptions.getInstance().getDataFolder(), "messages.yml");
        YamlConfiguration configMessages = YamlConfiguration.loadConfiguration(messagesConfig);
        String message = configMessages.getString("Messages.Prefix") + configMessages.getString("Messages." + key);
        message = message.replace("%playerName%", target);
        sender.sendMessage(ChatColor.translateAlternateColorCodes('&', message));
    }

    public static void sendRewards(CommandSender sender, Player player) {
        for (String configList : StarSubscriptions.getInstance().getConfig().getStringList("Daily-Rewards")) {
            String command = PlaceholderAPI.setPlaceholders(player, configList);
            Bukkit.dispatchCommand(sender, ColorTranslate.chatColor(" " + command));
        }
    }



}
