package net.resolutemc.starsubscriptions.utils;


import com.github.stefvanschie.inventoryframework.gui.GuiItem;
import com.github.stefvanschie.inventoryframework.gui.type.ChestGui;
import com.github.stefvanschie.inventoryframework.pane.PatternPane;
import com.github.stefvanschie.inventoryframework.pane.util.Pattern;
import me.clip.placeholderapi.PlaceholderAPI;
import net.resolutemc.starsubscriptions.StarSubscriptions;
import net.resolutemc.starsubscriptions.chat.ConsoleMessage;
import net.resolutemc.starsubscriptions.chat.PlayerMessage;
import net.resolutemc.starsubscriptions.config.ConfigCreator;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class GuiBuilder {


    public ChestGui createGui(Player player, String initialTitle) {
        String title = PlaceholderAPI.setPlaceholders(player, initialTitle);
        ChestGui gui = new ChestGui(6, title);

        Pattern pattern = new Pattern(
                "000000000",
                "011111110",
                "011111110",
                "011111110",
                "011111110",
                "000000000"
        );

        PatternPane patternPane = new PatternPane(0,0,9,6, pattern);

        // Replace this with a border of items
        String itemType = ConfigCreator.GUI_CONFIG.get().getString("Gui-Border-Item");

        ItemStack border = new ItemStack(Material.valueOf(itemType));
        if (!border.getType().isAir()) {
            ItemMeta borderMeta = border.getItemMeta();
            borderMeta.setDisplayName(" ");
            border.setItemMeta(borderMeta);
            patternPane.bindItem('0', new GuiItem(border, event -> {
                event.setCancelled(true);
            }));
        }

        gui.setOnClose(event -> {
            InventoryUtils.loadInventory(player);
            PlayerMessage.sendMessage(player, "Player-Reward-Given");
            ConsoleMessage.sendRewards(StarSubscriptions.getInstance().getServer().getConsoleSender(), player);
        });

        gui.addPane(patternPane);
        return gui;
    }
}
