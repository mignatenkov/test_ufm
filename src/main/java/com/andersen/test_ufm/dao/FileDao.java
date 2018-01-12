package com.andersen.test_ufm.dao;

import org.json.simple.JSONObject;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Интерфейс с методами доступа к данным
 */
public interface FileDao {
    default public List<File> getListFile(){
        return new ArrayList<>();
    }
}
