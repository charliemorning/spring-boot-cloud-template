package org.charlie.template.common.utility.bean;

import org.springframework.beans.BeanUtils;

public class BeanUtility {
    public static void copy(Object source, Object target) {
        BeanUtils.copyProperties(source, target);
    }

    public static <T> T copyFrom(Object source) {
        Object target = new Object();
        BeanUtils.copyProperties(source, target);
        return (T)target;
    }
}