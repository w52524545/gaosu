//package com.onway.makeploy.core.service;
//
//import java.net.URLEncoder;
//import java.text.MessageFormat;
//import java.util.HashMap;
//import java.util.Map;
//import java.util.Random;
//
//import javax.annotation.Resource;
//
//import org.apache.log4j.Logger;
//import org.springframework.stereotype.Component;
//
//import com.onway.common.lang.HttpUtils;
//import com.onway.common.lang.StringUtils;
//import com.onway.makeploy.common.dal.daointerface.SmDAO;
//import com.onway.makeploy.common.dal.dataobject.SmDO;
//import com.onway.platform.common.configration.ConfigrationFactory;
//
//@Component("yimeiSmsSender")
//public class SmsSender {
//    
//    
//    @Resource
//    private  SmDAO smDAO;
//    
//    
//    
//    private static final Logger logger = Logger.getLogger(SmsSender.class);
//
//    private final static String sn   = ConfigrationFactory.getConfigration().getPropertyValue(
//                                           "dl.account");
//
//    private final static String PASSWD = ConfigrationFactory.getConfigration().getPropertyValue(
//                                           "dl.pswd");
//
//    private final static String URL    = ConfigrationFactory.getConfigration().getPropertyValue(
//                                           "dl.url");
//
//    private final static String key    = ConfigrationFactory.getConfigration().getPropertyValue(
//                                           "dl.appkey");
//    
//    
//    
//    
//    
//    
//    public  boolean  send(String cell){
//        
//        String content="";
//        try {
//            
//            String verifyCode=getVerifyCode(6);
//            //发送内容
//           content = "您的验证码为："+verifyCode+"，请勿泄露";
//           
//            Map<String, String> paramsMap = new HashMap<String, String>();
//            paramsMap.put("username", sn);
//            paramsMap.put("password", PASSWD);
//            paramsMap.put("apikey", key);
//            paramsMap.put("mobile", cell);
//            paramsMap.put("content", URLEncoder.encode(content, "UTF-8"));
//            paramsMap.put("encode", "UTF-8");
//            
//            logger.info("发送内容：" + paramsMap.toString());
//
//            //转换返回值
//            String returnStr = HttpUtils.executePostMethod(URL, "UTF-8", paramsMap);
//
//            // 返回结果为‘0，20140009090990,1，提交成功’ 发送成功   具体见说明文档
//            logger.info(returnStr); //成功返回Success 失败返回：Faild
//            // 返回发送结果
//            if (StringUtils.isBlank(returnStr)) {
//                return false;
//            }
//
//            if (StringUtils.contains(returnStr, "success")) {
//                
//                SmDO smDO=new SmDO();
//                smDO.setCell(cell);
//                smDO.setSmsStatus("SUCCESS");
//                smDO.setVerifyCode(verifyCode);
//                smDO.setMemo(content);
//                smDAO.insert(smDO);
//                return true;
//            }
//
//            return false;
//
//        } catch (Exception e) {
//            logger.error(MessageFormat.format("短信发送失败,导流信息通道 phone:{0},content:{1}", new Object[] {
//                    cell, content}));
//            return false;
//        }
//        
//    }
//    
//    
//    /*
//     * 用户定制化短信内容
//     */
//  public  boolean  sendMsg(String cell){
//        
//        String content="";
//        try {
//            
//            //发送内容
//           content = "【伴郎伴娘】您好，我们收到您应聘的简历，请搜索添加微信公众号”伴郎伴娘服务“点击”我要兼职“，申请上线接单，我们会根据个人情况安排工作。客服微信：13012985447！~~非你莫属文化传媒有限公司";
//
//            Map<String, String> paramsMap = new HashMap<String, String>();
//            paramsMap.put("username", sn);
//            paramsMap.put("password", PASSWD);
//            paramsMap.put("apikey", key);
//            paramsMap.put("mobile", cell);
//            paramsMap.put("content", URLEncoder.encode(content, "UTF-8"));
//            paramsMap.put("encode", "UTF-8");
//            
//            logger.info("发送内容：" + paramsMap.toString());
//
//            //转换返回值
//            String returnStr = HttpUtils.executePostMethod(URL, "UTF-8", paramsMap);
//
//            // 返回结果为‘0，20140009090990,1，提交成功’ 发送成功   具体见说明文档
//            logger.info(returnStr); //成功返回Success 失败返回：Faild
//            // 返回发送结果
//            if (StringUtils.isBlank(returnStr)) {
//                return false;
//            }
//
//            if (StringUtils.contains(returnStr, "success")) {
//                
//                SmDO smDO=new SmDO();
//                smDO.setCell(cell);
//                smDO.setSmsStatus("SUCCESS");
//                smDO.setMemo(content);
//                smDAO.insert(smDO);
//                return true;
//            }
//
//            return false;
//
//        } catch (Exception e) {
//            logger.error(MessageFormat.format("短信发送失败,导流信息通道 phone:{0},content:{1}", new Object[] {
//                    cell, content}));
//            return false;
//        }
//        
//    }
//    
//    
//    
//    public  String getVerifyCode(int length) {
//        if (length < 1 || length > 10) {
//            return "";
//        }
//        StringBuffer sb = new StringBuffer();
//        for (int i = 1; i <= length; i++) {
//            int rand = new Random().nextInt(10);
//            sb.append(rand);
//        }
//        return sb.toString();
//    }
//
//}
