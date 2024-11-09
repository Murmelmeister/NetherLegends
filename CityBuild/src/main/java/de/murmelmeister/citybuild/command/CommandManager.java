package de.murmelmeister.citybuild.command;

import de.murmelmeister.citybuild.CityBuild;
import de.murmelmeister.citybuild.api.*;
import de.murmelmeister.citybuild.command.commands.*;
import de.murmelmeister.citybuild.command.commands.economy.*;
import de.murmelmeister.citybuild.command.commands.homes.AddHomeCommand;
import de.murmelmeister.citybuild.command.commands.homes.HomeCommand;
import de.murmelmeister.citybuild.command.commands.homes.RemoveHomeCommand;
import de.murmelmeister.citybuild.command.commands.inventories.*;
import de.murmelmeister.citybuild.command.commands.locations.*;
import de.murmelmeister.citybuild.command.commands.teleport.*;
import de.murmelmeister.citybuild.files.ConfigFile;
import de.murmelmeister.citybuild.files.MessageFile;
import de.murmelmeister.citybuild.util.HexColor;
import de.murmelmeister.citybuild.util.ListUtil;
import de.murmelmeister.citybuild.util.config.Configs;
import de.murmelmeister.citybuild.util.config.Messages;
import de.murmelmeister.murmelapi.user.User;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.CommandSender;
import org.bukkit.command.PluginCommand;
import org.bukkit.command.TabExecutor;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.util.StringUtil;
import org.slf4j.Logger;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public abstract class CommandManager implements TabExecutor {
    protected final Logger logger;
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
    protected final DecimalFormat decimalFormat;
    protected final PlayerInventory playerInventory;

    public CommandManager(CityBuild plugin) {
        this.logger = plugin.getSLF4JLogger();
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
        this.decimalFormat = new DecimalFormat(config.getString(Configs.PATTERN_DECIMAL));
    }

    public void sendMessage(CommandSender sender, String message) {
        if (config.getBoolean(Configs.PREFIX_ENABLE))
            sender.sendMessage(HexColor.format(this.message.prefix() + message));
        else sender.sendMessage(HexColor.format(message));
    }

    public boolean isEnable(CommandSender sender, Configs configs) {
        if (!(config.getBoolean(configs))) {
            sendMessage(sender, message.getString(Messages.DISABLE_COMMAND));
            return false;
        }
        return true;
    }

    public boolean hasPermission(CommandSender sender, Configs configs) {
        if (!(sender.hasPermission(config.getString(configs)))) {
            sendMessage(sender, message.getString(Messages.NO_PERMISSION));
            return false;
        }
        return true;
    }

    public Player getPlayer(CommandSender sender) {
        return sender instanceof Player ? (Player) sender : null;
    }

    public boolean existPlayer(CommandSender sender) {
        Player player = getPlayer(sender);
        if (player == null) {
            sendMessage(sender, message.getString(Messages.NO_CONSOLE));
            return false;
        } else return true;
    }

    public List<String> tabCompletePlayers(CommandSender sender, String[] args) {
        List<String> tabComplete = new ArrayList<>();
        String lastWord = args[args.length - 1];
        Player senderPlayer = sender instanceof Player ? (Player) sender : null;
        for (Player player : sender.getServer().getOnlinePlayers()) {
            String name = player.getName();
            if ((senderPlayer == null || senderPlayer.canSee(player)) && StringUtil.startsWithIgnoreCase(name, lastWord))
                tabComplete.add(name);
        }
        tabComplete.sort(String.CASE_INSENSITIVE_ORDER);
        return tabComplete;
    }

    public List<String> tabCompletePlayers(CommandSender sender, String[] args, int length) {
        if (args.length == length)
            return tabCompletePlayers(sender, args);
        return Collections.emptyList();
    }

    public List<String> tabCompleteOfflinePlayers(CommandSender sender, String[] args) {
        List<String> tabComplete = new ArrayList<>();
        String lastWord = args[args.length - 1];
        for (OfflinePlayer player : sender.getServer().getOfflinePlayers()) {
            String name = player.getName();
            assert name != null;
            if (StringUtil.startsWithIgnoreCase(name, lastWord))
                tabComplete.add(name);
        }
        tabComplete.sort(String.CASE_INSENSITIVE_ORDER);
        return tabComplete;
    }

    public List<String> tabCompleteOfflinePlayers(CommandSender sender, String[] args, int length) {
        if (args.length == length)
            return tabCompleteOfflinePlayers(sender, args);
        return Collections.emptyList();
    }

    public List<String> tabComplete(List<String> list, String[] args) {
        return list.stream().filter(s -> StringUtil.startsWithIgnoreCase(s, args[args.length - 1])).sorted().collect(Collectors.toList());
    }

    public List<String> tabComplete(List<String> list, String[] args, int length) {
        if (args.length == length)
            return tabComplete(list, args);
        return Collections.emptyList();
    }

    public static void register(CityBuild plugin) {
        addCommand(plugin, "citybuildreload", new CityBuildReloadCommand(plugin));
        addCommand(plugin, "enderchest", new EnderChestCommand(plugin));
        addCommand(plugin, "setspawn", new SetSpawnCommand(plugin));
        addCommand(plugin, "spawn", new SpawnCommand(plugin));
        addCommand(plugin, "removewarp", new RemoveWarpCommand(plugin));
        addCommand(plugin, "setwarp", new SetWarpCommand(plugin));
        addCommand(plugin, "warp", new WarpCommand(plugin));
        addCommand(plugin, "addhome", new AddHomeCommand(plugin));
        addCommand(plugin, "home", new HomeCommand(plugin));
        addCommand(plugin, "removehome", new RemoveHomeCommand(plugin));
        addCommand(plugin, "workbench", new WorkbenchCommand(plugin));
        addCommand(plugin, "anvil", new AnvilCommand(plugin));
        addCommand(plugin, "trash", new TrashCommand(plugin));
        addCommand(plugin, "feed", new FeedCommand(plugin));
        addCommand(plugin, "heal", new HealCommand(plugin));
        addCommand(plugin, "fly", new FlyCommand(plugin));
        addCommand(plugin, "godmode", new GodModeCommand(plugin));
        addCommand(plugin, "lobby", new LobbyCommand(plugin));
        addCommand(plugin, "live", new LiveCommand(plugin));
        addCommand(plugin, "day", new DayCommand(plugin));
        addCommand(plugin, "night", new NightCommand(plugin));
        addCommand(plugin, "sign", new SignCommand(plugin));
        addCommand(plugin, "unsign", new UnSignCommand(plugin));
        addCommand(plugin, "rename", new RenameCommand(plugin));
        addCommand(plugin, "repair", new RepairCommand(plugin));
        addCommand(plugin, "tp", new TpCommand(plugin));
        addCommand(plugin, "tpa", new TpaCommand(plugin));
        addCommand(plugin, "tpahere", new TpaHereCommand(plugin));
        addCommand(plugin, "tpaaccept", new TpaAcceptCommand(plugin));
        addCommand(plugin, "tpadeny", new TpaDenyCommand(plugin));
        addCommand(plugin, "money", new MoneyCommand(plugin));
        addCommand(plugin, "economy", new EconomyCommand(plugin));
        addCommand(plugin, "gamemode", new GameModeCommand(plugin));
        addCommand(plugin, "pay", new PayCommand(plugin));
        addCommand(plugin, "sell", new SellCommand(plugin));
        addCommand(plugin, "itemvalue", new ItemValueCommand(plugin));
        addCommand(plugin, "invsee", new InvseeCommand(plugin));
    }

    private static void addCommand(Plugin plugin, String name, TabExecutor executor) {
        PluginCommand command = plugin.getServer().getPluginCommand(name);
        if (command == null) throw new RuntimeException("Couldn't find command: " + name);
        command.setExecutor(executor);
        command.setTabCompleter(executor);
    }
}
