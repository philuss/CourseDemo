package com.mlib.rv;

import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;

import com.mlib.rv.base.RVBaseAdapter;

/**
 * Created on 2019-4-3 14:20.
 * Describe: TODO
 */

public interface RVCreate {

    /**
     * 创建SwipeRefresh下拉
     */
    SwipeRefreshLayout createSwipeRefresh();

    /**
     * SwipeRefresh下拉颜色
     */
    int[] colorRes();

    /**
     * 创建RecycleView
     */
    RecyclerView createRecyclerView();

    /**
     * 创建RecycleView.Adapter
     */
    RVBaseAdapter createRecycleViewAdapter();

    /**
     * 创建RecycleView.LayoutManager 布局管理器
     */
    RecyclerView.LayoutManager createLayoutManager();

    /**
     * 创建RecycleView.ItemDecoration 分割线
     */
    RecyclerView.ItemDecoration createItemDecoration();

    /**
     * 创建RecycleView.ItemAnimator Item动画
     */
    RecyclerView.ItemAnimator createItemAnimator();

    /**
     * 开始页码
     */
    int startPageNumber();

    /**
     * 是否支持分页
     */
    boolean isSupportPaging();
}
