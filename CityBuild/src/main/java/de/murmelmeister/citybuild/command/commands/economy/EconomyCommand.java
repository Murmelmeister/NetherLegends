package de.murmelmeister.citybuild.command.commands.economy;

import de.murmelmeister.citybuild.Main;
import de.murmelmeister.citybuild.command.CommandManager;
import de.murmelmeister.citybuild.util.config.Configs;
import de.murmelmeister.citybuild.util.config.Messages;
import de.murmelmeister.murmelapi.MurmelAPI;
import de.murmelmeister.murmelapi.user.User;
import de.murmelmeister.murmelapi.user.permission.UserPermission;
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
    public EconomyCommand(Main main) {
        super(main);
    }

    public void test(Player player) {
        User user = MurmelAPI.getUser();
        UserPermission userPermission = user.getPermission();
        int userId = user.getId(player.getUniqueId());
        if (userId == -2) {
            // Error
            return;
        }
        userPermission.addPermission(userId, -1, "worldedit.*", -1);
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (!(isEnable(sender, Configs.COMMAND_ENABLE_ECONOMY_COMMAND))) return true;
        if (!(hasPermission(sender, Configs.PERMISSION_ECONOMY_COMMAND))) return true;

        if (args.length >= 2) {
            switch (args[0]) {
                case "set" -> economySet(sender, args[1], args[2]);
                case "add" -> economyAdd(sender, args[1], args[2]);
                case "remove" -> economyRemove(sender, args[1], args[2]);
                case "reset" -> economyReset(sender, args[1]);
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

    private void economySet(CommandSender sender, String username, String amount) {
        if (!(isEnable(sender, Configs.COMMAND_ENABLE_ECONOMY_SET))) return;
        if (!(hasPermission(sender, Configs.PERMISSION_ECONOMY_SET))) return;

        OfflinePlayer target = sender.getServer().getOfflinePlayer(username);
        int targetId = user.getId(target.getUniqueId());
        if (targetId == -2) {
            sendMessage(sender, message.getString(Messages.NO_PLAYER_EXIST).replace("[PLAYER]", username));
            return;
        }

        try {
            double money = Double.parseDouble(amount);
            economy.setMoney(targetId, money);
            sendMessage(sender, message.getString(Messages.COMMAND_ECONOMY_SET)
                    .replace("[PLAYER]", username).replace("[MONEY]", decimalFormat.format(money)).replace("[CURRENCY]", config.getString(Configs.ECONOMY_CURRENCY)));
        } catch (NumberFormatException e) {
            sendMessage(sender, message.getString(Messages.NO_NUMBER));
        } catch (IllegalArgumentException | ArrayIndexOutOfBoundsException e) {
            sendMessage(sender, e.getMessage());
        }
    }

    private void economyAdd(CommandSender sender, String username, String amount) {
        if (!(isEnable(sender, Configs.COMMAND_ENABLE_ECONOMY_ADD))) return;
        if (!(hasPermission(sender, Configs.PERMISSION_ECONOMY_ADD))) return;

        OfflinePlayer target = sender.getServer().getOfflinePlayer(username);
        int targetId = user.getId(target.getUniqueId());
        if (targetId == -2) {
            sendMessage(sender, message.getString(Messages.NO_PLAYER_EXIST).replace("[PLAYER]", username));
            return;
        }

        try {
            double money = Double.parseDouble(amount);
            economy.addMoney(targetId, money);
            sendMessage(sender, message.getString(Messages.COMMAND_ECONOMY_ADD)
                    .replace("[PLAYER]", username).replace("[MONEY]", decimalFormat.format(money)).replace("[CURRENCY]", config.getString(Configs.ECONOMY_CURRENCY)));
        } catch (NumberFormatException e) {
            sendMessage(sender, message.getString(Messages.NO_NUMBER));
        } catch (IllegalArgumentException | ArrayIndexOutOfBoundsException e) {
            sendMessage(sender, e.getMessage());
        }
    }

    private void economyRemove(CommandSender sender, String username, String amount) {
        if (!(isEnable(sender, Configs.COMMAND_ENABLE_ECONOMY_REMOVE))) return;
        if (!(hasPermission(sender, Configs.PERMISSION_ECONOMY_REMOVE))) return;

        OfflinePlayer target = sender.getServer().getOfflinePlayer(username);
        int targetId = user.getId(target.getUniqueId());
        if (targetId == -2) {
            sendMessage(sender, message.getString(Messages.NO_PLAYER_EXIST).replace("[PLAYER]", username));
            return;
        }

        try {
            double money = Double.parseDouble(amount);
            economy.removeMoney(targetId, money);
            sendMessage(sender, message.getString(Messages.COMMAND_ECONOMY_REMOVE)
                    .replace("[PLAYER]", username).replace("[MONEY]", decimalFormat.format(money)).replace("[CURRENCY]", config.getString(Configs.ECONOMY_CURRENCY)));
        } catch (NumberFormatException e) {
            sendMessage(sender, message.getString(Messages.NO_NUMBER));
        } catch (IllegalArgumentException | ArrayIndexOutOfBoundsException e) {
            sendMessage(sender, e.getMessage());
        }
    }

    private void economyReset(CommandSender sender, String username) {
        if (!(isEnable(sender, Configs.COMMAND_ENABLE_ECONOMY_RESET))) return;
        if (!(hasPermission(sender, Configs.PERMISSION_ECONOMY_RESET))) return;

        OfflinePlayer target = sender.getServer().getOfflinePlayer(username);
        int targetId = user.getId(target.getUniqueId());
        if (targetId == -2) {
            sendMessage(sender, message.getString(Messages.NO_PLAYER_EXIST).replace("[PLAYER]", username));
            return;
        }

        economy.resetMoney(targetId);
        sendMessage(sender, message.getString(Messages.COMMAND_ECONOMY_RESET).replace("[PLAYER]", username));
    }
}
