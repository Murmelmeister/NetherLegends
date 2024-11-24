package de.murmelmeister.citybuild.command.commands;

import de.murmelmeister.citybuild.CityBuild;
import de.murmelmeister.citybuild.command.CommandManager;
import de.murmelmeister.citybuild.util.config.Configs;
import de.murmelmeister.citybuild.util.config.Messages;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.text.SimpleDateFormat;
import java.util.*;

public class SignCommand extends CommandManager {
    public SignCommand(CityBuild plugin) {
        super(plugin);
    }

    /*
    /sign <sign...>
     */
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (!(isEnable(sender, Configs.COMMAND_ENABLE_SIGN))) return true;
        if (!(hasPermission(sender, Configs.PERMISSION_SIGN))) return true;

        Player player = getPlayer(sender);
        if (!(existPlayer(sender))) return true;

        StringBuilder messageBuilder = new StringBuilder();
        for (String arg : args) {
            messageBuilder.append(arg).append(" ");
        }
        String messages = messageBuilder.toString();
        messages = messages.trim();

        if (player.getItemInHand().getType().equals(Material.AIR)) return true;

        if (player.hasPermission(config.getString(Configs.PERMISSION_NOT_COOLDOWN))) {
            createItem(player, messages);
            return true;
        }

        if (cooldown.getDuration(player.getUniqueId(), "Sign") <= System.currentTimeMillis())
            cooldown.remove(player.getUniqueId(), "Sign");

        if (cooldown.has(player.getUniqueId(), "Sign")) {
            sendMessage(player, message.getString(Messages.COOLDOWN_MESSAGE).replace("[DATE]", cooldown.getDurationDate(config, player.getUniqueId(), "Sign").replace(" ", message.getString(Messages.COOLDOWN_DATE))));
            return true;
        }

        cooldown.add(player.getUniqueId(), "Sign", config.getLong(Configs.TIME_SIGN_COOLDOWN));
        createItem(player, messages);
        return true;
    }

    @Override
    public @Nullable List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String alias, @NotNull String[] args) {
        return new ArrayList<>();
    }

    private void createItem(Player player, String lore) {
        ItemStack itemStack = player.getItemInHand();
        ItemMeta itemMeta = itemStack.getItemMeta();
        SimpleDateFormat format = new SimpleDateFormat(config.getString(Configs.PATTERN_COMMAND_SIGN));
        String created = format.format(new Date());
        List<Component> loreList = new ArrayList<>(Arrays.asList(MiniMessage.miniMessage().deserialize(lore), Component.text("\n")));
        loreList.add(MiniMessage.miniMessage().deserialize(message.getString(Messages.COMMAND_SIGN_CREATE).replace("[NAME]", player.getName()).replace("[DATE]", created.replace(" ", message.getString(Messages.COMMAND_SIGN_DATE)))));
        itemMeta.lore(loreList);
        itemStack.setItemMeta(itemMeta);
    }
}
