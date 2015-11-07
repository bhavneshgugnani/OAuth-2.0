package org.bhavnesh.google.vo;

import org.bhavnesh.google.customannotations.UserInfoType;

public class UserVO {
	public enum GENDER {
		MALE, FEMALE
	}

	@UserInfoType(type = "private")
	private String email = null;
	
	@UserInfoType(type = "hidden")
	private String password = null;

	@UserInfoType(type = "public")
	private String firstName = null;

	@UserInfoType(type = "public")
	private String lastName = null;

	@UserInfoType(type = "public")
	private int age = 0;

	@UserInfoType(type = "public")
	private GENDER gender = null;

	@UserInfoType(type = "public")
	private String address = null;

	@UserInfoType(type = "private")
	private GENDER phone = null;

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}

	public GENDER getGender() {
		return gender;
	}

	public void setGender(GENDER gender) {
		this.gender = gender;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public GENDER getPhone() {
		return phone;
	}

	public void setPhone(GENDER phone) {
		this.phone = phone;
	}
}
