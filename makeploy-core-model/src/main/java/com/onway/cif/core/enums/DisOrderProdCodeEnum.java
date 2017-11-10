package com.onway.cif.core.enums; /**
 * onway.com Inc.
 * Copyright (c) 2013-2013 All Rights Reserved.
 */

import com.onway.platform.common.enums.EnumBase;

/**
 * 清算层结果集码枚举
 * 
 * @author guangdong.li
 * @version $Id: CifCoreResultCodeEnum.java, v 0.1 2013-10-31 上午10:21:58  Exp $
 */
public enum DisOrderProdCodeEnum implements EnumBase {

    /**微信支付*/
    WECHAT_PAY("WECHAT_PAY","微信支付"),
    
    /**支付中*/
    PAYING("PAYING","支付中"),
    
    /**支付成功*/
    SUCCESS("SUCCESS","支付成功"),
    
    /**支付失败*/
    
    
    /**订单初始化*/
    INIT("INIT","订单初始化"),
    
    /**订单完结**/
    FINISH("FINISH","订单完结"),
    
    /** 产品修改失败 */
    FAIL_PRODUCT_UPDATE("FAIL_PRODUCT_UPDATE","产品修改失败");

    /** 枚举编号 */
    private String code;

    /** 枚举详情 */
    private String message;

    /**
     * 构造方法
     * 
     * @param code         枚举编号
     * @param message      枚举详情
     */
    private DisOrderProdCodeEnum(String code, String message) {
        this.code = code;
        this.message = message;
    }

    /**
     * 通过枚举<code>code</code>获得枚举。
     * 
     * @param code         枚举编号
     * @return
     */
    public static DisOrderProdCodeEnum getResultCodeEnumByCode(String code) {
        for (DisOrderProdCodeEnum param : values()) {
            if (param.getCode().equals(code)) {
                return param;
            }
        }
        return null;
    }

    /**
     * Getter method for property <tt>code</tt>.
     * 
     * @return property value of code
     */
    public String getCode() {
        return code;
    }

    /**
     * Getter method for property <tt>message</tt>.
     * 
     * @return property value of message
     */
    public String getMessage() {
        return message;
    }

    /**
     * Setter method for property <tt>message</tt>.
     * 
     * @param message value to be assigned to property message
     */
    public void setMessage(String message) {
        this.message = message;
    }

    /** 
     * @see EnumBase#message()
     */
    @Override
    public String message() {
        return message;
    }

    /**
     * @see EnumBase#value()
     */
    @Override
    public Number value() {
        return null;
    }
}
