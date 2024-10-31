package de.murmelmeister.citybuild.configs;

import de.murmelmeister.citybuild.Main;
import de.murmelmeister.citybuild.util.FileUtil;
import de.murmelmeister.citybuild.util.config.Configs;
import org.bukkit.configuration.file.YamlConfiguration;
import org.slf4j.Logger;

import java.io.File;
import java.io.IOException;

public class Config {
    private final Logger logger;

    private File file;
    private YamlConfiguration config;

    public Config(Main main) {
        this.logger = main.getLogger();
    }

    public void register() {
        create();
        load();
        save();
    }

    public void create() {
        String fileName = "config.yml";
        this.file = FileUtil.createFile(logger, String.format("plugins//%s//", Configs.FILE_NAME.getValue()), fileName);
        this.config = YamlConfiguration.loadConfiguration(file);
    }

    public void load() {
        for (Configs configs : Configs.values())
            if (get(configs) == null) set(configs);
    }

    public void save() {
        try {
            config.save(file);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void set(Configs configs) {
        config.set(configs.getPath(), configs.getValue());
    }

    public Object get(Configs configs) {
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
