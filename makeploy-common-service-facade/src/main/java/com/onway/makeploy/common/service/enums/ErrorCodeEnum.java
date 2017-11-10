/**
 * onway.com Inc.
 * Copyright (c) 2013-2015 All Rights Reserved.
 */

package com.onway.makeploy.common.service.enums;

import com.onway.common.lang.StringUtils;
import com.onway.platform.common.enums.EnumBase;

/**
 * 操作结果枚举
 * 
 * @author guangdong.li
 * @version $Id: SettlecoreResultCodeEnum.java, v 0.1 2015年10月30日 上午11:03:28 liudehong Exp $
 */
public enum ErrorCodeEnum implements EnumBase {

    /** 操作成功 */
    SUCCESS("SUCCESS", "业务处理成功"),

    /**业务处理失败*/
    BIZ_FAIL("BIZ_FAIL", "业务处理失败"),

    /**业务处理中*/
    BIZ_UNKNOWN("BIZ_UNKNOWN", "业务处理中,请稍候"),

    /**业务处理中,请稍候重试*/
    BIZ_PROCESSING("BIZ_PROCESSING", "业务处理中,请稍候重试"),

    /** 系统异常*/
    SYSTEM_ERROR("SYSTEM_ERROR", "系统异常，请联系管理员！"),

    /** 外部接口调用异常*/
    INTERFACE_SYSTEM_ERROR("INTERFACE_SYSTEM_ERROR", "服务异常，请联系管理员！"),

    /** 业务连接处理超时 */
    CONNECT_TIME_OUT("CONNECT_TIME_OUT", "系统超时，请稍后再试!"),

    /**并发异常*/
    CONCURRENT_ERROR("CONCURRENT_ERROR", "并发异常"),

    /** 非法操作 */
    ILLEGAL_OPERATION("ILLEGAL_OPERATION", "非法操作"),

    /** 参数不正确 */
    ILLEGAL_ARGUMENT("ILLEGAL_ARGUMENT", "参数不正确"),

    /** 对象为空 */
    NULL_OBJECT("NULL_OBJECT", "对象为空"),

    /** 参数为空 */
    NULL_ARGUMENT("NULL_ARGUMENT", "参数为空"),

    /** 系统配置未初始化 */
    SYS_CONFIG_NOT_INIT("SYS_CONFIG_NOT_INIT", "系统配置未初始化"),

    /** 用户id为空 */
    NULL_USERID("NULL_USERID", "会员ID为空!"),

    /** 业务编号为空 */
    NULL_BIZ_ID("NULL_BIZ_ID", "业务编号为空"),

    /** ID编号为空*/
    NULL_ID("NULL_ID", "ID编号为空!"),
    
    /**产品更新错误*/
    FAIL_PRODUCT_UPDATE("FAIL_PRODUCT_UPDATE","产品更新异常"),
    
    /**保存失败**/
    SAVE_FAILURE("SAVE_FAILURE","产品信息保存失败"),

    /** 无效的提现流水号 */
    INVALID_WITHDRAW_ID("INVALID_WITHDRAW_ID", "无效的业务编号"),

    /** 目标状态为空*/
    NULL_TARGET_STATE("NULL_TARGET_STATE", "目标状态为空!"),

    /** 卡号为空 */
    NULL_CARD_NO("NULL_CARD_NO", "卡号为空!"),

    /** 更新操作异常*/
    MODIFIED_ERROR("MODIFIED_ERROR", "更新操作异常，请联系管理员！"),

    /** 逻辑错误 */
    LOGIC_ERROR("LOGIC_ERROR", "逻辑错误"),

    /** 数据异常 */
    DATA_ERROR("DATA_ERROR", "数据异常"),

    /**任务锁正在被使用 */
    TASK_LOCK_HOLDING("TASK_LOCK_HOLDING", "任务锁正在被使用"),

    /** 金额为负异常*/
    NEGATIVE_AMOUNT_ERROR("NEGATIVE_AMOUNT_ERROR", "金额为负异常"),

    /** 预期外的状态错误*/
    UNEXPECTED_STATUS_ERROR("UNEXPECTED_STATUS_ERROR", "预期外的状态错误"),

    /** 错误的枚举编码 */
    ENUM_CODE_ERROR("ENUM_CODE_ERROR", "错误的枚举编码"),

    /** 渠道映射码配置错误 */
    RESULT_CODE_MAP_CONF_ERROR("RESULT_CODE_MAP_CONF_ERROR", "渠道映射码配置错误"),

    /** 无效的银行卡 */
    CARD_BIN_NOT_EXSIT("CARD_BIN_NOT_EXSIT", "无效的银行卡"),

    /** 银行总行的联行号为空  */
    NULL_BANK_LINE_NUM("NULL_BANK_LINE_NUM", "银行总行的联行号为空"),

    /**加密失败*/
    ENCRYPT_FAILURE("ENCRYPT_FAILURE", "加密失败"),

    /**解密失败*/
    DECRYPT_FAILURE("DECRYPT_FAILURE", "解密失败"),

    //============实名认证====================

    /** 会员真实姓名为空 */
    NULL_REAL_NAME("NULL_REAL_NAME", "会员真实姓名为空!"),

    /** 会员证件号为空 */
    NULL_CERT_NO("NULL_CERT_NO", "会员证件号为空!"),

    /** 证件类型为空 */
    NULL_CERT_TYPE("NULL_CERT_TYPE", "会员证件号为空!"),

    /** 初始化实名认证信息失败*/
    SAVE_USER_CERTIFY_ERROR("SAVE_USER_CERTIFY_ERROR", "初始化实名认证信息失败"),

    /** 更新实名认证信息失败*/
    UPDATE_USER_CERTIFY_ERROR("UPDATE_USER_CERTIFY_ERROR", "更新实名认证信息失败"),

    /**保存实名认证图片失败*/
    SAVE_CERT_IMG_ERROR("SAVE_CERT_IMG_ERROR", "保存实名认证图片失败"),

    /** 实名认证异常*/
    USER_CERTIFY_ERROR("USER_CERTIFY_ERROR", "实名认证异常"),

    /**用户认证失败,请联系客服!*/
    USER_CERTIFY_FAIL("USER_CERTIFY_FAIL", "用户认证失败,请联系客服!"),

    /** 实名认证超过最大可认证次数,请联系客服!*/
    CERTIFY_MORE_THAN_MAX_COUNT("CERTIFY_MORE_THAN_MAX_COUNT", " 实名认证超过最大可认证次数,请联系客服!"),

    //============区域查询====================

    /** 区域编号为空*/
    NULL_REGION_CODE("NULL_REGION_CODE", "区域编号为空!"),

    /** 区域编号为空*/
    NULL_PARENT_CODE("NULL_PARENT_CODE", "区域父编号为空!"),

    //============支付决策====================

    /**场景类型不合法*/
    ILLEGAL_SCENE_TYPE_ENUM("ILLEGAL_SCENE_TYPE_ENUM", "场景类型参数不合法"),

    /**平台code枚举不合法*/
    ILLEGAL_PLATFORM_CODE_ENUM("ILLEGAL_PLATFORM_CODE_ENUM", "平台code参数不合法"),

    /**支付方向不合法*/
    ILLEGAL_PAY_DERIECTION("ILLEGAL_PAY_DERIECTION", "支付方向不合法!"),

    /**银行code为空*/
    NULL_BANK_CODE("NULL_BANK_CODE", "银行code为空"),

    /**清算渠道暂时不支持*/
    SETTLE_CHANNEL_NOT_SUPPORT("SETTLE_CHANNEL_NOT_SUPPORT", "清算渠道暂时不支持"),

    /**支付渠道不可用*/
    CHANNEL_NOT_USABLE("CHANNEL_NOT_USABLE", "支付渠道不可用,请稍后再试!"),

    //=============充值代扣============
    /** 流水落地失败*/
    DEPOSIT_STORE_FAIL("DEPOSIT_STORE_FAIL", "清算流水落地失败"),

    /** 充值成功*/
    DEPOSIT_SUCESS("DEPOSIT_SUCESS", "充值成功"),

    /** 充值失败*/
    DEPOSIT_FAIL("DEPOSIT_FAIL", "充值失败"),

    /** 初始化*/
    DEPOSIT_INIT("DEPOSIT_INIT", "流水初始化"),

    /** 充值中*/
    DEPOSIT_PROCESS("DEPOSIT_PROCESS", "充值中"),

    /** 清算流水不存在*/
    DEPOSIT_NO_NOT_EXIST("DEPOSIT_NO_NOT_EXIST", "清算流水不存在"),

    /**充值流水id为空*/
    NULL_DEPOSIT_ID("NULL_DEPOSIT_ID", "充值流水id为空!"),

    /** 更新充值流水状态失败*/
    UPDATE_DEPOSIT_ERROR("UPDATE_DEPOSIT_ERROR", "更新充值流水状态失败"),

    /** 充值回查的次数超过最大次数,请联系客服!*/
    DEPOSIT_RECHECK_MORE_THAN_MAX_COUNT("DEPOSIT_RECHECK_MORE_THAN_MAX_COUNT",
                                        "充值回查的次数超过最大次数,请联系客服!"),
    /**流水过渡过期变更状态为失败*/
    DEPOSIT_OVER_DUE("DEPOSIT_OVER_DUE", "流水过渡过期变更状态为失败"),

    //=============提现============
    /** 提现状态银行未返回,请联系客服!*/
    WITHDRAW_RECHECK_MORE_THAN_MAX_COUNT("WITHDRAW_RECHECK_MORE_THAN_MAX_COUNT", "提现状态银行未返回,请联系客服!"),

    //=============签约验卡============
    /** 验卡渠道不存在*/
    AUTHAPI_NO_NOT_EXIST("AUTHAPI_NO_NOT_EXIST", "验卡渠道不存在"),

    /** 验卡成功*/
    AUTH_SUCESS("AUTH_SUCESS", "验卡成功"),

    /** 验卡失败*/
    AUTH_FAIL("AUTH_FAIL", "验卡失败"),

    /** 验卡初始化*/
    AUTH_INIT("AUTH_INIT", "验卡初始化"),

    /** 验卡中*/
    AUTH_PROCESS("AUTH_PROCESS", "验卡中"),

    /** 签约验卡状态变更失败*/
    AUTH_UPDATE_FAIL("AUTH_UPDATE_FAIL", "签约验卡状态变更失败"),

    /** 签约验卡超过最大可认证次数,请联系客服!*/
    AUTH_MORE_THAN_MAX_COUNT("AUTH_MORE_THAN_MAX_COUNT", "签约验卡超过最大可认证次数,请联系客服!"),

    ;
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
    private ErrorCodeEnum(String code, String message) {
        this.code = code;
        this.message = message;
    }

    /**
     * 通过枚举<code>code</code>获得枚举。
     * @param code 枚举值
     * @return  如果不存在返回NUll<br/>如果存在返回相关值
     */
    public static final ErrorCodeEnum getByCode(String code) {

        //如果为NUll返回 NUll
        if (StringUtils.isBlank(code)) {
            return null;
        }

        for (ErrorCodeEnum item : values()) {
            if (StringUtils.equals(item.getCode(), code)) {
                return item;
            }
        }

        return null;
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
     * @see com.onway.platform.common.enums.EnumBase#message()
     */
    @Override
    public String message() {
        return message;
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
     * @see com.onway.platform.common.enums.EnumBase#value()
     */
    @Override
    public Number value() {
        return null;
    }

}
