package com.onway.makeploy.core.service;

import com.onway.platform.common.enums.PlatformCodeEnum;

/**
 * DocardCode.com Inc.
 * Copyright (c) 2005-2009 All Rights Reserved.
 */

/**
 * ���ż�¼����
 * 
 * @author osmund
 * 
 * @version $Id: GtPhoneSmsInfoRepository.java, v 0.1 2013-5-15 ����8:20:45 WJL
 *          Exp $
 */
public interface GtPhoneSmsInfoComponent {

    /**
     * ���Ͷ�����֤��
     * 
     * @param businessCode
     * @param phone
     */
    public boolean sendVerifyCode(String businesscode, String phone, PlatformCodeEnum platformCode);

    /**
     * �����֤���Ƿ���ȷ
     * 
     * @param businessCode
     * @param phone
     * @param verifyCode
     * @return
     */
    public boolean checkVerifyCode(String businesscode, String phone, String verifyCode,
                                   PlatformCodeEnum platformCode);

}
