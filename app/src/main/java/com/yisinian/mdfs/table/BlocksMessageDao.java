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
public class BlocksMessageDao {

    public static String sql$BlocksMessageTable(){
        String sql = "create table if not exists " + BlocksMessage.BLOCKMESSAGE_TABLE + " ("
                + BlocksMessage.BLOCKMESSAGE_BLOCK_ID  + " TEXT default 0,"
                + BlocksMessage.BLOCKMESSAGE_FILE_ID  + " TEXT default 0,"
                + BlocksMessage.BLOCKMESSAGE_NODE_ID + " TEXT default 0,"
                + BlocksMessage.BLOCKMESSAGE_BLOCK_NAME+ " TEXT default 0,"
                + BlocksMessage.BLOCKMESSAGE_FILE_SERIAL_NUMBER+ " TEXT default 0,"
                + BlocksMessage.BLOCKMESSAGE_BLOCK_PATH+ " TEXT default 0,"
                + BlocksMessage.BLOCKMESSAGE_BLOCK_SIZE+ " TEXT default 0,"
                + BlocksMessage.BLOCKMESSAGE_BLOCK_SET_TIME+ " TEXT default 0,"
                + BlocksMessage.BLOCKMESSAGE_STATE+ " TEXT default 0,"
                + BlocksMessage.BLOCKMESSAGE_STATE_TIME+ " TEXT default 0 "
                + ");";
        return sql;

    }
    /**保存一个BlocksMessage的一个实例
     * @param context
     * @param entity
     */
    public static void saveBlocksMessage(Context context, BlocksMessage entity){
        DBHelper dbHelper = new DBHelper(context);
        dbHelper.open();
        ContentValues values = new ContentValues();
        values.put(BlocksMessage.BLOCKMESSAGE_BLOCK_ID, entity.blockId);
        values.put(BlocksMessage.BLOCKMESSAGE_FILE_ID, entity.fileId);
        values.put(BlocksMessage.BLOCKMESSAGE_NODE_ID, entity.nodeId);
        values.put(BlocksMessage.BLOCKMESSAGE_BLOCK_NAME, entity.blockName);
        values.put(BlocksMessage.BLOCKMESSAGE_FILE_SERIAL_NUMBER, entity.fileSerialNumber);
        values.put(BlocksMessage.BLOCKMESSAGE_BLOCK_PATH, entity.blockPath);
        values.put(BlocksMessage.BLOCKMESSAGE_BLOCK_SIZE, entity.blockSize);
        values.put(BlocksMessage.BLOCKMESSAGE_BLOCK_SET_TIME, entity.blockSetTime);
        values.put(BlocksMessage.BLOCKMESSAGE_STATE, entity.state);
        values.put(BlocksMessage.BLOCKMESSAGE_STATE_TIME, entity.stateTime);

        Cursor c = dbHelper.query(BlocksMessage.BLOCKMESSAGE_TABLE, null, BlocksMessage.BLOCKMESSAGE_BLOCK_ID + "=?", new String[]{entity.blockId}, null, null, null);
        if(c.getCount() > 0){//查询到数据库有该数据，就更新该行数据
            dbHelper.update(BlocksMessage.BLOCKMESSAGE_TABLE, values,
                    BlocksMessage.BLOCKMESSAGE_BLOCK_ID + "=?", new String[]{entity.blockId});
        }else{

            dbHelper.insert(BlocksMessage.BLOCKMESSAGE_TABLE, values);
            Log.i(StringConstant.MDFS_TAG_DB, "插入分块文件数据库：" + entity.blockId);
        }
        if(c != null && !c.isClosed()){
            c.close();
        }
		dbHelper.close();
    }
    /**根据分块id找出文件块信息
     * @param context
     * @return locksMessage
     */
    public static BlocksMessage  getOneBlocksMessageByBlockId(Context context,String blockId) {
        DBHelper dbHelper = new DBHelper(context);
        dbHelper.open();
        BlocksMessage block = new BlocksMessage();

        Cursor c = dbHelper.query(BlocksMessage.BLOCKMESSAGE_TABLE, null, BlocksMessage.BLOCKMESSAGE_BLOCK_ID + "=?", new String[]{blockId}, null, null, null);
        if (c!=null){
            while (c.moveToNext()){
                BlocksMessage aBlock  = new BlocksMessage();
                aBlock.blockId=c.getString(c.getColumnIndex(BlocksMessage.BLOCKMESSAGE_BLOCK_ID));
                aBlock.blockName=c.getString(c.getColumnIndex(BlocksMessage.BLOCKMESSAGE_BLOCK_NAME));
                aBlock.blockSize=c.getString(c.getColumnIndex(BlocksMessage.BLOCKMESSAGE_BLOCK_SIZE));
                aBlock.blockPath=c.getString(c.getColumnIndex(BlocksMessage.BLOCKMESSAGE_BLOCK_PATH));
                aBlock.fileId=c.getString(c.getColumnIndex(BlocksMessage.BLOCKMESSAGE_FILE_ID));
                aBlock.nodeId=c.getString(c.getColumnIndex(BlocksMessage.BLOCKMESSAGE_NODE_ID));
                aBlock.fileSerialNumber=c.getString(c.getColumnIndex(BlocksMessage.BLOCKMESSAGE_FILE_SERIAL_NUMBER));
                aBlock.state=c.getString(c.getColumnIndex(BlocksMessage.BLOCKMESSAGE_STATE));
                aBlock.stateTime=c.getString(c.getColumnIndex(BlocksMessage.BLOCKMESSAGE_STATE_TIME));
                aBlock.blockSetTime=c.getString(c.getColumnIndex(BlocksMessage.BLOCKMESSAGE_BLOCK_SET_TIME));
                block=aBlock;
                break;
            }
        }

        if (c != null && !c.isClosed()) {
            c.close();
        }
        dbHelper.close();
        return block;
    }

    /**所有的文件分块信息
     * @param context
     * @return List<BlocksMessage>
     */
    public static List<BlocksMessage>  getAllBlocksMessage(Context context) {
        DBHelper dbHelper = new DBHelper(context);
        dbHelper.open();
        List<BlocksMessage> blocks = new ArrayList<BlocksMessage>();

        Cursor c = dbHelper.query(BlocksMessage.BLOCKMESSAGE_TABLE, null, null, null, null, null, null);
        if (c!=null){
            while (c.moveToNext()){
                BlocksMessage aBlock  = new BlocksMessage();
                aBlock.blockId=c.getString(c.getColumnIndex(BlocksMessage.BLOCKMESSAGE_BLOCK_ID));
                aBlock.blockName=c.getString(c.getColumnIndex(BlocksMessage.BLOCKMESSAGE_BLOCK_NAME));
                aBlock.blockSize=c.getString(c.getColumnIndex(BlocksMessage.BLOCKMESSAGE_BLOCK_SIZE));
                aBlock.blockPath=c.getString(c.getColumnIndex(BlocksMessage.BLOCKMESSAGE_BLOCK_PATH));
                aBlock.fileId=c.getString(c.getColumnIndex(BlocksMessage.BLOCKMESSAGE_FILE_ID));
                aBlock.nodeId=c.getString(c.getColumnIndex(BlocksMessage.BLOCKMESSAGE_NODE_ID));
                aBlock.fileSerialNumber=c.getString(c.getColumnIndex(BlocksMessage.BLOCKMESSAGE_FILE_SERIAL_NUMBER));
                aBlock.state=c.getString(c.getColumnIndex(BlocksMessage.BLOCKMESSAGE_STATE));
                aBlock.stateTime=c.getString(c.getColumnIndex(BlocksMessage.BLOCKMESSAGE_STATE_TIME));
                aBlock.blockSetTime=c.getString(c.getColumnIndex(BlocksMessage.BLOCKMESSAGE_BLOCK_SET_TIME));
                blocks.add(aBlock);
            }
        }

        if (c != null && !c.isClosed()) {
            c.close();
        }
		dbHelper.close();

        return blocks;
    }

    /*
    * 根据文件块ID删除某一条block记录
    * @param blockId
    * @retrun null
    * */
    public static void deleteOneBlockByBlockId(Context context,String blockId){
        DBHelper dbHelper = new DBHelper(context);
        dbHelper.open();
        dbHelper.delete(BlocksMessage.BLOCKMESSAGE_TABLE, BlocksMessage.BLOCKMESSAGE_BLOCK_ID + "=?", new String[]{blockId});
        Log.i(StringConstant.MDFS_TAG_DB,"删除blockId="+blockId+"的数据");
        dbHelper.close();
    }
    /*
    * 删除文件名删除某一条block记录
    * @param blockName
    * @retrun null
    * */
    public static void deleteOneBlockByBlockName(Context context,String blockName){
        DBHelper dbHelper = new DBHelper(context);
        dbHelper.open();
        dbHelper.delete(BlocksMessage.BLOCKMESSAGE_TABLE, BlocksMessage.BLOCKMESSAGE_BLOCK_NAME + "=?", new String[]{blockName});
        Log.i(StringConstant.MDFS_TAG_DB,"删除blockName="+blockName+"的数据");
        dbHelper.close();
    }

    /*
    * 通过blockId更新某一条block记录
    * @param blockId
    * @retrun null
    * */

    public static void updateOneBlockByBlockId(Context context,ContentValues values,String blockId) {
        DBHelper dbHelper = new DBHelper(context);
        dbHelper.open();
        dbHelper.update(BlocksMessage.BLOCKMESSAGE_TABLE,values,BlocksMessage.BLOCKMESSAGE_BLOCK_ID+"=?",new String[]{blockId});
        Log.i(StringConstant.MDFS_TAG_DB, "更新blockId=" + blockId + "的数据");
        dbHelper.close();
    }
}
