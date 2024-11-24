package de.murmelmeister.citybuild.util.scoreboard;

import de.murmelmeister.citybuild.CityBuild;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.*;

import java.util.Objects;

public abstract class ScoreboardBuilder implements Runnable {
    private final Scoreboard scoreboard;
    private final Objective objective;

    protected final Player player;
    protected final CityBuild plugin;

    public ScoreboardBuilder(Player player, CityBuild plugin) {
        this.player = player;
        this.plugin = plugin;
        if (player.getScoreboard().equals(Bukkit.getScoreboardManager().getMainScoreboard()))
            player.setScoreboard(Bukkit.getScoreboardManager().getNewScoreboard());

        this.scoreboard = player.getScoreboard();

        if (this.scoreboard.getObjective("display") != null)
            Objects.requireNonNull(this.scoreboard.getObjective("display")).unregister();

        this.objective = this.scoreboard.registerNewObjective("display", Criteria.DUMMY, Component.text("Default Scoreboard", NamedTextColor.DARK_PURPLE));
        this.objective.setDisplaySlot(DisplaySlot.SIDEBAR);
        createScoreboard();
    }

    @Override
    public void run() {
        if (player.getScoreboard() != null && player.getScoreboard().getObjective("display") != null)
            updateScoreboard();
        else createScoreboard();
    }

    protected abstract void createScoreboard();

    protected abstract void updateScoreboard();

    protected void setDisplayName(Component displayName) {
        this.objective.displayName(displayName);
    }

    protected void setDisplayName(String displayName) {
        this.objective.displayName(MiniMessage.miniMessage().deserialize(displayName));
    }

    protected void setDisplayNameOld(String displayName) {
        this.objective.setDisplayName(displayName);
    }

    protected void setScoreContent(String content, int score) {
        this.objective.getScore(content).setScore(score);
    }

    protected void removeScoreContent(String content) {
        this.scoreboard.resetScores(content);
    }

    protected void setScoreTeam(Component content, int score) {
        Team team = getTeamByScore(score);
        if (team == null) return;
        team.prefix(content);
        showScore(score);
    }

    protected void setScoreTeam(String content, int score) {
        Team team = getTeamByScore(score);
        if (team == null) return;
        team.prefix(MiniMessage.miniMessage().deserialize(content));
        showScore(score);
    }

    protected void setScoreTeamOld(String content, int score) {
        Team team = getTeamByScore(score);
        if (team == null) return;
        team.setPrefix(content);
        showScore(score);
    }

    protected void removeScoreTeam(int score) {
        hideScore(score);
    }

    private EntryName getEntryNameByScore(int id) {
        for (EntryName name : EntryName.values())
            if (id == name.getEntry())
                return name;
        return null;
    }

    private Team getTeamByScore(int id) {
        EntryName name = getEntryNameByScore(id);
        if (name == null) return null;

        Team team = scoreboard.getEntryTeam(name.getEntryName());
        if (team != null) return team;

        team = scoreboard.registerNewTeam(name.name());
        team.addEntry(name.getEntryName());
        return team;
    }

    private void showScore(int score) {
        EntryName name = getEntryNameByScore(score);
        if (name == null) return;
        if (objective.getScore(name.getEntryName()).isScoreSet()) return;
        objective.getScore(name.getEntryName()).setScore(score);
    }

    private void hideScore(int score) {
        EntryName name = getEntryNameByScore(score);
        if (name == null) return;
        if (!objective.getScore(name.getEntryName()).isScoreSet()) return;
        scoreboard.resetScores(name.getEntryName());
    }
}
