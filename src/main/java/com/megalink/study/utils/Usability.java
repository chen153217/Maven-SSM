package com.megalink.study.utils;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

import java.io.*;

/**
 * Created by chenhansen on 2017/8/2.
 */
public class Usability {
    //用来分别保存适用性设置的读取的值
    private String[] name=new String[5];
    private String[] title=new String[5];
    private String[][] value=new String[8][10];
    private String[][] en=new String[8][10];
    private String[][] zh=new String[8][10];
    int exfcount=0;//用来记录扩展属性的数目
    public void initString(){
        for(int i=0;i<5;i++){
            name[i]="";
            title[i]="";
        }
        for(int i=0;i<8;i++)
            for(int j=0;j<10;j++){
                value[i][j]="";
                en[i][j]="";
                zh[i][j]="";
            }
            exfcount=0;
    }
    public void init(Workbook wb,int m){

        String s="";
        try{
            initString();//初始化字符串数组
            Sheet sheet=wb.getSheetAt(m);
            int i=0;
            Row row;
           // Cell cell;
            for(;i<3;i++){
                row=sheet.getRow(i);//首先读取产品，平台读者的数据到value数组中
                for(int j=1;row.getCell(j)!=null;j++){
                    row.getCell(j).setCellType(Cell.CELL_TYPE_STRING);
                    value[i][j-1]=row.getCell(j).toString();
                }
            }
            for(i=4;i<8&&sheet.getRow(i)!=null&&sheet.getRow(i).getCell(1)!=null;i++){//循环遍历扩展属性的name和title
                exfcount++;
                row=sheet.getRow(i);
                row.getCell(1).setCellType(Cell.CELL_TYPE_STRING);
                row.getCell(2).setCellType(Cell.CELL_TYPE_STRING);
                name[i-4]=row.getCell(1).toString();
                title[i-4]=row.getCell(2).toString();
            }
            for(i=4;i<4+exfcount;i++){
                row=sheet.getRow(i);
                for(int j=3;row.getCell(j)!=null;j++) {
                    row.getCell(j).setCellType(Cell.CELL_TYPE_STRING);
                    value[i - 1][j - 3] = row.getCell(j).toString();
                }
            }//至此,value中的值已经全部读取出来

            //开始遍历对应的中文和英文
            for(i=9;i<12+exfcount;i++){
                row=sheet.getRow(i);
                if(row!=null)
                for(int j=1;row.getCell(j)!=null;j++){
                    row.getCell(j).setCellType(Cell.CELL_TYPE_STRING);
                    en[i-9][j-1]=row.getCell(j).toString();
                }

            }
            if(sheet.getRow(18)!=null&&sheet.getRow(18).getCell(1)!=null)//如果存在中文，则读取
             for(i=18;i<21+exfcount;i++){
                row=sheet.getRow(i);
                if(row!=null)
                for(int j=1;row.getCell(j)!=null;j++){
                    row.getCell(j).setCellType(Cell.CELL_TYPE_STRING);
                    zh[i-18][j-1]=row.getCell(j).toString();
                }
            }//至此，所有的中英文全部读取出来
        }catch (Exception e){
            e.printStackTrace();
        }

    }
    public void setUsability(String path){
        StringBuilder result=new StringBuilder("");//用来保存usability中的内容
        String[] name_title={"product","platform","audience"};
        String[] usab={"<?xml version=\"1.0\" encoding=\"UTF-8\"?>",
        "<Profiles>",
        "\t<ProfileClasses>",
        "\t\t\t<Allow>",
        "\t\t\t</Allow>",
        "\t\t</Profile>",
        "\t</ProfileClasses>",
        "</Profiles>"};
        result.append(usab[0]+"\r\n");
        result.append(usab[1]+"\r\n");
        result.append(usab[2]+"\r\n");
        for(int i=0;i<3;i++){//添加三个默认属性
            result.append(getProfile(name_title[i],name_title[i])+"\r\n");
            result.append(usab[3]+"\r\n");
            for(int j=0;value[i][j]!="";j++)//遍历value每行的值
                result.append(getProduct(value[i][j])+"\r\n");
            result.append(usab[4]+"\r\n");
            result.append(usab[5]+"\r\n");
        }
        //开始添加扩展属性
        for(int i=0;i<exfcount;i++){
            result.append(getProfile(name[i],title[i])+"\r\n");
            result.append(usab[3]+"\r\n");
            for(int j=0;value[i+3][j]!="";j++)//遍历value每行的值
                result.append(getProduct(value[i+3][j])+"\r\n");
            result.append(usab[4]+"\r\n");
            result.append(usab[5]+"\r\n");
        }
        result.append(usab[6]+"\r\n");
        result.append(usab[7]+"\r\n");
        setFile(path,result.toString());
        //至此，适用性文件写入完成

    }
    public void setEnFile(String path){

        try{
            StringBuilder result=new StringBuilder("");
            StringBuilder str=new StringBuilder("");
            for(int i=0;i<3+exfcount;i++)
                for(int j=0;value[i][j]!="";j++)
                    str.append(value[i][j]+" = "+en[i][j]+"\r\n");
           // FileReader reader=new FileReader(path);
            FileInputStream fis=new FileInputStream(path);
            BufferedReader buf=new BufferedReader(new InputStreamReader(fis,"UTF-8"));
            String line;
            boolean isRead=true;
            while((line=buf.readLine())!=null){
                if(line.equals("//适用性枚举值")) {//如果读到适用性枚举值
                    result.append(line+"\r\n");
                    result.append(str+"\r\n");
                    isRead = false;
                }
                if(isRead)
                    result.append(line+"\r\n");

                if(line.equals("sDitaScheme = Applicability Option")){
                    result.append(line+"\r\n");
                    isRead = true;
                }
            }
            fis.close();
            buf.close();
           setFile(path,result.toString());

        }catch (Exception e){
            e.printStackTrace();
        }

    }

    public void setZhFile(String path){
        StringBuilder result=new StringBuilder("");
        StringBuilder str=new StringBuilder("");
        if(zh[0][0]!="")
        for(int i=0;i<3+exfcount;i++)
            for(int j=0;value[i][j]!="";j++)
                str.append(value[i][j]+" = "+zh[i][j]+"\r\n");
        try{
            FileInputStream fis=new FileInputStream(path);
            BufferedReader buf=new BufferedReader(new InputStreamReader(fis,"UTF-8"));
            String line;
            boolean isRead=true;
            while((line=buf.readLine())!=null){
                if(line.equals("//适用性枚举值")) {
                    result.append(line+"\r\n");
                    result.append(str+"\r\n");
                    isRead = false;
                }
                if(isRead)
                    result.append(line+"\r\n");
                if(line.equals("sDitaScheme = 适用性选项")){
                    result.append(line+"\r\n");
                    isRead = true;
                }
            }
            fis.close();
            buf.close();
            setFile(path,result.toString());
        }catch (Exception e){
            e.printStackTrace();
        }

    }
    public void setFile(String path,String result){
        try {
            //FileWriter fw = new FileWriter(path,false);
            FileOutputStream fos=new FileOutputStream(path);
            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(fos,"UTF-8"));
            writer.write(result);
            writer.flush();
            writer.close();
            fos.close();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public String getProduct(String str){
        return "\t\t\t\t<Allowed value=\""+str+"\" text=\""+str+"\"/>";
    }

    public String getProfile(String name,String title){
        return "\t\t<Profile name=\""+name+"\" title=\""+title+"\">";
    }
}
