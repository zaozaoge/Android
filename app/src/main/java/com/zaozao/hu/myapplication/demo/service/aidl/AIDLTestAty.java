package com.zaozao.hu.myapplication.demo.service.aidl;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.os.RemoteException;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.zaozao.hu.myapplication.R;
import com.zaozao.hu.myapplication.ui.BaseActivity;
import com.zaozao.hu.myapplication.utils.LogUtils;

public class AIDLTestAty extends BaseActivity {


    private boolean mBind;
    private IMyAidlInterface aidl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_aidltest_aty);
    }


    private ServiceConnection serviceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            aidl = AIDLImpl.Stub.asInterface(service);
            try {
                aidl.setName("胡章孝");
                aidl.setAge(20);
                String p = aidl.getPerson();
                LogUtils.d(TAG, "name----->" + p);
            } catch (RemoteException e) {
                e.printStackTrace();
            }
            mBind = true;

        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            mBind = false;
        }
    };

    public void bindService(View view) {
        Intent intent = new Intent(this, MyAIDLService.class);
        bindService(intent, serviceConnection, BIND_AUTO_CREATE);
    }

    public void unBindService(View view) {
        if (mBind) {
            unbindService(serviceConnection);
            mBind = false;
        }
    }
}
