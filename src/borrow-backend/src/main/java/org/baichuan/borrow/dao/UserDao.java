package org.baichuan.borrow.dao;


import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.baichuan.borrow.domin.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;


@Mapper
public interface UserDao {
    @Insert("insert into user(id,realname,password,state)values(#{id}, #{realname},#{password},#{state})")
    public int insert(User user);

    @Select("select * from user where id=#{id}")
    public User getById(@Param("uuid")String id);
}
