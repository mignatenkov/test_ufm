package com.andersen.test_ufm.akka.actors;

import com.andersen.test_ufm.service.IProcessService;
import com.andersen.test_ufm.utils.FileUtil;
import lombok.extern.slf4j.Slf4j;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

@Slf4j
@Component
public class ActorUtil {

    @Autowired
    private FileUtil fileUtil;

    @Autowired
    private IProcessService processService;

    @Value("${application.processedFilesDir:processed}")
    private String processedFilesDir;

    private JSONObject outputData;

    public void process(File inputFile) {
        log.info("Start processing file ...");
        JSONObject inputData = parseFileToJSON(inputFile);
        if(inputData != null){
            outputData = processService.process(inputData);
            log.info("Result processing file: " + outputData.toString());
        }
        File dest = new File(processedFilesDir + "/" + inputFile.getName());
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
