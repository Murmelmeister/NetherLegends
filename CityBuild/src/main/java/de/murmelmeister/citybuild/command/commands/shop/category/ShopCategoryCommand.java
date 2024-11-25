package de.murmelmeister.citybuild.command.commands.shop.category;

import de.murmelmeister.citybuild.CityBuild;
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

public final class ShopCategoryCommand extends CommandManager {
    public ShopCategoryCommand(CityBuild plugin) {
        super(plugin);
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (!isEnable(sender, Configs.COMMAND_ENABLE_SHOP_CATEGORY)) return true;
        if (!hasPermission(sender, Configs.PERMISSION_SHOP_CATEGORY)) return true;

        if (args.length == 1 && args[0].equals("import")) {
            shopCategory.importCategories(logger, config);
            sendMessage(sender, message.getString(Messages.SHOP_CATEGORY_IMPORT).replace("[FILE]", config.getString(Configs.IMPORT_DATA_SHOP_CATEGORIES)));
            return true;
        }

        if (args.length < 2) {
            sendMessage(sender, message.getString(Messages.COMMAND_SYNTAX).replace("[USAGE]", command.getUsage()));
            return true;
        }

        String categoryId = args[1];
        switch (args[0]) {
            case "add" -> addCategory(sender, command, categoryId, args);
            case "remove" -> removeCategory(sender, categoryId);
            case "edit" -> editCategory(sender, command, categoryId, args);
            default -> sendMessage(sender, message.getString(Messages.COMMAND_SYNTAX).replace("[USAGE]", command.getUsage()));
        }
        return true;
    }

    @Override
    public @NotNull List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (args.length == 1)
            return tabComplete(Arrays.asList("add", "remove", "edit", "import"), args);
        if (args.length == 2 && (args[0].equals("remove") || args[0].equals("edit")))
            return tabComplete(shopCategory.getCategories(), args);
        if (args.length == 3 && args[0].equals("add"))
            return tabComplete(Arrays.stream(Material.values()).map(Material::name).collect(Collectors.toList()), args);
        if (args.length == 3 && args[0].equals("edit"))
            return tabComplete(Arrays.asList("icon", "displayname"), args);
        if (args.length == 4 && args[0].equals("edit") && args[2].equals("icon"))
            return tabComplete(Arrays.stream(Material.values()).map(Material::name).collect(Collectors.toList()), args);
        return Collections.emptyList();
    }

    private void addCategory(CommandSender sender, Command command, String categoryId, String[] args) {
        if (args.length == 2) {
            sendMessage(sender, message.getString(Messages.COMMAND_SYNTAX).replace("[USAGE]", command.getUsage()));
            return;
        }

        if (shopCategory.existCategory(categoryId)) {
            sendMessage(sender, message.getString(Messages.SHOP_CATEGORY_EXIST));
            return;
        }

        String materialName = args[2];
        Material material = Material.getMaterial(materialName);
        if (material == null) {
            sendMessage(sender, message.getString(Messages.INVALID_ITEM));
            return;
        }

        StringBuilder builder = new StringBuilder();
        for (int i = 3; i < args.length; i++)
            builder.append(args[i]).append(" ");
        String displayName = builder.toString().trim();

        shopCategory.addCategory(categoryId, displayName, material);
        sendMessage(sender, message.getString(Messages.SHOP_CATEGORY_ADD));
    }

    private void removeCategory(CommandSender sender, String categoryId) {
        if (!shopCategory.existCategory(categoryId)) {
            sendMessage(sender, message.getString(Messages.SHOP_CATEGORY_NOT_EXIST));
            return;
        }

        shopCategory.removeCategory(shopItem, categoryId);
        sendMessage(sender, message.getString(Messages.SHOP_CATEGORY_REMOVE));
    }

    private void editCategory(CommandSender sender, Command command, String categoryId, String[] args) {
        if (args.length == 2) {
            sendMessage(sender, message.getString(Messages.COMMAND_SYNTAX).replace("[USAGE]", command.getUsage()));
            return;
        }

        if (!shopCategory.existCategory(categoryId)) {
            sendMessage(sender, message.getString(Messages.SHOP_CATEGORY_NOT_EXIST));
            return;
        }

        switch (args[2]) {
            case "icon" -> {
                Material material = Material.getMaterial(args[3]);
                if (material == null) {
                    sendMessage(sender, message.getString(Messages.INVALID_ITEM));
                    return;
                }

                String displayName = shopCategory.getDisplayName(categoryId);
                shopCategory.updateCategory(categoryId, displayName, material);
                sendMessage(sender, message.getString(Messages.SHOP_CATEGORY_EDIT));
            }
            case "displayname" -> {
                StringBuilder builder = new StringBuilder();
                for (int i = 3; i < args.length; i++)
                    builder.append(args[i]).append(" ");
                String displayName = builder.toString().trim();

                Material icon = shopCategory.getIconMaterial(categoryId);
                if (icon == null) {
                    sendMessage(sender, message.getString(Messages.INVALID_ITEM));
                    return;
                }
                shopCategory.updateCategory(categoryId, displayName, icon);
                sendMessage(sender, message.getString(Messages.SHOP_CATEGORY_EDIT));
            }
            default -> sendMessage(sender, message.getString(Messages.COMMAND_SYNTAX).replace("[USAGE]", command.getUsage()));
        }
    }
}
