package de.murmelmeister.citybuild.util;

import org.slf4j.Logger;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

public final class ImportDataUtil {
    public static void createDirectory(Logger logger, String path) {
        File file = new File(path);
        if (!file.exists()) {
            boolean success = file.mkdirs();
            if (!success) logger.error("Failed to create directory: {}", path);
        }
    }

    public static <T> List<T> readCsvFile(String filePath, Class<T> clazz) {
        List<T> list = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] values = line.split(",");
                T obj = clazz.getDeclaredConstructor().newInstance();
                // Set values to obj
                list.add(obj);
            }
        } catch (IOException | InstantiationException | IllegalAccessException | NoSuchMethodException |
                 InvocationTargetException e) {
            throw new RuntimeException(e);
        }
        return list;
    }
}
