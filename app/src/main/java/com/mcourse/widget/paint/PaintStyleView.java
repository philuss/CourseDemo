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
 * Describe: TODO
 */

public class PaintStyleView extends View {
    private Paint mPaint;
    private Paint mTextPaint;

    public PaintStyleView(Context context) {
        this(context, null);
    }

    public PaintStyleView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public PaintStyleView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initPaint();
    }

    private void initPaint() {
        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setColor(Color.RED);
        mPaint.setStrokeWidth(15);

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
        Log.e("PaintStyleView", "onDraw: ");

        mPaint.setStyle(Paint.Style.STROKE);
        canvas.drawRect(200, 50, 400, 250, mPaint);
        canvas.drawText("Paint.Style.STROKE", 500, 150, mTextPaint);

        mPaint.setStyle(Paint.Style.FILL);
        canvas.drawRect(200, 300, 400, 500, mPaint);
        canvas.drawText("Paint.Style.FILL", 500, 400, mTextPaint);

        mPaint.setStyle(Paint.Style.FILL_AND_STROKE);
        canvas.drawRect(200, 550, 400, 750, mPaint);
        canvas.drawText("Paint.Style.FILL_AND_STROKE", 500, 650, mTextPaint);
    }

}
