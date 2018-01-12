package com.andersen.test_ufm.dao.imp;

import com.andersen.test_ufm.dao.FileDao;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Класс реализующией методы доступа к данным
 */
@Component
public class FileDaoImp implements FileDao {
    private static final Logger LOGGER = LoggerFactory.getLogger(FileDaoImp.class);

    public List<File> getListFile(){
        File folder = new File("c:/Users/anduser/Other/input");
        File[] files = folder.listFiles();
        List<File> list= Arrays.asList(files);

        return list;
    }
}
