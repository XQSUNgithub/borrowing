package org.baichuan.borrow.util;

import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.baichuan.borrow.dao.UserDao;
import org.baichuan.borrow.domin.Directory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileNotFoundException;
import java.lang.reflect.Field;
import java.util.*;

@Slf4j
@Service
public class CollectionIdGenerator {

    @Autowired
    UserDao userDao;

    public Map<String,String> map=new HashMap<>();
    public String generateId(String info) throws ClassNotFoundException, IllegalAccessException {
        log.info("generated");
        //Date time1 = new Date();
        //String random = String.valueOf(time1.getTime()).substring(7, 13) + ((Double) Math.random()).toString().substring(4, 8);
        // String[] keys=info.split(" ");
        //return random;

        //从目录表中由String查到id
        String[] info0=info.split(" ");
        if(!map.containsKey(info0[0])) return null;
        log.info("目录存在");
        String libId=map.get(info0[0]); //目录String 名录String
        //再从名录表中由名录查到id
        Directory newDirectory=JSON.toJavaObject(userDao.getByField("directory",libId+info0[1],"Directory"),Directory.class);
        if(newDirectory==null){
            String maxDid=userDao.getMax(libId);
            Directory newDictionary0=new Directory();
            newDictionary0.setNum("1");
            int m=Integer.parseInt(maxDid.substring(9, 11))+1;
            String middle;
            if((m+"").length()==1){
               middle="0"+m;
            }else{
               middle=m+"";
            }
            String id= "JDGM"+libId+middle+"0000";
            newDictionary0.setDirectid(id);
            newDictionary0.setLibid(libId);
            newDictionary0.setDirectory(libId+info0[1]);
            userDao.insertUser("Directory","directory",newDictionary0.getDirectory());
            Field[] fields= Class.forName("org.baichuan.borrow.domin."+"Directory").getDeclaredFields();
            for(Field curField:fields){
                curField.setAccessible(true);
                if(curField.getName().equals("directory"))
                    continue;
                userDao.editUser("Directory",curField.getName(),curField.get(newDictionary0).toString(),"directory",newDictionary0.getDirectory());
            }
            return id;
        }else{
            //如果已经存在该名录 报错
           // newDirectory.setNum(Integer.valueOf(newDirectory.getNum())+1+"");
            //可以不要num字段？
            return null;
        }
        //1 查不到 查名录libid下的最大id id++ 插入新名录 num=1 返回编号
        //2 查得到 num+1 返回编号
    }

    public  void initialize() {
        try {
            File file = new File("/Users/sunxiaoqi/Downloads/test/吉林大学地质博物馆岩矿化石标本编号规则.csv");
            // System.out.println(file.getAbsolutePath());
            Scanner sc = new Scanner(file);
            int i=0;
            //从第3行开始
            sc.nextLine(); i++;
            sc.nextLine();i++;
            String newLib=null;
            String libId=null;
            String[] pre=new String[4];
            while (sc.hasNext()) {
                i++;
                String[] temp = sc.nextLine().split(",");
                for(String cur:temp){
                    log.info(cur+">");
                }
                int j=0;
                while(j<temp.length){
                    log.info(String.valueOf(temp[j].length()));
                    if(temp[j].length()<=1)
                    {
                        j++;
                        log.info(String.valueOf(j));
                    }else{
                        switch (j){
                            case 2:{
                                log.info("2"+temp[j]+"end"+j);
                                pre[0]=temp[j];  //pre[0]=temp[2]此时的指向
                                j++;
                                break;
                            }
                            case 4:{
                                log.info("4"+temp[j]+"end"+j);
                                pre[1]=temp[j];
                                j++;
                                break;
                            }
                            case 6:{
                                log.info("6"+temp[j]+"end"+j);
                                pre[2]=temp[j];
                                j++;
                                break;
                            }
                            case 8:{
                                log.info("8"+temp[j]+"end"+j);
                                //放内存
                                pre[3]=temp[j];
                                newLib=pre[0]+pre[1]+pre[2]+pre[3];
                                j++;
                                libId=temp[j];
                                log.info(newLib);
                                map.put(newLib,libId);
                                break;
                            }
                            case 10:{
                                Directory newDirectory=new Directory();
                                newDirectory.setDirectory(libId+temp[j]);
                                j++;
                                j++;
                                newDirectory.setDirectid(temp[j]);
                                newDirectory.setLibid(libId);
                                newDirectory.setNum("1");
                                System.out.println(JSON.toJSON(newDirectory)+newDirectory.getDirectory());
                                //以下为插入数据库的操作
                                /*userDao.insertUser("Directory","directory",newDirectory.getDirectory());
                                Field[] fields= Class.forName("org.baichuan.borrow.domin."+"Directory").getDeclaredFields();
                                for(Field curField:fields){
                                    curField.setAccessible(true);
                                    if(curField.getName().equals("directory"))
                                        continue;
                                    userDao.editUser("Directory",curField.getName(),curField.get(newDirectory).toString(),"directory",newDirectory.getDirectory());
                                }*/
                                //放mysql
                                break;
                            }
                            default:break;
                        }
                        j++;
                    }
                }
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return;
    }
}