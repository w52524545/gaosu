/**
 * onway.com Inc.
 * Copyright (c) 2013-2015 All Rights Reserved.
 */
package com.onway.makeploy.core.service.code;

import com.onway.platform.common.enums.PlatformCodeEnum;

/**
 * 编码生成器组件
 * 
 * @author guangdong.li
 * @version $Id: CodeGenerateComponent.java, v 0.1 2015年11月2日 下午4:50:26 guangdong.li Exp $
 */
public interface CodeGenerateComponent {

    /**
     * 根据类型获得编码 
     * 
     * @param platformCode  平台code
     * @return
     */
    public String nextCodeByType(PlatformCodeEnum platformCode);

    /**
     * 生成序列号
     * 
     * @param bizType 业务类型
     * @return
     */
    public int nextCode(final String bizType);

}
