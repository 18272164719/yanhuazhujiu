package com.buffer;


import java.io.*;
import java.util.*;

/**
 * 在规定内存里面把一个大文件进行排序
 */
public class CutBigFile {

    private static final int SPLIT_SIZE = 500;

    private static final String SPLIT_DIR = "D:\\app\\test";

    private static final String MERGE_FILE = "D:\\app\\result.txt";

    private int fileNum = 0;

    //先将大文件切割为很多小文件
    public static void main(String[] args) throws Exception {
        CutBigFile cutBigFile = new CutBigFile();
        File file = new File("D:\\app\\1.txt");
        //拆分 File
        //cutBigFile.splitFile(file);

        //根据依次出牌的逻辑 把小文件进行排序成一个大文件
        cutBigFile.mergeFile();


    }

    private void mergeFile() throws Exception {
        List<FileInfo> fileInfoList = new ArrayList<>();
        File file = new File(SPLIT_DIR);
        if (file.isDirectory()) {
            File[] files = file.listFiles();
            for (File f : files) {
                BufferedReader reader = new BufferedReader(new FileReader(f));
                FileInfo fileInfo = new FileInfo(reader, f.getName());
                fileInfoList.add(fileInfo);
            }
        }
        Collections.sort(fileInfoList);
        System.out.println(fileInfoList);
        File file1 = new File(MERGE_FILE);
        BufferedWriter writer = new BufferedWriter(new FileWriter(file1));
        while (!fileInfoList.isEmpty()) {
            FileInfo fileInfo = fileInfoList.get(0);
            writer.write(fileInfo.getCurrentNum() + "\r\n");
            System.out.println("当前的文件名: " + fileInfo.getFileName() + ", 数字为：" + fileInfo.getCurrentNum());
            fileInfo.readNext();
            Collections.sort(fileInfoList);
            if (fileInfo.getCurrentNum() == -1L) {
                fileInfo.close();
                fileInfoList.remove(fileInfo);
            }
        }
        writer.flush();
        writer.close();
    }

    private void splitFile(File file) {
        String data = null;
        int count = 0;
        //实现内排序
        SortedSet<Long> set = new TreeSet<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            do {
                data = reader.readLine();
                if (data != null) {
                    count++;
                    set.add(Long.valueOf(data.trim()));
                    if (count >= SPLIT_SIZE) {
                        writeFile(SPLIT_DIR, set);
                        count = 0;
                        set.clear();
                    }
                } else if (!set.isEmpty()) {
                    writeFile(SPLIT_DIR, set);
                    count = 0;
                    set.clear();
                }
            } while (data != null);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void writeFile(String fileName, SortedSet<Long> set) {
        System.out.println("================》》 开始切割");
        File dir = new File(fileName);
        if (!dir.isDirectory()) {
            dir.mkdir();
        }
        fileNum++;
        String fname = SPLIT_DIR + System.getProperty("file.separator") + fileNum + ".txt";

        try (BufferedWriter bw = new BufferedWriter(new FileWriter(fname))) {
            Iterator<Long> iterator = set.iterator();
            while (iterator.hasNext()) {
                bw.write(iterator.next() + "\r\n");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
