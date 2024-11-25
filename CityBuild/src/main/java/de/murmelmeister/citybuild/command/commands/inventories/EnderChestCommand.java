package de.murmelmeister.citybuild.command.commands.inventories;

import de.murmelmeister.citybuild.CityBuild;
import de.murmelmeister.citybuild.api.enderchest.EnderChestMenu;
import de.murmelmeister.citybuild.command.CommandManager;
import de.murmelmeister.citybuild.util.config.Configs;
import de.murmelmeister.citybuild.util.config.Messages;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Collections;
import java.util.List;

public class EnderChestCommand extends CommandManager {
    public EnderChestCommand(CityBuild plugin) {
        super(plugin);
    }

    /*
        /enderChest [player]
    */
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (!(isEnable(sender, Configs.COMMAND_ENABLE_ENDER_CHEST_COMMAND))) return true;
        if (!(hasPermission(sender, Configs.PERMISSION_ENDER_CHEST_COMMAND))) return true;

        Player player = getPlayer(sender);
        if (!(existPlayer(sender))) return true;

        int userId = user.getId(player.getUniqueId());

        if (args.length == 0) {
            if (!(isEnable(sender, Configs.COMMAND_ENABLE_ENDER_CHEST_USE))) return true;
            if (!(hasPermission(sender, Configs.PERMISSION_ENDER_CHEST_USE))) return true;

            EnderChestMenu menu = new EnderChestMenu(config, enderChestEditor, userId);
            menu.show(player);
        } else if (args.length == 1) {
            if (!(isEnable(sender, Configs.COMMAND_ENABLE_ENDER_CHEST_OTHER))) return true;
            if (!(hasPermission(sender, Configs.PERMISSION_ENDER_CHEST_OTHER))) return true;

            // TODO: Implement /enderChest [player]
        } else sendMessage(player, message.getString(Messages.COMMAND_SYNTAX).replace("[USAGE]", command.getUsage()));
        return true;
    }

    @Override
    public @Nullable List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String alias, @NotNull String[] args) {
        //return tabCompletePlayers(sender, args, 1);
        return Collections.emptyList();
    }
}
