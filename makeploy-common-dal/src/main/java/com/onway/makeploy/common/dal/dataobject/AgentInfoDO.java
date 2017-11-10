package com.onway.makeploy.common.dal.dataobject;

import java.util.Date;

import com.onway.common.lang.Money;

/**
 * @author jianyong.jiang
 * 
 *
 */
public class AgentInfoDO {
	String userId;
	String userName;
	String cell;
	String province;
	String city;
	String district;
	Money  areaIncome;
	Date   checkOut;
	Money  estimatedIncome;
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
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
	public String getDistrict() {
		return district;
	}
	public void setDistrict(String district) {
		this.district = district;
	}
	public Money getAreaIncome() {
		return areaIncome;
	}
	public void setAreaIncome(Money areaIncome) {
		this.areaIncome = areaIncome;
	}
	public Date getCheckOut() {
		return checkOut;
	}
	public void setCheckOut(Date checkOut) {
		this.checkOut = checkOut;
	}
	public Money getEstimatedIncome() {
		return estimatedIncome;
	}
	public void setEstimatedIncome(Money estimatedIncome) {
		this.estimatedIncome = estimatedIncome;
	}

}
