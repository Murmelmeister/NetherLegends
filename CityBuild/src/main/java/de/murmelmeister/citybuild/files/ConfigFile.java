package de.murmelmeister.citybuild.files;

import de.murmelmeister.citybuild.CityBuild;
import de.murmelmeister.citybuild.util.FileUtil;
import de.murmelmeister.citybuild.util.config.Configs;
import org.bukkit.configuration.file.YamlConfiguration;
import org.slf4j.Logger;

import java.io.File;
import java.io.IOException;

public class ConfigFile {
    private final Logger logger;
    private File file;
    private YamlConfiguration config;

    public ConfigFile(final Logger logger) {
        this.logger = logger;
        load();
    }

    public void reloadFile() {
        create();
    }

    private void create() {
        this.file = FileUtil.createFile(logger, CityBuild.getMainPath(), "config.yml");
        this.config = YamlConfiguration.loadConfiguration(file);
    }

    private void load() {
        create();
        for (Configs configs : Configs.VALUES)
            if (get(configs) == null) set(configs);
        save();
    }

    private void save() {
        try {
            config.save(file);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void set(Configs configs) {
        config.set(configs.getPath(), configs.getValue());
    }

    private Object get(Configs configs) {
        return config.get(configs.getPath());
    }

    public String getString(Configs configs) {
        return config.getString(configs.getPath());
    }

    public boolean getBoolean(Configs configs) {
        return config.getBoolean(configs.getPath());
    }

    public int getInt(Configs configs) {
        return config.getInt(configs.getPath());
    }

    public long getLong(Configs configs) {
        return config.getLong(configs.getPath());
    }

    public double getDouble(Configs configs) {
        return config.getDouble(configs.getPath());
    }
}
