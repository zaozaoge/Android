package com.zaozao.hu.myapplication.utils;

import android.app.Activity;
import android.content.res.Resources;
import android.graphics.Color;
import android.os.Build;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;

public class StatusBarUtil {


    /**
     * 设置状态栏的颜色
     *
     * @param activity
     * @param color
     */
    public static void setStatusBarColor(Activity activity, int color) {

        //5.0以上
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            activity.getWindow().setStatusBarColor(color);
        }
        //4.4到5.0之间
        else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            //先将界面设置为全屏、透明
            activity.getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            //在状态栏的部分添加一个和状态栏等高的布局
            View view = new View(activity);
            view.setBackgroundColor(color);
            view.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, getStatusBarHeight(activity)));

            ViewGroup decorView = (ViewGroup) activity.getWindow().getDecorView();
            decorView.addView(view);

            //获取当前Activity中setContentView的根布局
            ViewGroup contentView = activity.findViewById(android.R.id.content);
            // contentView.setPadding(0,getStatusBarHeight(activity),0,0);
            View activityView = contentView.getChildAt(0);
            activityView.setFitsSystemWindows(true);
        }
    }

    /**
     * 获取状态栏的高度
     */
    private static int getStatusBarHeight(Activity activity) {
        Resources resources = activity.getResources();
        int statusBarHeightId = resources.getIdentifier("status_bar_height", "dimen", "android");
        return resources.getDimensionPixelOffset(statusBarHeightId);
    }


    /**
     * 设置Activity全屏
     */
    public static void setActivityTranslucent(Activity activity) {
        //5.0以上
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            View decorView = activity.getWindow().getDecorView();
            decorView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
            activity.getWindow().setStatusBarColor(Color.TRANSPARENT);
        }
        //4.4到5.0之间
        else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            activity.getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }
    }
}
