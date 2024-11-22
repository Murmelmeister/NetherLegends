package de.murmelmeister.citybuild;

import de.murmelmeister.citybuild.api.*;
import de.murmelmeister.citybuild.api.shop.ShopCategory;
import de.murmelmeister.citybuild.api.shop.ShopItem;
import de.murmelmeister.citybuild.command.CommandManager;
import de.murmelmeister.citybuild.files.ConfigFile;
import de.murmelmeister.citybuild.files.MessageFile;
import de.murmelmeister.citybuild.files.MySQL;
import de.murmelmeister.citybuild.listener.ListenerManager;
import de.murmelmeister.citybuild.util.ListUtil;
import de.murmelmeister.citybuild.util.TablistUtil;
import de.murmelmeister.citybuild.util.scoreboard.TestScoreboard;
import de.murmelmeister.murmelapi.MurmelAPI;
import de.murmelmeister.murmelapi.MurmelPlugin;
import de.murmelmeister.murmelapi.user.User;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitTask;
import org.slf4j.Logger;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public final class CityBuild extends MurmelPlugin {
    private ListUtil listUtil;
    private MySQL mySQL;

    private ConfigFile config;
    private MessageFile message;
    private Cooldown cooldown;
    private Locations locations;
    private Homes homes;
    private Economy economy;
    private ItemValue itemValue;
    private EnderChest enderChest;
    private PlayerInventory playerInventory;
    private CustomItems customItems;
    private ShopCategory shopCategory;
    private ShopItem shopItem;

    private BukkitTask scoreboardTask;
    private BukkitTask tablistTask;
    private final Map<Player, TestScoreboard> playerTestScoreboard = new ConcurrentHashMap<>();
    private final Map<Player, TablistUtil> playerTablistUtil = new ConcurrentHashMap<>();

    @Override
    public void onLoad() {
        final Logger logger = getSLF4JLogger();
        this.config = new ConfigFile(logger);
        this.mySQL = new MySQL(logger, config);
        mySQL.connect();
        this.listUtil = new ListUtil();
        this.playerInventory = new PlayerInventory(logger);
        this.message = new MessageFile(logger);
        this.cooldown = new Cooldown(logger);
        this.locations = new Locations(logger, getServer());
        this.homes = new Homes();
        this.economy = new Economy();
        this.itemValue = new ItemValue(logger, config, economy, getUser());
        this.enderChest = new EnderChest(logger, config, message);
        this.customItems = new CustomItems();
        this.shopCategory = new ShopCategory();
        this.shopItem = new ShopItem(customItems);
    }

    @Override
    public void onDisable() {
        this.getServer().getMessenger().unregisterOutgoingPluginChannel(this);

        if (scoreboardTask != null && !scoreboardTask.isCancelled()) scoreboardTask.cancel();
        if (tablistTask != null && !tablistTask.isCancelled()) tablistTask.cancel();
        playerTestScoreboard.clear();
        playerTablistUtil.clear();
        mySQL.disconnect();
    }

    @Override
    public void onEnable() {
        super.onEnable();
        customItems.loadAllMaterials();
        ListenerManager.register(this);
        CommandManager.register(this);
        this.getServer().getMessenger().registerOutgoingPluginChannel(this, "BungeeCord");

        scoreboardTask = this.getServer().getScheduler().runTaskTimer(this, () -> {
            for (Player player : this.getServer().getOnlinePlayers())
                playerTestScoreboard.computeIfAbsent(player, user -> new TestScoreboard(user, this)).run();
        }, 0L, 2 * 20L);
        tablistTask = this.getServer().getScheduler().runTaskTimerAsynchronously(this, () -> {
            for (Player player : this.getServer().getOnlinePlayers())
                playerTablistUtil.computeIfAbsent(player, user -> new TablistUtil(player, this)).setScoreboardTabList();
        }, 0L, 2 * 20L);
    }

    public static CityBuild getInstance() {
        return getPlugin(CityBuild.class);
    }

    public static String getMainPath() {
        return "./plugins/" + CityBuild.class.getSimpleName();
    }

    public static NamespacedKey getKeyShopItem() {
        return new NamespacedKey(getInstance(), "shop_item");
    }

    public static NamespacedKey getKeyCustomItems() {
        return new NamespacedKey(getInstance(), "custom_items");
    }

    public ListUtil getListUtil() {
        return listUtil;
    }

    public ConfigFile getConfigFile() {
        return config;
    }

    public MessageFile getMessageFile() {
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

    public PlayerInventory getPlayerInventory() {
        return playerInventory;
    }

    public User getUser() {
        return MurmelAPI.getUser();
    }

    public CustomItems getCustomItems() {
        return customItems;
    }

    public ShopCategory getShopCategory() {
        return shopCategory;
    }

    public ShopItem getShopItem() {
        return shopItem;
    }
}
