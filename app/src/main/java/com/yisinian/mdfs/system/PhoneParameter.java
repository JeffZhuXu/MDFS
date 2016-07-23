package com.yisinian.mdfs.system;

/**手机的各种乱七八糟信息基本信息
 *
 * Created by zhuxu on 16/1/13.
 */
public class PhoneParameter {


    private String nodeId = "0";           // 节点id
    private String nodeName = "0";            //节点名称
    private String nodeCompany = "0";      //节点手机制造商
    private String nodePhoneType = "0";    //节点手机型号
    private String nodeOs= "0";                   //节点操作系统
    private String nodeOsVersion= "0";    //节点操作系统系统版本
    private String localStorage = "0";                      //手机本地的存储容量
    private String restLocalStorage = "0";                  //手机本地剩余的节点容量
    private String storage = "0";                           //手机提供给MDFS系统的容量
    private String restStorage = "0";                       //手机提供给MDFS系统剩余的容量
    private String ram = "0";                               //手机运行内存大小
    private String cpuCoreNum ="0";                         //手机CPU核心数，‘1’单核，‘2’双核 ‘4’四核 ‘8’八核
    private String cpuFrequency ="0";                       //手机CPU主频
    private String netType = "0";                       //手机联网模式
    private String netSpeed = "0";                          //当前手机节点的网速
    private String imel = "0";                          //手机的IMEL唯一识别码
    private String serialNum = "0";                     //手机的序列号
    private String jpuchId = "0";                       //JpushID
    private String state = "0";                         //该节点是否在线，‘0’表示离线，‘1’表示在线，‘2’表示节点故障，无法正常运行


}
