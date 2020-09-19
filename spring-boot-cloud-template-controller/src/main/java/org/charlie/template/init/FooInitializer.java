package org.charlie.template.init;


import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

/**
 * @author zhangchen
 */
@Component
@Slf4j
public class FooInitializer {

    @PostConstruct
    public void init() {
        log.info("Initialization.");
    }
}
