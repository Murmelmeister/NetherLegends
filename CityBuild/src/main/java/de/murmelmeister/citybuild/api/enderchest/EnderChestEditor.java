package de.murmelmeister.citybuild.api.enderchest;

import de.murmelmeister.citybuild.CityBuild;
import de.murmelmeister.citybuild.files.ConfigFile;
import de.murmelmeister.citybuild.util.config.Configs;
import de.murmelmeister.murmelapi.utils.Database;
import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bukkit.Material;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;

import java.util.Arrays;
import java.util.List;

import static de.murmelmeister.citybuild.util.InventoryUtil.loadItems;
import static de.murmelmeister.citybuild.util.InventoryUtil.saveItems;

public final class EnderChestEditor {
    public static final String METADATA_KEY = "EnderChestEditor";
    public static final String METADATA_VALUE = "EnderChestInventory";
    private final ConfigFile configFile;

    public EnderChestEditor(ConfigFile configFile) {
        this.configFile = configFile;
        String tableName = "CB_EnderChests";
        createTable(tableName);
        Procedure.loadAll(tableName);
    }

    private void createTable(String tableName) {
        Database.createTable(tableName, "UserID INT, Slot INT, PRIMARY KEY (UserID, Slot), Content MEDIUMTEXT, StorageContent MEDIUMTEXT");
    }

    public boolean exists(int userId, int slot) {
        return Database.callExists(Procedure.ENDER_CHEST_GET.getName(), userId, slot);
    }

    public void createOrUpdate(int userId, int slot, Inventory inventory, boolean update) {
        System.out.println("Save Content: " + Arrays.toString(inventory.getContents()));
        System.out.println("Save StorageContent: " + Arrays.toString(inventory.getStorageContents()));

        ItemStack[] contents = inventory.getContents();

        if (contents.length > 0) contents = Arrays.copyOf(contents, contents.length - 9);
        System.out.println("Save Content edited: " + Arrays.toString(contents));

        String content = saveItems(contents);
        String storageContent = saveItems(inventory.getStorageContents());
        if (update)
            Database.callUpdate(Procedure.ENDER_CHEST_UPDATE.getName(), userId, slot, content, storageContent);
        else
            Database.callUpdate(Procedure.ENDER_CHEST_CREATE.getName(), userId, slot, content, storageContent);
    }

    public void delete(int userId, int slot) {
        Database.callUpdate(Procedure.ENDER_CHEST_DELETE.getName(), userId, slot);
    }

    public void deleteAll(int userId) {
        Database.callUpdate(Procedure.ENDER_CHEST_DELETE_ALL.getName(), userId);
    }

    public ItemStack[] getContents(int userId, int slot) {
        return loadItems(Database.callQuery(null, "Content", String.class, Procedure.ENDER_CHEST_GET.getName(), userId, slot));
    }

    public ItemStack[] getStorageContents(int userId, int slot) {
        return loadItems(Database.callQuery(null, "StorageContent", String.class, Procedure.ENDER_CHEST_GET.getName(), userId, slot));
    }

    public void setInventory(int userId, int slot, Inventory inventory) {
        ItemStack[] contents = getContents(userId, slot);
        ItemStack[] storageContents = getStorageContents(userId, slot);

        System.out.println("Load Contents: " + Arrays.toString(contents));
        System.out.println("Load StorageContents: " + Arrays.toString(storageContents));

        inventory.setContents(contents);
        inventory.setStorageContents(storageContents);
    }

    public List<Integer> getSlots(int userId) {
        return Database.callQueryList("Slot", Integer.class, Procedure.ENDER_CHEST_GET_ALL.getName(), userId);
    }

    public ItemStack getLockedIcon(int slot) {
        Material material = Material.getMaterial(configFile.getString(Configs.ENDER_CHEST_MATERIAL_LOCKED));
        if (material == null) return null;
        ItemStack itemStack = new ItemStack(material);
        ItemMeta itemMeta = itemStack.getItemMeta();
        PersistentDataContainer container = itemMeta.getPersistentDataContainer();
        container.set(CityBuild.getKeyEnderChest(), PersistentDataType.BOOLEAN, true);
        itemMeta.displayName(MiniMessage.miniMessage().deserialize(configFile.getString(Configs.ENDER_CHEST_DISPLAY_NAME_LOCKED).replace("[SLOT]", slot + "")));
        itemStack.setItemMeta(itemMeta);
        return itemStack;
    }

    public ItemStack getUnlockedIcon(int slot) {
        Material material = Material.getMaterial(configFile.getString(Configs.ENDER_CHEST_MATERIAL_UNLOCKED));
        if (material == null) return null;
        ItemStack itemStack = new ItemStack(material);
        ItemMeta itemMeta = itemStack.getItemMeta();
        PersistentDataContainer container = itemMeta.getPersistentDataContainer();
        container.set(CityBuild.getKeyEnderChest(), PersistentDataType.BOOLEAN, true);
        itemMeta.displayName(MiniMessage.miniMessage().deserialize(configFile.getString(Configs.ENDER_CHEST_DISPLAY_NAME_UNLOCKED).replace("[SLOT]", slot + "")));
        itemStack.setItemMeta(itemMeta);
        return itemStack;
    }

    private enum Procedure {
        ENDER_CHEST_CREATE("EnderChest_Create", "uid INT, sid INT, cont MEDIUMTEXT, storage MEDIUMTEXT", "INSERT INTO [TABLE] VALUES (uid, sid, cont, storage);"),
        ENDER_CHEST_UPDATE("EnderChest_Update", "uid INT, sid INT, cont MEDIUMTEXT, storage MEDIUMTEXT", "UPDATE [TABLE] SET Content=cont, StorageContent=storage WHERE UserID=uid AND Slot=sid;"),
        ENDER_CHEST_DELETE("EnderChest_Delete", "uid INT, sid INT", "DELETE FROM [TABLE] WHERE UserID=uid AND Slot=sid;"),
        ENDER_CHEST_DELETE_ALL("EnderChest_DeleteAll", "uid INT", "DELETE FROM [TABLE] WHERE UserID=uid;"),
        ENDER_CHEST_GET("EnderChest_Get", "uid INT, sid INT", "SELECT * FROM [TABLE] WHERE UserID=uid AND Slot=sid;"),
        ENDER_CHEST_GET_ALL("EnderChest_GetAll", "uid INT", "SELECT * FROM [TABLE] WHERE UserID=uid;");
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
