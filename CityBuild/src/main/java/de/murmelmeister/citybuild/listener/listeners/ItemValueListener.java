package de.murmelmeister.citybuild.listener.listeners;

import de.murmelmeister.citybuild.CityBuild;
import de.murmelmeister.citybuild.listener.ListenerManager;
import org.bukkit.Material;
import org.bukkit.entity.Item;
import org.bukkit.event.EventHandler;
import org.bukkit.event.block.BlockDropItemEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.inventory.ItemStack;

public class ItemValueListener extends ListenerManager {
    public ItemValueListener(CityBuild plugin) {
        super(plugin);
    }

    @EventHandler
    public void testBlock(BlockDropItemEvent event) {
        for (Item item : event.getItems()) {
            Material material = item.getItemStack().getType();
            //item.setItemStack(itemValue.createItem(material));
        }
    }

    @EventHandler
    public void testDeadEntity(EntityDeathEvent event) {
        for (ItemStack itemStack : event.getDrops()) {
            //itemStack.setItemMeta(itemValue.createItemMeta(itemStack));
        }
    }
}
