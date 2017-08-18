package com.megalink.study.utils;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

/**
 * Created by chenhansen on 2017/7/26.
 */
public class ReadExcel {

    private String[] str=new String[200];  //将读取的excel中的内容放入数组中
    private int num;
    public ReadExcel(){//初始化各个数据

        for(int i=0;i<200;i++)
            str[i]="";
    }
    public String[] getStr() {
        return str;
    }
    public void readExcel(Workbook wb,int m){
        for(int i=0;i<num;i++)
            str[i]="";
        num=0;
        try {
            Sheet sheet=wb.getSheetAt(m);//获取sheet
            Row row;
            Cell cell;
            for(int i=1;i<=sheet.getLastRowNum();i++){//从第一行开始，循环遍历excel表中的内容
                row=sheet.getRow(i);
                for(int j=1;j<row.getLastCellNum();j++){
                    cell=row.getCell(j);

                    if(cell==null) str[num]="";
                    else {
                        cell.setCellType(Cell.CELL_TYPE_STRING);
                        str[num] = cell.toString();
                    }
                    num++;
                }
            }
        } catch(Exception e){
            e.printStackTrace();
        }
    }

}
