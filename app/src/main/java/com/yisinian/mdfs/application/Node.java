package com.yisinian.mdfs.application;

import android.app.Application;

import java.util.Locale;

/**
 * Created by zhuxu on 16/1/7.
 * 全局对象，节点的基本情况
 *
 */
public class Node extends Application {


    private boolean register = false;                   //节点是否注册标签，默认为false
    private String nodeId = "default-nodeId";           // 节点id
    private String nodeName = "defaul-name";            //节点名称
    private String nodeCompany = "defaul-company";      //节点手机制造商
    private String nodePhoneType = "defaul-company";    //节点手机型号
    private String nodeOs= "android";                   //节点操作系统
    private String nodeOsVersion= "default-version";    //节点操作系统系统版本
    private long localStorage = 0;                      //手机本地的存储容量
    private long restLocalStorage = 0;                  //手机本地剩余的节点容量
    private long storage = 0;                           //手机提供给MDFS系统的容量
    private long restStorage = 0;                       //手机提供给MDFS系统剩余的容量
    private long ram = 0;                               //手机运行内存大小
    private long cpuCoreNum =0;                         //手机CPU核心数，‘1’单核，‘2’双核 ‘4’四核 ‘8’八核
    private long cpuFrequency =0;                       //手机CPU主频
    private String netType = "0";                       //手机联网模式
    private long netSpeed = 0;                          //当前手机节点的网速
    private String imel = "0";                          //手机的IMEL唯一识别码
    private String serialNum = "0";                     //手机的序列号
    private String jpuchId = "0";                       //JpushID
    private String state = "0";                         //该节点是否在线，‘0’表示离线，‘1’表示在线，‘2’表示节点故障，无法正常运行

    public boolean isRegister() {
        return register;
    }

    public void setRegister(boolean register) {
        this.register = register;
    }

    public String getNodeId() {
        return nodeId;
    }

    public void setNodeId(String nodeId) {
        this.nodeId = nodeId;
    }

    public String getNodeName() {
        return nodeName;
    }

    public void setNodeName(String nodeName) {
        this.nodeName = nodeName;
    }

    public String getNodeCompany() {
        return nodeCompany;
    }

    public void setNodeCompany(String nodeCompany) {
        this.nodeCompany = nodeCompany;
    }

    public String getNodePhoneType() {
        return nodePhoneType;
    }

    public void setNodePhoneType(String nodePhoneType) {
        this.nodePhoneType = nodePhoneType;
    }

    public String getNodeOs() {
        return nodeOs;
    }

    public void setNodeOs(String nodeOs) {
        this.nodeOs = nodeOs;
    }

    public String getNodeOsVersion() {
        return nodeOsVersion;
    }

    public void setNodeOsVersion(String nodeOsVersion) {
        this.nodeOsVersion = nodeOsVersion;
    }

    public long getLocalStorage() {
        return localStorage;
    }

    public void setLocalStorage(long localStorage) {
        this.localStorage = localStorage;
    }

    public long getRestLocalStorage() {
        return restLocalStorage;
    }

    public void setRestLocalStorage(long restLocalStorage) {
        this.restLocalStorage = restLocalStorage;
    }

    public long getStorage() {
        return storage;
    }

    public void setStorage(long storage) {
        this.storage = storage;
    }

    public long getRestStorage() {
        return restStorage;
    }

    public void setRestStorage(long restStorage) {
        this.restStorage = restStorage;
    }

    public long getRam() {
        return ram;
    }

    public void setRam(long ram) {
        this.ram = ram;
    }

    public long getCpuCoreNum() {
        return cpuCoreNum;
    }

    public void setCpuCoreNum(long cpuCoreNum) {
        this.cpuCoreNum = cpuCoreNum;
    }

    public long getCpuFrequency() {
        return cpuFrequency;
    }

    public void setCpuFrequency(long cpuFrequency) {
        this.cpuFrequency = cpuFrequency;
    }

    public String getNetType() {
        return netType;
    }

    public void setNetType(String netType) {
        this.netType = netType;
    }

    public long getNetSpeed() {
        return netSpeed;
    }

    public void setNetSpeed(long netSpeed) {
        this.netSpeed = netSpeed;
    }

    public String getImel() {
        return imel;
    }

    public void setImel(String imel) {
        this.imel = imel;
    }

    public String getSerialNum() {
        return serialNum;
    }

    public void setSerialNum(String serialNum) {
        this.serialNum = serialNum;
    }

    public String getJpuchId() {
        return jpuchId;
    }

    public void setJpuchId(String jpuchId) {
        this.jpuchId = jpuchId;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }


}
