package net.resolutemc.starsubscriptions.utils;

import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;
import java.util.UUID;

public class InventoryUtils {

    private static final HashMap<UUID, ItemStack[]> inventoryMap = new HashMap<>();

    public static void saveInventory(Player player) {
        if (player.getInventory().getContents() == null) return;
        inventoryMap.put(player.getUniqueId(), player.getInventory().getContents());
        player.getInventory().clear();
    }

    public static void loadInventory(Player player) {
        if (!inventoryMap.containsKey(player.getUniqueId())) return;
        player.getInventory().setContents(inventoryMap.get(player.getUniqueId()));
        inventoryMap.remove(player.getUniqueId());
    }
}
