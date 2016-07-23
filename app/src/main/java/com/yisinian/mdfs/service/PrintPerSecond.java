package com.yisinian.mdfs.service;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.preference.PreferenceManager;
import android.util.Log;

public class PrintPerSecond extends Service {

    private boolean serviceRunning = false;
    private String data = "默认服务信息";

    public PrintPerSecond() {
    }

    @Override
    public IBinder onBind(Intent intent) {

        return new Binder();
    }

    public class Binder extends android.os.Binder{
        public void setData(String date){
            PrintPerSecond.this.data=date;
        }
        public PrintPerSecond getService(){
            return PrintPerSecond.this;
        }
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        data = intent.getStringExtra("data");
        Log.i("MDFS", "启动服务，传入数据");
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onCreate() {
        super.onCreate();
        serviceRunning = true;
        Log.i("MDFS", "创建服务");

            new Thread() {
                @Override
                public void run() {
                    super.run();
                    int i = 0;
                    while (serviceRunning) {
                        i++;
                        String str = "服务正在运行 服务数据："+i+":"+data;
                        if(callback!=null){
                            callback.onDataChanged(str);
                        }
                        Log.i("MDFS", str);
                        try {
                            sleep(2000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }

                    }
                }
            }.start();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.i("MDFS", "终止服务");
        serviceRunning=false;
    }

    private Callback callback = null;

    public void setCallback(Callback callback) {
        this.callback = callback;
    }

    public Callback getCallback(Callback data) {
        return callback;
    }

    public static interface Callback{
         void onDataChanged(String data);
    }
}
