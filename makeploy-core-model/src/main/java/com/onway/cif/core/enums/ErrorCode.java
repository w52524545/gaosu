/**
 * onway.com Inc.
 * Copyright (c) 2016-2016 All Rights Reserved.
 */
package com.onway.cif.core.enums;

import com.onway.platform.common.enums.EnumBase;

/**
 * 基础错误码
 * 
 * @author yuanlin.wang
 * @version $Id: ErrorCode.java, v 0.1 18 Feb 2016 18:37:32 guangdong.li Exp $
 */
public enum ErrorCode implements EnumBase {

    IMAGE_IS_NULL("IMAGE_IS_NULL", "图片为空"),

    /** 操作成功 */
    SUCCESS("SUCCESS", "操作成功"), SYSTEM_ERROR("SYSTEM_ERROR", "服务异常，请稍后尝试"),

    NOT_EQUAL_LOGIN_PASSWD("NOT_EQUAL_LOGIN_PASSWD", "您两次输入的密码不相符，请重新输入"),

    ILLEGAL_ARGUMENT("ILLEGAL_ARGUMENT", "非法参数"),

    ERROR_CHECK_CODE("ERROR_CHECK_CODE", "验证码输入错误，请重新输入！"),

    NETWORD_CONNECT_ERROR("NETWORD_CONNECT_ERROR", "网络不太给力,请重新尝试"),

    INVALID_REQUEST("INVALID_REQUEST", "非法请求"),

    USER_NOT_EXSIT("USER_NOT_EXSIT", "系统不存在此用户"),

    USERID_NOT_BLANK("USERID_NOT_BLANK", "用户号不正确"),

    TOKEN_NOT_BLANK("TOKEN_NOT_BLANK", "token不正确"),

    FORCE_LOG_OUT("FORCE_LOG_OUT", "账号已在其他设备上登录，为了账号安全，请重新登录"),

    BANK_INFO_ERROR("BANK_INFO_ERROR", "银行卡信息有误"),

    /**{0} 要用 @see MessageFormat.format */
    EXCEED_BANK_S("EXCEED_BANK_S", "申购金额大于银行卡单笔最大支付限额{0}元"),

    //超过单日剩余交易额度  对不起，您所绑定的银行卡当日交易额度已达上限
    EXCEED_BANK_R1("EXCEED_BANK_R1", "您当日交易额度剩余{0}元，请提升交易额度"),

    EXCEED_BANK_R2("EXCEED_BANK_R2", "对不起，您的银行卡所支持的该类产品每日交易额度已达上限"),

    EXCEED_CUSTOM_S("EXCEED_CUSTOM_S", "首次理财，佑米建议您进行{0}元以下申购"),

    EXCEED_BANK_D("EXCEED_BANK_D", "申购金额大于银行卡单日最大支付限额{0}元"),

    EXCEED_QUICK_PAY("EXCEED_QUICK_PAY", "因交易系统升级，此次交易采用快捷支付，且本次银行卡支付的金额上限暂为{0}元"),

    EXCEED_QUICK_PAY_MODIFY("EXCEED_QUICK_PAY_MODIFY",
                            "因交易系统升级，此次交易采用快捷支付，且本次银行卡支付的金额上限暂为{0}元，请返回修改金额"),

    EXCEED_AVAIL_WITHDRAW("EXCEED_AVAIL_WITHDRAW", "转出金额大于可提现金额{0}元"),

    DEFAULT("DEFAULT", "默认错误"),

    NEED_AUTH("NEED_AUTH", "需要鉴权"),

    NEW_USER_AUTH("NEW_USER_AUTH", "新用户鉴权"),

    LITTLE_AUTH_MONEY_ERROR("LITTLE_AUTH_MONEY_ERROR", "小额鉴权输入金额出错"),

    EXCEED_BANK_M("EXCEED_BANK_M", "申购金额大于银行卡单月最大支付限额{0}元"),

    ERROR_LOGIN_PWD("ERROR_LOGIN_PWD", "登录密码不正确，请重新输入"),

    CHECK_CODE_ERROR("CHECK_CODE_ERROR", "验证码错误"),

    TEMPLET_CODE_NULL("TEMPLET_CODE_NULL", "分期分类编码为空错误"),

    PARAM_IS_NULL("PARAM_IS_NULL", "参数为空"),

    NULL_OF_THIS_RECORD("NULL_OF_THIS_RECORD", "该记录在系统中不存在");

    private String code;

    private String desc;

    /**
     * @param code
     * @param desc
     */
    private ErrorCode(String code, String desc) {
        this.code = code;
        this.desc = desc;
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
     * Setter method for property <tt>code</tt>.
     * 
     * @param code value to be assigned to property code
     */
    public void setCode(String code) {
        this.code = code;
    }

    /**
     * Getter method for property <tt>desc</tt>.
     * 
     * @return property value of desc
     */
    public String getDesc() {
        return desc;
    }

    /**
     * Setter method for property <tt>desc</tt>.
     * 
     * @param desc value to be assigned to property desc
     */
    public void setDesc(String desc) {
        this.desc = desc;
    }

    @Override
    public String message() {
        return desc;
    }

    @Override
    public Number value() {
        return null;
    }

}
