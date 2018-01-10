package com.andersen.test_ufm;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.mongo.MongoAutoConfiguration;

@Slf4j
@SpringBootApplication
@EnableAutoConfiguration(exclude = MongoAutoConfiguration.class)
public class Application {

    public static void main(String[] args) {
        log.debug("start!");
    }
}
