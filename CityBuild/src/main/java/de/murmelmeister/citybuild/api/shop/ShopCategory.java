package de.murmelmeister.citybuild.api.shop;

import de.murmelmeister.citybuild.CityBuild;
import de.murmelmeister.murmelapi.utils.Database;
import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;

import java.util.List;

public final class ShopCategory {
    public ShopCategory() {
        String tableName = "ShopCategory";
        createTable(tableName);
        Procedure.loadAll(tableName);
    }

    private void createTable(String tableName) {
        Database.createTable(tableName, "CategoryID VARCHAR(255) PRIMARY KEY, DisplayName TINYTEXT, IconItem TINYTEXT");
    }

    public boolean existCategory(String id) {
        return Database.callExists(Procedure.CATEGORY_GET.getName(), id);
    }

    public void addCategory(String id, String displayName, Material icon) {
        if (id.length() > 255) throw new IllegalArgumentException("CategoryID is too long. Max length is 255 characters");
        if (displayName.length() > 255) throw new IllegalArgumentException("DisplayName is too long. Max length is 255 characters");
        if (icon.name().length() > 255) throw new IllegalArgumentException("IconItem is too long. Max length is 255 characters");
        Database.callUpdate(Procedure.CATEGORY_CREATE.getName(), id, displayName, icon.name());
    }

    public void removeCategory(ShopItem item, String id) {
        item.removeCategoryItems(id);
        Database.callUpdate(Procedure.CATEGORY_DELETE.getName(), id);
    }

    public Material getIconMaterial(String id) {
        String material = Database.callQuery(null, "IconItem", String.class, Procedure.CATEGORY_GET.getName(), id);
        return material == null ? null : Material.getMaterial(material);
    }

    public String getDisplayName(String id) {
        return Database.callQuery(null, "DisplayName", String.class, Procedure.CATEGORY_GET.getName(), id);
    }

    public ItemStack getIcon(String id) {
        Material material = getIconMaterial(id);
        if (material == null) return null;
        ItemStack itemStack = new ItemStack(material);
        ItemMeta itemMeta = itemStack.getItemMeta();
        PersistentDataContainer container = itemMeta.getPersistentDataContainer();
        container.set(CityBuild.getKeyShopCategory(), PersistentDataType.BOOLEAN, true);
        itemMeta.displayName(MiniMessage.miniMessage().deserialize(getDisplayName(id)));
        itemStack.setItemMeta(itemMeta);
        return itemStack;
    }

    public List<String> getCategories() {
        return Database.callQueryList("CategoryID", String.class, Procedure.CATEGORY_GET_ALL.getName());
    }

    public void updateCategory(String id, String displayName, Material icon) {
        if (displayName.length() > 255) throw new IllegalArgumentException("DisplayName is too long. Max length is 255 characters");
        if (icon.name().length() > 255) throw new IllegalArgumentException("IconItem is too long. Max length is 255 characters");
        Database.callUpdate(Procedure.CATEGORY_UPDATE.getName(), id, displayName, icon.name());
    }

    private enum Procedure {
        CATEGORY_CREATE("Shop_Category_Create", "id VARCHAR(255), display TINYTEXT, icon TINYTEXT", "INSERT INTO [TABLE] VALUES (id, display, icon);"),
        CATEGORY_DELETE("Shop_Category_Delete", "id VARCHAR(255)", "DELETE FROM [TABLE] WHERE CategoryID=id;"),
        CATEGORY_GET("Shop_Category_Get", "id VARCHAR(255)", "SELECT * FROM [TABLE] WHERE CategoryID=id;"),
        CATEGORY_GET_ALL("Shop_Category_GetAll", "", "SELECT * FROM [TABLE];"),
        CATEGORY_UPDATE("Shop_Category_Update", "id VARCHAR(255), display TINYTEXT, icon TINYTEXT", "UPDATE [TABLE] SET DisplayName=display, IconItem=icon WHERE CategoryID=id;");
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
