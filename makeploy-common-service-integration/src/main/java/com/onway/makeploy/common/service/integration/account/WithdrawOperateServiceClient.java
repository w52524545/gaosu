/**
 * onway.com Inc.
 * Copyright (c) 2016-2016 All Rights Reserved.
 */
package com.onway.makeploy.common.service.integration.account;

import com.onway.account.common.service.enums.WithdrawStatusEnum;
import com.onway.account.common.service.model.BankCardInfo;
import com.onway.account.common.service.model.OperatorInfo;
import com.onway.account.common.service.model.WithdrawInfo;
import com.onway.account.common.service.result.WithdrawOperateResult;
import com.onway.platform.common.base.BaseResult;
import com.onway.platform.common.base.PageQueryResult;
import com.onway.platform.common.base.QueryBase;
import com.onway.platform.common.log.digest.DigestLogAnnotated;
import com.onway.platform.common.log.digest.ServiceTypeEnum;

/**
 * �����������ַ�������
 * 
 * @author guangdong.li
 * @version $Id: WithdrawOperateServiceClient.java, v 0.1 21 Feb 2016 11:46:47 guangdong.li Exp $
 */
public interface WithdrawOperateServiceClient {

    /**
     * ��������
     * 
     * @param userId  �û�ID�����           
     * @param withdraw ������Ϣ
     *      <pre>
     *          <li>withdrawNo   ���ֺţ���Ϊ�գ�</li>
     *          <li>status       ����״̬����Ϊ�գ�</li>
     *          <li>amount       ���ֽ����</li>
     *          <li>chargeFee    ���ַ��ã�����,Ĭ��:0��</li>
     *      </pre>
     * 
     * @param bankCard ���п���Ϣ
     *      <pre>
     *          <li>bankCardNo          ���п��ţ����</li>
     *          <li>bankCardType        ���п���𣨱��</li>
     *          <li>bankAccountName     ���п��˻��������</li>
     *          <li>bankNo              ���кţ����</li>
     *          <li>bankName            �������ƣ����</li>
     *          <li>provinceCode        ʡ���루��Ϊ�գ�</li>
     *          <li>cityCode            �б��루��Ϊ�գ�</li>
     *      </pre>
     * 
     * @return  ҵ��������  
     *      <pre>
     *         <li>success              �ɹ�ʧ�ܱ�ʶ</li>
     *         <li>code                 �����</li>
     *         <li>message              �������</li>
     *         
     *         <li>orderNo              ������</li>
     *      </pre>
     */
    @DigestLogAnnotated(serviceType = ServiceTypeEnum.SAL, bizKey = "")
    public WithdrawOperateResult withdrawApply(String userId, WithdrawInfo withdraw,
                                               BankCardInfo bankCard);

    /**
     * �������(�ݵ�֧��)
     * 
     * @param withdrawNo ���ֺţ����
     * @param auditStatus ���״̬�����
     * @param operatorInfo ��������Ϣ
     *      <pre>
     *          <li>operatorId      ������ID����Ϊ�գ�</li>
     *          <li>operatorName    ���������ƣ����</li>
     *          <li>memo            ������ע�����</li>
     *      </pre>
     * 
     * @return  ҵ��������  
     *      <pre>
     *         <li>success              �ɹ�ʧ�ܱ�ʶ</li>
     *         <li>code                 �����</li>
     *         <li>message              �������</li>
     *      </pre>
     */
    @DigestLogAnnotated(serviceType = ServiceTypeEnum.SAL, bizKey = "")
    public BaseResult withdrawAudit(String withdrawNo, WithdrawStatusEnum auditStatus,
                                    OperatorInfo operatorInfo);

    /**
     * ����ת��(�ݵ�֧��)
     * 
     * @param withdrawNo ���ֺţ����
     * @param isSuccess  �����Ƿ�ɹ������
     * @param operatorInfo ����Ա��Ϣ
     * @return  ҵ��������  
     *      <pre>
     *         <li>success              �ɹ�ʧ�ܱ�ʶ</li>
     *         <li>code                 �����</li>
     *         <li>message              �������</li>
     *      </pre>
     */
    @DigestLogAnnotated(serviceType = ServiceTypeEnum.SAL, bizKey = "")
    public BaseResult transferAccounts(String withdrawNo, Boolean isSuccess,
                                       OperatorInfo operatorInfo);

    /**
     * ���ּ�¼��ѯ����ҳ��ѯ
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
    @DigestLogAnnotated(serviceType = ServiceTypeEnum.SAL, bizKey = "")
    public PageQueryResult<WithdrawInfo> queryWithdrawInfo(String userId, QueryBase query);

    /**
     * ������Ϣ��ѯ
     * 
     * @param withdrawNo ���ֺţ����
     * @return ������Ϣ
     */
    @DigestLogAnnotated(serviceType = ServiceTypeEnum.SAL, bizKey = "")
    public WithdrawInfo queryUserWithdrawInfo(String withdrawNo);

}
