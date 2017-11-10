package com.onway.cif.common.util;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

import org.apache.log4j.Logger;

import com.onway.platform.common.utils.VelocityHelper;

/**
 * ͨѶ���ĸ�����
 * 
 * @author guangdong.li
 * @version $Id: ParamsUtil.java, v 0.1 2014��10��20�� ����3:35:25 guangdong.li Exp $
 */
public class ParamsUtil {

    /** logger */
    private static final Logger logger = Logger.getLogger(ParamsUtil.class);

    /**
     * ���칫��������
     * 
     * @param sign
     * @param cer
     * @param pay
     * @param params
     * @param commTemplate
     * @return
     */
    public static String getCommonXml(String sign, String cer, String pay,
                                      Map<String, Object> params, String commTemplate) {
        params.put("sign", sign);
        params.put("cer", cer);
        params.put("requestPrams", pay);
        String xmlStr = VelocityHelper.getInstance().evaluate(params, commTemplate);
        logger.info("�����ױ��ģ�" + xmlStr.replaceAll("\r|\n", ""));
        return xmlStr.replaceAll("\r|\n", "");
    }

    /**
     * ��������ͷ��Ϣ
     * 
     * @param websvrName
     * @param websvrCode
     * @param appForm
     * @param keep
     * @param requestTime
     * @return
     */
    public static String getCtrlInfo(String websvrName, String websvrCode, String appForm,
                                     String keep, String requestTime) {

        StringBuffer sb = new StringBuffer();
        sb.append("<CTRL-INFO WEBSVRNAME=\"");
        sb.append(websvrName);
        sb.append("\" WEBSVRCODE=\"");
        sb.append(websvrCode);
        sb.append("\" APPFROM=\"");
        sb.append(appForm);
        sb.append("\" KEEP=\"");
        sb.append(keep);
        sb.append("\" REQUESTTIME=\"");
        sb.append(requestTime);
        sb.append("\"/>");
        //logger.info(sb.toString());
        return sb.toString();
    }

    /**
     * ������ˮ�ţ������ն˺�+��ǰʱ��+4λ�������
     * 
     * @param tmnnum
     * @return
     */
    public static String getkeep(String tmnNum) {
        DateFormat df = new SimpleDateFormat("yyyyMMddHHmmss");
        String time = df.format(new Date());
        Long random = (long) (Math.random() * Math.pow(10, 4));
        return tmnNum + time + random.toString();
    }

    /**
     * ��ȡ��ǰʱ���ַ���ʽ:yyyyMMddHHmmss
     * 
     * @return
     */
    public static String getRequestTime() {
        DateFormat df = new SimpleDateFormat("yyyyMMddHHmmss");
        return df.format(new Date());
    }

    /**
     * �ж��ַ����Ƿ�Ϊ��
     * 
     * @param o
     * @return boolean
     */
    public static boolean isEmpty(String str) {
        if (null == str || "".equals(str)) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * ȡ��һ�Ա�ǩ֮���ֵ
     * 
     * @param name
     *            ��ǩ��
     * @param xmlĿ���ַ���
     * @return
     */
    public String getValue(String name, String xml) {
        String start = "<" + name + ">";
        String end = "</" + name + ">";
        String value = xml.substring(xml.indexOf(start) + start.length(), xml.indexOf(end));
        // logger.info("��ǩ��=" + name + " ��ǩֵΪ=" + value);
        return value;
    }

    /**
     * ȡ��һ�Ա�ǩֵ(�����ǶԱ�ǩ��)
     * 
     * @param name
     *            ��ǩ��
     * @param xmlĿ���ַ���
     * @return
     */
    public String getValue1(String name, String xml) {
        String start = "<" + name + ">";
        String end = "</" + name + ">";
        String value = xml.substring(xml.indexOf(start), xml.indexOf(end) + end.length());
        //logger.info("��ǩ��=" + name + " ��ǩΪ=" + value);
        return value;
    }

}
