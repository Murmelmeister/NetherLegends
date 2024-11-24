package de.murmelmeister.citybuild.api;

import de.murmelmeister.murmelapi.utils.Database;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.yaml.snakeyaml.external.biz.base64Coder.Base64Coder;

import java.io.*;

public class PlayerInventory {
    public PlayerInventory() {
        String tableName = "CB_UserInventory";
        createTable(tableName);
        Procedure.loadAll(tableName);
    }

    private void createTable(String tableName) {
        Database.createTable(tableName, "UserID INT PRIMARY KEY, Contents MEDIUMTEXT, StorageContents MEDIUMTEXT");
    }

    public boolean existInventory(int userId) {
        return Database.callExists(Procedure.INVENTORY_GET.getName(), userId);
    }

    public void createInventory(int userId, Inventory inventory) {
        String contents = saveItems(inventory.getContents());
        String storageContents = saveItems(inventory.getStorageContents());
        Database.callUpdate(Procedure.INVENTORY_CREATE.getName(), userId, contents, storageContents);
    }

    public void deleteInventory(int userId) {
        Database.callUpdate(Procedure.INVENTORY_DELETE.getName(), userId);
    }

    public void updateInventory(int userId, Inventory inventory) {
        String contents = saveItems(inventory.getContents());
        String storageContents = saveItems(inventory.getStorageContents());
        Database.callUpdate(Procedure.INVENTORY_UPDATE.getName(), userId, contents, storageContents);
    }

    public ItemStack[] getContents(int userId) {
        return loadItems(Database.callQuery(null, "Contents", String.class, Procedure.INVENTORY_GET.getName(), userId));
    }

    public ItemStack[] getStorageContents(int userId) {
        return loadItems(Database.callQuery(null, "StorageContents", String.class, Procedure.INVENTORY_GET.getName(), userId));
    }

    public String saveItems(ItemStack[] items) {
        try (ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
             DataOutputStream dataOutputStream = new DataOutputStream(outputStream)) {
            dataOutputStream.writeInt(items.length);
            for (ItemStack item : items) {
                if (item != null) {
                    dataOutputStream.writeBoolean(true);
                    byte[] itemBytes = item.serializeAsBytes();
                    dataOutputStream.writeInt(itemBytes.length);
                    dataOutputStream.write(itemBytes);
                } else {
                    dataOutputStream.writeBoolean(false);
                }
            }
            return Base64Coder.encodeLines(outputStream.toByteArray());
        } catch (IOException e) {
            throw new RuntimeException("Unable to save items", e);
        }
    }

    public ItemStack[] loadItems(String data) {
        try (ByteArrayInputStream inputStream = new ByteArrayInputStream(Base64Coder.decodeLines(data));
        DataInputStream dataInputStream = new DataInputStream(inputStream)) {
            ItemStack[] items = new ItemStack[dataInputStream.readInt()];
            for (int i = 0; i < items.length; i++) {
                boolean isItemNotNull = dataInputStream.readBoolean();
                if (isItemNotNull) {
                    int itemLength = dataInputStream.readInt();
                    byte[] itemBytes = new byte[itemLength];
                    dataInputStream.readFully(itemBytes);
                    items[i] = ItemStack.deserializeBytes(itemBytes);
                } else {
                    items[i] = null;
                }
            }
            return items;
        } catch (IOException e) {
            throw new RuntimeException("Unable to load items", e);
        }
    }

    private enum Procedure {
        INVENTORY_CREATE("PlayerInventory_Create", "uid INT, content MEDIUMTEXT, storage MEDIUMTEXT", "INSERT INTO [TABLE] VALUES (uid, content, storage);"),
        INVENTORY_UPDATE("PlayerInventory_Update", "uid INT, content MEDIUMTEXT, storage MEDIUMTEXT", "UPDATE [TABLE] SET Contents=content, StorageContents=storage WHERE UserID=uid;"),
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
