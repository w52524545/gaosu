package com.onway.web.controller.result;

import java.util.Date;

import com.onway.common.lang.Money;

public class CollectionResult extends JsonResult{

	public CollectionResult(boolean bizSucc, String errCode, String message) {
		super(bizSucc, errCode, message);
	}
	
	private int id;

	
	private String userId;

	
	private String productNo;

	
	private String shopId;

	
	private String type;

	
	private String status;

	
	private String creater;

	
	private Date gmtCreate;

	
	private String modifier;

	
	private Date gmtModified;

	
	private String memo;

	//商品名称
	private String productName;
	
	//商品价格
	private Money price = new Money(0, 0);
	
	//店铺名称
	private String shopName;
	
	//店铺宝贝数量
	private int goodsCount;
	
	//商品图片
	private String productUrl;

	//店铺图片
	private String shopUrl;

	
	
	public String getProductUrl() {
		return productUrl;
	}


	public void setProductUrl(String productUrl) {
		this.productUrl = productUrl;
	}


	public String getShopUrl() {
		return shopUrl;
	}


	public void setShopUrl(String shopUrl) {
		this.shopUrl = shopUrl;
	}


	public Money getPrice() {
		return price;
	}


	public void setPrice(Money price) {
		this.price = price;
	}


	public int getGoodsCount() {
		return goodsCount;
	}


	public void setGoodsCount(int goodsCount) {
		this.goodsCount = goodsCount;
	}


	public String getProductName() {
		return productName;
	}


	public void setProductName(String productName) {
		this.productName = productName;
	}


	public String getShopName() {
		return shopName;
	}


	public void setShopName(String shopName) {
		this.shopName = shopName;
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


	public String getProductNo() {
		return productNo;
	}


	public void setProductNo(String productNo) {
		this.productNo = productNo;
	}


	public String getShopId() {
		return shopId;
	}


	public void setShopId(String shopId) {
		this.shopId = shopId;
	}


	public String getType() {
		return type;
	}


	public void setType(String type) {
		this.type = type;
	}


	public String getStatus() {
		return status;
	}


	public void setStatus(String status) {
		this.status = status;
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
