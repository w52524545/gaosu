package com.onway.makeploy.core.service;

/**
 * DocardCode.com Inc.
 * Copyright (c) 2005-2009 All Rights Reserved.
 */

import java.text.MessageFormat;
import java.util.UUID;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;

import com.onway.common.event.gotone.SmsEvent;
import com.onway.common.lang.StringUtils;
import com.onway.makeploy.common.service.integration.gotone.GotoneServiceClient;
import com.onway.gotone.common.service.publisher.SmsUniformEventSender;
import com.onway.gotone.common.utils.VerifyCodeUtils;
import com.onway.platform.cache.exception.TairCacheException;
import com.onway.platform.cache.impl.TairCacheManagerImpl;
import com.onway.platform.common.base.QueryResult;
import com.onway.platform.common.enums.PlatformCodeEnum;
import com.onway.platform.common.utils.LogUtil;

/**
 * @author osmund
 * @version $Id: GtPhoneSmsInfoRepositoryImpl.java, v 0.1 2013-5-15 下午8:26:08
 *          WJL Exp $
 */
public class GtPhoneSmsInfoComponentImpl implements GtPhoneSmsInfoComponent {

    private static final Logger   log                      = Logger
                                                               .getLogger(GtPhoneSmsInfoComponentImpl.class);

    private TairCacheManagerImpl  tairCacheManager;

    private SmsUniformEventSender smsUniformEventSender;

    private GotoneServiceClient   gotoneServiceClient;

    private static final int      SMS_TIMES                = 5000;

    //缓存时间为5分钟
    private static final int      EXPIRE                   = 5 * 60 * 1000;

    @SuppressWarnings("unused")
    private static final String   BUSINESSCODE_CELL_VERIFY = "SMS_CELL_VERIFY_CODE";

    private static final String   GOTONE_CACHE_KEY         = "GOTONE_KEY_";

    /**
     * @see com.bench.app.front.core.service.GtPhoneSmsInfoRepository#sendVerifyCode(java.lang.String, java.lang.String, java.lang.String)
     */
    @Override
    public boolean sendVerifyCode(String businesscode, String phone, PlatformCodeEnum platformCode) {
        try {
            boolean isProd = gotoneServiceClient.isProd();

            String verify = "000000";
            if (isProd) {
                /*   SmsInfo smsInfo = gotoneServiceClient.getNewestSmsInfo(businesscode, phone);
                   if (smsInfo != null
                       && (System.currentTimeMillis() - smsInfo.getGmtCreate().getTime() < SMS_TIMES)) {
                       verify = smsInfo.getMemo();
                   } else {
                       verify = VerifyCodeUtils.getVerifyCode(6);
                   }*/
                verify = VerifyCodeUtils.getVerifyCode(6);
            }

            String cacheKey = GOTONE_CACHE_KEY + platformCode.getCode() + "_" + phone + "_"
                              + businesscode;
            tairCacheManager.putObjectWithExpire(cacheKey, verify, EXPIRE);

            SmsEvent smsEvent = new SmsEvent(platformCode, UUID.randomUUID().toString(),
                businesscode, phone);
            smsEvent.setSendNow(true);
            smsEvent.setParam(SmsEvent.VERIFY_KEY, verify);
            smsEvent.setMemo(verify);
            smsEvent.setSendNow(true);

            LogUtil.info(log, smsEvent);

            QueryResult<String> result = smsUniformEventSender.sendEvent(smsEvent);
            if (!result.isSuccess()) {
                log.error("发送短信失败:" + result.getMessage());
                return false;
            }

        } catch (Exception e) {
            log.error(MessageFormat.format("发送验证码异常,businessCode:{0}, phone:{1}", businesscode,
                phone));
        } catch (Throwable e) {
            log.error(MessageFormat.format("发送验证码错误,businessCode:{0}, phone:{1}", businesscode,
                phone));
        }

        return true;

    }

    /**
     * @see com.bench.app.front.core.service.GtPhoneSmsInfoRepository#checkVerifyCode(java.lang.String, java.lang.String, java.lang.String)
     */
    @Override
    public boolean checkVerifyCode(String businesscode, String phone, String verifyCode,
                                   PlatformCodeEnum platformCode) {
        String verifyCodetemp = null;
        try {
            String cacheKey = GOTONE_CACHE_KEY + platformCode.getCode() + "_" + phone + "_"
                              + businesscode;
            verifyCodetemp = (String) tairCacheManager.getObject(cacheKey);
            if (StringUtils.isBlank(verifyCodetemp)) {
                //TODO  查询增加平台码
                verifyCodetemp = gotoneServiceClient.getVerifyCode(businesscode, phone,
                    platformCode);

            }

        } catch (TairCacheException e) {
            if (StringUtils.isBlank(verifyCodetemp)) {
                verifyCodetemp = gotoneServiceClient.getVerifyCode(businesscode, phone,
                    platformCode);
            }
        } catch (Exception e) {
            log.error(MessageFormat.format("获取验证码异常,businessCode:{0}, phone:{1}", businesscode,
                phone));
        } catch (Throwable e) {
            log.error("检查验证码出错" + e.getMessage());
            return false;
        }

        if (StringUtils.equals(verifyCode, verifyCodetemp)) {
            return true;
        }

        return false;
    }

    public void setTairCacheManager(TairCacheManagerImpl tairCacheManager) {
        this.tairCacheManager = tairCacheManager;
    }

    public void setSmsUniformEventSender(SmsUniformEventSender smsUniformEventSender) {
        this.smsUniformEventSender = smsUniformEventSender;
    }

    public void setGotoneServiceClient(GotoneServiceClient gotoneServiceClient) {
        this.gotoneServiceClient = gotoneServiceClient;
    }

}
