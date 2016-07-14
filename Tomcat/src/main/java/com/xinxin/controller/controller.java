package com.xinxin.controller;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.xinxin.dao.ContentDao;
import com.xinxin.dao.PersonDao;
import com.xinxin.dao.TrxDao;
import com.xinxin.meta.BuyList;
import com.xinxin.meta.Content;
import com.xinxin.meta.ContentItem;
import com.xinxin.meta.MessageOb;
import com.xinxin.meta.Person;
import com.xinxin.meta.Product;
import com.xinxin.meta.ProductList;
import com.xinxin.meta.ProductTemp;
import com.xinxin.meta.PurchaseItem;
import com.xinxin.meta.RequestBuyParam;
import com.xinxin.meta.User;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

@Controller
public class controller {
	public static String IMAGE_URL = "http:\\\\localhost:8080\\JavaWeb\\image\\";

	ApplicationContext context = new ClassPathXmlApplicationContext("spring-mybatis.xml");
	User user = new User();

	@RequestMapping("/login")
	public String login(ModelMap map, HttpServletRequest request, HttpServletResponse response) {
		this.processUser(request, map);
		return "login";
	}

	@RequestMapping("/logout")
	public String logout(HttpServletRequest request, HttpServletResponse response) {
		this.clearLoginCookie(request, response);
		user.logout();
		return "login";
	}

	@RequestMapping("/api/login")
	@ResponseBody
	public HashMap<Object, Object> loginHandler(ModelMap map, HttpServletRequest request, HttpServletResponse response,
			@RequestParam("userName") String userName, @RequestParam("password") String password) throws IOException {
		PersonDao personDao = context.getBean("personDao", PersonDao.class);
		HashMap<Object, Object> result = new HashMap<Object, Object>();
		if (personDao.isUserExist(userName, password) == 1) {
			Person person = personDao.getUser(userName, password);
			user.setUser(person);
			Cookie cookie = new Cookie("username", user.getUsername());
			cookie.setPath("/");
			response.addCookie(cookie);
			result.put("code", 200);
			result.put("message", "登陆成功");
			result.put("result", true);
		} else {
			result.put("code", 500);
			result.put("message", "用户不存在或密码错误");
			result.put("result", false);
		}
		return result;
	}

	@RequestMapping("/api/delete")
	@ResponseBody
	public HashMap<Object, Object> delete(ModelMap map, HttpServletRequest request, HttpServletResponse response,
			@RequestParam("id") int id) {
		HashMap<Object, Object> result = new HashMap<Object, Object>();
		try {
			context.getBean("contentDao", ContentDao.class).deleteContentById(id);
			result.put("code", 200);
			result.put("message", "删除成功");
			result.put("result", true);
		} catch (Exception e) {
			result.put("code", 500);
			result.put("message", "删除失败");
			result.put("result", false);
			System.out.println(e.getMessage());
		}
		return result;
	}

	@RequestMapping("/api/buy")
	@ResponseBody
	public HashMap<Object, Object> purchase(ModelMap map, HttpServletRequest request, HttpServletResponse response, @RequestBody List<RequestBuyParam> listBuyParam) {
		HashMap<Object, Object> result = new HashMap<Object, Object>();
		try {
			System.out.println(listBuyParam.size());
			if(listBuyParam.size()> 0){
				TrxDao trxDao = context.getBean("trxDao", TrxDao.class);
				for(RequestBuyParam buyParam:listBuyParam){
					if(buyParam.getNumber() == 0){
						throw new Exception("Quantity cannot be zero");
					}else{
						trxDao.insertTrx(buyParam.getId(), 1,  System.currentTimeMillis(), buyParam.getNumber());
					}
					
				}
			}else{
				throw new Exception("Cookie message is blank:" );
			}
			result.put("code", 200);
			result.put("message", "购买成功");
			result.put("result", true);
		} catch (Exception e) {
			result.put("code", 500);
			result.put("message", "购买失败");
			result.put("result", false);
			System.out.println(e.getMessage());
		}

		return result;
	}

	@RequestMapping("/")
	public String index(ModelMap map, HttpServletRequest request, HttpServletResponse response) {
		this.processUser(request, map);
		ContentDao contentDao = context.getBean("contentDao", ContentDao.class);
		List<ProductList> listProduct = contentDao.getProductList();
		map.addAttribute("productList", listProduct);
		return "index";
	}

	@RequestMapping("/show")
	public String show(@RequestParam("id") int id, ModelMap map, HttpServletRequest request,
			HttpServletResponse response) throws SQLException, IOException {
		this.processUser(request, map);
		Product product = context.getBean("contentDao", ContentDao.class).getProductById(id);
		map.addAttribute("product", product);
		return "show";
	}

	@RequestMapping("/index")
	public String index1(ModelMap map, HttpServletRequest request, HttpServletResponse response) {

		return "index";
	}

	@RequestMapping("/account")
	public String account(ModelMap map, HttpServletRequest request, HttpServletResponse response) {
		this.processUser(request, map);
		List<BuyList> listBuy = context.getBean("trxDao", TrxDao.class).getAccount(user.getUsername());
		map.addAttribute("buyList", listBuy);
		return "account";
	}

	@RequestMapping("/settleAccount")
	public String settleAccount(ModelMap map, HttpServletRequest request, HttpServletResponse response) {
		this.processUser(request, map);
		return "settleAccount";
	}

	@RequestMapping("/public")
	public String public1(ModelMap map, HttpServletRequest request, HttpServletResponse response) {
		this.processUser(request, map);

		return "public";
	}

	@RequestMapping("/api/upload")
	@ResponseBody
	public HashMap<Object, Object> upload(@RequestParam("file") MultipartFile file, HttpServletRequest request,
			HttpServletResponse response) {
		HashMap<Object, Object> result = new HashMap<Object, Object>();
		try {
			String imagePath = request.getServletContext().getRealPath("../image");
			String fileName = file.getOriginalFilename();
			String imageUrl = IMAGE_URL + fileName;
			imagePath += "\\" + fileName;
			File target = new File(imagePath);
			if (!target.exists()) {
				file.transferTo(target);
			}
			result.put("code", 200);
			result.put("message", "上传成功");
			result.put("result", imageUrl);
		} catch (Exception e) {
			result.put("code", 500);
			result.put("message", "上传失败");
			System.out.println(e.getMessage());
		}
		return result;
	}

	@RequestMapping("/publicSubmit")
	public String publicSubmit(ModelMap map, HttpServletRequest request, @RequestParam("title") String title,
			@RequestParam("image") String image, @RequestParam("detail") String detail,
			@RequestParam("price") double price, @RequestParam("summary") String summary) {
		this.processUser(request, map);
		try {
			ContentDao contentDao = context.getBean("contentDao", ContentDao.class);
			contentDao.insertContent(price, title, image, summary, detail);
			ContentItem contentItem = contentDao.getContentByDetails(price, title, image, summary, detail);
			map.addAttribute("product", contentItem);
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}

		return "publicSubmit";
	}

	@RequestMapping("/edit")
	public String edit(ModelMap map, HttpServletRequest request, @RequestParam("id") int id) {
		this.processUser(request, map);
		ContentItem contentItem = context.getBean("contentDao", ContentDao.class).getContentById(id);
		map.addAttribute("product", contentItem);
		return "edit";
	}

	@RequestMapping("/editSubmit")
	public String editSubmit(ModelMap map, HttpServletRequest request, @RequestParam("id") int id,
			@RequestParam("title") String title, @RequestParam("image") String image,
			@RequestParam("detail") String detail, @RequestParam("price") double price,
			@RequestParam("summary") String summary) {
		this.processUser(request, map);
		try {
			ContentDao contentDao = context.getBean("contentDao", ContentDao.class);
			contentDao.updateContentById(id, price, title, image, summary, detail);
			ContentItem contentItem = contentDao.getContentById(id);
			map.addAttribute("product", contentItem);
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		this.processUser(request, map);
		return "editSubmit";
	}

	public String getLoginUser(HttpServletRequest request) {
		String loginUserName = new String();
		Cookie[] cookies = request.getCookies();
		if (cookies != null) {
			for (Cookie cookie : cookies) {
				if (cookie.getName().equals("username")) {
					loginUserName = cookie.getValue();
					break;
				}
			}
		}
		return loginUserName;
	}

	public void clearLoginCookie(HttpServletRequest request, HttpServletResponse response) {
		Cookie[] cookies = request.getCookies();
		if (cookies != null) {
			for (Cookie cookie : cookies) {
				if (cookie.getName().equals("username") || cookie.getName().equals("usertype")) {
					cookie.setMaxAge(0);
					response.addCookie(cookie);
				}
			}
		}
	}

	public void processUser(HttpServletRequest request, ModelMap map) {
		if (user.hasLogin()) {
			map.addAttribute("user", user);
		}
	}
}
