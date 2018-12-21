package com.buffer;

import org.apache.commons.collections.CollectionUtils;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Collections;
import java.util.Random;
import java.util.TreeSet;

public class CutFileDemo {


    public static void main(String[] args) throws Exception {
        /*long start = System.currentTimeMillis();
        File file = new File("D:\\app\\1.txt");
        FileWriter fw = null;
        BufferedWriter bw = null;
        try{
            fw = new FileWriter(file,true);
            bw = new BufferedWriter(fw);
            for(int i=0 ;i<= 5000;i++){
                StringBuffer stringBuffer = new StringBuffer();
                Random random = new Random();
                int r1 = random.nextInt(10);
                for(int j = 0;j<= r1 ;j++){
                    stringBuffer.append(random.nextInt(10));
                }
                bw.write(stringBuffer.toString()+ "\r\n");
            }
            bw.flush();
            bw.close();
            fw.close();
            System.out.println("话费当前时间： "+ (System.currentTimeMillis()-start));
        }catch (Exception e){
            e.printStackTrace();
        }*/
        /*TreeSet<Integer> set = new TreeSet<>();
        set.add(Integer.parseInt("12"));
        set.add(14);
        set.add(9);
        set.add(5);
        set.add(7);
        set.add(17);
        set.add(16);
        System.out.println(set);*/

        Long a = 119731808l;
        Long b = 12201238l;
        System.out.println((int)(a-b));

    }



}
