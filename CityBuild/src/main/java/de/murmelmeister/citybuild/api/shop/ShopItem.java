package de.murmelmeister.citybuild.api.shop;

import de.murmelmeister.citybuild.CityBuild;
import de.murmelmeister.citybuild.api.Economy;
import de.murmelmeister.citybuild.files.ConfigFile;
import de.murmelmeister.citybuild.files.MessageFile;
import de.murmelmeister.citybuild.util.config.Configs;
import de.murmelmeister.citybuild.util.config.Messages;
import de.murmelmeister.murmelapi.utils.Database;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bukkit.Material;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

public final class ShopItem {
    public ShopItem() {
        String tableName = "ShopItems";
        createTable(tableName);
        Procedure.loadAll(tableName);
    }

    /*
     * ItemId soll zu mehreren Kategorien gehören können
     */
    private void createTable(String tableName) {
        Database.createTable(tableName, "CategoryID VARCHAR(255), ItemID VARCHAR(255), PRIMARY KEY (CategoryID, ItemID), DisplayName TINYTEXT, Material TINYTEXT, BuyPrice DOUBLE, SellPrice DOUBLE");
    }

    public boolean existItem(String categoryId, String itemId) {
        return Database.callExists(Procedure.ITEMS_GET.getName(), categoryId, itemId);
    }

    public void addItem(String categoryId, String itemId, String displayName, Material material, double buy, double sell) {
        if (categoryId.length() > 255)
            throw new IllegalArgumentException("CategoryID is too long. Max length is 255 characters");
        if (itemId.length() > 255)
            throw new IllegalArgumentException("ItemID is too long. Max length is 255 characters");
        if (displayName.length() > 255)
            throw new IllegalArgumentException("DisplayName is too long. Max length is 255 characters");
        if (material.name().length() > 255)
            throw new IllegalArgumentException("Material is too long. Max length is 255 characters");
        Database.callUpdate(Procedure.ITEMS_CREATE.getName(), categoryId, itemId, displayName, material.name(), buy, sell);
    }

    public void removeItem(String categoryId, String itemId) {
        Database.callUpdate(Procedure.ITEMS_DELETE.getName(), categoryId, itemId);
    }

    public void removeCategoryItems(String categoryId) {
        Database.callUpdate(Procedure.ITEMS_DELETE_CATEGORY_ITEMS.getName(), categoryId);
    }

    public Material getMaterial(String categoryId, String itemId) {
        String material = Database.callQuery(null, "Material", String.class, Procedure.ITEMS_GET.getName(), categoryId, itemId);
        return material == null ? null : Material.getMaterial(material);
    }

    public String getDisplayName(String categoryId, String itemId) {
        return Database.callQuery(null, "DisplayName", String.class, Procedure.ITEMS_GET.getName(), categoryId, itemId);
    }

    public double getBuyPrice(String categoryId, String itemId) {
        return Database.callQuery(0.0D, "BuyPrice", double.class, Procedure.ITEMS_GET.getName(), categoryId, itemId);
    }

    public double getSellPrice(String categoryId, String itemId) {
        return Database.callQuery(0.0D, "SellPrice", double.class, Procedure.ITEMS_GET.getName(), categoryId, itemId);
    }

    public ItemStack getItemStack(Material material, int amount) {
        return new ItemStack(material, amount);
    }

    public ItemStack getIcon(ConfigFile configFile, MessageFile messageFile, Economy economy, int userId, String categoryId, String itemId) {
        Material material = getMaterial(categoryId, itemId);
        if (material == null) return null;
        DecimalFormat decimalFormat = new DecimalFormat(configFile.getString(Configs.PATTERN_DECIMAL));
        int maxStackSize = material.getMaxStackSize();
        double buy = getBuyPrice(categoryId, itemId);
        double sell = getSellPrice(categoryId, itemId);
        ItemStack itemStack = new ItemStack(material);
        ItemMeta itemMeta = itemStack.getItemMeta();

        PersistentDataContainer container = itemMeta.getPersistentDataContainer();
        container.set(CityBuild.getKeyShopItem(), PersistentDataType.BOOLEAN, true);

        itemMeta.displayName(MiniMessage.miniMessage().deserialize(getDisplayName(categoryId, itemId)));

        List<Component> lore = new ArrayList<>();
        lore.add(MiniMessage.miniMessage().deserialize(" "));
        lore.add(MiniMessage.miniMessage().deserialize(messageFile.getString(Messages.SHOP_ITEM_BUY_ONE).replace("[PRICE]", decimalFormat.format(buy))));

        if (maxStackSize != 1)
            lore.add(MiniMessage.miniMessage().deserialize(messageFile.getString(Messages.SHOP_ITEM_BUY_STACK).replace("[PRICE]", decimalFormat.format(buy * maxStackSize))));

        lore.add(MiniMessage.miniMessage().deserialize(messageFile.getString(Messages.SHOP_ITEM_SELL).replace("[SELL]", decimalFormat.format(sell))));

        lore.add(MiniMessage.miniMessage().deserialize(" "));
        lore.add(MiniMessage.miniMessage().deserialize(messageFile.getString(Messages.SHOP_ITEM_MONEY).replace("[MONEY]", decimalFormat.format(economy.getMoney(userId)))));

        if (economy.hasEnoughMoney(userId, buy))
            lore.add(MiniMessage.miniMessage().deserialize(messageFile.getString(Messages.SHOP_ITEM_MONEY_LEFT_ITEM).replace("[MONEY]", decimalFormat.format(economy.getMoney(userId) - buy))));
        else
            lore.add(MiniMessage.miniMessage().deserialize(messageFile.getString(Messages.SHOP_ITEM_MONEY_NOT_ENOUGH_ITEM)));

        if (maxStackSize != 1)
            if (economy.hasEnoughMoney(userId, buy * maxStackSize))
                lore.add(MiniMessage.miniMessage().deserialize(messageFile.getString(Messages.SHOP_ITEM_MONEY_LEFT_STACK).replace("[MONEY]", decimalFormat.format(economy.getMoney(userId) - buy * maxStackSize))));
            else
                lore.add(MiniMessage.miniMessage().deserialize(messageFile.getString(Messages.SHOP_ITEM_MONEY_NOT_ENOUGH_STACK)));

        itemMeta.lore(lore);
        for (ItemFlag flag : ItemFlag.values())
            itemMeta.addItemFlags(flag);
        itemMeta.removeItemFlags(ItemFlag.HIDE_ADDITIONAL_TOOLTIP);
        itemMeta.removeItemFlags(ItemFlag.HIDE_ATTRIBUTES);

        itemStack.setItemMeta(itemMeta);
        return itemStack;
    }

    public List<String> getCategoryItems(String categoryId) {
        return Database.callQueryList("ItemID", String.class, Procedure.ITEMS_GET_CATEGORY_ITEMS.getName(), categoryId);
    }

    public void updateItem(String categoryId, String itemId, String displayName, Material material, double buy, double sell) {
        if (categoryId.length() > 255)
            throw new IllegalArgumentException("CategoryID is too long. Max length is 255 characters");
        if (itemId.length() > 255)
            throw new IllegalArgumentException("ItemID is too long. Max length is 255 characters");
        if (displayName.length() > 255)
            throw new IllegalArgumentException("DisplayName is too long. Max length is 255 characters");
        if (material.name().length() > 255)
            throw new IllegalArgumentException("Material is too long. Max length is 255 characters");
        Database.callUpdate(Procedure.ITEMS_UPDATE.getName(), categoryId, itemId, displayName, material.name(), buy, sell);
    }

    private enum Procedure {
        ITEMS_CREATE("Shop_Items_Create", "cid VARCHAR(255), item VARCHAR(255), display TINYTEXT, mat TINYTEXT, buy DOUBLE, sell DOUBLE",
                "INSERT INTO [TABLE] VALUES (cid, item, display, mat, buy, sell);"),
        ITEMS_DELETE("Shop_Items_Delete", "cid VARCHAR(255), item VARCHAR(255)", "DELETE FROM [TABLE] WHERE CategoryID=cid AND ItemID=item;"),
        ITEMS_DELETE_CATEGORY_ITEMS("Shop_Items_DeleteCategoryItems", "cid VARCHAR(255)", "DELETE FROM [TABLE] WHERE CategoryID=cid;"),
        ITEMS_GET("Shop_Items_Get", "cid VARCHAR(255), item VARCHAR(255)", "SELECT * FROM [TABLE] WHERE CategoryID=cid AND ItemID=item;"),
        ITEMS_GET_CATEGORY_ITEMS("Shop_Items_GetCategoryItems", "cid VARCHAR(255)", "SELECT * FROM [TABLE] WHERE CategoryID=cid;"),
        ITEMS_UPDATE("Shop_Items_Update", "cid VARCHAR(255), item VARCHAR(255), display TINYTEXT, mat TINYTEXT, buy DOUBLE, sell DOUBLE",
                "UPDATE [TABLE] SET DisplayName=display, Material=mat, BuyPrice=buy, SellPrice=sell WHERE CategoryID=cid AND ItemID=item;");
        private static final Procedure[] VALUES = values();

        private final String name;
        private final String query;

        Procedure(final String name, final String input, final String query) {
            this.name = name;
            this.query = Database.getProcedureQueryWithoutObjects(name, input, query);
        }

        public String getName() {
            return name;
        }

        public String getQuery(String tableName) {
            return query.replace("[TABLE]", tableName);
        }

        private static void loadAll(String tableName) {
            for (Procedure procedure : VALUES) Database.update(procedure.getQuery(tableName));
        }
    }
}
