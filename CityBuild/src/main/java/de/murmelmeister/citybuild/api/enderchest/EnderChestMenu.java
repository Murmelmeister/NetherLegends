package de.murmelmeister.citybuild.api.enderchest;

import de.murmelmeister.citybuild.files.ConfigFile;
import de.murmelmeister.citybuild.util.config.Configs;
import de.murmelmeister.murmelapi.menu.Menu;
import de.murmelmeister.murmelapi.menu.MultipleMenu;
import de.murmelmeister.murmelapi.menu.model.InventoryDrawer;
import de.murmelmeister.murmelapi.menu.model.MenuClickLocation;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public final class EnderChestMenu extends MultipleMenu<Integer> {
    private final ConfigFile configFile;
    private final EnderChestEditor enderChestEditor;
    private final int userId;

    public EnderChestMenu(ConfigFile configFile, EnderChestEditor enderChestEditor, int userId) {
        super(null, true, enderChestEditor.getSlots(userId));
        this.configFile = configFile;
        this.enderChestEditor = enderChestEditor;
        this.userId = userId;
        setTitle(configFile.getString(Configs.ENDER_CHEST_TITLE));
        Material material = Material.getMaterial(configFile.getString(Configs.ENDER_CHEST_PLACEHOLDER));
        if (material != null) setPlaceholder(material);
    }

    @Override
    protected ItemStack convertToItemStack(Integer slot) {
        if (getViewer().hasPermission(configFile.getString(Configs.ENDER_CHEST_PERMISSION).replace("[SLOT]", String.valueOf(slot))))
            return enderChestEditor.getUnlockedIcon(slot);
        else return enderChestEditor.getLockedIcon(slot);
    }

    @Override
    protected void handlePageClick(Player player, Integer slot, ClickType clickType) {
        if (player.hasPermission(configFile.getString(Configs.ENDER_CHEST_PERMISSION).replace("[SLOT]", String.valueOf(slot))))
            new EnderChestSlotMenu(this, configFile, enderChestEditor, userId, slot).show(player);
    }

    public static class EnderChestSlotMenu extends Menu {
        private final EnderChestEditor enderChestEditor;
        private final int userId;
        private final int slot;

        public EnderChestSlotMenu(Menu parent, ConfigFile configFile, EnderChestEditor enderChestEditor, int userId, int slot) {
            super(parent);
            this.userId = userId;
            this.slot = slot;
            this.enderChestEditor = enderChestEditor;
            setSize(configFile.getInt(Configs.ENDER_CHEST_SIZE));
            setTitle(configFile.getString(Configs.ENDER_CHEST_SLOT_TITLE).replace("[SLOT]", String.valueOf(slot)));
            setBackButton(getSize() - 1, parent);
        }

        @Override
        protected void handlePostDisplay(InventoryDrawer drawer) {
            ItemStack[] contents = enderChestEditor.getContents(userId, slot);
            for (int i = 0; i < Math.min(contents.length, getSize() - 9); i++)
                getInventory().setItem(i, contents[i]);
        }

        @Override
        protected void handleMenuClose(Player player, Inventory inventory) {
            enderChestEditor.createOrUpdate(userId, slot, inventory, true);
        }

        @Override
        protected boolean isActionAllowed(MenuClickLocation location, int slot, ItemStack clicked, ItemStack cursor) {
            return slot < getSize() - 9;
        }
    }
}
