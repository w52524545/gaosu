/**
 * onway.com Inc.
 * Copyright (c) 2016-2016 All Rights Reserved.
 */
package com.onway.web.controller.msg;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.onway.common.lang.StringUtils;
import com.onway.makeploy.core.service.GtPhoneSmsInfoComponent;
import com.onway.platform.common.enums.PlatformCodeEnum;
import com.onway.web.controller.AbstractController;
import com.onway.web.controller.result.JsonResult;

/**
 * 短信消息类json
 * 
 * @author guangdong.li
 * @version $Id: GoneOperateController.java, v 0.1 17 Feb 2016 16:37:20 guangdong.li Exp $
 */
@Controller
public class GoneOperateController extends AbstractController {

    private static final Logger        log = Logger.getLogger(GoneOperateController.class);

    
    @Resource
    GtPhoneSmsInfoComponent gtPhoneSmsInfoComponent;

    private static final class TimerLock {

        private ConcurrentHashMap<String, AtomicInteger> visitTable = new ConcurrentHashMap<String, AtomicInteger>();

        private volatile long                            timer;

        public synchronized boolean isOverflow(String phone) {

            long now = System.currentTimeMillis();
            if (now > timer) { //超时
                visitTable.clear();
                timer = now + (8 * 60 * 60 * 1000); //8小时
                return false;
            }
            AtomicInteger count = visitTable.get(phone);
            if (count == null) {
                return false;
            }
            if (count.intValue() > 5) {
                return true;
            }
            return false;
        }

        @SuppressWarnings("unused")
        public synchronized void increase(String phone) {
            AtomicInteger count = visitTable.get(phone);
            if (count == null) {
                visitTable.put(phone, new AtomicInteger(1));
            } else {
                count.incrementAndGet();
                visitTable.put(phone, count);
            }

        }
    }

    private TimerLock timerLock = new TimerLock();

    /**
     * 发送手机验证
     * 
     * v 1.0 ;v 2.0
     * 
     * @param request
     * @param modelMap
     * @return
     * @throws Exception
     */
    @RequestMapping("/smsverifysend.do")
    @ResponseBody
    public Object smsSend(HttpServletRequest request, ModelMap modelMap) {
 
        try {

            String phone = request.getParameter("phone");

            if (StringUtils.isBlank(phone)) {
                return new JsonResult(false, "", "请正确输入手机号码");
            }

            if (!phone.matches("^[1][2-9]\\d{9}$")) {
                return new JsonResult(false, "", "请正确输入手机号码");
            }

            if (timerLock.isOverflow(phone)) {
                log.warn("短信发送请求过于频繁， 号码：" + phone);
                return new JsonResult(false, "", "您操作得太快了，歇一会吧");
            }
            // String type = request.getParameter("type");

            phone = phone.trim();
            try {
                gtPhoneSmsInfoComponent.sendVerifyCode("LOGIN_VERIFY_CODE", phone, PlatformCodeEnum.CALL_PLATFORM);
            } catch (Exception e) {
                throw e;
            }
            return new JsonResult(true, "", "");
        } catch (Exception e) {
            log.error("手机验证码短信保存异常：", e);
            return null;
        }
    }
    
    /*
     * 校验手机号
     */
        @RequestMapping("/validatePhone.do")
        @ResponseBody
        public Object validatePhone(HttpServletRequest request, ModelMap modelMap) {
            JsonResult result=new JsonResult(false, "", "");
            String userId = (String) request.getSession().getAttribute("userId");
//          String userId="1120161102000020";
            try {
                String phone = request.getParameter("phone");
                String code = request.getParameter("code");

                if (StringUtils.isBlank(phone)) {
                   return new JsonResult(false, "", "请正确输入手机号码");
                }
                
                if (StringUtils.isBlank(code)) {
                    return new JsonResult(false, "", "请正确输入验证码");
                }
                
                if (!phone.matches("^[1][2-9]\\d{9}$")) {
                    return new JsonResult(false, "", "请正确输入手机号码");
                }

                phone = phone.trim();
                code=code.trim();
                  boolean checkResult= gtPhoneSmsInfoComponent.checkVerifyCode("LOGIN_VERIFY_CODE", phone,code, PlatformCodeEnum.MARRY_PLATFORM);
                 //验证成功更新用户表手机号
                   if(checkResult){

                       result.setBizSucc(true);
                   }else{
                       result.setErrMsg("验证码错误，请重新校验！");
                   }
                
            } catch (Exception e) {
                     log.error("手机验证码短信保存异常：", e);
                
            }
            return result;
        }
}
