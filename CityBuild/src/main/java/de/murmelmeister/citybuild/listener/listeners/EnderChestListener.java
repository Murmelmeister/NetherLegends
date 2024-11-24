package de.murmelmeister.citybuild.listener.listeners;

import de.murmelmeister.citybuild.CityBuild;
import de.murmelmeister.citybuild.listener.ListenerManager;
import de.murmelmeister.citybuild.util.config.Messages;
import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.Objects;

public class EnderChestListener extends ListenerManager {
    private final MiniMessage miniMessage = MiniMessage.miniMessage();

    public EnderChestListener(CityBuild plugin) {
        super(plugin);
    }

    @EventHandler
    public void handleEnderChestClick(InventoryClickEvent event) {
        Player player = (Player) event.getWhoClicked();
        ItemStack currentItem = event.getCurrentItem();

        if (currentItem == null) return;
        if (!(currentItem.hasItemMeta())) return;
        if (Objects.equals(currentItem.getItemMeta().displayName(), miniMessage.deserialize(message.getString(Messages.ENDER_CHEST_MATERIAL_LOCKED)))) {
            event.setCancelled(true);
            player.closeInventory();
        }
        if (event.getView().title().equals(miniMessage.deserialize(message.getString(Messages.ENDER_CHEST_MENU_USE)))) {
            event.setCancelled(true);
            for (int i = 1; i < 10; i++)
                if (Objects.equals(currentItem.getItemMeta().displayName(), miniMessage.deserialize(message.getString(Messages.ENDER_CHEST_MATERIAL_UNLOCKED).replace("[SLOT]", "" + i)))) {
                    int size = enderChest.getInventorySize(player.getUniqueId(), i) == null ? 6 * 9 : Integer.parseInt(enderChest.getInventorySize(player.getUniqueId(), i));
                    Inventory inventory = enderChest.loadInventory(player, i, "&dEnderChest - " + i, size); // TODO: Add config
                    enderChest.setInventory(inventory);
                    player.openInventory(enderChest.getInventory());
                }
        }

        Player target = enderChest.getTarget();
        if (target == null) {
            OfflinePlayer offlineTarget = enderChest.getOfflineTarget();
            if (offlineTarget == null) return;
            if (offlineTarget.isOnline() || offlineTarget.hasPlayedBefore()) {
                if (event.getView().title().equals(MiniMessage.miniMessage().deserialize(message.getString(Messages.ENDER_CHEST_MENU_OTHER).replace("[PLAYER]", offlineTarget.getName())))) {
                    event.setCancelled(true);
                    for (int i = 1; i < 10; i++)
                        if (Objects.equals(currentItem.getItemMeta().displayName(), miniMessage.deserialize(message.getString(Messages.ENDER_CHEST_MATERIAL_UNLOCKED).replace("[SLOT]", "" + i)))) {
                            int size = enderChest.getInventorySize(offlineTarget.getUniqueId(), i) == null ? 6 * 9 : Integer.parseInt(enderChest.getInventorySize(offlineTarget.getUniqueId(), i));
                            Inventory inventory = enderChest.loadInventory(offlineTarget.getUniqueId(), player.getServer(), i, "&dEnderChest - " + i + " - " + offlineTarget.getName(), size); // TODO: Add config
                            enderChest.setInventory(inventory);
                            player.openInventory(enderChest.getInventory());
                            event.setCancelled(true);
                        }
                }
            } else return;
            return;
        }

        if (event.getView().title().equals(miniMessage.deserialize(message.getString(Messages.ENDER_CHEST_MENU_OTHER).replace("[PLAYER]", target.getName())))) {
            event.setCancelled(true);
            for (int i = 1; i < 10; i++)
                if (Objects.equals(currentItem.getItemMeta().displayName(), miniMessage.deserialize(message.getString(Messages.ENDER_CHEST_MATERIAL_UNLOCKED).replace("[SLOT]", "" + i)))) {
                    int size = enderChest.getInventorySize(target.getUniqueId(), i) == null ? 6 * 9 : Integer.parseInt(enderChest.getInventorySize(target.getUniqueId(), i));
                    Inventory inventory = enderChest.loadInventory(target, i, "&dEnderChest - " + i + " - " + target.getName(), size); // TODO: Add config
                    enderChest.setInventory(inventory);
                    player.openInventory(enderChest.getInventory());
                    event.setCancelled(true);
                }
        }
    }

    @EventHandler
    public void handleEnderChestClose(InventoryCloseEvent event) {
        for (int i = 1; i < 10; i++) {
            String title = "&dEnderChest - " + i;
            if (event.getView().title().equals(miniMessage.deserialize(title)))
                enderChest.saveInventory(event.getPlayer().getUniqueId(), enderChest.getInventory(), i, title);

            Player target = enderChest.getTarget();
            if (target == null) {
                OfflinePlayer offlinePlayer = enderChest.getOfflineTarget();
                if (offlinePlayer == null) return;
                if (offlinePlayer.isOnline() || offlinePlayer.hasPlayedBefore()) {
                    if (event.getView().title().equals(miniMessage.deserialize(title + " - " + offlinePlayer.getName())))
                        enderChest.saveInventory(offlinePlayer.getUniqueId(), enderChest.getInventory(), i, title);
                } else return;
                return;
            }
            if (event.getView().title().equals(miniMessage.deserialize(title + " - " + target.getName())))
                enderChest.saveInventory(target.getUniqueId(), enderChest.getInventory(), i, title);
        }
    }
}
