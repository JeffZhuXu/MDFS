package com.yisinian.mdfs.fragment;


import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;

import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.yisinian.mdfs.R;
import com.yisinian.mdfs.adapter.Fileadapter;
import com.yisinian.mdfs.database.MdfsDb;
import com.yisinian.mdfs.system.MDFSFile;
import com.yisinian.mdfs.table.TaskMessageDao;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class Files extends Fragment {

    private ListView filesList;
    private Fileadapter fileadapter;


    public Files() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_files, container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        filesList=(ListView)getActivity().findViewById(R.id.fileslistView);
        fileadapter = new Fileadapter(getActivity(), TaskMessageDao.getAllTasksMessage(getActivity()));
        filesList.setAdapter(fileadapter);
        super.onActivityCreated(savedInstanceState);
    }

}
