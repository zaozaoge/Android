package com.zaozao.hu.myapplication.widget.banner;

import android.content.Context;
import android.view.animation.Interpolator;
import android.widget.Scroller;

public class BannerScroller extends Scroller {


    private int mPageSwitchDuration = 2000;


    public void setPageSwitchDuration(int mPageSwitchDuration) {
        this.mPageSwitchDuration = mPageSwitchDuration;
    }

    public BannerScroller(Context context) {
        super(context);
    }

    public BannerScroller(Context context, Interpolator interpolator) {
        super(context, interpolator);
    }

    public BannerScroller(Context context, Interpolator interpolator, boolean flywheel) {
        super(context, interpolator, flywheel);
    }


    @Override
    public void startScroll(int startX, int startY, int dx, int dy, int duration) {
        super.startScroll(startX, startY, dx, dy, mPageSwitchDuration);
    }
}
