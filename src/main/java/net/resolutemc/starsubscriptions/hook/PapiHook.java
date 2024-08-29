package net.resolutemc.starsubscriptions.hook;

import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import net.resolutemc.starsubscriptions.config.ConfigCreator;
import org.bukkit.OfflinePlayer;
import org.bukkit.configuration.file.FileConfiguration;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class PapiHook extends PlaceholderExpansion {

    @Override
    public @NotNull String getIdentifier() {
        return "StarSubscriptions";
    }

    @Override
    public @NotNull String getAuthor() {
        return "Mike3132";
    }

    @Override
    public @NotNull String getVersion() {
        return "1.0.0";
    }

    @Override
    public @Nullable String getRequiredPlugin() {
        return "StarSubscriptions";
    }

    @Override
    public boolean canRegister() {
        return true;
    }

    @Override
    public boolean persist() {
        return true;
    }

    @Override
    public @Nullable String onRequest(OfflinePlayer player, @NotNull String string) {
        if (player == null) return "";
        if (string.equalsIgnoreCase("Days_Remaining")) {
            FileConfiguration config = ConfigCreator.PLAYER_DATA.get();
            return String.valueOf(config.getInt(player.getUniqueId().toString()));
        }
        return null;
    }


}
