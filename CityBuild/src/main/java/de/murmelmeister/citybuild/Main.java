package de.murmelmeister.citybuild;

import de.murmelmeister.citybuild.api.*;
import de.murmelmeister.citybuild.command.Commands;
import de.murmelmeister.citybuild.configs.Config;
import de.murmelmeister.citybuild.configs.Message;
import de.murmelmeister.citybuild.listener.Listeners;
import de.murmelmeister.citybuild.util.ListUtil;
import de.murmelmeister.citybuild.util.TablistUtil;
import de.murmelmeister.citybuild.util.scoreboard.TestScoreboard;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitTask;
import org.slf4j.Logger;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class Main {
    private final CityBuild instance;
    private final Logger logger;
    private final ListUtil listUtil;

    private final Config config;
    private final Message message;
    private final Cooldown cooldown;
    private final Locations locations;
    private final Homes homes;
    private final Economy economy;
    private final ItemValue itemValue;
    private final EnderChest enderChest;

    private final Listeners listeners;
    private final Commands commands;

    private BukkitTask scoreboardTask;
    private BukkitTask tablistTask;
    private final Map<Player, TestScoreboard> playerTestScoreboard = new ConcurrentHashMap<>();
    private final Map<Player, TablistUtil> playerTablistUtil = new ConcurrentHashMap<>();

    public Main(CityBuild instance) {
        this.instance = instance;
        this.logger = instance.getSLF4JLogger();
        this.listUtil = new ListUtil();
        this.config = new Config(this);
        this.message = new Message(this);
        this.cooldown = new Cooldown(this);
        this.locations = new Locations(this);
        this.homes = new Homes(this);
        this.economy = new Economy(this);
        this.itemValue = new ItemValue(this);
        this.enderChest = new EnderChest(this);
        this.listeners = new Listeners(this);
        this.commands = new Commands(this);
    }

    public void disable() {
        instance.getServer().getMessenger().unregisterOutgoingPluginChannel(instance);

        if (scoreboardTask != null && !scoreboardTask.isCancelled()) scoreboardTask.cancel();
        if (tablistTask != null && !tablistTask.isCancelled()) tablistTask.cancel();
        playerTestScoreboard.clear();
        playerTablistUtil.clear();
    }

    public void enable() {
        config.register();
        message.register();
        locations.create();
        itemValue.register();

        listeners.register();
        commands.register();
        instance.getServer().getMessenger().registerOutgoingPluginChannel(instance, "BungeeCord");
        scoreboardTask = instance.getServer().getScheduler().runTaskTimer(instance, () -> {
            for (Player player : instance.getServer().getOnlinePlayers())
                playerTestScoreboard.computeIfAbsent(player, user -> new TestScoreboard(user, this)).run();
        }, 0L, 1L);
        tablistTask = instance.getServer().getScheduler().runTaskTimerAsynchronously(instance, () -> {
            for (Player player : instance.getServer().getOnlinePlayers())
                playerTablistUtil.computeIfAbsent(player, user -> new TablistUtil(player, this)).setScoreboardTabList();
        }, 0L, 1L);
    }

    public CityBuild getInstance() {
        return instance;
    }

    public Logger getLogger() {
        return logger;
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

    public Cooldown getCooldown() {
        return cooldown;
    }

    public Locations getLocations() {
        return locations;
    }

    public Homes getHomes() {
        return homes;
    }

    public Economy getEconomy() {
        return economy;
    }

    public ItemValue getItemValue() {
        return itemValue;
    }

    public EnderChest getEnderChest() {
        return enderChest;
    }
}
