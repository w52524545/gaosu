package com.onway.web.controller.mng;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

import com.onway.common.lang.HttpUtils;

/**
 * ��������ͨ��
 * 
 * @author li.hong
 * @version $Id: ChuanglanSmsSender.java, v 0.1 2016��10��11�� ����10:39:52 li.hong Exp $
 */
public class ChuanglanSmsSender {

    public static void main(String[] args) {
        try {
            //��������
            String phone = "15706805739";
            Map<String, String> paramsMap = new HashMap<String, String>();
            paramsMap.put("account", "N3210101");
            paramsMap.put("pswd", "OnUxhJV4Brb47e");
            paramsMap.put("mobile", phone);
            paramsMap.put("msg", "���㽭ŷҲ���硿�ɷѳɹ���");
            paramsMap.put("needstatus", "false");
            System.out.println("�������ݣ�" + paramsMap.toString());

            //ת������ֵ
            String returnStr = HttpUtils.executePostMethod(
                "http://sms.253.com/msg/HttpBatchSendSM?", "UTF-8", paramsMap);

            System.out.println(returnStr);
            String[] strs = returnStr.split("\n");

            String value = "";
            if (StringUtils.isNotBlank(strs[0])) {
                value = strs[0].split(",")[1];
            } else {

            }
            System.out.println(value);

            if (!StringUtils.equals(value, "0")) {
                System.out.println("���ŷ���ʧ�ܣ�" + phone);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        /*System.out.println(Md5Encrypt.toMD5High("321321"));*/
    }

}
