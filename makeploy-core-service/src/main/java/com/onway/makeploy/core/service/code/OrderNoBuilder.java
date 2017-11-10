/**
 * onway.com Inc.
 * Copyright (c) 2013-2015 All Rights Reserved.
 */
package com.onway.makeploy.core.service.code;

import com.onway.platform.common.enums.PlatformCodeEnum;

/**
 * 订单流水号生成器
 * 
 * @author guangdong.li
 * @version $Id: OrderNoBuilder.java, v 0.1 2015年11月2日 下午4:58:26 guangdong.li Exp $
 */
public class OrderNoBuilder extends CodeBuilder {

    /**
     * @param platformCode
     */
    public OrderNoBuilder(PlatformCodeEnum platformCode) {
        super(platformCode);
    }
}
