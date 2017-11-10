/**
 * onway.com Inc.
 * Copyright (c) 2016-2016 All Rights Reserved.
 */
package com.onway.makeploy.common.service.integration.account;

import java.math.BigDecimal;

import com.onway.account.common.service.enums.TransDirectionEnum;
import com.onway.account.common.service.model.TransferAccountInfo;
import com.onway.account.common.service.model.UserAccountInfo;
import com.onway.platform.common.base.PageQueryResult;
import com.onway.platform.common.base.QueryBase;
import com.onway.platform.common.log.digest.DigestLogAnnotated;
import com.onway.platform.common.log.digest.ServiceTypeEnum;

/**
 * ���������˻���Ϣ��ѯ��������
 * 
 * @author guangdong.li
 * @version $Id: AccountInfoQueryServiceClient.java, v 0.1 21 Feb 2016 11:37:49 guangdong.li Exp $
 */
public interface AccountInfoQueryServiceClient {

    /**
     * �˻�����ѯ
     * 
     * @param userId �û�ID�����
     * @return  ҵ��������  
     *      <pre>
     *         <li>info                 �û��˻���Ϣ�����˻��š�����������������</li>
     *      </pre>
     */
    @DigestLogAnnotated(serviceType = ServiceTypeEnum.SAL, bizKey = "")
    public UserAccountInfo queryUserAccountInfo(String userId);

    /**
     * ��ѯ����ת�˼�¼����ҳ��ѯ
     * 
     * @param userId �û�ID�����   
     * @param query  ��ѯ����ģ��
     * 
     * @return  ҵ��������  
     *      <pre>
     *         <li>success              �ɹ�ʧ�ܱ�ʶ</li>
     *         <li>code                 �����</li>
     *         <li>message              �������</li>
     *         
     *         <li>totalItems           ��ѯ���Ľ������</li>
     *         <li>totalPages           ��ҳ��</li>
     *         <li>currentPage          ��ǰҳ��</li>
     *         <li>itemsPerPage         ÿҳ����</li>
     *         <li>resultList           ��ҳ�����</li>
     *      </pre>
     */
    @DigestLogAnnotated(serviceType = ServiceTypeEnum.SAL, bizKey = "��ѯ����ת�˼�¼")
    public PageQueryResult<TransferAccountInfo> queryAllTransferAccountInfo(String userId,
                                                                            QueryBase query);

    /**
     * �����ʽ������ѯת�˼�¼����ҳ��ѯ
     * 
     * @param userId �û�ID�����   
     * @param transDirection �ʽ����򣨱��
     * @param query  ��ѯ����ģ��
     * 
     * @return  ҵ��������  
     *      <pre>
     *         <li>success              �ɹ�ʧ�ܱ�ʶ</li>
     *         <li>code                 �����</li>
     *         <li>message              �������</li>
     *         
     *         <li>totalItems           ��ѯ���Ľ������</li>
     *         <li>totalPages           ��ҳ��</li>
     *         <li>currentPage          ��ǰҳ��</li>
     *         <li>itemsPerPage         ÿҳ����</li>
     *         <li>resultList           ��ҳ�����</li>
     *      </pre>
     */
    @DigestLogAnnotated(serviceType = ServiceTypeEnum.SAL, bizKey = "")
    public PageQueryResult<TransferAccountInfo> queryTransferAccountInfo(String userId,
                                                                         TransDirectionEnum transDirection,
                                                                         QueryBase query);

    /**
     * ��ȡ�û���ʷ�ĺ���ܽ��
     * ʥ���ר�ã����ã�
     * @param userId userId �û�ID�����   
     * @return
     */
    @Deprecated
    @DigestLogAnnotated(serviceType = ServiceTypeEnum.SAL, bizKey = "")
    public BigDecimal queryUserBonus(String userId);

}
