package com.zaozao.hu.myapplication.demo.service.aidl;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

import com.zaozao.hu.myapplication.utils.LogUtils;

public class MyAIDLService extends Service {

    private static final String TAG = "MyAIDLService";
    AIDLImpl mBinder = new AIDLImpl();

    public MyAIDLService() {
    }


    @Override
    public void onCreate() {
        super.onCreate();
        LogUtils.d(TAG, "onCreate");
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        LogUtils.d(TAG, "onStartCommand");
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        LogUtils.d(TAG, "onDestroy");

    }

    @Override
    public IBinder onBind(Intent intent) {
        return mBinder;
    }
}
