package org.charlie.template.framework.utils.bean;

import org.springframework.beans.BeanUtils;


/**
 * @author Charlie
 * bean copy util for object convert among VO, BO, PO, DTO object.
 * for example:
 *
 * ...
 * FooBO fooBO = ...;
 * FooVO fooVO = FooVO.builder().build();
 * BeanUtil.copy(fooBO, fooVO);
 * ...
 *
 */
public class BeanUtil {
    public static void copy(Object source, Object target) {
        BeanUtils.copyProperties(source, target);
    }
}