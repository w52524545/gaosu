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
 * 账务中心提现服务门面
 * 
 * @author guangdong.li
 * @version $Id: WithdrawOperateServiceClient.java, v 0.1 21 Feb 2016 11:46:47 guangdong.li Exp $
 */
public interface WithdrawOperateServiceClient {

    /**
     * 提现申请
     * 
     * @param userId  用户ID（必填）           
     * @param withdraw 提现信息
     *      <pre>
     *          <li>withdrawNo   提现号（可为空）</li>
     *          <li>status       提现状态（可为空）</li>
     *          <li>amount       提现金额（必填）</li>
     *          <li>chargeFee    提现费用（必填,默认:0）</li>
     *      </pre>
     * 
     * @param bankCard 银行卡信息
     *      <pre>
     *          <li>bankCardNo          银行卡号（必填）</li>
     *          <li>bankCardType        银行卡类别（必填）</li>
     *          <li>bankAccountName     银行卡账户名（必填）</li>
     *          <li>bankNo              银行号（必填）</li>
     *          <li>bankName            银行名称（必填）</li>
     *          <li>provinceCode        省编码（可为空）</li>
     *          <li>cityCode            市编码（可为空）</li>
     *      </pre>
     * 
     * @return  业务处理结果集  
     *      <pre>
     *         <li>success              成功失败标识</li>
     *         <li>code                 结果码</li>
     *         <li>message              结果描述</li>
     *         
     *         <li>orderNo              订单号</li>
     *      </pre>
     */
    @DigestLogAnnotated(serviceType = ServiceTypeEnum.SAL, bizKey = "")
    public WithdrawOperateResult withdrawApply(String userId, WithdrawInfo withdraw,
                                               BankCardInfo bankCard);

    /**
     * 提现审核(幂等支持)
     * 
     * @param withdrawNo 提现号（必填）
     * @param auditStatus 审核状态（必填）
     * @param operatorInfo 操作人信息
     *      <pre>
     *          <li>operatorId      操作人ID（可为空）</li>
     *          <li>operatorName    操作人名称（必填）</li>
     *          <li>memo            操作备注（必填）</li>
     *      </pre>
     * 
     * @return  业务处理结果集  
     *      <pre>
     *         <li>success              成功失败标识</li>
     *         <li>code                 结果码</li>
     *         <li>message              结果描述</li>
     *      </pre>
     */
    @DigestLogAnnotated(serviceType = ServiceTypeEnum.SAL, bizKey = "")
    public BaseResult withdrawAudit(String withdrawNo, WithdrawStatusEnum auditStatus,
                                    OperatorInfo operatorInfo);

    /**
     * 提现转账(幂等支持)
     * 
     * @param withdrawNo 提现号（必填）
     * @param isSuccess  提现是否成功（必填）
     * @param operatorInfo 操作员信息
     * @return  业务处理结果集  
     *      <pre>
     *         <li>success              成功失败标识</li>
     *         <li>code                 结果码</li>
     *         <li>message              结果描述</li>
     *      </pre>
     */
    @DigestLogAnnotated(serviceType = ServiceTypeEnum.SAL, bizKey = "")
    public BaseResult transferAccounts(String withdrawNo, Boolean isSuccess,
                                       OperatorInfo operatorInfo);

    /**
     * 提现记录查询，分页查询
     * 
     * @param userId 用户ID（必填）   
     * @param query  查询对象模型
     * 
     * @return  业务处理结果集  
     *      <pre>
     *         <li>success              成功失败标识</li>
     *         <li>code                 结果码</li>
     *         <li>message              结果描述</li>
     *         
     *         <li>totalItems           查询到的结果总数</li>
     *         <li>totalPages           总页数</li>
     *         <li>currentPage          当前页码</li>
     *         <li>itemsPerPage         每页条数</li>
     *         <li>resultList           分页结果集</li>
     *      </pre>
     */
    @DigestLogAnnotated(serviceType = ServiceTypeEnum.SAL, bizKey = "")
    public PageQueryResult<WithdrawInfo> queryWithdrawInfo(String userId, QueryBase query);

    /**
     * 提现信息查询
     * 
     * @param withdrawNo 提现号（必填）
     * @return 提现信息
     */
    @DigestLogAnnotated(serviceType = ServiceTypeEnum.SAL, bizKey = "")
    public WithdrawInfo queryUserWithdrawInfo(String withdrawNo);

}
