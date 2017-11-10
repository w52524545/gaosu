/**
 * yingyinglicai.com Inc.
 * Copyright (c) 2013-2014 All Rights Reserved.
 */
 package com.onway.web.controller.mng;

import com.onway.makeploy.common.dal.dataobject.ProductPromotionDO;
import com.onway.makeploy.common.dal.dataobject.ShopDO;


public class ProductPromotionResult {

	private ProductPromotionDO productPromotionDO;
	
	private ShopDO shopDO;

	public ProductPromotionDO getProductPromotionDO() {
		return productPromotionDO;
	}

	public void setProductPromotionDO(ProductPromotionDO productPromotionDO) {
		this.productPromotionDO = productPromotionDO;
	}

	public ShopDO getShopDO() {
		return shopDO;
	}

	public void setShopDO(ShopDO shopDO) {
		this.shopDO = shopDO;
	}
	
	
}
