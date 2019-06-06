package com.zaozao.hu.myapplication.demo.service;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;

import com.zaozao.hu.myapplication.utils.LogUtils;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class MyService extends Service {

    public static final String TAG = "MyService";
    private MyBinder myBinder = new MyBinder();

    public MyService() {
    }

    @Override
    public void onCreate() {
        super.onCreate();
        LogUtils.d(TAG, "MyService------>onCreate");
    }


    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        LogUtils.d(TAG, "MyService------>onStartCommand");
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        executorService.execute(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(5000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                stopSelf();
            }
        });
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        LogUtils.d(TAG, "MyService------>onDestroy");

    }

    @Override
    public IBinder onBind(Intent intent) {
        return myBinder;
    }

    class MyBinder extends Binder {
        void startDownLoad() {
            Executors.newSingleThreadExecutor().execute(new Runnable() {
                @Override
                public void run() {
                    LogUtils.d(TAG, "MyService------>startDownLoad");

                }
            });
        }

        int getProgress() {
            LogUtils.d(TAG, "MyService------>getProgress");

            return 0;
        }
    }
}
