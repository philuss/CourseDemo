package com.mcourse.widget.paint;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.CornerPathEffect;
import android.graphics.DashPathEffect;
import android.graphics.DiscretePathEffect;
import android.graphics.Paint;
import android.graphics.Path;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

/**
 * Created on 2019-4-1 17:24.
 * Describe: 线帽类型
 */

public class PaintPathEffectView extends View {
    private Paint mPaint;
    private Paint mTextPaint;

    public PaintPathEffectView(Context context) {
        this(context, null);
    }

    public PaintPathEffectView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public PaintPathEffectView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initPaint();
    }

    private void initPaint() {
        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setColor(Color.RED);
        mPaint.setStrokeWidth(3);
        mPaint.setStyle(Paint.Style.STROKE);

        mTextPaint = new Paint();
        mTextPaint = new Paint();
        mTextPaint.setAntiAlias(true);
        mTextPaint.setColor(Color.RED);
        mTextPaint.setStrokeWidth(3);
        mTextPaint.setTextSize(28);

        initPath();
    }

    private Path mPath;

    private void initPath() {
        mPath = new Path();
        mPath.moveTo(50, 200);
        mPath.lineTo(150, 50);
        mPath.lineTo(250, 200);
    }

    private void resetPaint(Paint paint) {
        paint.reset();
        paint.setAntiAlias(true);
        paint.setColor(Color.RED);
        paint.setStrokeWidth(3);
        paint.setStyle(Paint.Style.STROKE);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        Log.e("PaintPathEffectView", "onDraw: ");
        resetPaint(mPaint);
        mPath.reset();
        mPath.moveTo(50, 200);
        mPath.lineTo(150, 50);
        mPath.lineTo(250, 200);
        canvas.drawPath(mPath, mPaint);
        mPaint.setColor(Color.GREEN);
        mPaint.setPathEffect(new CornerPathEffect(50));     //传入圆形半径
        canvas.drawPath(mPath, mPaint);
        mPaint.setColor(Color.BLUE);
        mPaint.setPathEffect(new CornerPathEffect(100));
        canvas.drawPath(mPath, mPaint);
        canvas.drawText("CornerPathEffect 圆形拐角", 0, 250, mTextPaint);

        resetPaint(mPaint);
        mPath.reset();
        mPath.moveTo(50, 600);
        mPath.lineTo(150, 450);
        mPath.lineTo(250, 600);
        canvas.drawPath(mPath, mPaint);
        canvas.translate(0, 10);
        mPaint.setColor(Color.GREEN);
        mPaint.setPathEffect(new DashPathEffect(new float[]{20, 10, 30, 2, 5, 9, 6, 8}, 0));     //数组偏移量为0
        canvas.drawPath(mPath, mPaint);
        canvas.translate(0,10);
        mPaint.setColor(Color.BLUE);
        mPaint.setPathEffect(new DashPathEffect(new float[]{20, 10, 30, 2, 5, 9, 6, 8}, 3));//数组偏移量为3，虚线从2开始
        canvas.drawPath(mPath, mPaint);
        canvas.drawText("DashPathEffect 虚线效果", 0, 650, mTextPaint);

        resetPaint(mPaint);
        mPath.reset();
        mPath.moveTo(550, 200);
        mPath.lineTo(650, 50);
        mPath.lineTo(750, 200);
        canvas.drawPath(mPath, mPaint);
        canvas.translate(0, 20);
        mPaint.setColor(Color.GREEN);
        mPaint.setPathEffect(new DiscretePathEffect(2,5));     //传入圆形半径
        canvas.drawPath(mPath, mPaint);
        canvas.translate(0, 20);
        mPaint.setColor(Color.BLUE);
        mPaint.setPathEffect(new DiscretePathEffect(6,5));
        canvas.drawPath(mPath, mPaint);
        canvas.drawText("DiscretePathEffect 离散路径效果", 500, 250, mTextPaint);

        canvas.drawText("PathDashPathEffect 印章路径效果", 500, 450, mTextPaint);
        canvas.drawText("ComposePathEffect 组合路径", 500, 500, mTextPaint);
        canvas.drawText("SumPathEffect  组合路径", 500, 550, mTextPaint);
    }

}
