/**
 * onway.com Inc.
 * Copyright (c) 2016-2016 All Rights Reserved.
 */
package com.onway.makeploy.common.service.integration.gotone;


import com.onway.gotone.common.service.model.MsgInfo;
import com.onway.gotone.common.service.model.PushInfo;
import com.onway.gotone.common.service.model.SmsInfo;
import com.onway.platform.common.base.BaseResult;
import com.onway.platform.common.base.QueryBase;
import com.onway.platform.common.enums.PlatformCodeEnum;
import com.onway.platform.common.log.digest.DigestLogAnnotated;
import com.onway.platform.common.log.digest.ServiceTypeEnum;
import com.onway.platform.common.page.PageList;

/**
 * ��ͨ���ط���ͻ���
 * 
 * @author guangdong.li
 * @version $Id: GotoneServiceClient.java, v 0.1 17 Feb 2016 18:18:25 guangdong.li Exp $
 */
public interface GotoneServiceClient {
    /**
     * �鿴��֤��
     * 
     * @param businessCode            �ֻ�����
     * @param phone              �ֻ���
     * @return
     *  <pre>
     *      <li>success             ���ñ�ʶ false:���쳣������ʧ��  true:���óɹ� </li>
     *      <li>code                �����</li>
     *      <li>message             �������</li>
     *      <li>resultObj           ҵ��ɹ�ʧ�ܱ�ʶ false:δ������true���ѿ���</li>
     *  </pre>
     */
    @DigestLogAnnotated(serviceType = ServiceTypeEnum.SAL, bizKey = "�鿴��֤��")
    public String getVerifyCode(String businessCode, String phone, PlatformCodeEnum platformCode);

    /**
     * ��ȡδ����Ϣ��
     * @param appType            app����
     * @param userId             �û����
     */
    @DigestLogAnnotated(serviceType = ServiceTypeEnum.SAL, bizKey = "��ȡδ����Ϣ��")
    public int getNewUnReadMsgCount(PlatformCodeEnum platformCode, String userId);

    /**
     * ��ȡ�û���Ϣ�����б�
     * @param query              ��ѯ���� 
     *          <pre>
     *              <li>pageNum     ҳ����</li>
     *              <li>pageSize    ҳ���С</li>
     *          </pre>
     * @param appType            app����
     * @param userId             �û����
     * @param isRead             ��ȡ״̬�����Ϊnull��Ϊȫ������
     * @return
     *  <pre>
     *      <li>success             ���ñ�ʶ false:���쳣������ʧ��  true:���óɹ� </li>
     *      <li>code                �����</li>
     *      <li>message             �������</li>
     *      <li>totalItems          ��ѯ���Ľ������ </li>
     *      <li>totalPages          ��ҳ�� </li>
     *      <li>currentPage         ��ǰҳ��</li>
     *      <li>itemsPerPage        ÿҳ���� </li>
     *      <li>resultList          MsgInfo�б�</li>
     *  </pre>
     */
    @DigestLogAnnotated(serviceType = ServiceTypeEnum.SAL, bizKey = "��ȡ�û���Ϣ�����б�")
    public PageList<MsgInfo> getUserMsgList(QueryBase query, PlatformCodeEnum platformCode,
                                            String userId, Boolean isRead);

    /**
     * �޸��Ѷ�״̬
     * 
     * @param msgId ��Ϣ���
     * @return
     *  <pre>
     *      <li>success             ���ñ�ʶ false:���쳣������ʧ��  true:���óɹ� </li>
     *      <li>code                �����</li>
     *      <li>message             �������</li>
     *  </pre>
     */
    @DigestLogAnnotated(serviceType = ServiceTypeEnum.SAL, bizKey = "�޸��Ѷ�״̬")
    public BaseResult readMsg(int msgId);

    /**
     * �û�������Ϣ�Ѷ�
     * 
     * @param userId �û����
     * @return
     *  <pre>
     *      <li>success             ���ñ�ʶ false:���쳣������ʧ��  true:���óɹ� </li>
     *      <li>code                �����</li>
     *      <li>message             �������</li>
     *  </pre>
     */
    @DigestLogAnnotated(serviceType = ServiceTypeEnum.SAL, bizKey = "�޸��Ѷ�״̬")
    public BaseResult readUserAllMsg(String userId);

    /**
     * ��ѯ��Ϣ����
     * 
     * @param msgId ��Ϣ���
     * @return
     */
    @DigestLogAnnotated(serviceType = ServiceTypeEnum.SAL, bizKey = "��ѯ��Ϣ����")
    public MsgInfo getUserMsg(int msgId);

    /**
     * ��ѯPush��Ϣ
     * 
     * @param userId �û����
     * @return
     */
    @DigestLogAnnotated(serviceType = ServiceTypeEnum.SAL, bizKey = "��ѯPush��Ϣ")
    public PushInfo getNotSendPush(String userId);

    /**
     * �鿴�ֻ�������ҵ�����͵Ķ��ţ���Ҫ�����ж���֤�룩
     * 
     * @param businessCode            �ֻ�����
     * @param phone              �ֻ���
     * @return
     *  <pre>
     *      <li>success             ���ñ�ʶ false:���쳣������ʧ��  true:���óɹ� </li>
     *      <li>code                �����</li>
     *      <li>message             �������</li>
     *      <li>resultObj           ҵ��ɹ�ʧ�ܱ�ʶ false:δ������true���ѿ���</li>
     *  </pre>
     */
    @DigestLogAnnotated(serviceType = ServiceTypeEnum.SAL, bizKey = "�鿴�ֻ�������ҵ�����͵Ķ���")
    public SmsInfo getNewestSmsInfo(String businessCode, String phone, PlatformCodeEnum platformCode);

    /**
     * �Ƿ�Ϊ��������
     * 
     * @param configName
     * @return
     */
    @DigestLogAnnotated(serviceType = ServiceTypeEnum.SAL, bizKey = "�Ƿ�Ϊ��������")
    public Boolean isProd();

}
