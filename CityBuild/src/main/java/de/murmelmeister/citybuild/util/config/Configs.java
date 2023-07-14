package de.murmelmeister.citybuild.util.config;

import org.bukkit.ChatColor;

public enum Configs {

    PREFIX_ENABLE("Prefix.Enable", true),
    FILE_NAME("FileName", "Necronic"),
    TIMEZONE_ENABLE("TimeZone.Enable", true),
    TIMEZONE_CONFIG_ENABLE("TimeZone.Config.Enable", false),
    TIMEZONE_ZONE("TimeZone.Zone", "Europe/Berlin"),
    CURRENT_SERVER("CurrentServer", "CityBuild"),
    PATTERN_CONFIG("Pattern.Config", "yyyy-MM-dd HH:mm:ss.SSS"),
    PATTERN_DECIMAL("Pattern.Decimal", "###,###,###.##"),
    PATTERN_COMMAND_SIGN("Pattern.Command.Sign", "yyyy-MM-dd HH:mm"),
    PERMISSION_ENDER_CHEST_COMMAND("Permission.EnderChest.Command", "necronic.command.enderchest.command"),
    PERMISSION_ENDER_CHEST_USE("Permission.EnderChest.Use", "necronic.command.enderchest.use"),
    PERMISSION_ENDER_CHEST_OTHER("Permission.EnderChest.Other", "necronic.command.enderchest.other"),
    PERMISSION_RELOAD("Permission.Reload", "necronic.command.reload"),
    PERMISSION_SPAWN("Permission.Spawn", "necronic.command.spawn"),
    PERMISSION_SET_SPAWN("Permission.SetSpawn", "necronic.command.setspawn"),
    PERMISSION_LOCATION("Permission.Warp", "necronic.command.warp"),
    PERMISSION_SET_LOCATION("Permission.SetWarp", "necronic.command.setwarp"),
    PERMISSION_REMOVE_LOCATION("Permission.RemoveWarp", "necronic.command.removewarp"),
    PERMISSION_HOME("Permission.Home", "necronic.command.home"),
    PERMISSION_ADD_HOME("Permission.AddHome", "necronic.command.addhome"),
    PERMISSION_REMOVE_HOME("Permission.RemoveHome", "necronic.command.removehome"),
    PERMISSION_COLOR_NORMAL("Permission.Color.Normal", "necronic.event.color.normal"),
    PERMISSION_COLOR_HEX("Permission.Color.Hex", "necronic.event.color.hex"),
    PERMISSION_HOME_UNLIMITED("Permission.Homes.Unlimited", "necronic.home.unlimited"),
    PERMISSION_HOME_LIMIT_TEAM("Permission.Homes.Limit.Team", "necronic.home.limit.team"),
    PERMISSION_HOME_LIMIT_RANK("Permission.Homes.Limit.Rank", "necronic.home.limit.rank"),
    PERMISSION_WORKBENCH("Permission.Workbench", "necronic.command.workbench"),
    PERMISSION_ANVIL("Permission.Anvil", "necronic.command.anvil"),
    PERMISSION_TRASH("Permission.Trash", "necronic.command.trash"),
    PERMISSION_FEED_COMMAND("Permission.Feed.Command", "necronic.command.feed.command"),
    PERMISSION_FEED_USE("Permission.Feed.Use", "necronic.command.feed.use"),
    PERMISSION_FEED_OTHER("Permission.Feed.Other", "necronic.command.feed.other"),
    PERMISSION_HEAL_COMMAND("Permission.Heal.Command", "necronic.command.heal.command"),
    PERMISSION_HEAL_USE("Permission.Heal.Use", "necronic.command.heal.use"),
    PERMISSION_HEAL_OTHER("Permission.Heal.Other", "necronic.command.heal.other"),
    PERMISSION_FLY_COMMAND("Permission.Fly.Command", "necronic.command.fly.command"),
    PERMISSION_FLY_USE("Permission.Fly.Use", "necronic.command.fly.use"),
    PERMISSION_FLY_OTHER("Permission.Fly.Other", "necronic.command.fly.other"),
    PERMISSION_GOD_MODE_COMMAND("Permission.GodMode.Command", "necronic.command.godmode.command"),
    PERMISSION_GOD_MODE_USE("Permission.GodMode.Use", "necronic.command.godmode.use"),
    PERMISSION_GOD_MODE_OTHER("Permission.GodMode.Other", "necronic.command.godmode.other"),
    PERMISSION_JOIN_FLY("Permission.Fly.Join", "necronic.event.join.fly"),
    PERMISSION_JOIN_GOD_MODE("Permission.GodMode.Join", "necronic.event.join.godmode"),
    PERMISSION_RANK_COMMAND("Permission.Rank.Command", "necronic.command.rank.command"),
    PERMISSION_RANK_ADD("Permission.Rank.Add", "necronic.command.rank.add"),
    PERMISSION_RANK_REMOVE("Permission.Rank.Remove", "necronic.command.rank.remove"),
    PERMISSION_RANK_GET("Permission.Rank.Get", "necronic.command.rank.get"),
    PERMISSION_RANK_SET_CHAT_PREFIX("Permission.Rank.Set.Chat.Prefix", "necronic.command.rank.set.chat.prefix"),
    PERMISSION_RANK_SET_CHAT_SUFFIX("Permission.Rank.Set.Chat.Suffix", "necronic.command.rank.set.chat.suffix"),
    PERMISSION_RANK_SET_CHAT_COLOR("Permission.Rank.Set.Chat.Color", "necronic.command.rank.set.chat.color"),
    PERMISSION_RANK_SET_TAB_PREFIX("Permission.Rank.Set.Tab.Prefix", "necronic.command.rank.set.tab.prefix"),
    PERMISSION_RANK_SET_TAB_SUFFIX("Permission.Rank.Set.Tab.Suffix", "necronic.command.rank.set.tab.suffix"),
    PERMISSION_RANK_SET_TAB_COLOR("Permission.Rank.Set.Tab.Color", "necronic.command.rank.set.tab.color"),
    PERMISSION_RANK_SET_TAB_ID("Permission.Rank.Set.Tab.ID", "necronic.command.rank.set.tab.id"),
    PERMISSION_RANK_SET_TEAM_ID("Permission.Rank.Set.TeamID", "necronic.command.rank.set.teamid"),
    PERMISSION_RANK_SET_PERMISSION("Permission.Rank.Set.Permission", "necronic.command.rank.set.permission"),
    PERMISSION_LOBBY("Permission.Lobby", "necronic.command.lobby"),
    PERMISSION_TIME_DAY("Permission.Time.Day", "necronic.command.day"),
    PERMISSION_TIME_NIGHT("Permission.Time.Night", "necronic.command.night"),
    PERMISSION_HEAD("Permission.Head", "necronic.command.head"),
    PERMISSION_SIGN("Permission.Sign", "necronic.command.sign"),
    PERMISSION_UN_SIGN("Permission.UnSign", "necronic.command.unsign"),
    PERMISSION_REPAIR("Permission.Repair", "necronic.command.repair"),
    PERMISSION_RENAME("Permission.Rename", "necronic.command.rename"),
    PERMISSION_LIVE("Permission.Live", "necronic.command.live"),
    PERMISSION_NOT_COOLDOWN("Permission.NotCooldown", "necronic.admin.notcooldown"),
    PERMISSION_TP("Permission.Tp", "necronic.command.tp"),
    PERMISSION_TPA("Permission.Tpa", "necronic.command.tpa"),
    PERMISSION_TPA_HERE("Permission.TpaHere", "necronic.command.tpahere"),
    PERMISSION_TPA_ACCEPT("Permission.TpaAccept", "necronic.command.tpaaccept"),
    PERMISSION_TPA_DENY("Permission.TpaDeny", "necronic.command.tpadeny"),
    PERMISSION_MONEY_COMMAND("Permission.Money.Command", "necronic.command.money.command"),
    PERMISSION_MONEY_USE("Permission.Money.Use", "necronic.command.money.use"),
    PERMISSION_MONEY_OTHER("Permission.Money.Other", "necronic.command.money.other"),
    PERMISSION_ECONOMY_COMMAND("Permission.Economy.Command", "necronic.command.economy.command"),
    PERMISSION_ECONOMY_SET("Permission.Economy.Set", "necronic.command.economy.set"),
    PERMISSION_ECONOMY_ADD("Permission.Economy.Add", "necronic.command.economy.add"),
    PERMISSION_ECONOMY_REMOVE("Permission.Economy.Remove", "necronic.command.economy.remove"),
    PERMISSION_ECONOMY_RESET("Permission.Economy.Reset", "necronic.command.economy.reset"),
    PERMISSION_PAY_USE("Permission.Pay.Use", "necronic.command.pay.use"),
    PERMISSION_PAY_ALL_PLAYERS("Permission.Pay.All.Players", "necronic.command.pay.all.players"),
    PERMISSION_PAY_ALL_MONEY("Permission.Pay.All.Money", "necronic.command.pay.all.money"),
    PERMISSION_PAY_NEGATIVE("Permission.Pay.Negative", "necronic.command.pay.negative"),
    PERMISSION_GAME_MODE_COMMAND("Permission.GameMode.Command", "necronic.command.gamemode.command"),
    PERMISSION_GAME_MODE_USE_SURVIVAL("Permission.GameMode.Use.Survival", "necronic.command.gamemode.use.survival"),
    PERMISSION_GAME_MODE_USE_CREATIVE("Permission.GameMode.Use.Creative", "necronic.command.gamemode.use.creative"),
    PERMISSION_GAME_MODE_USE_ADVENTURE("Permission.GameMode.Use.Adventure", "necronic.command.gamemode.use.adventure"),
    PERMISSION_GAME_MODE_USE_SPECTATOR("Permission.GameMode.Use.Spectator", "necronic.command.gamemode.use.spectator"),
    PERMISSION_GAME_MODE_OTHER_SURVIVAL("Permission.GameMode.Other.Survival", "necronic.command.gamemode.other.survival"),
    PERMISSION_GAME_MODE_OTHER_CREATIVE("Permission.GameMode.Other.Creative", "necronic.command.gamemode.other.creative"),
    PERMISSION_GAME_MODE_OTHER_ADVENTURE("Permission.GameMode.Other.Adventure", "necronic.command.gamemode.other.adventure"),
    PERMISSION_GAME_MODE_OTHER_SPECTATOR("Permission.GameMode.Other.Spectator", "necronic.command.gamemode.other.spectator"),
    PERMISSION_SELL("Permission.Sell", "necronic.command.sell"),
    PERMISSION_ITEM_VALUE_COMMAND("Permission.ItemValue.Command", "necronic.command.itemvalue.command"),
    PERMISSION_ITEM_VALUE_GET("Permission.ItemValue.Get", "necronic.command.itemvalue.get"),
    PERMISSION_ITEM_VALUE_SET("Permission.ItemValue.Set", "necronic.command.itemvalue.set"),
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
    COMMAND_ENABLE_RANK_COMMAND("Command.Enable.Rank.Command", true),
    COMMAND_ENABLE_RANK_ADD("Command.Enable.Rank.Add", true),
    COMMAND_ENABLE_RANK_REMOVE("Command.Enable.Rank.Remove", true),
    COMMAND_ENABLE_RANK_GET("Command.Enable.Rank.Get", true),
    COMMAND_ENABLE_RANK_SET_CHAT_PREFIX("Command.Enable.Rank.Set.Chat.Prefix", true),
    COMMAND_ENABLE_RANK_SET_CHAT_SUFFIX("Command.Enable.Rank.Set.Chat.Suffix", true),
    COMMAND_ENABLE_RANK_SET_CHAT_COLOR("Command.Enable.Rank.Set.Chat.Color", true),
    COMMAND_ENABLE_RANK_SET_TAB_PREFIX("Command.Enable.Rank.Set.Tab.Prefix", true),
    COMMAND_ENABLE_RANK_SET_TAB_SUFFIX("Command.Enable.Rank.Set.Tab.Suffix", true),
    COMMAND_ENABLE_RANK_SET_TAB_COLOR("Command.Enable.Rank.Set.Tab.Color", true),
    COMMAND_ENABLE_RANK_SET_TAB_ID("Command.Enable.Rank.Set.Tab.ID", true),
    COMMAND_ENABLE_RANK_SET_TEAM_ID("Command.Enable.Rank.Set.TeamID", true),
    COMMAND_ENABLE_RANK_SET_PERMISSION("Command.Enable.Rank.Set.Permission", true),
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
    EVENT_ENABLE_PLAYER_JOIN("Event.Enable.PlayerJoin", true),
    EVENT_ENABLE_PLAYER_QUIT("Event.Enable.PlayerQuit", true),
    EVENT_ENABLE_TELEPORT_TO_SPAWN("Event.Enable.TeleportToSpawn", true),
    EVENT_ENABLE_JOIN_MESSAGE("Event.Enable.Join.Message", true),
    EVENT_ENABLE_JOIN_TITLE("Event.Enable.Join.Title", true),
    HOME_LIMIT_DEFAULT("Home.Limit.Default", 4),
    HOME_LIMIT_RANK("Home.Limit.Rank", 8),
    HOME_LIMIT_TEAM("Home.Limit.Team", 12),
    RANK_CHAT_PREFIX_MESSAGE("Rank.Chat.PrefixMessage", " &8» &r"),
    RANK_UPDATE_TAB_TIMER("Rank.Update.TabTimer", 2L),
    RANK_PRE_PERMISSION("Rank.DefaultPrePermission", "necronic.rank."),
    RANK_DEFAULT_NAME("Rank.Default.Name", "default"),
    RANK_DEFAULT_CHAT_PREFIX("Rank.Default.Chat.Prefix", "&7"),
    RANK_DEFAULT_CHAT_SUFFIX("Rank.Default.Chat.Suffix", ""),
    RANK_DEFAULT_CHAT_COLOR("Rank.Default.Chat.Color", "&f"),
    RANK_DEFAULT_TAB_PREFIX("Rank.Default.Tab.Prefix", "&7"),
    RANK_DEFAULT_TAB_SUFFIX("Rank.Default.Tab.Suffix", ""),
    RANK_DEFAULT_TAB_COLOR("Rank.Default.Tab.Color", ChatColor.GRAY.name()),
    RANK_DEFAULT_TAB_ID("Rank.Default.Tab.ID", "1111"),
    RANK_LIVE_SUFFIX("Rank.Live.Suffix", " &8[&5LIVE&8]"),
    RANK_ENABLE_TAB("Rank.Enable", true),
    SCOREBOARD_ENABLE_TAB_LIST("Scoreboard.Enable.TabList", true),
    SCOREBOARD_ENABLE_SCOREBOARD("Scoreboard.Enable.Scoreboard", true),
    SCOREBOARD_UPDATE_TAB_LIST("Scoreboard.Update.TabList", 2L),
    SCOREBOARD_UPDATE_SCORE_TIMER("Scoreboard.Update.ScoreTimer", 2L),
    SCOREBOARD_ENABLE_SCORE_15("Scoreboard.Enable.Score.15", false),
    SCOREBOARD_ENABLE_SCORE_14("Scoreboard.Enable.Score.14", false),
    SCOREBOARD_ENABLE_SCORE_13("Scoreboard.Enable.Score.13", true),
    SCOREBOARD_ENABLE_SCORE_12("Scoreboard.Enable.Score.12", true),
    SCOREBOARD_ENABLE_SCORE_11("Scoreboard.Enable.Score.11", true),
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
    MATERIAL_CASE("Material.Case", false);

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
