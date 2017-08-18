package com.megalink.study.utils;

import com.megalink.study.mappers.FolderMapper;
import com.megalink.study.model.nochange.FolderWithBLOBs;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by chenhansen on 2017/8/14.
 */
public class SetFolder {

    private Sheet sheet;
    private Row row;
    private Cell cell;

    private List<Long> pId=new ArrayList<Long>(20);
    private List<String> pPath=new ArrayList<String>(20);
    private int irow;
    private int icol;
    public void readExcel(FolderWithBLOBs folder,Map<String,Long>map,Workbook wb,int m,FolderMapper folderMapper){
        for(int t=0;t<20;t++){
            pId.add((long)0);
            pPath.add("*");
        }
        try{
            sheet=wb.getSheetAt(m);//获取sheet
            //首先遍历第一列,根据内容库的名称给forder设置folder的对应的内容库的ID
            for(irow=2;irow<=sheet.getLastRowNum();irow++){
                if(sheet.getRow(irow)!=null&&sheet.getRow(irow).getCell(0)!=null&&
                        sheet.getRow(irow).getCell(0).toString()!=""){
                    row=sheet.getRow(irow);
                    cell=row.getCell(0);
                    cell.setCellType(Cell.CELL_TYPE_STRING);
                    for(Map.Entry<String,Long>entry:map.entrySet()){
                        String s1=entry.getKey().toString();
                        String s2=cell.toString();
                        if(s1.equals(s2)){
                            folder.setCONTAINER_ID(entry.getValue());
                        }
                    }
                    //开始遍历第二列，根据找到的内容往数据库里插入folder
                    if(folder.getCONTAINER_ID()!=null){
                        irow++;//找到产品库,行数加1

                        for(;sheet.getRow(irow)!=null&&sheet.getRow(irow).getCell(1)!=null&&
                                sheet.getRow(irow).getCell(1).toString()!="";){
                            folder.setPARENT_FOLDER_ID(null);
                            cell=sheet.getRow(irow).getCell(1);
                            cell.setCellType(Cell.CELL_TYPE_STRING);
                            folder.setFOLDER_NAME(cell.toString());
                            folder.setFOLDER_PATH("\\"+cell.toString());
                            folderMapper.insertFolder(folder);
                            irow++;//找到了，则开始遍历下一列
                            pId.set(2,folder.getFOLDER_ID());
                            pPath.set(2,folder.getFOLDER_PATH());
                            icol=2;
                            findNext(folder,folderMapper,icol);
                        }
                        irow--;
                    }
                }
            }

        }catch(Exception e){
            e.printStackTrace();
        }
    }
    public void findNext(FolderWithBLOBs folder,FolderMapper folderMapper,int num){

        if(sheet.getRow(irow)!=null&&sheet.getRow(irow).getCell(num)!=null&&
                sheet.getRow(irow).getCell(num).toString()!=""){
            folder.setPARENT_FOLDER_ID(pId.get(num));
            cell=sheet.getRow(irow).getCell(num);
            cell.setCellType(Cell.CELL_TYPE_STRING);
            folder.setFOLDER_NAME(cell.toString());
            folder.setFOLDER_PATH(pPath.get(num)+"\\"+cell.toString());
            folderMapper.insertFolder(folder);
            ++irow;++num;//如果有，则遍历下一行下一列
            pId.set(num,folder.getFOLDER_ID());//遍历下一个行前，先把父节点保存起来
            pPath.set(num,folder.getFOLDER_PATH());
            findNext(folder,folderMapper,num);
        }
        else{
            num--;//如果没有，则遍历下一行
            if(num>=2)findNext(folder,folderMapper,num);
        }
    }

/*    public void readExcel(FolderWithBLOBs folder, Map<String,Long> map, Workbook wb, int m, FolderMapper folderMapper){
        try {
            for(int t=0;t<20;t++){
                pId.add((long)0);
                pPath.add("*");
            }
                sheet=wb.getSheetAt(m);//获取sheet
            //首先遍历第一列,根据 内容库的名称给forder设置folder的对应的内容库的ID
            for(i=2;i<=sheet.getLastRowNum();i++){
                if(sheet.getRow(i)!=null&&sheet.getRow(i).getCell(0)!=null&&
                        sheet.getRow(i).getCell(0).toString()!=""){
                    row=sheet.getRow(i);
                    cell=row.getCell(0);
                    cell.setCellType(Cell.CELL_TYPE_STRING);
                    for(Map.Entry<String,Long> entry : map.entrySet()){
                        String s1=entry.getKey().toString();
                        String s2=cell.toString();
                        if(s1.equals(s2)) {
                            folder.setCONTAINER_ID(entry.getValue());
                        }
                    }
                //开始遍历第二列，根据找到的内容往数据库里插入folder
                    if(folder.getCONTAINER_ID()!=null){
                        for(j=i+1,i=i+1;sheet.getRow(j)!=null&&sheet.getRow(j).getCell(1)!=null&&
                                sheet.getRow(j).getCell(1).toString()!="";){
                            folder.setPARENT_FOLDER_ID(null);
                            cell=sheet.getRow(j).getCell(1);
                            cell.setCellType(Cell.CELL_TYPE_STRING);
                            folder.setFOLDER_NAME(cell.toString());
                            folder.setFOLDER_PATH("\\" + cell.toString());
                            folderMapper.insertFolder(folder);
                            ++j;++i;//找到了，则开始遍历下一列
                            folderID = folder.getFOLDER_ID();
                            parent_path = folder.getFOLDER_PATH();
                            col=2;
                            findNext(folder,folderMapper,j,col,folderID,parent_path);
                        }
                    }
              }
            }
        } catch(Exception e){
            e.printStackTrace();
        }
    }
    public void findNext(FolderWithBLOBs folder,FolderMapper folderMapper,int rows ,int num,Long folderId,String parentPath) {
        pId.set(num,folderId);//遍历下一个行前，先把父节点保存起来
        pPath.set(num,parentPath);
        if (sheet.getRow(rows) != null && sheet.getRow(rows).getCell(num) != null &&
                sheet.getRow(rows).getCell(num).toString() != "" && i <= sheet.getLastRowNum()){
           folder.setPARENT_FOLDER_ID(pId.get(num));
            cell=sheet.getRow(rows).getCell(num);
            cell.setCellType(Cell.CELL_TYPE_STRING);
            folder.setFOLDER_NAME(cell.toString());
            folder.setFOLDER_PATH(pPath.get(num)+ "\\" + cell.toString());
            folderMapper.insertFolder(folder);
            ++rows;++num;//如果有，则遍历下一行下一列
            i=rows;
            j=rows;
            folderId=folder.getFOLDER_ID();
            parentPath=folder.getFOLDER_PATH();
           findNext(folder, folderMapper,rows,num,folderId,parentPath);
        }
        else {
           --num;//如果没有，则遍历下一行
           if(num>=2)
               findNext(folder, folderMapper,rows,num,pId.get(num),pPath.get(num));
           else
               --i;
       }
    }*/
}
