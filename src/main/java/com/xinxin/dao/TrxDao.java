package com.xinxin.dao;

import java.util.List;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import com.xinxin.meta.BuyList;

public interface TrxDao {
@Select("select a.contentId as id, b.title, b.icon as image, a.price as buyPrice, a.quantity as buyNum, a.time as buyTime, a.price*a.quantity as total from trx a left join content b on a.contentId = b.id left join person c on c.id = a.personId where c.userName = #{userName} ")
public List<BuyList> getAccount(String userName);

@Insert("insert into trx (contentId,personId,price,time,quantity) values(#{contentId}, #{personId},(select price from content where content.id = #{contentId}) * #{quantity}, #{time}, #{quantity}) ")
public void insertTrx(@Param("contentId") int contentId,@Param("personId") int personId,  @Param("time") long time,@Param("quantity") int quantity);
}
