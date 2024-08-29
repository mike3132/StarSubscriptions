package net.resolutemc.starsubscriptions.utils;

import com.github.stefvanschie.inventoryframework.gui.type.ChestGui;
import net.resolutemc.starsubscriptions.chat.ColorTranslate;
import net.resolutemc.starsubscriptions.config.ConfigCreator;
import org.bukkit.entity.Player;

public class SubscriptionGui {

    GuiBuilder guiBuilder = new GuiBuilder();

    public ChestGui getGui(Player player) {
       String title = ConfigCreator.GUI_CONFIG.get().getString("Gui-Title");
       InventoryUtils.saveInventory(player);
       return guiBuilder.createGui(player, ColorTranslate.chatColor(title));
    }



}
