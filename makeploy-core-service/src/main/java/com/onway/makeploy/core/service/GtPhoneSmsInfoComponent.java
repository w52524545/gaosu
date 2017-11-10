package com.onway.makeploy.core.service;

import com.onway.platform.common.enums.PlatformCodeEnum;

/**
 * DocardCode.com Inc.
 * Copyright (c) 2005-2009 All Rights Reserved.
 */

/**
 * 短信记录服务
 * 
 * @author osmund
 * 
 * @version $Id: GtPhoneSmsInfoRepository.java, v 0.1 2013-5-15 下午8:20:45 WJL
 *          Exp $
 */
public interface GtPhoneSmsInfoComponent {

    /**
     * 发送短信验证码
     * 
     * @param businessCode
     * @param phone
     */
    public boolean sendVerifyCode(String businesscode, String phone, PlatformCodeEnum platformCode);

    /**
     * 检查验证码是否正确
     * 
     * @param businessCode
     * @param phone
     * @param verifyCode
     * @return
     */
    public boolean checkVerifyCode(String businesscode, String phone, String verifyCode,
                                   PlatformCodeEnum platformCode);

}
