package com.mcourse.widget.canvas;

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
 * Describe: 变换
 */

public class CanvasTransformationView extends View {
    private final String TAG = CanvasTransformationView.class.getSimpleName();
    private Paint mPaint;

    public CanvasTransformationView(Context context) {
        this(context, null);
    }

    public CanvasTransformationView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CanvasTransformationView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initPaint();
    }

    private void initPaint() {
        mPaint = new Paint();
        mPaint.setAntiAlias(true);

        mPaint.setStrokeWidth(3);
        mPaint.setStyle(Paint.Style.FILL);
    }


    RectF rect1 = new RectF(0, 0, 150, 100);
    RectF rect2 = new RectF(200, 300, 400, 500);

    RectF rect3 = new RectF(500, 50, 700, 250);
    RectF rect4 = new RectF(500, 300, 700, 500);

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        // 平移
        mPaint.setColor(Color.RED);
        canvas.drawRect(rect1, mPaint);
        canvas.translate(300, 300);
        mPaint.setColor(Color.BLUE);
        canvas.drawRect(rect1, mPaint);
        Log.e(TAG, "onDraw: ");

//        canvas.drawText("不使用圆心", 750, 200, mPaint);
//        canvas.drawText("使用圆心", 750, 450, mPaint);
//
//        canvas.drawText("画笔不填充", 250, 550, mPaint);
//        canvas.drawText("画笔填充", 550, 550, mPaint);
    }

}
