package com.xinxin;

import java.util.List;

import org.springframework.context.ApplicationContext;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class test {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		ApplicationContext context = new ClassPathXmlApplicationContext("application-context.xml");
		MybatisUserDao dao = context.getBean("mybatisUserDao", MybatisUserDao.class);
		List<User> userList = dao.getUserList();
		for(User user:userList){
			//System.out.println(user.getFirstName() + " " + user.getLastName());
		}
		User user1 = dao.getUser("xiaoming");
		System.out.println(user1.getFirstName()+ " " + user1.getLastName());
		((ConfigurableApplicationContext)context).close();
	}

}
