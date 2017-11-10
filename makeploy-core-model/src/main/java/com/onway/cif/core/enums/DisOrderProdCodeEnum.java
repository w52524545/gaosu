package com.onway.cif.core.enums; /**
 * onway.com Inc.
 * Copyright (c) 2013-2013 All Rights Reserved.
 */

import com.onway.platform.common.enums.EnumBase;

/**
 * �����������ö��
 * 
 * @author guangdong.li
 * @version $Id: CifCoreResultCodeEnum.java, v 0.1 2013-10-31 ����10:21:58  Exp $
 */
public enum DisOrderProdCodeEnum implements EnumBase {

    /**΢��֧��*/
    WECHAT_PAY("WECHAT_PAY","΢��֧��"),
    
    /**֧����*/
    PAYING("PAYING","֧����"),
    
    /**֧���ɹ�*/
    SUCCESS("SUCCESS","֧���ɹ�"),
    
    /**֧��ʧ��*/
    
    
    /**������ʼ��*/
    INIT("INIT","������ʼ��"),
    
    /**�������**/
    FINISH("FINISH","�������"),
    
    /** ��Ʒ�޸�ʧ�� */
    FAIL_PRODUCT_UPDATE("FAIL_PRODUCT_UPDATE","��Ʒ�޸�ʧ��");

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
    private DisOrderProdCodeEnum(String code, String message) {
        this.code = code;
        this.message = message;
    }

    /**
     * ͨ��ö��<code>code</code>���ö�١�
     * 
     * @param code         ö�ٱ��
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
