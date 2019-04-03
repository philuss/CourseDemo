package com.mlib.rv.base;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created on 2019-4-3 14:23.
 * Describe: TODO
 */

public class RVViewHolder extends RecyclerView.ViewHolder {
    // 所有控件的集合  键：R.id.xxx   值：View 对象 比如：TextView  ImageView
    // 用于保存当前Item中所有的控件
    private SparseArray<View> mViews;

    public RVViewHolder(View itemView) {
        super(itemView);
        mViews = new SparseArray<>();
    }

    // 创建RViewHolder对象
    public static RVViewHolder createViewHolder(Context context, ViewGroup parent, int layoutId) {
        View itemView = LayoutInflater.from(context).inflate(layoutId, parent, false);
        return new RVViewHolder(itemView);
    }

    // 获取当前View
    public View getConvertView() {
        return itemView;
    }

    // 通过R.id.xxx获取某个控件
    public <T extends View> T getView(int viewId) {
        View view = mViews.get(viewId);
        if (view == null) {     // 如果缓存中没有就通过findViewById获取一个，并放入缓存
            view = itemView.findViewById(viewId);
            // 添加到缓存中。 键：R.id.xxx   值：TextView  ImageView
            mViews.put(viewId, view);
        }
        return (T) view;
    }

}

