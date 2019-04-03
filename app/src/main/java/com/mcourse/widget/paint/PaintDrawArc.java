package com.mcourse.widget.paint;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

/**
 * Created on 2019-4-1 17:24.
 * Describe: TODO
 */

public class PaintDrawArc extends View {
    private Paint mPaint;

    public PaintDrawArc(Context context) {
        this(context, null);
    }

    public PaintDrawArc(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public PaintDrawArc(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initPaint();
    }

    private void initPaint() {
        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setColor(Color.RED);
        mPaint.setStrokeWidth(3);

    }


    RectF rect1 = new RectF(200, 50, 400, 250);
    RectF rect2 = new RectF(200, 300, 400, 500);

    RectF rect3 = new RectF(500, 50, 700, 250);
    RectF rect4 = new RectF(500, 300, 700, 500);

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        Log.e("PaintDrawArc", "onDraw: ");
        mPaint.setStyle(Paint.Style.STROKE);
        canvas.drawArc(rect1, 0, 150, false, mPaint);
        canvas.drawArc(rect2, 0, 150, true, mPaint);

        mPaint.setStyle(Paint.Style.FILL);
        canvas.drawArc(rect3, 0, 150, false, mPaint);
        canvas.drawArc(rect4, 0, 150, true, mPaint);

        canvas.drawText("不使用圆心", 750, 200, mPaint);
        canvas.drawText("使用圆心", 750, 450, mPaint);

        canvas.drawText("画笔不填充", 250, 550, mPaint);
        canvas.drawText("画笔填充", 550, 550, mPaint);
    }

}
