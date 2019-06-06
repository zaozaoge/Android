package com.zaozao.hu.myapplication.utils;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

public class LogUtils {
    public static void i(String tag, String msg) {
        Log.i(tag, msg);
    }

    public static void d(String tag, String msg) {
        Log.d(tag, msg);
    }

    public static void showToast(Context context, String txt) {
        Toast.makeText(context, txt, Toast.LENGTH_LONG).show();
    }
}
