package de.murmelmeister.citybuild.listener.listeners;

import de.murmelmeister.citybuild.CityBuild;
import de.murmelmeister.citybuild.listener.ListenerManager;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.inventory.ItemStack;
import org.slf4j.Logger;

public class LoggingListener extends ListenerManager {
    private final Logger logger;

    public LoggingListener(CityBuild plugin) {
        super(plugin);
        this.logger = plugin.getSLF4JLogger();
    }

    @EventHandler
    public void handlePlayerDropItem(PlayerDropItemEvent event) {
        Player player = event.getPlayer();
        Item item = event.getItemDrop();
        ItemStack itemStack = item.getItemStack();
        logger.info("{} dropped {} * {} at the location '{}'",
                player.getName(),
                itemStack.getAmount(),
                itemStack.getType(),
                item.getLocation());
    }

    @EventHandler
    public void handleChestInteract(InventoryClickEvent event) {
        if (event.getInventory().getType() == InventoryType.CHEST) {
            if (event.getClick() == ClickType.LEFT || event.getClick() == ClickType.RIGHT) {
                ItemStack currentItem = event.getCurrentItem();
                if (currentItem != null && currentItem.getType() != Material.AIR) {
                    logger.info("{} took {} * {} from chest at the location '{}'",
                            event.getWhoClicked().getName(),
                            currentItem.getAmount(),
                            currentItem.getType(),
                            event.getInventory().getLocation());
                }
            }
        }
    }

    @EventHandler
    public void handleCreativeInventoryClick(InventoryClickEvent event) {
        if (event.getWhoClicked().getGameMode() == GameMode.CREATIVE) {
            if (event.getClick() == ClickType.CREATIVE) {
                ItemStack currentItem = event.getCurrentItem();
                if (currentItem != null && currentItem.getType() != Material.AIR) {
                    logger.info("{} took {} * {} from Creative Inventory",
                            event.getWhoClicked().getName(),
                            currentItem.getAmount(),
                            currentItem.getType());
                }
            }
        }
    }
}
