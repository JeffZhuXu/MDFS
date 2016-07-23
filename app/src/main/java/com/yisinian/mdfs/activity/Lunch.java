package com.yisinian.mdfs.activity;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.graphics.PixelFormat;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.WindowManager;


import com.yisinian.mdfs.MainActivity;
import com.yisinian.mdfs.R;
import com.yisinian.mdfs.constant.StringConstant;
import com.yisinian.mdfs.http.MDFSHttp;
import com.yisinian.mdfs.service.NetService;
import com.yisinian.mdfs.table.NodeMessage;
import com.yisinian.mdfs.table.NodeMessageDao;

import java.io.File;


/**
 * 启动页面
 * 初始化一些操作
 * Created by zhuxu on 16/1/6.
 */
public class Lunch extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        //新建分块文件APP内部文件夹
        String root=getApplicationContext().getFilesDir().getAbsolutePath();
        File dir = new File(root+File.separator+"blocks");
        if (!dir.exists()){
            dir.mkdirs();
            Log.i(StringConstant.MDFS_TAG_FILE,"新建分块文件夹");
        }


        //判断节点信息是否存在，存在的话不操作，不存在的话新建
        boolean nodeState =  NodeMessageDao.getLogin(this);
        //Log.i(StringConstant.MDFS_TAG_DB,"节点状态:"+nodeState);
        //没信息，节点信息表中新建节点信息
        if (nodeState==false) {
            //初始化数据库，新建NodeMessage,BlocksMessage表，同时给NodeMessage表添加一条基本信息
            NodeMessage baseNodeMessage = new NodeMessage(this);
            Log.i(StringConstant.MDFS_TAG_DB,"nodeId:"+baseNodeMessage.nodeId);
            NodeMessageDao.saveNodeMessage(this, baseNodeMessage);
            //向服务器发送节点当前状态信息
            MDFSHttp.nodeLogin(this);
        }

        setContentView(R.layout.lunch_ativity);
        new Handler().postDelayed(new Runnable() {
            public void run() {
                /* Create an Intent that will start the Main WordPress Activity. */
                Intent mainIntent = new Intent(Lunch.this, Slider.class);
                Lunch.this.startActivity(mainIntent);
                Lunch.this.finish();
            }
        }, 2900); //2900 for release
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
