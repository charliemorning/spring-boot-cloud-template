package org.charlie.template.init;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Slf4j
@Component
public class TemplateInit {

    @PostConstruct
    public void init() {
        log.info("here is the initialization");
    }
}
