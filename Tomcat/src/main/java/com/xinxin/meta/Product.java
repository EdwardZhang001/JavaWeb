package com.xinxin.meta;

import java.io.IOException;
import java.sql.SQLException;

import com.mysql.jdbc.Blob;
import com.xinxin.meta.Utility;

public class Product {
	private int id;
	private String title;
	private String summary;
	private String detail;
	private String image;
	private double price;
	private double buyPrice;
	private int buyNum;
	private int saleNum;
	private boolean isBuy;
	private boolean isSell;
	
	public Product(){
		
	}
	
	public Product (ProductTemp productTemp) throws SQLException, IOException{
		this.id = productTemp.getId();
		this.title = productTemp.getTitle();
		this.summary = productTemp.getSummary();
		this.detail =Utility.blobToString(productTemp.getDetail());
		this.image = productTemp.getImage();
		this.price = productTemp.getPrice();
		this.buyPrice = productTemp.getBuyPrice();
		this.buyNum = productTemp.getBuyNum();
		this.saleNum = productTemp.getBuyNum();
		this.isBuy = productTemp.getIsBuy();
		this.isSell = productTemp.getIsSell();
		
	}
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getSummary() {
		return summary;
	}
	public void setSummary(String summary) {
		this.summary = summary;
	}
	public String getDetail() {
		return detail;
	}
	public void setDetail(String detail) throws SQLException, IOException {
		this.detail = Utility.blobProcesser(detail);
	}
	public String getImage() {
		return image;
	}
	public void setImage(String image) {
		this.image = image;
	}
	public double getPrice() {
		return price;
	}
	public void setPrice(double price) {
		this.price = price;
	}
	public double getBuyPrice() {
		return buyPrice;
	}
	public void setBuyPrice(double buyPrice) {
		this.buyPrice = buyPrice;
	}
	public int getBuyNum() {
		return buyNum;
	}
	public void setBuyNum(int buyNum) {
		this.buyNum = buyNum;
	}
	public int getSaleNum() {
		return saleNum;
	}
	public void setSaleNum(int saleNum) {
		this.saleNum = saleNum;
	}
	public boolean getIsBuy() {
		return isBuy;
	}
	public void setIsBuy(boolean isBuy) {
		this.isBuy = isBuy;
	}
	public boolean getIsSell() {
		return isSell;
	}
	public void setIsSell(boolean isSell) {
		this.isSell = isSell;
	}
}
