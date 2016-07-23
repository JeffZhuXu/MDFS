package com.yisinian.mdfs.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.yisinian.mdfs.R;
import com.yisinian.mdfs.system.MDFSFile;
import com.yisinian.mdfs.table.TaskMessage;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by mac on 16/1/8.
 */
public class Fileadapter extends BaseAdapter{
    private Context context;
    private LayoutInflater inflater;
    private List<TaskMessage> taskList ;

//    public

    public Fileadapter(Context context,List<TaskMessage> list){
        this.context=context;
        this.inflater=LayoutInflater.from(context);
        if (list==null){
            list = new ArrayList<TaskMessage>();
        }
        this.taskList=list;
    }
    @Override
    public int getCount() {
        return taskList.size();
    }

    @Override
    public TaskMessage getItem(int position) {

        return taskList.get(position);
    }

    @Override
    public long getItemId(int position) {

        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ViewHolder holder = null;
        if(convertView==null) {
            convertView = inflater.inflate(R.layout.item, null);
            holder = new ViewHolder();
            holder.taskIdTextView = (TextView) convertView.findViewById(R.id.taskId);       //获取文件id的textview
            holder.taskTypeTextView= (TextView) convertView.findViewById(R.id.taskType);    //获取文件名称的textview
            holder.taskContentTextView = (TextView) convertView.findViewById(R.id.taskContent);       //获取文件id的textview
            holder.taskTimeTextView= (TextView) convertView.findViewById(R.id.taskTime);    //获取文件名称的textview

            convertView.setTag(holder);
        }else{
            holder=(ViewHolder)convertView.getTag();
        }
        TaskMessage aTask =getItem(position);
        holder.taskIdTextView.setText(aTask.nodeTaskId.toString());             //任务id
        holder.taskTypeTextView.setText(aTask.taskType.toString());             //任务类型
        holder.taskContentTextView.setText(aTask.content.toString());           //任务参数
        holder.taskTimeTextView.setText(aTask.finishTime.toString());           //任务时间
        return convertView;
    }

    class ViewHolder{
        TextView taskIdTextView;
        TextView taskTypeTextView;
        TextView taskContentTextView;
        TextView taskTimeTextView;

    }
}
