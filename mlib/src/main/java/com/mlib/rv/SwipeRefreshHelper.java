package com.mlib.rv;

import android.support.annotation.ColorRes;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;

/**
 * Created on 2019-4-3 14:20.
 * Describe: TODO
 */

public class SwipeRefreshHelper {

    private SwipeRefreshLayout swipeRefresh;
    private SwipeRefreshListener swipeRefreshListener;

    static SwipeRefreshHelper createSwipeRefreshHelper(SwipeRefreshLayout swipeRefresh, @ColorRes int... colorResIds) {
        return new SwipeRefreshHelper(swipeRefresh, colorResIds);
    }

    private SwipeRefreshHelper(@Nullable SwipeRefreshLayout swipeRefresh, @ColorRes int... colorResIds) {
        this.swipeRefresh = swipeRefresh;
        init(colorResIds);
    }

    private void init(@ColorRes int... colorResIds) {
        if (colorResIds == null || colorResIds.length == 0) {       //使用默认的颜色数组
            swipeRefresh.setColorSchemeResources(android.R.color.holo_orange_dark,
                    android.R.color.holo_green_dark, android.R.color.holo_blue_dark);
        } else {
            swipeRefresh.setColorSchemeResources(colorResIds);
        }
        // 设置下拉刷新监听
        swipeRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {   // 下拉刷新
                if (swipeRefreshListener != null){
                    swipeRefreshListener.onRefresh();
                }

            }
        });
    }

    public interface SwipeRefreshListener {
        void onRefresh();
    }

    public void setSwipeRefreshListener(SwipeRefreshListener swipeRefreshListener) {
        this.swipeRefreshListener = swipeRefreshListener;
    }

}
