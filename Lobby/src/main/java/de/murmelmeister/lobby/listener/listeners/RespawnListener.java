package de.murmelmeister.lobby.listener.listeners;

import de.murmelmeister.lobby.Main;
import de.murmelmeister.lobby.listener.Listeners;
import de.murmelmeister.lobby.util.Items;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.bukkit.scheduler.BukkitRunnable;

public class RespawnListener extends Listeners {
    public RespawnListener(Main main) {
        super(main);
    }

    @EventHandler
    public void handleDeath(PlayerDeathEvent event) {
        Player player = event.getEntity();
        new BukkitRunnable() {

            @Override
            public void run() {
                player.spigot().respawn();
                player.teleport(locations.getSpawn());
            }
        }.runTaskLater(instance, 2L);
    }

    @EventHandler
    public void handleRespawn(PlayerRespawnEvent event) {
        Player player = event.getPlayer();
        Items.setLobbyItems(config, player);
    }
}
