package org.charlie.template.framework.utils.bean;

import org.springframework.beans.BeanUtils;

import javax.validation.constraints.NotNull;


/**
 * bean copy util for object convert among VO, BO, PO, DTO object.
 * for example:
 * <p>
 * ...
 * FooBO fooBO = ...;
 * FooVO fooVO = FooVO.builder().build();
 * BeanUtil.copy(fooBO, fooVO);
 * ...
 *
 * @author Charlie
 */
public class BeanUtil {
    public static void copy(@NotNull Object source, @NotNull Object target) {
        BeanUtils.copyProperties(source, target);
    }
}