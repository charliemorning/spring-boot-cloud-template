package org.charlie.template.framework.inits;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;


/**
 * To define initialization.
 * <p>
 * Delete this class if it is not necessary.
 *
 * @author Charlie
 */
@Slf4j
@Component
public class TemplateInit {

    @PostConstruct
    public void init() {
        // FIXME: add initialization here
        log.debug("here is the initialization, fix me.");
    }
}
