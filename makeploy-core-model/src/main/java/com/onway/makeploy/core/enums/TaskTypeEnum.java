/**
 * onway.com Inc.
 * Copyright (c) 2016-2016 All Rights Reserved.
 */
package com.onway.makeploy.core.enums;

import org.apache.commons.lang.StringUtils;

import com.onway.platform.common.enums.EnumBase;

/**
 * 任务类型
 * 
 * @author yuanlin.wang
 * @version $Id: InsurancePayStatusEnum.java, v 0.1 06 Jul 2016 08:59:07
 *          yuanlin.wang Exp $
 */
public enum TaskTypeEnum implements EnumBase {

	TASK_MEN_WOMEN("TASK_MEN_WOMEN", "伴郎伴娘", "1"), // 伴郎伴娘

	TASK_OTHER("TASK_OTHER", "其他", "2") // 其他
	;

	private String code;

	private String message;

	private String value;

	private TaskTypeEnum(String code, String message, String value) {
		this.code = code;
		this.message = message;
		this.value = value;
	}

	@Override
	public String message() {
		return message;
	}

	public static TaskTypeEnum getInsuranceInfoByCode(String name) {
		for (TaskTypeEnum enumObj : TaskTypeEnum.values()) {
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
