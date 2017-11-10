package com.onway.web.controller.result;

import java.util.Date;

import com.onway.common.lang.Money;

public class DiscountResult extends JsonResult{

	public DiscountResult(boolean bizSucc, String errCode, String message) {
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
	 * This property corresponds to db column <tt>DISCOUNT_NO</tt>.
	 */
	private String discountNo;

	/**
	 * This property corresponds to db column <tt>STATUS</tt>.
	 */
	private String status;

	/**
	 * This property corresponds to db column <tt>END_DATE</tt>.
	 */
	private Date endDate;

	/**
	 * This property corresponds to db column <tt>ACCOUT</tt>.
	 */
	private Money accout = new Money(0, 0);

	/**
	 * This property corresponds to db column <tt>LIMIT_ACCOUT</tt>.
	 */
	private Money limitAccout = new Money(0, 0);

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
	
	//日期比较
	private int timeStatus;
	

	public int getTimeStatus() {
		return timeStatus;
	}

	public void setTimeStatus(int timeStatus) {
		this.timeStatus = timeStatus;
	}

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

	public String getDiscountNo() {
		return discountNo;
	}

	public void setDiscountNo(String discountNo) {
		this.discountNo = discountNo;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public Money getAccout() {
		return accout;
	}

	public void setAccout(Money accout) {
		this.accout = accout;
	}

	public Money getLimitAccout() {
		return limitAccout;
	}

	public void setLimitAccout(Money limitAccout) {
		this.limitAccout = limitAccout;
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
}
