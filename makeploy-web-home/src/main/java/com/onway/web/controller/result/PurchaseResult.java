/**
 * onway.com Inc.
 * Copyright (c) 2016-2016 All Rights Reserved.
 */
package com.onway.web.controller.result;

import com.onway.common.lang.Money;

/**
 * 申购结果集
 *
 * @author guangdong.li
 * @version $Id: EmpLoginResult.java, v 0.1 2016年4月11日 下午10:28:00 guangdong.li Exp $
 */
public class PurchaseResult extends JsonResult {



    /**
     * @param isSucc
     * @param succ
     * @param message
     */
    public PurchaseResult(boolean bizSucc, String errCode, String message) {
        super(bizSucc, errCode, message);
    }

    /**  */
    private static final long serialVersionUID = 1L;

    private String            paySign;

    private String            wepackage;

    private String            appId;

    private String            timeStamp;

    private String            nonceStr;

    private String            signType;

    private String            authUrl;

    private String            prepayId;

    private Money             profit;

    /**
     * Getter method for property <tt>paySign</tt>.
     *
     * @return property value of paySign
     */
    public String getPaySign() {
        return paySign;
    }

    /**
     * Setter method for property <tt>paySign</tt>.
     *
     * @param paySign value to be assigned to property paySign
     */
    public void setPaySign(String paySign) {
        this.paySign = paySign;
    }

    /**
     * Getter method for property <tt>wepackage</tt>.
     *
     * @return property value of wepackage
     */
    public String getWepackage() {
        return wepackage;
    }

    /**
     * Setter method for property <tt>wepackage</tt>.
     *
     * @param wepackage value to be assigned to property wepackage
     */
    public void setWepackage(String wepackage) {
        this.wepackage = wepackage;
    }

    /**
     * Getter method for property <tt>appId</tt>.
     *
     * @return property value of appId
     */
    public String getAppId() {
        return appId;
    }

    /**
     * Setter method for property <tt>appId</tt>.
     *
     * @param appId value to be assigned to property appId
     */
    public void setAppId(String appId) {
        this.appId = appId;
    }

    /**
     * Getter method for property <tt>timeStamp</tt>.
     *
     * @return property value of timeStamp
     */
    public String getTimeStamp() {
        return timeStamp;
    }

    /**
     * Setter method for property <tt>timeStamp</tt>.
     *
     * @param timeStamp value to be assigned to property timeStamp
     */
    public void setTimeStamp(String timeStamp) {
        this.timeStamp = timeStamp;
    }

    /**
     * Getter method for property <tt>nonceStr</tt>.
     *
     * @return property value of nonceStr
     */
    public String getNonceStr() {
        return nonceStr;
    }

    /**
     * Setter method for property <tt>nonceStr</tt>.
     *
     * @param nonceStr value to be assigned to property nonceStr
     */
    public void setNonceStr(String nonceStr) {
        this.nonceStr = nonceStr;
    }

    /**
     * Getter method for property <tt>signType</tt>.
     *
     * @return property value of signType
     */
    public String getSignType() {
        return signType;
    }

    /**
     * Setter method for property <tt>signType</tt>.
     *
     * @param signType value to be assigned to property signType
     */
    public void setSignType(String signType) {
        this.signType = signType;
    }

    /**
     * Getter method for property <tt>authUrl</tt>.
     *
     * @return property value of authUrl
     */
    public String getAuthUrl() {
        return authUrl;
    }

    /**
     * Setter method for property <tt>authUrl</tt>.
     *
     * @param authUrl value to be assigned to property authUrl
     */
    public void setAuthUrl(String authUrl) {
        this.authUrl = authUrl;
    }

    /**
     * Getter method for property <tt>prepayId</tt>.
     *
     * @return property value of prepayId
     */
    public String getPrepayId() {
        return prepayId;
    }

    /**
     * Setter method for property <tt>prepayId</tt>.
     *
     * @param prepayId value to be assigned to property prepayId
     */
    public void setPrepayId(String prepayId) {
        this.prepayId = prepayId;
    }

    public Money getProfit() {
        return profit;
    }

    public void setProfit(Money profit) {
        this.profit = profit;
    }

}
