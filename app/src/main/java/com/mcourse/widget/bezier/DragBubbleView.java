package com.mcourse.widget.bezier;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PointF;
import android.graphics.Rect;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import com.mcourse.R;

/**
 * Created by philus on 2019/4/9 23:35 .
 */

public class DragBubbleView extends View {
    private final String TAG = DragBubbleView.class.getSimpleName();

    private int mBubbleColor = Color.RED;   // 小球颜色
    private String mTextStr;       // 小球显示的文本
    private float mTextSize;    // 文字大小
    private int mTextColor;     // 文本颜色
    /**
     * 气泡半径
     */
    private float mBubbleRadius = 10;
    private float mBubFixedRadius;      //静止小球的半径
    private float mBubMovedRadius;       //移动小球的半径
    private PointF mBubFixedCenter;         // 静止小球的圆心坐标
    private PointF mBubMovedCenter;         //移动小球的圆心坐标

    /**
     * 气泡的画笔
     */
    private Paint mBubblePaint;
    /**
     * 贝塞尔曲线path
     */
    private Path mBezierPath;

    private Paint mTextPaint;           //绘制小球上的文本的画笔

    //文本绘制区域
    private Rect mTextRect;

    private Paint mBurstPaint;          //爆炸效果的画笔

    //爆炸绘制区域
    private Rect mBurstRect;

    /**
     * 两气泡圆心距离
     */
    private float mDist;
    /**
     * 气泡相连状态最大圆心距离
     */
    private float mMaxDist;
    /**
     * 手指触摸偏移量
     */
    private float MOVE_OFFSET;

    /**
     * 气泡爆炸的bitmap数组
     */
    private Bitmap[] mBurstBitmapsArray;
    /**
     * 是否在执行气泡爆炸动画
     */
    private boolean mIsBurstAnimStart = false;

    /**
     * 当前气泡爆炸图片index
     */
    private int mCurDrawableIndex;

    /**
     * 气泡爆炸的图片id数组
     */
    private int[] mBurstDrawablesArray = {R.mipmap.burst_1, R.mipmap.burst_2, R.mipmap.burst_3, R.mipmap.burst_4, R.mipmap.burst_5};

    private State mState = State.DEFAULT;       //当前状态

    private enum State {
        DEFAULT,
        CONNECT,
        APART,
        DISMISS
    }

    public DragBubbleView(Context context) {
        this(context, null);
    }

    public DragBubbleView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public DragBubbleView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs, defStyleAttr);
    }

    private void init(Context context, AttributeSet attrs, int defStyleAttr) {
        TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.DragBubbleView, defStyleAttr, 0);
        mBubbleRadius = array.getDimension(R.styleable.DragBubbleView_bubble_radius, mBubbleRadius);
        mBubbleColor = array.getColor(R.styleable.DragBubbleView_bubble_color, Color.RED);
        mTextStr = array.getString(R.styleable.DragBubbleView_bubble_text);
        mTextSize = array.getDimension(R.styleable.DragBubbleView_bubble_textSize, mTextSize);
        mTextColor = array.getColor(R.styleable.DragBubbleView_bubble_textColor, Color.WHITE);
        array.recycle();


        mBubbleRadius = 12;
        mBubbleColor = Color.RED;
        mTextStr = "9+";
//        mTextSize = 8;
        mTextColor = Color.WHITE;

        //两个圆半径大小一致
        mBubFixedRadius = mBubbleRadius;
        mBubMovedRadius = mBubFixedRadius;
        mMaxDist = 8 * mBubbleRadius;

        MOVE_OFFSET = mMaxDist / 4;

        //抗锯齿
        mBubblePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mBubblePaint.setColor(mBubbleColor);
        mBubblePaint.setStyle(Paint.Style.FILL);
        mBezierPath = new Path();

        //文本画笔
        mTextPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mTextPaint.setColor(mTextColor);
        mTextPaint.setTextSize(mTextSize);
        mTextRect = new Rect();

        //爆炸画笔
        mBurstPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mBurstPaint.setFilterBitmap(true);
        mBurstRect = new Rect();
        mBurstBitmapsArray = new Bitmap[mBurstDrawablesArray.length];
        for (int i = 0; i < mBurstDrawablesArray.length; i++) {
            //将气泡爆炸的drawable转为bitmap
            Bitmap bitmap = BitmapFactory.decodeResource(getResources(), mBurstDrawablesArray[i]);
            mBurstBitmapsArray[i] = bitmap;
        }
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        initCenter(w, h);
    }

    private void initCenter(int w, int h) {
        mState = State.DEFAULT;
        //设置固定气泡圆心初始坐标
        if (mBubFixedCenter == null) {
            mBubFixedCenter = new PointF(w / 2, h / 2);
        } else {
            mBubFixedCenter.set(w / 2, h / 2);
        }
        //设置可动气泡圆心初始坐标
        if (mBubMovedCenter == null) {
            mBubMovedCenter = new PointF(w / 2, h / 2);
        } else {
            mBubMovedCenter.set(w / 2, h / 2);
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        drawText(canvas);
        switch (mState) {
            case DEFAULT:
                canvas.drawCircle(mBubFixedCenter.x, mBubFixedCenter.y, mBubFixedRadius, mBubblePaint);
                break;
            case CONNECT:
                break;
            case APART:
                break;
            case DISMISS:
                break;
            default:
                break;
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                if (mState!=State.DISMISS){

                }
                break;
            case MotionEvent.ACTION_MOVE:
                break;
            case MotionEvent.ACTION_UP:
                break;
        }
        return super.onTouchEvent(event);
    }

    private void drawText(Canvas canvas) {
        if (mState != State.DISMISS) {
            mTextPaint.getTextBounds("9+", 0, mTextStr.length(), mTextRect);
            canvas.drawText("9+", mBubMovedCenter.x - mTextRect.width() / 2, mBubMovedCenter.y + mTextRect.height() / 2, mTextPaint);
        }
    }
}
