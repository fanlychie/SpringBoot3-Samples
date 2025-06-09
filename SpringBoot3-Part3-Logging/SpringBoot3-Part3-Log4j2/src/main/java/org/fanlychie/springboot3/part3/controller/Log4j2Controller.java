package org.fanlychie.springboot3.part3.controller;

import org.fanlychie.springboot3.part3.service.Log4j2Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class Log4j2Controller {

    private static final Logger log = LoggerFactory.getLogger(Log4j2Controller.class);

    @Autowired
    private Log4j2Service log4j2Service;

    //输出应用名称
    @GetMapping("/")
    public String info() {
        String msg = log4j2Service.getMessage();
        log.debug("Log4j2Controller return {}", msg);
        return msg;
    }

}