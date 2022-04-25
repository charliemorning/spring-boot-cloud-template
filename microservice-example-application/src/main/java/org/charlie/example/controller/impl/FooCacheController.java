package org.charlie.example.controller.impl;

import org.charlie.example.service.api.FooService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@RequestMapping("/api/v2/foo")
public class FooCacheController {
    FooService fooServiceWithLocalCache;

    @Resource(name = "fooServiceWithLocalCache")
    public void setFooServiceWithLocalCache(FooService fooServiceWithLocalCache) {
        this.fooServiceWithLocalCache = fooServiceWithLocalCache;
    }
}
