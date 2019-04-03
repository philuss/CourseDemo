package com.mcourse.widget.paint;

import android.animation.Animator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;
import android.os.Message;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.LinearInterpolator;

import com.mcourse.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created on 2019-4-3 8:52.
 * Describe: 粒子效果
 */

public class ParticleView extends View {
    private final String TAG = ParticleView.class.getSimpleName();
    private Paint mPaint;
    private Bitmap mBitmap;
    private int d = 3;      //小球直径
    private List<Particle> particles;
    private ValueAnimator mAnimator;
    private Handler uiHandler;
    private HandlerThread workThread;
    private Handler workHandler;
    private final int MSG_UPDATE = 1;           //更新小球坐标
    private final int MSG_DRAW = 2;             //绘制
    private final int MSG_START = 3;            //初始化小球坐标

    private final int sampleX = 3;      //X方向上的采样率
    private final int sampleY = 3;      //Y方向上的采样率
    private boolean isInit = false;     //是否初始化
    private boolean isStarted = false;  //是否已经启动
    private boolean allOut = false;     //所有粒子都移出显示区域

    public ParticleView(Context context) {
        this(context, null);
    }

    public ParticleView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ParticleView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
        initHandler();
    }

    private void initHandler() {
        workThread = new HandlerThread("work");
        workThread.start();
        workHandler = new Handler(workThread.getLooper()) {
            @Override
            public void handleMessage(Message msg) {
//                Log.e(TAG, "handleMessage: workHandler " + msg.what);
                switch (msg.what) {
                    case MSG_UPDATE:
                        updateParticle();
                        uiHandler.sendEmptyMessage(MSG_DRAW);
                        break;
                    case MSG_START:
                        getParticles();
                        allOut = false;
                        uiHandler.sendEmptyMessage(MSG_START);
                        break;
                }
            }
        };

        uiHandler = new Handler(Looper.getMainLooper()) {
            @Override
            public void handleMessage(Message msg) {
//                Log.e(TAG, "handleMessage: uiHandler " + msg.what);
                switch (msg.what) {
                    case MSG_DRAW:
                        invalidate();
                        break;
                    case MSG_START:
                        isInit = true;
                        break;
                }
            }
        };

        workHandler.sendEmptyMessage(MSG_START);
    }

    private void init() {
        mPaint = new Paint();
        mBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.avatar_drakeet);
        particles = new ArrayList<>();
        mAnimator = ValueAnimator.ofFloat(0, 1);
        mAnimator.setRepeatCount(-1);
        mAnimator.setDuration(2000);
        mAnimator.setInterpolator(new LinearInterpolator());
        mAnimator.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {
                isStarted = true;
                allOut = false;
                Log.e(TAG, "onAnimationStart: " + isStarted + "; isInit = " + isInit + "; allOut = " + allOut);
            }

            @Override
            public void onAnimationEnd(Animator animation) {
                isStarted = false;
                isInit = false;
                Log.e(TAG, "onAnimationEnd: " + isStarted + "; isInit = " + isInit + "; allOut = " + allOut);
            }

            @Override
            public void onAnimationCancel(Animator animation) {
                isStarted = false;
                isInit = false;
                Log.e(TAG, "onAnimationCancel: " + isStarted + "; isInit = " + isInit + "; allOut = " + allOut);
            }

            @Override
            public void onAnimationRepeat(Animator animation) {
                isStarted = true;
//                isInit = false;
//                workHandler.sendEmptyMessage(MSG_START);
                Log.e(TAG, "onAnimationRepeat: " + isStarted + "; isInit = " + isInit + "; allOut = " + allOut);
            }
        });
        mAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                workHandler.sendEmptyMessage(MSG_UPDATE);
                if (allOut) {
                    mAnimator.cancel();
                }
            }
        });
    }

    private void getParticles() {
        if (particles == null) {
            particles = new ArrayList<>();
        }
        particles.clear();
        int bmpWidth = mBitmap.getWidth();
        int bmpHeight = mBitmap.getHeight();
        for (int i = 0, width = mBitmap.getWidth(); i < width; i += sampleX) {
            for (int j = 0, height = mBitmap.getHeight(); j < height; j += sampleY) {
                Particle particle = new Particle();
//                particle.cx = i * d + d / 2;
//                particle.cy = j * d + d / 2;
                particle.cx = i + d / 2;
                particle.cy = j + d / 2;
                particle.r = d / 2;
                particle.color = mBitmap.getPixel(i, j);

                // 初始化速度
                //速度(-20,20)
                particle.vx = (float) (Math.pow(-1, Math.ceil(Math.random() * 1000)) * 20 * Math.random());
//                particle.vy = rangInt(-15, 35);
                particle.vy = rangInt(-1, 35);
                //加速度
                particle.ax = 0;
                particle.ay = 0.98f;
                particles.add(particle);
            }
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
//        Log.e(TAG, "onDraw: isStarted = " + isStarted + "; isInit = " + isInit);
        canvas.translate(350, 20);
        if (!isStarted || !isInit) {
            canvas.drawBitmap(mBitmap, 0, 0, mPaint);
            return;
        }
        for (Particle particle : particles) {
            mPaint.setColor(particle.color);
            canvas.drawCircle(particle.cx, particle.cy, particle.r, mPaint);
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            if (!isInit) {      //重新计算
                workHandler.sendEmptyMessage(MSG_START);
            }
            // 执行动画
            if (!mAnimator.isRunning()) {
                mAnimator.start();
            }
        }
        return super.onTouchEvent(event);
    }

    private int rangInt(int i, int j) {
        int max = Math.max(i, j);
        int min = Math.min(i, j) - 1;
        //在0到(max - min)范围内变化，取大于x的最小整数 再随机
        return (int) (min + Math.ceil(Math.random() * (max - min)));
    }

    private void updateParticle() {
        allOut = true;
        //更新粒子的位置和速度
        for (Particle particle : particles) {
            particle.cx += particle.vx;
            particle.cy += particle.vy;
            particle.vx += particle.ax;
            particle.vy += particle.ay;
            if (particle.cx >= 0 && particle.cx <= getWidth() && particle.cy >= 0 && particle.cy <= getHeight()) {
                allOut = false;
            }
        }
    }

    /**
     * 单一粒子类
     */
    private class Particle {
        int color;          //粒子对应颜色
        int cx, cy;         //粒子中心坐标
        float r;            //离子半径
        float vx, vy;        //x,y方向上的速度
        float ax, ay;        //x,y方向上的加速度
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        mAnimator.cancel();
        Log.e(TAG, "onDetachedFromWindow: " + "; allOut = " + allOut);
    }
}
