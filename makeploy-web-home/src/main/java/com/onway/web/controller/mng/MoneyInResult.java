package com.onway.web.controller.mng;

import java.util.Date;

import com.onway.common.lang.Money;

public class MoneyInResult {
	
	private String userName;
	
	private String cell;
	
	private Money amount = new Money(0, 0);
	
	private int point;
	
	private Date gmtCreate;

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getCell() {
		return cell;
	}

	public void setCell(String cell) {
		this.cell = cell;
	}

	public Money getAmount() {
		return amount;
	}

	public void setAmount(Money amount) {
		this.amount = amount;
	}

	public int getPoint() {
		return point;
	}

	public void setPoint(int point) {
		this.point = point;
	}

	public Date getGmtCreate() {
		return gmtCreate;
	}

	public void setGmtCreate(Date gmtCreate) {
		this.gmtCreate = gmtCreate;
	}
	
	

}
