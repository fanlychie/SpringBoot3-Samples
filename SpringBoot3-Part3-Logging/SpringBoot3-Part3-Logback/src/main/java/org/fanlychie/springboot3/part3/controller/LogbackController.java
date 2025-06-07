package org.fanlychie.springboot3.part3.controller;

import org.fanlychie.springboot3.part3.service.LogbackService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LogbackController {

    private static final Logger log = LoggerFactory.getLogger(LogbackController.class);

    @Autowired
    private LogbackService logbackService;

    //输出应用名称
    @GetMapping("/")
    public String info() {
        String msg = logbackService.getMessage();
        log.debug("LogbackController return {}", msg);
        return msg;
    }

}