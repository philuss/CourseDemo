package com.mlib.ioc.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Created on 2019-4-2 17:07.
 * Describe: 控件事件封装
 */
@Target(ElementType.ANNOTATION_TYPE) // 该注解作用于注解的上面
@Retention(RetentionPolicy.RUNTIME)
public @interface EventBase {
    // 事件的三个成员
    // 1、set方法名
    String listenerSetter();

    // 2、监听的对象
    Class<?> listenerType();

    // 3、回调方法
    String callBackListener();
}
