package com.mcourse.bean.advanceui;


import com.mlib.rv.base.IBaseBean;

import java.io.Serializable;

/**
 * Created on 2019-4-1 15:31.
 * Describe: TODO
 */

public class PaintBean implements IBaseBean, Serializable {

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
        return "PaintBean{" +
                "style=" + style +
                ", title='" + title + '\'' +
                ", tips='" + tips + '\'' +
                '}';
    }
}
