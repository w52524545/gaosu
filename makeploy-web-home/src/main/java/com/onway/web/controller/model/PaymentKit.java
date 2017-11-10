package com.onway.web.controller.model;

import java.io.InputStream;  
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.TreeMap;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

/**
 * 
 * @author wenqiang.Wang
 * @version $Id: PaymentKit.java, v 0.1 2016年10月31日 下午2:32:44 wenqiang.Wang Exp $
 */
public class PaymentKit {

    private static final String CHARSET = "UTF-8";

    public static String packageSign(Map<String, String> params, boolean urlEncoder) {

        TreeMap<String, String> sortedParams = new TreeMap<String, String>(params);

        sortedParams.remove("sign");

        StringBuilder sb = new StringBuilder();
        boolean first = true;
        for (Entry<String, String> param : sortedParams.entrySet()) {
            String value = param.getValue();
            if (StringUtils.isBlank(value)) {
                continue;
            }
            if (first) {
                first = false;
            } else {
                sb.append("&");
            }
            sb.append(param.getKey()).append("=");
            if (urlEncoder) {
                try {
                    value = urlEncode(value);
                } catch (UnsupportedEncodingException e) {
                }
            }
            sb.append(value);
        }
        return sb.toString();
    }

    private static String urlEncode(String src) throws UnsupportedEncodingException {
        return URLEncoder.encode(src, CHARSET).replace("+", "%20");
    }

    public static String createSign(Map<String, String> params, String paternerKey) {
        String stringA = packageSign(params, false);
        String stringSignTemp = stringA + "&key=" + paternerKey;
        return MD5Util.MD5Encode(stringSignTemp, "UTF-8").toUpperCase();
    }

    public static boolean verifyNotify(Map<String, String> params, String paternerKey) {
        String sign = PaymentKit.createSign(params, paternerKey);
        return sign.equals(params.get("sign"));
    }
   /*
    * 将文本内容转化为xml
    */
    public static String toXml(Map<String, String> params) {
        StringBuilder xml = new StringBuilder();
        xml.append("<xml>");
        for (Entry<String, String> entry : params.entrySet()) {
            String key = entry.getKey();
            String value = entry.getValue();

            if (StringUtils.isBlank(value))
                continue;
            xml.append("<").append(key).append(">");
            xml.append(entry.getValue());
            xml.append("</").append(key).append(">");
        }
        xml.append("</xml>");
        return xml.toString();
    }
    
    /*
     * 扩展使其支持CDATA
     */
    public static String toCDATAXml(Map<String, String> params) {
        StringBuilder xml = new StringBuilder();
        xml.append("<xml>");
        for (Entry<String, String> entry : params.entrySet()) {
            String key = entry.getKey();
            String value = entry.getValue();

            if (StringUtils.isBlank(value))
                continue;
            xml.append("<").append(key).append(">");
            xml.append("<![CDATA[");
            xml.append(entry.getValue());
            xml.append("]]>");
            xml.append("</").append(key).append(">");
        }
        xml.append("</xml>");
        return xml.toString();
    }
    
    /** 
     * 解析微信发来的请求（XML） 
     *  
     * @param request 
     * @return 
     * @throws Exception 
     */  
    @SuppressWarnings("unchecked")  
    public static Map<String, String> parseXml(HttpServletRequest request) throws Exception {  
        // 将解析结果存储在HashMap中  
        Map<String, String> map = new HashMap<String, String>();   
        // 从request中取得输入流  
        InputStream inputStream = request.getInputStream();  
        // 读取输入流  
        SAXReader reader = new SAXReader();  
        Document document = reader.read(inputStream);  
        // 得到xml根元素  
        Element root = document.getRootElement();  
        // 得到根元素的所有子节点  
        List<Element> elementList = root.elements();  
        // 遍历所有子节点  
        for (Element e : elementList)  
            map.put(e.getName(), e.getText());  
        // 释放资源  
        inputStream.close();  
        inputStream = null;   
        return map;  
    }  

}
