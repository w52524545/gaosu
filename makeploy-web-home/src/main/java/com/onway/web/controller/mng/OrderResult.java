package com.onway.web.controller.mng;

import java.util.List;

import com.onway.makeploy.common.dal.dataobject.OrderDO;
import com.onway.makeploy.common.dal.dataobject.ProductDO;
import com.onway.makeploy.common.dal.dataobject.ShopDO;

public class OrderResult {

	
	private ShopDO shopDO;
	private OrderDO orderDO;
	private ProductDO productDO;
	
	private List<OrderResult> resultList;
	
	private String memo;
	
	public ShopDO getShopDO() {
		return shopDO;
	}
	public void setShopDO(ShopDO shopDO) {
		this.shopDO = shopDO;
	}
	public OrderDO getOrderDO() {
		return orderDO;
	}
	public void setOrderDO(OrderDO orderDO) {
		this.orderDO = orderDO;
	}
	public ProductDO getProductDO() {
		return productDO;
	}
	public void setProductDO(ProductDO productDO) {
		this.productDO = productDO;
	}
	public List<OrderResult> getResultList() {
		return resultList;
	}
	public void setResultList(List<OrderResult> resultList) {
		this.resultList = resultList;
	}
	public String getMemo() {
		return memo;
	}
	public void setMemo(String memo) {
		this.memo = memo;
	}
	
}
