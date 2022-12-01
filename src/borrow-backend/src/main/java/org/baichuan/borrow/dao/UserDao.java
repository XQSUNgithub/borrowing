package org.baichuan.borrow.dao;


import org.apache.ibatis.annotations.*;
import org.baichuan.borrow.domin.User;

import java.util.List;


@Mapper
public interface UserDao {


    @Select("select * from #{table} where #{key}=#{value}")
    public Object getByField(@Param("key")String key,@Param("value")String value,@Param("table")String tablename);

    @Select("select * from #{table} where #{key}!=#{value}")
    public List getallList(@Param("key")String key,@Param("value")String value,@Param("table")String tablename);

    @Select("select * from #{table} where #{key}=#{value}")
    public List getList(@Param("key")String key,@Param("value")String value,@Param("table")String tablename);

    @Insert("insert into user(uuid,realname,password,state)values(#{uuid}, #{realname},#{password},#{state})")
    public int insertUser(User user);

   // public int insertCollectin(Collection collection);

    @Update("update User set realname=#{realname},password=#{password},state=#{state} where uuid = #{uuid}")
    public int editUser(User user);
}
