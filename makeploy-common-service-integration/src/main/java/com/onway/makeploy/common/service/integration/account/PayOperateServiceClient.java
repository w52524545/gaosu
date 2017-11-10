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
 * 账务中心余额支付服务门面
 * 
 * @author guangdong.li
 * @version $Id: PayOperateServiceClient.java, v 0.1 21 Feb 2016 11:43:13 guangdong.li Exp $
 */
public interface PayOperateServiceClient {

    /**
     * 申请支付并冻结支付金额(幂等支持)
     * 
     * @param payRequest        申请支付请求模型
     *       <pre>
     *         <li>userId              用户ID(非空)<li>
     *         <li>voucherType         凭证类型(非空)<li>
     *         <li>voucherNo           凭证号(非空)<li>
     *         <li>amount              交易金额(非空)<li>
     *       </pre>
     * @return
     *      <pre>
     *         <li>success             成功失败标识</li>
     *         <li>code                结果码</li>
     *         <li>timeout             是否超时标识</li>
     *         <li>message             结果描述</li>
     *      </pre>
     */
    @DigestLogAnnotated(serviceType = ServiceTypeEnum.SAL, bizKey = "")
    public BaseResult payApply(PayRequest payRequest);

    /**
     * 确认支付结果(幂等支持)
     *  支付成功:解除冻结并转账
     *  支付失败:解除冻结
     *  
     * @param payRequest        申请支付请求模型
     *       <pre>
     *         <li>userId              用户ID(非空)<li>
     *         <li>voucherType         凭证类型(非空)<li>
     *         <li>voucherNo           凭证号(非空)<li>
     *         <li>amount              交易金额(非空)<li>
     *       </pre>
     * @param isSuccess         成功标记: true-成功   false-失败
     * @return
     *      <pre>
     *         <li>success             成功失败标识</li>
     *         <li>timeout             是否超时标识</li>
     *         <li>code                结果码</li>
     *         <li>message             结果描述</li>
     *      </pre>
     */
    @DigestLogAnnotated(serviceType = ServiceTypeEnum.SAL, bizKey = "")
    public BaseResult finishPay(PayRequest payRequest, boolean isSuccess);

}
