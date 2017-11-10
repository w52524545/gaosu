/**
 * onway.com Inc.
 * Copyright (c) 2016-2016 All Rights Reserved.
 */
package com.onway.makeploy.core.enums;

import org.apache.commons.lang.StringUtils;

import com.onway.platform.common.enums.EnumBase;

/**
 * 排序方式
 * 
 * @author yuanlin.wang
 * @version $Id: InsurancePayStatusEnum.java, v 0.1 06 Jul 2016 08:59:07
 *          yuanlin.wang Exp $
 */
public enum SortTypeEnum implements EnumBase {

	NORMAL("NORMAL", "普通", "1"), // 伴郎伴娘
	ADVANCED("ADVANCED", "靠前", "2"), // 其他
	STICK("STICK", "置顶", "3") // 其他
	;

	private String code;

	private String message;

	private String value;

	private SortTypeEnum(String code, String message, String value) {
		this.code = code;
		this.message = message;
		this.value = value;
	}

	@Override
	public String message() {
		return message;
	}

	public static SortTypeEnum getInsuranceInfoByCode(String name) {
		for (SortTypeEnum enumObj : SortTypeEnum.values()) {
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
