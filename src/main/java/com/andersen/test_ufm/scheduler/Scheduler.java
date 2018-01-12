package com.andersen.test_ufm.scheduler;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.routing.BalancingPool;
import akka.routing.RoundRobinPool;
import com.andersen.test_ufm.dao.FileDao;
import com.andersen.test_ufm.queue.extention.FileActorExtension;
import org.json.simple.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.File;
import java.util.List;

/**
 * Класс-шедулер для обработки файлов через акторы
 */
@Component
public class Scheduler {
    private static final Logger LOGGER = LoggerFactory.getLogger(Scheduler.class);
    private final long DELAY = 10_000;

    @Autowired
    ApplicationContext context;

    @Autowired
    FileDao dataProvider;

    private ActorRef fileActor;

    private int countActors;

    @Scheduled(fixedDelay = DELAY)
    public void performRegularAction() {
        List<File> fileList = dataProvider.getListFile();
        LOGGER.info("Count files in input folder: " + fileList.size());

        for (File item : fileList) {
            fileActor.tell(item, ActorRef.noSender());
        }
    }

    @PostConstruct
    public void postConstructMethod() {
        LOGGER.info("Scheduler post construct");
        ActorSystem system = context.getBean(ActorSystem.class);
        FileActorExtension fileActorExtension = context.getBean(FileActorExtension.class);

        countActors = dataProvider.getListFile().size();
        LOGGER.info("Count actors: " + countActors);

        fileActor = system.actorOf(fileActorExtension.props("filePerformActor").withRouter(new RoundRobinPool(countActors)));
    }
}
