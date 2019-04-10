package com.mcourse.parallelAnimation;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;

import com.mcourse.R;

/**
 * Created on 2019-4-10 9:02.
 * Describe: TODO
 */

public class ParallelLayoutInflater extends LayoutInflater {
    private static final String TAG = "ParallelLayoutInflater";
    private ParallelFragment fragment;

    protected ParallelLayoutInflater(Context context) {
        super(context);
    }

    protected ParallelLayoutInflater(LayoutInflater original, Context newContext) {
        super(original, newContext);
    }

    protected ParallelLayoutInflater(LayoutInflater original, Context newContext, ParallelFragment fragment) {
        super(original, newContext);
        this.fragment = fragment;
        setFactory2(new ParallelFactory(this));
    }

    @Override
    public LayoutInflater cloneInContext(Context newContext) {
        return new ParallelLayoutInflater(this, newContext, fragment);
    }

    class ParallelFactory implements Factory2 {
        private final String[] sClassPrefix = {
                "android.widget.",
                "android.view."
        };
        int[] attrIds = {
                R.attr.a_in,
                R.attr.a_out,
                R.attr.x_in,
                R.attr.x_out,
                R.attr.y_in,
                R.attr.y_out};

        private LayoutInflater inflater;
        public ParallelFactory(LayoutInflater inflater) {
            this.inflater = inflater;
        }

        @Override
        public View onCreateView(View parent, String name, Context context, AttributeSet attrs) {
            View view = null;
            view = createMyView(name, context, attrs);
            if (view != null) {
                TypedArray a = context.obtainStyledAttributes(attrs, attrIds);
                if (a != null && a.length() > 0) {
                    //获取自定义属性
                    ParallelViewTag tag = new ParallelViewTag();
                    tag.alphaIn = a.getFloat(0, 0f);
                    tag.alphaOut = a.getFloat(1, 0f);
                    tag.xIn = a.getFloat(2, 0f);
                    tag.xOut = a.getFloat(3, 0f);
                    tag.yIn = a.getFloat(4, 0f);
                    tag.yOut = a.getFloat(5, 0f);
                    view.setTag(R.id.parallax_view_tag, tag);
                    a.recycle();
                }
                fragment.getParallelViews().add(view);
            }
//            Log.e(TAG, "onCreateView: " + view);
            return view;
        }

        private View createMyView(String name, Context context, AttributeSet attrs) {
            if (name.contains(".")) {       //自定义控件
                return reflectView(name, null, context, attrs);
            } else {        //系统控件
                for (String prefix : sClassPrefix) {
                    View view = reflectView(name, prefix, context, attrs);
                    // 获取系统空间的自定义属性
                    if (view != null) {
                        return view;
                    }
                }
            }
            return null;
        }

        private View reflectView(String name, String prefix, Context context,
                                 AttributeSet attrs) {
            try {
                //通过统的inflater创建视图，读取系统的属性
                return inflater.createView(name, prefix, attrs);
            } catch (Exception e) {
                return null;
            }
        }

        @Override
        public View onCreateView(String name, Context context, AttributeSet attrs) {
            return null;
        }
    }
}
