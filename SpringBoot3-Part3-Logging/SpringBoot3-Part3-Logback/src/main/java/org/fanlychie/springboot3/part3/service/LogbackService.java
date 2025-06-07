package org.fanlychie.springboot3.part3.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class LogbackService {

    //映射属性值到Bean字段中
    @Value("${spring.application.name}")
    private String applicationName;

    private static final Logger log = LoggerFactory.getLogger(LogbackService.class);

    public String getMessage() {
        String msg = "Hello " + applicationName + "!";
        log.info("LogbackService.getMessage() return {}", msg);
        return msg;
    }

}