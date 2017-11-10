package com.onway.web.controller.template;

import com.onway.web.controller.result.JsonResult;

/**
 * onway.com Inc.
 * Copyright (c) 2013-2013 All Rights Reserved.
 */

/**
 * ����ģ��
 * 
 * @author guangdong.li
 * @version $Id: ServiceTemplate.java, v 0.1 2013-11-4 ����3:42:38  Exp $
 */
public interface ControllerTemplate {

    /**
     * <pre> ֧�ֱ�������ģ��ִ��ҵ����
     * 1. ���������װ
     * 2. �쳣���񣬼����ദ��
     * 3. ҵ����־��¼
     * </pre>
     * @param baseResult    ���ض���
     * @param action        ҵ������ص��Ľӿ�
     */
    public void execute(JsonResult jsonResult, ControllerCallBack callBack);

}
