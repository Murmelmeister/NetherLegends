package de.murmelmeister.citybuild.api;

import de.murmelmeister.citybuild.CityBuild;
import de.murmelmeister.citybuild.files.ConfigFile;
import de.murmelmeister.citybuild.util.FileUtil;
import de.murmelmeister.citybuild.util.config.Configs;
import org.bukkit.configuration.file.YamlConfiguration;
import org.slf4j.Logger;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Locale;
import java.util.UUID;

public class Cooldown {
    private final Logger logger;
    private File file;
    private YamlConfiguration config;

    public Cooldown(final Logger logger) {
        this.logger = logger;
    }

    private void create(UUID uuid) {
        this.file = FileUtil.createFile(logger, CityBuild.getMainPath() + "/Cooldown/", uuid.toString() + ".yml");
        this.config = YamlConfiguration.loadConfiguration(file);
    }

    public void add(UUID uuid, String cooldownName, long time) {
        create(uuid);
        long duration = System.currentTimeMillis() + time;
        set("Cooldown." + cooldownName + ".Start", System.currentTimeMillis());
        set("Cooldown." + cooldownName + ".Duration", duration);
        save();
    }

    public void remove(UUID uuid, String cooldownName) {
        create(uuid);
        set("Cooldown." + cooldownName, null);
        save();
    }

    public boolean has(UUID uuid, String cooldownName) {
        create(uuid);
        return config.get("Cooldown." + cooldownName) != null;
    }

    public long getDuration(UUID uuid, String cooldownName) {
        create(uuid);
        return config.getLong("Cooldown." + cooldownName + ".Duration");
    }

    public String getDurationDate(ConfigFile configFile, UUID uuid, String cooldownName) {
        long duration = getDuration(uuid, cooldownName);
        return new SimpleDateFormat(configFile.getString(Configs.DATE_FORMAT_PATTERN), Locale.of(configFile.getString(Configs.DATE_FORMAT_LOCALE))).format(duration);
    }

    public String getStartDate(ConfigFile configFile, UUID uuid, String cooldownName) {
        long startTime = config.getLong("Cooldown." + cooldownName + ".Start");
        return new SimpleDateFormat(configFile.getString(Configs.DATE_FORMAT_PATTERN), Locale.of(configFile.getString(Configs.DATE_FORMAT_LOCALE))).format(startTime);
    }

    private void save() {
        try {
            config.save(file);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void set(String path, Object value) {
        config.set(path, value);
    }
}
