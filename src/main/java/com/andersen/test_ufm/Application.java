package com.andersen.test_ufm;

import com.andersen.test_ufm.service.ProcessService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.mongo.MongoAutoConfiguration;

@Slf4j
@SpringBootApplication
@EnableAutoConfiguration(exclude = MongoAutoConfiguration.class)
public class Application implements CommandLineRunner {

    @Autowired
    private ProcessService processService;

    public static void main(String[] args) {
        log.debug("start!");
        SpringApplication.run(Application.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        processService.countSpentByClient(null);
    }
}
