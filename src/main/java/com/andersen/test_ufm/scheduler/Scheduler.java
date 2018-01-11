package com.andersen.test_ufm.scheduler;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.routing.BalancingPool;
import com.andersen.test_ufm.dao.FileDao;
import com.andersen.test_ufm.queue.extention.FileActorExtension;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.File;
import java.util.List;

@Component
public class Scheduler {
    private static final Logger LOGGER = LoggerFactory.getLogger(Scheduler.class);
    private final long DELAY = 5000;

    @Autowired
    ApplicationContext context;

    @Autowired
    FileDao dataProvider;

    private ActorRef fileActor;

    private int countActors;

    @Scheduled(fixedDelay = DELAY)
    public void performRegularAction() {
        List<File> listFile = dataProvider.getListFile();
        LOGGER.info("Count files in input folder: " + listFile.size());

        for (File item : listFile) {
            LOGGER.info("Send file to actor");
            fileActor.tell(item, ActorRef.noSender());
        }
    }

    @PostConstruct
    public void postConstructMethod() {
        LOGGER.info("Scheduler post construct");
        ActorSystem system = context.getBean(ActorSystem.class);
        FileActorExtension springExtension = context.getBean(FileActorExtension.class);

        countActors = dataProvider.getListFile().size();
        LOGGER.info("Count actors: " + countActors);

        fileActor = system.actorOf(springExtension.props("filePerformActor").withRouter(new BalancingPool(countActors)));
    }
}
