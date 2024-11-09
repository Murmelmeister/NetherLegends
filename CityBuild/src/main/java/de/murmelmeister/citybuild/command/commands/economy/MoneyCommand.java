package de.murmelmeister.citybuild.command.commands.economy;

import de.murmelmeister.citybuild.CityBuild;
import de.murmelmeister.citybuild.command.CommandManager;
import de.murmelmeister.citybuild.util.config.Configs;
import de.murmelmeister.citybuild.util.config.Messages;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class MoneyCommand extends CommandManager {
    public MoneyCommand(CityBuild plugin) {
        super(plugin);
    }

    /*
    /money [player]
     */
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (!(isEnable(sender, Configs.COMMAND_ENABLE_MONEY_COMMAND))) return true;
        if (!(hasPermission(sender, Configs.PERMISSION_MONEY_COMMAND))) return true;

        Player player = getPlayer(sender);
        if (!(existPlayer(sender))) return true;


        int userId = user.getId(player.getUniqueId());

        if (userId == -2) {
            logger.error("{} has no ID in the database", player.getName());
            return true;
        }

        if (args.length == 0) {
            if (!(isEnable(sender, Configs.COMMAND_ENABLE_MONEY_USE))) return true;
            if (!(hasPermission(sender, Configs.PERMISSION_MONEY_USE))) return true;
            sendMessage(player, message.getString(Messages.COMMAND_MONEY_USE).replace("[CURRENCY]", config.getString(Configs.ECONOMY_CURRENCY)).replace("[MONEY]", decimalFormat.format(economy.getMoney(userId))));
        } else if (args.length == 1) {
            if (!(isEnable(sender, Configs.COMMAND_ENABLE_MONEY_OTHER))) return true;
            if (!(hasPermission(sender, Configs.PERMISSION_MONEY_OTHER))) return true;
            String targetName = args[0];
            OfflinePlayer target = sender.getServer().getOfflinePlayer(targetName);

            int targetId = user.getId(target.getUniqueId());
            if (targetId == -2) {
                sendMessage(player, message.getString(Messages.NO_PLAYER_EXIST).replace("[PLAYER]", targetName));
                return true;
            }

            sendMessage(player, message.getString(Messages.COMMAND_MONEY_OTHER).replace("[CURRENCY]", config.getString(Configs.ECONOMY_CURRENCY))
                    .replace("[MONEY]", decimalFormat.format(economy.getMoney(targetId))).replace("[PLAYER]", targetName));
        } else sendMessage(sender, message.getString(Messages.COMMAND_SYNTAX).replace("[USAGE]", command.getUsage()));
        return true;
    }

    @Override
    public @Nullable List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String alias, @NotNull String[] args) {
        return tabCompleteOfflinePlayers(sender, args, 1);
    }
}
