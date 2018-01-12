package com.andersen.test_ufm.queue.actors;

import akka.actor.AbstractActor;
import akka.actor.Props;
import com.andersen.test_ufm.service.IProcessService;
import com.andersen.test_ufm.utils.FileUtil;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.springframework.beans.factory.annotation.Autowired;
import akka.event.Logging;
import akka.event.LoggingAdapter;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;

/**
 * Класс-актор для обработки файла. Для каждого файла будет создаваться свой класс-актор
 */
@Component
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class FilePerformActor extends AbstractActor {
    private final LoggingAdapter LOGGER = Logging.getLogger(getContext().getSystem(), this);

    @Autowired
    IProcessService processService;

    @Autowired
    FileUtil fileUtil;

    public FilePerformActor() {
    }

    public FilePerformActor(IProcessService processService) {
        this.processService = processService;
    }

    public static Props props(IProcessService processService) {
        return Props.create(FilePerformActor.class, processService);
    }

    @Override
    public void preStart() throws Exception {
        super.preStart();
        LOGGER.info("Custom actor started");
    }

    @Override
    public void postStop() throws Exception {
        LOGGER.info("Custom actor stopped");
        super.postStop();
    }

    @Override
    public Receive createReceive() {
        return receiveBuilder().match(File.class, this::process).build();
    }


    private void process(File inputFile) {
        LOGGER.info("Start process message file ...");
        JSONObject inputData = parseFileToJSON(inputFile);
        //if(inputData != null){
        //    processService.process(inputData);
        //}
        File dest = new File("c:/Users/anduser/Other/processed/"+inputFile.getName());
        fileUtil.copyFile(inputFile, dest);
        //fileUtil.deleteFile(inputFile);
    }

    private JSONObject parseFileToJSON(File inputFile){
        JSONParser parser = new JSONParser();
        JSONObject jsonObject = null;

        try {
            Object obj = parser.parse(new FileReader(inputFile));
            jsonObject = (JSONObject) obj;
        } catch (Exception e) {
            LOGGER.error("Error parsing the file: " + inputFile.getName());
        }

        return jsonObject;
    }
}
