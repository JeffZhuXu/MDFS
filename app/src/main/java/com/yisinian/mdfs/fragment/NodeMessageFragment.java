package com.yisinian.mdfs.fragment;


import android.os.Bundle;

import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.yisinian.mdfs.R;
import com.yisinian.mdfs.adapter.NodeMessageadapter;
import com.yisinian.mdfs.application.Node;
import com.yisinian.mdfs.table.NodeMessage;
import com.yisinian.mdfs.table.NodeMessageDao;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

/**
 * A simple {@link Fragment} subclass.
 */
public class NodeMessageFragment extends Fragment {

    private ListView nodeMessageListView;
    private NodeMessageadapter nodeMessageadapter;

    public NodeMessageFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.node_message, container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        nodeMessageListView = (ListView) getActivity().findViewById(R.id.nodeMessageListView);
        try {
            nodeMessageadapter = new NodeMessageadapter(getActivity(), getData());
            nodeMessageListView.setAdapter(nodeMessageadapter);                     //将数据添加进去
        } catch (JSONException e) {
            e.printStackTrace();
        }
        super.onActivityCreated(savedInstanceState);
    }


    public List<JSONObject> getData() throws JSONException {

        NodeMessage nodeMessage = NodeMessageDao.getNodeMessage(getActivity());

        List<JSONObject> a = new ArrayList<JSONObject>();

        JSONObject nodeId = new JSONObject();
        nodeId.put("nodeId", nodeMessage.nodeId);

//        JSONObject systemId = new JSONObject();
//        nodeId.put("systemId", nodeMessage.systemId);

        JSONObject nodeName = new JSONObject();
        nodeName.put("nodeName", nodeMessage.nodeName);

        JSONObject nodeCompany = new JSONObject();
        nodeCompany.put("nodeCompany", nodeMessage.company);

        JSONObject nodePhoneType = new JSONObject();
        nodePhoneType.put("nodePhoneType", nodeMessage.phoneType);

        JSONObject nodeOs = new JSONObject();
        nodeOs.put("nodeOs", nodeMessage.os);

        JSONObject nodeOsVersion = new JSONObject();
        nodeOsVersion.put("nodeOsVersion", nodeMessage.osVersion);

        JSONObject localStorage = new JSONObject();
        localStorage.put("localStorage", nodeMessage.localStorage+" Byte");

        JSONObject restLocalStorage = new JSONObject();
        restLocalStorage.put("restLocalStorage", nodeMessage.restLocalStorage+" Byte");

        JSONObject storage = new JSONObject();
        storage.put("storage", nodeMessage.storage+" Byte");

        JSONObject restStorage = new JSONObject();
        restStorage.put("restStorage", nodeMessage.restStorage+" Byte");

        JSONObject ram = new JSONObject();
        ram.put("ram", nodeMessage.ram+" M");

        JSONObject cpuCoreNum = new JSONObject();
        cpuCoreNum.put("cpuCoreNum", nodeMessage.coreNumber);

        JSONObject cpuFrequency = new JSONObject();
        cpuFrequency.put("cpuFrequency", nodeMessage.cpuFrequency+" KHz");

        JSONObject netType = new JSONObject();
        netType.put("netType", nodeMessage.netType);

        JSONObject netSpeed = new JSONObject();
        netSpeed.put("netSpeed", nodeMessage.netSpeed);

        JSONObject imel = new JSONObject();
        imel.put("imel", nodeMessage.imel);

        JSONObject serialNum = new JSONObject();
        serialNum.put("serialNum", nodeMessage.serialNumber);

        JSONObject jpuchId = new JSONObject();
        jpuchId.put("jpuchId", nodeMessage.jpushId);

        JSONObject state = new JSONObject();
        state.put("state", nodeMessage.state);

        a.add(nodeId);
        a.add(nodeName);
        a.add(nodeCompany);
        a.add(nodePhoneType);
        a.add(nodeOs);
        a.add(nodeOsVersion);
        a.add(localStorage);
        a.add(restLocalStorage);
        a.add(storage);
        a.add(restStorage);
        a.add(ram);
        a.add(cpuCoreNum);
        a.add(cpuFrequency);
        a.add(netType);
        a.add(netSpeed);
        a.add(imel);
        a.add(serialNum);
        a.add(jpuchId);
        a.add(state);
        return a;
    }

    ;

//    public List<JSONObject> getData() throws JSONException {
//        List<JSONObject> a =new ArrayList<JSONObject>();
//        JSONObject b = new JSONObject();
//        b.put("b","b");
//        JSONObject c = new JSONObject();
//        c.put("c", "c");
//        a.add(b);
//        a.add(c);
//        return a;
//    }

}
