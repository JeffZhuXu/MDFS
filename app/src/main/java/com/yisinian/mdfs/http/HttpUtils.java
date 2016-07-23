package com.yisinian.mdfs.http;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Map;


import org.json.JSONObject;

import android.app.Dialog;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Toast;

public class HttpUtils {
    public static final String TAG = "HttpURLConnection";

    public static final String NETWORK_CODE = "network_code";   //网络码
    public static final String RESULT = "result";   //网络码
    public static final int FIRST_TIME_TO_SERVER = 0;//表示第一次登陆

    /*
     * Function  :   发送Post请求到服务器
     * Param     :   params请求体内容，encode编码格式
     * Author    :   博客园-依旧淡然
     */
    public static String getEntity(String urlString, Map<String, String> params) {
        Log.e(TAG, "http请求开始");//打印响应码
        int i = 0;
        String encode = "utf-8";
        URL url = null;
        OutputStream outputStream = null;
        HttpURLConnection httpURLConnection = null;
        InputStream inputStream = null;
        while (true) {
            try {
                byte[] data = getRequestData(params, encode).toString().getBytes();//获得请求体
                url = new URL(urlString);
                Log.w(TAG, "url: " + urlString);
                httpURLConnection = (HttpURLConnection) url.openConnection();
                if (i == FIRST_TIME_TO_SERVER) {//第一次连接服务器

                    httpURLConnection.setReadTimeout(13000);    //读取时间最大是30s
                    httpURLConnection.setConnectTimeout(5000);      //设置连接超时时间，最大是5秒
                } else {//第二次连接服务器
                    //httpURLConnection.setReadTimeout(16000);    //读取时间最大是30s
                    //httpURLConnection.setConnectTimeout(10000);      //设置连接超时时间，最大是10秒
                }
                httpURLConnection.setDoInput(true);                  //打开输入流，以便从服务器获取数据
                httpURLConnection.setDoOutput(true);                 //打开输出流，以便向服务器提交数据
                httpURLConnection.setRequestProperty("contentType", "utf-8");//康鹏设置，设置主体的格式是utf-8
                httpURLConnection.setInstanceFollowRedirects(true);//设置这个连接是否遵循重定向。


                // 设置请求的头  设置长连接暂时不用
//    	            httpURLConnection.setRequestProperty("Connection", "keep-alive");

                httpURLConnection.setRequestMethod("POST");   //设置以Post方式提交数据
                httpURLConnection.setUseCaches(false);               //使用Post方式不能使用缓存
                //设置请求体的类型是文本类型
                httpURLConnection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
                //设置请求体的长度
                httpURLConnection.setRequestProperty("Content-Length", String.valueOf(data.length));
                //获得输出流，向服务器写入数据
                outputStream = httpURLConnection.getOutputStream();
                outputStream.write(data);
                if (outputStream != null) {
//    	        		try {
                    outputStream.flush();
                    outputStream.close();
//    					} catch (IOException e) {
//    						// TODO Auto-generated catch block
//    						e.printStackTrace();
//    					}
                }

                inputStream = httpURLConnection.getInputStream();//  // 要注意的是httpURLConnection.getOutputStream会隐含的进行connect。
                int response = httpURLConnection.getResponseCode();            //获得服务器的响应码
                Log.e(TAG, "HTTP响应码" + response);//打印响应码
                if (inputStream != null) {
                    if (response == HttpURLConnection.HTTP_OK) {
                        String returnString = null;
                        returnString = dealResponseResult(response, inputStream);
                        return returnString;                     //处理服务器的响应结果
                    } else {
                        String returnString = null;
                        returnString = dealResponseResult(response, inputStream);
                        return returnString; //不等于200的时候的响应码
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
                if (i >= 1) {
                    Log.e(TAG, "增加一次" + i);
                    return null;//捕捉到异常就返回的就是空。
                }
                i++;
                Log.e(TAG, "增加一次" + i);
            } finally {

                if (inputStream != null) {//多关闭几次也不会报错。没有问题。
                    try {
                        inputStream.close();
                    } catch (IOException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                }

                if (httpURLConnection != null) {
                    httpURLConnection.disconnect();
                }
            }

        }


    }

    /*
     * Function  :   封装请求体信息
     * Param     :   params请求体内容，encode编码格式
     * Author    :   博客园-依旧淡然
     */
    public static StringBuffer getRequestData(Map<String, String> params, String encode) throws Exception {
        if (params == null || params.size() == 0) {

        }
        StringBuffer stringBuffer = new StringBuffer();        //存储封装好的请求体信息
//        try {
        for (Map.Entry<String, String> entry : params.entrySet()) {
            stringBuffer.append(entry.getKey())
                    .append("=")
                    .append(URLEncoder.encode(entry.getValue(), encode))
                    .append("&");
        }
        stringBuffer.deleteCharAt(stringBuffer.length() - 1);    //删除最后的一个"&"

//        } catch (Exception e) {
//            e.printStackTrace();
//        }
        return stringBuffer;
    }

    /*
    2      * Function  :   处理服务器的响应结果（将输入流转化成字符串）
    3      * Param     :   inputStream服务器的响应输入流
    4      * Author    :   博客园-依旧淡然
    5      */
    public static String dealResponseResult(int response, InputStream inputStream) throws Exception {
        String resultData = "";      //存储处理结果,如果使用空的话，那么就有问题。
        InputStreamReader mInputStreamReader = null;
        BufferedReader mBufferedReader = null;
        if (response == 200) {//返回的是200码
            if (inputStream != null) {
//                     	 try {
                mInputStreamReader = new InputStreamReader(inputStream, "utf-8");
                if (mInputStreamReader != null) {
                    mBufferedReader = new BufferedReader(mInputStreamReader);
                    String line = "";
                    while ((line = mBufferedReader.readLine()) != null) {
                        resultData += line;
                    }

                }

                if (mBufferedReader != null) {
                    mBufferedReader.close();
                }
                if (mInputStreamReader != null) {
                    mInputStreamReader.close();
                }

//          				} catch (UnsupportedEncodingException e) {
//          					// TODO Auto-generated catch block
//          					e.printStackTrace();
//          				} catch (IOException e) {
//          					// TODO Auto-generated catch block
//          					e.printStackTrace();
//          				}

            } else {//流为空，直接返回
                return null;
            }
        }
        if (TextUtils.isEmpty(resultData)) {
            return null;//就是当解析完还是空，就直接返回空，不进行json打包
        }


        JSONObject jsonObject = new JSONObject();
//				try {
        jsonObject.put("network_code", response);
        jsonObject.put("result", resultData);
//				} catch (JSONException e) {
//					e.printStackTrace();
//				}
        String returnJsonString = null;
        returnJsonString = jsonObject.toString();
        Log.w(TAG, "http请求结果：" + returnJsonString);
        return returnJsonString;
    }


//            Log.e("resultData", ""+resultData);
//            return resultData;
//   	  }

    /**
     * 判断不成功时 的网络码
     *
     * @param context
     * @param dialog
     * @param code
     */
    public static void judgeHttpNotSuccessCode(Context context, int code) {
        switch (code) {

            case HttpURLConnection.HTTP_BAD_REQUEST://400
                Toast.makeText(context, "网络请求错误", Toast.LENGTH_SHORT).show();
                Log.w(TAG, "网络请求错误");
                break;
            case HttpURLConnection.HTTP_FORBIDDEN://403
                Toast.makeText(context, "网络访问被禁止", Toast.LENGTH_SHORT).show();
                Log.w(TAG, "网络访问被禁止");
                break;
            case HttpURLConnection.HTTP_NOT_FOUND://404
                Toast.makeText(context, "网络地址未找到", Toast.LENGTH_SHORT).show();
                Log.w(TAG, "网络地址未找到");
                break;
            case HttpURLConnection.HTTP_CLIENT_TIMEOUT://408
                Toast.makeText(context, "网络请求超时", Toast.LENGTH_SHORT).show();
                Log.w(TAG, "网络请求超时");
                break;
            case 333://
                Toast.makeText(context, "网络登陆失败", Toast.LENGTH_SHORT).show();
                Log.w(TAG, "网络登陆失败");
                break;
            default:
                Toast.makeText(context, "网络连接异常", Toast.LENGTH_SHORT).show();
                Log.w(TAG, "网络连接异常");
                break;
        }
    }

    /**
     * 判断不成功时 的网络码
     *
     * @param context
     * @param dialog
     * @param code
     */
    public static void judgeHttpNotSuccessCode(Context context, Dialog dialog, int code) {
        switch (code) {

            case HttpURLConnection.HTTP_BAD_REQUEST:
                Toast.makeText(context, "网络请求错误", Toast.LENGTH_SHORT).show();
                Log.w(TAG, "网络请求错误");
                dialog.dismiss();
                break;
            case HttpURLConnection.HTTP_FORBIDDEN:
                Toast.makeText(context, "网络访问被禁止", Toast.LENGTH_SHORT).show();
                Log.w(TAG, "网络请求错误");
                dialog.dismiss();
                break;
            case HttpURLConnection.HTTP_NOT_FOUND:
                Toast.makeText(context, "网络地址未找到", Toast.LENGTH_SHORT).show();
                Log.w(TAG, "网络请求错误");
                dialog.dismiss();
                break;
            case HttpURLConnection.HTTP_CLIENT_TIMEOUT:
                Toast.makeText(context, "网络请求超时", Toast.LENGTH_SHORT).show();
                Log.w(TAG, "网络请求错误");
                dialog.dismiss();
                break;
            case 333:
                Toast.makeText(context, "网络登陆失败", Toast.LENGTH_SHORT).show();
                Log.w(TAG, "网络请求错误");
                dialog.dismiss();
                break;
            default:
                Toast.makeText(context, "网络连接异常", Toast.LENGTH_SHORT).show();
                Log.w(TAG, "网络连接异常");
                dialog.dismiss();
                break;
        }
    }

    /**
     * String stringToReverse = URLEncoder.encode(args[1], "UTF-8");
     * 判断是否有网络连接
     *
     * @param context
     * @return
     */
    public static boolean isNetworkConnected(Context context) {

        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        if (activeNetworkInfo != null && activeNetworkInfo.isAvailable() && activeNetworkInfo.isConnected()) {
            return true;
        } else {
//    			Toast.makeText(context, "网络异常", Toast.LENGTH_SHORT).show();
            return false;
        }
    }
}
