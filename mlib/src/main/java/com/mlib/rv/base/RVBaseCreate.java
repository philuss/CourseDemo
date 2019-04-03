package com.mlib.rv.base;

import android.support.v7.widget.RecyclerView;

import com.mlib.rv.RVCreate;
import com.mlib.rv.SwipeRefreshHelper;

/**
 * Created on 2019-4-3 14:25.
 * Describe: TODO
 */

public abstract class RVBaseCreate implements RVCreate, SwipeRefreshHelper.SwipeRefreshListener {
//    @Override
//    public SwipeRefreshLayout createSwipeRefresh() {
//        return null;
//    }

    @Override
    public int[] colorRes() {
        return new int[0];
    }

//    @Override
//    public RecyclerView createRecyclerView() {
//        return null;
//    }
//
//    @Override
//    public RVBaseAdapter createRecycleViewAdapter() {
//        return null;
//    }
//
//    @Override
//    public RecyclerView.LayoutManager createLayoutManager() {
//        return null;
//    }

    @Override
    public RecyclerView.ItemDecoration createItemDecoration() {
        return null;
    }

    @Override
    public RecyclerView.ItemAnimator createItemAnimator() {
        return null;
    }

    @Override
    public int startPageNumber() {
        return 0;
    }

    @Override
    public boolean isSupportPaging() {
        return false;
    }

    @Override
    public void onRefresh() {

    }
}
