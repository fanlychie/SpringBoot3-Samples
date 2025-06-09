package org.fanlychie.springboot3.part3;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Log4j2Application {

    private static final Logger log = LoggerFactory.getLogger(Log4j2Application.class);

    public static void main(String[] args) {
        log.info("Log4j2Application started!");
        SpringApplication.run(Log4j2Application.class, args);
    }

}
