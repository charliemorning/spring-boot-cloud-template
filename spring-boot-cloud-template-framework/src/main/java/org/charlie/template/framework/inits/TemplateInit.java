package org.charlie.template.framework.inits;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Slf4j
@Component
public class TemplateInit {

    @PostConstruct
    public void init() {
        // FIXME: add initialization here
        log.info("here is the initialization, fix me.");
    }
}
