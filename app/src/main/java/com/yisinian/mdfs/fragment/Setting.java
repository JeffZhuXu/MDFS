package com.yisinian.mdfs.fragment;


import android.os.Bundle;

import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.yisinian.mdfs.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class Setting extends Fragment {

    private Button changeStorage;
    private EditText changeStorageEditText;

    public Setting() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        Log.i("MDFS","oncreateView");
        View root = inflater.inflate(R.layout.fragment_setting, container, false);
//        changeStorage= (Button) root.findViewById(R.id.changeStorageButton);
//        changeStorageEditText= (EditText) root.findViewById(R.id.changeStorageEditText);
//        changeStorage.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Toast.makeText(getActivity(),"修改容量为："+changeStorageEditText.getText().toString(),Toast.LENGTH_LONG).show();
//            }
//        });
        return root;

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {

        Log.i("MDFS","onCreate");
        super.onCreate(savedInstanceState);
    }
}
