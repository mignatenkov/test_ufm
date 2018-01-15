package com.andersen.test_ufm.repository;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import javax.annotation.PostConstruct;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Класс реализующией методы доступа к файлам
 */
@Slf4j
@Repository
public class FileRepository {

    @Value("${application.inputFilesDir:input}")
    @Getter
    private String inputFilesDir;

    @Value("${application.processedFilesDir:processed}")
    @Getter
    private String processedFilesDir;

    @Value("${application.outputFilesDir:output}")
    @Getter
    private String outputFilesDir;

    private File fileInputFilesDir;
    private File fileProcessedFilesDir;
    private File fileOutputFilesDir;

    @PostConstruct
    public void init() {
        fileInputFilesDir = new File(inputFilesDir);
        fileProcessedFilesDir = new File(processedFilesDir);
        fileOutputFilesDir = new File(outputFilesDir);

        if (!fileInputFilesDir.exists()) {
            fileInputFilesDir.mkdir();
        }
        if (!fileProcessedFilesDir.exists()) {
            fileProcessedFilesDir.mkdir();
        }
        if (!fileOutputFilesDir.exists()) {
            fileOutputFilesDir.mkdir();
        }
    }

    public List<File> getListInputFiles(){
        File[] files = fileInputFilesDir.listFiles();
        List<File> list;
        if (files != null) {
            list = Arrays.asList(files);
        } else {
            list = new ArrayList<>();
        }

        return list;
    }

    public void moveFile(File source, File dest){
        if (source == null) {
            log.error("File " + source.getName() + " is null. Moving file stopped.");
            return;
        }
        try {
            Files.move(source.toPath(), dest.toPath(), StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {
            log.error("Error while moving the file: " + source.getName() + ". Error: " + e.toString());
        }
    }

    public void createOutputFile(String fileName, JSONObject obj){
        try (FileWriter file = new FileWriter(outputFilesDir + File.separator + fileName)) {
            file.write(obj.toJSONString());
        } catch (IOException e) {
            log.error("Error while creating file. Error: " + e.getMessage());
        }
    }
}
