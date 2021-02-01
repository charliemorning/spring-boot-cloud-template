package org.charlie.template.utilities;

import org.springframework.beans.BeanUtils;
import org.springframework.cglib.beans.BeanCopier;

public class BeanUtility {


    /*public static <T , S> void copy(T source, S target) {
        BeanCopier beanCopier = BeanCopier.create(T, S.class, false);
        beanCopier.copy(source, target, null);
    }*/

    public static void copy(Object source, Object target) {
        BeanUtils.copyProperties(source, target);
    }

}
