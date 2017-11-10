/**
 * onway.com Inc.
 * Copyright (c) 2016-2016 All Rights Reserved.
 */
package com.onway.front.biz.service.query.impl;

import java.text.MessageFormat;
import java.util.UUID;

import javax.annotation.Resource;

import org.apache.log4j.Logger;

import com.onway.common.event.gotone.SmsEvent;
import com.onway.common.lang.StringUtils;
import com.onway.front.biz.service.inner.base.AbstractOperateService;
import com.onway.front.biz.service.query.GtPhoneSmsInfoService;
import com.onway.front.common.service.integration.gotone.GotoneServiceClient;
import com.onway.gotone.common.service.model.SmsInfo;
import com.onway.gotone.common.service.publisher.SmsUniformEventSender;
import com.onway.gotone.common.utils.VerifyCodeUtils;
import com.onway.platform.cache.exception.TairCacheException;
import com.onway.platform.cache.impl.TairCacheManagerImpl;
import com.onway.platform.common.base.QueryResult;
import com.onway.platform.common.enums.PlatformCodeEnum;
import com.onway.platform.common.utils.LogUtil;

/**
 * 短信相关业务服务实现
 * 
 * @author guangdong.li
 * @version $Id: GtPhoneSmsInfoServiceImpl.java, v 0.1 17 Feb 2016 18:14:22 guangdong.li Exp $
 */
public class GtPhoneSmsInfoServiceImpl extends AbstractOperateService implements
                                                                     GtPhoneSmsInfoService {

    private static final Logger   log              = Logger
                                                       .getLogger(GtPhoneSmsInfoServiceImpl.class);
    @Resource
    private GotoneServiceClient   gotoneServiceClient;

    @Resource
    private TairCacheManagerImpl  tairCacheManager;

    @Resource
    private SmsUniformEventSender smsUniformEventSender;

    private static final int      SMS_TIMES        = 5000;
    //缓存时间为5分钟
    private static final int      EXPIRE           = 5 * 60 * 1000;

    private static final String   GOTONE_CACHE_KEY = "GOTONE_KEY_";

    /** 
     * @see com.onway.front.biz.service.query.GtPhoneSmsInfoService#sendVerifyCode(java.lang.String, java.lang.String)
     */
    @Override
    public void sendVerifyCode(final String businesscode, final String phone,
                               PlatformCodeEnum platformCode) {
        try {
            boolean isProd = false;

            String verify = "000000";
            if (isProd) {
                SmsInfo smsInfo = gotoneServiceClient.getNewestSmsInfo(businesscode, phone,
                    platformCode);
                if (smsInfo != null
                    && (System.currentTimeMillis() - smsInfo.getGmtCreate().getTime() < SMS_TIMES)) {
                    verify = smsInfo.getMemo();
                } else {
                    verify = VerifyCodeUtils.getVerifyCode(6);
                }
            }

            String cacheKey = GOTONE_CACHE_KEY + phone + "_" + businesscode;
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
            }

        } catch (Exception e) {
            log.error(MessageFormat.format("发送验证码异常,businessCode:{0}, phone:{1}", businesscode,
                phone));
        } catch (Throwable e) {
            log.error(MessageFormat.format("发送验证码错误,businessCode:{0}, phone:{1}", businesscode,
                phone));
        }
    }

    /** 
     * @see com.onway.front.biz.service.query.GtPhoneSmsInfoService#checkVerifyCode(java.lang.String, java.lang.String, java.lang.String)
     */
    @Override
    public boolean checkVerifyCode(String businesscode, String phone, String verifyCode,
                                   PlatformCodeEnum platformCode) {
        String verifyCodetemp = null;
        try {
            String cacheKey = GOTONE_CACHE_KEY + phone + "_" + businesscode;
            verifyCodetemp = (String) tairCacheManager.getObject(cacheKey);
            if (StringUtils.isBlank(verifyCodetemp)) {
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

    /**
     * Setter method for property <tt>gotoneServiceClient</tt>.
     * 
     * @param gotoneServiceClient value to be assigned to property gotoneServiceClient
     */
    public void setGotoneServiceClient(GotoneServiceClient gotoneServiceClient) {
        this.gotoneServiceClient = gotoneServiceClient;
    }

}
