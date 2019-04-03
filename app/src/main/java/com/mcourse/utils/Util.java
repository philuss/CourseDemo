package com.mcourse.utils;

/**
 * Created by YUANZQ2 on 2019-3-21.
 * Date: 2019-3-21 15:37
 * Describe: TODO
 */

public class Util {
    /**
     * 检查对象是否为null
     *
     * @param object 给定对象
     * @param <T>    对象类型
     * @return 不为null，原样返回
     */
    @SuppressWarnings("ConstantConditions")
    public static <T> T checkNotNull(final T object) {
        if (object == null) {
            throw new NullPointerException();
        }
        return object;
    }

}
