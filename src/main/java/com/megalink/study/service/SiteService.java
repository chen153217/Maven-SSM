package com.megalink.study.service;

import com.megalink.study.mappers.*;
import com.megalink.study.model.nochange.*;
import com.megalink.study.utils.ReadExcel;
import com.megalink.study.utils.SetFolder;
import com.megalink.study.utils.Usability;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.*;
import java.util.List;
import java.util.Map;

/**
 * Created by chenhansen on 2017/7/25.
 */
@Service
public class SiteService {
   @Autowired
   private GroupMapper groupMapper;
   @Autowired
   private DomainMapper domainMapper;
   @Autowired
   private LcmuserMapper  lcmuserMapper;
   @Autowired
   private ContainerMapper containerMapper;
   @Autowired
   private MemberLinkMapper memberLinkMapper;
   @Autowired
   private WfdefineMapper  wfdefineMapper;
   @Autowired
   private PermissionMapper permissionMapper;
   @Autowired
   private FolderMapper   folderMapper;

   ReadExcel re=new ReadExcel();
   Usability usability=new Usability();

   public void insertDomain(DomainWithBLOBs domain,String[] str){
      domain.setKEY(str[0]);
      domain.setNAME(str[1]);
      domain.setTYPE(str[2]);
      domain.setDESCRIPTION(str[3]);
      domainMapper.insertDomain(domain);
   }
   public void insertGroup(GroupWithBLOBs group, String id, String name, String info)
   {
      group.setNAME(name);
      group.setPRINCIPALID(id);
      group.setDESCRIPTION(info);
      groupMapper.insertGroup(group);
   }
   public void setUsability(Workbook wb,String siteID,int m)throws IOException{
      String usapath="C:\\softwarekit\\apache-tomcat-7.0.72\\webapps\\imeWeb\\config"+"/"+siteID+"/usability.pcf";
      String enPath="C:\\softwarekit\\apache-tomcat-7.0.72\\webapps\\imeWeb\\config"+"/"+siteID+"/lang/i18n_en.properties";
      String zhpath="C:\\softwarekit\\apache-tomcat-7.0.72\\webapps\\imeWeb\\config"+"/"+siteID+"/lang/i18n_zh.properties";
      usability.init(wb,m);
      usability.setUsability(usapath);
      usability.setEnFile(enPath);
      usability.setZhFile(zhpath);
   }
    public boolean insertUser(LcmuserWithBLOBs lcmuser){
       boolean isInsert=true;
       List<String> principalID=lcmuserMapper.getUsers();
       for(String s:principalID)
          if(lcmuser.getPRINCIPALID().equals(s)){
             isInsert=false;
          }
       if (isInsert)
       lcmuserMapper.insertUser(lcmuser);
       return isInsert;
    }

    public void insertContainer(ContainerWithBLOBs container){
       containerMapper.insertContainer(container);
    }

    public void insertMemberLink(MemberLinkWithBLOBs memberlink)
    {
       memberLinkMapper.insertMemberLink(memberlink);
    }

    public void insertWfdefine(WfdefineWithBLOBs wfdefine,String[] str){
       for(int i=0;str[3*i]!=""||str[3*i+1]!=""||str[3*i+2]!="";i++) {
          wfdefine.setNAME(str[3*i]);
          wfdefine.setTITLE(str[3*i+1]);
          wfdefine.setXMLFILE(str[3*i+2]);
          wfdefineMapper.insertWfdefine(wfdefine);
       }
    }

    public void insertPermission(Permission permission){
       permissionMapper.insertPermission(permission);
    }
    public void insertFolder(FolderWithBLOBs folder, Map<String,Long> map,Workbook wb,int m){
       SetFolder setFolder=new SetFolder();
      //folderMapper.insertFolder(folder);
       setFolder.readExcel(folder,map,wb,m,folderMapper);
}
    public String[] readExcel( Workbook wb,int m)throws IOException{
       re.readExcel(wb,m);
       return re.getStr();
    }
   public void copyFolder(String oldPath, String newPath) {    //第一步，拷贝文件
      try {
         (new File(newPath)).mkdir();//没有则创建文件夹
         File a=new File(oldPath);
         String[] file=a.list();
         File temp=null;
         for (int i = 0; i < file.length; i++) {
            if(oldPath.endsWith(File.separator)){
               temp=new File(oldPath+file[i]);
            }
            else{
               temp=new File(oldPath+File.separator+file[i]);
            }
            if(temp.isFile()){

               FileInputStream input = new FileInputStream(temp);
               FileOutputStream output = new FileOutputStream(newPath + "/" +
                       (temp.getName()).toString());
               byte[] b = new byte[1024 * 5];
               int len;
               while ( (len = input.read(b)) != -1) {
                  output.write(b, 0, len);
               }
               output.flush();
               output.close();
               input.close();
            }
            if(temp.isDirectory()){//如果是子文件夹
               copyFolder(oldPath+"/"+file[i],newPath+"/"+file[i]);
            }
         }
      }
      catch (Exception e) {
         System.out.println("复制整个文件夹内容操作出错");
         e.printStackTrace();
      }
   }
}
