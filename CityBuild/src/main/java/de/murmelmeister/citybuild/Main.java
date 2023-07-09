package de.murmelmeister.citybuild;

import de.murmelmeister.citybuild.api.*;
import de.murmelmeister.citybuild.command.Commands;
import de.murmelmeister.citybuild.configs.Config;
import de.murmelmeister.citybuild.configs.Message;
import de.murmelmeister.citybuild.listener.Listeners;
import de.murmelmeister.citybuild.util.ListUtil;

public class Main {
    private final CityBuild instance;
    private final ListUtil listUtil;

    private final Config config;
    private final Message message;
    private final SchedulerTask schedulerTask;
    private final Cooldown cooldown;
    private final Locations locations;
    private final Homes homes;
    private final Ranks ranks;
    private final Economy economy;
    private final ItemValue itemValue;

    private final Listeners listeners;
    private final Commands commands;

    public Main(CityBuild instance) {
        this.instance = instance;
        this.listUtil = new ListUtil();
        this.config = new Config(this);
        this.message = new Message(this);
        this.schedulerTask = new SchedulerTask(this);
        this.cooldown = new Cooldown(this);
        this.locations = new Locations(this);
        this.homes = new Homes(this);
        this.ranks = new Ranks(this);
        this.economy = new Economy(this);
        this.itemValue = new ItemValue(this);
        this.listeners = new Listeners(this);
        this.commands = new Commands(this);
    }

    public void disable() {
        instance.getServer().getMessenger().unregisterOutgoingPluginChannel(instance);
    }

    public void enable() {
        config.register();
        message.register();
        locations.create();
        ranks.register();
        itemValue.register();

        listeners.register();
        commands.register();
        instance.getServer().getMessenger().registerOutgoingPluginChannel(instance, "BungeeCord");
    }

    public CityBuild getInstance() {
        return instance;
    }

    public ListUtil getListUtil() {
        return listUtil;
    }

    public Config getConfig() {
        return config;
    }

    public Message getMessage() {
        return message;
    }

    public SchedulerTask getSchedulerTask() {
        return schedulerTask;
    }

    public Cooldown getCooldown() {
        return cooldown;
    }

    public Locations getLocations() {
        return locations;
    }

    public Homes getHomes() {
        return homes;
    }

    public Ranks getRanks() {
        return ranks;
    }

    public Economy getEconomy() {
        return economy;
    }

    public ItemValue getItemValue() {
        return itemValue;
    }
}
