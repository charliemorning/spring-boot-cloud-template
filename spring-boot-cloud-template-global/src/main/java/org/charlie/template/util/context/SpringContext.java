package org.charlie.template.util.context;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;


/**
 * @author zhangchen
 * @version 0.1.0
 */
@Component
public class SpringContext implements ApplicationContextAware {

    final static Logger LOGGER = LoggerFactory.getLogger(SpringContext.class);

    private static ApplicationContext applicationContext = null;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        if(SpringContext.applicationContext == null){
            SpringContext.applicationContext  = applicationContext;
            LOGGER.debug("Application context has been set: %s" + String.valueOf(SpringContext.applicationContext));
        }
    }

    public static ApplicationContext getApplicationContext() {
        return applicationContext;
    }

    public static Object getBean(String name){
        return getApplicationContext().getBean(name);

    }

    public static <T> T getBean(Class<T> clazz){
        LOGGER.debug(String.valueOf(clazz));
        LOGGER.debug(String.valueOf(getApplicationContext()));
        return getApplicationContext().getBean(clazz);
    }

    public static <T> T getBean(String name,Class<T> clazz){
        return getApplicationContext().getBean(name, clazz);
    }
}