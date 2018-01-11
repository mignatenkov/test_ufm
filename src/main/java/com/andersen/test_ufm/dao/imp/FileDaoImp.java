package com.andersen.test_ufm.dao.imp;

import com.andersen.test_ufm.dao.FileDao;
import org.springframework.stereotype.Component;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Component
public class FileDaoImp implements FileDao {
    public List<File> getListFile(){
        File folder = new File("c:/Users/anduser/Other/input");
        File[] files = folder.listFiles();
        List<File> list= Arrays.asList(files);

        return list;
    }
}
