package com.xinxin.meta;

public class User {
	private String username;
	private int usertype;

	public User() {
		username = "";
	}

	public User(Person person) {
		this.username = person.getUserName();
		this.usertype = person.getUserType();
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public int getUsertype() {
		return usertype;
	}

	public void setUsertype(int usertype) {
		this.usertype = usertype;
	}

	public void setUser(Person person) {
		this.username = person.getUserName();
		this.usertype = person.getUserType();
	}

	public boolean hasLogin() {
		if (this.username.length() > 0) {
			return true;
		} else {
			return false;
		}
	}
	public void logout(){
		username = "";
	}
}
