package com.yisinian.mdfs.table;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.util.Log;

import com.yisinian.mdfs.constant.StringConstant;
import com.yisinian.mdfs.database.DBHelper;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zhuxu on 16/1/12.
 */
public class TaskMessageDao {

    public static String sql$TasksMessageTable(){
        String sql = "create table if not exists " + TaskMessage.TASKMESSAGE_TABLE + " ("
                + TaskMessage.NODE_TASK_ID  + " TEXT default 0,"
                + TaskMessage.TASK_ID  + " TEXT default 0,"
                + TaskMessage.TASK_TYPE + " TEXT default 0,"
                + TaskMessage.CONTENT+ " TEXT default 0,"
                + TaskMessage.BLOCK_ID+ " TEXT default 0,"
                + TaskMessage.FINISH_TIME+ " TEXT default 0"
                + ");";
        return sql;

    }
    /**保存一个TaskMessage的一个实例
     * @param context
     * @param entity
     */
    public static void saveTaskMessage(Context context, TaskMessage entity){
        DBHelper dbHelper = new DBHelper(context);
        dbHelper.open();
        ContentValues values = new ContentValues();
        values.put(TaskMessage.NODE_TASK_ID, entity.nodeTaskId);
        values.put(TaskMessage.BLOCK_ID, entity.blockId);
        values.put(TaskMessage.TASK_ID, entity.taskId);
        values.put(TaskMessage.TASK_TYPE, entity.taskType);
        values.put(TaskMessage.CONTENT, entity.content);
        values.put(TaskMessage.FINISH_TIME, entity.finishTime);

        Cursor c = dbHelper.query(TaskMessage.TASKMESSAGE_TABLE, null, TaskMessage.NODE_TASK_ID + "=?", new String[]{entity.nodeTaskId}, null, null, null);
        if(c.getCount() > 0){//查询到数据库有该数据，就更新该行数据
            dbHelper.update(TaskMessage.TASKMESSAGE_TABLE, values,
                    TaskMessage.NODE_TASK_ID + "=?", new String[]{entity.nodeTaskId});
        }else{

            dbHelper.insert(TaskMessage.TASKMESSAGE_TABLE, values);
            Log.i(StringConstant.MDFS_TAG_DB, "插入任务数据库：" + entity.nodeTaskId);
        }
        if(c != null && !c.isClosed()){
            c.close();
        }
		dbHelper.close();
    }

    /**所有的任务信息
     * @param context
     * @return List<BlocksMessage>
     */
    public static List<TaskMessage>  getAllTasksMessage(Context context) {
        DBHelper dbHelper = new DBHelper(context);
        dbHelper.open();
        List<TaskMessage> tasks = new ArrayList<TaskMessage>();

        Cursor c = dbHelper.query(TaskMessage.TASKMESSAGE_TABLE, null, null, null, null, null, null);
        if (c!=null){
            while (c.moveToNext()){
                TaskMessage aTask  = new TaskMessage();
                aTask.nodeTaskId=c.getString(c.getColumnIndex(TaskMessage.NODE_TASK_ID));
                aTask.taskId=c.getString(c.getColumnIndex(TaskMessage.TASK_ID));
                aTask.taskType=c.getString(c.getColumnIndex(TaskMessage.TASK_TYPE));
                aTask.blockId=c.getString(c.getColumnIndex(TaskMessage.BLOCK_ID));
                aTask.content=c.getString(c.getColumnIndex(TaskMessage.CONTENT));
                aTask.finishTime=c.getString(c.getColumnIndex(TaskMessage.FINISH_TIME));
                tasks.add(aTask);
            }
        }
        if (c != null && !c.isClosed()) {
            c.close();
        }
		dbHelper.close();
        return tasks;
    }

}
