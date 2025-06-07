package org.fanlychie.springboot3.part3;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.concurrent.TimeUnit;
@SpringBootApplication
public class LogbackApplication {

    private static final Logger log = LoggerFactory.getLogger(LogbackApplication.class);

    public static void main(String[] args) {
        log.info("LogbackApplication started!");
        SpringApplication.run(LogbackApplication.class, args);
    }

}
