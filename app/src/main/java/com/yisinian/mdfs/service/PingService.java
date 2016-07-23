package com.yisinian.mdfs.service;

/**
 * Created by mac on 16/1/18.
 */


import android.app.Service;
import android.content.ContentValues;
import android.content.Intent;
import android.net.TrafficStats;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.util.Log;

import com.yisinian.mdfs.constant.StringConstant;
import com.yisinian.mdfs.http.MDFSHttp;
import com.yisinian.mdfs.table.NodeMessage;
import com.yisinian.mdfs.table.NodeMessageDao;
import com.yisinian.mdfs.tool.CommandUtil;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author:zhuxu
 * @tips :实时获取当前网速的service
 * @date :2016-1-18
 */
public class PingService extends Service {

    private long total_data = TrafficStats.getTotalRxBytes();
    private Handler mHandler;

    //几秒刷新一次
    private final int count = 60;
    /**
     * 定义线程周期性地获取网速
     */
    private Runnable mRunnable = new Runnable() {
        @Override
        public void run() {
            //定时器
            mHandler.postDelayed(mRunnable, count * 1000);
            Message msg = mHandler.obtainMessage();
            msg.what = 1;
            MDFSHttp.ping(getApplicationContext());

        }
    };
    @Override
    public void onCreate() {
        super.onCreate();
        Log.i(StringConstant.MDFS_TAG_SERVICE,"ping service onCreate");

        mHandler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
//                if (msg.what == 1) {
//                    //float real_data = (float)msg.arg1;
//                    Bundle netMsg = msg.getData();
//                    String relayTime = netMsg.getString("relayTime");
//                    //更新本地数据库
//                    ContentValues cv = new ContentValues();
//                    cv.put(NodeMessage.NODEMESSAGE_RELAY_TIME,relayTime);
//                    NodeMessageDao.updateNodeMessageByNodeId(getApplicationContext(), cv);
//                }
            }
        };
    }



    /**
     * 启动服务时就开始启动线程获取网速
     */
    @Override
    public void onStart(Intent intent, int startId) {
        mHandler.postDelayed(mRunnable, 0);
    }

    /**
     * 在服务结束时删除消息队列
     */
    @Override
    public void onDestroy() {
        mHandler.removeCallbacks(mRunnable);
        //MDFSHttp.nodeLogout(getApplicationContext());
        Log.i(StringConstant.MDFS_TAG_SERVICE, "ping service onDestroy");
        super.onDestroy();
    }

    ;

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    public String ping(){
        int pingNum =1;
        int line =0;
        String m_strForNetAddress = "here.yisinian.com";
        String result ="";
        StringBuffer tv_PingInfo = new StringBuffer();
        String relayTime ="";
        int status = 0;
        try {
            Process p = Runtime.getRuntime().exec("/system/bin/ping -c "+ "1" + " " + "here.yisinian.com"); // 10.83.50.111  m_strForNetAddress
            status = p.waitFor();

            if (status == 0) {
                result="success";
            }
            else
            {
                result="failed";
            }
            String lost = new String();
            String delay = new String();
            BufferedReader buf = new BufferedReader(new InputStreamReader(p.getInputStream()));

            String str = new String();

            //读出所有信息并显示
            while((str=buf.readLine())!=null){
                if (line==1){
                    String[] msg=str.split(" ");
                    relayTime = msg[(msg.length-2)].replaceAll("time=","");
                    Log.i(StringConstant.MDFS_TAG_HTTP,"节点Ping服务:ping服务器时间"+relayTime+" ms");
                    break;
                }
                line++;
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return  relayTime;
    }

}
