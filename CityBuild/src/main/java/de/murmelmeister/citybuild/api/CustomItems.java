package de.murmelmeister.citybuild.api;

import de.murmelmeister.murmelapi.utils.Database;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import java.util.List;

public final class CustomItems {
    public CustomItems() {
        String tableName = "CB_CustomItems";
        createTable(tableName);
        Procedure.loadAll(tableName);
    }

    private void createTable(String tableName) {
        Database.createTable(tableName, "ItemID VARCHAR(255) PRIMARY KEY, DisplayName TINYTEXT, Material TINYTEXT, Lore MEDIUMTEXT, IsStatic BOOLEAN");
    }

    public boolean existItem(String itemId) {
        return Database.callExists(Procedure.ITEM_GET.getName(), itemId);
    }

    public void addItem(String itemId, String displayName, String material, String lore, boolean isStatic) {
        if (existItem(itemId)) return;
        byte staticItem = (byte) (isStatic ? 1 : 0);
        Database.callUpdate(Procedure.ITEM_ADD.getName(), itemId, displayName, material, lore, staticItem);
    }

    public void addItem(String itemId, String displayName, Material material, String lore, boolean isStatic) {
        addItem(itemId, displayName, material.name(), lore, isStatic);
    }

    public void removeItem(String itemId) {
        Database.callUpdate(Procedure.ITEM_REMOVE.getName(), itemId);
    }

    public String getDisplayName(String itemId) {
        return Database.callQuery(null, "DisplayName", String.class, Procedure.ITEM_GET.getName(), itemId);
    }

    public String getMaterialName(String itemId) {
        return Database.callQuery(null, "Material", String.class, Procedure.ITEM_GET.getName(), itemId);
    }

    public Material getMaterial(String itemId) {
        String material = getMaterialName(itemId);
        return material == null ? null : Material.getMaterial(material);
    }

    public String getLore(String itemId) {
        return Database.callQuery(null, "Lore", String.class, Procedure.ITEM_GET.getName(), itemId);
    }

    public boolean isStatic(String itemId) {
        return Database.callQuery(false, "IsStatic", boolean.class, Procedure.ITEM_GET.getName(), itemId);
    }

    public void updateItem(String itemId, String displayName, String material, String lore, boolean isStatic) {
        byte staticItem = (byte) (isStatic ? 1 : 0);
        Database.callUpdate(Procedure.ITEM_UPDATE.getName(), itemId, displayName, material, lore, staticItem);
    }

    public void updateItem(String itemId, String displayName, Material material, String lore, boolean isStatic) {
        updateItem(itemId, displayName, material.name(), lore, isStatic);
    }

    public List<String> getItemIDs() {
        return Database.callQueryList("ItemID", String.class, Procedure.ITEM_GET_ALL.getName());
    }

    public void loadAllMaterials() {
        for (Material material : Material.values()) {
            if (!material.isItem()) continue;
            ItemStack itemStack = new ItemStack(material);
            addItem(material.name(), itemStack.getI18NDisplayName(), material, null, true);
        }
    }

    private enum Procedure {
        ITEM_ADD("CustomItem_Add", "iid VARCHAR(255), display TINYTEXT, imaterial TINYTEXT, ilore MEDIUMTEXT, static BOOLEAN",
                "INSERT INTO [TABLE] VALUES (iid, display, imaterial, ilore, static);"),
        ITEM_REMOVE("CustomItem_Remove", "iid VARCHAR(255)", "DELETE FROM [TABLE] WHERE ItemID=iid;"),
        ITEM_GET("CustomItem_Get", "iid VARCHAR(255)", "SELECT * FROM [TABLE] WHERE ItemID=iid;"),
        ITEM_GET_ALL("CustomItem_GetAll", "", "SELECT * FROM [TABLE];"),
        ITEM_UPDATE("CustomItem_Update", "iid VARCHAR(255), display TINYTEXT, imaterial TINYTEXT, ilore MEDIUMTEXT, static BOOLEAN",
                "UPDATE [TABLE] SET DisplayName=display, Material=imaterial, Lore=ilore, IsStatic=static WHERE ItemID=iid;");
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

        public static void loadAll(String tableName) {
            for (Procedure procedure : VALUES) Database.update(procedure.getQuery(tableName));
        }
    }
}
