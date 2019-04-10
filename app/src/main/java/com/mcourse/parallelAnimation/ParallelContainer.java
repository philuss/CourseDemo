package com.mcourse.parallelAnimation;

import android.content.Context;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import com.mcourse.R;
import com.mcourse.activity.ParallelActivity;
import java.util.ArrayList;
import java.util.List;

public class ParallelContainer extends FrameLayout implements ViewPager.OnPageChangeListener {
    private final String TAG = ParallelContainer.class.getSimpleName();
    private List<ParallelFragment> fragments;
    private ParallelPagerAdapter adapter;

    public ParallelContainer(@NonNull Context context) {
        super(context);
    }

    private ImageView iv_man;

    public void setIv_man(ImageView iv_man) {
        this.iv_man = iv_man;
    }

    public ParallelContainer(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public void setUp(int... childIds) {
//        Fragment数组
        fragments = new ArrayList<ParallelFragment>();
        for (int i = 0; i < childIds.length; i++) {
            ParallelFragment f = new ParallelFragment();
            Bundle args = new Bundle();
            //Fragment中需要加载的布局文件id
            args.putInt("layoutId", childIds[i]);
            f.setArguments(args);
            fragments.add(f);
            Log.e(TAG, "setUp: " + childIds[i]);
        }

        ViewPager vp = new ViewPager(getContext());
        vp.setId(R.id.parallax_pager);
        //实例化适配器
        ParallelActivity activity = (ParallelActivity) getContext();
        adapter = new ParallelPagerAdapter(activity.getSupportFragmentManager(), fragments);
        vp.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
        vp.setAdapter(adapter);
        vp.setOnPageChangeListener(this);
        addView(vp, 0);
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
//        Log.e(TAG, "onPageScrolled: " + position);
//        动画
        int containerWidth = getWidth();
        ParallelFragment outFragment = null;

        try {
            outFragment = fragments.get(position - 1);
        } catch (Exception e) {
        }
        //获取到退出的页面
        ParallelFragment inFragment = null;
        try {
            inFragment = fragments.get(position);
        } catch (Exception e) {
        }

        if (outFragment != null) {
            //获取Fragment上所有的视图，实现动画效果
            List<View> inViews = outFragment.getParallelViews();
//            动画
            if (inViews != null) {
                for (View view : inViews) {
//
                    ParallelViewTag tag = (ParallelViewTag) view.getTag(R.id.parallax_view_tag);
                    if (tag == null) {
                        continue;
                    }
                    view.setTranslationX((containerWidth - positionOffsetPixels) * tag.xIn);
                    view.setTranslationY((containerWidth - positionOffsetPixels) * tag.yIn);
                }
            }
        }
        if (inFragment != null) {
            List<View> outViews = inFragment.getParallelViews();
            if (outViews != null) {
                for (View view : outViews) {
                    ParallelViewTag tag = (ParallelViewTag) view.getTag(R.id.parallax_view_tag);
                    if (tag == null) {
                        continue;
                    }
                    //仔细观察退出的fragment中view从原始位置开始向上移动，translationY应为负数
                    view.setTranslationX(0 - positionOffsetPixels * tag.xOut);
                    view.setTranslationY(0 - positionOffsetPixels * tag.yOut);
                }
            }
        }
    }


    @Override
    public void onPageSelected(int position) {
        if (position == adapter.getCount() - 1) {
            iv_man.setVisibility(INVISIBLE);
        } else {
            iv_man.setVisibility(VISIBLE);
        }
    }

    @Override
    public void onPageScrollStateChanged(int state) {
        AnimationDrawable animation = (AnimationDrawable) iv_man.getBackground();
        switch (state) {
            case ViewPager.SCROLL_STATE_DRAGGING:
                animation.start();
                break;
            case ViewPager.SCROLL_STATE_IDLE:
                animation.stop();
                break;
            default:
                break;
        }
    }
}
