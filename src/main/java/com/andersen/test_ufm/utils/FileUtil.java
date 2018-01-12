package com.andersen.test_ufm.utils;

import org.json.simple.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

/**
 * Класс реализующий методы для работы с файлами
 */
@Component
public class FileUtil {
    private static final Logger LOGGER = LoggerFactory.getLogger(FileUtil.class);

    public void copyFile(File source, File dest){
        try {
            if(!dest.exists()){
                Files.copy(source.toPath(), dest.toPath());
            } else {
                LOGGER.error("File " + source.getName() + " already exists. Copying file stopped.");
            }
        } catch (IOException e) {
            LOGGER.error("Error while coping the file: " + source.getName() + ". Error: " + e.getMessage());
        }
    }

    public void deleteFile(File file){
        try {
            Files.delete(file.toPath());
        } catch (IOException e) {
            LOGGER.error("Error while deleting the file: " + file.getName() + ". Error: " + e.getMessage());
        }
    }

    public void createFile(String fileName, JSONObject obj){
        try (FileWriter file = new FileWriter("c:/Users/anduser/Other/output/" + fileName)) {
            file.write(obj.toJSONString());
        } catch (IOException e) {
            LOGGER.error("Error while creating file. Error: " + e.getMessage());
        }
    }
}
