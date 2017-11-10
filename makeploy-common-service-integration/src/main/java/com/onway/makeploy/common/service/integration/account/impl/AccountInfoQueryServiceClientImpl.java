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
 * ���������˻���Ϣ��ѯ��������ʵ��
 * 
 * @author guangdong.li
 * @version $Id: AccountInfoQueryServiceClientImpl.java, v 0.1 21 Feb 2016 11:40:10 guangdong.li Exp $
 */
@Component("accountInfoQueryServiceClient")
public class AccountInfoQueryServiceClientImpl implements AccountInfoQueryServiceClient {

    /** logger */
    private static final Logger     logger = Logger
                                               .getLogger(AccountInfoQueryServiceClientImpl.class);

    /** ��������:�˻���Ϣ��ѯ���� */
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
                logger.warn("����˻���ѯ�쳣,userId:" + userId);
                throw new BaseRuntimeException("����˻���ѯ�쳣");
            }
            return result.getInfo();
        } catch (Throwable e) {
            logger.error("��ѯ�˻���Ϣ�쳣,userId:" + userId, e);
            throw new BaseRuntimeException("ϵͳ�쳣");
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
            logger.error("����ת����Ϣ��ѯ�ӿڵ����쳣,userId:" + userId + ",query" + query, e);
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
            logger.error("�����ʽ������ѯת�˼�¼�ӿڵ����쳣,userId:" + userId + ",query" + query, e);
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
            logger.error("��ȡ�û�����ܽ��ӿڵ����쳣��userId" + userId, e);
        }
        return null;

    }

}
