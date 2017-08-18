package com.megalink.study.controller;


import com.megalink.study.mappers.DomainMapper;
import com.megalink.study.mappers.LcmuserMapper;
import com.megalink.study.model.nochange.*;
import com.megalink.study.service.SiteService;
import com.megalink.study.utils.Md5Utils;
import com.megalink.study.utils.SetFolder;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.commons.CommonsMultipartFile;
import org.springframework.web.servlet.ModelAndView;

import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by chenhansen on 2017/7/25.
 */
@Controller
@RequestMapping("/site")
public class SiteController {
    @Autowired
    private SiteService siteService;
    @Autowired
    private LcmuserMapper lcmuserMapper;
    @Autowired
    private DomainMapper domainMapper;

    private Workbook wb;
    private String siteID;//从domain（excel）中读取的ID
    private Long domainID;//存储domain表中的主键
    private Long[] groupID=new Long[4];
    private Long[] containerID=new Long[20];
    private Long[] userID=new Long[20];
    private Date date;
    private int containerNum;
    private Long superAdminID;
    private boolean isSuccess;
    private Map<String,Long>  containerMap=new HashMap<String, Long>();
    private ModelAndView model=new ModelAndView("/login/fail");



    @RequestMapping(value="/resite",method = RequestMethod.GET)
    public String fail(){
        return "login/site";
    }
    @RequestMapping(value="/site",method = RequestMethod.POST)
    public ModelAndView createSite(@RequestParam("file") CommonsMultipartFile file){
        isSuccess=true;
        wb=null;
        try {
            InputStream is = file.getInputStream();
            try{
                wb= WorkbookFactory.create(is);
            }catch (InvalidFormatException e){
                e.printStackTrace();
            }
            insertDomain(wb,0);
            if(!isSuccess){
                return model;
            }
            insertGroup(wb,1);
            setUsability(wb,siteID,2);
            insertUsers(wb,3);
            insertContainer(wb,4);
            insertWfdefine(wb,5);
            insertFolder(wb,6);
        }catch (IOException e){
            e.printStackTrace();
        }
        if(!isSuccess)
            return model;
      return new ModelAndView("login/success");
    }
   public void insertDomain(Workbook wb,int m){
       DomainWithBLOBs domain=new DomainWithBLOBs();
       String []str;//保存excel中返回的字符串
       date=new Date();
       try{
           //读取excel中的内容，并将内容存放在str数组中
           str= siteService.readExcel(wb,m);
           if(str[0]==""||str[1]==""||str[0].length()<2){
               isSuccess=false;
               model.addObject("errorMsg","站点ID或名称不能为空，请仔细检查后重新导入创建");
               return;
           }
           List<String> domainKeys=domainMapper.getDomainKey();
           for(String key:domainKeys){
               if(str[0].equals(key)){
                   isSuccess=false;
                   model.addObject("errorMsg","该站点ID已存在，请修改后重新导入创建");
                   return;
               }
           }
           List<String> domainNames=domainMapper.getDomainName();
           for(String name:domainNames){
               if(str[1].equals(name)){
                   isSuccess=false;
                   model.addObject("errorMsg","该站点名称已存在，请修改后重新导入创建");
                   return;
               }
           }

           domain.setCREATED(date);
           domain.setMODIFIED(date);
           domain.setISAVAILABLE(1);
           domain.setCREATORID("superAdmin");
           //将信息插入到数据库中

           siteService.insertDomain(domain,str);
           siteID=str[0];//将站点ID保存到groupID中，后面创建分组会用到
           domainID=domain.getDOMAIN_ID();//将插入记录行的自增ID保存在domainID中，后面多个数据表会用到
            /*在config文件夹下新建一个以站点ID为名称的文件夹，
            同时将config/default文件夹下的所有内容拷贝至新文件夹中；
            在custom文件夹下新建一个以站点ID为名称的文件夹，
            同时将custom/default文件夹下的所有内容拷贝至新文件夹中*/
           String fromfile="C:\\softwarekit\\apache-tomcat-7.0.72\\webapps\\imeWeb\\config";
           String tofile=fromfile+"/"+siteID;
           fromfile=fromfile+"/"+"NEWUI";
           siteService.copyFolder(fromfile,tofile);
           fromfile="C:\\softwarekit\\apache-tomcat-7.0.72\\webapps\\imeWeb\\custom";
           tofile=fromfile+"/"+siteID;
           fromfile=fromfile+"/"+"NEWUI";
           siteService.copyFolder(fromfile,tofile);
       }catch (IOException e){
           e.printStackTrace();
       }
   }

    public void insertGroup(Workbook wb,int m){
        GroupWithBLOBs group = new GroupWithBLOBs();
        String[] str;
        date=new Date();
        String [] strId={siteID+"G",siteID.substring(0,2)+"MapStructureMG"
                ,siteID.substring(0,2)+"DomainBAG",siteID.substring(0,2)+"MegaTestG"};
        group.setCREATED(date);
        group.setMODIFIED(date);
        group.setDOMAIN_ID((float)domainID);
        try {
            str= siteService.readExcel(wb,m);
            //循环往数据库插入四条分组记录
            for(int i=0;i<4;i++) {
                siteService.insertGroup(group,strId[i],str[i*2],str[i*2+1]);
                groupID[i]=group.getID();
            }
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    public void setUsability(Workbook wb,String siteID,int m){
        try {
            siteService.setUsability(wb,siteID,m);
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    public void insertUsers(Workbook wb,int m){

        MemberLinkWithBLOBs memberlink=new MemberLinkWithBLOBs();
        superAdminID=(long)0;
        date=new Date();
        boolean isExist=false;
        String ExistName="";
        for(int i=0;i<20;i++)userID[i]=(long)0;
        String []str;//保存excel中返回的字符串
        LcmuserWithBLOBs lcmuser = new LcmuserWithBLOBs();
        lcmuser.setCREATED(date);
        lcmuser.setMODIFIED(date);
        lcmuser.setSTATUS("active");
        lcmuser.setLOGINFAILCOUNT((float)0);
        lcmuser.setPWDCOMPLEX("security.normal");
        try {
            str= siteService.readExcel(wb,m);
           //将读取的信息插入到数据库中
            //domain和user建立联系
            memberlink.setROLEACLASS("com.imecms.container.Domain");
            memberlink.setROLEBCLASS("com.imecms.principal.User");
            memberlink.setROLEAID((float)domainID);

            for(int i=0;str[5*i]!=""||str[5*i+1]!=""||str[5*i+2]!=""||str[5*i+3]!=""||str[5*i+4]!="";i++) {
                if(str[5*i].length()>=4) {//用户ID的最小长度为4
                    lcmuser.setPRINCIPALID(str[i * 5]);
                    lcmuser.setNAME(str[i * 5 + 1]);
                    lcmuser.setPASSWORD(Md5Utils.md5(str[i * 5 + 2]));
                    lcmuser.setEMAIL(str[i * 5 + 3]);
                    lcmuser.setTEL(str[i * 5 + 4]);
                    if (siteService.insertUser(lcmuser)) {//如果数据能插入数据库
                        userID[i] = lcmuser.getID();
                        memberlink.setPRINCIPALID(str[i * 5]);
                        memberlink.setROLEBID((float) userID[i]);
                        siteService.insertMemberLink(memberlink);
                    }
                    else{
                        isExist=true;
                        ExistName+=lcmuser.getPRINCIPALID()+", ";
                    }
                }
                else{
                    model.addObject("errorUserLength","创建完成，但存在用户ID长度小于4的记录，请检查，并重新手动添加！");
                    isSuccess=false;
                }
            }
            if(isExist){
                isSuccess=false;
                model.addObject("userExist","创建完成，但数据库中已存在用户:"+ExistName);
            }
            superAdminID=lcmuserMapper.getSuperAdminId();
            if(superAdminID==null)
            {
                lcmuser=new LcmuserWithBLOBs();
                lcmuser.setCREATED(date);
                lcmuser.setMODIFIED(date);
                lcmuser.setSTATUS("active");
                lcmuser.setLOGINFAILCOUNT((float)0);
                lcmuser.setPWDCOMPLEX("security.normal");
                lcmuser.setNAME("Adminwbtrator");
                lcmuser.setPRINCIPALID("superAdmin");
                lcmuser.setPASSWORD(Md5Utils.md5("1"));
                siteService.insertUser(lcmuser);
                superAdminID=lcmuser.getID();
            }
            memberlink.setROLEBID((float)superAdminID);
            memberlink.setPRINCIPALID("superAdmin");
            siteService.insertMemberLink(memberlink);
        }catch (IOException e){
            e.printStackTrace();
        }
    }
    public void insertContainer(Workbook wb,int m){
        containerNum=0;
        date=new Date();
        String[] str;
        ContainerWithBLOBs  container=new ContainerWithBLOBs();
        Permission  permwbsion=new Permission();
        MemberLinkWithBLOBs memberlink=new MemberLinkWithBLOBs();
        container.setCreated(date);
        container.setModified(date);
        container.setDomain_id(domainID);
        container.setCreatorid("superAdmin");
        container.setIsavailable(BigDecimal.ONE);
        for(int i=0;i<20;i++)containerID[i]=(long)0;
        try{
            str= siteService.readExcel(wb,m);
            for(int i=0;str[2*i]!=""||str[2*i+1]!="";i++) {
             //添加内容库
                container.setName(str[2*i]);
                container.setDescription(str[2*i+1]);
                siteService.insertContainer(container);
                containerID[i]=container.getContainer_id();
                containerMap.put(container.getName(),containerID[i]);
                containerNum++;
            }
            //内容库成员添加
           /* 1）P-XXXX（成员（组）：站点ID+G；站点ID第1-2个字母+MapStructureMG；站点ID第1-2个字母+DomainBAG；）；
            2）R-重用库（成员（组）：站点ID+G；站点ID第1-2个字母+MapStructureMG；站点ID第1-2个字母+DomainBAG；）；
            3）D-文档（成员（组）：站点ID+G；站点ID第1-2个字母+MapStructureMG；站点ID第1-2个字母+DomainBAG；）；*/
            memberlink.setROLEACLASS("com.imecms.container.Container");
            memberlink.setROLEBCLASS("com.imecms.principal.Group");
            memberlink.setPRINCIPALID("");
            for(int i=1;containerID[i]!=(long)0;i++)
                for(int j=0;j<3;j++){
                    memberlink.setROLEAID((float)containerID[i]);
                    memberlink.setROLEBID((float)groupID[j]);
                    siteService.insertMemberLink(memberlink);
                }
            //P-内部存储库（成员（组）：站点ID第1-2个字母+MegaTest）
            memberlink.setROLEAID((float)containerID[0]);
            memberlink.setROLEBID((float)groupID[3]);
            siteService.insertMemberLink(memberlink);
           /* 2）关键字ID第1-2个字母+MapStructureMG；（它的成员有：关键字ID+G、关键字ID第1-2个字母+DomainBAG;
            且组的成员在数据库中的表memberlink中
）*/
            memberlink.setROLEACLASS("com.imecms.principal.Group");
            memberlink.setROLEBCLASS("com.imecms.principal.Group");
            memberlink.setROLEAID((float)groupID[1]);
            memberlink.setROLEBID((float)groupID[0]);
            siteService.insertMemberLink(memberlink);
            memberlink.setROLEBID((float)groupID[2]);
            siteService.insertMemberLink(memberlink);
            //domain和group建立联系
            memberlink.setROLEACLASS("com.imecms.container.Domain");
            memberlink.setROLEBCLASS("com.imecms.principal.Group");
            memberlink.setROLEAID((float)domainID);
            for(int i=0;i<4;i++){
                memberlink.setROLEBID((float)groupID[i]);
                siteService.insertMemberLink(memberlink);
            }
            //ID+G和user建立联系
            memberlink.setROLEACLASS("com.imecms.principal.Group");
            memberlink.setROLEBCLASS("com.imecms.principal.User");
            memberlink.setROLEAID((float)groupID[0]);
            for(int i=0;userID[i]!=(long)0;i++){
             memberlink.setROLEBID((float)userID[i]);
                siteService.insertMemberLink(memberlink);
            }
            //superAdmin和group建立关系
            memberlink.setROLEBID((float)superAdminID);
            for(int i=0;i<4;i++){
                memberlink.setROLEAID((float)groupID[i]);
                siteService.insertMemberLink(memberlink);
            }
                //内容库成员权限设置
            String s1=siteID+"G";
            String s2=siteID.substring(0,2)+"DomainBAG";
            String s3=siteID.substring(0,2)+"MegaTest";
            permwbsion.setDOMAIN_ID("com.imecms.container.Domain:"+domainID);
            permwbsion.setSOFTTYPE("all");
            permwbsion.setSTATUS("all");
            permwbsion.setMODIFIED(date);
            permwbsion.setCREATED(date);
            permwbsion.setMODIFIERID("superAdmin");
            permwbsion.setCREATERID("superAdmin");
            permwbsion.setTYPE("group");
            permwbsion.setREAD_PERMISSION("Y");
            permwbsion.setWRITE_PERMISSION("Y");
            permwbsion.setDELETE_PERMISSION("Y");
            permwbsion.setADD_PERMISSION("Y");
            permwbsion.setALL_PERMISSION("Y");
            permwbsion.setMAG_PERMISSION("Y");
            permwbsion.setDOWNLOAD_PERMISSION("Y");
            permwbsion.setPRINT_PERMISSION("Y");
            permwbsion.setPRINCIPALID(s2);
            permwbsion.setUSERID("com.imecms.container.Group:"+groupID[2]);
            //先设置站点ID第1-2个字母+DomainBAG（所有权限）；
            for(int i=1;i<containerNum;i++) {
                permwbsion.setCONTAINER_ID("com.imecms.container.Container:"+containerID[i]);
                siteService.insertPermission(permwbsion);
            }
            //然后设置站点ID第1-2个字母+MegaTest(所有权限）;
            permwbsion.setPRINCIPALID(s3);
            permwbsion.setUSERID("com.imecms.container.Group:"+groupID[3]);
            permwbsion.setCONTAINER_ID("com.imecms.container.Container:"+containerID[0]);
            siteService.insertPermission(permwbsion);
            //再设置站点ID+G；
            permwbsion.setPRINCIPALID(s1);
            permwbsion.setUSERID("com.imecms.container.Group:"+groupID[0]);
            permwbsion.setCONTAINER_ID("com.imecms.container.Container:"+containerID[containerNum-1]);
            siteService.insertPermission(permwbsion);
            permwbsion.setALL_PERMISSION("N");
            permwbsion.setDELETE_PERMISSION("N");
            for(int i=1;i<containerNum-2;i++){
                permwbsion.setCONTAINER_ID("com.imecms.container.Container:"+containerID[i]);
                siteService.insertPermission(permwbsion);
            }
            permwbsion.setCONTAINER_ID("com.imecms.container.Container:"+containerID[containerNum-2]);
            permwbsion.setWRITE_PERMISSION("N");
            permwbsion.setADD_PERMISSION("N");
            permwbsion.setMAG_PERMISSION("N");
            permwbsion.setPRINT_PERMISSION("N");
            siteService.insertPermission(permwbsion);

        }catch (IOException e){
            e.printStackTrace();
        }
    }

    public void insertWfdefine(Workbook wb,int m) {
        date=new Date();
        String[] str;
        WfdefineWithBLOBs wfdefine=new WfdefineWithBLOBs();
        wfdefine.setCREATED(date);
        wfdefine.setMODIFIED(date);
        wfdefine.setDOMAIN_ID(domainID);
        wfdefine.setISLATEST((long)1);
        wfdefine.setVERSION("1");
        wfdefine.setSTATUS("active");
        try {
            str= siteService.readExcel(wb,m);
            //将读取的信息插入到数据库中
            siteService.insertWfdefine(wfdefine,str);
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    public void insertFolder(Workbook wb,int m){
        date=new Date();
       // String[][] str;
        FolderWithBLOBs folder=new FolderWithBLOBs();
        folder.setCREATED(date);
        folder.setMODIFIED(date);
        folder.setDOMAIN_ID(domainID);
        folder.setCREATORID("superAdmin");
        siteService.insertFolder(folder,containerMap,wb,m);
    }
}
