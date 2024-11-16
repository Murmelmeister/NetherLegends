package de.murmelmeister.citybuild.util.config;

import org.bukkit.Material;

public enum Configs {
    PREFIX_ENABLE("Prefix.Enable", true),
    CURRENT_SERVER("CurrentServer", "CityBuild"),
    DATE_FORMAT_PATTERN("Date.Format.Pattern", "yyyy-MM-dd HH:mm:ss"),
    DATE_FORMAT_LOCALE("Date.Format.Locale", "GERMAN"),
    PATTERN_DECIMAL("Pattern.Decimal", "###,###,###.##"),
    PATTERN_COMMAND_SIGN("Pattern.Command.Sign", "yyyy-MM-dd HH:mm"),
    PERMISSION_ENDER_CHEST_COMMAND("Permission.EnderChest.Command", "citybuild.command.enderchest.command"),
    PERMISSION_ENDER_CHEST_USE("Permission.EnderChest.Use", "citybuild.command.enderchest.use"),
    PERMISSION_ENDER_CHEST_OTHER("Permission.EnderChest.Other", "citybuild.command.enderchest.other"),
    PERMISSION_ENDER_CHEST_SLOTS("Permission.EnderChest.Slots", "citybuild.command.enderchest.slot"),
    PERMISSION_RELOAD("Permission.Reload", "citybuild.command.reload"),
    PERMISSION_SPAWN("Permission.Spawn", "citybuild.command.spawn"),
    PERMISSION_SET_SPAWN("Permission.SetSpawn", "citybuild.command.setspawn"),
    PERMISSION_LOCATION("Permission.Warp", "citybuild.command.warp"),
    PERMISSION_SET_LOCATION("Permission.SetWarp", "citybuild.command.setwarp"),
    PERMISSION_REMOVE_LOCATION("Permission.RemoveWarp", "citybuild.command.removewarp"),
    PERMISSION_HOME("Permission.Home", "citybuild.command.home"),
    PERMISSION_ADD_HOME("Permission.AddHome", "citybuild.command.addhome"),
    PERMISSION_REMOVE_HOME("Permission.RemoveHome", "citybuild.command.removehome"),
    PERMISSION_HOME_UNLIMITED("Permission.Homes.Unlimited", "citybuild.home.unlimited"),
    PERMISSION_HOME_LIMIT_TEAM("Permission.Homes.Limit.Team", "citybuild.home.limit.team"),
    PERMISSION_HOME_LIMIT_RANK("Permission.Homes.Limit.Rank", "citybuild.home.limit.rank"),
    PERMISSION_WORKBENCH("Permission.Workbench", "citybuild.command.workbench"),
    PERMISSION_ANVIL("Permission.Anvil", "citybuild.command.anvil"),
    PERMISSION_TRASH("Permission.Trash", "citybuild.command.trash"),
    PERMISSION_FEED_COMMAND("Permission.Feed.Command", "citybuild.command.feed.command"),
    PERMISSION_FEED_USE("Permission.Feed.Use", "citybuild.command.feed.use"),
    PERMISSION_FEED_OTHER("Permission.Feed.Other", "citybuild.command.feed.other"),
    PERMISSION_HEAL_COMMAND("Permission.Heal.Command", "citybuild.command.heal.command"),
    PERMISSION_HEAL_USE("Permission.Heal.Use", "citybuild.command.heal.use"),
    PERMISSION_HEAL_OTHER("Permission.Heal.Other", "citybuild.command.heal.other"),
    PERMISSION_FLY_COMMAND("Permission.Fly.Command", "citybuild.command.fly.command"),
    PERMISSION_FLY_USE("Permission.Fly.Use", "citybuild.command.fly.use"),
    PERMISSION_FLY_OTHER("Permission.Fly.Other", "citybuild.command.fly.other"),
    PERMISSION_GOD_MODE_COMMAND("Permission.GodMode.Command", "citybuild.command.godmode.command"),
    PERMISSION_GOD_MODE_USE("Permission.GodMode.Use", "citybuild.command.godmode.use"),
    PERMISSION_GOD_MODE_OTHER("Permission.GodMode.Other", "citybuild.command.godmode.other"),
    PERMISSION_JOIN_FLY("Permission.Fly.Join", "citybuild.event.join.fly"),
    PERMISSION_JOIN_GOD_MODE("Permission.GodMode.Join", "citybuild.event.join.godmode"),
    PERMISSION_LOBBY("Permission.Lobby", "citybuild.command.lobby"),
    PERMISSION_TIME_DAY("Permission.Time.Day", "citybuild.command.day"),
    PERMISSION_TIME_NIGHT("Permission.Time.Night", "citybuild.command.night"),
    PERMISSION_HEAD("Permission.Head", "citybuild.command.head"),
    PERMISSION_SIGN("Permission.Sign", "citybuild.command.sign"),
    PERMISSION_UN_SIGN("Permission.UnSign", "citybuild.command.unsign"),
    PERMISSION_REPAIR("Permission.Repair", "citybuild.command.repair"),
    PERMISSION_RENAME("Permission.Rename", "citybuild.command.rename"),
    PERMISSION_LIVE("Permission.Live", "citybuild.command.live"),
    PERMISSION_NOT_COOLDOWN("Permission.NotCooldown", "citybuild.admin.notcooldown"),
    PERMISSION_TP("Permission.Tp", "citybuild.command.tp"),
    PERMISSION_TPA("Permission.Tpa", "citybuild.command.tpa"),
    PERMISSION_TPA_HERE("Permission.TpaHere", "citybuild.command.tpahere"),
    PERMISSION_TPA_ACCEPT("Permission.TpaAccept", "citybuild.command.tpaaccept"),
    PERMISSION_TPA_DENY("Permission.TpaDeny", "citybuild.command.tpadeny"),
    PERMISSION_MONEY_COMMAND("Permission.Money.Command", "citybuild.command.money.command"),
    PERMISSION_MONEY_USE("Permission.Money.Use", "citybuild.command.money.use"),
    PERMISSION_MONEY_OTHER("Permission.Money.Other", "citybuild.command.money.other"),
    PERMISSION_ECONOMY_COMMAND("Permission.Economy.Command", "citybuild.command.economy.command"),
    PERMISSION_ECONOMY_SET("Permission.Economy.Set", "citybuild.command.economy.set"),
    PERMISSION_ECONOMY_ADD("Permission.Economy.Add", "citybuild.command.economy.add"),
    PERMISSION_ECONOMY_REMOVE("Permission.Economy.Remove", "citybuild.command.economy.remove"),
    PERMISSION_ECONOMY_RESET("Permission.Economy.Reset", "citybuild.command.economy.reset"),
    PERMISSION_PAY_USE("Permission.Pay.Use", "citybuild.command.pay.use"),
    PERMISSION_PAY_ALL_PLAYERS("Permission.Pay.All.Players", "citybuild.command.pay.all.players"),
    PERMISSION_PAY_ALL_MONEY("Permission.Pay.All.Money", "citybuild.command.pay.all.money"),
    PERMISSION_PAY_NEGATIVE("Permission.Pay.Negative", "citybuild.command.pay.negative"),
    PERMISSION_GAME_MODE_COMMAND("Permission.GameMode.Command", "citybuild.command.gamemode.command"),
    PERMISSION_GAME_MODE_USE_SURVIVAL("Permission.GameMode.Use.Survival", "citybuild.command.gamemode.use.survival"),
    PERMISSION_GAME_MODE_USE_CREATIVE("Permission.GameMode.Use.Creative", "citybuild.command.gamemode.use.creative"),
    PERMISSION_GAME_MODE_USE_ADVENTURE("Permission.GameMode.Use.Adventure", "citybuild.command.gamemode.use.adventure"),
    PERMISSION_GAME_MODE_USE_SPECTATOR("Permission.GameMode.Use.Spectator", "citybuild.command.gamemode.use.spectator"),
    PERMISSION_GAME_MODE_OTHER_SURVIVAL("Permission.GameMode.Other.Survival", "citybuild.command.gamemode.other.survival"),
    PERMISSION_GAME_MODE_OTHER_CREATIVE("Permission.GameMode.Other.Creative", "citybuild.command.gamemode.other.creative"),
    PERMISSION_GAME_MODE_OTHER_ADVENTURE("Permission.GameMode.Other.Adventure", "citybuild.command.gamemode.other.adventure"),
    PERMISSION_GAME_MODE_OTHER_SPECTATOR("Permission.GameMode.Other.Spectator", "citybuild.command.gamemode.other.spectator"),
    PERMISSION_SELL("Permission.Sell", "citybuild.command.sell"),
    PERMISSION_ITEM_VALUE_COMMAND("Permission.ItemValue.Command", "citybuild.command.itemvalue.command"),
    PERMISSION_ITEM_VALUE_GET("Permission.ItemValue.Get", "citybuild.command.itemvalue.get"),
    PERMISSION_ITEM_VALUE_SET("Permission.ItemValue.Set", "citybuild.command.itemvalue.set"),
    PERMISSION_INVSEE("Permission.InvSee", "citybuild.command.invsee"),
    PERMISSION_SHOP_MENU("Permission.Shop.Menu", "citybuild.command.shop.menu"),
    PERMISSION_SHOP_CATEGORY("Permission.Shop.Category", "citybuild.command.shop.category"),
    PERMISSION_SHOP_ITEM("Permission.Shop.Item", "citybuild.command.shop.item"),
    COMMAND_ENABLE_ENDER_CHEST_COMMAND("Command.Enable.EnderChest.Command", true),
    COMMAND_ENABLE_ENDER_CHEST_USE("Command.Enable.EnderChest.Use", true),
    COMMAND_ENABLE_ENDER_CHEST_OTHER("Command.Enable.EnderChest.Other", true),
    COMMAND_ENABLE_RELOAD("Command.Enable.Reload", true),
    COMMAND_ENABLE_SPAWN("Command.Enable.Spawn", true),
    COMMAND_ENABLE_SET_SPAWN("Command.Enable.SetSpawn", true),
    COMMAND_ENABLE_LOCATION("Command.Enable.Warp", true),
    COMMAND_ENABLE_SET_LOCATION("Command.Enable.SetWarp", true),
    COMMAND_ENABLE_REMOVE_LOCATION("Command.Enable.RemoveWarp", true),
    COMMAND_ENABLE_HOME("Command.Enable.Home", true),
    COMMAND_ENABLE_ADD_HOME("Command.Enable.AddHome", true),
    COMMAND_ENABLE_REMOVE_HOME("Command.Enable.RemoveHome", true),
    COMMAND_ENABLE_WORKBENCH("Command.Enable.Workbench", true),
    COMMAND_ENABLE_ANVIL("Command.Enable.Anvil", true),
    COMMAND_ENABLE_TRASH("Command.Enable.Trash", true),
    COMMAND_ENABLE_FEED_COMMAND("Command.Enable.Feed.Command", true),
    COMMAND_ENABLE_FEED_USE("Command.Enable.Feed.Use", true),
    COMMAND_ENABLE_FEED_OTHER("Command.Enable.Feed.Other", true),
    COMMAND_ENABLE_HEAL_COMMAND("Command.Enable.Heal.Command", true),
    COMMAND_ENABLE_HEAL_USE("Command.Enable.Heal.Use", true),
    COMMAND_ENABLE_HEAL_OTHER("Command.Enable.Heal.Other", true),
    COMMAND_ENABLE_FLY_COMMAND("Command.Enable.Fly.Command", true),
    COMMAND_ENABLE_FLY_USE("Command.Enable.Fly.Use", true),
    COMMAND_ENABLE_FLY_OTHER("Command.Enable.Fly.Other", true),
    COMMAND_ENABLE_GOD_MODE_COMMAND("Command.Enable.GodMode.Command", true),
    COMMAND_ENABLE_GOD_MODE_USE("Command.Enable.GodMode.Use", true),
    COMMAND_ENABLE_GOD_MODE_OTHER("Command.Enable.GodMode.Other", true),
    COMMAND_ENABLE_LOBBY("Command.Enable.Lobby", true),
    COMMAND_ENABLE_TIME_DAY("Command.Enable.Time.Day", true),
    COMMAND_ENABLE_TIME_NIGHT("Command.Enable.Time.Night", true),
    COMMAND_ENABLE_HEAD("Command.Enable.Head", true),
    COMMAND_ENABLE_SIGN("Command.Enable.Sign", true),
    COMMAND_ENABLE_UN_SIGN("Command.Enable.UnSign", true),
    COMMAND_ENABLE_REPAIR("Command.Enable.Repair", true),
    COMMAND_ENABLE_RENAME("Command.Enable.Rename", true),
    COMMAND_ENABLE_LIVE("Command.Enable.Live", false),
    COMMAND_ENABLE_TP("Command.Enable.Tp", true),
    COMMAND_ENABLE_TPA("Command.Enable.Tpa", true),
    COMMAND_ENABLE_TPA_HERE("Command.Enable.TpaHere", true),
    COMMAND_ENABLE_TPA_ACCEPT("Command.Enable.TpaAccept", true),
    COMMAND_ENABLE_TPA_DENY("Command.Enable.TpaDeny", true),
    COMMAND_ENABLE_MONEY_COMMAND("Command.Enable.Money.Command", true),
    COMMAND_ENABLE_MONEY_USE("Command.Enable.Money.Use", true),
    COMMAND_ENABLE_MONEY_OTHER("Command.Enable.Money.Other", true),
    COMMAND_ENABLE_ECONOMY_COMMAND("Command.Enable.Economy.Command", true),
    COMMAND_ENABLE_ECONOMY_SET("Command.Enable.Economy.Set", true),
    COMMAND_ENABLE_ECONOMY_ADD("Command.Enable.Economy.Add", true),
    COMMAND_ENABLE_ECONOMY_REMOVE("Command.Enable.Economy.Remove", true),
    COMMAND_ENABLE_ECONOMY_RESET("Command.Enable.Economy.Reset", true),
    COMMAND_ENABLE_PAY("Command.Enable.Pay", true),
    COMMAND_ENABLE_GAME_MODE_COMMAND("Command.Enable.GameMode.Command", true),
    COMMAND_ENABLE_GAME_MODE_USE_SURVIVAL("Command.Enable.GameMode.Use.Survival", true),
    COMMAND_ENABLE_GAME_MODE_USE_CREATIVE("Command.Enable.GameMode.Use.Creative", true),
    COMMAND_ENABLE_GAME_MODE_USE_ADVENTURE("Command.Enable.GameMode.Use.Adventure", true),
    COMMAND_ENABLE_GAME_MODE_USE_SPECTATOR("Command.Enable.GameMode.Use.Spectator", true),
    COMMAND_ENABLE_GAME_MODE_OTHER_SURVIVAL("Command.Enable.GameMode.Other.Survival", true),
    COMMAND_ENABLE_GAME_MODE_OTHER_CREATIVE("Command.Enable.GameMode.Other.Creative", true),
    COMMAND_ENABLE_GAME_MODE_OTHER_ADVENTURE("Command.Enable.GameMode.Other.Adventure", true),
    COMMAND_ENABLE_GAME_MODE_OTHER_SPECTATOR("Command.Enable.GameMode.Other.Spectator", true),
    COMMAND_ENABLE_SELL("Command.Enable.Sell", true),
    COMMAND_ENABLE_ITEM_VALUE_COMMAND("Command.Enable.ItemValue.Command", true),
    COMMAND_ENABLE_ITEM_VALUE_GET("Command.Enable.ItemValue.Get", true),
    COMMAND_ENABLE_ITEM_VALUE_SET("Command.Enable.ItemValue.Set", true),
    COMMAND_ENABLE_INVSEE("Command.Enable.InvSee", true),
    COMMAND_ENABLE_SHOP_MENU("Command.Enable.Shop.Menu", true),
    COMMAND_ENABLE_SHOP_CATEGORY("Command.Enable.Shop.Category", true),
    COMMAND_ENABLE_SHOP_ITEM("Command.Enable.Shop.Item", true),
    EVENT_ENABLE_PLAYER_JOIN("Event.Enable.PlayerJoin", true),
    EVENT_ENABLE_PLAYER_QUIT("Event.Enable.PlayerQuit", true),
    EVENT_ENABLE_TELEPORT_TO_SPAWN("Event.Enable.TeleportToSpawn", true),
    EVENT_ENABLE_JOIN_MESSAGE("Event.Enable.Join.Message", true),
    EVENT_ENABLE_JOIN_TITLE("Event.Enable.Join.Title", true),
    HOME_LIMIT_DEFAULT("Home.Limit.Default", 4),
    HOME_LIMIT_RANK("Home.Limit.Rank", 8),
    HOME_LIMIT_TEAM("Home.Limit.Team", 12),
    SCOREBOARD_ENABLE_SCORE_15("Scoreboard.Enable.Score.15", false),
    SCOREBOARD_ENABLE_SCORE_14("Scoreboard.Enable.Score.14", false),
    SCOREBOARD_ENABLE_SCORE_13("Scoreboard.Enable.Score.13", false),
    SCOREBOARD_ENABLE_SCORE_12("Scoreboard.Enable.Score.12", false),
    SCOREBOARD_ENABLE_SCORE_11("Scoreboard.Enable.Score.11", false),
    SCOREBOARD_ENABLE_SCORE_10("Scoreboard.Enable.Score.10", true),
    SCOREBOARD_ENABLE_SCORE_9("Scoreboard.Enable.Score.9", true),
    SCOREBOARD_ENABLE_SCORE_8("Scoreboard.Enable.Score.8", true),
    SCOREBOARD_ENABLE_SCORE_7("Scoreboard.Enable.Score.7", true),
    SCOREBOARD_ENABLE_SCORE_6("Scoreboard.Enable.Score.6", true),
    SCOREBOARD_ENABLE_SCORE_5("Scoreboard.Enable.Score.5", true),
    SCOREBOARD_ENABLE_SCORE_4("Scoreboard.Enable.Score.4", true),
    SCOREBOARD_ENABLE_SCORE_3("Scoreboard.Enable.Score.3", true),
    SCOREBOARD_ENABLE_SCORE_2("Scoreboard.Enable.Score.2", true),
    SCOREBOARD_ENABLE_SCORE_1("Scoreboard.Enable.Score.1", true),
    SCOREBOARD_ENABLE_SCORE_0("Scoreboard.Enable.Score.0", false),
    LOBBY_TO_SEND_SERVER("LobbyToSendServer", "Lobby-1"),
    TIME_DAY_TIME("Time.Day.Time", 1000L),
    TIME_NIGHT_TIME("Time.Night.Time", 13000L),
    TIME_DAY_COOLDOWN("Time.Day.Cooldown", 60 * 60 * 1000),
    TIME_NIGHT_COOLDOWN("Time.Night.Cooldown", 60 * 60 * 1000),
    TIME_HEAD_COOLDOWN("Time.Head.Cooldown", 7 * 24 * 60 * 60 * 1000),
    TIME_SIGN_COOLDOWN("Time.Sign.Cooldown", 24 * 60 * 60 * 1000),
    TIME_UN_SIGN_COOLDOWN("Time.UnSign.Cooldown", 24 * 60 * 60 * 1000),
    TIME_REPAIR_COOLDOWN("Time.Repair.Cooldown", 4 * 24 * 60 * 60 * 1000),
    TIME_RENAME_COOLDOWN("Time.Rename.Cooldown", 24 * 60 * 60 * 1000),
    ECONOMY_CURRENCY("Economy.Currency", "Money"),
    ECONOMY_DEFAULT_MONEY("Economy.Default.Money", 0L),
    ECONOMY_DEFAULT_ITEM_SELL_PRICE("Economy.Default.ItemSellPrice", 0L),
    MATERIAL_CASE("Material.Case", false),
    ENDER_CHEST_MATERIAL_UNLOCKED("EnderChest.Material.Unlocked", Material.LIME_STAINED_GLASS_PANE.toString()),
    ENDER_CHEST_MATERIAL_LOCKED("EnderChest.Material.Locked", Material.RED_STAINED_GLASS_PANE.toString()),
    SHOP_CATEGORY_TITLE("Shop.Category.Title", "<#ccff88>Shop Menu"),
    SHOP_CATEGORY_PLACEHOLDER("Shop.Category.Placeholder", "BLACK_STAINED_GLASS_PANE"),
    SHOP_ITEM_TITLE("Shop.Item.Title", "<#ccff88>[CATEGORY] Items"),
    SHOP_ITEM_PLACEHOLDER("Shop.Item.Placeholder", "BLACK_STAINED_GLASS_PANE"),;
    public static final Configs[] VALUES = values();

    private final String path;
    private final Object value;

    Configs(String path, Object value) {
        this.path = path;
        this.value = value;
    }

    public String getPath() {
        return path;
    }

    public Object getValue() {
        return value;
    }
}
