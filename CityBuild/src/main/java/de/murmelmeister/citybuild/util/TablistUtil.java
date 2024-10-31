package de.murmelmeister.citybuild.util;

import de.murmelmeister.citybuild.Main;
import de.murmelmeister.citybuild.configs.Config;
import de.murmelmeister.citybuild.configs.Message;
import de.murmelmeister.citybuild.util.config.Configs;
import de.murmelmeister.citybuild.util.config.Messages;
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
