package com.megalink.study.utils;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;

/**
 * Created by chenhansen on 2017/7/27.
 */
public class DeleteXML {
    public static void deleteXML(String[] str,String url){
        try {
            FileReader input=new FileReader(url);
            BufferedReader reader=new BufferedReader(input);
            StringBuilder s=new StringBuilder("");
            String line;
            int i=0;
            while ((line = reader.readLine()) != null) {
                if (line.indexOf(str[i]) == -1) {
                    try {
                        s.append(line + "\r\n");
                    }catch (NullPointerException e){
                        e.printStackTrace();
                    }
                }else{
                    if(i==(str.length-1)){

                    } else i=i+1;
                }
            }
            input.close();
            reader.close();
            FileWriter fw = new FileWriter(url, false);
            BufferedWriter writer = new BufferedWriter(fw);
            writer.write(s.toString());
            writer.flush();
            writer.close();
            fw.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
