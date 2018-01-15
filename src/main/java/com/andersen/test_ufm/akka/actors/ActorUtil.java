package com.andersen.test_ufm.akka.actors;

import com.andersen.test_ufm.repository.ClientDBRepository;
import com.andersen.test_ufm.repository.FileRepository;
import com.andersen.test_ufm.service.IProcessService;
import lombok.extern.slf4j.Slf4j;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;

@Slf4j
@Component
public class ActorUtil {

    @Autowired
    private FileRepository fileRepository;

    @Autowired
    private ClientDBRepository clientDBRepository;

    @Autowired
    private IProcessService processService;

    public void process(File inputFile) {
        if (inputFile == null) {
            log.error("Input file is null!");
            return;
        }
        String inputFileName = inputFile.getName();
        log.debug("Start processing file " + inputFileName);
        File processedFile = new File(fileRepository.getProcessedFilesDir() + File.separator + inputFileName);

        try {
            fileRepository.moveFile(inputFile, processedFile);
        } catch (NoSuchFileException nsfe) {
            log.info("Input file " + inputFileName + " had been already processed by other actor. Breaking processing...");
            return;
        }

        JSONObject inputData = parseFileToJSON(processedFile);
        JSONObject outputData = new JSONObject();
        if (inputData != null){
            outputData = processService.process(inputData);
            log.debug("Result processing file: " + outputData.toString());
        }
        fileRepository.createOutputFile(inputFileName, outputData);

        clientDBRepository.createClient(outputData);
    }


    public JSONObject parseFileToJSON(File inputFile) {
        String content = null;
        JSONObject json = null;

        try {
            content = new String(Files.readAllBytes(inputFile.toPath()));
        } catch (IOException e) {
            log.error(e.toString());
        }

        JSONParser parser = new JSONParser();
        try {
            json = (JSONObject) parser.parse(content);
        } catch (ParseException e) {
            log.error(e.toString());
        }

        return json;
    }
}
