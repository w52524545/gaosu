/**
 * onway.com Inc.
 * Copyright (c) 2016-2016 All Rights Reserved.
 */
package com.onway.cif.core.enums;

import com.onway.platform.common.enums.EnumBase;

/**
 * ����������
 * 
 * @author yuanlin.wang
 * @version $Id: ErrorCode.java, v 0.1 18 Feb 2016 18:37:32 guangdong.li Exp $
 */
public enum ErrorCode implements EnumBase {

    IMAGE_IS_NULL("IMAGE_IS_NULL", "ͼƬΪ��"),

    /** �����ɹ� */
    SUCCESS("SUCCESS", "�����ɹ�"), SYSTEM_ERROR("SYSTEM_ERROR", "�����쳣�����Ժ���"),

    NOT_EQUAL_LOGIN_PASSWD("NOT_EQUAL_LOGIN_PASSWD", "��������������벻���������������"),

    ILLEGAL_ARGUMENT("ILLEGAL_ARGUMENT", "�Ƿ�����"),

    ERROR_CHECK_CODE("ERROR_CHECK_CODE", "��֤������������������룡"),

    NETWORD_CONNECT_ERROR("NETWORD_CONNECT_ERROR", "���粻̫����,�����³���"),

    INVALID_REQUEST("INVALID_REQUEST", "�Ƿ�����"),

    USER_NOT_EXSIT("USER_NOT_EXSIT", "ϵͳ�����ڴ��û�"),

    USERID_NOT_BLANK("USERID_NOT_BLANK", "�û��Ų���ȷ"),

    TOKEN_NOT_BLANK("TOKEN_NOT_BLANK", "token����ȷ"),

    FORCE_LOG_OUT("FORCE_LOG_OUT", "�˺����������豸�ϵ�¼��Ϊ���˺Ű�ȫ�������µ�¼"),

    BANK_INFO_ERROR("BANK_INFO_ERROR", "���п���Ϣ����"),

    /**{0} Ҫ�� @see MessageFormat.format */
    EXCEED_BANK_S("EXCEED_BANK_S", "�깺���������п��������֧���޶�{0}Ԫ"),

    //��������ʣ�ཻ�׶��  �Բ��������󶨵����п����ս��׶���Ѵ�����
    EXCEED_BANK_R1("EXCEED_BANK_R1", "�����ս��׶��ʣ��{0}Ԫ�����������׶��"),

    EXCEED_BANK_R2("EXCEED_BANK_R2", "�Բ����������п���֧�ֵĸ����Ʒÿ�ս��׶���Ѵ�����"),

    EXCEED_CUSTOM_S("EXCEED_CUSTOM_S", "�״���ƣ����׽���������{0}Ԫ�����깺"),

    EXCEED_BANK_D("EXCEED_BANK_D", "�깺���������п��������֧���޶�{0}Ԫ"),

    EXCEED_QUICK_PAY("EXCEED_QUICK_PAY", "����ϵͳ�������˴ν��ײ��ÿ��֧�����ұ������п�֧���Ľ��������Ϊ{0}Ԫ"),

    EXCEED_QUICK_PAY_MODIFY("EXCEED_QUICK_PAY_MODIFY",
                            "����ϵͳ�������˴ν��ײ��ÿ��֧�����ұ������п�֧���Ľ��������Ϊ{0}Ԫ���뷵���޸Ľ��"),

    EXCEED_AVAIL_WITHDRAW("EXCEED_AVAIL_WITHDRAW", "ת�������ڿ����ֽ��{0}Ԫ"),

    DEFAULT("DEFAULT", "Ĭ�ϴ���"),

    NEED_AUTH("NEED_AUTH", "��Ҫ��Ȩ"),

    NEW_USER_AUTH("NEW_USER_AUTH", "���û���Ȩ"),

    LITTLE_AUTH_MONEY_ERROR("LITTLE_AUTH_MONEY_ERROR", "С���Ȩ���������"),

    EXCEED_BANK_M("EXCEED_BANK_M", "�깺���������п��������֧���޶�{0}Ԫ"),

    ERROR_LOGIN_PWD("ERROR_LOGIN_PWD", "��¼���벻��ȷ������������"),

    CHECK_CODE_ERROR("CHECK_CODE_ERROR", "��֤�����"),

    TEMPLET_CODE_NULL("TEMPLET_CODE_NULL", "���ڷ������Ϊ�մ���"),

    PARAM_IS_NULL("PARAM_IS_NULL", "����Ϊ��"),

    NULL_OF_THIS_RECORD("NULL_OF_THIS_RECORD", "�ü�¼��ϵͳ�в�����");

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
