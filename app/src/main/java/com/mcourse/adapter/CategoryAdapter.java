package com.mcourse.adapter;



import com.mcourse.bean.item.CategoryItem;
import com.mlib.rv.base.IBaseBean;
import com.mlib.rv.base.RVBaseAdapter;

import java.util.List;

/**
 * Created on 2019-4-1 16:03.
 * Describe: TODO
 */

public class CategoryAdapter extends RVBaseAdapter {
    public static final int STYLE = 1;

    public CategoryAdapter(List<IBaseBean> dataList) {
        super(dataList);

        CategoryItem item = new CategoryItem(STYLE);
        addItemStyle(item);
    }
}
