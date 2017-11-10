package com.onway.makeploy.core.localcache.enums;

import org.apache.commons.lang.StringUtils;

import com.onway.platform.common.enums.EnumBase;

/**
 * �������ö��? * 
 * @author guangdong.li
 * @version $Id: LocalCacheNameEnum.java, v 0.1 2015��11��2�� ����3:38:11 liudehong Exp $
 */
public enum LocalCacheNameEnum implements EnumBase {

    /**ϵͳ����*/
    SYS_CONFIG("SYS_CONFIG", "ϵͳ����"),

    ;

    /** ö����*/
    private String code;

    /** ������Ϣ*/
    private String message;

    private LocalCacheNameEnum(String code, String message) {
        this.code = code;
        this.message = message;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

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
     * @see com.onway.platform.common.enums.EnumBase#value()
     */
    @Override
    public Number value() {
        return null;
    }

    /**
     * ͨ��ö��<code>code</code>���ö�١�?     * 
     * @param code         ö�ٱ��?     * @return
     */
    public static LocalCacheNameEnum getLocalCacheNameEnumByCode(String code) {
        for (LocalCacheNameEnum param : values()) {
            if (StringUtils.equals(param.getCode(), code)) {
                return param;
            }
        }
        return null;
    }
}
