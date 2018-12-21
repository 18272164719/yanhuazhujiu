package com.buffer;

import java.io.BufferedReader;
import java.io.IOException;

public class FileInfo implements Comparable<FileInfo>{

    private String fileName = "";

    private BufferedReader reader;

    private Long currentNum ;

    public FileInfo(BufferedReader reader,String fileName) throws IOException {
        this.reader = reader;
        this.currentNum = Long.parseLong(reader.readLine().trim());
        this.fileName = fileName;
    }


    public void readNext() throws IOException {
        String data = reader.readLine();
        if(data != null){
            this.currentNum = Long.parseLong(data.trim());
        }else {
            this.currentNum = -1l;
        }
    }

    public void close() throws IOException {
        this.reader.close();
    }

    @Override
    public int compareTo(FileInfo o) {
        if(!o.getCurrentNum().equals(this.currentNum))
            return (int)(this.currentNum - o.getCurrentNum());
        else
            return this.fileName.compareTo(o.getFileName());
    }

    public BufferedReader getReader() {
        return reader;
    }

    public void setReader(BufferedReader reader) {
        this.reader = reader;
    }

    public Long getCurrentNum() {
        return currentNum;
    }

    public void setCurrentNum(Long currentNum) {
        this.currentNum = currentNum;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }
}
