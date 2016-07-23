package com.yisinian.mdfs.activity;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.yisinian.mdfs.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class A extends Fragment {


    public A() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        Log.i("MDFS","创建fragment  A 界面");
        return inflater.inflate(R.layout.fragment_a, container, false);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.i("MDFS","创建fragment  A");
    }

    @Override
    public void onPause() {
        super.onPause();
        Log.i("MDFS","暂停fragment A");
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        Log.i("MDFS", "删除fragment A 界面");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.i("MDFS", "删除fragment  A");
    }
}
