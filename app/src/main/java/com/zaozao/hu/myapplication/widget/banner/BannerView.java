package com.zaozao.hu.myapplication.widget.banner;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.zaozao.hu.myapplication.R;
import com.zaozao.hu.myapplication.utils.LogUtils;

import java.util.List;

public class BannerView extends FrameLayout {

    private BannerViewPager mBannerViewPager;

    private TextView mBannerDes;

    private LinearLayout mDotContainer;

    private BannerAdapter mBannerAdapter;

    private View mBottomIndicator;
    //选中时的指示器颜色
    private Drawable mFocusDrawable;
    //未选中时的指示器颜色
    private Drawable mNormalDrawable;
    //当前的位置
    private int mCurPosition = 0;
    //点的显示位置
    private int mGravity = -1;
    //点的大小，默认8dp
    private int mDotSize = 8;
    //点的间距，默认8dp
    private int mDotDistance = 8;
    //底部颜色,默认透明
    private int mBottomColor = Color.TRANSPARENT;
    //宽高比例
    private float mWidthProportion, mHeightProportion;
    private Context mContext;

    public BannerView(Context context) {
        this(context, null);
    }

    public BannerView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public BannerView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mContext = context;
        inflate(context, R.layout.banner_layout, this);
        initAttribute(attrs);
        initView();
    }

    private void initAttribute(AttributeSet attrs) {
        TypedArray typedArray = mContext.obtainStyledAttributes(attrs, R.styleable.BannerView);
        //获取自定义属性
        //获取点的位置
        mGravity = typedArray.getInt(R.styleable.BannerView_dotGravity, mGravity);
        //获取点的颜色
        mFocusDrawable = typedArray.getDrawable(R.styleable.BannerView_dotIndicatorFocus);
        mNormalDrawable = typedArray.getDrawable(R.styleable.BannerView_dotIndicatorNormal);
        if (mFocusDrawable == null) {
            //如果在布局文件中没有配置点的颜色
            mFocusDrawable = new ColorDrawable(Color.RED);
        }
        if (mNormalDrawable == null) {
            mNormalDrawable = new ColorDrawable(Color.WHITE);
        }
        //获取点的大小
        mDotSize = (int) typedArray.getDimension(R.styleable.BannerView_dotSize, dip2px(mDotSize));
        //获取点的间距
        mDotDistance = (int) typedArray.getDimension(R.styleable.BannerView_dotDistance, dip2px(mDotDistance));
        //获取底部颜色
        mBottomColor = typedArray.getColor(R.styleable.BannerView_bottomColor, mBottomColor);
        //宽高比
        mWidthProportion = typedArray.getFloat(R.styleable.BannerView_widthProportion, mWidthProportion);
        mHeightProportion = typedArray.getFloat(R.styleable.BannerView_heightProportion, mHeightProportion);
        typedArray.recycle();
    }


    private void initView() {
        mBannerViewPager = findViewById(R.id.banner);
        mBannerDes = findViewById(R.id.banner_des);
        mDotContainer = findViewById(R.id.dot_container);
        mBottomIndicator = findViewById(R.id.bottomIndicator);

        mBottomIndicator.setBackgroundColor(mBottomColor);
    }

    /**
     * 初始化底部指示器
     */
    private void initDotIndicator() {
        int count = mBannerAdapter.getCount();
        int dotGravity = getDotGravity();
        mDotContainer.setGravity(dotGravity);
        for (int i = 0; i < count; i++) {
            BannerIndicatorView indicatorView = new BannerIndicatorView(mContext);
            if (i == 0) {
                indicatorView.setDrawable(mFocusDrawable);
            } else {
                indicatorView.setDrawable(mNormalDrawable);
            }
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(dip2px(8), dip2px(8));
            params.leftMargin = dip2px(2);
            params.rightMargin = dip2px(2);
            indicatorView.setLayoutParams(params);
            mDotContainer.addView(indicatorView);
        }
    }

    /**
     * 获取点的位置
     */
    private int getDotGravity() {
        switch (mGravity) {
            case 0:
                return Gravity.CENTER;
            case -1:
                return Gravity.END;
            case 1:
                return Gravity.START;
            default:
                return Gravity.START;
        }
    }

    /**
     * 设置数据源
     */
    public void setAdapter(BannerAdapter bannerAdapter) {
        mBannerViewPager.setAdapter(bannerAdapter);
        this.mBannerAdapter = bannerAdapter;
        initDotIndicator();

        mBannerViewPager.addOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                pageSelect(position);
            }
        });
        LogUtils.i("BannerView", "mWidthProportion---->" + mWidthProportion + ",mHeightProportion------>" + mHeightProportion);
        //动态指定高度
        if (mWidthProportion == 0 || mHeightProportion == 0)
            return;
        //动态指定宽高
        int width = getMeasuredWidth();
        //计算高度
        //指定宽高
        ViewGroup.LayoutParams params = getLayoutParams();
        params.height = (int) (width * mHeightProportion / mWidthProportion);
        LogUtils.i("BannerView", "height:" + params.height + ",width:" + getMeasuredWidth());
        setLayoutParams(params);
    }

    /**
     * 设置Item点击事件
     */
    public void setBannerItemClickListener(BannerViewPager.BannerItemClickListener bannerItemClickListener) {
        mBannerViewPager.setBannerItemClickListener(bannerItemClickListener);
    }

    /**
     * 页面切换的回调
     */
    private void pageSelect(int position) {
        //将原先点亮的点设置为默认
        BannerIndicatorView oldIndicatorView = (BannerIndicatorView) mDotContainer.getChildAt(mCurPosition);
        oldIndicatorView.setDrawable(mNormalDrawable);

        mCurPosition = position % mBannerAdapter.getCount();
        BannerIndicatorView mCurIndicatorView = (BannerIndicatorView) mDotContainer.getChildAt(mCurPosition);
        mCurIndicatorView.setDrawable(mFocusDrawable);

    }

    public void startRoll() {
        mBannerViewPager.startRoll();
    }

    /**
     * dp 转 px
     */
    private int dip2px(int dp) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, getResources().getDisplayMetrics());
    }
}
