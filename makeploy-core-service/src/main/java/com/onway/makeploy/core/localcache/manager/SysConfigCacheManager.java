package com.onway.makeploy.core.localcache.manager;


import com.onway.makeploy.core.localcache.enums.SysConfigCacheKeyEnum;

/**
 * ϵͳ��������
 * 
 * @author guangdong.li
 * @version $Id: SysConfigCacheManager.java, v 0.1 17 Feb 2016 15:35:47 guangdong.li Exp $
 */
public interface SysConfigCacheManager {

    /**
     * ���ݲ������õ�key��ȡ��Ӧ��value
     * 
     * @param configKeyEnum ���õ�key
     * 
     * @return
     */
    public String getConfigValue(SysConfigCacheKeyEnum configKeyEnum);
}
