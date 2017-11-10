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
// * վ���źͶ���
// * @author yuanlin.wang
// * @version $Id: MsgController.java, v 0.1 2016/7/29 11:16 junjie.lin Exp $
// */
//@Controller
//public class MsgAndSmsController extends AbstractController {
//
//    //վ���ŷ���
//    @Resource
//    private MsgUniformEventSender msgUniformEventSender;
//
//    //���ŷ���
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
//     * ����վ����
//     * @param userId ���յ��û�userId
//     * @param msgTemplate վ����ģ��
//     * @param params      ����
//     * @return
//     */
//    public boolean sendMsg(String userId,MsgTemplateEnum msgTemplate,Map<String,String> params){
//
//        //��ˮ��
//        String serilNo = UUID.randomUUID().toString();
//        //�����¼�
//        MsgEvent msgEvent = new MsgEvent(PlatformCodeEnum.makeploy_PLATFORM,
//                serilNo,
//                msgTemplate.getCode(),
//                userId);
//        //�Ƿ����ñ���
//        if(params != null && !params.isEmpty()){
//            Set<Map.Entry<String,String>> set = params.entrySet();
//            Iterator<Map.Entry<String,String>> it = set.iterator();
//            Map.Entry<String,String> entry = null;
//            while(it.hasNext()){
//                entry = it.next();
//                msgEvent.setParam(entry.getKey(),entry.getValue());
//            }
//        }
//        //�Ƿ���������
//        msgEvent.setSendNow(true);
//        //�����¼�
//        QueryResult<String> queryResult = msgUniformEventSender.sendEvent(msgEvent);
//        //��¼��־
//        LogUtil.info(logger, queryResult);
//        //���ͳɹ�
//        if(queryResult != null && queryResult.getResultObject() != null &&
//                queryResult.isSuccess()){
//            return true;
//        }
//        return false;
//    }
//
//    /**
//     * ���Ͷ���
//     * @param phone        �ֻ���
//     * @param msgTemplate  ģ��
//     * @param params       ����
//     * @return
//     */
//    public boolean sendSms(String phone,MsgTemplateEnum msgTemplate,Map<String,String> params){
//
//        //��ˮ��
//        String serilNo = UUID.randomUUID().toString();
//        //�����¼�
//        SmsEvent smsEvent = new SmsEvent(PlatformCodeEnum.makeploy_PLATFORM,
//                serilNo,
//                msgTemplate.getCode(),
//                phone);
//        //�Ƿ����ñ���
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
//        //�Ƿ���������
//        smsEvent.setSendNow(true);
//        //�����¼�
//        QueryResult<String> queryResult = smsUniformEventSender.sendEvent(smsEvent);
//        //��¼��־
//        LogUtil.info(logger, queryResult);
//        //���ͳɹ�
//        if(queryResult != null && queryResult.getResultObject() != null &&
//                queryResult.isSuccess()){
//            return true;
//        }
//        return false;
//    }
//
//}
