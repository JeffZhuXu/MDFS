package com.yisinian.mdfs.table;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.telephony.TelephonyManager;
import android.util.Log;

import com.yisinian.mdfs.constant.StringConstant;
import com.yisinian.mdfs.database.DBHelper;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by zhuxu on 16/1/12.
 */
public class NodeMessageDao {

    public static String sql$NodeMessageTable() {
        String sql = "create table if not exists " + NodeMessage.NODEMESSAGE_TABLE + " ("
                + NodeMessage.NODEMESSAGE_NODE_ID + " TEXT default 0,"
                + NodeMessage.NODEMESSAGE_SYSTEM_ID + " TEXT default 0,"
                + NodeMessage.NODEMESSAGE_NODE_NAME + " TEXT default 0,"
                + NodeMessage.NODEMESSAGE_COMPANY + " TEXT default 0,"
                + NodeMessage.NODEMESSAGE_PHONE_TYPE + " TEXT default 0,"
                + NodeMessage.NODEMESSAGE_OS + " TEXT default 0,"
                + NodeMessage.NODEMESSAGE_OS_VERSION + " TEXT default 0,"
                + NodeMessage.NODEMESSAGE_LOCAL_STORAGE + " TEXT default 0,"
                + NodeMessage.NODEMESSAGE_REST_LOCAL_STORAGE + " TEXT default 0,"
                + NodeMessage.NODEMESSAGE_STORAGE + " TEXT default 0,"
                + NodeMessage.NODEMESSAGE_REST_STORAGE + " TEXT default 0,"
                + NodeMessage.NODEMESSAGE_RAM + " TEXT default 0,"
                + NodeMessage.NODEMESSAGE_CPU_FREQUENCY + " TEXT default 0,"
                + NodeMessage.NODEMESSAGE_CORE_NUMBER + " TEXT default 0,"
                + NodeMessage.NODEMESSAGE_NET_TYPE + " TEXT default 0,"
                + NodeMessage.NODEMESSAGE_NET_SPEED + " TEXT default 0,"
                + NodeMessage.NODEMESSAGE_PHONE_MODEL + " TEXT default 0,"
                + NodeMessage.NODEMESSAGE_IMEL + " TEXT default 0,"
                + NodeMessage.NODEMESSAGE_SERIAL_NUMBER + " TEXT default 0,"
                + NodeMessage.NODEMESSAGE_JPUSH_ID + " TEXT default 0,"
                + NodeMessage.NODEMESSAGE_STATE + " TEXT default 0,"
                + NodeMessage.NODEMESSAGE_START_TIME + " TEXT default 0,"
                + NodeMessage.NODEMESSAGE_END_TIME + " TEXT default 0,"
                + NodeMessage.NODEMESSAGE_RELAY_TIME + " TEXT default 0"
                + ");";
        return sql;
    }

    /**
     * 保存一个NodeMessage的一个实例
     *
     * @param context
     * @param entity
     */
    public static void saveNodeMessage(Context context, NodeMessage entity) {
        DBHelper dbHelper = new DBHelper(context);
        dbHelper.open();
        //由于nodemessage表中只有一条数据，插入之前默认数据数据全部删除
        dbHelper.delete(NodeMessage.NODEMESSAGE_TABLE, NodeMessage.NODEMESSAGE_NODE_ID + " =?", new String[]{"0"});

        ContentValues values = new ContentValues();
        values.put(NodeMessage.NODEMESSAGE_NODE_ID, entity.nodeId);
        values.put(NodeMessage.NODEMESSAGE_SYSTEM_ID, entity.systemId);
        values.put(NodeMessage.NODEMESSAGE_NODE_NAME, entity.nodeName);
        values.put(NodeMessage.NODEMESSAGE_COMPANY, entity.company);
        values.put(NodeMessage.NODEMESSAGE_PHONE_TYPE, entity.phoneType);
        values.put(NodeMessage.NODEMESSAGE_OS, entity.os);
        values.put(NodeMessage.NODEMESSAGE_OS_VERSION, entity.osVersion);
        values.put(NodeMessage.NODEMESSAGE_LOCAL_STORAGE, entity.localStorage);
        values.put(NodeMessage.NODEMESSAGE_REST_LOCAL_STORAGE, entity.restLocalStorage);
        values.put(NodeMessage.NODEMESSAGE_STORAGE, entity.storage);
        values.put(NodeMessage.NODEMESSAGE_REST_STORAGE, entity.restStorage);
        values.put(NodeMessage.NODEMESSAGE_RAM, entity.ram);
        values.put(NodeMessage.NODEMESSAGE_CPU_FREQUENCY, entity.cpuFrequency);
        values.put(NodeMessage.NODEMESSAGE_CORE_NUMBER, entity.coreNumber);
        values.put(NodeMessage.NODEMESSAGE_NET_TYPE, entity.netType);
        values.put(NodeMessage.NODEMESSAGE_NET_SPEED, entity.netSpeed);
        values.put(NodeMessage.NODEMESSAGE_PHONE_MODEL, entity.phoneModel);
        values.put(NodeMessage.NODEMESSAGE_IMEL, entity.imel);
        values.put(NodeMessage.NODEMESSAGE_SERIAL_NUMBER, entity.serialNumber);
        values.put(NodeMessage.NODEMESSAGE_JPUSH_ID, entity.jpushId);
        values.put(NodeMessage.NODEMESSAGE_STATE, entity.state);
        values.put(NodeMessage.NODEMESSAGE_START_TIME, entity.startTime);
        values.put(NodeMessage.NODEMESSAGE_END_TIME, entity.endTime);
        values.put(NodeMessage.NODEMESSAGE_RELAY_TIME, entity.relayTime);
        Cursor c = dbHelper.query(NodeMessage.NODEMESSAGE_TABLE, null, NodeMessage.NODEMESSAGE_NODE_ID + " =?", new String[]{entity.nodeId}, null, null, null);
        if (c.getCount() > 0) {//查询到数据库中该节点信息已经有了的话
            dbHelper.update(NodeMessage.NODEMESSAGE_TABLE, values,
                    NodeMessage.NODEMESSAGE_NODE_ID + "=?", new String[]{entity.nodeId});
        } else {
            dbHelper.insert(NodeMessage.NODEMESSAGE_TABLE, values);
            Log.i(StringConstant.MDFS_TAG_DB, "node_message表中插入数据，数据id：" + entity.nodeId);
        }
        if (c != null && !c.isClosed()) {
            c.close();
        }
        dbHelper.close();
    }


    /**
     * 查看节点信息
     *
     * @param context
     * @return BlocksMessage a
     */
    public static NodeMessage getNodeMessage(Context context) {
        DBHelper dbHelper = new DBHelper(context);
        dbHelper.open();
        Cursor c = dbHelper.query(NodeMessage.NODEMESSAGE_TABLE, null, null, null, null, null, null);
        NodeMessage aNode = new NodeMessage();
        if (c != null && c.moveToFirst()) {
            while (c.moveToFirst()) {
                aNode.nodeId = c.getString(c.getColumnIndex(NodeMessage.NODEMESSAGE_NODE_ID));
                Log.i(StringConstant.MDFS_TAG_DB, "节点id：" + c.getString(c.getColumnIndex(NodeMessage.NODEMESSAGE_NODE_ID)));
                aNode.systemId = c.getString(c.getColumnIndex(NodeMessage.NODEMESSAGE_SYSTEM_ID));
                aNode.nodeName = c.getString(c.getColumnIndex(NodeMessage.NODEMESSAGE_NODE_NAME));
                aNode.company = c.getString(c.getColumnIndex(NodeMessage.NODEMESSAGE_COMPANY));
                aNode.phoneType = c.getString(c.getColumnIndex(NodeMessage.NODEMESSAGE_PHONE_TYPE));
                aNode.os = c.getString(c.getColumnIndex(NodeMessage.NODEMESSAGE_OS));
                aNode.osVersion = c.getString(c.getColumnIndex(NodeMessage.NODEMESSAGE_OS_VERSION));
                aNode.localStorage = c.getString(c.getColumnIndex(NodeMessage.NODEMESSAGE_LOCAL_STORAGE));
                aNode.restLocalStorage = c.getString(c.getColumnIndex(NodeMessage.NODEMESSAGE_REST_LOCAL_STORAGE));
                aNode.storage = c.getString(c.getColumnIndex(NodeMessage.NODEMESSAGE_STORAGE));
                aNode.restStorage = c.getString(c.getColumnIndex(NodeMessage.NODEMESSAGE_REST_STORAGE));
                aNode.ram = c.getString(c.getColumnIndex(NodeMessage.NODEMESSAGE_RAM));
                aNode.cpuFrequency = c.getString(c.getColumnIndex(NodeMessage.NODEMESSAGE_CPU_FREQUENCY));
                aNode.coreNumber = c.getString(c.getColumnIndex(NodeMessage.NODEMESSAGE_CORE_NUMBER));
                aNode.netType = c.getString(c.getColumnIndex(NodeMessage.NODEMESSAGE_NET_TYPE));
                aNode.netSpeed = c.getString(c.getColumnIndex(NodeMessage.NODEMESSAGE_NET_SPEED));
                aNode.phoneModel = c.getString(c.getColumnIndex(NodeMessage.NODEMESSAGE_PHONE_MODEL));
                aNode.imel = c.getString(c.getColumnIndex(NodeMessage.NODEMESSAGE_IMEL));
                aNode.serialNumber = c.getString(c.getColumnIndex(NodeMessage.NODEMESSAGE_SERIAL_NUMBER));
                aNode.jpushId = c.getString(c.getColumnIndex(NodeMessage.NODEMESSAGE_JPUSH_ID));
                aNode.state = c.getString(c.getColumnIndex(NodeMessage.NODEMESSAGE_STATE));
                aNode.startTime = c.getString(c.getColumnIndex(NodeMessage.NODEMESSAGE_START_TIME));
                aNode.endTime = c.getString(c.getColumnIndex(NodeMessage.NODEMESSAGE_END_TIME));
                aNode.relayTime = c.getString(c.getColumnIndex(NodeMessage.NODEMESSAGE_RELAY_TIME));
                break;
            }
        }
        if (c != null && !c.isClosed()) {
            c.close();
        }
        dbHelper.close();
        return aNode;
    }

    //判断数据库里是否有节点信息，即节点是否在数据库里注册,已经注册返回true，没注册返回false
    public static boolean getLogin(Context context) {
        DBHelper dbHelper = new DBHelper(context);
        dbHelper.open();
        //获取手机ID
        TelephonyManager tm = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
        String nodeId = tm.getDeviceId();
        Log.i(StringConstant.MDFS_TAG_DB, "手机ID：" + nodeId);
        Cursor c = dbHelper.query(NodeMessage.NODEMESSAGE_TABLE, null, NodeMessage.NODEMESSAGE_NODE_ID + " =?", new String[]{nodeId}, null, null, null);
        boolean state;
        if (c != null && c.moveToFirst()) {
            state = true;
            Log.i(StringConstant.MDFS_TAG_DB, "手机信息是否已存在：" + state);
        } else {
            state = false;
            Log.i(StringConstant.MDFS_TAG_DB, "手机信息是否已存在：" + state);
        }
        if (c != null && !c.isClosed()) {
            c.close();
        }
        dbHelper.close();
        return state;
    }

    /*
    * 更新node记录
    * @param nodeId
    * @retrun null
    * */
    public static void updateNodeMessageByNodeId(Context context, ContentValues values) {
        DBHelper dbHelper = new DBHelper(context);
        dbHelper.open();
        dbHelper.update(NodeMessage.NODEMESSAGE_TABLE, values, null, null);
        Log.i(StringConstant.MDFS_TAG_DB, "更新node数据");
        dbHelper.close();
    }

    /*
* 更新node状态,有限的固定的几个参数
* @param nodeId
* @param net_type
* @param net_speed
* @param state
* @retrun null
* 更新完成后，同时保存到服务器
* */
    public static void updateNodeStatus(Context context) {
        DBHelper dbHelper = new DBHelper(context);
        dbHelper.open();
        //服务器的请求参数
        Map<String, String> nodeMessage = new HashMap<String, String>();
        //保存到本地数据库参数
        ContentValues values = new ContentValues();

        //状态参数
        String restLocalStorage = NodeMessage.getPhoneRestStorage(); //当前的手机本地剩余存储容量
        String storage = NodeMessage.NODEMESSAGE_STORAGE; //MDFS的总容量
        String restStorage = NodeMessage.NODEMESSAGE_REST_STORAGE; //MDFS剩余的存储容量
        String netType = NodeMessage.NODEMESSAGE_NET_TYPE;//网络连接类型
        String netSpeed = NodeMessage.NODEMESSAGE_NET_SPEED;//当前网速
        String state = NodeMessage.NODEMESSAGE_STATE;   //当前状态


        values.put(NodeMessage.NODEMESSAGE_REST_LOCAL_STORAGE, NodeMessage.getPhoneRestStorage());  //手机本地剩余的存储空间
        values.put(NodeMessage.NODEMESSAGE_STORAGE, "1024"); //MDFS总的存储容量
        values.put(NodeMessage.NODEMESSAGE_REST_STORAGE, "1024");  //MDFS剩余的存储空间
        values.put(NodeMessage.NODEMESSAGE_STATE, "1");
        values.put(NodeMessage.NODEMESSAGE_NET_SPEED, "111"); //当前网速网速
        values.put(NodeMessage.NODEMESSAGE_NET_TYPE, "wifi"); //当前网络连接类型

        nodeMessage.put(NodeMessage.NODEMESSAGE_NODE_ID, NodeMessage.getPhoneId(context));


        dbHelper.update(NodeMessage.NODEMESSAGE_TABLE, values, null, null);
        Log.i(StringConstant.MDFS_TAG_DB, "-------更新node状态信息------");
        dbHelper.close();
    }
}
