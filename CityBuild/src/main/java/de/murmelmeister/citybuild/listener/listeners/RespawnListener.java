package de.murmelmeister.citybuild.listener.listeners;

import de.murmelmeister.citybuild.Main;
import de.murmelmeister.citybuild.listener.Listeners;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.PlayerDeathEvent;
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
                if (locations.isSpawnExist()) player.teleport(locations.getSpawn());
            }
        }.runTaskLater(instance, 2L);
    }
}
