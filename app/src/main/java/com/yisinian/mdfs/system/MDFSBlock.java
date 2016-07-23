package com.yisinian.mdfs.system;

import java.util.Comparator;

/**
 * Created by mac on 16/1/9.
 */
public class MDFSBlock implements Comparable {

    private String blockId="0";
    private String blockName="0";
    private String blockSize="0";
    private String blockSerialNumber="0";
    private String fileName="0";
    private String fileId="0";

    public void Block(){

    }
    public void Block(
            String blockId,
            String blockName,
            String blockSize,
            String blockSerialNumber,
            String fileName,
            String fileId
            ){
        this.blockId=blockId;
        this.blockName=blockName;
        this.blockSize=blockSize;
        this.blockSerialNumber=blockSerialNumber;
        this.fileName=fileName;
        this.fileId = fileId;
    }


    public String getBlockId() {
        return blockId;
    }

    public void setBlockId(String blockId) {
        this.blockId = blockId;
    }

    public String getBlockName() {
        return blockName;
    }

    public void setBlockName(String blockName) {
        this.blockName = blockName;
    }

    public String getBlockSize() {
        return blockSize;
    }

    public void setBlockSize(String blockSize) {
        this.blockSize = blockSize;
    }


    public String getBlockSerialNumber() {
        return blockSerialNumber;
    }

    public void setBlockSerialNumber(String blockSerialNumber) {
        this.blockSerialNumber = blockSerialNumber;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getFileId() {
        return fileId;
    }

    public void setFileId(String fileId) {
        this.fileId = fileId;
    }


    @Override
    public int compareTo(Object another) {
        MDFSBlock block= (MDFSBlock) another;
        return Integer.parseInt(this.blockSize)-Integer.parseInt(block.blockSize);
    }
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (o == null || getClass() != o.getClass()) return false;

        MDFSBlock block = (MDFSBlock) o;

        if (((MDFSBlock) o).blockName.equals(this.blockName)){
            return true;
        }else {
            return false;
        }
    }

}
