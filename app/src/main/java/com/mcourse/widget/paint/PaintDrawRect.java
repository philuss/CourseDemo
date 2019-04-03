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

public class PaintDrawRect extends View {
    private final String TAG = PaintDrawRect.class.getSimpleName();
    private Paint mPaint;

    public PaintDrawRect(Context context) {
        this(context, null);
    }

    public PaintDrawRect(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public PaintDrawRect(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initPaint();
    }

    private void initPaint() {
        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setColor(Color.BLUE);
        mPaint.setStrokeWidth(5);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        Log.e(TAG, "onDraw: ");
        canvas.drawRect(200, 200, 400, 400, mPaint);
    }

}
