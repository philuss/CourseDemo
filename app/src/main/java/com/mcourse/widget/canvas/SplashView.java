package com.mcourse.widget.canvas;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.LinearInterpolator;
import android.view.animation.OvershootInterpolator;

import com.mcourse.R;

/**
 * Created on 2019-4-4 15:31.
 * Describe: 如果有动画，可以将动画拆分成几段，然后分别实现每段,通过监听动画的执行实现连续执行多段动画
 */

public class SplashView extends View {

    private final String TAG = SplashView.class.getSimpleName();
    // 旋转圆画笔
    private Paint mPaint;
    // 扩散圆画笔
    private Paint mHolePaint;
    // 属性动画
    private ValueAnimator mAnimator;
    // 背景色
    private int mBackgroundColor = Color.WHITE;

    // 表示斜对角线长度的一半,扩散圆最大半径
    private float mDistance;

    // 小球颜色
    private int[] mBallColors;
    // 小球半径
    private float mBallRadius = 10;
    // 六小球旋转半径
    private float mRotateRadius = 90;
    // 小球旋转圆中心坐标
    private float mRotateCenterX, mRotateCenterY;

    // 六小球当前旋转角度
    private float mRotateCurrentAngle = 0;

    // 当前扩散圆的半径
    private float mRotateCurrentRadius = mRotateRadius;

    // 扩散圆当前半径
    private float mHoleCurrentRadius;

    // 旋转动画执行时间
    private int mRotateDuration = 1000;

    private Bitmap mBitmap;

    public SplashView(Context context) {
        this(context, null);
    }

    public SplashView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public SplashView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context) {
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);

        mHolePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mHolePaint.setStyle(Paint.Style.STROKE);
        mHolePaint.setColor(mBackgroundColor);

        mBallColors = context.getResources().getIntArray(R.array.splash_circle_colors);

        mBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.content);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mRotateCenterX = w * 1f / 2;
        mRotateCenterY = h * 1f / 2;
        mDistance = (float) (Math.hypot(w, h) / 2);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (mState == null) {
            drawBackground(canvas);
            drawBalls(canvas);
        } else {
            mState.drawState(canvas);
        }
    }

    private void startAnimation() {
        if (mState == null) {
            mState = new RotateState();
        }
        invalidate();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        Log.e(TAG, "onTouchEvent: " + event.getAction());
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            startAnimation();
        }
        return super.onTouchEvent(event);
    }

    // 绘制背景
    private void drawBackground(Canvas canvas) {
        if (mHoleCurrentRadius > 0) {
            canvas.drawBitmap(mBitmap, 0, 0, mHolePaint);
            // 绘制空心圆
            float strokeWidth = mDistance - mHoleCurrentRadius;
            float radius = strokeWidth / 2 + mHoleCurrentRadius;
            mHolePaint.setStrokeWidth(strokeWidth);
            canvas.drawCircle(mRotateCenterX, mRotateCenterY, radius, mHolePaint);
        } else {
            canvas.drawColor(mBackgroundColor);
        }
    }

    // 绘制六个小球
    private void drawBalls(Canvas canvas) {
        float rotateAngle = (float) (Math.PI * 2 / mBallColors.length);
        float cx, cy, angle;
        canvas.save();
        for (int i = 0, len = mBallColors.length; i < len; i++) {
            // x = r * cos(a) + centX;
            // y = r * sin(a) + centY;
            mPaint.setColor(mBallColors[i]);

            angle = i * rotateAngle + mRotateCurrentAngle;      //计算当前的角度
//            cx = (float) (mRotateCurrentRadius + mRotateCenterX); //计算当前小球的圆心x坐标
//            cy = (float) (mRotateCenterY); //计算当前小球的圆心y坐标
//            canvas.rotate(angle,mRotateCenterX,mRotateCenterY);
//            canvas.drawCircle(cx, cy, mBallRadius, mPaint);

            cx = (float) (mRotateCurrentRadius * Math.cos(angle) + mRotateCenterX); //计算当前小球的圆心x坐标
            cy = (float) (mRotateCurrentRadius * Math.sin(angle) + mRotateCenterY); //计算当前小球的圆心y坐标
            canvas.drawCircle(cx, cy, mBallRadius, mPaint);

        }
        canvas.restore();
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        if (mBitmap != null && !mBitmap.isRecycled()) {
            mBitmap.recycle();
            mBitmap = null;
        }
    }


    // 动画状态
    private SplashState mState;

    // 动画状态抽象类
    private abstract class SplashState {
        abstract void drawState(Canvas canvas);
    }

    // 旋转状态
    private class RotateState extends SplashState {

        private RotateState() {
            mHoleCurrentRadius = 0f;
            mRotateCurrentRadius = mRotateRadius;
            // 动画范围：0 - 2*PI
            mAnimator = ValueAnimator.ofFloat(0, (float) (Math.PI * 2));
            mAnimator.setDuration(mRotateDuration);
            mAnimator.setRepeatCount(1);
            mAnimator.setInterpolator(new LinearInterpolator());
            mAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                @Override
                public void onAnimationUpdate(ValueAnimator animation) {
                    mRotateCurrentAngle = (float) animation.getAnimatedValue();
                    invalidate();
                }
            });

            mAnimator.addListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    super.onAnimationEnd(animation);
                    mState = new MerginState();
                }
            });

            mAnimator.start();
        }

        @Override
        void drawState(Canvas canvas) {
            drawBackground(canvas);     //绘制背景
            drawBalls(canvas);          //绘制六个小球
        }
    }

    // 扩散状态
    private class MerginState extends SplashState {
        private MerginState() {
            // 动画范围：mBallRadius - mRotateRadius
            mAnimator = ValueAnimator.ofFloat(mRotateRadius,mBallRadius);
            mAnimator.setDuration(mRotateDuration);
            mAnimator.setInterpolator(new OvershootInterpolator(10f));
            mAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                @Override
                public void onAnimationUpdate(ValueAnimator animation) {
                    // 更新扩散半径
                    mRotateCurrentRadius = (float) animation.getAnimatedValue();
                    Log.e(TAG, "onAnimationUpdate: " + mRotateCurrentRadius);
                    invalidate();
                }
            });

            mAnimator.addListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    super.onAnimationEnd(animation);
                    mState = new ExpandState();
                }
            });
            mAnimator.start();
        }

        @Override
        void drawState(Canvas canvas) {
            drawBackground(canvas);
            drawBalls(canvas);
        }
    }

    // 水波纹
    private class ExpandState extends SplashState {
        private ExpandState() {
            // 动画范围：mBallRadius - mDistance
            mAnimator = ValueAnimator.ofFloat(mBallRadius, mDistance);
            mAnimator.setDuration(mRotateDuration);
            mAnimator.setInterpolator(new LinearInterpolator());
            mAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                @Override
                public void onAnimationUpdate(ValueAnimator animation) {
                    // 更新扩散半径
                    mHoleCurrentRadius = (float) animation.getAnimatedValue();
                    invalidate();
                }
            });

            mAnimator.start();
        }

        @Override
        void drawState(Canvas canvas) {
            drawBackground(canvas);
        }
    }

}
