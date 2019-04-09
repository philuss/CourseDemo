package com.mcourse.widget.bezier;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by philus on 2019/4/9 23:35 .
 */

public class DragBubbleView extends View {
    private final String TAG = DragBubbleView.class.getSimpleName();
    private Paint mPaint;       //绘制小球的画笔
    private Paint mTextPaint;   //绘制小球上的文本的画笔

    private float mRadius;      // 小球半径
    private float mDistance;    // 小球拖拽的最远距离（超过这个距离就会离开原来的位置）
    private int mBubbleColor;   // 小球颜色
    private String mText;   // 小球显示的文本
    private int mTextColor;     // 文本颜色
    private float mBubbleStaticRadius;      //静止小球的半径
    private float mBubbleMovedRadius;       //移动小球的半径


    private State mState = State.DEFAULT;       //当前状态

    public DragBubbleView(Context context) {
        this(context,null);
    }

    public DragBubbleView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,0);
    }

    public DragBubbleView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        mPaint = new Paint();
        mPaint.setColor(Color.RED);
        mPaint.setStyle(Paint.Style.FILL);
        mPaint.setAntiAlias(true);
    }

    private enum State{
        DEFAULT,
        CONNECT,
        APART,
        DISMISS
    }



}
