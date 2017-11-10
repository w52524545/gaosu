/**
 * onway.com Inc.
 * Copyright (c) 2016-2016 All Rights Reserved.
 */
package com.onway.makeploy.common.service.integration.account.impl;

import java.net.ConnectException;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;

import com.onway.account.common.service.api.PayOperateService;
import com.onway.account.common.service.enums.AccountCoreResultCodeEnum;
import com.onway.account.common.service.model.PayRequest;
import com.onway.makeploy.common.service.integration.account.PayOperateServiceClient;
import com.onway.platform.common.base.BaseResult;

/**
 * 账务中心余额支付服务门面实现
 * 
 * @author guangdong.li
 * @version $Id: PayOperateServiceClientImpl.java, v 0.1 21 Feb 2016 11:48:13 guangdong.li Exp $
 */
public class PayOperateServiceClientImpl implements PayOperateServiceClient {

    /** logger */
    private static final Logger logger = Logger.getLogger(PayOperateServiceClientImpl.class);

    /** 账务中心支付操作服务 */
    @Resource
    private PayOperateService   payOperateService;

    /** 
     * @see com.yylc.account.common.service.api.PayOperateService#payApply(com.yylc.account.common.service.model.PayRequest)
     */
    @Override
    public BaseResult payApply(PayRequest payRequest) {
        try {
            BaseResult result = payOperateService.payApply(payRequest);
            return result;
        } catch (Throwable e) {
            logger.error("申请支付接口调用异常,request:" + payRequest, e);
            BaseResult baseResult = new BaseResult(false);
            if (e instanceof ConnectException) {
                baseResult.setCode(AccountCoreResultCodeEnum.CONNECT_TIME_OUT.getCode());
            }
            baseResult.setMessage(AccountCoreResultCodeEnum.CONNECT_TIME_OUT.getMessage());
            return baseResult;
        }
    }

    /** 
     * @see com.yylc.account.common.service.api.PayOperateService#finishPay(com.yylc.account.common.service.model.PayRequest, boolean)
     */
    @Override
    public BaseResult finishPay(PayRequest payRequest, boolean isSuccess) {
        try {
            BaseResult result = payOperateService.finishPay(payRequest, isSuccess);

            return result;
        } catch (Throwable e) {
            BaseResult baseResult = new BaseResult(false);
            if (e instanceof ConnectException) {
                baseResult.setCode(AccountCoreResultCodeEnum.CONNECT_TIME_OUT.getCode());

            }
            baseResult.setMessage(AccountCoreResultCodeEnum.CONNECT_TIME_OUT.getMessage());
            return baseResult;
        }
    }

}
