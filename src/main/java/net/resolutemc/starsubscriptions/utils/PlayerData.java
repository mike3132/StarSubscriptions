package net.resolutemc.starsubscriptions.utils;

import net.resolutemc.starsubscriptions.StarSubscriptions;
import net.resolutemc.starsubscriptions.chat.ConsoleMessage;
import net.resolutemc.starsubscriptions.chat.PlayerMessage;
import net.resolutemc.starsubscriptions.config.ConfigCreator;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

import java.util.Random;
import java.util.UUID;

public class PlayerData {

    private int amount;

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public void saveConfig(UUID player, Integer amount) {
        FileConfiguration config = ConfigCreator.PLAYER_DATA.get();
        config.set(player.toString(), amount);
        ConfigCreator.PLAYER_DATA.save(config);
    }

    public void loadConfig(UUID player) {
        FileConfiguration config = ConfigCreator.PLAYER_DATA.get();
        setAmount(config.getInt(player.toString()));
    }

    public void addAmount(CommandSender sender, Player player, Integer amount) {
        loadConfig(player.getUniqueId());
        int current = getAmount();


        current = current + amount;
        saveConfig(player.getUniqueId(), current);
        ConsoleMessage.sendPlayerWithAmountPlaceholderMessage(sender, "Admin-Amount-Add", player.getName(), String.valueOf(amount));
        PlayerMessage.sendPlayerPlaceholderMessage(player, "Player-Amount-Add", String.valueOf(amount));
        PlayerMessage.sendPlayerPlaceholderMessage(player, "Player-Amount-Total", String.valueOf(current));
    }

    public void adminGetPlayerAmount(CommandSender sender, Player player) {
        loadConfig(player.getUniqueId());
        ConsoleMessage.sendPlayerWithAmountPlaceholderMessage(sender, "Target-Player-Amount-Total", player.getName(), String.valueOf(getAmount()));
    }

    public void getPlayerAmount(Player player) {
        loadConfig(player.getUniqueId());
        PlayerMessage.sendPlayerPlaceholderMessage(player, "Player-Amount-Total", String.valueOf(getAmount()));
    }

    public void resetPlayerAmount(CommandSender sender, Player player) {
        loadConfig(player.getUniqueId());
        if (amount == 0) {
            ConsoleMessage.sendMessage(sender, "Admin-Amount-Reset-None-Remaining");
            return;
        }
        amount = 0;
        saveConfig(player.getUniqueId(), amount);
        ConsoleMessage.sendPlayerPlaceholderMessage(sender, "Admin-Amount-Reset", player.getName());
    }

    public void removeAmountAutomatic() {
        int amount = StarSubscriptions.getInstance().getConfig().getInt("Join-Amount-To-Take");
        for (String uuid : ConfigCreator.PLAYER_DATA.get().getKeys(false)) {
            UUID player = UUID.fromString(uuid);
            loadConfig(player);
            int current = getAmount();
            if (current < amount) continue;
            current = current - amount;
            saveConfig(player, current);
        }
    }

    public void removeAmountManual(CommandSender sender, Player player, Integer amount) {
        loadConfig(player.getUniqueId());
        int current = getAmount();

        if (current < amount) {
            ConsoleMessage.sendPlayerWithAmountPlaceholderMessage(sender, "Admin-Amount-Remove-More", player.getName(), String.valueOf(current));
            return;
        }
        current = current - amount;

        saveConfig(player.getUniqueId(), current);

        ConsoleMessage.sendPlayerWithAmountPlaceholderMessage(sender, "Admin-Amount-Remove", player.getName(), String.valueOf(amount));
        PlayerMessage.sendPlayerPlaceholderMessage(player, "Player-Amount-Remove", String.valueOf(amount));
        PlayerMessage.sendPlayerPlaceholderMessage(player, "Player-Amount-Total", String.valueOf(current));
    }

    // debug code for making a random player and int in config
    public void makeFakePlayers() {
        FileConfiguration config = ConfigCreator.PLAYER_DATA.get();
        Random randomAmount = new Random();
        String fakeUUID = String.valueOf(UUID.randomUUID());
        int amount = randomAmount.nextInt(10);

        config.set(fakeUUID, amount);
        ConfigCreator.PLAYER_DATA.save(config);
    }

}
