package de.murmelmeister.citybuild.util.scoreboard;

import de.murmelmeister.citybuild.Main;
import de.murmelmeister.citybuild.api.Economy;
import de.murmelmeister.citybuild.configs.Config;
import de.murmelmeister.citybuild.configs.Message;
import de.murmelmeister.citybuild.util.HexColor;
import de.murmelmeister.citybuild.util.config.Configs;
import de.murmelmeister.citybuild.util.config.Messages;
import org.bukkit.entity.Player;

import java.text.DecimalFormat;

public class TestScoreboard extends ScoreboardBuilder {
    //private BukkitTask task;

    public TestScoreboard(Player player, Main main) {
        super(player, main);
    }

    /*public void start() {
        task = main.getInstance().getServer().getScheduler().runTaskTimerAsynchronously(main.getInstance(), this, 0L, 20L);
    }

    public void stop() {
        if (task != null && task.isCancelled()) task.cancel();
    }*/

    @Override
    protected void createScoreboard() {
        setScoreboard();
    }

    @Override
    protected void updateScoreboard() {
        setScoreboard();
    }

    private void setScoreboard() {
        final Config config = main.getConfig();
        final Message message = main.getMessage();
        final Economy economy = main.getEconomy();
        final DecimalFormat decimalFormat = new DecimalFormat(config.getString(Configs.PATTERN_DECIMAL));
        setDisplayName(HexColor.format(message.getString(Messages.SCOREBOARD_SCORE_DISPLAY_NAME)));

        if (config.getBoolean(Configs.SCOREBOARD_ENABLE_SCORE_15))
            setScoreTeam(HexColor.format(message.getString(Messages.SCOREBOARD_SCORE_15)
                    .replace("[SERVER]", config.getString(Configs.CURRENT_SERVER))
                    .replace("[CURRENCY]", config.getString(Configs.ECONOMY_CURRENCY)).replace("[MONEY]", decimalFormat.format(economy.getMoney(player.getUniqueId())))), 15);
        if (config.getBoolean(Configs.SCOREBOARD_ENABLE_SCORE_14))
            setScoreTeam(HexColor.format(message.getString(Messages.SCOREBOARD_SCORE_14)
                    .replace("[SERVER]", config.getString(Configs.CURRENT_SERVER))
                    .replace("[CURRENCY]", config.getString(Configs.ECONOMY_CURRENCY)).replace("[MONEY]", decimalFormat.format(economy.getMoney(player.getUniqueId())))), 14);
        if (config.getBoolean(Configs.SCOREBOARD_ENABLE_SCORE_13))
            setScoreTeam(HexColor.format(message.getString(Messages.SCOREBOARD_SCORE_13)
                    .replace("[SERVER]", config.getString(Configs.CURRENT_SERVER))
                    .replace("[CURRENCY]", config.getString(Configs.ECONOMY_CURRENCY)).replace("[MONEY]", decimalFormat.format(economy.getMoney(player.getUniqueId())))), 13);
        if (config.getBoolean(Configs.SCOREBOARD_ENABLE_SCORE_12))
            setScoreTeam(HexColor.format(message.getString(Messages.SCOREBOARD_SCORE_12)
                    .replace("[SERVER]", config.getString(Configs.CURRENT_SERVER))
                    .replace("[CURRENCY]", config.getString(Configs.ECONOMY_CURRENCY)).replace("[MONEY]", decimalFormat.format(economy.getMoney(player.getUniqueId())))), 12);
        if (config.getBoolean(Configs.SCOREBOARD_ENABLE_SCORE_11))
            setScoreTeam(HexColor.format(message.getString(Messages.SCOREBOARD_SCORE_11)
                    .replace("[SERVER]", config.getString(Configs.CURRENT_SERVER))
                    .replace("[CURRENCY]", config.getString(Configs.ECONOMY_CURRENCY)).replace("[MONEY]", decimalFormat.format(economy.getMoney(player.getUniqueId())))), 11);
        if (config.getBoolean(Configs.SCOREBOARD_ENABLE_SCORE_10))
            setScoreTeam(HexColor.format(message.getString(Messages.SCOREBOARD_SCORE_10)
                    .replace("[SERVER]", config.getString(Configs.CURRENT_SERVER))
                    .replace("[CURRENCY]", config.getString(Configs.ECONOMY_CURRENCY)).replace("[MONEY]", decimalFormat.format(economy.getMoney(player.getUniqueId())))), 10);
        if (config.getBoolean(Configs.SCOREBOARD_ENABLE_SCORE_9))
            setScoreTeam(HexColor.format(message.getString(Messages.SCOREBOARD_SCORE_9)
                    .replace("[SERVER]", config.getString(Configs.CURRENT_SERVER))
                    .replace("[CURRENCY]", config.getString(Configs.ECONOMY_CURRENCY)).replace("[MONEY]", decimalFormat.format(economy.getMoney(player.getUniqueId())))), 9);
        if (config.getBoolean(Configs.SCOREBOARD_ENABLE_SCORE_8))
            setScoreTeam(HexColor.format(message.getString(Messages.SCOREBOARD_SCORE_8)
                    .replace("[SERVER]", config.getString(Configs.CURRENT_SERVER))
                    .replace("[CURRENCY]", config.getString(Configs.ECONOMY_CURRENCY)).replace("[MONEY]", decimalFormat.format(economy.getMoney(player.getUniqueId())))), 8);
        if (config.getBoolean(Configs.SCOREBOARD_ENABLE_SCORE_7))
            setScoreTeam(HexColor.format(message.getString(Messages.SCOREBOARD_SCORE_7)
                    .replace("[SERVER]", config.getString(Configs.CURRENT_SERVER))
                    .replace("[CURRENCY]", config.getString(Configs.ECONOMY_CURRENCY)).replace("[MONEY]", decimalFormat.format(economy.getMoney(player.getUniqueId())))), 7);
        if (config.getBoolean(Configs.SCOREBOARD_ENABLE_SCORE_6))
            setScoreTeam(HexColor.format(message.getString(Messages.SCOREBOARD_SCORE_6)
                    .replace("[SERVER]", config.getString(Configs.CURRENT_SERVER))
                    .replace("[CURRENCY]", config.getString(Configs.ECONOMY_CURRENCY)).replace("[MONEY]", decimalFormat.format(economy.getMoney(player.getUniqueId())))), 6);
        if (config.getBoolean(Configs.SCOREBOARD_ENABLE_SCORE_5))
            setScoreTeam(HexColor.format(message.getString(Messages.SCOREBOARD_SCORE_5)
                    .replace("[SERVER]", config.getString(Configs.CURRENT_SERVER))
                    .replace("[CURRENCY]", config.getString(Configs.ECONOMY_CURRENCY)).replace("[MONEY]", decimalFormat.format(economy.getMoney(player.getUniqueId())))), 5);
        if (config.getBoolean(Configs.SCOREBOARD_ENABLE_SCORE_4))
            setScoreTeam(HexColor.format(message.getString(Messages.SCOREBOARD_SCORE_4)
                    .replace("[SERVER]", config.getString(Configs.CURRENT_SERVER))
                    .replace("[CURRENCY]", config.getString(Configs.ECONOMY_CURRENCY)).replace("[MONEY]", decimalFormat.format(economy.getMoney(player.getUniqueId())))), 4);
        if (config.getBoolean(Configs.SCOREBOARD_ENABLE_SCORE_3))
            setScoreTeam(HexColor.format(message.getString(Messages.SCOREBOARD_SCORE_3)
                    .replace("[SERVER]", config.getString(Configs.CURRENT_SERVER))
                    .replace("[CURRENCY]", config.getString(Configs.ECONOMY_CURRENCY)).replace("[MONEY]", decimalFormat.format(economy.getMoney(player.getUniqueId())))), 3);
        if (config.getBoolean(Configs.SCOREBOARD_ENABLE_SCORE_2))
            setScoreTeam(HexColor.format(message.getString(Messages.SCOREBOARD_SCORE_2)
                    .replace("[SERVER]", config.getString(Configs.CURRENT_SERVER))
                    .replace("[CURRENCY]", config.getString(Configs.ECONOMY_CURRENCY)).replace("[MONEY]", decimalFormat.format(economy.getMoney(player.getUniqueId())))), 2);
        if (config.getBoolean(Configs.SCOREBOARD_ENABLE_SCORE_1))
            setScoreTeam(HexColor.format(message.getString(Messages.SCOREBOARD_SCORE_1)
                    .replace("[SERVER]", config.getString(Configs.CURRENT_SERVER))
                    .replace("[CURRENCY]", config.getString(Configs.ECONOMY_CURRENCY)).replace("[MONEY]", decimalFormat.format(economy.getMoney(player.getUniqueId())))), 1);
        if (config.getBoolean(Configs.SCOREBOARD_ENABLE_SCORE_0))
            setScoreTeam(HexColor.format(message.getString(Messages.SCOREBOARD_SCORE_0)
                    .replace("[SERVER]", config.getString(Configs.CURRENT_SERVER))
                    .replace("[CURRENCY]", config.getString(Configs.ECONOMY_CURRENCY)).replace("[MONEY]", decimalFormat.format(economy.getMoney(player.getUniqueId())))), 0);

    }
}
