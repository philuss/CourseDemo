package com.mcourse.bean.item;

import android.widget.TextView;

import com.mcourse.R;
import com.mcourse.bean.advanceui.CategoryBean;
import com.mlib.rv.base.IRVItemView;
import com.mlib.rv.base.RVViewHolder;


/**
 * Created on 2019-4-1 16:50.
 * Describe: TODO
 */

public class CategoryItem implements IRVItemView<CategoryBean> {
    private int style;

    public CategoryItem(int style) {
        this.style = style;
    }

    @Override
    public void setItemStyle(int style) {
        this.style = style;
    }

    @Override
    public int getItemStyle() {
        return style;
    }

    @Override
    public int getItemLayout() {
        return R.layout.item_category;
    }

    @Override
    public boolean canClicked() {
        return true;
    }

    @Override
    public boolean isItemView(int style, int position) {
        return this.style == style;
    }

    @Override
    public void convert(RVViewHolder holder, CategoryBean entity, int position) {
        TextView tv_title = holder.getView(R.id.tv_title);
        TextView tv_tips = holder.getView(R.id.tv_tips);
        tv_title.setText(entity.getTitle());
        tv_tips.setText(entity.getTips());
    }
}
