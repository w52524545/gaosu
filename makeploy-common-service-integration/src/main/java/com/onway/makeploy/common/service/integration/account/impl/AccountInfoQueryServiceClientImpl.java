/**
 * onway.com Inc.
 * Copyright (c) 2016-2016 All Rights Reserved.
 */
package com.onway.makeploy.common.service.integration.account.impl;

import java.math.BigDecimal;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;

import com.onway.account.common.service.api.AccountInfoQueryService;
import com.onway.account.common.service.enums.TransDirectionEnum;
import com.onway.account.common.service.model.TransferAccountInfo;
import com.onway.account.common.service.model.UserAccountInfo;
import com.onway.account.common.service.model.UserBonus;
import com.onway.account.common.service.result.AccountInfoQueryResult;
import com.onway.makeploy.common.service.integration.account.AccountInfoQueryServiceClient;
import com.onway.platform.common.base.PageQueryResult;
import com.onway.platform.common.base.QueryBase;
import com.onway.platform.common.exception.BaseRuntimeException;

/**
 * 账务中心账户信息查询服务门面实现
 * 
 * @author guangdong.li
 * @version $Id: AccountInfoQueryServiceClientImpl.java, v 0.1 21 Feb 2016 11:40:10 guangdong.li Exp $
 */
@Component("accountInfoQueryServiceClient")
public class AccountInfoQueryServiceClientImpl implements AccountInfoQueryServiceClient {

    /** logger */
    private static final Logger     logger = Logger
                                               .getLogger(AccountInfoQueryServiceClientImpl.class);

    /** 账务中心:账户信息查询服务 */
    @Resource
    private AccountInfoQueryService accountInfoQueryService;

    /** 
     * @see com.bench.app.front.integration.account.AccountInfoQueryServiceFacade#queryUserAccountInfo(java.lang.String)
     */
    @Override
    public UserAccountInfo queryUserAccountInfo(String userId) {
        try {
            AccountInfoQueryResult<UserAccountInfo> result = accountInfoQueryService
                .queryUserAccountInfo(userId);
            if (result == null || !result.isSuccess() || result.getInfo() == null) {
                logger.warn("余额账户查询异常,userId:" + userId);
                throw new BaseRuntimeException("余额账户查询异常");
            }
            return result.getInfo();
        } catch (Throwable e) {
            logger.error("查询账户信息异常,userId:" + userId, e);
            throw new BaseRuntimeException("系统异常");
        }
    }

    /** 
     * @see com.bench.app.front.integration.account.AccountInfoQueryServiceFacade#queryAllTransferAccountInfo(java.lang.String, com.yylc.account.common.service.base.QueryBase)
     */
    @Override
    public PageQueryResult<TransferAccountInfo> queryAllTransferAccountInfo(String userId,
                                                                            QueryBase query) {
        try {
            PageQueryResult<TransferAccountInfo> result = accountInfoQueryService
                .queryAllTransferAccountInfo(userId, query);

            PageQueryResult<TransferAccountInfo> bReuslt = new PageQueryResult<TransferAccountInfo>(
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
            logger.error("所有转账信息查询接口调用异常,userId:" + userId + ",query" + query, e);
            return new PageQueryResult<TransferAccountInfo>(false);
        }
    }

    /** 
     * @see com.bench.app.front.integration.account.AccountInfoQueryServiceFacade#queryTransferAccountInfo(java.lang.String, com.yylc.account.common.service.enums.TransDirectionEnum, com.yylc.account.common.service.base.QueryBase)
     */
    @Override
    public PageQueryResult<TransferAccountInfo> queryTransferAccountInfo(String userId,
                                                                         TransDirectionEnum transDirection,
                                                                         QueryBase query) {
        try {
            PageQueryResult<TransferAccountInfo> result = accountInfoQueryService
                .queryTransferAccountInfo(userId, transDirection, query);

            PageQueryResult<TransferAccountInfo> bReuslt = new PageQueryResult<TransferAccountInfo>(
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
            logger.error("根据资金流向查询转账记录接口调用异常,userId:" + userId + ",query" + query, e);
            return new PageQueryResult<TransferAccountInfo>(false);
        }
    }

    /** 
     * @see com.bench.app.front.integration.account.AccountInfoQueryServiceFacade#queryUserBonus(java.lang.String)
     */
    @SuppressWarnings("deprecation")
    @Override
    public BigDecimal queryUserBonus(String userId) {
        try {
            AccountInfoQueryResult<UserBonus> result = accountInfoQueryService
                .queryUserBonus(userId);
            UserBonus info = result.getInfo();
            if (info != null) {
                return info.getTotal();
            } else {
                return null;
            }
        } catch (Throwable e) {
            logger.error("获取用户红包总金额接口调用异常，userId" + userId, e);
        }
        return null;

    }

}
