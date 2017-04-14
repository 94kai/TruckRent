package com.truck.model;

public class User {
	String password;
	String phoneNumber;
    String leanCloudId;
    String username;
    String province;
    String city;
    String piecearea;
    String idcard;
    String avatarurl;
    boolean isChecked;
    int isMember;
    String introduce;
    String idcard_face_pic;
	public String getLeanCloudId() {
		return leanCloudId;
	}
	public void setLeanCloudId(String leanCloudId) {
		this.leanCloudId = leanCloudId;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getProvince() {
		return province;
	}
	public void setProvince(String province) {
		this.province = province;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public String getPiecearea() {
		return piecearea;
	}
	public void setPiecearea(String piecearea) {
		this.piecearea = piecearea;
	}
	public String getIdcard() {
		return idcard;
	}
	public void setIdcard(String idcard) {
		this.idcard = idcard;
	}
	public String getAvatarurl() {
		return avatarurl;
	}
	public void setAvatarurl(String avatarurl) {
		this.avatarurl = avatarurl;
	}
	public boolean getIsChecked() {
		return isChecked;
	}
	public void setIsChecked(boolean isChecked) {
		this.isChecked = isChecked;
	}
	public int getIsMember() {
		return isMember;
	}
	public void setIsMember(int isMember) {
		this.isMember = isMember;
	}
	public String getIntroduce() {
		return introduce;
	}
	public void setIntroduce(String introduce) {
		this.introduce = introduce;
	}
	public String getIdcard_face_pic() {
		return idcard_face_pic;
	}
	public void setIdcard_face_pic(String idcard_face_pic) {
		this.idcard_face_pic = idcard_face_pic;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getPhoneNumber() {
		return phoneNumber;
	}
	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}
	@Override
	public String toString() {
		return "User [password=" + password + ", phoneNumber=" + phoneNumber + ", leanCloudId=" + leanCloudId
				+ ", username=" + username + ", province=" + province + ", city=" + city + ", piecearea=" + piecearea
				+ ", idcard=" + idcard + ", avatarurl=" + avatarurl + ", isChecked=" + isChecked + ", isMember="
				+ isMember + ", introduce=" + introduce + ", idcard_face_pic=" + idcard_face_pic + "]";
	}
	


}
