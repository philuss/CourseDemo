package com.mcourse.widget.paint;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

/**
 * Created on 2019-4-1 17:24.
 * Describe: 线帽类型
 */

public class PaintCapView extends View {
    private Paint mPaint;
    private Paint mTextPaint;

    public PaintCapView(Context context) {
        this(context, null);
    }

    public PaintCapView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public PaintCapView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initPaint();
    }

    private void initPaint() {
        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setColor(Color.RED);
        mPaint.setStrokeWidth(15);
        mPaint.setStyle(Paint.Style.STROKE);

        mTextPaint = new Paint();
        mTextPaint = new Paint();
        mTextPaint.setAntiAlias(true);
        mTextPaint.setColor(Color.RED);
        mTextPaint.setStrokeWidth(3);
        mTextPaint.setTextSize(28);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        Log.e("PaintCapView", "onDraw: ");

        mPaint.setStrokeCap(Paint.Cap.BUTT);
        canvas.drawLine(200,100,400,100,mPaint);
        canvas.drawText("Paint.Cap.BUTT", 500, 100, mTextPaint);

        mPaint.setStrokeCap(Paint.Cap.ROUND);
        canvas.drawLine(200,200,400,200,mPaint);
        canvas.drawText("Paint.Cap.ROUND", 500, 200, mTextPaint);

        mPaint.setStrokeCap(Paint.Cap.SQUARE);
        canvas.drawLine(200,300,400,300,mPaint);
        canvas.drawText("Paint.Cap.SQUARE", 500, 300, mTextPaint);
    }

}
