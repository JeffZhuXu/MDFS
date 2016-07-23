package com.yisinian.mdfs.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.yisinian.mdfs.table.BlocksMessageDao;
import com.yisinian.mdfs.table.NodeMessageDao;
import com.yisinian.mdfs.table.TaskMessageDao;

/**
 * @author Teancher.Wang
 *
 */
public class DBHelper  {
    private final String TAG = getClass().getSimpleName();
    // DDL操作
    private static DatabaseHelper dbHelper;
    // DML操作
    private static SQLiteDatabase db;
    // 数据库名
    private static final String DATABASE_NAME = "mdfsdb.db";
    // 数据库版本
    private static final int DATABASE_VERSION = 1;
    // 上下文环境
    private Context mCtx=null;
    //印用于打

    /* SQliteOpenHelper是一个抽象类，来管理数据库的创建和版本的管理 */
    private static class DatabaseHelper extends SQLiteOpenHelper{

        DatabaseHelper(Context context) {

            super(context, DATABASE_NAME, null, DATABASE_VERSION);
            // TODO Auto-generated constructor stub
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            // TODO Auto-generated method stub

            //文件分块信息表
            String blocksMessage = BlocksMessageDao.sql$BlocksMessageTable();
            db.execSQL(blocksMessage);
            //节点信息表
            String nodeMessage = NodeMessageDao.sql$NodeMessageTable();
            db.execSQL(nodeMessage);
            //任务信息表
            String taskMassage = TaskMessageDao.sql$TasksMessageTable();
            db.execSQL(taskMassage);

        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        }
    }

    public DBHelper(Context context){
        this.mCtx=context;
    }

    public DBHelper open() throws SQLException{
        dbHelper=new DatabaseHelper(mCtx);
        db=dbHelper.getWritableDatabase();
        return this;
    }

    public SQLiteDatabase getSQLiteDatabase(){
        return db;
    }
    public void close(){
        dbHelper.close();
        Log.e(TAG, "db、dbHelper close()");
    }

    /**
     * 插入数据
     * 参数：tableName 表名
     * initialValues 要插入的列对应值
     **/
    public long insert(String tableName,ContentValues values){
        return db.insert(tableName, null, values);

    }

    /**
     * 删除数据
     * 参数：tableName 表名
     * deleteCondition 删除的条件
     * deleteArgs 如果deleteCondition中有“？”号，将用此数组中的值替换
     **/
    public int delete(String tableName,String deleteCondition,String[] deleteArgs){
        int returnValue=db.delete(tableName, deleteCondition, deleteArgs);
        return returnValue;
    }

    /**
     * 更新数据
     * 参数：tableName 表名
     * initialValues 要更新的列
     * selection 更新的条件
     * selectArgs 如果selection中有“？”号，将用此数组中的值替换
     *   */

    public int update(String tableName,ContentValues values,String selection,String[] selectArgs){
        int returnValue=db.update(tableName, values, selection, selectArgs);
        return returnValue;
    }



    /**
     * 取得一个列表
     * 参数：tableName 表名
     * columns 返回的列
     * selection 查询条件
     * selectArgs 如果selection中有“？”号，将用此数组中的值替换
     *   */
    public Cursor query(String tableName,String[] columns,String selection,String[] selectionArgs, String groupBy, String having, String orderBy){
        return db.query(tableName, columns, selection, selectionArgs, groupBy, having, orderBy);

    }

}