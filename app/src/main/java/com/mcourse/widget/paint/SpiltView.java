package com.mcourse.widget.paint;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.animation.LinearInterpolator;
import android.widget.Toast;

import com.mcourse.R;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;

/**
 * Created by arvinljw on 2019/4/2 16:14
 * Function：
 * Desc：
 */
public class SpiltView extends View implements View.OnClickListener {
    private Paint paint;
    private Bitmap bitmap;
    private List<Ball> balls;
    private int len;
    private boolean isInitSuccess;
    private boolean startAnim;

    private ValueAnimator animator;

    public SpiltView(Context context) {
        this(context, null);
    }

    public SpiltView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public SpiltView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    protected void init() {
        len = 10;
        paint = new Paint();
        balls = new ArrayList<>();
        bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.pic);

        Executors.newCachedThreadPool().execute(new Runnable() {
            @Override
            public void run() {
                calculateBalls();
                isInitSuccess = true;
            }
        });

        animator = ValueAnimator.ofFloat(0, 1);
        animator.setDuration(2000);
        animator.setRepeatCount(ValueAnimator.INFINITE);
        animator.setInterpolator(new LinearInterpolator());
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                Log.d("value", animation.getAnimatedValue().toString());
                boolean allOut = updateBall();
                invalidate();
                if (allOut) {
                    animation.cancel();
                }
            }
        });

        setClickable(true);
        setOnClickListener(this);
    }

    private void calculateBalls() {
        int width = bitmap.getWidth();
        int height = bitmap.getHeight();

        for (int i = 0; i < width; i += len) {
            for (int j = 0; j < height; j += len) {
                Ball ball = new Ball();

                int realWidth = len;
                int realHeight = len;
                if (i + realWidth > width) {
                    realWidth = width - i;
                }
                if (j + realHeight > height) {
                    realHeight = height - j;
                }
                int[] colors = new int[realWidth * realHeight];
                bitmap.getPixels(colors, 0, realWidth, i, j, realWidth, realHeight);

                int color = getColor(colors);
                ball.color = color;
                int d = 3;
                ball.x = ((float) (i + len) * d / 2 + (float) d / 2);
                ball.y = ((float) (j + len) * d / 2 + (float) d / 2);
                ball.r = ((float) d / 2);

                //x方向速度20或者-20
                ball.vX = ((float) (Math.pow(-1, Math.ceil(Math.random() * 1000)) * 20 * Math.random()));
                ball.vY = (rangeInt(-20, 20));
                ball.aX = (0);
                ball.aY = (0.98f);
                balls.add(ball);
            }
        }
    }

    private int getColor(int[] colors) {
        int ar = 0, ag = 0, ab = 0;
        for (int color : colors) {
            int r = (color & 0xff0000) >> 16;
            int g = (color & 0x00ff00) >> 8;
            int b = color & 0x0000ff;

            ar += r;
            ag += g;
            ab += b;
        }
        return Color.rgb(ar / colors.length, ag / colors.length, ab / colors.length);
    }

    private float rangeInt(int i, int j) {
        int max = Math.max(i, j);
        int min = Math.min(i, j) - 1;
        //在0到(max - min)范围内变化，取大于x的最小整数 再随机
        return (int) (min + Math.ceil(Math.random() * (max - min)));
    }

    private boolean updateBall() {
        boolean allOut = true;
        for (Ball ball : balls) {
            ball.x = (ball.x + ball.vX);
            ball.y = (ball.y + ball.vY);
            ball.vX = (ball.vX + ball.aX);
            ball.vY = (ball.vY + ball.aY);
            if (ball.x >= 0 && ball.x <= getWidth() && ball.y >= 0 && ball.y <= getHeight()) {
                allOut = false;
            }
        }
        return allOut;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.translate(getWidth() / 2 - bitmap.getWidth() / 2, 400);

        if (!startAnim) {
            canvas.drawBitmap(bitmap, 0, 0, paint);
            return;
        }

        for (Ball ball : balls) {
            paint.setColor(ball.color);
            canvas.drawCircle(ball.x, ball.y, ball.r, paint);
//            canvas.drawBitmap(ball.getBitmap(), ball.getX(), ball.getY(), paint);
        }
    }

    @Override
    public void onClick(View v) {
        if (!isInitSuccess) {
            Toast.makeText(getContext(), "粒子初始化中", Toast.LENGTH_SHORT).show();
            return;
        }
        if (animator.isStarted()) {
            return;
        }
        startAnim = true;
        animator.start();
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        animator.cancel();
    }

    private class Ball {
        public int color; //图片像素点颜色值
        public float x; //粒子圆心坐标x
        public float y; //粒子圆心坐标y
        public float r; //粒子半径

        public float vX;//粒子运动水平方向速度
        public float vY;//粒子运动垂直方向速度
        public float aX;//粒子运动水平方向加速度
        public float aY;//粒子运动垂直方向加速度
    }
}
