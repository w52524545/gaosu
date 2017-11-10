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
 * 沟通网关服务客户端
 * 
 * @author guangdong.li
 * @version $Id: GotoneServiceClient.java, v 0.1 17 Feb 2016 18:18:25 guangdong.li Exp $
 */
public interface GotoneServiceClient {
    /**
     * 查看验证码
     * 
     * @param businessCode            手机内容
     * @param phone              手机号
     * @return
     *  <pre>
     *      <li>success             调用标识 false:有异常，调用失败  true:调用成功 </li>
     *      <li>code                结果码</li>
     *      <li>message             结果描述</li>
     *      <li>resultObj           业务成功失败标识 false:未开户，true：已开户</li>
     *  </pre>
     */
    @DigestLogAnnotated(serviceType = ServiceTypeEnum.SAL, bizKey = "查看验证码")
    public String getVerifyCode(String businessCode, String phone, PlatformCodeEnum platformCode);

    /**
     * 获取未读消息数
     * @param appType            app类型
     * @param userId             用户编号
     */
    @DigestLogAnnotated(serviceType = ServiceTypeEnum.SAL, bizKey = "获取未读消息数")
    public int getNewUnReadMsgCount(PlatformCodeEnum platformCode, String userId);

    /**
     * 获取用户消息中心列表
     * @param query              查询条件 
     *          <pre>
     *              <li>pageNum     页面数</li>
     *              <li>pageSize    页面大小</li>
     *          </pre>
     * @param appType            app类型
     * @param userId             用户编号
     * @param isRead             读取状态，如果为null则为全部数据
     * @return
     *  <pre>
     *      <li>success             调用标识 false:有异常，调用失败  true:调用成功 </li>
     *      <li>code                结果码</li>
     *      <li>message             结果描述</li>
     *      <li>totalItems          查询到的结果总数 </li>
     *      <li>totalPages          总页数 </li>
     *      <li>currentPage         当前页码</li>
     *      <li>itemsPerPage        每页条数 </li>
     *      <li>resultList          MsgInfo列表</li>
     *  </pre>
     */
    @DigestLogAnnotated(serviceType = ServiceTypeEnum.SAL, bizKey = "获取用户消息中心列表")
    public PageList<MsgInfo> getUserMsgList(QueryBase query, PlatformCodeEnum platformCode,
                                            String userId, Boolean isRead);

    /**
     * 修改已读状态
     * 
     * @param msgId 消息编号
     * @return
     *  <pre>
     *      <li>success             调用标识 false:有异常，调用失败  true:调用成功 </li>
     *      <li>code                结果码</li>
     *      <li>message             结果描述</li>
     *  </pre>
     */
    @DigestLogAnnotated(serviceType = ServiceTypeEnum.SAL, bizKey = "修改已读状态")
    public BaseResult readMsg(int msgId);

    /**
     * 用户所有消息已读
     * 
     * @param userId 用户编号
     * @return
     *  <pre>
     *      <li>success             调用标识 false:有异常，调用失败  true:调用成功 </li>
     *      <li>code                结果码</li>
     *      <li>message             结果描述</li>
     *  </pre>
     */
    @DigestLogAnnotated(serviceType = ServiceTypeEnum.SAL, bizKey = "修改已读状态")
    public BaseResult readUserAllMsg(String userId);

    /**
     * 查询消息详情
     * 
     * @param msgId 消息编号
     * @return
     */
    @DigestLogAnnotated(serviceType = ServiceTypeEnum.SAL, bizKey = "查询消息详情")
    public MsgInfo getUserMsg(int msgId);

    /**
     * 查询Push消息
     * 
     * @param userId 用户编号
     * @return
     */
    @DigestLogAnnotated(serviceType = ServiceTypeEnum.SAL, bizKey = "查询Push消息")
    public PushInfo getNotSendPush(String userId);

    /**
     * 查看手机号最新业务类型的短信（主要用作判断验证码）
     * 
     * @param businessCode            手机内容
     * @param phone              手机号
     * @return
     *  <pre>
     *      <li>success             调用标识 false:有异常，调用失败  true:调用成功 </li>
     *      <li>code                结果码</li>
     *      <li>message             结果描述</li>
     *      <li>resultObj           业务成功失败标识 false:未开户，true：已开户</li>
     *  </pre>
     */
    @DigestLogAnnotated(serviceType = ServiceTypeEnum.SAL, bizKey = "查看手机号最新业务类型的短信")
    public SmsInfo getNewestSmsInfo(String businessCode, String phone, PlatformCodeEnum platformCode);

    /**
     * 是否为发布环境
     * 
     * @param configName
     * @return
     */
    @DigestLogAnnotated(serviceType = ServiceTypeEnum.SAL, bizKey = "是否为发布环境")
    public Boolean isProd();

}
