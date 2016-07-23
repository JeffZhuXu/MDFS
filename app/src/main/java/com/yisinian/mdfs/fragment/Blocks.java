package com.yisinian.mdfs.fragment;


import android.os.Bundle;

import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.yisinian.mdfs.R;
import com.yisinian.mdfs.adapter.Blocksadapter;
import com.yisinian.mdfs.system.MDFSBlock;
import com.yisinian.mdfs.table.BlocksMessageDao;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class Blocks extends Fragment {

    private ListView blocksList;
    private Blocksadapter blocksadapter;

    public Blocks() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_blocks, container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        blocksList = (ListView) getActivity().findViewById(R.id.blocksList);
        blocksadapter = new Blocksadapter(getActivity(), BlocksMessageDao.getAllBlocksMessage(getActivity()));
        blocksList.setAdapter(blocksadapter);
        super.onActivityCreated(savedInstanceState);
    }
}
