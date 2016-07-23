package com.yisinian.mdfs.http;


import android.content.Context;
import android.os.Handler;
import android.util.Log;
import android.widget.Toast;
import com.yisinian.mdfs.constant.StringConstant;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

public class HttpDownloadUtil {

    //分块文件在服务器上的存储地址
    public static String blocksUrl = "http://120.24.167.68:8082/MDFSFileBlocks/";
    //分块文件在服务器上的存储地址
    public static String ltCodesUrl = "http://120.24.167.68:8082/codes/";

    //分块文件在手机本地上存储的地址
    public static String blocksLocalUrl = "/data/data/com.yisinian.mdfs/files/blocks/";

    private static Handler handler;

    /**
     * 根据URL下载文件，前提是这个文件当中的内容是文本，函数返回值是文件当中的文本内容
     *
     * @param urlstr
     * @return
     */
    public static String downFile(String urlstr) {
        StringBuffer sb = new StringBuffer();
        BufferedReader buffer = null;
        URL url = null;
        String line = null;
        try {
            //创建一个URL对象
            url = new URL(urlstr);
            //根据URL对象创建一个Http连接
            HttpURLConnection urlConn = (HttpURLConnection) url.openConnection();
            //使用IO读取下载的文件数据
            buffer = new BufferedReader(new InputStreamReader(urlConn.getInputStream()));
            while ((line = buffer.readLine()) != null) {
                sb.append(line);
            }
        } catch (MalformedURLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } finally {
            try {
                if (buffer != null) {
                    buffer.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return sb.toString();
    }

    /**
     * 根据URL得到输入流
     *
     * @param urlstr
     * @return
     */
    public static InputStream getInputStreamFormUrl(String urlstr) {
        InputStream inputStream = null;
        try {
            URL url = new URL(urlstr);
            HttpURLConnection urlConn = (HttpURLConnection) url.openConnection();
            inputStream = urlConn.getInputStream();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return inputStream;
    }

    /*
    *
    *@author zhuxu create at 16/1/19下午4:10
    *@param fileName,context
    *@return void
    */
    public static void downloadBlocksFromWebByBlockName(final String fileName, final String blockId,final Context context) {

        new Thread(new Runnable() {
            @Override
            public void run() {

                //首先判定这个文件存不存在，不存在的话再访问服务器
                File file = new File(blocksLocalUrl + fileName);
                //存在的话，不执行操作
                if (file.exists()) {
                    Log.i(StringConstant.MDFS_TAG_FILE, fileName + "文件已存在");
                } else {
                    //Toast.makeText(context,"下载分块文件",Toast.LENGTH_SHORT);
                    //不存在的时候重新从服务器拿
                    long blockDownloadTime = System.currentTimeMillis();//下载文件所用的时间,单位毫秒（ms）
                    URL url = null;
                    StringBuffer stringbuffer = new StringBuffer();
                    String line;
                    BufferedReader bufferReader = null;
                    try {
                        //用于写文件
                        FileOutputStream fos = new FileOutputStream(file);
                        OutputStreamWriter osw = new OutputStreamWriter(fos, "UTF-8");
                        OutputStream output=null;
                        //创建一个URL对象

                        //如果是LT码块，从LT码位置下载
                        if (fileName.indexOf("lt")>0){
                            url = new URL(ltCodesUrl + fileName);
                        }else{
                            //如果是普通文件块，从普通文件块位置下载
                            url = new URL(blocksUrl + fileName);
                        }


                        //得到一个HttpURLConnection对象
                        HttpURLConnection httpUrlConnection = (HttpURLConnection) url.openConnection();


                        //如果是压缩文件
                        if(fileName.contains(".gz")){
                            Log.i(StringConstant.MDFS_TAG_HTTP,"压缩文件下载");
                            output=new FileOutputStream(file);
                            InputStream input=httpUrlConnection.getInputStream();
                            //读取大文件
                            int count = 0;
                            byte[] buffer=new byte[1024];
                            while((count=input.read(buffer))!=-1){
                                output.write(buffer,0,count);
                            }
                            output.flush();
                            output.close();
                        }else {     //如果不是压缩文件，正常的文本文件
                            Log.i(StringConstant.MDFS_TAG_HTTP,"非压缩文件下载");
                            int len =0;
                            char[] buff = new char[1024];
                            // 得到IO流，使用IO流读取数据
                            bufferReader = new BufferedReader(new InputStreamReader(httpUrlConnection.getInputStream(), "UTF-8"));
                            while ((len = bufferReader.read(buff)) > 0) {
                                osw.write(buff, 0, len);     //写
                            }
                            bufferReader.close();
                            osw.flush();
                            osw.close();
                            fos.close();
                        }

                        Log.i(StringConstant.MDFS_TAG_FILE, "···········下载文件·············");
                        Log.i(StringConstant.MDFS_TAG_FILE, fileName + "文件已保存到本地");
                        //界面显示接收文件
                        Toast.makeText(context,"接收文件",Toast.LENGTH_SHORT).show();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    blockDownloadTime = System.currentTimeMillis()-blockDownloadTime;
                    Log.i(StringConstant.MDFS_TAG_HTTP,"文件下载时间"+blockDownloadTime+"ms");
                    Map<String,String> dowmMsg = new HashMap<String, String>();
                    dowmMsg.put("blockId",blockId);
                    dowmMsg.put("blockDownloadTime",blockDownloadTime+"");
                    MDFSHttp.uploadFileResult(context,dowmMsg);
                }
            }
        }).start();

    }
}