///**
// * onway.com Inc.
// * Copyright (c) 2016-2016 All Rights Reserved.
// */
//package com.onway.web.controller.msg;
//
//import java.util.Iterator;
//import java.util.List;
//import java.util.Map;
//import java.util.Set;
//import java.util.UUID;
//
//import javax.annotation.Resource;
//import javax.servlet.http.HttpServletRequest;
//
//import org.springframework.stereotype.Controller;
//import org.springframework.ui.ModelMap;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.ResponseBody;
//
//import com.onway.common.event.gotone.MsgEvent;
//import com.onway.common.event.gotone.SmsEvent;
//import com.onway.common.lang.StringUtils;
//import com.onway.gotone.common.service.publisher.MsgUniformEventSender;
//import com.onway.gotone.common.service.publisher.SmsUniformEventSender;
//import com.onway.platform.common.base.QueryResult;
//import com.onway.platform.common.enums.PlatformCodeEnum;
//import com.onway.platform.common.utils.LogUtil;
//import com.onway.web.controller.AbstractController;
//
///**
// * 站内信和短信
// * @author yuanlin.wang
// * @version $Id: MsgController.java, v 0.1 2016/7/29 11:16 junjie.lin Exp $
// */
//@Controller
//public class MsgAndSmsController extends AbstractController {
//
//    //站内信发送
//    @Resource
//    private MsgUniformEventSender msgUniformEventSender;
//
//    //短信发送
//    @Resource
//    private SmsUniformEventSender smsUniformEventSender;
//    
//    @Resource
//    private IphoneDAO iphoneDAO;
//    
//    
//    @RequestMapping("/userSendMsg.do")
//    @ResponseBody
//	public void smsSend(HttpServletRequest request, ModelMap modelMap) {
//
//		List<IphoneDO> lst = iphoneDAO.getIphoneList();
//		for (int i = 0; i < lst.size(); i++) {
//			
//			String phone = lst.get(i).getCell();
//			if (StringUtils.isBlank(phone)) {
//				continue;
//			}
//
//			if (!phone.matches("^[1][2-9]\\d{9}$")) {
//				continue;
//			}
//
//			// String type = request.getParameter("type");
//			phone = phone.trim();
//			sendSms(lst.get(i).getCell(),
//					MsgTemplateEnum.makeploy_MSG_USER_REGISTER_NOTICE, null);
//		}
//
//	}
//
//    /**
//     * 发送站内信
//     * @param userId 接收的用户userId
//     * @param msgTemplate 站内信模板
//     * @param params      变量
//     * @return
//     */
//    public boolean sendMsg(String userId,MsgTemplateEnum msgTemplate,Map<String,String> params){
//
//        //流水号
//        String serilNo = UUID.randomUUID().toString();
//        //创建事件
//        MsgEvent msgEvent = new MsgEvent(PlatformCodeEnum.makeploy_PLATFORM,
//                serilNo,
//                msgTemplate.getCode(),
//                userId);
//        //是否设置变量
//        if(params != null && !params.isEmpty()){
//            Set<Map.Entry<String,String>> set = params.entrySet();
//            Iterator<Map.Entry<String,String>> it = set.iterator();
//            Map.Entry<String,String> entry = null;
//            while(it.hasNext()){
//                entry = it.next();
//                msgEvent.setParam(entry.getKey(),entry.getValue());
//            }
//        }
//        //是否立即发送
//        msgEvent.setSendNow(true);
//        //发送事件
//        QueryResult<String> queryResult = msgUniformEventSender.sendEvent(msgEvent);
//        //记录日志
//        LogUtil.info(logger, queryResult);
//        //发送成功
//        if(queryResult != null && queryResult.getResultObject() != null &&
//                queryResult.isSuccess()){
//            return true;
//        }
//        return false;
//    }
//
//    /**
//     * 发送短信
//     * @param phone        手机号
//     * @param msgTemplate  模板
//     * @param params       参数
//     * @return
//     */
//    public boolean sendSms(String phone,MsgTemplateEnum msgTemplate,Map<String,String> params){
//
//        //流水号
//        String serilNo = UUID.randomUUID().toString();
//        //创建事件
//        SmsEvent smsEvent = new SmsEvent(PlatformCodeEnum.makeploy_PLATFORM,
//                serilNo,
//                msgTemplate.getCode(),
//                phone);
//        //是否设置变量
//        if(params != null && !params.isEmpty()){
//            Set<Map.Entry<String,String>> set = params.entrySet();
//            Iterator<Map.Entry<String,String>> it = set.iterator();
//            Map.Entry<String,String> entry = null;
//            while(it.hasNext()){
//                entry = it.next();
//                smsEvent.setParam(entry.getKey(),entry.getValue());
//            }
//        }
//
//        //是否立即发送
//        smsEvent.setSendNow(true);
//        //发送事件
//        QueryResult<String> queryResult = smsUniformEventSender.sendEvent(smsEvent);
//        //记录日志
//        LogUtil.info(logger, queryResult);
//        //发送成功
//        if(queryResult != null && queryResult.getResultObject() != null &&
//                queryResult.isSuccess()){
//            return true;
//        }
//        return false;
//    }
//
//}
