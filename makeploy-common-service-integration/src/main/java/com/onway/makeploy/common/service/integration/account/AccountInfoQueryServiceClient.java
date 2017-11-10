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
 * 账务中心账户信息查询服务门面
 * 
 * @author guangdong.li
 * @version $Id: AccountInfoQueryServiceClient.java, v 0.1 21 Feb 2016 11:37:49 guangdong.li Exp $
 */
public interface AccountInfoQueryServiceClient {

    /**
     * 账户余额查询
     * 
     * @param userId 用户ID（必填）
     * @return  业务处理结果集  
     *      <pre>
     *         <li>info                 用户账户信息（含账户号、余额、可用余额、可提现余额）</li>
     *      </pre>
     */
    @DigestLogAnnotated(serviceType = ServiceTypeEnum.SAL, bizKey = "")
    public UserAccountInfo queryUserAccountInfo(String userId);

    /**
     * 查询所有转账记录，分页查询
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
    @DigestLogAnnotated(serviceType = ServiceTypeEnum.SAL, bizKey = "查询所有转账记录")
    public PageQueryResult<TransferAccountInfo> queryAllTransferAccountInfo(String userId,
                                                                            QueryBase query);

    /**
     * 根据资金流向查询转账记录，分页查询
     * 
     * @param userId 用户ID（必填）   
     * @param transDirection 资金流向（必填）
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
    public PageQueryResult<TransferAccountInfo> queryTransferAccountInfo(String userId,
                                                                         TransDirectionEnum transDirection,
                                                                         QueryBase query);

    /**
     * 获取用户历史的红包总金额
     * 圣诞活动专用（弃用）
     * @param userId userId 用户ID（必填）   
     * @return
     */
    @Deprecated
    @DigestLogAnnotated(serviceType = ServiceTypeEnum.SAL, bizKey = "")
    public BigDecimal queryUserBonus(String userId);

}
