package com.onway.makeploy.common.dal.dataobject;

import java.io.Serializable;

public class ShopShow implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 3227641641488059560L;
	
	private String shopName;
	public String getShopName() {
		return shopName;
	}
	public void setShopName(String shopName) {
		this.shopName = shopName;
	}
	public String getShopId() {
		return shopId;
	}
	public void setShopId(String shopId) {
		this.shopId = shopId;
	}
	private String shopId;

}
