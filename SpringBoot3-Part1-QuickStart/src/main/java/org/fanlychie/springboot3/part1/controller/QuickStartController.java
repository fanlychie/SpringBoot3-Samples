package org.fanlychie.springboot3.part1.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class QuickStartController {

    @GetMapping("/")
    public String index() {
        return "Hello 世界！";
    }

}