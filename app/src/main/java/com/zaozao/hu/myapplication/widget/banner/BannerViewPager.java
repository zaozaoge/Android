package com.zaozao.hu.myapplication.widget.banner;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;

import com.zaozao.hu.myapplication.utils.LogUtils;

import java.lang.ref.WeakReference;
import java.lang.reflect.Field;

/**
 * 自定义可以无限轮播的ViewPager
 */
public class BannerViewPager extends ViewPager {

    private static final String TAG = "BannerViewPager";
    private BannerAdapter mAdapter;
    private MyHandler mHandler;
    private BannerScroller mScroller;
    //页面切换间隔时间
    private int mRollToNextTime = 3500;
    private static final int MSG_SCROLL = 0x0011;

    public BannerViewPager(@NonNull Context context) {
        this(context, null);
    }

    public BannerViewPager(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        try {
            Field field = ViewPager.class.getDeclaredField("mScroller");
            //设置为强制改变private
            field.setAccessible(true);
            mScroller = new BannerScroller(context);
            //第一个参数 当前属性在哪个类，第二个参数代表要设置的值
            field.set(this, mScroller);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    /**
     * 设置页面切换动画执行的时间
     */
    public void setPageSwitchDuration(int duration) {
        mScroller.setPageSwitchDuration(duration);
    }

    /**
     * @param adapter 自定义的适配器
     */
    public void setAdapter(@Nullable BannerAdapter adapter) {
        this.mAdapter = adapter;
        //设置父类的Adapter
        setAdapter(new BannerPagerAdapter());
    }


    /**
     * 开始滚动
     */
    public void startRoll() {
        if (mHandler == null) {
            WeakReference<BannerViewPager> weakReference = new WeakReference<>(this);
            mHandler = new MyHandler(weakReference);
        }
        //清除消息
        mHandler.removeMessages(MSG_SCROLL);
        mHandler.sendEmptyMessageDelayed(MSG_SCROLL, mRollToNextTime);
        //LogUtils.i(TAG, "----->startRoll");
    }


    /**
     * 销毁Handler
     */
    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        LogUtils.i(TAG, "----->onDetachedFromWindow");
        mHandler.removeMessages(MSG_SCROLL);
        mHandler = null;
    }

    private static class MyHandler extends Handler {

        WeakReference<BannerViewPager> viewPagerWeakReference;

        MyHandler(WeakReference<BannerViewPager> viewPagerWeakReference) {
            this.viewPagerWeakReference = viewPagerWeakReference;
        }

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.what == MSG_SCROLL) {
                if (viewPagerWeakReference != null) {
                    BannerViewPager viewPager = viewPagerWeakReference.get();
                    if (viewPager == null) return;
                    viewPager.setCurrentItem(viewPager.getCurrentItem() + 1);
                    viewPager.startRoll();
                }
            }
        }
    }

    private class BannerPagerAdapter extends PagerAdapter {

        @Override
        public int getCount() {
            //返回一个很大的值，确保可以无限轮播
            return Integer.MAX_VALUE;
        }

        @Override
        public boolean isViewFromObject(@NonNull View view, @NonNull Object o) {
            return view == o;
        }

        @NonNull
        @Override
        public Object instantiateItem(@NonNull ViewGroup container, int position) {
            View bannerView = mAdapter.getView(position % mAdapter.getCount());
            container.addView(bannerView);
            return bannerView;
        }

        @Override
        public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
            container.removeView((View) object);
        }
    }
}
