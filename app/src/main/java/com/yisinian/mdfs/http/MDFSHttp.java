package com.yisinian.mdfs.http;

import android.content.ContentValues;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.widget.Toast;

import com.yisinian.mdfs.constant.StringConstant;
import com.yisinian.mdfs.constant.UrlConstant;
import com.yisinian.mdfs.fragment.Blocks;
import com.yisinian.mdfs.system.FileOption;
import com.yisinian.mdfs.system.MDFSBlock;
import com.yisinian.mdfs.system.MDFSTime;
import com.yisinian.mdfs.table.BlocksMessage;
import com.yisinian.mdfs.table.BlocksMessageDao;
import com.yisinian.mdfs.table.NodeMessage;
import com.yisinian.mdfs.table.NodeMessageDao;
import com.yisinian.mdfs.table.TaskMessage;
import com.yisinian.mdfs.table.TaskMessageDao;
import com.yisinian.mdfs.tool.TaskUtil;
import com.yisinian.mdfs.tool.UploadUtil;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 用于和服务器通信的类主体，所有的访问请求都在这个类下面
 * Created by zhuxu on 16/1/15.
 */
public class MDFSHttp {

    //各种http请求的URL
    private static final String nodeLoginUrl = "http://120.24.167.68:8082/nodeLogin.action"; //登录，提交节点信息请求请求
    private static final String nodeLogoutUrl = "http://120.24.167.68:8082/nodeLogout.action"; //节点离线的请求
    private static final String nodeUpdateUrl = "http://120.24.167.68:8082/nodeUpdate.action"; //节点状态更新的请求
    public static final String resultUpdateUrl = "http://120.24.167.68:8082/uploadTaskResult.action"; //提交任务结果
    public static final String uploadFileResult = "http://120.24.167.68:8082/uploadFileResult.action"; //提交文件下载信息

    public static NodeMessage nodeMessage;
    private static Map<String, String> updateNodeMessage;
    private static Handler handler;

    //节点接入服务器的时候，将本节点的节点信息上传到服务器，注册和登录都使用这个
    public static void nodeLogin(final Context context) {

        NodeMessage node = NodeMessageDao.getNodeMessage(context);
        //节点登录
        new Thread(new Runnable() {
            @Override
            public void run() {
                NodeMessage nodeMessage = NodeMessageDao.getNodeMessage(context);
                String url = nodeLoginUrl;
                Map<String, String> nodeMsg = new HashMap<String, String>();
                nodeMsg.put(NodeMessage.NODEMESSAGE_NODE_ID, nodeMessage.nodeId);
                Log.i("MDFS", "节点id：" + nodeMessage.nodeId);
                nodeMsg.put(NodeMessage.NODEMESSAGE_SYSTEM_ID, nodeMessage.systemId);
                nodeMsg.put(NodeMessage.NODEMESSAGE_NODE_NAME, nodeMessage.nodeName);
                nodeMsg.put(NodeMessage.NODEMESSAGE_COMPANY, nodeMessage.company);
                nodeMsg.put(NodeMessage.NODEMESSAGE_PHONE_TYPE, nodeMessage.phoneType);
                nodeMsg.put(NodeMessage.NODEMESSAGE_OS, nodeMessage.os);
                nodeMsg.put(NodeMessage.NODEMESSAGE_OS_VERSION, nodeMessage.osVersion);
                nodeMsg.put(NodeMessage.NODEMESSAGE_LOCAL_STORAGE, nodeMessage.localStorage);
                nodeMsg.put(NodeMessage.NODEMESSAGE_REST_LOCAL_STORAGE, nodeMessage.restLocalStorage);
                nodeMsg.put(NodeMessage.NODEMESSAGE_STORAGE, nodeMessage.storage);
                nodeMsg.put(NodeMessage.NODEMESSAGE_REST_STORAGE, nodeMessage.restStorage);
                nodeMsg.put(NodeMessage.NODEMESSAGE_RAM, nodeMessage.ram);
                nodeMsg.put(NodeMessage.NODEMESSAGE_CPU_FREQUENCY, nodeMessage.cpuFrequency);
                nodeMsg.put(NodeMessage.NODEMESSAGE_CORE_NUMBER, nodeMessage.coreNumber);
                nodeMsg.put(NodeMessage.NODEMESSAGE_NET_TYPE, nodeMessage.netType);
                nodeMsg.put(NodeMessage.NODEMESSAGE_NET_SPEED, nodeMessage.netSpeed);
                nodeMsg.put(NodeMessage.NODEMESSAGE_PHONE_MODEL, nodeMessage.phoneModel);
                nodeMsg.put(NodeMessage.NODEMESSAGE_IMEL, nodeMessage.imel);
                nodeMsg.put(NodeMessage.NODEMESSAGE_SERIAL_NUMBER, nodeMessage.serialNumber);
                nodeMsg.put(NodeMessage.NODEMESSAGE_JPUSH_ID, nodeMessage.jpushId);
                nodeMsg.put(NodeMessage.NODEMESSAGE_STATE, nodeMessage.state);
                nodeMsg.put(NodeMessage.NODEMESSAGE_START_TIME, nodeMessage.startTime);
                nodeMsg.put(NodeMessage.NODEMESSAGE_END_TIME, nodeMessage.endTime);

                String result = HttpUtils.getEntity(url, nodeMsg);
                Log.i(StringConstant.MDFS_TAG_HTTP, "请求参数：" + nodeMsg.toString());
            }
        }).start();
    }

    //节点关闭主activity或者服务，退出系统的时候，调用节点退出接口
    public static void nodeLogout(final Context context) {

        NodeMessage node = NodeMessageDao.getNodeMessage(context);
        //节点登出
        new Thread(new Runnable() {
            @Override
            public void run() {
                NodeMessage nodeMessage = NodeMessageDao.getNodeMessage(context);
                String url = nodeLogoutUrl;
                Map<String, String> nodeMsg = new HashMap<String, String>();
                nodeMsg.put(NodeMessage.NODEMESSAGE_NODE_ID, nodeMessage.nodeId);
                String result = HttpUtils.getEntity(url, nodeMsg);
                Log.i(StringConstant.MDFS_TAG_HTTP, "请求参数：" + nodeMsg.toString());
                Log.i(StringConstant.MDFS_TAG_HTTP, "返回值：" + result);
                Log.i(StringConstant.MDFS_TAG_DB, "-----节点退出系统----- 节点id：" + nodeMessage.nodeId);
                //更新手机本地数据库
                ContentValues cv = new ContentValues();
                cv.put(NodeMessage.NODEMESSAGE_NET_TYPE, "0");
                cv.put(NodeMessage.NODEMESSAGE_STATE, "0");
                cv.put(NodeMessage.NODEMESSAGE_NET_SPEED, "0");
                cv.put(NodeMessage.NODEMESSAGE_END_TIME, MDFSTime.getPhoneTime());
                NodeMessageDao.updateNodeMessageByNodeId(context, cv);

            }
        }).start();
    }

    //节点上传计算任务结果
    public static void uploadTaskResult(final Context context, final String url, final Map<String, String> taskMsg) {

        new Thread(new Runnable() {
            @Override
            public void run() {
                String result = HttpUtils.getEntity(url, taskMsg);
                Log.i(StringConstant.MDFS_TAG_HTTP, "返回值：" + result);
            }
        }).start();
    }

    //节点上传下载文件块结果
    public static void uploadFileResult(final Context context, final Map<String, String> fileMsg) {

        new Thread(new Runnable() {
            @Override
            public void run() {
                String result = HttpUtils.getEntity("http://120.24.167.68:8082/uploadFileResult.action", fileMsg);
                Log.i(StringConstant.MDFS_TAG_HTTP, "反馈文件块下载信息");
                Log.i(StringConstant.MDFS_TAG_HTTP, "反馈信息：" + fileMsg);
                Log.i(StringConstant.MDFS_TAG_HTTP, "反馈结果:" + result);
            }
        }).start();
    }

    //节点ping服务器
    public static void ping(final Context context) {

        new Thread(new Runnable() {
            @Override
            public void run() {
                Map<String, String> msg = new HashMap<String, String>();
                msg.put("a", "a");
                long time = System.currentTimeMillis();
                String result = HttpUtils.getEntity("http://120.24.167.68:8082/ping.action", msg);
                time = System.currentTimeMillis() - time;
                Log.i(StringConstant.MDFS_TAG_SERVICE, "ping服务器时延:" + time + "ms");
                //更新本地数据库
                ContentValues cv = new ContentValues();
                cv.put(NodeMessage.NODEMESSAGE_RELAY_TIME, time);
                NodeMessageDao.updateNodeMessageByNodeId(context, cv);
            }
        }).start();
    }

    //节点向服务器更新自身各种状态信息
    public static void updateNodeMessage(final Context context) {

        handler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                // TODO Auto-generated method stub
                Bundle fileMessage = msg.getData();
                String fileOption = fileMessage.getString("fileOption");
                if (fileOption.equals("download")) {
                    Toast.makeText(context, "接收文件", Toast.LENGTH_SHORT).show();
                } else if (fileOption.equals("delete")) {
                    Toast.makeText(context, "删除文件", Toast.LENGTH_SHORT).show();
                } else if (fileOption.equals("task")) {
                    Toast.makeText(context, "接收任务", Toast.LENGTH_SHORT).show();
                } else if (fileOption.equals("getLTFile")){
                    Toast.makeText(context, "回传LT码块", Toast.LENGTH_SHORT).show();
                }
            }
        };
        new Thread(new Runnable() {
            @Override
            public void run() {
                NodeMessage nodeMessage = NodeMessageDao.getNodeMessage(context);
                updateNodeMessage = new HashMap<String, String>();
                updateNodeMessage.put(NodeMessage.NODEMESSAGE_NODE_ID, nodeMessage.nodeId);
                updateNodeMessage.put(NodeMessage.NODEMESSAGE_NET_TYPE, nodeMessage.netType);
                updateNodeMessage.put(NodeMessage.NODEMESSAGE_NET_SPEED, nodeMessage.netSpeed);
                updateNodeMessage.put(NodeMessage.NODEMESSAGE_REST_LOCAL_STORAGE, nodeMessage.restLocalStorage);
                updateNodeMessage.put(NodeMessage.NODEMESSAGE_STORAGE, nodeMessage.storage);
                updateNodeMessage.put(NodeMessage.NODEMESSAGE_REST_STORAGE, nodeMessage.restStorage);
                updateNodeMessage.put(NodeMessage.NODEMESSAGE_STATE, nodeMessage.state);
                updateNodeMessage.put(NodeMessage.NODEMESSAGE_RELAY_TIME, nodeMessage.relayTime);


                String url = nodeUpdateUrl;
                //ping耗时
                //long time = System.currentTimeMillis();
                String result = HttpUtils.getEntity(url, updateNodeMessage);
                //time = System.currentTimeMillis()-time;
                //更新时延
//                ContentValues cv = new ContentValues();
//                cv.put(NodeMessage.NODEMESSAGE_RELAY_TIME,time);
//                NodeMessageDao.updateNodeMessageByNodeId(context, cv);
                //当有返回值的时候
                if (result != null) {
                    Log.i(StringConstant.MDFS_TAG_HTTP, "----------节点状态更新----------");
                    Log.i(StringConstant.MDFS_TAG_HTTP, "请求参数：" + updateNodeMessage.toString());
                    //Log.i(StringConstant.MDFS_TAG_HTTP, "返回值：" + result);
                    try {
                        JSONObject httpResults = new JSONObject(result);
                        String jsonResults = httpResults.getString("result");
                        //Log.i(StringConstant.MDFS_TAG_HTTP,"解析内容参数"+jsonResults);
                        JSONObject data = new JSONObject(jsonResults);
                        String statusCode = data.getString("statusCode");
                        Log.i(StringConstant.MDFS_TAG_HTTP, "返回码：" + statusCode);
                        //如果是成功码
                        if (statusCode.equals("10001")) {


                            //获取该节点上的分块文件信息
                            JSONArray blocks = data.getJSONArray("blockMsg");
                            //本地总共应该有的文件块个数
                            int blockNum = blocks.length();
                            Log.i(StringConstant.MDFS_TAG_FILE, "本地文件路径：" + context.getFilesDir().getAbsolutePath());

                            List<String> receiveBlocks = new ArrayList<String>();
                            //获取该节点上应该有的文件块名称集合，用于与本地的文件进行比较
                            for (int j = 0; j < blocks.length(); j++) {
                                JSONObject ablock = blocks.getJSONObject(j);
                                String blockName = ablock.getString("blockName");
                                receiveBlocks.add(blockName);
                            }
                            Log.i(StringConstant.MDFS_TAG_FILE, "节点上应该有的文件块：" + receiveBlocks.toString());


                            //本地如果有多余的已经被删除的文件块，那么把文件块删除掉
                            File localStoragedBlocks = new File(context.getFilesDir().getAbsolutePath() + "/blocks");
                            if (localStoragedBlocks.exists() && localStoragedBlocks.isDirectory()) {
                                File[] loclalBlocks = localStoragedBlocks.listFiles();
                                for (File a : loclalBlocks) {
                                    String blockName = a.getName();
                                    //如果这个文件块已经被删除，即不在应该有的文件名列表中，则删除本地这个文件块
                                    if (!(receiveBlocks.contains(blockName))) {
                                        //删除文件块
                                        a.delete();
                                        //删除本地数据库中文件块信息
                                        BlocksMessageDao.deleteOneBlockByBlockName(context, blockName);
                                        Log.i(StringConstant.MDFS_TAG_FILE, "删除文件：" + blockName);
                                        //将删除文件的操作通过handler传出线程
                                        Message fileFileOption = new Message();
                                        Bundle fileMessage = new Bundle();
                                        fileMessage.putString("fileOption", "delete");
                                        fileFileOption.setData(fileMessage);
                                        handler.sendMessage(fileFileOption);
                                        //Toast.makeText(context,"删除文件",Toast.LENGTH_SHORT).show();
                                    }
                                }
                            }
                            //文件块个数大于0的时候进行下部分操作
                            if (blockNum > 0) {
                                //分块信息不为空的时候
                                for (int i = 0; i < blocks.length(); i++) {
                                    JSONObject ablock = blocks.getJSONObject(i);
                                    String blockName = ablock.getString("blockName");
                                    String blockId = ablock.getString("blockId");
                                    String nodeId = ablock.getString("nodeId");
                                    String fileId = ablock.getString("fileId");
                                    String blockSize = ablock.getString("blockSize");
                                    String fileSerialNumber = ablock.getString("fileSerialNumber");
                                    String blockPath = ablock.getString("blockPath");
                                    String blockSetTime = ablock.getString("blockSetTime");
                                    String state = ablock.getString("state");
                                    String stateTime = ablock.getString("stateTime");

                                    File blockFile = new File(UrlConstant.blocksLocalPathUrl + blockName);
                                    //这个分块文件在本地不存在的话，去服务器下载
                                    if (!blockFile.exists()) {
                                        //去服务器拿数据
                                        HttpDownloadUtil.downloadBlocksFromWebByBlockName(blockName, blockId, context);
                                        BlocksMessageDao blocksMessageDao = new BlocksMessageDao();
                                        BlocksMessage blocksMessage = new BlocksMessage();
                                        blocksMessage.blockId = blockId;
                                        blocksMessage.blockName = blockName;
                                        blocksMessage.nodeId = nodeId;
                                        blocksMessage.fileId = fileId;
                                        blocksMessage.blockSize = blockSize;
                                        blocksMessage.fileSerialNumber = fileSerialNumber;
                                        blocksMessage.blockPath = blockPath;
                                        blocksMessage.blockSetTime = blockSetTime;
                                        blocksMessage.state = state;
                                        blocksMessage.stateTime = stateTime;
                                        //将分块文件信息存到数据库当中
                                        blocksMessageDao.saveBlocksMessage(context, blocksMessage);
                                        //将下载文件的操作通过handler传出线程
                                        Message fileFileOption = new Message();
                                        Bundle fileMessage = new Bundle();
                                        fileMessage.putString("fileOption", "download");
                                        fileFileOption.setData(fileMessage);
                                        handler.sendMessage(fileFileOption);
//                                        Toast.makeText(context,"下载文件",Toast.LENGTH_SHORT).show();
                                        //跳出循环，每次只下载一个
                                        Log.i(StringConstant.MDFS_TAG_FILE, "下载文件：" + blockName);
                                        break;
                                    }

                                }
                            }

                            //获取节点上应该上传的文件块信息,并开始上传文件块
                            JSONArray blockIds = data.getJSONArray("fileUploadMsg");
                            Log.i(StringConstant.MDFS_TAG_FILE, "节点上应该上传的文件块：" + blockIds);
                            int blocksNum = blockIds.length();
                            if (blocksNum>0){
                                //将下接收的LT码回收任务通过handler传出线程
                                Message fileFileOption = new Message();
                                Bundle fileMessage = new Bundle();
                                fileMessage.putString("fileOption", "getLTFile");
                                fileFileOption.setData(fileMessage);
                                handler.sendMessage(fileFileOption);
                                int num=0;
                                for (int i=0;i<blocksNum;i++){
                                    String aBlockId=  blockIds.get(i)+"";
                                    UploadUtil.uploadLTCodeFile(context,aBlockId);
                                    num++;
                                    //每次回传两个文件
                                    if (num==2){
                                        break;
                                    }

                                }
                            }

                            //接收并执行相关任务
                            JSONArray tasks = data.getJSONArray("taskMsg");
                            int taskNum = tasks.length();
                            //任务不为空的时候
                            if (taskNum > 0) {
                                //将下接收任务的操作通过handler传出线程
                                Message fileFileOption = new Message();
                                Bundle fileMessage = new Bundle();
                                fileMessage.putString("fileOption", "task");
                                fileFileOption.setData(fileMessage);
                                handler.sendMessage(fileFileOption);
                                for (int i = 0; i < taskNum; i++) {
                                    JSONObject aTask = (JSONObject) tasks.get(i);
                                    String nodeTaskId = aTask.getString("nodeTaskId");
                                    String taskId = aTask.getString("taskId");
                                    String taskType = aTask.getString("taskType");
                                    String blockId = aTask.getString("blockId");
                                    String content = aTask.getString("content");
                                    String finishTime = "0";
                                    //将任务信息保存到数据库当中
                                    TaskMessage aTaskMsg = new TaskMessage();
                                    aTaskMsg.nodeTaskId = nodeTaskId;
                                    aTaskMsg.taskId = taskId;
                                    aTaskMsg.taskType = taskType;
                                    aTaskMsg.content = content;
                                    aTaskMsg.finishTime = finishTime;
                                    aTaskMsg.blockId = blockId;
                                    TaskMessageDao.saveTaskMessage(context, aTaskMsg);
                                    //如果是文件检索类任务
                                    if (aTaskMsg.taskType.equals("文件检索")) {
                                        TaskUtil.searchFileA(context, aTaskMsg);
                                        break;
                                    }
                                }
                            }
                        }

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }

            }
        }).start();
    }

}
