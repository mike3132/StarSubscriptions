package net.resolutemc.starsubscriptions.events;

import net.resolutemc.starsubscriptions.StarSubscriptions;
import net.resolutemc.starsubscriptions.utils.DateUtils;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.scheduler.BukkitRunnable;


public class PlayerEvent implements Listener {

    DateUtils dateUtils = new DateUtils();

    private final int waitTime = StarSubscriptions.getInstance().getConfig().getInt("Join-Delay-Time");

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent pje) {
        Player player = pje.getPlayer();

        if (!player.hasPermission("Subscriptions.Active.Daily")) return;

        new BukkitRunnable() {
            @Override
            public void run() {
                dateUtils.dateCheck(player);
            }
        }.runTaskLater(StarSubscriptions.getInstance(), waitTime);

    }

}
