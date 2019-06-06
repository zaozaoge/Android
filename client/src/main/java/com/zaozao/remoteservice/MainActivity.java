package com.zaozao.remoteservice;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.os.RemoteException;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.zaozao.hu.myapplication.demo.service.aidl.IMyAidlInterface;

public class MainActivity extends AppCompatActivity {


    private boolean isBinded;
    IMyAidlInterface aidlInterface;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }


    private ServiceConnection serviceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            isBinded = true;
            aidlInterface = IMyAidlInterface.Stub.asInterface(service);
            try {
                String person = aidlInterface.getPerson();
                Log.i("ServiceConnection", "person--->" + person);
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            isBinded = false;
        }
    };

    public void bindService(View view) {
        Intent intent = new Intent();
        intent.setAction("com.zaozao.hu.demo.service.aidl.MyAIDLService");
        intent.setPackage("com.zaozao.hu.myapplication");
        bindService(intent, serviceConnection, BIND_AUTO_CREATE);
    }

    public void unBindService(View view) {
        if (isBinded) {
            unbindService(serviceConnection);
            isBinded = false;
        }
    }
}
