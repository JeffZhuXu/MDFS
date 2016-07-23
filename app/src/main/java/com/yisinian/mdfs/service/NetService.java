package com.yisinian.mdfs.service;

/**
 * Created by mac on 16/1/18.
 */


import android.app.Service;
import android.content.ContentValues;
import android.content.Context;
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

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * @author:zhuxu
 * @tips :实时获取当前网速的service
 * @date :2016-1-18
 */
public class NetService extends Service {

    private long total_data = TrafficStats.getTotalRxBytes();
    private Handler mHandler;


    //几秒刷新一次
    private final int count = 15;

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
            Bundle netMsg = new Bundle();
            netMsg.putString("netType", NodeMessage.getCurrentNetType(getApplicationContext()));    //网络连接类型
            netMsg.putInt("netSpeed", getNetSpeed());                                               //当前网速
            netMsg.putString("state", "1");                                                         //节点在线状态
            netMsg.putString("restLocalStorage", NodeMessage.getPhoneRestStorage());                //手机本地剩余的分块信息
            netMsg.putString("storage", "1048576");                                                    //MDFS总容量
            netMsg.putString("restStorage", "1048576");                                           //MDFS剩余容量
            //msg.arg1 = getNetSpeed();
            msg.setData(netMsg);
            mHandler.sendMessage(msg);
        }
    };

    @Override
    public void onCreate() {
        super.onCreate();
        Log.i(StringConstant.MDFS_TAG_SERVICE,"net service onCreate");

        mHandler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                if (msg.what == 1) {
                    //float real_data = (float)msg.arg1;
                    Bundle netMsg = msg.getData();
                    String netType = netMsg.getString("netType");
                    int netSpeed = netMsg.getInt("netSpeed");
                    String state = netMsg.getString("state");
                    String restLocalStorage = netMsg.getString("restLocalStorage");
                    String storage = netMsg.getString("storage");
                    String restStorage = netMsg.getString("restStorage");
                    //更新本地数据库
                    ContentValues cv = new ContentValues();
                    cv.put(NodeMessage.NODEMESSAGE_NET_TYPE,netType);
                    cv.put(NodeMessage.NODEMESSAGE_NET_SPEED,netSpeed+"");
                    cv.put(NodeMessage.NODEMESSAGE_REST_LOCAL_STORAGE,restLocalStorage);
                    cv.put(NodeMessage.NODEMESSAGE_STORAGE,storage);
                    cv.put(NodeMessage.NODEMESSAGE_REST_STORAGE,restStorage);
                    cv.put(NodeMessage.NODEMESSAGE_STATE,state);
                    NodeMessageDao.updateNodeMessageByNodeId(getApplicationContext(),cv);
                    Log.i(StringConstant.MDFS_TAG_SERVICE,"当前网速："+netSpeed + "b/s"+" 网络连接类型："+netType+" 当前状态："+state+
                            " 本地剩余存储："+restLocalStorage
                            +" MDFS系统容量："+storage
                            +" MDFS系统剩余容量："+restStorage
                            );
                    //将新的数据上传到服务器
                    MDFSHttp.updateNodeMessage(getApplicationContext());
                }
            }
        };
    }

    /**
     * 核心方法，得到当前网速
     *
     * @return
     */
    private int getNetSpeed() {
        long traffic_data = TrafficStats.getTotalRxBytes() - total_data;
        total_data = TrafficStats.getTotalRxBytes();
        return (int) traffic_data / count;
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
        MDFSHttp.nodeLogout(getApplicationContext());
        Log.i(StringConstant.MDFS_TAG_SERVICE, "net service onDestroy");
        super.onDestroy();
    }

    ;

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    public String ping(){
        int pingNum =2;
        int line =0;
        String m_strForNetAddress = "here.yisinian.com";
        String result ="";
        StringBuffer tv_PingInfo = new StringBuffer();
        String relayTime ="";
        int status = 0;
        try {
            Process p = Runtime.getRuntime().exec("/system/bin/ping -c "+ pingNum + " " + m_strForNetAddress); // 10.83.50.111  m_strForNetAddress
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
                    break;
                }
                line++;
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }finally {

        }
        return  relayTime;
    }

}
