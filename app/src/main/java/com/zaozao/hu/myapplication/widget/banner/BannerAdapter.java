package com.zaozao.hu.myapplication.widget.banner;

import android.view.View;

public abstract class BannerAdapter {
    protected abstract View getView(int position);

    protected abstract int getCount();
}
