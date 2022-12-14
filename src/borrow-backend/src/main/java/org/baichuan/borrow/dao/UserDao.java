package org.baichuan.borrow.dao;


import com.alibaba.fastjson.JSONObject;
import org.apache.ibatis.annotations.*;
import org.baichuan.borrow.domin.User;

import java.util.List;


@Mapper
public interface UserDao {


    @Select("select * from ${table} where ${key}=#{value}")  //拿出某一条数据
    public JSONObject getByField(@Param("key")String key, @Param("value")String value, @Param("table")String table);

    @Select("select * from ${table} ")  //拿出某张表的全部数据
    public List<JSONObject> getallList(@Param("table")String table);

    @Select("select * from ${table} where ${key} like concat('%',#{value},'%')") //拿出
    public List<JSONObject> getList(@Param("key")String key,@Param("value")String value,@Param("table")String tablename);

    @Select("select max(directid) from Directory where libid=#{value}" )
    public String getMax(@Param("value") String value);

    @Insert("insert into user(uuid,realname,password,state)values(#{uuid}, #{realname},#{password},#{state})")
    public int insertUser0(User user);


    @Insert("insert into ${table}(${key})values(#{value})")
    public int insertUser(@Param("table") String table,@Param("key")String key,@Param("value")String value);

   // public int insertCollectin(Collection collection);

   // @Update("update User set realname=#{realname},password=#{password},state=#{state} where uuid = #{uuid}")
    @Update("update ${table} set ${fieldName}=#{fieldValue} where ${key}=#{value}")
    public int editUser(@Param("table") String table,@Param("fieldName") String fieldName,@Param("fieldValue") String fieldValue,@Param("key")String key,@Param("value")String value);

    @Delete("delete from ${table} where ${key}=#{value}")
    public int deleteLine(@Param("key")String key,@Param("value")String value,@Param("table")String tablename);
}
