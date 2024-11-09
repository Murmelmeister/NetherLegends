package de.murmelmeister.citybuild.listener.listeners;

import de.murmelmeister.citybuild.CityBuild;
import de.murmelmeister.citybuild.listener.ListenerManager;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.scheduler.BukkitRunnable;

public class RespawnListener extends ListenerManager {
    public RespawnListener(CityBuild plugin) {
        super(plugin);
    }

    @EventHandler
    public void handleDeath(PlayerDeathEvent event) {
        Player player = event.getEntity();
        new BukkitRunnable() {

            @Override
            public void run() {
                player.spigot().respawn();
                if (locations.isSpawnExist()) player.teleport(locations.getSpawn());
            }
        }.runTaskLater(plugin, 2L);
    }
}
