package de.murmelmeister.citybuild.api;

import de.murmelmeister.citybuild.CityBuild;
import de.murmelmeister.citybuild.util.FileUtil;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.slf4j.Logger;
import org.yaml.snakeyaml.external.biz.base64Coder.Base64Coder;

import java.io.*;
import java.util.UUID;

public class PlayerInventory {
    private final Logger logger;
    private File file;
    private YamlConfiguration config;

    public PlayerInventory(final Logger logger) {
        this.logger = logger;
    }

    public void reloadFile(UUID uuid) {
        createFile(uuid);
    }

    private void createFile(UUID uuid) {
        this.file = FileUtil.createFile(logger, CityBuild.getMainPath() + "/Inventories/", uuid.toString() + ".yml");
        this.config = YamlConfiguration.loadConfiguration(this.file);
    }

    private void saveFile() {
        try {
            this.config.save(file);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public boolean existFile(UUID uuid) {
        File path = new File("./plugins/" + CityBuild.class.getSimpleName() + "/Inventories/", uuid.toString() + ".yml");
        return path.exists();
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

    public void savePlayerInventory(UUID uuid, Inventory inventory) {
        createFile(uuid);
        String path = "Inventories.Player.";
        String contents = saveItems(inventory.getContents());
        String storageContents = saveItems(inventory.getStorageContents());
        config.set(path + "Contents", contents);
        config.set(path + "StorageContents", storageContents);
        saveFile();
    }

    public Inventory loadPlayerInventory(UUID uuid) {
        String path = "Inventories.Player.";
        String contents = config.getString(path + "Contents");
        String storageContents = config.getString(path + "StorageContents");
        Inventory inventory = Bukkit.createInventory(null, 45, Component.text(uuid.toString(), NamedTextColor.DARK_PURPLE));
        if (contents != null) inventory.setContents(loadItems(contents));
        if (storageContents != null) inventory.setStorageContents(loadItems(storageContents));
        return inventory;
    }
}
