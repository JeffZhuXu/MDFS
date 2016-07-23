package com.yisinian.mdfs.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.yisinian.mdfs.R;
import com.yisinian.mdfs.system.MDFSFile;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;

/**
 * Created by mac on 16/1/8.
 */
public class NodeMessageadapter extends BaseAdapter{
    private Context context;
    private LayoutInflater inflater;
    private List<JSONObject> nodeMessageList ;

//    public

    public NodeMessageadapter(Context context, List<JSONObject> list){
        this.context=context;
        this.inflater=LayoutInflater.from(context);
        if (list==null){
            list = new ArrayList<JSONObject>();
        }
        this.nodeMessageList=list;
    }
    @Override
    public int getCount() {
        return nodeMessageList.size();
    }

    @Override
    public JSONObject getItem(int position) {

        return nodeMessageList.get(position);
    }

    @Override
    public long getItemId(int position) {

        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ViewHolder holder = null;
        if(convertView==null) {
            convertView = inflater.inflate(R.layout.node_property, null);
            holder = new ViewHolder();
            holder.nodePropertyName = (TextView) convertView.findViewById(R.id.nodePropertyName);       //获取节点参数名的textview
            holder.nodePropertyValue= (TextView) convertView.findViewById(R.id.nodePropertyValue);    //获取节点参数值的textview
            convertView.setTag(holder);
        }else{
            holder=(ViewHolder)convertView.getTag();
        }

        JSONObject aProperty =getItem(position);
        Iterator it = aProperty.keys();
        while (it.hasNext()){
            String key = it.next().toString();
            holder.nodePropertyName.setText(key);                                       //参数名
            try {
                holder.nodePropertyValue.setText(aProperty.get(key).toString());        //参数值
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return convertView;
    }

    class ViewHolder{
        TextView nodePropertyName;
        TextView nodePropertyValue;
    }
}
