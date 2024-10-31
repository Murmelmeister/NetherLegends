package de.murmelmeister.lobby.listener.listeners;

import de.murmelmeister.lobby.Main;
import de.murmelmeister.lobby.listener.Listeners;
import de.murmelmeister.lobby.util.Items;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerRespawnEvent;

public class RespawnListener extends Listeners {
    public RespawnListener(Main main) {
        super(main);
    }

    @EventHandler
    public void handleRespawn(PlayerRespawnEvent event) {
        Player player = event.getPlayer();
        player.spigot().respawn();
        Items.setLobbyItems(config, player);
    }
}
