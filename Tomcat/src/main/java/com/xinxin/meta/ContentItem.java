package com.xinxin.meta;

public class ContentItem {
private int id;
private String title;
private String summary;
private String detail;
private String image;
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
public void setDetail(String detail) {
	this.detail = detail;
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
private double price;
}
