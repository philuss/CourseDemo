package com.mlib.ioc.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Created on 2019-4-2 17:03.
 * Describe: 控件Id，用于替代findViewById()
 */
@Target(ElementType.FIELD)  // 该注解作用于属性上
@Retention(RetentionPolicy.RUNTIME)
public @interface InjectView {
    int value();
}
