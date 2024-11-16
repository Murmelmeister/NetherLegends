package de.murmelmeister.citybuild.command.commands.economy;

import de.murmelmeister.citybuild.CityBuild;
import de.murmelmeister.citybuild.api.Economy;
import de.murmelmeister.citybuild.command.CommandManager;
import de.murmelmeister.citybuild.util.config.Configs;
import de.murmelmeister.citybuild.util.config.Messages;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class EconomyCommand extends CommandManager {
    public EconomyCommand(CityBuild plugin) {
        super(plugin);
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (!(isEnable(sender, Configs.COMMAND_ENABLE_ECONOMY_COMMAND))) return true;
        if (!(hasPermission(sender, Configs.PERMISSION_ECONOMY_COMMAND))) return true;

        if (args.length >= 2) {
            String username = args[1];
            String amount = args[2];

            if (username.equals("*")) {
                for (Player all : sender.getServer().getOnlinePlayers()) {
                    int allIds = user.getId(all.getUniqueId());
                    if (allIds == -2) continue;
                    switch (args[0]) {
                        case "set" -> economySet(sender, allIds, all.getName(), amount);
                        case "add" -> economyAdd(sender, allIds, all.getName(), amount);
                        case "remove" -> economyRemove(sender, allIds, all.getName(), amount);
                        case "reset" -> economyReset(sender, allIds, all.getName());
                        default ->
                                sendMessage(sender, message.getString(Messages.COMMAND_SYNTAX).replace("[USAGE]", command.getUsage()));
                    }
                }
                return true;
            }

            OfflinePlayer target = sender.getServer().getOfflinePlayer(username);
            int targetId = user.getId(target.getUniqueId());

            if (targetId == -2) {
                sendMessage(sender, message.getString(Messages.NO_PLAYER_EXIST).replace("[PLAYER]", username));
                return true;
            }

            if (!Economy.MONEY_PATTERN.matcher(amount).matches()) {
                sendMessage(sender, message.getString(Messages.INVALID_NUMBERS));
                return true;
            }

            switch (args[0]) {
                case "set" -> economySet(sender, targetId, username, amount);
                case "add" -> economyAdd(sender, targetId, username, amount);
                case "remove" -> economyRemove(sender, targetId, username, amount);
                case "reset" -> economyReset(sender, targetId, username);
                default ->
                        sendMessage(sender, message.getString(Messages.COMMAND_SYNTAX).replace("[USAGE]", command.getUsage()));
            }
        } else sendMessage(sender, message.getString(Messages.COMMAND_SYNTAX).replace("[USAGE]", command.getUsage()));
        return true;
    }

    @Override
    public @Nullable List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String alias, @NotNull String[] args) {
        if (args.length == 1) return tabComplete(Arrays.asList("set", "add", "remove", "reset"), args);
        if (args.length == 2) return tabCompleteOfflinePlayers(sender, args);
        return Collections.emptyList();
    }

    private void economySet(CommandSender sender, int targetId, String username, String amount) {
        if (!(isEnable(sender, Configs.COMMAND_ENABLE_ECONOMY_SET))) return;
        if (!(hasPermission(sender, Configs.PERMISSION_ECONOMY_SET))) return;

        double money = Double.parseDouble(amount);
        economy.setMoney(targetId, money);
        sendMessage(sender, message.getString(Messages.COMMAND_ECONOMY_SET)
                .replace("[PLAYER]", username).replace("[MONEY]", decimalFormat.format(money)).replace("[CURRENCY]", config.getString(Configs.ECONOMY_CURRENCY)));
    }

    private void economyAdd(CommandSender sender, int targetId, String username, String amount) {
        if (!(isEnable(sender, Configs.COMMAND_ENABLE_ECONOMY_ADD))) return;
        if (!(hasPermission(sender, Configs.PERMISSION_ECONOMY_ADD))) return;

        double money = Double.parseDouble(amount);
        economy.addMoney(targetId, money);
        sendMessage(sender, message.getString(Messages.COMMAND_ECONOMY_ADD)
                .replace("[PLAYER]", username).replace("[MONEY]", decimalFormat.format(money)).replace("[CURRENCY]", config.getString(Configs.ECONOMY_CURRENCY)));
    }

    private void economyRemove(CommandSender sender, int targetId, String username, String amount) {
        if (!(isEnable(sender, Configs.COMMAND_ENABLE_ECONOMY_REMOVE))) return;
        if (!(hasPermission(sender, Configs.PERMISSION_ECONOMY_REMOVE))) return;

        double money = Double.parseDouble(amount);
        economy.removeMoney(targetId, money);
        sendMessage(sender, message.getString(Messages.COMMAND_ECONOMY_REMOVE)
                .replace("[PLAYER]", username).replace("[MONEY]", decimalFormat.format(money)).replace("[CURRENCY]", config.getString(Configs.ECONOMY_CURRENCY)));
    }

    private void economyReset(CommandSender sender, int targetId, String username) {
        if (!(isEnable(sender, Configs.COMMAND_ENABLE_ECONOMY_RESET))) return;
        if (!(hasPermission(sender, Configs.PERMISSION_ECONOMY_RESET))) return;

        economy.resetMoney(targetId);
        sendMessage(sender, message.getString(Messages.COMMAND_ECONOMY_RESET).replace("[PLAYER]", username));
    }
}
