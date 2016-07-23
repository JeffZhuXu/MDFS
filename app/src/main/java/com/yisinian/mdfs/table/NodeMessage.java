package com.yisinian.mdfs.table;

import android.app.ActivityManager;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.TrafficStats;
import android.os.Environment;
import android.os.StatFs;
import android.telephony.TelephonyManager;
import android.util.Log;

import com.yisinian.mdfs.constant.StringConstant;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileFilter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Pattern;

/**
 * Created by mac on 16/1/12.
 */
public class NodeMessage {
    public static final String NODEMESSAGE_TABLE="node_message";
    public static final String NODEMESSAGE_NODE_ID="node_id";
    public static final String NODEMESSAGE_SYSTEM_ID="system_id";
    public static final String NODEMESSAGE_NODE_NAME="node_name";
    public static final String NODEMESSAGE_COMPANY="company";
    public static final String NODEMESSAGE_PHONE_TYPE="phone_type";
    public static final String NODEMESSAGE_OS="os";
    public static final String NODEMESSAGE_OS_VERSION="os_version";
    public static final String NODEMESSAGE_LOCAL_STORAGE="local_storage";
    public static final String NODEMESSAGE_REST_LOCAL_STORAGE="rest_local_storage";
    public static final String NODEMESSAGE_STORAGE="storage";
    public static final String NODEMESSAGE_REST_STORAGE="rest_storage";
    public static final String NODEMESSAGE_RAM="ram";
    public static final String NODEMESSAGE_CPU_FREQUENCY="cpu_frequency";
    public static final String NODEMESSAGE_CORE_NUMBER="core_number";
    public static final String NODEMESSAGE_NET_TYPE="net_type";
    public static final String NODEMESSAGE_NET_SPEED="net_speed";
    public static final String NODEMESSAGE_PHONE_MODEL="phone_model";
    public static final String NODEMESSAGE_IMEL="imel";
    public static final String NODEMESSAGE_SERIAL_NUMBER="serial_number";
    public static final String NODEMESSAGE_JPUSH_ID="jpush_id";
    public static final String NODEMESSAGE_STATE="state";
    public static final String NODEMESSAGE_START_TIME="start_time";
    public static final String NODEMESSAGE_END_TIME="end_time";
    public static final String NODEMESSAGE_RELAY_TIME="relay_time";


    private static long total_data = TrafficStats.getTotalRxBytes();


    public  String nodeId="0";
    public  String systemId="1";
    public  String nodeName="0";
    public  String company="0";
    public  String phoneType="0";
    public  String os="android";
    public  String osVersion="0";
    public  String localStorage="0";
    public  String restLocalStorage="0";
    public  String storage="104885760";
    public  String restStorage="104885760";
    public  String ram="0";
    public  String cpuFrequency ="0";
    public  String coreNumber="0";
    public  String netType="0";
    public  String netSpeed="0";
    public  String phoneModel="0";
    public  String imel="imel";
    public  String serialNumber="0";
    public  String jpushId="0";
    public  String state="0";
    public  String startTime="0";
    public  String endTime="0";
    public  String relayTime="0";
    //不带参数的构造函数,所有的参数是默认值
    public  NodeMessage(){

    }
    //带参数的构造函数,所有的参数是从手机获取的值
    public  NodeMessage(Context context){
        TelephonyManager tm = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
        this.nodeId = tm.getDeviceId();
        this.systemId = "1";
        this.nodeName = tm.getDeviceId()+"";
        this.imel = tm.getDeviceId();
        this.company = android.os.Build.MANUFACTURER;
        this.phoneType = android.os.Build.MODEL;
        this.os = "android";
        this.osVersion = android.os.Build.VERSION.RELEASE;
        this.localStorage = getPhoneTotalStorage();
        this.restLocalStorage = getPhoneRestStorage();
        this.storage = "104885760";
        this.restStorage = "104885760";
        this.ram = getAvailMemory(context)+"";
        this.cpuFrequency= getCurCpuFreq();
        this.coreNumber = getNumCores()+"";
        this.netType = getCurrentNetSpeed(context);
        this.netSpeed = "0";
        this.imel = tm.getDeviceId();
        this.serialNumber = "0";
        this.jpushId = "0";
        this.state="1";
        this.startTime = getPhoneTime();
        this.endTime="0";
        this.relayTime="0";
    }

    //获取手机id
    public static String getPhoneId(Context context) {
        TelephonyManager tm = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
        return tm.getDeviceId();
    }

    public static String getPhoneTime() {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date curDate = new Date(System.currentTimeMillis());//获取当前时间
        String str = formatter.format(curDate);
        return str;
    }

    //获取系统总的存储量，Byte单位
    public static String getPhoneTotalStorage() {
        File root = Environment.getRootDirectory();
        StatFs sf = new StatFs(root.getPath());
        int totalBlockNum = sf.getBlockCount(); //总块数
        int blockSize = sf.getBlockSize(); // 块大小
        return  (totalBlockNum*blockSize) +"";

    }
    //获取系统剩余的存储量，Byte单位
    public static String getPhoneRestStorage() {
        File root = Environment.getRootDirectory();
        StatFs sf = new StatFs(root.getPath());
        int freeBlockNum = sf.getAvailableBlocks();  // 剩余块数
        int blockSize = sf.getBlockSize(); // 块大小
        return (freeBlockNum * blockSize) +"";
    }

    //获取当前可用的RAM大小，单位是M
    public static String getAvailMemory(Context context) {
        ActivityManager am = (ActivityManager)context.getSystemService(Context.ACTIVITY_SERVICE);
        ActivityManager.MemoryInfo mi = new ActivityManager.MemoryInfo();
        am.getMemoryInfo(mi);
        return mi.availMem/1024/1024+"";
    }

    // 实时获取CPU当前频率（单位KHZ）
    public static String getCurCpuFreq() {
        String result = "N/A";
        try {
            FileReader fr = new FileReader(
                    "/sys/devices/system/cpu/cpu0/cpufreq/scaling_cur_freq");
            BufferedReader br = new BufferedReader(fr);
            String text = br.readLine();
            result = text.trim();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }

    //获取手机CPU核心数
    public static int getNumCores() {
        //Private Class to display only CPU devices in the directory listing
        class CpuFilter implements FileFilter {
            @Override
            public boolean accept(File pathname) {
                //Check if filename is "cpu", followed by a single digit number
                if(Pattern.matches("cpu[0-9]", pathname.getName())) {
                    return true;
                }
                return false;
            }
        }

        try {
            //Get directory containing CPU info
            File dir = new File("/sys/devices/system/cpu/");
            //Filter to only list the devices we care about
            File[] files = dir.listFiles(new CpuFilter());
            Log.d(StringConstant.MDFS_TAG_NODE_MESSAGE, "CPU Count: "+files.length);
            //Return the number of cores (virtual CPU devices)
            return files.length;
        } catch(Exception e) {
            //Print exception
            Log.d(StringConstant.MDFS_TAG_NODE_MESSAGE, "CPU Count: Failed.");
            e.printStackTrace();
            //Default to return 1 core
            return 1;
        }
    }

    /**
     * 得到当前的手机网络类型
     *
     * @param context
     * @return
     */
    public static String getCurrentNetType(Context context) {
        String type = "";
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo info = cm.getActiveNetworkInfo();
        if (info == null) {
            type = "null";
        } else if (info.getType() == ConnectivityManager.TYPE_WIFI) {
            type = "wifi";
        } else if (info.getType() == ConnectivityManager.TYPE_MOBILE) {
            int subType = info.getSubtype();
            if (subType == TelephonyManager.NETWORK_TYPE_CDMA || subType == TelephonyManager.NETWORK_TYPE_GPRS
                    || subType == TelephonyManager.NETWORK_TYPE_EDGE) {
                type = "2g";
            } else if (subType == TelephonyManager.NETWORK_TYPE_UMTS || subType == TelephonyManager.NETWORK_TYPE_HSDPA
                    || subType == TelephonyManager.NETWORK_TYPE_EVDO_A || subType == TelephonyManager.NETWORK_TYPE_EVDO_0
                    || subType == TelephonyManager.NETWORK_TYPE_EVDO_B) {
                type = "3g";
            } else if (subType == TelephonyManager.NETWORK_TYPE_LTE) {// LTE是3g到4g的过渡，是3.9G的全球标准
                type = "4g";
            }
        }
        return type;
    }

    /**
     * 得到当前的手机网络网速
     *
     * @param context
     * @return
     */
    public static String getCurrentNetSpeed(Context context) {
        String type = "";
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo info = cm.getActiveNetworkInfo();
        if (info == null) {
            type = "null";
        } else if (info.getType() == ConnectivityManager.TYPE_WIFI) {
            type = "wifi";
        } else if (info.getType() == ConnectivityManager.TYPE_MOBILE) {
            int subType = info.getSubtype();
            if (subType == TelephonyManager.NETWORK_TYPE_CDMA || subType == TelephonyManager.NETWORK_TYPE_GPRS
                    || subType == TelephonyManager.NETWORK_TYPE_EDGE) {
                type = "2g";
            } else if (subType == TelephonyManager.NETWORK_TYPE_UMTS || subType == TelephonyManager.NETWORK_TYPE_HSDPA
                    || subType == TelephonyManager.NETWORK_TYPE_EVDO_A || subType == TelephonyManager.NETWORK_TYPE_EVDO_0
                    || subType == TelephonyManager.NETWORK_TYPE_EVDO_B) {
                type = "3g";
            } else if (subType == TelephonyManager.NETWORK_TYPE_LTE) {// LTE是3g到4g的过渡，是3.9G的全球标准
                type = "4g";
            }
        }
        return type;
    }


    public static int getNetSpeed() {
        long traffic_data = TrafficStats.getTotalRxBytes() - total_data;
        total_data = TrafficStats.getTotalRxBytes();
        return (int)traffic_data /5 ;
    }

    private static NodeMessage mCheckInClassEntity=null;
    public static final NodeMessage getInstance(){
        if(null == mCheckInClassEntity){
            mCheckInClassEntity = new NodeMessage();
        }
        return mCheckInClassEntity;
    }
}
