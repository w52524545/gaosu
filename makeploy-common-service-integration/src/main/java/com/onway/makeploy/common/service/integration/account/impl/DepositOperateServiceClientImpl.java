package com.onway.makeploy.common.service.integration.account.impl;

/**
 * onway.com Inc.
 * Copyright (c) 2013-2015 All Rights Reserved.
 */

import java.text.MessageFormat;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSON;
import com.onway.account.common.service.api.DepositOperateService;
import com.onway.account.common.service.model.DepositRequest;
import com.onway.makeploy.common.service.enums.ErrorCodeEnum;
import com.onway.makeploy.common.service.exception.ErrorException;
import com.onway.makeploy.common.service.integration.account.DepositOperateServiceClient;
import com.onway.platform.common.base.BaseResult;
import com.onway.platform.common.utils.LogUtil;

/**
 * 
 * @author guangdong.li
 * @version $Id: DepositOperateServiceFacadeImpl.java, v 0.1 2015��8��26�� ����3:49:31 guangdong.li Exp $
 */
@Component("depositOperateServiceClient")
public class DepositOperateServiceClientImpl implements DepositOperateServiceClient {

    /** logger */
    private static final Logger   logger = Logger.getLogger(DepositOperateServiceClientImpl.class);

    @Resource
    private DepositOperateService depositOperateService;

    /** 
     * @see com.onway.tradecore.common.service.integration.account.DepositOperateServiceClient#deposit(com.onway.account.common.service.model.DepositRequest)
     */
    @Override
    public BaseResult deposit(DepositRequest depositRequest) {

        try {
            return depositOperateService.deposit(depositRequest);
        } catch (Throwable e) {
            LogUtil.error(logger, "����ֵ�쳣", e);
            throw new ErrorException( ErrorCodeEnum.INTERFACE_SYSTEM_ERROR);
        }

    }

    @Override
    public void depositBonus(DepositRequest depositRequest) {

        BaseResult res = null;
        try {

            res = depositOperateService.depositBonus(depositRequest);

        } catch (Throwable e) {
            LogUtil.error(logger, "�����ֵ�쳣", e);
            throw new ErrorException( ErrorCodeEnum.INTERFACE_SYSTEM_ERROR);
        }

        if (res == null || !res.isSuccess()) {
            LogUtil.error(
                logger,
                MessageFormat.format("�����ֵʧ�ܣ�depositRequest:{0},msg:{1}",
                    new Object[] { JSON.toJSONString(depositRequest), res.getMessage() }));
            throw new ErrorException( ErrorCodeEnum.INTERFACE_SYSTEM_ERROR,"�����ֵʧ��");
        }

        LogUtil.info(
            logger,
            MessageFormat.format("����{0}Ԫ���ųɹ���depositRequest:{1}",
                new Object[] { depositRequest.getAmount(), JSON.toJSONString(depositRequest) }));
    }
}
