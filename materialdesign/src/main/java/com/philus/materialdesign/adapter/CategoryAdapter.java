package com.philus.materialdesign.adapter;

import com.mlib.rv.base.IBaseBean;
import com.mlib.rv.base.RVBaseAdapter;
import com.philus.materialdesign.bean.item.CategoryItem;

import java.util.List;

/**
 * Created by philus on 2019/4/17 11:32 .
 */

public class CategoryAdapter extends RVBaseAdapter {
    public static final int STYLE = 1;

    public CategoryAdapter(List<IBaseBean> dataList) {
        super(dataList);

        CategoryItem item = new CategoryItem(STYLE);
        addItemStyle(item);
    }
}
