package de.murmelmeister.citybuild.command.commands;

import de.murmelmeister.citybuild.CityBuild;
import de.murmelmeister.citybuild.command.CommandManager;
import de.murmelmeister.citybuild.util.config.Configs;
import de.murmelmeister.citybuild.util.config.Messages;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

public class CityBuildReloadCommand extends CommandManager {
    public CityBuildReloadCommand(CityBuild plugin) {
        super(plugin);
    }

    /*
        /cityBuildReload
    */
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (!(isEnable(sender, Configs.COMMAND_ENABLE_RELOAD))) return true;
        if (!(hasPermission(sender, Configs.PERMISSION_RELOAD))) return true;

        this.config.reloadFile();
        this.message.reloadFile();
        this.locations.reloadFile();

        for (Player player : sender.getServer().getOnlinePlayers())
            player.closeInventory();

        sendMessage(sender, message.getString(Messages.COMMAND_RELOAD));
        return true;
    }

    @Override
    public @Nullable List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String alias, @NotNull String[] args) {
        return new ArrayList<>();
    }
}
