package com.zaozao.hu.myapplication.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;

import com.zaozao.hu.myapplication.R;

public class HealthStandardView extends View {


    private Paint mIndicatorPaint;
    private Paint mTextPaint;
    //当前刻度值
    private int mCurProgress = 30;
    //最大刻度值，默认为100
    private int mMaxProgress = 100;
    //圆的半径，默认为8dp
    private int mCircleRadius = 8;
    //圆的轮廓宽度，默认为3dp
    private int mCircleStrokeWidth = 3;
    //圆下竖线的高度，默认为10dp
    private int mVerticalLineHeight = 10;
    //圆下竖线的宽度，默认为3dp
    private int mVerticalLineWidth = 3;
    //竖线到圆的间距
    private int mVerticalLineTopMargin = 1;
    //竖线到进度条的间距
    private int mVerticalLineBottomMargin = 2;
    //进度条的高度，默认15dp
    private int mProgressbarHeight = 15;
    //进度条到底部文字之间的间距 默认15dp
    private int mBottomTextTopMargin = 15;

    //底部刻度条每一小段的长度，默认60dp
    private int mPerScaleWidth = 60;
    //分类数量 默认为4个
    private int mSortNumber = 4;
    //圆心坐标
    private float cx, cy;

    private int[] mColors = {Color.BLUE, Color.GREEN, Color.YELLOW, Color.RED};
    private String[] mTexts = {"偏低", "理想", "超重", "严重超标"};
    private String[] mScaleTexts = {"105.0|b", "118.0|b", "144.0|b"};
    private Paint[] mPaints;
    //刻度条圆角值，默认30dp
    private int mProgressbarRadius = 30;

    //底部文字颜色,默认黑色
    private int mBottomTextColor = Color.BLACK;
    //底部文字大小，默认15sp
    private int mBottomTextSize = 15;
    //测量文本宽高时使用
    private Rect bounds = new Rect();

    public HealthStandardView(Context context) {
        this(context, null);
    }

    public HealthStandardView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public HealthStandardView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initAttributes(context, attrs);
        initPaint();
    }

    /**
     * 初始化自定义属性
     */
    private void initAttributes(Context context, AttributeSet attrs) {
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.HealthStandardView);
        mCircleRadius = typedArray.getDimensionPixelSize(R.styleable.HealthStandardView_circle_radius, mCircleRadius);
        mCircleStrokeWidth = typedArray.getDimensionPixelSize(R.styleable.HealthStandardView_circle_stroke, mCircleStrokeWidth);
        mVerticalLineWidth = typedArray.getDimensionPixelSize(R.styleable.HealthStandardView_vertical_line_width, mVerticalLineWidth);
        mVerticalLineHeight = typedArray.getDimensionPixelSize(R.styleable.HealthStandardView_vertical_line_height, mVerticalLineHeight);
        mProgressbarHeight = typedArray.getDimensionPixelSize(R.styleable.HealthStandardView_progressbar_height, mProgressbarHeight);
        mVerticalLineTopMargin = typedArray.getDimensionPixelSize(R.styleable.HealthStandardView_vertical_line_top_margin, mVerticalLineTopMargin);
        mVerticalLineBottomMargin = typedArray.getDimensionPixelSize(R.styleable.HealthStandardView_vertical_line_bottom_margin, dp2px(mVerticalLineBottomMargin));
        mBottomTextTopMargin = typedArray.getDimensionPixelSize(R.styleable.HealthStandardView_bottomText_top_margin, dp2px(mBottomTextTopMargin));


        mBottomTextColor = typedArray.getColor(R.styleable.HealthStandardView_bottomText_color, mBottomTextColor);
        mBottomTextSize = typedArray.getDimensionPixelSize(R.styleable.HealthStandardView_bottomText_size, sp2px(mBottomTextSize));

        mPerScaleWidth = typedArray.getDimensionPixelSize(R.styleable.HealthStandardView_perScaleWidth, dp2px(mPerScaleWidth));
        mSortNumber = typedArray.getInteger(R.styleable.HealthStandardView_sortNumber, mSortNumber);
        mProgressbarRadius = typedArray.getDimensionPixelSize(R.styleable.HealthStandardView_progressbar_radius, mProgressbarRadius);
        typedArray.recycle();

    }

    public void setCurProgress(int progress) {
        this.mCurProgress = progress;
    }

    /**
     * 设置刻度条颜色数组*
     */
    public void setProgressbarColors(int[] colors) {
        if (colors == null || colors.length != mSortNumber)
            return;
        this.mColors = colors;
        mPaints = new Paint[colors.length];
        for (int i = 0; i < mPaints.length; i++) {
            mPaints[i] = getPaintByColor(mColors[i]);
        }
    }

    /**
     * 设置底部文字数据*
     */
    public void setBottomTexts(String[] bottomTexts) {
        if (bottomTexts == null || bottomTexts.length != mSortNumber)
            return;
        this.mTexts = bottomTexts;
    }

    /**
     * 设置顶部刻度信息
     */
    public void setScaleTexts(String[] scaleTexts) {
        if (scaleTexts == null || scaleTexts.length != mSortNumber - 1)
            return;
        this.mScaleTexts = scaleTexts;
    }


    public void show() {
        if (mTexts != null && mTexts.length != mSortNumber)
            return;
        if (mScaleTexts != null && mScaleTexts.length != mSortNumber - 1)
            return;
        if (mColors != null && mPaints != null && mColors.length != mPaints.length)
            return;
        invalidate();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {

        //1、首先要计算控件的宽高
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);
        if (widthMode == MeasureSpec.AT_MOST) {
            //如果设置的是wrap_content ,
            widthSize = mPerScaleWidth * mSortNumber;
        } else if (widthMode == MeasureSpec.EXACTLY) {
            //如果控件的宽度设定的是一个默认的值，那么重新计算每段刻度的长度
            mPerScaleWidth = widthSize / mSortNumber;
        }
        mTextPaint.getTextBounds(mTexts[0], 0, mTexts[0].length(), bounds);
        if (heightMode == MeasureSpec.AT_MOST) {
            //（圆的半径+轮廓宽度)*2+圆的轮廓到竖线的间距+竖线高度+竖线到进度条的间距+进度条的高度+进度条到文字的间距+文字的高度
            heightSize = (mCircleRadius + mCircleStrokeWidth) * 2 + mVerticalLineTopMargin
                    + mVerticalLineHeight + mVerticalLineBottomMargin + mProgressbarHeight + mBottomTextTopMargin
                    + bounds.height();
        }

        setMeasuredDimension(widthSize, heightSize);
    }


    private void initPaint() {
        mPaints = new Paint[mColors.length];
        for (int i = 0; i < mColors.length; i++) {
            mPaints[i] = getPaintByColor(mColors[i]);
        }
        getIndicatorPaint();
        getTextPaint();
    }


    /**
     * 根据颜色获取画笔对象
     */
    private Paint getPaintByColor(int color) {
        Paint paint = new Paint();
        paint.setAntiAlias(true);
        paint.setDither(true);
        paint.setStyle(Paint.Style.FILL);
        paint.setColor(color);
        return paint;
    }

    /**
     * 获取指示器画笔
     */
    private void getIndicatorPaint() {
        mIndicatorPaint = new Paint();
        mIndicatorPaint.setStyle(Paint.Style.STROKE);
        mIndicatorPaint.setAntiAlias(true);
        mIndicatorPaint.setDither(true);
        mIndicatorPaint.setColor(Color.MAGENTA);
        mIndicatorPaint.setStrokeWidth(mCircleStrokeWidth);
    }


    /**
     * 获取文字画笔
     */
    private void getTextPaint() {
        mTextPaint = new Paint();
        mTextPaint.setColor(mBottomTextColor);
        mTextPaint.setTextSize(mBottomTextSize);
        mTextPaint.setAntiAlias(true);
        mTextPaint.setDither(true);
        mTextPaint.setStyle(Paint.Style.STROKE);
    }

    @Override
    protected void onDraw(Canvas canvas) {

        setIndicatorPaintColor();
        //绘制圆
        drawCircle(canvas);
        //绘制顶部标记文字
        drawTopText(canvas);
        //绘制竖线
        drawVerticalLine(canvas);
        //绘制进度条
        drawRectProgressbar(canvas);
        //绘制底部文字
        drawBottomText(canvas);
    }

    /**
     * 根据进度值设置画笔颜色
     */
    private void setIndicatorPaintColor() {
        float per = mMaxProgress * (1.0f) / mSortNumber * (1.0f);
        int index = (int) (mCurProgress * (1.0f) / per * (1.0f));
        if (index < mSortNumber) {
            mIndicatorPaint.setColor(mColors[index]);
        }
    }

    /**
     * 绘制顶部刻度值
     */
    private void drawTopText(Canvas canvas) {
        if (mScaleTexts == null || mScaleTexts.length != mTexts.length - 1) {
            return;
        }
        for (int i = 0; i < mScaleTexts.length; i++) {
            mTextPaint.getTextBounds(mScaleTexts[i], 0, mScaleTexts[i].length(), bounds);
            int x = (i + 1) * mPerScaleWidth - bounds.width() / 2;
            Paint.FontMetricsInt fontMetricsInt = mTextPaint.getFontMetricsInt();
            int dy = (fontMetricsInt.bottom - fontMetricsInt.top) / 2 - fontMetricsInt.bottom;
            float top = (mCircleRadius + mCircleStrokeWidth) * 2 + mVerticalLineTopMargin + mVerticalLineHeight + mVerticalLineBottomMargin;
            int baseLine = (int) (top - mBottomTextTopMargin - (bounds.height() >> 1) + dy);
            canvas.drawText(mScaleTexts[i], x, baseLine, mTextPaint);
        }
    }


    /**
     * 绘制圆
     */
    private void drawCircle(Canvas canvas) {
        //圆心的坐标
        cx = mCircleRadius + mCircleStrokeWidth + getWidth() * mCurProgress / mMaxProgress;
        cy = mCircleRadius + mCircleStrokeWidth;
        //圆的颜色应该要根据进度条的颜色改变
        canvas.drawCircle(cx, cy, mCircleRadius, mIndicatorPaint);
    }

    /**
     * 绘制竖线
     */
    private void drawVerticalLine(Canvas canvas) {
        //竖线的初始x坐标即为圆心的x坐标
        float startX = cx;
        //竖线的初始y坐标即为圆心的y坐标+圆的半径+轮廓宽度+竖线到圆的距离
        float startY = cy + mCircleStrokeWidth + mCircleRadius + mVerticalLineTopMargin;
        float stopY = startY + mVerticalLineHeight;
        canvas.drawLine(startX, startY, startX, stopY, mIndicatorPaint);
    }

    /**
     * 绘制进度条
     */
    private void drawRectProgressbar(Canvas canvas) {
        float top = (mCircleRadius + mCircleStrokeWidth) * 2 + mVerticalLineTopMargin + mVerticalLineHeight + mVerticalLineBottomMargin;
        //进度条有四种颜色，分四段绘制
        float bottom = top + mProgressbarHeight;
        RectF rectF = new RectF(0, top, getWidth(), bottom);
        for (int i = 0; i < mSortNumber; i++) {
            int left = i * mPerScaleWidth;
            int right = left + mPerScaleWidth;
            canvas.save();
            canvas.clipRect(left, top, right, bottom);
            canvas.drawRoundRect(rectF, mProgressbarRadius, mProgressbarRadius, mPaints[i]);
            canvas.restore();
        }
    }

    /**
     * 绘制底部文字
     */
    private void drawBottomText(Canvas canvas) {
        if (mTexts == null)
            return;
        for (int i = 0; i < mTexts.length; i++) {
            mTextPaint.getTextBounds(mTexts[i], 0, mTexts[i].length(), bounds);
            int x = mPerScaleWidth * i + mPerScaleWidth / 2 - bounds.width() / 2;
            Paint.FontMetricsInt fontMetricsInt = mTextPaint.getFontMetricsInt();
            int dy = (fontMetricsInt.bottom - fontMetricsInt.top) / 2 - fontMetricsInt.bottom;
            int baseLine = getHeight() - bounds.height() / 2 + dy;
            canvas.drawText(mTexts[i], x, baseLine, mTextPaint);
        }
    }


    /**
     * sp转px
     */
    private int sp2px(int sp) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, sp, getResources().getDisplayMetrics());
    }

    /**
     * dp转px
     */
    private int dp2px(int dp) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, getResources().getDisplayMetrics());
    }

}
