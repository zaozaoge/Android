package com.zaozao.hu.myapplication.demo.thread;

import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class ThreadPoolDemo {



    public ThreadPoolExecutor getThreadPoolExecutor(){
        ThreadPoolExecutor poolExecutor = new ThreadPoolExecutor(3,
                5,1, TimeUnit.SECONDS,new LinkedBlockingDeque<Runnable>(100));

        return poolExecutor;
    }


}
