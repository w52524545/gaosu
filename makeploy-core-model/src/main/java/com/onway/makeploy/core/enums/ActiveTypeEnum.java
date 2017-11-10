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
public enum ActiveTypeEnum implements EnumBase {

	BANNER_TYPE("BANNER_TYPE", "banner", "1"), // banner
	POLICY_TYPE("POLICY_TYPE", "政策", "2"), // 政策
	LINK_TYPE("STICK", "友情链接", "3"), // 友情链接
	NOTICE_TYPE("STICK", "公告", "4") // 公告
	;

	private String code;

	private String message;

	private String value;

	private ActiveTypeEnum(String code, String message, String value) {
		this.code = code;
		this.message = message;
		this.value = value;
	}

	@Override
	public String message() {
		return message;
	}

	public static ActiveTypeEnum getInsuranceInfoByCode(String name) {
		for (ActiveTypeEnum enumObj : ActiveTypeEnum.values()) {
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
