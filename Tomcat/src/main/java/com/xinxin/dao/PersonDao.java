package com.xinxin.dao;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import com.xinxin.meta.Person;

public interface PersonDao {
	@Select("select count(1) from person where userName= #{userName} and password = #{password}")
	public Integer isUserExist(@Param("userName") String userName, @Param("password") String password);
	
	@Select("select * from person where userName= #{userName} and password = #{password}")
	public Person getUser(@Param("userName") String userName, @Param("password") String password);
	
}
