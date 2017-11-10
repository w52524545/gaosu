/**
 * onway.com Inc.
 * Copyright (c) 2013-2015 All Rights Reserved.
 */
package com.onway.makeploy.common.service.exception;

import com.onway.platform.common.enums.EnumBase;
import com.onway.platform.common.exception.BaseRuntimeException;

/**
 * @author guangdong.li
 * @version $Id: SettleCoreException.java, v 0.1 2015年11月2日 下午4:54:45 guangdong.li Exp $
 */
public class ErrorException extends BaseRuntimeException {

    /** serialVersionUID */
    private static final long serialVersionUID = -6520895041948389252L;

	public ErrorException() {
		super();
		// TODO Auto-generated constructor stub
	}

	public ErrorException(EnumBase baseEnum, String message) {
		super(baseEnum, message);
		// TODO Auto-generated constructor stub
	}

	public ErrorException(EnumBase baseEnum) {
		super(baseEnum);
		// TODO Auto-generated constructor stub
	}

	public ErrorException(String errorCode, String message, Throwable cause) {
		super(errorCode, message, cause);
		// TODO Auto-generated constructor stub
	}

	public ErrorException(String errorCode, String message) {
		super(errorCode, message);
		// TODO Auto-generated constructor stub
	}

	public ErrorException(String message, Throwable cause) {
		super(message, cause);
		// TODO Auto-generated constructor stub
	}

	public ErrorException(String message) {
		super(message);
		// TODO Auto-generated constructor stub
	}

	public ErrorException(Throwable cause) {
		super(cause);
		// TODO Auto-generated constructor stub
	}


}
