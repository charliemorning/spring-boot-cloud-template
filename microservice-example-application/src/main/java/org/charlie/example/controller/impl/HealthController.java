package org.charlie.example.controller.impl;


import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@Slf4j
@RestController
@RequestMapping("/health")
public class HealthController {

    @GetMapping("")
    public void health() {
        log.info("health check.");
    }
}
