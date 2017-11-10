package com.onway.web.controller.result;

import com.onway.makeploy.common.dal.dataobject.ShopDO;

public class ShopResult extends JsonResult {
	private static final long serialVersionUID = 741231858441822688L;
	private ShopDO shopDO;

	/**
	 * @param bizSucc
	 * @param errCode
	 * @param message
	 */
	public ShopResult(boolean bizSucc, String errCode, String message) {
		super(bizSucc, message, message);
	}

	public ShopDO getShopDO() {
		return shopDO;
	}

	public void setShopDO(ShopDO shopDO) {
		this.shopDO = shopDO;
	}

}
