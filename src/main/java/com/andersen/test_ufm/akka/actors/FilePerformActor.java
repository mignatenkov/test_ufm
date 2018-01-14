package com.andersen.test_ufm.akka.actors;

import akka.actor.AbstractActor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.io.*;

/**
 * Класс-актор для обработки файла. Для каждого файла будет создаваться свой класс-актор
 */
@Slf4j
@Component
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class FilePerformActor extends AbstractActor {

    @Autowired
    private ActorUtil actorUtil;

    @Override
    public void preStart() throws Exception {
        super.preStart();
        log.info("Custom actor started");
    }

    @Override
    public void postStop() throws Exception {
        log.info("Custom actor stopped");
        super.postStop();
    }

    @Override
    public Receive createReceive() {
        return receiveBuilder().match(File.class, this::process).build();
    }

    private void process(File inputFile) {
        actorUtil.process(inputFile);
    }

}
