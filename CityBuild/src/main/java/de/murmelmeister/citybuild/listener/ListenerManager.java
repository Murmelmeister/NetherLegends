package de.murmelmeister.citybuild.listener;

import de.murmelmeister.citybuild.CityBuild;
import de.murmelmeister.citybuild.api.*;
import de.murmelmeister.citybuild.files.ConfigFile;
import de.murmelmeister.citybuild.files.MessageFile;
import de.murmelmeister.citybuild.listener.listeners.*;
import de.murmelmeister.citybuild.util.ListUtil;
import de.murmelmeister.citybuild.util.config.Configs;
import de.murmelmeister.murmelapi.user.User;
import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bukkit.command.CommandSender;
import org.bukkit.event.Listener;
import org.bukkit.plugin.Plugin;

public class ListenerManager implements Listener {
    protected final CityBuild plugin;
    protected final ListUtil listUtil;

    protected final User user;

    protected final ConfigFile config;
    protected final MessageFile message;
    protected final Cooldown cooldown;
    protected final Locations locations;
    protected final Homes homes;
    protected final Economy economy;
    protected final ItemValue itemValue;
    protected final EnderChest enderChest;
    protected final PlayerInventory playerInventory;

    public ListenerManager(CityBuild plugin) {
        this.plugin = plugin;
        this.listUtil = plugin.getListUtil();
        this.user = plugin.getUser();
        this.config = plugin.getConfigFile();
        this.message = plugin.getMessageFile();
        this.cooldown = plugin.getCooldown();
        this.locations = plugin.getLocations();
        this.homes = plugin.getHomes();
        this.economy = plugin.getEconomy();
        this.itemValue = plugin.getItemValue();
        this.enderChest = plugin.getEnderChest();
        this.playerInventory = plugin.getPlayerInventory();
    }

    public static void register(CityBuild plugin) {
        addListener(plugin, new GodModeListener(plugin));
        addListener(plugin, new ConnectListener(plugin));
        addListener(plugin, new EnderChestListener(plugin));
        addListener(plugin, new RespawnListener(plugin));
        addListener(plugin, new LoggingListener(plugin));
        //addListener(new ItemValueListener(main));
        //addListener(new TestItem(main));
    }

    private static void addListener(Plugin plugin, Listener listener) {
        plugin.getServer().getPluginManager().registerEvents(listener, plugin);
    }

    public void sendMessage(CommandSender sender, String message) {
        if (config.getBoolean(Configs.PREFIX_ENABLE))
            sender.sendMessage(MiniMessage.miniMessage().deserialize(this.message.prefix() + message));
        else sender.sendMessage(MiniMessage.miniMessage().deserialize(message));
    }
}
