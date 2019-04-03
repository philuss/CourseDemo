package com.mcourse.adapter;




import com.mcourse.bean.item.PaintItem;
import com.mlib.rv.base.IBaseBean;
import com.mlib.rv.base.RVBaseAdapter;

import java.util.List;

/**
 * Created on 2019-3-27 9:02.
 * Describe: TODO
 */

public class PaintAdapter extends RVBaseAdapter {

    // 高级UI
    public static final int STYLE_A = 1;
    public static final int STYLE_B = 2;
    public static final int STYLE_C = 3;
    public static final int STYLE_D = 4;
    public static final int STYLE_E = 5;

    public PaintAdapter(List<IBaseBean> dataList) {
        super(dataList);

        PaintItem item = new PaintItem(STYLE_A);
        addItemStyle(item);
//        AItem aItem = new AItem(STYLE_A);
////        aItem.setItemStyle(STYLE_A);
//        BItem bItem = new BItem(STYLE_B);
////        bItem.setItemStyle(STYLE_B);
//        CItem cItem = new CItem(STYLE_C);
////        cItem.setItemStyle(STYLE_C);
//        DItem dItem = new DItem(STYLE_D);
////        dItem.setItemStyle(STYLE_D);
//        EItem eItem = new EItem(STYLE_E);
////        eItem.setItemStyle(STYLE_E);
//
//        addItemStyle(aItem);
//        addItemStyle(bItem);
//        addItemStyle(cItem);
//        addItemStyle(dItem);
//        addItemStyle(eItem);


//        addItemStyle(STYLE_A, aItem);
//        addItemStyle(STYLE_B, bItem);
//        addItemStyle(STYLE_C, cItem);
//        addItemStyle(STYLE_D, dItem);
//        addItemStyle(STYLE_E, eItem);
    }


}
