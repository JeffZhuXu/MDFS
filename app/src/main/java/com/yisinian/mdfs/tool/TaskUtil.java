package com.yisinian.mdfs.tool;

import android.content.Context;
import android.util.Log;

import com.yisinian.mdfs.constant.StringConstant;
import com.yisinian.mdfs.http.MDFSHttp;
import com.yisinian.mdfs.table.BlocksMessage;
import com.yisinian.mdfs.table.BlocksMessageDao;
import com.yisinian.mdfs.table.TaskMessage;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/*
 *@author zhuxu
 *@time ����10:34:502016
 *@info �ֲ�ʽ���񹤾���
 *
 */

public class TaskUtil {

	//�ļ���Ϣ������
	public static void searchFileA(Context context,TaskMessage taskMessage){
		long startTime = System.currentTimeMillis();
		long finishTime = 0l;
		String content = taskMessage.content;
		String searchResult = "";
		String blockId = taskMessage.blockId;
		BlocksMessage blocksMessage=BlocksMessageDao.getOneBlocksMessageByBlockId(context, blockId);
		//获取文件块所在手机本地路径
		String blockPath = context.getFilesDir().getAbsolutePath()+"/blocks/"+blocksMessage.blockName;
		try {
            String encoding="UTF-8";
			//如果该文件是压缩后的文件
			if (blocksMessage.blockName.contains(".gz")){
				//将文件解压出来，原压缩文件不删除
				blockPath = GZipUtils.decompress(blockPath,false);
			}
            File file=new File(blockPath);
            if(file.isFile() && file.exists()){ //�ж��ļ��Ƿ����
				Log.i(StringConstant.MDFS_TAG_TASK,"任务计算文件存在："+blockPath);
                InputStreamReader read = new InputStreamReader(
                new FileInputStream(file),encoding);//���ǵ������ʽ
                BufferedReader bufferedReader = new BufferedReader(read);
                String lineTxt = null;
                while((lineTxt = bufferedReader.readLine()) != null){
                	Matcher matcher = Pattern.compile(content).matcher(lineTxt);
                	while(matcher.find()){
						Log.i(StringConstant.MDFS_TAG_TASK,lineTxt);
                		searchResult=searchResult+"\n"+lineTxt;
                	}
                }
                read.close();
    }else{
//        System.out.println("�Ҳ���ָ�����ļ�");
    }
    } catch (Exception e) {
//        System.out.println("��ȡ�ļ����ݳ���");
        e.printStackTrace();
    }
		//Log.i(StringConstant.MDFS_TAG_TASK,"任务计算结果："+searchResult);
		finishTime = System.currentTimeMillis()-startTime;
		Map<String,String> taskMsg = new HashMap<String,String>();
		taskMsg.put("nodeTaskId",taskMessage.nodeTaskId);
		taskMsg.put("taskId",taskMessage.taskId);
		taskMsg.put("taskResult",searchResult);
		taskMsg.put("finishTime",finishTime+"");
		MDFSHttp.uploadTaskResult(context,MDFSHttp.resultUpdateUrl,taskMsg);
	}

	//���ַ�д��ָ���ı��ļ�
	public static void writeSearchResultsIntoFile(String resultFilePath,String result){
		String localFilePath=resultFilePath;
		String searchResult = result;
		File resultFile = new File(localFilePath);
		try {
		if(resultFile.isFile()&&resultFile.exists()){

			FileOutputStream writerStream = new FileOutputStream(resultFile,true);
			BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(writerStream, "UTF-8"));
			writer.write(searchResult);
			writer.close();

		}else {
			resultFile.createNewFile();
			FileOutputStream writerStream = new FileOutputStream(resultFile,true);
			BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(writerStream, "UTF-8"));
			writer.write(searchResult);
			writer.close();
		}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
//		String filePath ="F:\\1.log";
//		String message = "�ֻ�ɨ��";
//		String searchResult=searchFileA(filePath,message);
//		writeSearchResultsIntoFile("F:\\result.txt", searchResult);
	}
}
