package de.murmelmeister.lobby;

import de.murmelmeister.lobby.api.Locations;
import de.murmelmeister.lobby.command.Commands;
import de.murmelmeister.lobby.configs.Config;
import de.murmelmeister.lobby.configs.Message;
import de.murmelmeister.lobby.listener.Listeners;
import de.murmelmeister.lobby.util.ListUtil;
import de.murmelmeister.lobby.util.TablistUtil;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitTask;
import org.slf4j.Logger;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class Main {
    private final Lobby instance;
    private final Logger logger;
    private final ListUtil listUtil;

    private final Config config;
    private final Message message;
    private final Locations locations;

    private final Listeners listeners;
    private final Commands commands;

    private BukkitTask tablistTask;
    private final Map<Player, TablistUtil> playerTablistUtil = new ConcurrentHashMap<>();

    public Main(Lobby instance) {
        this.instance = instance;
        this.logger = instance.getSLF4JLogger();
        this.listUtil = new ListUtil();
        this.config = new Config(this);
        this.message = new Message(this);
        this.locations = new Locations(this);
        this.listeners = new Listeners(this);
        this.commands = new Commands(this);
    }

    public void disable() {
        instance.getServer().getMessenger().unregisterOutgoingPluginChannel(instance);
        if (tablistTask != null && !tablistTask.isCancelled()) tablistTask.cancel();
        playerTablistUtil.clear();
    }

    public void enable() {
        config.register();
        message.register();
        locations.create();

        listeners.register();
        commands.register();
        instance.getServer().getMessenger().registerOutgoingPluginChannel(instance, "BungeeCord");
        tablistTask = instance.getServer().getScheduler().runTaskTimerAsynchronously(instance, () -> {
            for (Player player : instance.getServer().getOnlinePlayers())
                playerTablistUtil.computeIfAbsent(player, user -> new TablistUtil(player, this)).setScoreboardTabList();
        }, 0L, 1L);
    }

    public Lobby getInstance() {
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

    public Locations getLocations() {
        return locations;
    }
}
