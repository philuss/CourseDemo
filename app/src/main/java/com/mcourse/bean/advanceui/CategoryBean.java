package com.mcourse.bean.advanceui;



import com.mlib.rv.base.IBaseBean;

import java.io.Serializable;

/**
 * Created on 2019-4-1 16:51.
 * Describe: TODO
 */

public class CategoryBean implements IBaseBean, Serializable {

    private int style;
    private String title;
    private String tips;

    @Override
    public int getItemStyle() {
        return style;
    }

    @Override
    public void setItemStyle(int style) {
        this.style = style;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTips() {
        return tips;
    }

    public void setTips(String tips) {
        this.tips = tips;
    }

    @Override
    public String toString() {
        return "CategoryBean{" +
                "style=" + style +
                ", title='" + title + '\'' +
                ", tips='" + tips + '\'' +
                '}';
    }
}
