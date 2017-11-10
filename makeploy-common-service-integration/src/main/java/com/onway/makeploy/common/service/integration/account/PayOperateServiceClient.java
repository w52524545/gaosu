/**
 * onway.com Inc.
 * Copyright (c) 2016-2016 All Rights Reserved.
 */
package com.onway.makeploy.common.service.integration.account;

import com.onway.account.common.service.model.PayRequest;
import com.onway.platform.common.base.BaseResult;
import com.onway.platform.common.log.digest.DigestLogAnnotated;
import com.onway.platform.common.log.digest.ServiceTypeEnum;

/**
 * �����������֧����������
 * 
 * @author guangdong.li
 * @version $Id: PayOperateServiceClient.java, v 0.1 21 Feb 2016 11:43:13 guangdong.li Exp $
 */
public interface PayOperateServiceClient {

    /**
     * ����֧��������֧�����(�ݵ�֧��)
     * 
     * @param payRequest        ����֧������ģ��
     *       <pre>
     *         <li>userId              �û�ID(�ǿ�)<li>
     *         <li>voucherType         ƾ֤����(�ǿ�)<li>
     *         <li>voucherNo           ƾ֤��(�ǿ�)<li>
     *         <li>amount              ���׽��(�ǿ�)<li>
     *       </pre>
     * @return
     *      <pre>
     *         <li>success             �ɹ�ʧ�ܱ�ʶ</li>
     *         <li>code                �����</li>
     *         <li>timeout             �Ƿ�ʱ��ʶ</li>
     *         <li>message             �������</li>
     *      </pre>
     */
    @DigestLogAnnotated(serviceType = ServiceTypeEnum.SAL, bizKey = "")
    public BaseResult payApply(PayRequest payRequest);

    /**
     * ȷ��֧�����(�ݵ�֧��)
     *  ֧���ɹ�:������Ტת��
     *  ֧��ʧ��:�������
     *  
     * @param payRequest        ����֧������ģ��
     *       <pre>
     *         <li>userId              �û�ID(�ǿ�)<li>
     *         <li>voucherType         ƾ֤����(�ǿ�)<li>
     *         <li>voucherNo           ƾ֤��(�ǿ�)<li>
     *         <li>amount              ���׽��(�ǿ�)<li>
     *       </pre>
     * @param isSuccess         �ɹ����: true-�ɹ�   false-ʧ��
     * @return
     *      <pre>
     *         <li>success             �ɹ�ʧ�ܱ�ʶ</li>
     *         <li>timeout             �Ƿ�ʱ��ʶ</li>
     *         <li>code                �����</li>
     *         <li>message             �������</li>
     *      </pre>
     */
    @DigestLogAnnotated(serviceType = ServiceTypeEnum.SAL, bizKey = "")
    public BaseResult finishPay(PayRequest payRequest, boolean isSuccess);

}
