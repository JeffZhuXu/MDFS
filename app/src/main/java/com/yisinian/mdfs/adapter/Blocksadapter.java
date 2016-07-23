package com.yisinian.mdfs.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.yisinian.mdfs.R;
import com.yisinian.mdfs.system.MDFSBlock;
import com.yisinian.mdfs.table.BlocksMessage;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by mac on 16/1/8.
 */
public class Blocksadapter extends BaseAdapter{
    private Context context;
    private LayoutInflater inflater;
    private List<BlocksMessage> blockList ;

//    public

    public Blocksadapter(Context context, List<BlocksMessage> list){
        this.context=context;
        this.inflater=LayoutInflater.from(context);
        if (list==null){
            list = new ArrayList<BlocksMessage>();
        }
        this.blockList=list;
    }
    @Override
    public int getCount() {
        return blockList.size();
    }

    @Override
    public BlocksMessage  getItem(int position) {

        return blockList.get(position);
    }

    @Override
    public long getItemId(int position) {

        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ViewHolder holder = null;
        if(convertView==null) {
            convertView = inflater.inflate(R.layout.blocks, null);
            holder = new ViewHolder();
            holder.blockIdTextView = (TextView) convertView.findViewById(R.id.blockId);
            holder.blockNameTextView = (TextView) convertView.findViewById(R.id.blockName);
            holder.blockSizeTextView = (TextView) convertView.findViewById(R.id.blockSize);
            holder.blockFileNameTextView = (TextView) convertView.findViewById(R.id.blockFileName);
            convertView.setTag(holder);
        }else{
            holder=(ViewHolder)convertView.getTag();
        }


        BlocksMessage aBlock =getItem(position);

        holder.blockIdTextView.setText(aBlock.blockId);
        holder.blockNameTextView.setText(aBlock.blockName);
        holder.blockSizeTextView.setText(aBlock.blockSize);
        holder.blockFileNameTextView.setText(aBlock.fileId);
        return convertView;
    }

    class ViewHolder{
        TextView blockIdTextView;
        TextView blockNameTextView;
        TextView blockSizeTextView;
        TextView blockFileNameTextView;
        
        
    }
}
