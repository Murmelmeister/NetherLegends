package de.murmelmeister.citybuild.command.commands.shop.item;

import de.murmelmeister.citybuild.CityBuild;
import de.murmelmeister.citybuild.api.Economy;
import de.murmelmeister.citybuild.command.CommandManager;
import de.murmelmeister.citybuild.util.config.Configs;
import de.murmelmeister.citybuild.util.config.Messages;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public final class ShopItemCommand extends CommandManager {
    public ShopItemCommand(CityBuild plugin) {
        super(plugin);
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (!isEnable(sender, Configs.COMMAND_ENABLE_SHOP_ITEM)) return true;
        if (!hasPermission(sender, Configs.PERMISSION_SHOP_ITEM)) return true;

        if (args.length < 3) {
            sendMessage(sender, message.getString(Messages.COMMAND_SYNTAX).replace("[USAGE]", command.getUsage()));
            return true;
        }

        String categoryId = args[1];
        String itemId = args[2];
        switch (args[0]) {
            case "add" -> addItem(sender, command, categoryId, itemId, args);
            case "remove" -> removeItem(sender, categoryId, itemId);
            case "edit" -> editItem(sender, command, categoryId, itemId, args);
            default ->
                    sendMessage(sender, message.getString(Messages.COMMAND_SYNTAX).replace("[USAGE]", command.getUsage()));
        }
        return true;
    }

    @Override
    public @NotNull List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (args.length == 1)
            return tabComplete(Arrays.asList("add", "remove", "edit"), args);
        if (args.length == 2)
            return tabComplete(shopCategory.getCategories(), args);
        if (args.length == 3 && !args[0].equals("add")) {
            String categoryId = args[1];
            if (!shopCategory.existCategory(categoryId))
                return Collections.emptyList();
            return tabComplete(shopItem.getCategoryItems(categoryId), args);
        }
        if (args.length == 4 && args[0].equals("add"))
            return tabComplete(Arrays.stream(Material.values()).map(Material::name).collect(Collectors.toList()), args);
        if (args.length == 4 && args[0].equals("edit"))
            return tabComplete(Arrays.asList("material", "buy", "sell", "displayName"), args);
        if (args.length == 5 && args[0].equals("edit") && args[3].equals("material"))
            return tabComplete(Arrays.stream(Material.values()).map(Material::name).collect(Collectors.toList()), args);
        return Collections.emptyList();
    }

    private void addItem(CommandSender sender, Command command, String categoryId, String itemId, String[] args) {
        //          0   1            2        3          4     5      6
        // shopItem add <categoryId> <itemId> <material> <buy> <sell> <displayName>
        if (args.length < 7) {
            sendMessage(sender, message.getString(Messages.COMMAND_SYNTAX).replace("[USAGE]", command.getUsage()));
            return;
        }

        if (!shopCategory.existCategory(categoryId)) {
            sendMessage(sender, message.getString(Messages.SHOP_CATEGORY_NOT_EXIST));
            return;
        }

        if (shopItem.existItem(categoryId, itemId)) {
            sendMessage(sender, message.getString(Messages.SHOP_ITEM_EXIST));
            return;
        }

        String materialName = args[3];
        Material material = Material.getMaterial(materialName);
        if (material == null) {
            sendMessage(sender, message.getString(Messages.INVALID_ITEM));
            return;
        }

        String buy = args[4];
        String sell = args[5];
        if (!Economy.MONEY_PATTERN.matcher(buy).matches() || !Economy.MONEY_PATTERN.matcher(sell).matches()) {
            sendMessage(sender, message.getString(Messages.INVALID_NUMBERS));
            return;
        }

        double buyPrice = Double.parseDouble(buy);
        double sellPrice = Double.parseDouble(sell);

        StringBuilder builder = new StringBuilder();
        for (int i = 6; i < args.length; i++)
            builder.append(args[i]).append(" ");
        String displayName = builder.toString().trim();

        shopItem.addItem(categoryId, itemId, displayName, material, buyPrice, sellPrice);
        sendMessage(sender, message.getString(Messages.SHOP_ITEM_ADD));
    }

    private void removeItem(CommandSender sender, String categoryId, String itemId) {
        //          0      1            2
        // shopItem remove <categoryId> <itemId>
        if (!shopCategory.existCategory(categoryId)) {
            sendMessage(sender, message.getString(Messages.SHOP_CATEGORY_NOT_EXIST));
            return;
        }

        if (!shopItem.existItem(categoryId, itemId)) {
            sendMessage(sender, message.getString(Messages.SHOP_ITEM_NOT_EXIST));
            return;
        }

        shopItem.removeItem(categoryId, itemId);
        sendMessage(sender, message.getString(Messages.SHOP_ITEM_REMOVE));
    }

    private void editItem(CommandSender sender, Command command, String categoryId, String itemId, String[] args) {
        //          0    1            2        3                               4
        // shopItem edit <categoryId> <itemId> <material|buy|sell|displayName> <value>
        if (!shopCategory.existCategory(categoryId)) {
            sendMessage(sender, message.getString(Messages.SHOP_CATEGORY_NOT_EXIST));
            return;
        }

        if (!shopItem.existItem(categoryId, itemId)) {
            sendMessage(sender, message.getString(Messages.SHOP_ITEM_NOT_EXIST));
            return;
        }

        switch (args[3]) {
            case "material" -> {
                Material material = Material.getMaterial(args[4]);
                if (material == null) {
                    sendMessage(sender, message.getString(Messages.INVALID_ITEM));
                    return;
                }

                String displayName = shopItem.getDisplayName(categoryId, itemId);
                double buy = shopItem.getBuyPrice(categoryId, itemId);
                double sell = shopItem.getSellPrice(categoryId, itemId);
                shopItem.updateItem(categoryId, itemId, displayName, material, buy, sell);
                sendMessage(sender, message.getString(Messages.SHOP_ITEM_EDIT));
            }
            case "buy" -> {
                String buy = args[4];
                if (!Economy.MONEY_PATTERN.matcher(buy).matches()) {
                    sendMessage(sender, message.getString(Messages.INVALID_NUMBERS));
                    return;
                }

                double buyPrice = Double.parseDouble(buy);
                Material material = shopItem.getMaterial(categoryId, itemId);
                if (material == null) {
                    sendMessage(sender, message.getString(Messages.INVALID_ITEM));
                    return;
                }
                String displayName = shopItem.getDisplayName(categoryId, itemId);
                double sell = shopItem.getSellPrice(categoryId, itemId);
                shopItem.updateItem(categoryId, itemId, displayName, material, buyPrice, sell);
                sendMessage(sender, message.getString(Messages.SHOP_ITEM_EDIT));
            }
            case "sell" -> {
                String sell = args[4];
                if (!Economy.MONEY_PATTERN.matcher(sell).matches()) {
                    sendMessage(sender, message.getString(Messages.INVALID_NUMBERS));
                    return;
                }

                double sellPrice = Double.parseDouble(sell);
                Material material = shopItem.getMaterial(categoryId, itemId);
                if (material == null) {
                    sendMessage(sender, message.getString(Messages.INVALID_ITEM));
                    return;
                }
                String displayName = shopItem.getDisplayName(categoryId, itemId);
                double buy = shopItem.getBuyPrice(categoryId, itemId);
                shopItem.updateItem(categoryId, itemId, displayName, material, buy, sellPrice);
                sendMessage(sender, message.getString(Messages.SHOP_ITEM_EDIT));
            }
            case "displayName" -> {
                StringBuilder builder = new StringBuilder();
                for (int i = 4; i < args.length; i++)
                    builder.append(args[i]).append(" ");
                String displayName = builder.toString().trim();

                Material material = shopItem.getMaterial(categoryId, itemId);
                if (material == null) {
                    sendMessage(sender, message.getString(Messages.INVALID_ITEM));
                    return;
                }
                double buy = shopItem.getBuyPrice(categoryId, itemId);
                double sell = shopItem.getSellPrice(categoryId, itemId);
                shopItem.updateItem(categoryId, itemId, displayName, material, buy, sell);
                sendMessage(sender, message.getString(Messages.SHOP_ITEM_EDIT));
            }
            default ->
                    sendMessage(sender, message.getString(Messages.COMMAND_SYNTAX).replace("[USAGE]", command.getUsage()));
        }
    }
}
