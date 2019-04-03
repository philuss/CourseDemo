package com.mcourse.widget.paint;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ComposeShader;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.RadialGradient;
import android.graphics.Shader;
import android.graphics.SweepGradient;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import com.mcourse.R;

/**
 * Created on 2019-4-1 17:24.
 * Describe: TODO
 */

public class PaintShaderView extends View {
    private final String TAG = PaintShaderView.class.getSimpleName();
    private Paint mPaint;
    private Paint mTextPaint;
    private Shader mShader;
    private Bitmap mBitmap;

    public PaintShaderView(Context context) {
        this(context, null);
    }

    public PaintShaderView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public PaintShaderView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initPaint();
        init();
    }

    private void initPaint() {
        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setColor(Color.RED);
        mPaint.setStrokeWidth(2);
        mPaint.setStyle(Paint.Style.FILL);

        mTextPaint = new Paint();
        mTextPaint = new Paint();
        mTextPaint.setAntiAlias(true);
        mTextPaint.setColor(Color.RED);
        mTextPaint.setStrokeWidth(3);
        mTextPaint.setTextSize(28);
    }

    private void init() {
        mBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.beauty);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        Log.e(TAG, "onDraw: ");


        /**
         * 位图渲染，BitmapShader(@NonNull Bitmap bitmap, @NonNull TileMode tileX, @NonNull TileMode tileY)
         * Bitmap:构造shader使用的bitmap
         * tileX：X轴方向的TileMode
         * tileY:Y轴方向的TileMode
         *  REPEAT, 绘制区域超过渲染区域的部分，重复排版
         *  CLAMP， 绘制区域超过渲染区域的部分，会以最后一个像素拉伸排版
         *  MIRROR, 绘制区域超过渲染区域的部分，镜像翻转排版
         */
        mShader = new BitmapShader(mBitmap, Shader.TileMode.REPEAT, Shader.TileMode.MIRROR);
        mPaint.setShader(mShader);
        canvas.drawRect(20, 20, 300, 300, mPaint);
//        canvas.drawCircle(150, 550, 120, mPaint);
        canvas.drawText("BitmapShader", 50, 350, mTextPaint);


//        /**
//         * 线性渲染,LinearGradient(float x0, float y0, float x1, float y1, @NonNull @ColorInt int colors[], @Nullable float positions[], @NonNull TileMode tile)
//         * (x0,y0)：渐变起始点坐标
//         * (x1,y1):渐变结束点坐标
//         * color0:渐变开始点颜色,16进制的颜色表示，必须要带有透明度
//         * color1:渐变结束颜色
//         * colors:渐变数组
//         * positions:位置数组，position的取值范围[0,1],作用是指定某个位置的颜色值，如果传null，渐变就线性变化。
//         * tile:用于指定控件区域大于指定的渐变区域时，空白区域的颜色填充方法
//         */
//        mShader = new LinearGradient(50, 50, 250, 250, new int[]{Color.RED, Color.BLUE, Color.GREEN}, new float[]{0.0f, 0.7f, 1}, Shader.TileMode.REPEAT);
////        mShader = new LinearGradient(50, 50, 500, 500, new int[]{Color.RED, Color.BLUE}, new float[]{0.0f, 1}, Shader.TileMode.REPEAT);
//        mPaint.setShader(mShader);
////        canvas.drawCircle(250, 250, 200, mPaint);
//        canvas.drawRect(20, 20, 300, 300, mPaint);
//        canvas.drawText("LinearGradient", 50, 350, mTextPaint);


        /**
         * 环形渲染，RadialGradient(float centerX, float centerY, float radius, @ColorInt int colors[], @Nullable float stops[], TileMode tileMode)
         * centerX ,centerY：shader的中心坐标，开始渐变的坐标
         * radius:渐变的半径
         * centerColor,edgeColor:中心点渐变颜色，边界的渐变颜色
         * colors:渐变颜色数组
         * stoops:渐变位置数组，类似扫描渐变的positions数组，取值[0,1],中心点为0，半径到达位置为1.0f
         * tileMode:shader未覆盖以外的填充模式。
         */
        mShader = new RadialGradient(450, 150, 100, new int[]{Color.GREEN, Color.YELLOW, Color.RED}, null, Shader.TileMode.CLAMP);
        mPaint.setShader(mShader);
        canvas.drawCircle(450, 150, 120, mPaint);
        canvas.drawText("RadialGradient", 350, 350, mTextPaint);

        /**
         * 扫描渲染，SweepGradient(float cx, float cy, @ColorInt int color0,int color1)
         * cx,cy 渐变中心坐标
         * color0,color1：渐变开始结束颜色
         * colors，positions：类似LinearGradient,用于多颜色渐变,positions为null时，根据颜色线性渐变
         */
//        mShader = new SweepGradient(750, 150, Color.RED, Color.GREEN);
        mShader = new SweepGradient(750, 150, new int[]{Color.GREEN, Color.YELLOW, Color.RED}, new float[]{0.2f, 0.7f, 1.0f});
        mPaint.setShader(mShader);
        canvas.drawCircle(750, 150, 120, mPaint);
        canvas.drawText("SweepGradient", 650, 350, mTextPaint);


        /**
         * 线性渲染,LinearGradient(float x0, float y0, float x1, float y1, @NonNull @ColorInt int colors[], @Nullable float positions[], @NonNull TileMode tile)
         * (x0,y0)：渐变起始点坐标
         * (x1,y1):渐变结束点坐标
         * color0:渐变开始点颜色,16进制的颜色表示，必须要带有透明度
         * color1:渐变结束颜色
         * colors:渐变数组
         * positions:位置数组，position的取值范围[0,1],作用是指定某个位置的颜色值，如果传null，渐变就线性变化。
         * tile:用于指定控件区域大于指定的渐变区域时，空白区域的颜色填充方法
         */
        mShader = new LinearGradient(50, 450, 350, 750, new int[]{Color.RED, Color.BLUE, Color.GREEN}, new float[]{0.0f, 0.7f, 1}, Shader.TileMode.REPEAT);
//        mShader = new LinearGradient(50, 50, 500, 500, new int[]{Color.RED, Color.BLUE}, new float[]{0.0f, 1}, Shader.TileMode.REPEAT);
        mPaint.setShader(mShader);
//        canvas.drawCircle(250, 250, 200, mPaint);
        canvas.drawRect(20, 420, 300, 700, mPaint);
        canvas.drawText("LinearGradient", 50, 750, mTextPaint);


//        /**
//         * 位图渲染，BitmapShader(@NonNull Bitmap bitmap, @NonNull TileMode tileX, @NonNull TileMode tileY)
//         * Bitmap:构造shader使用的bitmap
//         * tileX：X轴方向的TileMode
//         * tileY:Y轴方向的TileMode
//         *  REPEAT, 绘制区域超过渲染区域的部分，重复排版
//         *  CLAMP， 绘制区域超过渲染区域的部分，会以最后一个像素拉伸排版
//         *  MIRROR, 绘制区域超过渲染区域的部分，镜像翻转排版
//         */
//        mShader = new BitmapShader(mBitmap, Shader.TileMode.REPEAT, Shader.TileMode.MIRROR);
//        mPaint.setShader(mShader);
//        canvas.drawRect(20, 420, 300, 700, mPaint);
////        canvas.drawCircle(150, 550, 120, mPaint);
//        canvas.drawText("BitmapShader", 50, 750, mTextPaint);


        /**
         * 组合渲染，
         * ComposeShader(@NonNull Shader shaderA, @NonNull Shader shaderB, Xfermode mode)
         * ComposeShader(@NonNull Shader shaderA, @NonNull Shader shaderB, PorterDuff.Mode mode)
         * shaderA,shaderB:要混合的两种shader
         * Xfermode mode： 组合两种shader颜色的模式
         * PorterDuff.Mode mode: 组合两种shader颜色的模式
         */
        BitmapShader bitmapShader = new BitmapShader(mBitmap, Shader.TileMode.REPEAT, Shader.TileMode.REPEAT);
        LinearGradient linearGradient = new LinearGradient(420, 420, 750, 750, new int[]{Color.RED, Color.GREEN, Color.BLUE}, null, Shader.TileMode.CLAMP);
        mShader = new ComposeShader(bitmapShader, linearGradient, PorterDuff.Mode.MULTIPLY);
        mPaint.setShader(mShader);
        canvas.drawCircle(650, 550, 120, mPaint);
        canvas.drawText("ComposeShader", 550, 750, mTextPaint);

    }

}
