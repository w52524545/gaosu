package com.onway.web.controller.result;

import com.onway.common.lang.Money;

import java.util.Date;

public class OrderResult extends JsonResult {
	private static final long serialVersionUID = 741231858441822688L;

    private int id;

    private String userId;

    private String orderId;

    private String payNo;

    private String productNo;

    private int productCount;

    private Money orderPrice = new Money(0, 0);

    private String payStatus;

    private String sendGoods;

    private Date payTime;

    private String payWay;

    private String orderType;

    private String orderStatus;

    private Money luggage = new Money(0, 0);

    private String shopId;

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

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getPayNo() {
        return payNo;
    }

    public void setPayNo(String payNo) {
        this.payNo = payNo;
    }

    public String getProductNo() {
        return productNo;
    }

    public void setProductNo(String productNo) {
        this.productNo = productNo;
    }

    public int getProductCount() {
        return productCount;
    }

    public void setProductCount(int productCount) {
        this.productCount = productCount;
    }

    public Money getOrderPrice() {
        return orderPrice;
    }

    public void setOrderPrice(Money orderPrice) {
        this.orderPrice = orderPrice;
    }

    public String getPayStatus() {
        return payStatus;
    }

    public void setPayStatus(String payStatus) {
        this.payStatus = payStatus;
    }

    public String getSendGoods() {
        return sendGoods;
    }

    public void setSendGoods(String sendGoods) {
        this.sendGoods = sendGoods;
    }

    public Date getPayTime() {
        return payTime;
    }

    public void setPayTime(Date payTime) {
        this.payTime = payTime;
    }

    public String getPayWay() {
        return payWay;
    }

    public void setPayWay(String payWay) {
        this.payWay = payWay;
    }

    public String getOrderType() {
        return orderType;
    }

    public void setOrderType(String orderType) {
        this.orderType = orderType;
    }

    public String getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(String orderStatus) {
        this.orderStatus = orderStatus;
    }

    public Money getLuggage() {
        return luggage;
    }

    public void setLuggage(Money luggage) {
        this.luggage = luggage;
    }

    public String getShopId() {
        return shopId;
    }

    public void setShopId(String shopId) {
        this.shopId = shopId;
    }


	/**
	 * @param bizSucc
	 * @param errCode
	 * @param message
	 */
	public OrderResult(boolean bizSucc, String errCode, String message) {
		super(bizSucc, message, message);
	}


}
