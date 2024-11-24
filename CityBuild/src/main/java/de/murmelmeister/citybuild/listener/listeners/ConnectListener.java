package de.murmelmeister.citybuild.listener.listeners;

import de.murmelmeister.citybuild.CityBuild;
import de.murmelmeister.citybuild.listener.ListenerManager;
import de.murmelmeister.citybuild.util.config.Configs;
import de.murmelmeister.citybuild.util.config.Messages;
import net.kyori.adventure.text.minimessage.MiniMessage;
import net.kyori.adventure.title.Title;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class ConnectListener extends ListenerManager {
    public ConnectListener(CityBuild plugin) {
        super(plugin);
    }

    @EventHandler
    public void handlePlayerJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();

        if (config.getBoolean(Configs.EVENT_ENABLE_PLAYER_JOIN))
            if (player.canSee(player)) // Maybe unnecessary, but if someone is in vanish you should not see the message
                if (config.getBoolean(Configs.PREFIX_ENABLE))
                    event.joinMessage(MiniMessage.miniMessage().deserialize(message.prefix() + message.getString(Messages.EVENT_PLAYER_JOIN).replace("[PLAYER]", player.getName())));
                else
                    event.joinMessage(MiniMessage.miniMessage().deserialize(message.getString(Messages.EVENT_PLAYER_JOIN).replace("[PLAYER]", player.getName())));
            else event.joinMessage(null);
        else event.joinMessage(null);

        if (locations.isSpawnExist()) {
            if (config.getBoolean(Configs.EVENT_ENABLE_TELEPORT_TO_SPAWN))
                player.teleport(locations.getLocation("Spawn"));
        } else
            sendMessage(player, message.getString(Messages.EVENT_SPAWN_NOT_EXIST).replace("[PREFIX]", message.prefix()));

        int userId = user.getId(player.getUniqueId());
        if (user.existsUser(player.getUniqueId())) {
            economy.createUser(userId);
        }

        for (int i = 1; i < 10; i++)
            enderChest.setAccess(player.getUniqueId(), i, player.hasPermission(config.getString(Configs.PERMISSION_ENDER_CHEST_SLOTS) + "." + i));

        if (player.hasPermission(config.getString(Configs.PERMISSION_JOIN_FLY))) {
            player.setAllowFlight(true);
            player.setFlying(true);
            sendMessage(player, message.getString(Messages.EVENT_JOIN_AUTO_FLY));
        }

        if (player.hasPermission(config.getString(Configs.PERMISSION_JOIN_GOD_MODE))) {
            listUtil.getGodMode().add(player.getUniqueId());
            sendMessage(player, message.getString(Messages.EVENT_JOIN_AUTO_GOD_MODE));
        }

        if (config.getBoolean(Configs.EVENT_ENABLE_JOIN_MESSAGE))
            sendMessage(player, message.getString(Messages.EVENT_JOIN_MESSAGE).replace("[PREFIX]", message.prefix()).replace("[PLAYER]", player.getName()));
        if (config.getBoolean(Configs.EVENT_ENABLE_JOIN_TITLE)) {
            Title title = Title.title(MiniMessage.miniMessage().deserialize(message.getString(Messages.EVENT_JOIN_TITLE).replace("[PREFIX]", message.prefix()).replace("[PLAYER]", player.getName())),
                    MiniMessage.miniMessage().deserialize(message.getString(Messages.EVENT_JOIN_SUB_TITLE).replace("[PREFIX]", message.prefix()).replace("[PLAYER]", player.getName())));
            player.showTitle(title);
        }
        player.updateCommands();

        if (playerInventory.existInventory(userId)) {
            player.getInventory().setContents(playerInventory.getContents(userId));
            player.getInventory().setStorageContents(playerInventory.getStorageContents(userId));
        } else {
            playerInventory.createInventory(userId, player.getInventory());
        }
    }

    @EventHandler
    public void handlePlayerQuit(PlayerQuitEvent event) {
        Player player = event.getPlayer();
        if (config.getBoolean(Configs.EVENT_ENABLE_PLAYER_QUIT))
            if (player.canSee(player)) // Maybe unnecessary, but if someone is in vanish you should not see the message
                if (config.getBoolean(Configs.PREFIX_ENABLE))
                    event.quitMessage(MiniMessage.miniMessage().deserialize(message.prefix() + message.getString(Messages.EVENT_PLAYER_QUIT).replace("[PLAYER]", player.getName())));
                else
                    event.quitMessage(MiniMessage.miniMessage().deserialize(message.getString(Messages.EVENT_PLAYER_QUIT).replace("[PLAYER]", player.getName())));
            else event.quitMessage(null);
        else event.quitMessage(null);
        listUtil.getGodMode().remove(player.getUniqueId());
        listUtil.getLive().remove(player.getUniqueId());
        int userId = user.getId(player.getUniqueId());
        playerInventory.updateInventory(userId, player.getInventory());
    }
}
