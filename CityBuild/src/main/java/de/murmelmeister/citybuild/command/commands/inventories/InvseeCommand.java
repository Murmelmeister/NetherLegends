package de.murmelmeister.citybuild.command.commands.inventories;

import de.murmelmeister.citybuild.CityBuild;
import de.murmelmeister.citybuild.api.nbt.NbtCompound;
import de.murmelmeister.citybuild.api.nbt.NbtTagFloat;
import de.murmelmeister.citybuild.api.nbt.NbtTagString;
import de.murmelmeister.citybuild.command.CommandManager;
import de.murmelmeister.citybuild.util.config.Configs;
import de.murmelmeister.citybuild.util.config.Messages;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.*;
import java.util.*;

public class InvseeCommand extends CommandManager {
    public InvseeCommand(CityBuild plugin) {
        super(plugin);
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (!isEnable(sender, Configs.COMMAND_ENABLE_INVSEE)) return true;
        if (!hasPermission(sender, Configs.PERMISSION_INVSEE)) return true;

        Player player = getPlayer(sender);
        if (!existPlayer(sender)) return true;

        if (args.length != 1) {
            sendMessage(player, message.getString(Messages.COMMAND_SYNTAX).replace("[USAGE]", command.getUsage()));
            return true;
        }

        OfflinePlayer target = player.getServer().getOfflinePlayer(args[0]);
        if (!target.hasPlayedBefore()) {
            sendMessage(player, message.getString(Messages.NO_PLAYER_EXIST).replace("[PLAYER]", args[0]));
            return true;
        }

        if (!playerInventory.existFile(target.getUniqueId())) {
            sendMessage(player, message.getString(Messages.NO_PLAYER_EXIST).replace("[PLAYER]", args[0]));
            return true;
        }

        player.sendMessage("Reading player data...");
        readData(player, target.getUniqueId());
        return true;
    }

    @Override
    public @Nullable List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        return tabCompleteOfflinePlayers(sender, args, 1);
    }

    private void readData(Player player, UUID uuid) {
        String mainWorld = getMainWorld();
        if (mainWorld == null) throw new RuntimeException("Couldn't find main world");
        final File file = new File("./" + mainWorld + "/playerdata/", uuid + ".dat");
        NbtCompound readData = new NbtCompound();
        try (FileInputStream inputStream = new FileInputStream(file);
             DataInputStream dataInputStream = new DataInputStream(inputStream)) {
            readData.read(dataInputStream);
            NbtTagString name = (NbtTagString) readData.getTag("lastKnownName");
            NbtTagFloat health = (NbtTagFloat) readData.getTag("Health");
            player.sendMessage("Name: " + name.getValue() + "; Health: " + health.getValue());
        } catch (IOException e) {
            throw new RuntimeException("Failed to read player data", e);
        }
    }

    private String getMainWorld() {
        Properties properties = new Properties();
        try (FileInputStream inputStream = new FileInputStream("./server.properties")) {
            properties.load(inputStream);
            return properties.getProperty("level-name");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
