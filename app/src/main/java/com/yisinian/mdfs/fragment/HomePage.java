package com.yisinian.mdfs.fragment;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;

import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import android.widget.Toast;

import com.yisinian.mdfs.R;

import com.yisinian.mdfs.service.NetService;
import com.yisinian.mdfs.service.PingService;



/**
 * A simple {@link Fragment} subclass.
 */
public class HomePage extends Fragment {
    private Button specialButton;


    public HomePage() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.home_page, container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {

        //主页面按钮
        specialButton = (Button) getActivity().findViewById(R.id.SpecialButton);
        specialButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                String blockId = "转移本地缓存";
//            UploadUtil.uploadLTCodeFile(getContext(),blockId);
                Toast.makeText(getContext(),blockId,Toast.LENGTH_SHORT).show();;
            }
        });

        //在加载主页面的fragment的时候，启动测网速以及更新数据的service,同时启动ping测网络延时service
        getActivity().startService(new Intent(getActivity(), NetService.class));
        getActivity().startService(new Intent(getActivity(), PingService.class));


        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public void onPause() {

        super.onPause();
    }

    @Override
    public void onPrepareOptionsMenu(Menu menu) {


        super.onPrepareOptionsMenu(menu);
    }
}
