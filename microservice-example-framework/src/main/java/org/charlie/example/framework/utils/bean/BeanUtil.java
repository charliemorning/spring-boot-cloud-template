package org.charlie.example.framework.utils.bean;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

import javax.validation.constraints.NotNull;


/**
 * To fetch bean from spring context.
 *
 *
 * @author Charlie
 */
@Slf4j
public class BeanUtil implements ApplicationContextAware {

    private static ApplicationContext applicationContext = null;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        if (BeanUtil.applicationContext == null) {
            BeanUtil.applicationContext = applicationContext;
            log.debug(String.format("Application context has been set: %s", String.valueOf(BeanUtil.applicationContext)));
        }
    }

    public static ApplicationContext getApplicationContext() {
        return applicationContext;
    }

    public static Object getBean(String name) {
        return getApplicationContext().getBean(name);
    }

    public static <T> T getBean(Class<T> clazz) {
        log.debug(String.valueOf(clazz));
        log.debug(String.valueOf(getApplicationContext()));
        return getApplicationContext().getBean(clazz);
    }

    public static <T> T getBean(String name, Class<T> clazz) {
        return getApplicationContext().getBean(name, clazz);
    }

    /**
     * To fetch bean from spring context.
     * bean copy util for object convert among VO, BO, PO, DTO object.
     * for example:
     * <p>
     * ...
     * FooBO fooBO = ...;
     * FooVO fooVO = FooVO.builder().build();
     * BeanUtil.copy(fooBO, fooVO);
     * ...
     *
     */
    public static void copy(@NotNull Object source, @NotNull Object target) {
        BeanUtils.copyProperties(source, target);
    }
}