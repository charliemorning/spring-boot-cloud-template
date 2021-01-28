package org.charlie.template.init;


import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

/**
 * @author Charlie
 */
@Component
@Slf4j
public class FooInitializer {

    @PostConstruct
    public void init() {
        log.info("Initialization.");
    }
}
