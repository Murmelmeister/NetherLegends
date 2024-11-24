package de.murmelmeister.citybuild.files;

import de.murmelmeister.citybuild.CityBuild;
import de.murmelmeister.citybuild.util.FileUtil;
import de.murmelmeister.citybuild.util.config.Messages;
import org.bukkit.configuration.file.YamlConfiguration;
import org.slf4j.Logger;

import java.io.File;
import java.io.IOException;

public class MessageFile {
    private final Logger logger;
    private File file;
    private YamlConfiguration config;

    public MessageFile(final Logger logger) {
        this.logger = logger;
        load();
    }

    public void reloadFile() {
        create();
    }

    private void create() {
        this.file = FileUtil.createFile(logger, CityBuild.getMainPath(), "message.yml");
        this.config = YamlConfiguration.loadConfiguration(file);
    }

    private void load() {
        create();
        for (Messages messages : Messages.VALUES)
            if (getString(messages) == null) set(messages);
        save();
    }

    private void save() {
        try {
            config.save(file);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void set(Messages messages) {
        config.set(messages.getPath(), messages.getValue());
    }

    public String getString(Messages messages) {
        return config.getString(messages.getPath());
    }

    public String prefix() {
        return getString(Messages.PREFIX);
    }
}
