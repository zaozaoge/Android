package com.zaozao.hu.myapplication.widget.banner;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.util.AttributeSet;
import android.view.View;

public class BannerIndicatorView extends View {


    private Drawable mDrawable;

    public BannerIndicatorView(Context context) {
        this(context, null);
    }

    public BannerIndicatorView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public BannerIndicatorView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (mDrawable != null) {
//            mDrawable.setBounds(0, 0, getMeasuredWidth(), getMeasuredHeight());
//            mDrawable.draw(canvas);
            Bitmap bitmap = drawableToBitmap(mDrawable);
            Bitmap circleBitmap = getCircleBitmap(bitmap);
            canvas.drawBitmap(circleBitmap, 0, 0, null);
        }
    }

    /**
     * 获取圆形Bitmap
     */
    @NonNull
    private Bitmap getCircleBitmap(Bitmap bitmap) {
        //创建一个Bitmap
        Bitmap circleBitmap = Bitmap.createBitmap(getMeasuredWidth(), getMeasuredHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(circleBitmap);

        //在画布上画一个圆
        Paint paint = new Paint();
        //抗锯齿
        paint.setAntiAlias(true);
        //防抖动
        paint.setDither(true);
        canvas.drawCircle(getMeasuredWidth() >> 1, getMeasuredHeight() >> 1, getMeasuredWidth() >> 1, paint);
        //取圆和Bitmap举行的交集
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
        //再把原来的bitmap绘制到新的圆上面
        canvas.drawBitmap(bitmap, 0, 0, paint);
        return circleBitmap;
    }


    /**
     * 将Drawable转化为Bitmap
     */
    private Bitmap drawableToBitmap(Drawable drawable) {

        //如果是BitmapDrawable 类型
        if (drawable instanceof BitmapDrawable) {
            return ((BitmapDrawable) drawable).getBitmap();
        }
        //其他类型 ColorDrawable
        //创建一个什么都没有的Bitmap
        Bitmap outBitmap = Bitmap.createBitmap(getMeasuredWidth(), getMeasuredHeight(), Bitmap.Config.ARGB_8888);
        //创建一个画布
        Canvas canvas = new Canvas(outBitmap);
        //将drawable绘制到画布上
        drawable.setBounds(0, 0, getMeasuredWidth(), getMeasuredHeight());
        drawable.draw(canvas);
        return outBitmap;
    }


    /**
     * 设置指示器圆点背景
     */
    public void setDrawable(Drawable drawable) {
        this.mDrawable = drawable;
        invalidate();
    }
}
