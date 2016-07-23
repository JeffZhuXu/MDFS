package com.yisinian.mdfs.system;

import android.content.Context;
import android.os.Environment;
import android.util.Log;
import android.widget.Toast;

import com.yisinian.mdfs.activity.Slider;
import com.yisinian.mdfs.constant.StringConstant;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;

/**
 * Created by mac on 16/1/11.
 */
public class FileOption {

        //分块文件在手机本地上存储的地址
        public static String blocksLocalUrl="/data/data/com.yisinian.mdfs/files/blocks/";

        /*
        *
        *@author zhuxu create at 下午3:56
        *@param filename,fileContent
        *@return int 返回1表示文件保存成功，返回0表示文件已存在
        */
        public static int saveBlock(String blockName,String content){
//                String root=context.getFilesDir().getAbsolutePath();
                int result = 0;
                try {
                        FileOutputStream fos = null; // openFileOutput(）方法就是直接写入文件APP内部路径
                        File file = new File(blocksLocalUrl+blockName);
                        if (file.exists()){
                                result= 0;
                                Log.i(StringConstant.MDFS_TAG_FILE, blockName + "文件已存在");
                        }else {
                                fos = new FileOutputStream(file);
                                OutputStreamWriter osw = new OutputStreamWriter(fos, "UTF-8");
                                osw.write(content);
                                osw.flush();
                                osw.close();
                                fos.close();
                                Log.i(StringConstant.MDFS_TAG_FILE, blockName + "文件已保存到本地");
                                result=1;
                        }
                } catch (FileNotFoundException e) {
                        e.printStackTrace();
                } catch (UnsupportedEncodingException e) {
                        e.printStackTrace();
                } catch (IOException e) {
                        e.printStackTrace();
                }
                return  result;
        }

}
