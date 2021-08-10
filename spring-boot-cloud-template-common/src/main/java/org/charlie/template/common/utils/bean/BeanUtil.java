package org.charlie.template.common.utils.bean;

import org.springframework.beans.BeanUtils;

public class BeanUtil {
    public static void copy(Object source, Object target) {
        BeanUtils.copyProperties(source, target);
    }
}