package com.yisinian.mdfs.system;

/**
 * Created by mac on 16/1/9.
 */
public class MDFSTask {


    private String fileId;
    private String fileName;

    public void  File(){}
    public void File(String fileId,String fileName){
        this.fileId=fileId;
        this.fileName=fileName;
    }


    public String getFileId() {
        return fileId;
    }

    public void setFileId(String fileId) {
        this.fileId = fileId;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }








}
