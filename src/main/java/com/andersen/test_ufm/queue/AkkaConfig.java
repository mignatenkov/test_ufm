package com.andersen.test_ufm.queue;

import akka.actor.ActorSystem;
import com.andersen.test_ufm.queue.extention.FileActorExtension;
import com.typesafe.config.Config;
import com.typesafe.config.ConfigFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;

@Configuration
@Lazy
public class AkkaConfig {
    @Autowired
    ApplicationContext applicationContext;

    @Autowired
    FileActorExtension fileActorExtension;

    @Bean
    public ActorSystem actorSystem() {
        ActorSystem system = ActorSystem.create("TEST_UFM", akkaConfiguration());
        fileActorExtension.initialize(applicationContext);
        return system;
    }

    /**
     * Загрузка конфигурации из файла application.conf
     */
    @Bean
    public Config akkaConfiguration() {
        return ConfigFactory.load();
    }
}
