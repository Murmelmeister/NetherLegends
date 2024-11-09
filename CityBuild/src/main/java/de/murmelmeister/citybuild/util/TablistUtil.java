package de.murmelmeister.citybuild.util;

import de.murmelmeister.citybuild.CityBuild;
import de.murmelmeister.citybuild.files.ConfigFile;
import de.murmelmeister.citybuild.files.MessageFile;
import de.murmelmeister.citybuild.util.config.Configs;
import de.murmelmeister.citybuild.util.config.Messages;
import org.bukkit.Server;
import org.bukkit.entity.Player;

public class TablistUtil {
    private final Player player;
    private final CityBuild plugin;

    public TablistUtil(final Player player, final CityBuild plugin) {
        this.player = player;
        this.plugin = plugin;
    }

    public void setScoreboardTabList() {
        final ConfigFile config = plugin.getConfigFile();
        final MessageFile message = plugin.getMessageFile();
        final Server server = player.getServer();
        player.setPlayerListHeaderFooter(HexColor.format(message.getString(Messages.SCOREBOARD_TAB_LIST_HEADER)
                        .replace("[CURRENT_PLAYERS]", String.valueOf(server.getOnlinePlayers().size()))
                        .replace("[MAX_PLAYERS]", String.valueOf(server.getMaxPlayers()))
                        .replace("[SERVER]", config.getString(Configs.CURRENT_SERVER))),
                HexColor.format(message.getString(Messages.SCOREBOARD_TAB_LIST_FOOTER)));
    }
}
