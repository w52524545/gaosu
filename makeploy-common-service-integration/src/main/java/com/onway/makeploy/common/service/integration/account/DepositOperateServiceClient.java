package com.onway.makeploy.common.service.integration.account;

/**
 * onway.com Inc.
 * Copyright (c) 2013-2015 All Rights Reserved.
 */

import com.onway.account.common.service.model.DepositRequest;
import com.onway.platform.common.base.BaseResult;
import com.onway.platform.common.log.digest.DigestLogAnnotated;
import com.onway.platform.common.log.digest.ServiceTypeEnum;

/**
 * 
 * @author guangdong.li
 * @version $Id: DepositOperateServiceFacade.java, v 0.1 2015年8月26日 下午3:49:16 guangdong.li Exp $
 */
public interface DepositOperateServiceClient {

    @DigestLogAnnotated(serviceType = ServiceTypeEnum.SAL, bizKey = "余额充值")
    BaseResult deposit(DepositRequest depositRequest);

    @DigestLogAnnotated(serviceType = ServiceTypeEnum.SAL, bizKey = "红包充值")
    void depositBonus(DepositRequest depositRequest);

}
