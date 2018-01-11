package com.andersen.test_ufm.dao;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public interface FileDao {
    default public List<File> getListFile(){
        return new ArrayList<>();
    }
}
