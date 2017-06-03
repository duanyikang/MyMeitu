package com.guoguoquan.meitu;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.ColorMatrixColorFilter;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

/**
 * 作者： duanyikang on 2017/6/3.
 * 邮箱： duanyikang@yixia.com
 * 描述：
 */

public class MyColorMatrixView extends View {

    private Paint mPaint;
    private Bitmap bitmap;
    private  Matrix mMatrix = new Matrix();

    public MyColorMatrixView(Context context) {
        super(context);
    }

    public MyColorMatrixView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (bitmap != null)
            canvas.drawBitmap(bitmap, mMatrix, mPaint);
    }

    public void setBitmap(Bitmap bitmap) {
        this.bitmap = bitmap;
        postInvalidate();
    }

    public void setMatrix(float colorArray[]) {
        mPaint.setColorFilter(new ColorMatrixColorFilter(colorArray));
        postInvalidate();
    }

    public void setRotate(int i) {
        mMatrix.setRotate(i);
        postInvalidate();
    }
}
