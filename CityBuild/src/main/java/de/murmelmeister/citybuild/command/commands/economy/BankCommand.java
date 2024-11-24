package de.murmelmeister.citybuild.command.commands.economy;

import de.murmelmeister.citybuild.CityBuild;
import de.murmelmeister.citybuild.api.Economy;
import de.murmelmeister.citybuild.command.CommandManager;
import de.murmelmeister.citybuild.util.config.Configs;
import de.murmelmeister.citybuild.util.config.Messages;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.Arrays;
import java.util.List;

public final class BankCommand extends CommandManager {
    public BankCommand(CityBuild plugin) {
        super(plugin);
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (!isEnable(sender, Configs.COMMAND_ENABLE_BANK)) return true;
        if (!hasPermission(sender, Configs.PERMISSION_BANK)) return true;

        Player player = getPlayer(sender);
        if (!existPlayer(sender)) return true;

        int userId = user.getId(player.getUniqueId());

        switch (args.length) {
            case 0 -> {
                sendMessage(player, "<gray>Current money: <aqua>" + decimalFormat.format(economy.getMoney(userId)));
                sendMessage(player, "<gray>Current bank money: <aqua>" + decimalFormat.format(economy.getBankMoney(userId)));
                sendMessage(player, "<aqua>/bank deposit <amount> <gray>- Deposit money to your bank account");
                sendMessage(player, "<aqua>/bank withdraw <amount> <gray>- Withdraw money from your bank account");
            }
            case 2 -> {
                String input = args[1];
                if (!Economy.MONEY_PATTERN.matcher(input).matches()) {
                    sendMessage(player, message.getString(Messages.INVALID_NUMBERS));
                    return true;
                }

                double amount = Double.parseDouble(input);
                switch (args[0]) {
                    case "deposit" -> {
                        if (economy.hasEnoughMoney(userId, amount)) {
                            economy.removeMoney(userId, amount);
                            economy.addBankMoney(userId, amount);
                            sendMessage(player, "<green>You have successfully deposited " + decimalFormat.format(amount) + " money.");
                        } else {
                            sendMessage(player, "<red>You don't have enough money.");
                        }
                    }
                    case "withdraw" -> {
                        if (economy.hasEnoughBankMoney(userId, amount)) {
                            economy.removeBankMoney(userId, amount);
                            economy.addMoney(userId, amount);
                            sendMessage(player, "<green>You have successfully withdrawn " + decimalFormat.format(amount) + " money.");
                        } else {
                            sendMessage(player, "<red>You don't have enough money in your bank account.");
                        }
                    }
                    default -> sendMessage(player, message.getString(Messages.COMMAND_SYNTAX).replace("[USAGE]", command.getUsage()));
                }
            }
            default -> sendMessage(player, message.getString(Messages.COMMAND_SYNTAX).replace("[USAGE]", command.getUsage()));
        }
        return true;
    }

    @Override
    public @NotNull List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        return tabComplete(Arrays.asList("deposit", "withdraw"), args, 1);
    }
}
