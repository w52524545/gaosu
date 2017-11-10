/**
 * onway.com Inc.
 * Copyright (c) 2013-2015 All Rights Reserved.
 */
package com.onway.makeploy.core.service.code;

import com.onway.common.lang.DateUtils;
import com.onway.common.lang.StringUtils;
import com.onway.makeploy.common.service.enums.ErrorCodeEnum;
import com.onway.platform.common.enums.PlatformCodeEnum;
import com.onway.platform.common.exception.BaseRuntimeException;

/**
 * ����������������
 * 
 * @author guangdong.li
 * @version $Id: CodeBuilder.java, v 0.1 2015��11��2�� ����4:56:19 guangdong.li Exp $
 */
public abstract class CodeBuilder {

    /**����*/
    protected PlatformCodeEnum    platformCode;

    /**C��ƽ̨*/
    private static ProdNoBuilder prodNoBuilder  = new ProdNoBuilder(
                                                     PlatformCodeEnum.CUSTOMER_PLATFORM);

    /**����ƽ̨*/
    private static OrderNoBuilder orderNoBuilder = new OrderNoBuilder(
                                                     PlatformCodeEnum.SETTLE_PLATFORM);
    
    /**B��ƽ̨*/
    private static OrderNoBuilder productNoBuilder = new OrderNoBuilder(
                                                     PlatformCodeEnum.MERCHANT_PLATFORM);
    
    /**��ٍƽ̨*/
    private static ProdNoBuilder mallNoBuilder = new ProdNoBuilder(
                                                     PlatformCodeEnum.MALL_PLATFORM);
    

    /**
     * ����ҵ�����͵õ�builderʵ��
     * @param type
     * @return
     */
    public static CodeBuilder getCodeBuilder(PlatformCodeEnum platformCode) {
        switch (platformCode) {
            case CUSTOMER_PLATFORM:
                return prodNoBuilder;
            case SETTLE_PLATFORM:
                return orderNoBuilder;
            case MERCHANT_PLATFORM:
                return productNoBuilder;
            case MALL_PLATFORM:
                return mallNoBuilder;
            default:
                throw new BaseRuntimeException(ErrorCodeEnum.ILLEGAL_ARGUMENT, "��Ч�ı�������");
        }
    }

    /**
     *  ��ţ�  ǰ׺��nλ�� + ʱ�����8λ��+seq(NO_CIRCLE_LENGTHλ)+��׺��mλ��
     * 
     * @param seq
     * @return
     */
    public String getCode(int seq) {
        StringBuilder sb = new StringBuilder(getPrefix());
        sb.append(DateUtils.getTodayString());
        sb.append(StringUtils.fillPrefix(Long.toString(seq % CodeGenerateConfig.NO_CIRCLE), "0",
            CodeGenerateConfig.NO_CIRCLE_LENGTH));
        sb.append(getSuffix());
        return sb.toString();
    }

    /**
     * ���췽��
     * 
     * @param sceneType
     */
    protected CodeBuilder(PlatformCodeEnum platformCode) {
        this.platformCode = platformCode;
    }

    /** ���ǰ׺ */
    protected String getPrefix() {
        return platformCode.getValue() + "";
    }

    /** ��ú�׺ */
    protected String getSuffix() {
        return "";
    }
}
