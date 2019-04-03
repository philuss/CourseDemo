package com.mcourse.widget.paint;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
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

public class PaintJoinView extends View {
    private Paint mPaint;
    private Paint mTextPaint;

    public PaintJoinView(Context context) {
        this(context, null);
    }

    public PaintJoinView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public PaintJoinView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
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

    private Path path = new Path();

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        Log.e("PaintJoinView", "onDraw: ");

        path.moveTo(100, 100);
        path.lineTo(450, 100);
        path.lineTo(100, 300);
        mPaint.setStrokeJoin(Paint.Join.MITER);
        canvas.drawPath(path, mPaint);
        canvas.drawText("Paint.Join.MITER", 500, 200, mTextPaint);


        path.moveTo(100, 350);
        path.lineTo(450, 400);
        path.lineTo(100, 550);
        mPaint.setStrokeJoin(Paint.Join.BEVEL);
        canvas.drawPath(path, mPaint);
        canvas.drawText("Paint.Join.BEVEL", 500, 450, mTextPaint);


        path.moveTo(100, 600);
        path.lineTo(450, 700);
        path.lineTo(100, 800);
        mPaint.setStrokeJoin(Paint.Join.ROUND);
        canvas.drawPath(path, mPaint);
        canvas.drawText("Paint.Join.ROUND", 500, 700, mTextPaint);
    }

}
