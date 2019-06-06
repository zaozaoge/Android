package com.zaozao.hu.myapplication.widget.banner;

import android.view.View;

public abstract class BannerAdapter {

    protected abstract View getView(int position,View convertView);

    protected abstract int getCount();
}
