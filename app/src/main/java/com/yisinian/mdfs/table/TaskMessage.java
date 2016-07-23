package com.yisinian.mdfs.table;

/**
 * Created by zhuxu on 16/1/12.
 */
public class TaskMessage {


    public static final String TASKMESSAGE_TABLE="task_message";
    public static final String NODE_TASK_ID="node_task_id";
    public static final String TASK_ID="task_id";
    public static final String TASK_TYPE="task_type";
    public static final String BLOCK_ID="block_id";
    public static final String CONTENT="content";
    public static final String FINISH_TIME="finish_time";



    public String nodeTaskId="0";
    public String taskId="0";
    public String taskType="0";
    public String blockId="0";
    public String content="0";
    public String finishTime="0";


    private static TaskMessage mCheckInClassEntity=null;
    public static final TaskMessage getInstance(){
        if(null == mCheckInClassEntity){
            mCheckInClassEntity = new TaskMessage();
        }
        return mCheckInClassEntity;
    }

}
