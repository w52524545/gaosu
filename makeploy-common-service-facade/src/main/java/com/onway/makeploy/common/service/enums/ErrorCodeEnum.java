/**
 * onway.com Inc.
 * Copyright (c) 2013-2015 All Rights Reserved.
 */

package com.onway.makeploy.common.service.enums;

import com.onway.common.lang.StringUtils;
import com.onway.platform.common.enums.EnumBase;

/**
 * �������ö��
 * 
 * @author guangdong.li
 * @version $Id: SettlecoreResultCodeEnum.java, v 0.1 2015��10��30�� ����11:03:28 liudehong Exp $
 */
public enum ErrorCodeEnum implements EnumBase {

    /** �����ɹ� */
    SUCCESS("SUCCESS", "ҵ����ɹ�"),

    /**ҵ����ʧ��*/
    BIZ_FAIL("BIZ_FAIL", "ҵ����ʧ��"),

    /**ҵ������*/
    BIZ_UNKNOWN("BIZ_UNKNOWN", "ҵ������,���Ժ�"),

    /**ҵ������,���Ժ�����*/
    BIZ_PROCESSING("BIZ_PROCESSING", "ҵ������,���Ժ�����"),

    /** ϵͳ�쳣*/
    SYSTEM_ERROR("SYSTEM_ERROR", "ϵͳ�쳣������ϵ����Ա��"),

    /** �ⲿ�ӿڵ����쳣*/
    INTERFACE_SYSTEM_ERROR("INTERFACE_SYSTEM_ERROR", "�����쳣������ϵ����Ա��"),

    /** ҵ�����Ӵ���ʱ */
    CONNECT_TIME_OUT("CONNECT_TIME_OUT", "ϵͳ��ʱ�����Ժ�����!"),

    /**�����쳣*/
    CONCURRENT_ERROR("CONCURRENT_ERROR", "�����쳣"),

    /** �Ƿ����� */
    ILLEGAL_OPERATION("ILLEGAL_OPERATION", "�Ƿ�����"),

    /** ��������ȷ */
    ILLEGAL_ARGUMENT("ILLEGAL_ARGUMENT", "��������ȷ"),

    /** ����Ϊ�� */
    NULL_OBJECT("NULL_OBJECT", "����Ϊ��"),

    /** ����Ϊ�� */
    NULL_ARGUMENT("NULL_ARGUMENT", "����Ϊ��"),

    /** ϵͳ����δ��ʼ�� */
    SYS_CONFIG_NOT_INIT("SYS_CONFIG_NOT_INIT", "ϵͳ����δ��ʼ��"),

    /** �û�idΪ�� */
    NULL_USERID("NULL_USERID", "��ԱIDΪ��!"),

    /** ҵ����Ϊ�� */
    NULL_BIZ_ID("NULL_BIZ_ID", "ҵ����Ϊ��"),

    /** ID���Ϊ��*/
    NULL_ID("NULL_ID", "ID���Ϊ��!"),
    
    /**��Ʒ���´���*/
    FAIL_PRODUCT_UPDATE("FAIL_PRODUCT_UPDATE","��Ʒ�����쳣"),
    
    /**����ʧ��**/
    SAVE_FAILURE("SAVE_FAILURE","��Ʒ��Ϣ����ʧ��"),

    /** ��Ч��������ˮ�� */
    INVALID_WITHDRAW_ID("INVALID_WITHDRAW_ID", "��Ч��ҵ����"),

    /** Ŀ��״̬Ϊ��*/
    NULL_TARGET_STATE("NULL_TARGET_STATE", "Ŀ��״̬Ϊ��!"),

    /** ����Ϊ�� */
    NULL_CARD_NO("NULL_CARD_NO", "����Ϊ��!"),

    /** ���²����쳣*/
    MODIFIED_ERROR("MODIFIED_ERROR", "���²����쳣������ϵ����Ա��"),

    /** �߼����� */
    LOGIC_ERROR("LOGIC_ERROR", "�߼�����"),

    /** �����쳣 */
    DATA_ERROR("DATA_ERROR", "�����쳣"),

    /**���������ڱ�ʹ�� */
    TASK_LOCK_HOLDING("TASK_LOCK_HOLDING", "���������ڱ�ʹ��"),

    /** ���Ϊ���쳣*/
    NEGATIVE_AMOUNT_ERROR("NEGATIVE_AMOUNT_ERROR", "���Ϊ���쳣"),

    /** Ԥ�����״̬����*/
    UNEXPECTED_STATUS_ERROR("UNEXPECTED_STATUS_ERROR", "Ԥ�����״̬����"),

    /** �����ö�ٱ��� */
    ENUM_CODE_ERROR("ENUM_CODE_ERROR", "�����ö�ٱ���"),

    /** ����ӳ�������ô��� */
    RESULT_CODE_MAP_CONF_ERROR("RESULT_CODE_MAP_CONF_ERROR", "����ӳ�������ô���"),

    /** ��Ч�����п� */
    CARD_BIN_NOT_EXSIT("CARD_BIN_NOT_EXSIT", "��Ч�����п�"),

    /** �������е����к�Ϊ��  */
    NULL_BANK_LINE_NUM("NULL_BANK_LINE_NUM", "�������е����к�Ϊ��"),

    /**����ʧ��*/
    ENCRYPT_FAILURE("ENCRYPT_FAILURE", "����ʧ��"),

    /**����ʧ��*/
    DECRYPT_FAILURE("DECRYPT_FAILURE", "����ʧ��"),

    //============ʵ����֤====================

    /** ��Ա��ʵ����Ϊ�� */
    NULL_REAL_NAME("NULL_REAL_NAME", "��Ա��ʵ����Ϊ��!"),

    /** ��Ա֤����Ϊ�� */
    NULL_CERT_NO("NULL_CERT_NO", "��Ա֤����Ϊ��!"),

    /** ֤������Ϊ�� */
    NULL_CERT_TYPE("NULL_CERT_TYPE", "��Ա֤����Ϊ��!"),

    /** ��ʼ��ʵ����֤��Ϣʧ��*/
    SAVE_USER_CERTIFY_ERROR("SAVE_USER_CERTIFY_ERROR", "��ʼ��ʵ����֤��Ϣʧ��"),

    /** ����ʵ����֤��Ϣʧ��*/
    UPDATE_USER_CERTIFY_ERROR("UPDATE_USER_CERTIFY_ERROR", "����ʵ����֤��Ϣʧ��"),

    /**����ʵ����֤ͼƬʧ��*/
    SAVE_CERT_IMG_ERROR("SAVE_CERT_IMG_ERROR", "����ʵ����֤ͼƬʧ��"),

    /** ʵ����֤�쳣*/
    USER_CERTIFY_ERROR("USER_CERTIFY_ERROR", "ʵ����֤�쳣"),

    /**�û���֤ʧ��,����ϵ�ͷ�!*/
    USER_CERTIFY_FAIL("USER_CERTIFY_FAIL", "�û���֤ʧ��,����ϵ�ͷ�!"),

    /** ʵ����֤����������֤����,����ϵ�ͷ�!*/
    CERTIFY_MORE_THAN_MAX_COUNT("CERTIFY_MORE_THAN_MAX_COUNT", " ʵ����֤����������֤����,����ϵ�ͷ�!"),

    //============�����ѯ====================

    /** ������Ϊ��*/
    NULL_REGION_CODE("NULL_REGION_CODE", "������Ϊ��!"),

    /** ������Ϊ��*/
    NULL_PARENT_CODE("NULL_PARENT_CODE", "���򸸱��Ϊ��!"),

    //============֧������====================

    /**�������Ͳ��Ϸ�*/
    ILLEGAL_SCENE_TYPE_ENUM("ILLEGAL_SCENE_TYPE_ENUM", "�������Ͳ������Ϸ�"),

    /**ƽ̨codeö�ٲ��Ϸ�*/
    ILLEGAL_PLATFORM_CODE_ENUM("ILLEGAL_PLATFORM_CODE_ENUM", "ƽ̨code�������Ϸ�"),

    /**֧�����򲻺Ϸ�*/
    ILLEGAL_PAY_DERIECTION("ILLEGAL_PAY_DERIECTION", "֧�����򲻺Ϸ�!"),

    /**����codeΪ��*/
    NULL_BANK_CODE("NULL_BANK_CODE", "����codeΪ��"),

    /**����������ʱ��֧��*/
    SETTLE_CHANNEL_NOT_SUPPORT("SETTLE_CHANNEL_NOT_SUPPORT", "����������ʱ��֧��"),

    /**֧������������*/
    CHANNEL_NOT_USABLE("CHANNEL_NOT_USABLE", "֧������������,���Ժ�����!"),

    //=============��ֵ����============
    /** ��ˮ���ʧ��*/
    DEPOSIT_STORE_FAIL("DEPOSIT_STORE_FAIL", "������ˮ���ʧ��"),

    /** ��ֵ�ɹ�*/
    DEPOSIT_SUCESS("DEPOSIT_SUCESS", "��ֵ�ɹ�"),

    /** ��ֵʧ��*/
    DEPOSIT_FAIL("DEPOSIT_FAIL", "��ֵʧ��"),

    /** ��ʼ��*/
    DEPOSIT_INIT("DEPOSIT_INIT", "��ˮ��ʼ��"),

    /** ��ֵ��*/
    DEPOSIT_PROCESS("DEPOSIT_PROCESS", "��ֵ��"),

    /** ������ˮ������*/
    DEPOSIT_NO_NOT_EXIST("DEPOSIT_NO_NOT_EXIST", "������ˮ������"),

    /**��ֵ��ˮidΪ��*/
    NULL_DEPOSIT_ID("NULL_DEPOSIT_ID", "��ֵ��ˮidΪ��!"),

    /** ���³�ֵ��ˮ״̬ʧ��*/
    UPDATE_DEPOSIT_ERROR("UPDATE_DEPOSIT_ERROR", "���³�ֵ��ˮ״̬ʧ��"),

    /** ��ֵ�ز�Ĵ�������������,����ϵ�ͷ�!*/
    DEPOSIT_RECHECK_MORE_THAN_MAX_COUNT("DEPOSIT_RECHECK_MORE_THAN_MAX_COUNT",
                                        "��ֵ�ز�Ĵ�������������,����ϵ�ͷ�!"),
    /**��ˮ���ɹ��ڱ��״̬Ϊʧ��*/
    DEPOSIT_OVER_DUE("DEPOSIT_OVER_DUE", "��ˮ���ɹ��ڱ��״̬Ϊʧ��"),

    //=============����============
    /** ����״̬����δ����,����ϵ�ͷ�!*/
    WITHDRAW_RECHECK_MORE_THAN_MAX_COUNT("WITHDRAW_RECHECK_MORE_THAN_MAX_COUNT", "����״̬����δ����,����ϵ�ͷ�!"),

    //=============ǩԼ�鿨============
    /** �鿨����������*/
    AUTHAPI_NO_NOT_EXIST("AUTHAPI_NO_NOT_EXIST", "�鿨����������"),

    /** �鿨�ɹ�*/
    AUTH_SUCESS("AUTH_SUCESS", "�鿨�ɹ�"),

    /** �鿨ʧ��*/
    AUTH_FAIL("AUTH_FAIL", "�鿨ʧ��"),

    /** �鿨��ʼ��*/
    AUTH_INIT("AUTH_INIT", "�鿨��ʼ��"),

    /** �鿨��*/
    AUTH_PROCESS("AUTH_PROCESS", "�鿨��"),

    /** ǩԼ�鿨״̬���ʧ��*/
    AUTH_UPDATE_FAIL("AUTH_UPDATE_FAIL", "ǩԼ�鿨״̬���ʧ��"),

    /** ǩԼ�鿨����������֤����,����ϵ�ͷ�!*/
    AUTH_MORE_THAN_MAX_COUNT("AUTH_MORE_THAN_MAX_COUNT", "ǩԼ�鿨����������֤����,����ϵ�ͷ�!"),

    ;
    /** ö�ٱ�� */
    private String code;

    /** ö������ */
    private String message;

    /**
     * ���췽��
     * 
     * @param code         ö�ٱ��
     * @param message      ö������
     */
    private ErrorCodeEnum(String code, String message) {
        this.code = code;
        this.message = message;
    }

    /**
     * ͨ��ö��<code>code</code>���ö�١�
     * @param code ö��ֵ
     * @return  ��������ڷ���NUll<br/>������ڷ������ֵ
     */
    public static final ErrorCodeEnum getByCode(String code) {

        //���ΪNUll���� NUll
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
