package de.murmelmeister.citybuild.command.commands;

import de.murmelmeister.citybuild.CityBuild;
import de.murmelmeister.citybuild.command.CommandManager;
import de.murmelmeister.citybuild.util.config.Configs;
import de.murmelmeister.citybuild.util.config.Messages;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class LobbyCommand extends CommandManager {
    private final Plugin plugin;
    public LobbyCommand(CityBuild plugin) {
        super(plugin);
        this.plugin = plugin;
    }

    /*
    /lobby
     */
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (!(isEnable(sender, Configs.COMMAND_ENABLE_LOBBY))) return true;
        if (!(hasPermission(sender, Configs.PERMISSION_LOBBY))) return true;

        Player player = getPlayer(sender);
        if (!(existPlayer(sender))) return true;

        ByteArrayOutputStream b = new ByteArrayOutputStream();
        DataOutputStream out = new DataOutputStream(b);

        try {
            out.writeUTF("Connect");
            out.writeUTF(config.getString(Configs.LOBBY_TO_SEND_SERVER));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        sendMessage(player, message.getString(Messages.COMMAND_LOBBY_SEND));
        player.sendPluginMessage(plugin, "BungeeCord", b.toByteArray());
        return true;
    }

    @Override
    public @Nullable List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String alias, @NotNull String[] args) {
        return new ArrayList<>();
    }
}
