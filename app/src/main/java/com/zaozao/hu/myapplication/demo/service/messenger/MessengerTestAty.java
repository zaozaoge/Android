package com.zaozao.hu.myapplication.demo.service.messenger;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.zaozao.hu.myapplication.R;

public class MessengerTestAty extends AppCompatActivity {


    private Messenger messenger;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_messenger_test_aty);
    }


    private ServiceConnection serviceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            messenger = new Messenger(service);
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {

        }
    };

    @Override
    protected void onStart() {
        super.onStart();
        Intent intent = new Intent(this, MyMessengerService.class);
        bindService(intent, serviceConnection, BIND_AUTO_CREATE);
    }

    public void bindService(View view) {
        try {
            messenger.send(new Message());
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }
}
