/**
 * onway.com Inc.
 * Copyright (c) 2016-2016 All Rights Reserved.
 */
package com.onway.makeploy.common.service.integration.gotone.impl;

import java.text.MessageFormat;

import org.apache.log4j.Logger;

import com.onway.gotone.common.service.api.MsgService;
import com.onway.gotone.common.service.api.PushService;
import com.onway.gotone.common.service.api.SmsService;
import com.onway.gotone.common.service.api.SysConfigService;
import com.onway.gotone.common.service.model.MsgInfo;
import com.onway.gotone.common.service.model.PushInfo;
import com.onway.gotone.common.service.model.SmsInfo;
import com.onway.makeploy.common.service.integration.gotone.GotoneServiceClient;
import com.onway.platform.common.base.BaseResult;
import com.onway.platform.common.base.PageQueryResult;
import com.onway.platform.common.base.QueryBase;
import com.onway.platform.common.base.QueryResult;
import com.onway.platform.common.enums.PlatformCodeEnum;
import com.onway.platform.common.exception.BaseRuntimeException;
import com.onway.platform.common.page.Page;
import com.onway.platform.common.page.PageAttribute;
import com.onway.platform.common.page.PageList;

/**
 * ��ͨ�ͻ���ʵ��
 * 
 * @author guangdong.li
 * @version $Id: GotoneServiceClientImpl.java, v 0.1 17 Feb 2016 18:20:03 guangdong.li Exp $
 */
public class GotoneServiceClientImpl implements GotoneServiceClient {

    /** logger */
    private static final Logger logger = Logger.getLogger(GotoneServiceClientImpl.class);

    /** ��Ϣ���� */
    private MsgService          msgService;

    /** ����Ϣ���� */
    private SmsService          smsService;

    /** ���ͷ��� */
    private PushService         pushService;

    /** �������÷��� */
    private SysConfigService    sysConfigService;

    /** 
     * @see com.onway.front.common.service.integration.gotone.GotoneServiceClient#getVerifyCode(java.lang.String, java.lang.String)
     */
    @Override
    public String getVerifyCode(String businessCode, String phone, PlatformCodeEnum platformCode) {
        try {
            QueryResult<String> result = smsService
                .getVerifyCode(businessCode, phone, platformCode);
            return result.getResultObject();
        } catch (Throwable e) {
            logger.error("��ȡ��֤���쳣", e);
            throw new BaseRuntimeException("ϵͳ�쳣�����Ժ�����");
        }
    }

    /** 
     * @see com.onway.front.common.service.integration.gotone.GotoneServiceClient#getNewUnReadMsgCount(com.onway.common.enums.AppType, java.lang.String)
     */
    @Override
    public int getNewUnReadMsgCount(PlatformCodeEnum platformCode, String userId) {
        try {
            return msgService.getNewUnReadMsgCount(platformCode, userId).getResultObject();
        } catch (Throwable e) {
            logger.error("��ȡδ����Ϣ���쳣", e);
            throw new BaseRuntimeException("ϵͳ�쳣�����Ժ�����");
        }
    }

    /** 
     * @see com.onway.front.common.service.integration.gotone.GotoneServiceClient#getUserMsgList(com.onway.platform.common.base.QueryBase, com.onway.common.enums.AppType, java.lang.String, java.lang.Boolean)
     */
    @Override
    public PageList<MsgInfo> getUserMsgList(QueryBase query, PlatformCodeEnum platformCode,
                                            String userId, Boolean isRead) {
        try {
            PageQueryResult<MsgInfo> result = msgService.getUserMsgList(query, platformCode,
                userId, isRead);
            Page page = Page.getInstance(
                new PageAttribute(query.getPageNum(), query.getPageSize()), result.getTotalItems());
            PageList<MsgInfo> msgList = PageList.getInstance(result.getResultList(), page);
            return msgList;
        } catch (Throwable e) {
            logger.error("��ȡ�û���Ϣ�����б��쳣", e);
            throw new BaseRuntimeException("ϵͳ�쳣�����Ժ�����");
        }
    }

    /** 
     * @see com.onway.front.common.service.integration.gotone.GotoneServiceClient#readMsg(int)
     */
    @Override
    public BaseResult readMsg(int msgId) {
        try {
            return msgService.readMsg(msgId);
        } catch (Throwable e) {
            logger.error("�޸��Ѷ�״̬�쳣", e);
            throw new BaseRuntimeException("ϵͳ�쳣�����Ժ�����");
        }
    }

    /** 
     * @see com.onway.front.common.service.integration.gotone.GotoneServiceClient#readUserAllMsg(java.lang.String)
     */
    @Override
    public BaseResult readUserAllMsg(String userId) {
        try {
            return msgService.readUserAllMsg(userId);
        } catch (Throwable e) {
            logger.error("�޸��Ѷ�״̬�쳣", e);
            throw new BaseRuntimeException("ϵͳ�쳣�����Ժ�����");
        }
    }

    /** 
     * @see com.onway.front.common.service.integration.gotone.GotoneServiceClient#getUserMsg(int)
     */
    @Override
    public MsgInfo getUserMsg(int msgId) {
        try {
            return msgService.getUserMsg(msgId).getResultObject();
        } catch (Throwable e) {
            logger.error("��ѯ��Ϣ�����쳣", e);
            throw new BaseRuntimeException("ϵͳ�쳣�����Ժ�����");
        }
    }

    /** 
     * @see com.onway.front.common.service.integration.gotone.GotoneServiceClient#getNotSendPush(java.lang.String)
     */
    @Override
    public PushInfo getNotSendPush(String userId) {
        try {
            QueryResult<PushInfo> result = pushService.getUserNotSendPush(
                PlatformCodeEnum.ONWAY_PLATFORM, userId);
            return result.getResultObject();
        } catch (Throwable e) {
            logger.error("��ȡ�û�δ����Push��Ϣ�б�", e);
            throw new BaseRuntimeException("ϵͳ�쳣�����Ժ�����");
        }
    }

    /** 
     * @see com.onway.front.common.service.integration.gotone.GotoneServiceClient#getNewestSmsInfo(java.lang.String, java.lang.String)
     */
    @Override
    public SmsInfo getNewestSmsInfo(String businessCode, String phone, PlatformCodeEnum platformCode) {
        try {
            QueryResult<SmsInfo> result = smsService.getNewestSmsInfo(businessCode, phone,
                platformCode);
            return result.getResultObject();
        } catch (Throwable e) {
            logger.error(MessageFormat
                .format("getNewestSmsInfo �鿴�ֻ�������ҵ�����͵Ķ��� �� businessCode��{0}��phone{1} ",
                    businessCode, phone), e);
            throw new BaseRuntimeException("��ȡ��֤���쳣");
        }
    }

    /** 
     * @see com.onway.front.common.service.integration.gotone.GotoneServiceClient#isProd()
     */
    @Override
    public Boolean isProd() {
        // return false;
        try {
            QueryResult<Boolean> result = sysConfigService.isProd();
            return result.getResultObject();
        } catch (Throwable e) {
            logger.error("isProd �鿴���Ż���״̬�쳣 ");
            throw new BaseRuntimeException("isProd �鿴���Ż���״̬�쳣");
        }
    }

    /**
     * Setter method for property <tt>msgService</tt>.
     * 
     * @param msgService value to be assigned to property msgService
     */
    public void setMsgService(MsgService msgService) {
        this.msgService = msgService;
    }

    /**
     * Setter method for property <tt>smsService</tt>.
     * 
     * @param smsService value to be assigned to property smsService
     */
    public void setSmsService(SmsService smsService) {
        this.smsService = smsService;
    }

    /**
     * Setter method for property <tt>pushService</tt>.
     * 
     * @param pushService value to be assigned to property pushService
     */
    public void setPushService(PushService pushService) {
        this.pushService = pushService;
    }

    /**
     * Setter method for property <tt>sysConfigService</tt>.
     * 
     * @param sysConfigService value to be assigned to property sysConfigService
     */
    public void setSysConfigService(SysConfigService sysConfigService) {
        this.sysConfigService = sysConfigService;
    }

}
