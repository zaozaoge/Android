package com.zaozao.hu.myapplication.widget.banner;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.zaozao.hu.myapplication.R;

public class BannerView extends FrameLayout {

    private BannerViewPager mBannerViewPager;

    private TextView mBannerDes;

    private LinearLayout mDotContainer;

    private BannerAdapter mBannerAdapter;

    //选中时的指示器颜色
    private ColorDrawable mFocusDrawable;
    //未选中时的指示器颜色
    private ColorDrawable mNormalDrawable;
    //当前的位置
    private int mCurPosition = 0;
    private Context mContext;

    public BannerView(Context context) {
        this(context, null);
    }

    public BannerView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public BannerView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        inflate(context, R.layout.banner_layout, this);
        initView();
        mContext = context;
        mFocusDrawable = new ColorDrawable(Color.RED);
        mNormalDrawable = new ColorDrawable(Color.WHITE);
    }


    private void initView() {
        mBannerViewPager = findViewById(R.id.banner);
        mBannerDes = findViewById(R.id.banner_des);
        mDotContainer = findViewById(R.id.dot_container);
    }


    private void initDotIndicator() {
        int count = mBannerAdapter.getCount();
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
    }

    /**
     * 页面切换的回调
     *
     * @param position
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
