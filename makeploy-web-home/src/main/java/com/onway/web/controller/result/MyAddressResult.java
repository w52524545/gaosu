package com.onway.web.controller.result;

import java.util.Date;

public class MyAddressResult extends JsonResult{

	public MyAddressResult(boolean bizSucc, String errCode, String message) {
		super(bizSucc, errCode, message);
		// TODO Auto-generated constructor stub
	}

	/**
	 * This property corresponds to db column <tt>ID</tt>.
	 */
	private int id;

	/**
	 * This property corresponds to db column <tt>USER_ID</tt>.
	 */
	private String userId;

	/**
	 * This property corresponds to db column <tt>ADDRESS_NO</tt>.
	 */
	private String addressNo;

	/**
	 * This property corresponds to db column <tt>DELIVERY_NAME</tt>.
	 */
	private String deliveryName;

	/**
	 * This property corresponds to db column <tt>CELL</tt>.
	 */
	private String cell;

	/**
	 * This property corresponds to db column <tt>DISTRICT</tt>.
	 */
	private String district;

	/**
	 * This property corresponds to db column <tt>CITY</tt>.
	 */
	private String city;

	/**
	 * This property corresponds to db column <tt>PROVIENCE</tt>.
	 */
	private String provience;

	/**
	 * This property corresponds to db column <tt>ADDR</tt>.
	 */
	private String addr;
	
	//œÍœ∏µÿ÷∑£®provience+city+district+addr£©
	private String detialAddr;
	

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getAddressNo() {
		return addressNo;
	}

	public void setAddressNo(String addressNo) {
		this.addressNo = addressNo;
	}

	public String getDeliveryName() {
		return deliveryName;
	}

	public void setDeliveryName(String deliveryName) {
		this.deliveryName = deliveryName;
	}

	public String getCell() {
		return cell;
	}

	public void setCell(String cell) {
		this.cell = cell;
	}

	public String getDistrict() {
		return district;
	}

	public void setDistrict(String district) {
		this.district = district;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getProvience() {
		return provience;
	}

	public void setProvience(String provience) {
		this.provience = provience;
	}

	public String getAddr() {
		return addr;
	}

	public void setAddr(String addr) {
		this.addr = addr;
	}

	public String getDetialAddr() {
		return detialAddr;
	}

	public void setDetialAddr(String detialAddr) {
		this.detialAddr = detialAddr;
	}

	public String getFlg() {
		return flg;
	}

	public void setFlg(String flg) {
		this.flg = flg;
	}

	public String getDelFlg() {
		return delFlg;
	}

	public void setDelFlg(String delFlg) {
		this.delFlg = delFlg;
	}

	public String getCreater() {
		return creater;
	}

	public void setCreater(String creater) {
		this.creater = creater;
	}

	public Date getGmtCreate() {
		return gmtCreate;
	}

	public void setGmtCreate(Date gmtCreate) {
		this.gmtCreate = gmtCreate;
	}

	public String getModifier() {
		return modifier;
	}

	public void setModifier(String modifier) {
		this.modifier = modifier;
	}

	public Date getGmtModified() {
		return gmtModified;
	}

	public void setGmtModified(Date gmtModified) {
		this.gmtModified = gmtModified;
	}

	public String getMemo() {
		return memo;
	}

	public void setMemo(String memo) {
		this.memo = memo;
	}

	/**
	 * This property corresponds to db column <tt>FLG</tt>.
	 */
	private String flg;

	/**
	 * This property corresponds to db column <tt>DEL_FLG</tt>.
	 */
	private String delFlg;

	/**
	 * This property corresponds to db column <tt>CREATER</tt>.
	 */
	private String creater;

	/**
	 * This property corresponds to db column <tt>GMT_CREATE</tt>.
	 */
	private Date gmtCreate;

	/**
	 * This property corresponds to db column <tt>MODIFIER</tt>.
	 */
	private String modifier;

	/**
	 * This property corresponds to db column <tt>GMT_MODIFIED</tt>.
	 */
	private Date gmtModified;

	/**
	 * This property corresponds to db column <tt>MEMO</tt>.
	 */
	private String memo;
}
