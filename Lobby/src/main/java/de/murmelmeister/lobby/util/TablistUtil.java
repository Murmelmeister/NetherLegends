package de.murmelmeister.lobby.util;

import de.murmelmeister.lobby.Main;
import de.murmelmeister.lobby.configs.Config;
import de.murmelmeister.lobby.configs.Message;
import de.murmelmeister.lobby.util.config.Configs;
import de.murmelmeister.lobby.util.config.Messages;
import org.bukkit.Server;
import org.bukkit.entity.Player;

public class TablistUtil {
    private final Player player;
    private final Main main;

    public TablistUtil(Player player, Main main) {
        this.player = player;
        this.main = main;
    }

    public void setScoreboardTabList() {
        final Config config = main.getConfig();
        final Message message = main.getMessage();
        final Server server = player.getServer();
        player.setPlayerListHeaderFooter(HexColor.format(message.getString(Messages.SCOREBOARD_TAB_LIST_HEADER)
                        .replace("[CURRENT_PLAYERS]", String.valueOf(server.getOnlinePlayers().size()))
                        .replace("[MAX_PLAYERS]", String.valueOf(server.getMaxPlayers()))
                        .replace("[SERVER]", config.getString(Configs.CURRENT_SERVER))),
                HexColor.format(message.getString(Messages.SCOREBOARD_TAB_LIST_FOOTER)));
    }
}
