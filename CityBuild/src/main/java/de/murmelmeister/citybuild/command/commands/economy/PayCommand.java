package de.murmelmeister.citybuild.command.commands.economy;

import de.murmelmeister.citybuild.Main;
import de.murmelmeister.citybuild.command.CommandManager;
import de.murmelmeister.citybuild.util.config.Configs;
import de.murmelmeister.citybuild.util.config.Messages;
import de.murmelmeister.murmelapi.MurmelAPI;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class PayCommand extends CommandManager {
    public PayCommand(Main main) {
        super(main);
    }

    /*
    /pay <player> <money>
     */
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (!(isEnable(sender, Configs.COMMAND_ENABLE_PAY))) return true;
        if (!(hasPermission(sender, Configs.PERMISSION_PAY_USE))) return true;

        Player player = getPlayer(sender);
        if (!(existPlayer(sender))) return true;

        int userId = user.getId(player.getUniqueId());

        if (userId == -2) {
            logger.error("{} has no ID in the database", player.getName());
            return true;
        }

        if (args.length != 2) {
            sendMessage(player, message.getString(Messages.COMMAND_SYNTAX).replace("[USAGE]", command.getUsage()));
            return true;
        }

        if (args[0].equals("*")) {
            if (!(hasPermission(sender, Configs.PERMISSION_PAY_ALL_PLAYERS))) return true;

            if (args[1].equals("*")) {
                sendMessage(player, message.getString(Messages.COMMAND_PAY_ALL));
                return true;
            }

            payAllPlayers(player, userId, args[1]);
            return true;
        }

        Player target = sender.getServer().getPlayer(args[0]);
        if (target == null) {
            sendMessage(sender, message.getString(Messages.NO_PLAYER_EXIST).replace("[PLAYER]", args[0]));
            return true;
        }

        int targetId = user.getId(target.getUniqueId());

        if (targetId == -2) {
            sendMessage(sender, message.getString(Messages.NO_PLAYER_EXIST).replace("[PLAYER]", args[0]));
            return true;
        }

        if (args[1].equals("*")) {
            payAllMoney(player, target, userId, targetId);
            return true;
        }

        payPlayerMoney(player, target, userId, targetId, args[1]);
        return true;
    }

    @Override
    public @Nullable List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String alias, @NotNull String[] args) {
        return tabCompletePlayers(sender, args, 1);
    }

    private void payAllPlayers(Player player, int userId, String args) {
        try {
            double money = Double.parseDouble(args);

            if (!(player.hasPermission(config.getString(Configs.PERMISSION_PAY_NEGATIVE))) && -money >= 0) {
                sendMessage(player, message.getString(Messages.COMMAND_PAY_MONEY_NEGATIVE));
                return;
            }

            if (!(economy.hasEnoughMoney(userId, money))) {
                sendMessage(player, message.getString(Messages.COMMAND_PAY_MONEY_ENOUGH).replace("[CURRENCY]", config.getString(Configs.ECONOMY_CURRENCY)));
                return;
            }

            for (Player all : player.getServer().getOnlinePlayers()) {
                int allIds = user.getId(all.getUniqueId());
                if (allIds == -2) continue;
                economy.payMoneyToPlayer(userId, allIds, money);
                sendMessage(all, message.getString(Messages.COMMAND_PAY_TARGET).replace("[PLAYER]", player.getName()).replace("[MONEY]", decimalFormat.format(money)).replace("[CURRENCY]", config.getString(Configs.ECONOMY_CURRENCY)));

            }

            sendMessage(player, message.getString(Messages.COMMAND_PAY_PLAYER).replace("[PLAYER]", "all").replace("[MONEY]", decimalFormat.format(money)).replace("[CURRENCY]", config.getString(Configs.ECONOMY_CURRENCY)));
        } catch (NumberFormatException exception) {
            sendMessage(player, message.getString(Messages.NO_NUMBER));
        }
    }

    private void payAllMoney(Player player, Player target, int userId, int targetId) {
        if (!(hasPermission(player, Configs.PERMISSION_PAY_ALL_MONEY))) return;

        try {
            double allMoney = economy.getMoney(userId);

            if (!(player.hasPermission(config.getString(Configs.PERMISSION_PAY_NEGATIVE))) && -allMoney >= 0) {
                sendMessage(player, message.getString(Messages.COMMAND_PAY_MONEY_NEGATIVE));
                return;
            }

            economy.payMoneyToPlayer(userId, targetId, allMoney);
            sendMessage(target, message.getString(Messages.COMMAND_PAY_TARGET).replace("[PLAYER]", player.getName()).replace("[MONEY]", decimalFormat.format(allMoney)).replace("[CURRENCY]", config.getString(Configs.ECONOMY_CURRENCY)));
            sendMessage(player, message.getString(Messages.COMMAND_PAY_PLAYER).replace("[PLAYER]", target.getName()).replace("[MONEY]", decimalFormat.format(allMoney)).replace("[CURRENCY]", config.getString(Configs.ECONOMY_CURRENCY)));
        } catch (NumberFormatException exception) {
            sendMessage(player, message.getString(Messages.NO_NUMBER));
        }
    }

    private void payPlayerMoney(Player player, Player target, int userId, int targetId, String args) {
        try {
            double money = Double.parseDouble(args);

            if (!(player.hasPermission(config.getString(Configs.PERMISSION_PAY_NEGATIVE))) && -money >= 0) {
                sendMessage(player, message.getString(Messages.COMMAND_PAY_MONEY_NEGATIVE));
                return;
            }

            if (!(economy.hasEnoughMoney(userId, money))) {
                sendMessage(player, message.getString(Messages.COMMAND_PAY_MONEY_ENOUGH).replace("[CURRENCY]", config.getString(Configs.ECONOMY_CURRENCY)));
                return;
            }

            economy.payMoneyToPlayer(userId, targetId, money);
            sendMessage(target, message.getString(Messages.COMMAND_PAY_TARGET).replace("[PLAYER]", player.getName()).replace("[MONEY]", decimalFormat.format(money)).replace("[CURRENCY]", config.getString(Configs.ECONOMY_CURRENCY)));
            sendMessage(player, message.getString(Messages.COMMAND_PAY_PLAYER).replace("[PLAYER]", target.getName()).replace("[MONEY]", decimalFormat.format(money)).replace("[CURRENCY]", config.getString(Configs.ECONOMY_CURRENCY)));
        } catch (NumberFormatException exception) {
            sendMessage(player, message.getString(Messages.NO_NUMBER));
        }
    }
}
