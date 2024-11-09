package de.murmelmeister.citybuild.listener;

import de.murmelmeister.citybuild.CityBuild;
import de.murmelmeister.citybuild.Main;
import de.murmelmeister.citybuild.api.*;
import de.murmelmeister.citybuild.configs.Config;
import de.murmelmeister.citybuild.configs.Message;
import de.murmelmeister.citybuild.listener.listeners.*;
import de.murmelmeister.citybuild.util.HexColor;
import de.murmelmeister.citybuild.util.ListUtil;
import de.murmelmeister.citybuild.util.config.Configs;
import de.murmelmeister.murmelapi.user.User;
import org.bukkit.command.CommandSender;
import org.bukkit.event.Listener;

public class Listeners implements Listener {
    protected final Main main;
    protected final CityBuild instance;
    protected final ListUtil listUtil;

    protected final User user;

    protected final Config config;
    protected final Message message;
    protected final Cooldown cooldown;
    protected final Locations locations;
    protected final Homes homes;
    protected final Economy economy;
    protected final ItemValue itemValue;
    protected final EnderChest enderChest;
    protected final PlayerInventory playerInventory;

    public Listeners(Main main) {
        this.main = main;
        this.instance = main.getInstance();
        this.listUtil = main.getListUtil();
        this.user = main.getUser();
        this.config = main.getConfig();
        this.message = main.getMessage();
        this.cooldown = main.getCooldown();
        this.locations = main.getLocations();
        this.homes = main.getHomes();
        this.economy = main.getEconomy();
        this.itemValue = main.getItemValue();
        this.enderChest = main.getEnderChest();
        this.playerInventory = main.getPlayerInventory();
    }

    public void register() {
        addListener(new GodModeListener(main));
        addListener(new ConnectListener(main));
        addListener(new EnderChestListener(main));
        addListener(new RespawnListener(main));
        addListener(new LoggingListener(main));
        //addListener(new ItemValueListener(main));
        //addListener(new TestItem(main));
    }

    private void addListener(Listener listener) {
        instance.getServer().getPluginManager().registerEvents(listener, instance);
    }

    public void sendMessage(CommandSender sender, String message) {
        if (config.getBoolean(Configs.PREFIX_ENABLE))
            sender.sendMessage(HexColor.format(this.message.prefix() + message));
        else sender.sendMessage(HexColor.format(message));
    }
}
