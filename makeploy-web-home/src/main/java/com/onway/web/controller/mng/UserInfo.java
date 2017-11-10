/**
 * onway.com Inc.
 * Copyright (c) 2016-2016 All Rights Reserved.
 */
package com.onway.web.controller.mng;

import com.onway.makeploy.common.dal.dataobject.PartnerRankDO;
import com.onway.makeploy.common.dal.dataobject.UserDO;

public class UserInfo {

	private UserDO userDO;
	
	private double pointCount;
	
	private int partnerNum;
	
	private int orderNum;
	
	private String orderId;
	
	private int outNum;
	
	private PartnerRankDO partnerRankDO;

	public UserDO getUserDO() {
		return userDO;
	}

	public void setUserDO(UserDO userDO) {
		this.userDO = userDO;
	}

	public double getPointCount() {
		return pointCount;
	}

	public void setPointCount(double pointCount) {
		this.pointCount = pointCount;
	}

	public int getPartnerNum() {
		return partnerNum;
	}

	public void setPartnerNum(int partnerNum) {
		this.partnerNum = partnerNum;
	}

	public int getOrderNum() {
		return orderNum;
	}

	public void setOrderNum(int orderNum) {
		this.orderNum = orderNum;
	}

	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	public int getOutNum() {
		return outNum;
	}

	public void setOutNum(int outNum) {
		this.outNum = outNum;
	}

	public PartnerRankDO getPartnerRankDO() {
		return partnerRankDO;
	}

	public void setPartnerRankDO(PartnerRankDO partnerRankDO) {
		this.partnerRankDO = partnerRankDO;
	}
	
	
}
