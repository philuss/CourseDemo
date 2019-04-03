package com.mlib.rv.manager;

import android.support.v4.util.SparseArrayCompat;

import com.mlib.rv.base.IBaseBean;
import com.mlib.rv.base.IRVItemView;
import com.mlib.rv.base.RVViewHolder;

/**
 * Created on 2019-4-3 14:26.
 * Describe: TODO
 */

public class RVItemStyleManager {
    /**
     * 存放Item显示的样式类型 key：viewType  value：IRVItemView
     */
    private SparseArrayCompat<IRVItemView<? extends IBaseBean>> styles;

    public RVItemStyleManager() {
        styles = new SparseArrayCompat<>();
    }

    /**
     * 获取style类型的个数
     *
     * @return 类型个数
     */
    public int getItemStyleCount() {
        return styles == null ? 0 : styles.size();
    }

    /**
     * 添加类型
     *
     * @param viewType 类型
     * @param item     类型对应的RVViewItem
     */
    public void addStyle(int viewType, IRVItemView item) {
        if (item == null) {
            return;
        }
        styles.put(viewType, item);
    }


    /**
     * 根据显示的viewType返回IRVItem对象（获取value）
     *
     * @param viewType 布局类型
     * @return IRVItem对象
     */
    public IRVItemView getItemByStyle(int viewType) {
        if (!hasKey(viewType)) {
            throw new IllegalArgumentException("没有匹配的RViewItem类型");
        }
        return styles.get(viewType);
    }

    /**
     * 是否包含给定的显示类型
     *
     * @param viewType 给定的类型
     * @return true 包含
     */
    private boolean hasKey(int viewType) {
        for (int i = styles.size() - 1; i >= 0; i--) {
            if (viewType == styles.keyAt(i)) {
                return true;
            }
        }
        return false;
    }

//    /**
//     * 通过数据和位置获取显示style
//     *
//     * @param entity   数据
//     * @param position 位置
//     * @return 有的话返回样式，否则抛出异常
//     */
//    public int getItemStyle(T entity, int position) {
//        // 样式倒序循环，避免增删集合抛出异常
//        for (int i = styles.size() - 1; i >= 0; i--) {
//            // 比如第1个位置（索引0），第1类条目样式
//            IRVItemView item = styles.valueAt(i);
//            // 是否为当前样式显示，由外面实现
//            if (item.isItemView(entity, position)) {
//                // 获得集合key，viewType
//                return styles.keyAt(i);
//            }
//        }
//        throw new IllegalArgumentException("位置：" + position + "，该item没有匹配的RViewItem类型");
//    }
//
//    /**
//     * 视图与数据源绑定显示
//     *
//     * @param holder   ViewHolder对象
//     * @param entity   数据
//     * @param position 位置
//     */
//    public void convert(RVViewHolder holder, T entity, int position) {
//        // 遍历styles 找到对应的item类型
//        for (int i = 0; i < styles.size(); i++) {
//            IRVItemView item = styles.valueAt(i);
//            if (item.isItemView(entity, position)) {
//                // 视图与数据源绑定显示，由外面实现（参数赋值，外面用）
//                item.convert(holder, entity, position);
//                return;
//            }
//        }
//        throw new IllegalArgumentException("位置：" + position + "，该item没有匹配的RViewItem类型");
//    }

    /**
     * 通过数据和位置获取显示style
     *
     * @param entity   数据
     * @param position 位置
     * @param <T>      泛型参数
     * @return 有的话返回样式，否则抛出异常
     */
    public <T extends IBaseBean> int getItemStyle(T entity, int position) {
        // 样式倒序循环，避免增删集合抛出异常
        for (int i = styles.size() - 1; i >= 0; i--) {
            // 比如第1个位置（索引0），第1类条目样式
            IRVItemView item = styles.valueAt(i);

            // 是否为当前样式显示，由外面实现
//            boolean isSameStyle = item.isItemView(entity.getItemStyle(), position);
//            Log.e("======> Manager", "getItemStyle: " + isSameStyle);
//            if (isSameStyle) {
            if (item.isItemView(entity.getItemStyle(), position)) {
                // 获得集合key，viewType
                return styles.keyAt(i);
            }
        }
        throw new IllegalArgumentException("位置：" + position + "，该item没有匹配的RViewItem类型");
    }

    /**
     * 视图与数据源绑定显示
     *
     * @param holder   ViewHolder对象
     * @param entity   数据
     * @param position 位置
     * @param <T>
     */
    public <T extends IBaseBean> void convert(RVViewHolder holder, T entity, int position) {
        // 遍历styles 找到对应的item类型
        for (int i = 0; i < styles.size(); i++) {
            IRVItemView item = styles.valueAt(i);
            if (item.isItemView(entity.getItemStyle(), position)) {
                // 视图与数据源绑定显示，由外面实现（参数赋值，外面用）
                item.convert(holder, entity, position);
                return;
            }
        }
        throw new IllegalArgumentException("位置：" + position + "，该item没有匹配的RViewItem类型");
    }

}
