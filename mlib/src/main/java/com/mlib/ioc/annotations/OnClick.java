package com.mlib.ioc.annotations;

import android.view.View;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Created on 2019-4-2 17:04.
 * Describe: 控件的点击事件
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@EventBase(listenerSetter = "setOnClickListener", listenerType = View.OnClickListener.class, callBackListener = "onClick")
public @interface OnClick {
    int[] value(); // 数组形式，多id多控件共用某点击方法
}
