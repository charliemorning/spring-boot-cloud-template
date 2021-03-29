package org.charlie.template.controller;

import lombok.extern.slf4j.Slf4j;
import org.charlie.template.service.FooService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@Slf4j
@RestController
@RequestMapping(value = "/foo")
public class FooController {

    FooService fooService;

    @Resource(name = "fooServiceImpl")
    public void setFooService(FooService fooService) {
        this.fooService = fooService;
    }

    @GetMapping("/greeting")
    public void foo() {
        log.info(String.valueOf(fooService.queryFoos()));
    }


}
