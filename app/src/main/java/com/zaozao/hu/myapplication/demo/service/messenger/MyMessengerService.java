package com.zaozao.hu.myapplication.demo.service.messenger;

import android.app.Service;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;

import com.zaozao.hu.myapplication.utils.LogUtils;

public class MyMessengerService extends Service {


    private MyHander hander;
    private Messenger messenger;

    static class MyHander extends Handler {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            LogUtils.i("MyMessengerService", "received message");
        }
    }

    @Override
    public void onCreate() {
        super.onCreate();
        hander = new MyHander();
        messenger = new Messenger(hander);
    }

    public MyMessengerService() {

    }

    @Override
    public IBinder onBind(Intent intent) {
        return messenger.getBinder();
    }
}
