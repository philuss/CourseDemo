package com.mlib.rv.base;

/**
 * Created on 2019-4-3 14:24.
 * Describe: TODO
 */

public interface IRVItemView<T> {

    /**
     * 设置Item显示样式
     *
     * @param style
     */
    void setItemStyle(int style);

    /**
     * 获取Item显示样式
     *
     * @return 样式
     */
    int getItemStyle();

    /**
     * 获取item布局
     *
     * @return
     */
    int getItemLayout();

    /**
     * 是否可以点击
     *
     * @return true 可以
     */
    boolean canClicked();

    /**
     * 是否为当前item布局
     *
     * @param style    类型
     * @param position 位置
     * @return true 是当前布局
     */
    boolean isItemView(int style, int position);

    /**
     * 将item和需要显示的数据绑定
     *
     * @param holder   holder
     * @param entity   数据
     * @param position 位置
     */
    void convert(RVViewHolder holder, T entity, int position);
}