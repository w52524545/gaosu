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
 * 编码生成器抽象类
 * 
 * @author guangdong.li
 * @version $Id: CodeBuilder.java, v 0.1 2015年11月2日 下午4:56:19 guangdong.li Exp $
 */
public abstract class CodeBuilder {

    /**场景*/
    protected PlatformCodeEnum    platformCode;

    /**C端平台*/
    private static ProdNoBuilder prodNoBuilder  = new ProdNoBuilder(
                                                     PlatformCodeEnum.CUSTOMER_PLATFORM);

    /**清算平台*/
    private static OrderNoBuilder orderNoBuilder = new OrderNoBuilder(
                                                     PlatformCodeEnum.SETTLE_PLATFORM);
    
    /**B端平台*/
    private static OrderNoBuilder productNoBuilder = new OrderNoBuilder(
                                                     PlatformCodeEnum.MERCHANT_PLATFORM);
    
    /**聚賺平台*/
    private static ProdNoBuilder mallNoBuilder = new ProdNoBuilder(
                                                     PlatformCodeEnum.MALL_PLATFORM);
    

    /**
     * 根据业务类型得到builder实例
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
                throw new BaseRuntimeException(ErrorCodeEnum.ILLEGAL_ARGUMENT, "无效的编码类型");
        }
    }

    /**
     *  编号：  前缀（n位） + 时间戳（8位）+seq(NO_CIRCLE_LENGTH位)+后缀（m位）
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
     * 构造方法
     * 
     * @param sceneType
     */
    protected CodeBuilder(PlatformCodeEnum platformCode) {
        this.platformCode = platformCode;
    }

    /** 获得前缀 */
    protected String getPrefix() {
        return platformCode.getValue() + "";
    }

    /** 获得后缀 */
    protected String getSuffix() {
        return "";
    }
}
