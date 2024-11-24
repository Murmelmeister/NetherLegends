package de.murmelmeister.citybuild.util.scoreboard;

import de.murmelmeister.citybuild.CityBuild;
import de.murmelmeister.citybuild.api.Economy;
import de.murmelmeister.citybuild.files.ConfigFile;
import de.murmelmeister.citybuild.files.MessageFile;
import de.murmelmeister.citybuild.util.config.Configs;
import de.murmelmeister.citybuild.util.config.Messages;
import de.murmelmeister.murmelapi.user.User;
import org.bukkit.entity.Player;

public class TestScoreboard extends ScoreboardBuilder {
    public TestScoreboard(Player player, CityBuild plugin) {
        super(player, plugin);
    }

    @Override
    protected void createScoreboard() {
        setScoreboard();
    }

    @Override
    protected void updateScoreboard() {
        setScoreboard();
    }

    private void setScoreboard() {
        final ConfigFile config = plugin.getConfigFile();
        final MessageFile message = plugin.getMessageFile();
        final Economy economy = plugin.getEconomy();
        final User user = plugin.getUser();
        final int userId = user.getId(player.getUniqueId());
        setDisplayName(message.getString(Messages.SCOREBOARD_SCORE_DISPLAY_NAME));

        if (config.getBoolean(Configs.SCOREBOARD_ENABLE_SCORE_15))
            setScoreTeam(message.getString(Messages.SCOREBOARD_SCORE_15)
                    .replace("[SERVER]", config.getString(Configs.CURRENT_SERVER))
                    .replace("[MONEY]", economy.getFormattedMoney(userId))
                    .replace("[BANK]", economy.getFormattedBankMoney(userId)), 15);
        if (config.getBoolean(Configs.SCOREBOARD_ENABLE_SCORE_14))
            setScoreTeam(message.getString(Messages.SCOREBOARD_SCORE_14)
                    .replace("[SERVER]", config.getString(Configs.CURRENT_SERVER))
                    .replace("[MONEY]", economy.getFormattedMoney(userId))
                    .replace("[BANK]", economy.getFormattedBankMoney(userId)), 14);
        if (config.getBoolean(Configs.SCOREBOARD_ENABLE_SCORE_13))
            setScoreTeam(message.getString(Messages.SCOREBOARD_SCORE_13)
                    .replace("[SERVER]", config.getString(Configs.CURRENT_SERVER))
                    .replace("[MONEY]", economy.getFormattedMoney(userId))
                    .replace("[BANK]", economy.getFormattedBankMoney(userId)), 13);
        if (config.getBoolean(Configs.SCOREBOARD_ENABLE_SCORE_12))
            setScoreTeam(message.getString(Messages.SCOREBOARD_SCORE_12)
                    .replace("[SERVER]", config.getString(Configs.CURRENT_SERVER))
                    .replace("[MONEY]", economy.getFormattedMoney(userId))
                    .replace("[BANK]", economy.getFormattedBankMoney(userId)), 12);
        if (config.getBoolean(Configs.SCOREBOARD_ENABLE_SCORE_11))
            setScoreTeam(message.getString(Messages.SCOREBOARD_SCORE_11)
                    .replace("[SERVER]", config.getString(Configs.CURRENT_SERVER))
                    .replace("[MONEY]", economy.getFormattedMoney(userId))
                    .replace("[BANK]", economy.getFormattedBankMoney(userId)), 11);
        if (config.getBoolean(Configs.SCOREBOARD_ENABLE_SCORE_10))
            setScoreTeam(message.getString(Messages.SCOREBOARD_SCORE_10)
                    .replace("[SERVER]", config.getString(Configs.CURRENT_SERVER))
                    .replace("[MONEY]", economy.getFormattedMoney(userId))
                    .replace("[BANK]", economy.getFormattedBankMoney(userId)), 10);
        if (config.getBoolean(Configs.SCOREBOARD_ENABLE_SCORE_9))
            setScoreTeam(message.getString(Messages.SCOREBOARD_SCORE_9)
                    .replace("[SERVER]", config.getString(Configs.CURRENT_SERVER))
                    .replace("[MONEY]", economy.getFormattedMoney(userId))
                    .replace("[BANK]", economy.getFormattedBankMoney(userId)), 9);
        if (config.getBoolean(Configs.SCOREBOARD_ENABLE_SCORE_8))
            setScoreTeam(message.getString(Messages.SCOREBOARD_SCORE_8)
                    .replace("[SERVER]", config.getString(Configs.CURRENT_SERVER))
                    .replace("[MONEY]", economy.getFormattedMoney(userId))
                    .replace("[BANK]", economy.getFormattedBankMoney(userId)), 8);
        if (config.getBoolean(Configs.SCOREBOARD_ENABLE_SCORE_7))
            setScoreTeam(message.getString(Messages.SCOREBOARD_SCORE_7)
                    .replace("[SERVER]", config.getString(Configs.CURRENT_SERVER))
                    .replace("[MONEY]", economy.getFormattedMoney(userId))
                    .replace("[BANK]", economy.getFormattedBankMoney(userId)), 7);
        if (config.getBoolean(Configs.SCOREBOARD_ENABLE_SCORE_6))
            setScoreTeam(message.getString(Messages.SCOREBOARD_SCORE_6)
                    .replace("[SERVER]", config.getString(Configs.CURRENT_SERVER))
                    .replace("[MONEY]", economy.getFormattedMoney(userId))
                    .replace("[BANK]", economy.getFormattedBankMoney(userId)), 6);
        if (config.getBoolean(Configs.SCOREBOARD_ENABLE_SCORE_5))
            setScoreTeam(message.getString(Messages.SCOREBOARD_SCORE_5)
                    .replace("[SERVER]", config.getString(Configs.CURRENT_SERVER))
                    .replace("[MONEY]", economy.getFormattedMoney(userId))
                    .replace("[BANK]", economy.getFormattedBankMoney(userId)), 5);
        if (config.getBoolean(Configs.SCOREBOARD_ENABLE_SCORE_4))
            setScoreTeam(message.getString(Messages.SCOREBOARD_SCORE_4)
                    .replace("[SERVER]", config.getString(Configs.CURRENT_SERVER))
                    .replace("[MONEY]", economy.getFormattedMoney(userId))
                    .replace("[BANK]", economy.getFormattedBankMoney(userId)), 4);
        if (config.getBoolean(Configs.SCOREBOARD_ENABLE_SCORE_3))
            setScoreTeam(message.getString(Messages.SCOREBOARD_SCORE_3)
                    .replace("[SERVER]", config.getString(Configs.CURRENT_SERVER))
                    .replace("[MONEY]", economy.getFormattedMoney(userId))
                    .replace("[BANK]", economy.getFormattedBankMoney(userId)), 3);
        if (config.getBoolean(Configs.SCOREBOARD_ENABLE_SCORE_2))
            setScoreTeam(message.getString(Messages.SCOREBOARD_SCORE_2)
                    .replace("[SERVER]", config.getString(Configs.CURRENT_SERVER))
                    .replace("[MONEY]", economy.getFormattedMoney(userId))
                    .replace("[BANK]", economy.getFormattedBankMoney(userId)), 2);
        if (config.getBoolean(Configs.SCOREBOARD_ENABLE_SCORE_1))
            setScoreTeam(message.getString(Messages.SCOREBOARD_SCORE_1)
                    .replace("[SERVER]", config.getString(Configs.CURRENT_SERVER))
                    .replace("[MONEY]", economy.getFormattedMoney(userId))
                    .replace("[BANK]", economy.getFormattedBankMoney(userId)), 1);
        if (config.getBoolean(Configs.SCOREBOARD_ENABLE_SCORE_0))
            setScoreTeam(message.getString(Messages.SCOREBOARD_SCORE_0)
                    .replace("[SERVER]", config.getString(Configs.CURRENT_SERVER))
                    .replace("[MONEY]", economy.getFormattedMoney(userId))
                    .replace("[BANK]", economy.getFormattedBankMoney(userId)), 0);

    }
}
