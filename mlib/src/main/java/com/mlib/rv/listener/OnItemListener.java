package com.mlib.rv.listener;

import android.view.View;

/**
 * Created on 2019-4-3 14:27.
 * Describe: TODO
 */

public interface OnItemListener<T> {
    /**
     * item点击事件监听
     */
    void onItemClick(View view, T entity, int position);

    /**
     * item长按事件监听
     */
    boolean onItemLongClick(View view, T entity, int position);
}
