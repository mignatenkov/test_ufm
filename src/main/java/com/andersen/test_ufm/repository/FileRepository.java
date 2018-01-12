package com.andersen.test_ufm.repository;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import java.io.File;
import java.util.Arrays;
import java.util.List;

/**
 * Класс реализующией методы доступа к данным
 */
@Slf4j
@Repository
public class FileRepository {

    @Value("${application.inputFilesDir:input}")
    private String inputFilesDir;

    public List<File> getListFile(){
        File folder = new File(inputFilesDir);
        File[] files = folder.listFiles();
        List<File> list= Arrays.asList(files);

        return list;
    }
}
