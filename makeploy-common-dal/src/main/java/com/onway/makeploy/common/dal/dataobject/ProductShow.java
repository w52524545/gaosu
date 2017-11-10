package com.onway.makeploy.common.dal.dataobject;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import com.onway.common.lang.Money;

public class ProductShow extends BaseDO{

	/**
	 * 
	 */
	private static final long serialVersionUID = -136494198041659828L;
	
	private String userId;
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	private String orderPrice;
	private String orderStatus;
	private String orderId;
	public String getOrderId() {
		return orderId;
	}
	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}
	public String getOrderStatus() {
		return orderStatus;
	}
	public void setOrderStatus(String orderStatus) {
		this.orderStatus = orderStatus;
	}
	public String getOrderPrice() {
		return orderPrice;
	}
	public void setOrderPrice(String orderPrice) {
		this.orderPrice = orderPrice;
	}
	public int getProductCount() {
		return ProductCount;
	}
	public void setProductCount(int productCount) {
		ProductCount = productCount;
	}
	public long getLuggage() {
		return luggage;
	}
	public void setLuggage(long luggage) {
		this.luggage = luggage;
	}
	public String getProductName() {
		return productName;
	}
	public void setProductName(String productName) {
		this.productName = productName;
	}
	public long getPrice() {
		return price;
	}
	public void setPrice(long price) {
		this.price = price;
	}
	private int ProductCount;
	private long luggage;
	private String productName;
	private long price;
	private String shopId;
	
	
	public String getShopId() {
		return shopId;
	}
	public void setShopId(String shopId) {
		this.shopId = shopId;
	}
	public String getSendGoods() {
		return sendGoods;
	}
	public void setSendGoods(String sendGoods) {
		this.sendGoods = sendGoods;
	}
	public String getPayStatus() {
		return payStatus;
	}
	public void setPayStatus(String payStatus) {
		this.payStatus = payStatus;
	}
	private String sendGoods;
	private String payStatus;
	private String productImg;
	
	
	public String getProductImg() {
		return productImg;
	}
	public void setProductImg(String productImg) {
		this.productImg = productImg;
	}
	private int offset;
	public int getOffset() {
		return offset;
	}
	public void setOffset(int offset) {
		this.offset = offset;
	}
	public int getLimit() {
		return limit;
	}
	public void setLimit(int limit) {
		this.limit = limit;
	}
	private int limit;
	
	
	
	
	
	private Money money1 = new Money(0,0); //商品单价(元)
	private Money money2 = new Money(0,0); //商品总价(元)
	private Money money3 = new Money(0,0); //运费(元)
	private Money money4 = new Money(0,0); //订单价(元)
	
	public Money getMoney1() {
		return money1;
	}
	public void setMoney1(Money money1) {
		if(money1 == null){
			this.money1 = new Money(0, 0);
		}else {
			this.money1 = money1;
		}
	}
	public Money getMoney2() {
		return money2;
	}
	public void setMoney2(Money money2) {
		if(money2 == null){
			this.money2 = new Money(0, 0);
		}else {
			this.money2 = money2;
		}
	}
	
	public Money getMoney3() {
		return money3;
	}
	public void setMoney3(Money money3) {
		if(money3 == null){
			this.money3 = new Money(0, 0);
		}else {
			this.money3 = money3;
		}
	}
	
	public Money getMoney4() {
		return money4;
	}
	public void setMoney4(Money money4) {
		if(money4 == null){
			this.money4 = new Money(0, 0);
		}else {
			this.money4 = money4;
		}
	}
	
	Map<String,Object> map = new HashMap();
	public Map<String, Object> getMap() {
		return map;
	}
	public void setMap(Map<String, Object> map) {
		this.map = map;
	}
	
	
	private String productNo;
	private Date gmtcreate;
	private Date gmtModified;
	private String cell;
	public String getCell() {
		return cell;
	}
	public void setCell(String cell) {
		this.cell = cell;
	}
	public Date getGmtModified() {
		return gmtModified;
	}
	public void setGmtModified(Date gmtModified) {
		this.gmtModified = gmtModified;
	}
	public Date getGmtcreate() {
		return gmtcreate;
	}
	public void setGmtcreate(Date gmtcreate) {
		this.gmtcreate = gmtcreate;
	}
	public String getProductNo() {
		return productNo;
	}
	public void setProductNo(String productNo) {
		this.productNo = productNo;
	}
	
	
	
	
 

}
