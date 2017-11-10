/**
 * onway.com Inc.
 * Copyright (c) 2016-2016 All Rights Reserved.
 */
package com.onway.makeploy.core.enums;

import org.apache.commons.lang.StringUtils;

import com.onway.platform.common.enums.EnumBase;

/**
 * ∞È¿…∞ÈƒÔ±Í ∂
 * 
 * @author yuanlin.wang
 * @version $Id: InsurancePayStatusEnum.java, v 0.1 06 Jul 2016 08:59:07
 *          yuanlin.wang Exp $
 */
public enum UserFlagEnum implements EnumBase {

	MEN_FLG("MEN_FLG", "∞È¿…", "1"), // ∞È¿…

	WOMEN_FLG("WOMEN_FLG", "∞ÈƒÔ", "2"), // ∞ÈƒÔ

	MEN_WOMEN_FLG("MEN_WOMEN_FLG", "¡Ω’ﬂºÊ±∏", "3")// ¡Ω’ﬂºÊ±∏
	;

	private String code;

	private String message;

	private String value;

	private UserFlagEnum(String code, String message, String value) {
		this.code = code;
		this.message = message;
		this.value = value;
	}

	@Override
	public String message() {
		return message;
	}

	public static UserFlagEnum getInsuranceInfoByCode(String name) {
		for (UserFlagEnum enumObj : UserFlagEnum.values()) {
			if (StringUtils.equals(enumObj.name(), name))
				return enumObj;
		}
		return null;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	@Override
	public Number value() {
		// TODO Auto-generated method stub
		return null;
	}

}
