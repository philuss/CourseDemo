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

public class PaintDrawText extends View {
    private Paint mPaint;

    public PaintDrawText(Context context) {
        this(context, null);
    }

    public PaintDrawText(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public PaintDrawText(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initPaint();
    }

    private void initPaint() {
        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setColor(Color.RED);
        mPaint.setStrokeWidth(2);
        mPaint.setTextSize(60);
        mPaint.setLetterSpacing(0.05f);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        Log.e("PaintDrawText", "onDraw: ");

        mPaint.setStyle(Paint.Style.STROKE);
        canvas.drawText("在画板上写字", 10, 100, mPaint);
        mPaint.setStyle(Paint.Style.FILL);
        canvas.drawText("在画板上写字", 10, 200, mPaint);
        mPaint.setStyle(Paint.Style.FILL_AND_STROKE);
        canvas.drawText("在画板上写字", 10, 300, mPaint);
    }

}
