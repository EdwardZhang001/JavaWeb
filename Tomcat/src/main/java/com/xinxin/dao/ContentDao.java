package com.xinxin.dao;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.xinxin.meta.Content;
import com.xinxin.meta.ContentItem;
import com.xinxin.meta.Product;
import com.xinxin.meta.ProductList;
import com.xinxin.meta.ProductTemp;

public interface ContentDao {
	@Results({ @Result(property = "id", column = "id"), 
		    @Result(property = "price", column = "price"),
			@Result(property = "title", column = "title"), 
			@Result(property = "icon", column = "icon"),
			@Result(property = "abstract", column = "abstr"), 
			@Result(property = "text", column = "text"), })
	@Select("select * from content")
	public List<Content> selectAllContent();

	@Results({ @Result(property = "id", column = "id"), 
		    @Result(property = "price", column = "price"),
			@Result(property = "title", column = "title"), 
			@Result(property = "image", column = "icon"), 
			@Result(property = "isBuy", column = "isBuy"),
			@Result(property = "isSell", column = "isSell")})
	@Select("select a.id,a.price,a.title,a.icon,(case when exists (select 'x' from trx b1 where b1.contentId = a.id and b1.personId = 1) then true else false end) as isBuy, (case when exists (select 'x' from trx b1 where b1.contentId = a.id)  then true else false end) as isSell from content a left join trx b on a.id = b.contentId;")
	public List<ProductList> getProductList();

	@Insert("insert into content set  price=#{price},title = #{title}, icon = #{image}, abstract = #{summary}, text = #{detail}")
	public void insertContent(@Param("price") double price, @Param("title") String title, @Param("image") String image, @Param("summary") String summary, @Param("detail") String detail );
	
	
	
	@Results({ @Result(property = "id", column = "id"), 
	    @Result(property = "price", column = "price"),
		@Result(property = "title", column = "title"), 
		@Result(property = "image", column = "icon"),
		@Result(property = "summary", column = "abstr"), 
		@Result(property = "detail", column = "text"), 
		@Result(property = "buyPrice", column = "buyPrice"),
		@Result(property = "buyNum", column = "buyNum"),
		@Result(property = "saleNum", column = "saleNum"),
		@Result(property = "isBuy", column = "isBuy"),
		@Result(property = "isSell", column = "isSell"),
	})
	@Select("select a.id,a.price,a.title,a.icon, a.abstract, a.text, b.price as buyPrice, (select b1.quantity from trx b1 where b1.personId = 1 and b1.contentId = a.id)as buyNum, (select sum(b2.quantity) from trx b2 where b2.contentId = a.id) as saleNum, (case when exists (select 'x' from trx b3 where b3.contentId = a.id and b3.personId = 1) then true else false end) as isBuy, (case when exists (select 'x' from trx b4 where b4.contentId = a.id ) then true else false end) as isSell from content a left join trx b on a.id = b.contentId where a.id = #{id}")
	public Product getProductById(@Param("id") int id);
	
	@Select("select id, price, title, icon as image, abstract as summary, text as detail from content where price = #{price} and title = #{title} and icon = #{image} and abstract = #{summary} and text = #{detail}")
	public ContentItem getContentByDetails(@Param("price") double price, @Param("title") String title, @Param("image") String image, @Param("summary") String summary, @Param("detail") String detail);
	
	@Results({ @Result(property = "image", column = "icon"),
		@Result(property = "summary", column = "abstract"),
		@Result(property = "detail", column = "text"),
	})
	@Select("select * from content where id = #{id}")
	public ContentItem getContentById(int id);
	
	@Update("update content set price = #{price}, title = #{title}, icon = #{image}, abstract = #{summary}, text = #{detail} where id = #{id}")
	public void updateContentById( @Param("id") int id, @Param("price") double price,  @Param("title") String title,  @Param("image") String image, @Param("summary")  String summary, @Param("detail")  String detail);
	
	@Delete("delete from content where id = #{id}")
	public void deleteContentById(int id);
}
