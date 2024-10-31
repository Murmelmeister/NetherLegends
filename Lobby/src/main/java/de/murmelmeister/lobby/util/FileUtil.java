package de.murmelmeister.lobby.util;

import org.slf4j.Logger;

import java.io.File;
import java.io.IOException;

public final class FileUtil {
    public static File createFile(Logger logger, String path, String fileName) {
        final File file = new File(path, fileName);
        final File parent = file.getParentFile();
        if (!parent.exists()) {
            boolean exists = parent.mkdirs();
            if (!exists) logger.warn("Failed to create directory: {}", parent);
        }

        if (!file.exists()) {
            try {
                boolean exists = file.createNewFile();
                if (!exists) logger.warn("Failed to create file: {}", file);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        return file;
    }
}
