package com.onway.makeploy.common.dal.dataobject;

import java.io.Serializable;

public class Clothing implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 9166909311442064579L;
	
	
	private String color;
	public String getColor() {
		return color;
	}
	public void setColor(String color) {
		this.color = color;
	}
	public String getDimensions() {
		return dimensions;
	}
	public void setDimensions(String dimensions) {
		this.dimensions = dimensions;
	}
	private String dimensions;
	private String productNo;
	public String getProductNo() {
		return productNo;
	}
	public void setProductNo(String productNo) {
		this.productNo = productNo;
	}
}
