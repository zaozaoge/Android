package com.zaozao.hu.myapplication.demo.service.aidl;

import android.os.RemoteException;

public class AIDLImpl extends IMyAidlInterface.Stub {

    String name;
    int age;

    @Override
    public void basicTypes(int anInt, long aLong, boolean aBoolean, float aFloat, double aDouble, String aString) throws RemoteException {

    }

    @Override
    public void setName(String name) throws RemoteException {
        this.name = name;
    }

    @Override
    public void setAge(int age) throws RemoteException {
        this.age = age;
    }

    @Override
    public String getPerson() throws RemoteException {
        return name;
    }
}
