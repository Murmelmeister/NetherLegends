package de.murmelmeister.citybuild.api;

import de.murmelmeister.murmelapi.utils.Database;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import static de.murmelmeister.citybuild.util.InventoryUtil.loadItems;
import static de.murmelmeister.citybuild.util.InventoryUtil.saveItems;

public final class PlayerInventory {
    public PlayerInventory() {
        String tableName = "CB_UserInventory";
        createTable(tableName);
        Procedure.loadAll(tableName);
    }

    private void createTable(String tableName) {
        Database.createTable(tableName, "UserID INT PRIMARY KEY, Content MEDIUMTEXT, StorageContent MEDIUMTEXT, ArmorContent MEDIUMTEXT, ExtraContent MEDIUMTEXT, " +
                                        "EnderChestContent MEDIUMTEXT, EnderChestStorage MEDIUMTEXT");
    }

    public boolean existInventory(int userId) {
        return Database.callExists(Procedure.INVENTORY_GET.getName(), userId);
    }

    public void createOrUpdateInventory(int userId, Player player, boolean update) {
        String contents = saveItems(player.getInventory().getContents());
        String storageContents = saveItems(player.getInventory().getStorageContents());
        String armorContents = saveItems(player.getInventory().getArmorContents());
        String extraContents = saveItems(player.getInventory().getExtraContents());
        String enderChestContents = saveItems(player.getEnderChest().getContents());
        String enderChestStorage = saveItems(player.getEnderChest().getStorageContents());
        if (update)
            Database.callUpdate(Procedure.INVENTORY_UPDATE.getName(), userId, contents, storageContents, armorContents, extraContents, enderChestContents, enderChestStorage);
        else
            Database.callUpdate(Procedure.INVENTORY_CREATE.getName(), userId, contents, storageContents, armorContents, extraContents, enderChestContents, enderChestStorage);
    }

    public void deleteInventory(int userId) {
        Database.callUpdate(Procedure.INVENTORY_DELETE.getName(), userId);
    }

    public void setInventory(int userId, Player player) {
        player.getInventory().setContents(getContents(userId));
        player.getInventory().setStorageContents(getStorageContents(userId));
        player.getInventory().setArmorContents(getArmorContents(userId));
        player.getInventory().setExtraContents(getExtraContents(userId));
        player.getEnderChest().setContents(getEnderChestContents(userId));
        player.getEnderChest().setStorageContents(getEnderChestStorage(userId));
    }

    public ItemStack[] getContents(int userId) {
        return loadItems(Database.callQuery(null, "Content", String.class, Procedure.INVENTORY_GET.getName(), userId));
    }

    public ItemStack[] getStorageContents(int userId) {
        return loadItems(Database.callQuery(null, "StorageContent", String.class, Procedure.INVENTORY_GET.getName(), userId));
    }

    public ItemStack[] getArmorContents(int userId) {
        return loadItems(Database.callQuery(null, "ArmorContent", String.class, Procedure.INVENTORY_GET.getName(), userId));
    }

    public ItemStack[] getExtraContents(int userId) {
        return loadItems(Database.callQuery(null, "ExtraContent", String.class, Procedure.INVENTORY_GET.getName(), userId));
    }

    public ItemStack[] getEnderChestContents(int userId) {
        return loadItems(Database.callQuery(null, "EnderChestContent", String.class, Procedure.INVENTORY_GET.getName(), userId));
    }

    public ItemStack[] getEnderChestStorage(int userId) {
        return loadItems(Database.callQuery(null, "EnderChestStorage", String.class, Procedure.INVENTORY_GET.getName(), userId));
    }

    private enum Procedure {
        INVENTORY_CREATE("PlayerInventory_Create", "uid INT, cont MEDIUMTEXT, storage MEDIUMTEXT, armor MEDIUMTEXT, extra MEDIUMTEXT, eccont MEDIUMTEXT, ecstorage MEDIUMTEXT",
                "INSERT INTO [TABLE] VALUES (uid, cont, storage, armor, extra, eccont, ecstorage);"),
        INVENTORY_UPDATE("PlayerInventory_Update", "uid INT, cont MEDIUMTEXT, storage MEDIUMTEXT, armor MEDIUMTEXT, extra MEDIUMTEXT, eccont MEDIUMTEXT, ecstorage MEDIUMTEXT",
                "UPDATE [TABLE] SET Content=cont, StorageContent=storage, ArmorContent=armor, ExtraContent=extra, EnderChestContent=eccont, EnderChestStorage=ecstorage WHERE UserID=uid;"),
        INVENTORY_DELETE("PlayerInventory_Delete", "uid INT", "DELETE FROM [TABLE] WHERE UserID=uid;"),
        INVENTORY_GET("PlayerInventory_Get", "uid INT", "SELECT * FROM [TABLE] WHERE UserID=uid;");
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
