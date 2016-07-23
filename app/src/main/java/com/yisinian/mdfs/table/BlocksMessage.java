package com.yisinian.mdfs.table;

/**
 * Created by zhuxu on 16/1/12.
 */
public class BlocksMessage {


    public static final String BLOCKMESSAGE_TABLE="blocks_message";
    public static final String BLOCKMESSAGE_BLOCK_ID="block_id";
    public static final String BLOCKMESSAGE_FILE_ID="file_id";
    public static final String BLOCKMESSAGE_NODE_ID="node_id";
    public static final String BLOCKMESSAGE_BLOCK_NAME="block_name";
    public static final String BLOCKMESSAGE_FILE_SERIAL_NUMBER="file_serial_number";
    public static final String BLOCKMESSAGE_BLOCK_PATH="block_path";
    public static final String BLOCKMESSAGE_BLOCK_SIZE="block_size";
    public static final String BLOCKMESSAGE_BLOCK_SET_TIME="block_set_time";
    public static final String BLOCKMESSAGE_STATE="state";
    public static final String BLOCKMESSAGE_STATE_TIME="state_time";


    public String blockId="0";
    public String fileId="0";
    public String nodeId="0";
    public String blockName="0";
    public String fileSerialNumber="0";
    public String blockPath="0";
    public String blockSize="0";
    public String blockSetTime="0";
    public String state="0";
    public String stateTime="0";



    private static BlocksMessage mCheckInClassEntity=null;
    public static final BlocksMessage getInstance(){
        if(null == mCheckInClassEntity){
            mCheckInClassEntity = new BlocksMessage();
        }
        return mCheckInClassEntity;
    }

}
