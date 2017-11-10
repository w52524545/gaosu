/**
 * onway.com Inc.
 * Copyright (c) 2016-2016 All Rights Reserved.
 */
package com.onway.cif.core.constants;

/**
 * ���������쳣������,��Ҫ�����������в��������쳣ͨ��Exception������������
 * 
 * @author guangdong.li
 * @version $Id: ParamErrorException.java, v 0.1 17 Feb 2016 11:38:35 guangdong.li Exp $
 */
public class ParamErrorException extends RuntimeException {

    /**  */
    private static final long serialVersionUID = 1659968443894081660L;

    private String            message;

    /**
     * @param message
     */
    public ParamErrorException(String message) {
        super();
        this.message = message;
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

}
