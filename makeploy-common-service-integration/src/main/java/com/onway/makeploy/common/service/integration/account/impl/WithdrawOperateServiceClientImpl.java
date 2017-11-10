/**
 * onway.com Inc.
 * Copyright (c) 2016-2016 All Rights Reserved.
 */
package com.onway.makeploy.common.service.integration.account.impl;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;

import com.onway.account.common.service.api.WithdrawOperateService;
import com.onway.account.common.service.enums.WithdrawStatusEnum;
import com.onway.account.common.service.model.BankCardInfo;
import com.onway.account.common.service.model.OperatorInfo;
import com.onway.account.common.service.model.WithdrawInfo;
import com.onway.account.common.service.result.WithdrawInfoQueryResult;
import com.onway.account.common.service.result.WithdrawOperateResult;
import com.onway.makeploy.common.service.integration.account.WithdrawOperateServiceClient;
import com.onway.platform.common.base.BaseResult;
import com.onway.platform.common.base.PageQueryResult;
import com.onway.platform.common.base.QueryBase;
import com.onway.platform.common.exception.BaseRuntimeException;

/**
 * 账务中心提现服务门面实现
 * 
 * @author guangdong.li
 * @version $Id: WithdrawOperateServiceClientImpl.java, v 0.1 21 Feb 2016 11:53:23 guangdong.li Exp $
 */
@Component("withdrawOperateServiceClient")
public class WithdrawOperateServiceClientImpl implements WithdrawOperateServiceClient {

    /** logger */
    private static final Logger    logger = Logger
                                              .getLogger(WithdrawOperateServiceClientImpl.class);

    @Resource
    private WithdrawOperateService withdrawOperateService;

    /** 
     * @see com.WithdrawOperateServiceClient.app.front.integration.account.WithdrawOperateServiceFacade#withdrawApply(java.lang.String, com.yylc.account.common.service.model.WithdrawInfo, com.yylc.account.common.service.model.BankCardInfo)
     */
    @Override
    public WithdrawOperateResult withdrawApply(String userId, WithdrawInfo withdraw,
                                               BankCardInfo bankCard) {
        try {
            WithdrawOperateResult result = withdrawOperateService.withdrawApply(userId, withdraw,
                bankCard);

            //定义结果集
            WithdrawOperateResult bReuslt = new WithdrawOperateResult(result.isSuccess());
            bReuslt.setCode(result.getCode());
            bReuslt.setMessage(result.getMessage());
            bReuslt.setWithdrawNo(result.getWithdrawNo());
            return bReuslt;
        } catch (Throwable e) {
            logger.error("提现申请接口调用异常,userId:" + userId + ",withdraw" + withdraw + ",bankCard"
                         + bankCard, e);
            return new WithdrawOperateResult(false);
        }
    }

    /** 
     * @see com.WithdrawOperateServiceClient.app.front.integration.account.WithdrawOperateServiceFacade#withdrawAudit(java.lang.String, com.yylc.account.common.service.enums.WithdrawStatusEnum, com.yylc.account.common.service.model.OperatorInfo)
     */
    @Override
    public BaseResult withdrawAudit(String withdrawNo, WithdrawStatusEnum auditStatus,
                                    OperatorInfo operatorInfo) {
        try {
            BaseResult result = withdrawOperateService.withdrawAudit(withdrawNo, auditStatus,
                operatorInfo);

            BaseResult bReuslt = new BaseResult(result.isSuccess());
            bReuslt.setCode(result.getCode());
            bReuslt.setMessage(result.getMessage());
            return bReuslt;
        } catch (Throwable e) {
            logger.error("提现审核接口调用异常,withdrawNo:" + withdrawNo + ",auditStatus" + auditStatus
                         + ",operatorInfo" + operatorInfo, e);
            return new BaseResult(false);
        }
    }

    /** 
     * @see com.WithdrawOperateServiceClient.app.front.integration.account.WithdrawOperateServiceFacade#transferAccounts(java.lang.String, java.lang.Boolean)
     */
    @Override
    public BaseResult transferAccounts(String withdrawNo, Boolean isSuccess,
                                       OperatorInfo operatorInfo) {
        try {
            BaseResult result = withdrawOperateService.transferAccounts(withdrawNo, isSuccess,
                operatorInfo);

            BaseResult bReuslt = new BaseResult(result.isSuccess());
            bReuslt.setCode(result.getCode());
            bReuslt.setMessage(result.getMessage());
            return bReuslt;
        } catch (Throwable e) {
            logger.error("提现转账接口调用异常,withdrawNo:" + withdrawNo, e);
            return new BaseResult(false);
        }
    }

    /** 
     * @see com.WithdrawOperateServiceClient.app.front.integration.account.WithdrawOperateServiceFacade#queryWithdrawInfo(java.lang.String, com.yylc.account.common.service.base.QueryBase)
     */
    @Override
    public PageQueryResult<WithdrawInfo> queryWithdrawInfo(String userId, QueryBase query) {
        try {
            PageQueryResult<WithdrawInfo> result = withdrawOperateService.queryWithdrawInfo(userId,
                query);

            PageQueryResult<WithdrawInfo> bReuslt = new PageQueryResult<WithdrawInfo>(
                result.isSuccess());
            bReuslt.setCurrentPage(result.getCurrentPage());
            bReuslt.setItemsPerPage(result.getItemsPerPage());
            bReuslt.setResultList(result.getResultList());
            bReuslt.setTotalItems(result.getTotalItems());
            bReuslt.setTotalPages(result.getTotalPages());
            bReuslt.setCode(result.getCode());
            bReuslt.setMessage(result.getMessage());
            return bReuslt;
        } catch (Throwable e) {
            logger.error("提现信息查询接口调用异常,userId:" + userId + ",query" + query, e);
            return new PageQueryResult<WithdrawInfo>(false);
        }
    }

    /** 
     * @see com.WithdrawOperateServiceClient.app.front.integration.account.WithdrawOperateServiceFacade#queryUserWithdrawInfo(java.lang.String)
     */
    @Override
    public WithdrawInfo queryUserWithdrawInfo(String withdrawNo) {
        try {
            WithdrawInfoQueryResult<WithdrawInfo> result = withdrawOperateService
                .queryUserWithdrawInfo(withdrawNo);
            if (result == null || !result.isSuccess() || result.getInfo() == null) {
                logger.warn("提现信息查询异常,withdrawNo:" + withdrawNo);
                throw new BaseRuntimeException("提现信息查询异常");
            }
            return result.getInfo();

        } catch (Throwable e) {
            logger.error("查询提现信息异常,withdrawNo:" + withdrawNo, e);
            throw new BaseRuntimeException("系统异常");
        }
    }

}
