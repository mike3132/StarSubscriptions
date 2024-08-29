package net.resolutemc.starsubscriptions.commands;

import net.resolutemc.starsubscriptions.StarSubscriptions;
import net.resolutemc.starsubscriptions.chat.ColorTranslate;
import net.resolutemc.starsubscriptions.chat.ConsoleMessage;
import net.resolutemc.starsubscriptions.chat.PlayerMessage;
import net.resolutemc.starsubscriptions.config.ConfigCreator;
import net.resolutemc.starsubscriptions.utils.PlayerData;
import net.resolutemc.starsubscriptions.utils.SubscriptionGui;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.io.File;

public class SubscriptionCommand implements CommandExecutor {

    PlayerData playerData = new PlayerData();
    SubscriptionGui subscriptionGui = new SubscriptionGui();

    public SubscriptionCommand() {
        StarSubscriptions.getInstance().getCommand("Subscription").setExecutor(this);
    }

    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (sender.hasPermission("StarSubscriptions.Command.Subscription.Admin")) {
            if (args.length == 0) {
                ConsoleMessage.sendMessage(sender, "Not-Enough-Args");
                return false;
            }

            if (args[0].equalsIgnoreCase("Reload")) {
                sender.sendMessage(ColorTranslate.chatColor("&6Star &eSubscriptions &a> &2Config reloaded in " + (System.currentTimeMillis() - 1)));
                StarSubscriptions.getInstance().reloadConfig();
                return true;
            }

            if (args[0].equalsIgnoreCase("Gui")) {
                Player player = (Player) sender;
                sender.sendMessage(ColorTranslate.chatColor("&6Star &eSubscriptions &a> &2Opening reward gui"));
                subscriptionGui.getGui(player).show(player);
                return true;
            }

            if (args[0].equalsIgnoreCase("Pass")) {
                if (args.length == 1) {
                    ConsoleMessage.sendMessage(sender, "Not-Enough-Args");
                    return false;
                }
                Player target = Bukkit.getPlayer(args[1]);
                if (target == null) {
                    ConsoleMessage.sendMessage(sender, "Not-Player-Arg");
                    return false;
                }
                playerData.adminGetPlayerAmount(sender, target);
                return true;
            }

            if (args[0].equalsIgnoreCase("Reset")) {
                if (args.length == 1) {
                    ConsoleMessage.sendMessage(sender, "Not-Enough-Args");
                    return false;
                }
                Player target = Bukkit.getPlayer(args[1]);
                if (target == null) {
                    ConsoleMessage.sendMessage(sender, "Not-Player-Arg");
                    return false;
                }
                playerData.resetPlayerAmount(sender, target);
                return false;
            }

            if (args[0].equalsIgnoreCase("GlobalReset")) {
                if (args.length == 1) {
                    ConsoleMessage.sendMessage(sender, "Admin-Global-Reset-Warning");
                    return false;
                }
                if (args[1].equalsIgnoreCase("really")) {
                    File file = ConfigCreator.PLAYER_DATA.getFile();
                    if (!file.exists()) return false;
                    file.delete();
                    ConfigCreator.PLAYER_DATA.create();
                    ConsoleMessage.sendMessage(sender, "Admin-Global-Reset");
                    return false;
                }
                ConsoleMessage.sendMessage(sender, "Admin-Global-Reset-Invalid-Arg");
                return false;
            }

            if (!args[0].equalsIgnoreCase("Time")) {
                ConsoleMessage.sendMessage(sender, "Not-Time-Arg");
                return false;
            }

            if (args.length == 1) {
                ConsoleMessage.sendMessage(sender, "Not-Enough-Args");
                return true;
            }

            if (args[1].equalsIgnoreCase("Add")) {
                if (args.length == 2) {
                    ConsoleMessage.sendMessage(sender, "Not-Enough-Args");
                    return true;
                }

                Player target = Bukkit.getPlayer(args[2]);

                if (target == null) {
                    ConsoleMessage.sendMessage(sender, "Not-Player-Arg");
                    return false;
                }

                if (args.length == 3) {
                    ConsoleMessage.sendMessage(sender, "Not-Enough-Args");
                    return false;
                }

                try {
                    int amount = Integer.parseInt(args[3]);
                    playerData.addAmount(sender, target, amount);
                } catch (NumberFormatException e) {
                    ConsoleMessage.sendMessage(sender, "Not-Integer");
                }
                return false;
            }
            if (args[1].equalsIgnoreCase("Take")) {
                    if (args.length == 2) {
                        ConsoleMessage.sendMessage(sender, "Not-Enough-Args");
                        return true;
                    }

                    Player target = Bukkit.getPlayer(args[2]);

                    if (target == null) {
                        ConsoleMessage.sendMessage(sender, "Not-Player-Arg");
                        return false;
                    }

                    if (args.length == 3) {
                        ConsoleMessage.sendMessage(sender, "Not-Enough-Args");
                        return false;
                    }

                    try {
                        int amount = Integer.parseInt(args[3]);
                        playerData.removeAmountManual(sender, target, amount);
                    } catch (NumberFormatException e) {
                        ConsoleMessage.sendMessage(sender, "Not-Integer");
                    }
                    return false;
            }
        }

        if (!(sender instanceof Player)) {
            ConsoleMessage.sendMessage(sender, "Player-Only");
            return false;
        }

        Player player = (Player) sender;

        if (!player.hasPermission("StarSubscriptions.Command.Subscription")) {
            PlayerMessage.sendMessage(player, "No-Permission");
            return false;
        }

        if (args.length == 0) {
            playerData.getPlayerAmount(player);
            return false;
        }

        if (!player.hasPermission("StarSubscriptions.Command.Subscription.Help")) {
            PlayerMessage.sendMessage(player, "No-Permission");
            return false;
        }

        if (args[0].equalsIgnoreCase("Help")) {
            PlayerMessage.sendMessage(player, "Player-Help-Message");
            return false;
        }

        return false;
    }
}
