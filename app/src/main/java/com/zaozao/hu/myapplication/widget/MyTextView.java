package com.zaozao.hu.myapplication.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;

import com.zaozao.hu.myapplication.R;

public class MyTextView extends View {


    private Paint mPaint;
    private String mText;
    private int mTextSize = 15;
    private Rect mRect;

    public MyTextView(Context context) {
        this(context, null);
    }

    public MyTextView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public MyTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);


        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.MyTextView);
        mText = typedArray.getString(R.styleable.MyTextView_MyTextView_Text);
        mTextSize = typedArray.getDimensionPixelSize(R.styleable.MyTextView_MyTextView_Size, sp2px(mTextSize));
        typedArray.recycle();

        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setDither(true);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setColor(Color.BLACK);
        mPaint.setTextSize(mTextSize);
        mRect = new Rect();

       // setWillNotDraw(false);
    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        int width = MeasureSpec.getSize(widthMeasureSpec);
        int height = MeasureSpec.getSize(heightMeasureSpec);
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        if (widthMode == MeasureSpec.AT_MOST) {
            //如果是wrap_content ,需要自己计算
            mPaint.getTextBounds(mText, 0, mText.length(), mRect);
            width = mRect.width()+getPaddingLeft()+getPaddingRight();
        }

        if (heightMode == MeasureSpec.AT_MOST) {
            mPaint.getTextBounds(mText, 0, mText.length(), mRect);
            height = mRect.height()+getPaddingTop()+getPaddingBottom();
        }

        setMeasuredDimension(width, height);
    }


    @Override
    protected void onDraw(Canvas canvas) {

        Paint.FontMetricsInt fontMetricsInt = mPaint.getFontMetricsInt();
        int dy = (fontMetricsInt.bottom - fontMetricsInt.top) / 2 - fontMetricsInt.bottom;
        int baseLine = getHeight() / 2 + dy;
        int x = getPaddingLeft();
        canvas.drawText(mText, x, baseLine, mPaint);
    }

    private int dip2Px(int dip) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dip, getResources().getDisplayMetrics());
    }

    private int sp2px(int sp) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, sp, getResources().getDisplayMetrics());
    }
}
