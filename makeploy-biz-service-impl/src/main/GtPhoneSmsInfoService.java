/**
 * onway.com Inc.
 * Copyright (c) 2016-2016 All Rights Reserved.
 */
package com.onway.makeploy.biz.service.query;

import com.onway.platform.common.enums.PlatformCodeEnum;

/**
 * ��ͨ�������ҵ�����
 * 
 * @author guangdong.li
 * @version $Id: GtPhoneSmsInfoService.java, v 0.1 17 Feb 2016 18:12:35 guangdong.li Exp $
 */
public interface GtPhoneSmsInfoService {
    /**
     * ���Ͷ�����֤��
     * 
     * @param businessCode
     * @param phone
     */
    public void sendVerifyCode(String businesscode, String phone, PlatformCodeEnum platformCode);

    /**
     * У����֤���Ƿ���ȷ
     * 
     * @param businessCode
     * @param phone
     * @param verifyCode
     * @return
     */
    public boolean checkVerifyCode(String businesscode, String phone, String verifyCode,
                                   PlatformCodeEnum platformCode);

}
