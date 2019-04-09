package com.mcourse.widget.bezier;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by philus on 2019/4/9 23:14 .
 */

public class PathView extends View {
    private String TAG = PathView.class.getSimpleName();
    private Paint mPaint;
    private Path mPath;

    public PathView(Context context) {
        this(context, null);
    }

    public PathView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public PathView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        mPaint = new Paint();
        mPaint.setColor(Color.RED);
        mPaint.setStrokeWidth(4);
        mPaint.setStyle(Paint.Style.STROKE);

        mPath = new Path();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        //一阶贝塞尔曲线，表示的是一条直线
        mPath.moveTo(100, 70); //移动
//        mPath.lineTo(140, 800);//连线
        //等同于上一行代码效果
        mPath.rLineTo(40, 430);
        canvas.drawPath(mPath, mPaint);

        // 二阶贝塞尔曲线
        mPath.reset();
        mPath.moveTo(300, 300);
        mPath.quadTo(400, 100, 500, 500);
        canvas.drawPath(mPath, mPaint);

        // 三阶贝塞尔曲线
        mPath.reset();
        mPath.moveTo(600, 300);
        mPath.cubicTo(700, 300, 300, 700, 800, 700);
        canvas.drawPath(mPath, mPaint);
    }
}
