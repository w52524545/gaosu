/**
 * onway.com Inc.
 * Copyright (c) 2013-2015 All Rights Reserved.
 */
package com.onway.makeploy.core.service.code;

import org.apache.log4j.Logger;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallback;
import org.springframework.transaction.support.TransactionTemplate;

import com.onway.makeploy.common.dal.daointerface.SequenceDAO;
import com.onway.makeploy.common.dal.dataobject.SequenceDO;
import com.onway.makeploy.common.service.enums.ErrorCodeEnum;
import com.onway.makeploy.common.service.exception.ErrorException;
import com.onway.platform.common.enums.PlatformCodeEnum;

/**
 * �������������ʵ��
 * 
 * @author guangdong.li
 * @version $Id: CodeGenerateComponentImpl.java, v 0.1 2015��11��2�� ����4:51:43 guangdong.li Exp $
 */
public class CodeGenerateComponentImpl implements CodeGenerateComponent {

    /** logger */
    protected static final Logger logger = Logger.getLogger(CodeGenerateComponentImpl.class);

    /** ���к�DAO */
    private SequenceDAO           sequenceDAO;

    /** ����ģ�� */
    private TransactionTemplate   localNewTransTransactionTemplate;

    /**
     * @see com.onway.settlecore.core.service.code.CodeGenerateComponent#nextCodeByType(com.onway.settlecore.common.service.enums.PlatformCodeEnum)
     */
    @Override
    public String nextCodeByType(final PlatformCodeEnum platformCode) {

        int sequenceNo = nextCode(platformCode.name());

        if (sequenceNo <= 0) {
            throw new ErrorException(ErrorCodeEnum.DATA_ERROR, "�������в�����:" + platformCode.name());
        }
        return CodeBuilder.getCodeBuilder(platformCode).getCode(sequenceNo);
    }

    /**
     * @see com.onway.settlecore.core.service.code.CodeGenerateComponent#nextCode(java.lang.String)
     */
    public int nextCode(final String bizType) {
        int sequenceNo = localNewTransTransactionTemplate
            .execute(new TransactionCallback<Integer>() {

                @Override
                public Integer doInTransaction(TransactionStatus status) {
                    int count = sequenceDAO.nextSequence(bizType);
                    if (count == 0) { //��һ��,�����ݳ�ʼ��
                        SequenceDO sequenceDO = new SequenceDO();
                        sequenceDO.setBizName(bizType);
                        sequenceDO.setCurrentValue(CodeGenerateConfig.INIT_VALUE);
                        sequenceDO.setIncrementValue(CodeGenerateConfig.INCREMENT_STEP);
                        sequenceDO.setPlatformCode(bizType);
                        sequenceDAO.insert(sequenceDO);
                    } else if (count > 1) {
                        logger.warn("ҵ�����к������쳣�������ظ���¼,bizKey:" + bizType);
                        throw new ErrorException(ErrorCodeEnum.DATA_ERROR);
                    }
                    SequenceDO sequence = sequenceDAO.currentSequence(bizType);
                    return sequence.getCurrentValue();
                }
            });

        return sequenceNo;
    }

    /**
     * Setter method for property <tt>sequenceDAO</tt>.
     * 
     * @param sequenceDAO value to be assigned to property sequenceDAO
     */
    public void setSequenceDAO(SequenceDAO sequenceDAO) {
        this.sequenceDAO = sequenceDAO;
    }

    /**
     * Setter method for property <tt>localNewTransTransactionTemplate</tt>.
     * 
     * @param localNewTransTransactionTemplate value to be assigned to property localNewTransTransactionTemplate
     */
    public void setLocalNewTransTransactionTemplate(TransactionTemplate localNewTransTransactionTemplate) {
        this.localNewTransTransactionTemplate = localNewTransTransactionTemplate;
    }

}
