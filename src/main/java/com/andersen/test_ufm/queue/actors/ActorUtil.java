package com.andersen.test_ufm.queue.actors;

import com.andersen.test_ufm.scheduler.Scheduler;
import com.andersen.test_ufm.service.IProcessService;
import com.andersen.test_ufm.utils.FileUtil;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

@Component
public class ActorUtil {
    private static final Logger LOGGER = LoggerFactory.getLogger(ActorUtil.class);

    @Autowired
    FileUtil fileUtil;

    @Autowired
    IProcessService processService;

    private JSONObject outputData;

    public void process(File inputFile) {
        LOGGER.info("Start processing file ...");
        JSONObject inputData = parseFileToJSON(inputFile);
        if(inputData != null){
            outputData = processService.process(inputData);
            LOGGER.info("Result processing file: " + outputData.toString());
        }
        File dest = new File("c:/Users/anduser/Other/processed/"+inputFile.getName());
        fileUtil.copyFile(inputFile, dest);
        fileUtil.deleteFile(inputFile);
        fileUtil.createFile(inputFile.getName(),outputData);
    }


    public JSONObject parseFileToJSON(File inputFile) {
        String content = null;
        JSONObject json = null;

        try {
            content = new String(Files.readAllBytes(inputFile.toPath()));
        } catch (IOException e) {
            e.printStackTrace();
        }

        JSONParser parser = new JSONParser();
        try {
            json = (JSONObject) parser.parse(content);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return json;
    }
}
