package com.andersen.test_ufm.queue.actors;

import akka.actor.AbstractActor;
import akka.actor.Props;
import com.andersen.test_ufm.service.IProcessService;
import com.andersen.test_ufm.service.MultithreadSpentCalc;
import org.springframework.beans.factory.annotation.Autowired;
import akka.event.Logging;
import akka.event.LoggingAdapter;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class FilePerformActor extends AbstractActor {
    private final LoggingAdapter LOGGER = Logging.getLogger(getContext().getSystem(), this);

    @Autowired
    IProcessService processService;

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
        return receiveBuilder().match(String.class, this::process).build();
    }


    private void process(String messageFile) {
        LOGGER.info("Start process message file ...");
        processService.process(null);
        LOGGER.info("Finish process message file");
    }
}
