package com.andersen.test_ufm.repository;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.io.File;
import java.util.Arrays;
import java.util.List;

/**
 * Класс реализующией методы доступа к данным
 */
@Component
public class FileDao {
    private static final Logger LOGGER = LoggerFactory.getLogger(FileDao.class);

    public List<File> getListFile(){
        File folder = new File("c:/Users/anduser/Other/input");
        File[] files = folder.listFiles();
        List<File> list= Arrays.asList(files);

        return list;
    }
}
