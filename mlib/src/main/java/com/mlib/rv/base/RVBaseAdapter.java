package com.mlib.rv.base;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;

import com.mlib.rv.listener.OnItemListener;
import com.mlib.rv.manager.RVItemStyleManager;

import java.util.ArrayList;
import java.util.List;

/**
 * Created on 2019-4-3 14:22.
 * Describe: TODO
 */

public class RVBaseAdapter extends RecyclerView.Adapter<RVViewHolder> {

    private List<IBaseBean> dataList;               // 数据列表
    private RVItemStyleManager itemStyles;          // Item显示的样式管理
    private OnItemListener<IBaseBean> itemListener;      // item条目点击监听

    // 阻塞事件 500ms
    private final static long QUICK_EVENT_TIME_SPAN = 500;
    //上一次的点击事件
    private long lastClickTime;

    public RVBaseAdapter(List<IBaseBean> dataList) {
        this.dataList = dataList == null ? new ArrayList<IBaseBean>() : dataList;
        itemStyles = new RVItemStyleManager();
    }

    @Override
    public RVViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        IRVItemView item = itemStyles.getItemByStyle(viewType);
        RVViewHolder holder = RVViewHolder.createViewHolder(parent.getContext(), parent, item.getItemLayout());
        // 点击监听
        if (item.canClicked()) {
            setListener(holder);
        }
        return holder;
    }

    @Override
    public void onBindViewHolder(RVViewHolder holder, int position) {
        convert(holder, dataList.get(position));
    }

    @Override
    public int getItemCount() {
        return dataList == null ? 0 : dataList.size();
    }

    @Override
    public int getItemViewType(int position) {
        if (hasMultiStyle()) {       //UI布局是多样式，从样式管理里面获取布局样式类型
            return itemStyles.getItemStyle(dataList.get(position), position);
        }
        return super.getItemViewType(position);
    }

    private void setListener(final RVViewHolder holder) {
        holder.getConvertView().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (itemListener != null) {
                    int position = holder.getAdapterPosition();

                    // 阻塞事件添加 ---
                    long timeSpan = System.currentTimeMillis() - lastClickTime;
                    if (timeSpan < QUICK_EVENT_TIME_SPAN) {
                        Log.e(">>>>>> ", "点击阻塞，防止误点: " + timeSpan);
                        return;
                    }
                    lastClickTime = System.currentTimeMillis();

                    itemListener.onItemClick(v, dataList.get(position), position);
                }
            }
        });

        holder.getConvertView().setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                if (itemListener != null) {
                    int position = holder.getAdapterPosition();
                    itemListener.onItemLongClick(v, dataList.get(position), position);
                }
                return false;           // 返回false才会执行onClick回调。
            }
        });
    }

    // 将数据和布局绑定
    private void convert(RVViewHolder holder, IBaseBean entity) {
        itemStyles.convert(holder, entity, holder.getAdapterPosition());
    }

    /**
     * 添加数据集
     *
     * @param datas 数据集
     */
    public void addData(List<IBaseBean> datas) {
        if (datas == null) {
            return;
        }
        this.dataList.addAll(datas);
        notifyDataSetChanged();
    }

    /**
     * 添加单一数据
     *
     * @param data 数据
     */
    public void addData(IBaseBean data) {
        if (data == null) {
            return;
        }
        this.dataList.add(data);
        notifyDataSetChanged();
    }

    /**
     * 更新数据
     *
     * @param datas 数据集
     */
    public void updateData(List<IBaseBean> datas) {
        if (datas == null) {
            return;
        }
        this.dataList = datas;
        notifyDataSetChanged();
    }

    /**
     * UI布局是否是多样式
     *
     * @return true 是
     */
    private boolean hasMultiStyle() {
        return itemStyles.getItemStyleCount() > 0;
    }

    /**
     * 添加item显示样式
     *
     * @param viewType 样式
     * @param item     IRVItem对象
     */
    public void addItemStyle(int viewType, IRVItemView item) {
        itemStyles.addStyle(viewType, item);
    }

    /**
     * 添加item显示样式
     *
     * @param item IRVItem对象
     */
    public void addItemStyle(IRVItemView item) {
        itemStyles.addStyle(item.getItemStyle(), item);
    }

    /**
     * 设置Item点击监听
     *
     * @param listener 监听回调
     */
    public void setOnItemListener(OnItemListener listener) {
        this.itemListener = listener;
    }

}
