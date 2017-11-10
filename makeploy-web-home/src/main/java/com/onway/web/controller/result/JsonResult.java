package com.onway.web.controller.result;

/**
 * onway.com Inc.
 * Copyright (c) 2016-2016 All Rights Reserved.
 */

import com.onway.common.lang.DateUtils;
import com.onway.makeploy.common.dal.dataobject.ProductDO;
import com.onway.makeploy.common.dal.dataobject.ShopDO;

import java.io.Serializable;
import java.util.Date;

/**
 * json����Ĭ�Ͻ��
 * 
 * @author guangdong.li
 * @version $Id: JsonResult.java, v 0.1 2013-10-30 ����5:38:55  Exp $
 */
public class JsonResult implements Serializable {

    /**
     * serialVersionUID
     */
    private static final long serialVersionUID = 1475348231900998033L;

    private String            time             = DateUtils.format(new Date(), DateUtils.newFormat);

    // ҵ��������
    private boolean           bizSucc          = true;

    // �Ƿ�ǿ�����ߣ��˺�������һ�豸��¼
    //private boolean           isForceLogOut    = false;

    private String            errMsg           = "";

    // ������
    private String            errCode          = "";
    
    //ĳ����Ϣ
    private String            information      = "";
    
    private ShopDO            shopDO;
    
    private ProductDO         productDO;

    public JsonResult(boolean bizSucc) {
        this.bizSucc = bizSucc;
    }

    public JsonResult(boolean bizSucc, String errCode, String message) {
        this.bizSucc = bizSucc;
        this.errMsg = message;
        this.errCode = errCode;
    }

    public void markResult(boolean bizSucc, String errCode, String message) {
        this.bizSucc = bizSucc;
        this.errMsg = message;
        this.errCode = errCode;
    }

    public boolean isBizSucc() {
        return bizSucc;
    }

    public void setBizSucc(boolean bizSucc) {
        this.bizSucc = bizSucc;
    }

    public String getErrMsg() {
        return errMsg;
    }

    public void setErrMsg(String errMsg) {
        this.errMsg = errMsg;
    }

    public String getErrCode() {
        return errCode;
    }

    public void setErrCode(String errCode) {
        this.errCode = errCode;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

	public String getInformation() {
		return information;
	}

	public void setInformation(String information) {
		this.information = information;
	}

	public ShopDO getShopDO() {
		return shopDO;
	}

	public void setShopDO(ShopDO shopDO) {
		this.shopDO = shopDO;
	}

	public ProductDO getProductDO() {
		return productDO;
	}

	public void setProductDO(ProductDO productDO) {
		this.productDO = productDO;
	}

    
}
