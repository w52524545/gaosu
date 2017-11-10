package com.onway.web.controller.sendMessage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.httpclient.HttpException;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.onway.common.lang.HttpUtils;
import com.onway.web.controller.sendMessage.Article;


/**  
*   
* ��Ŀ���ƣ�wechatapi  
* �����ƣ�SendCustomMessage  
* �����������Ϳͷ���Ϣ���� 
* �����ˣ�wenqiang Wang  
* ����ʱ��:2016-10-10 ����10:37:08  
* @version       
*/
public class SendCustomMessage {
    
    public final static String GET_ACCESS_TOKEN_URL = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=APPID&secret=APPSECRET";   
    protected final static String SEND_CUSTOM_URL="https://api.weixin.qq.com/cgi-bin/message/custom/send?access_token=ACCESS_TOKEN";
    
    public static void main(String[] args) {
        // ��ȡ�ӿڷ���ƾ֤
        String accessToken;
        try {
            accessToken = getAccessToken("wx47708549abf6613d", "c1bffdd3fa3720f2b2c55c997f5b4351");
         // ��װ�ı��ͷ���Ϣ
            String jsonTextMsg=makeTextCustomMessage("oStIev6lTvr2UR0QCoydmqYrhvSk", "��ã���ӭʹ��õ���ֵ�΢�ſͷ���Ϣ���ԣ������յ�����һ�����Կͷ���Ϣ");
          //�ϴ�ͼƬ��ȡmedia-id
            String mediaFileUrl="http://nxpp.wangdaidiandian.com/distribution_image/item1.jpg";
            String mediaId=MediaUtil.uploadMedia(accessToken, "image", mediaFileUrl).getMediaId();
          //��װͼƬ�ͷ���Ϣ
            String jsonImageMsg=makeImageCustomMessage("oStIev6lTvr2UR0QCoydmqYrhvSk", mediaId);
            //��װ�����ͷ���Ϣ
            String jsonVoiceMsg=makeVoiceCustomMessage("oStIev6lTvr2UR0QCoydmqYrhvSk", mediaId); 
            //��װͼ�Ŀͷ���Ϣ
            List<Article> articleList=makeArticleList();
            String jsonNewsMsg=makeNewsCustomMessage("oStIev6lTvr2UR0QCoydmqYrhvSk", articleList); 
            // ���Ϳͷ���Ϣ
            sendCustomMessage(accessToken, jsonNewsMsg);
        } catch (HttpException e) {
            System.out.println(""+ e);
        } catch (IOException e) {
            System.out.println(""+ e);
        } 
    }
    
    /**
     * ���Ϳͷ���Ϣ����
     * 
     * @param accessToken �ӿڷ���ƾ֤
     * @param jsonMsg json��ʽ�ͷ���Ϣ
     * @return true|false
     * @throws IOException 
     * @throws HttpException 
     */
    public static boolean sendCustomMessage(String accessToken,String jsonMsg) throws HttpException, IOException {
        System.out.println("��Ϣ���ݣ�{}"+jsonMsg);
        boolean result=false;
        String requestUrl=SEND_CUSTOM_URL.replace("ACCESS_TOKEN", accessToken);
        // ���Ϳͷ���Ϣ
         String returnXml = HttpUtils.executePostMethod(requestUrl, "UTF-8", jsonMsg);
        if (null!=returnXml) {
            System.out.println("�Ѿ����Ϳͷ���Ϣ");
        }
        return result;
    }
    
    /**
     * ��ȡaccess_token
     *
     * @param appid ƾ֤
     * @param appsecret ��Կ
     * @return AccessToken ���ؽӿ�ƾ֤
     * @throws IOException 
     * @throws HttpException 
     */
    public static  String getAccessToken(String appid, String appsecret) throws HttpException, IOException {
        String requestUrl = GET_ACCESS_TOKEN_URL.replace("APPID", appid).replace("APPSECRET", appsecret);

        String resultStr = HttpUtils.executeGetMethod(requestUrl);
        TokenInfo tokenInfo = JSON.parseObject(resultStr, TokenInfo.class);
        
        String token=tokenInfo.getAccess_token();
      
        return token;
    }
    
    // ��װ�ı��ͷ���Ϣ
    public static String makeTextCustomMessage(String openId,String content) {
        // ����Ϣ�����е�˫���Ž���ת��
        content=content.replace("\"", "\\\"");
        String jsonMsg = "{\"touser\":\"%s\",\"msgtype\":\"text\",\"text\":" +
                "{\"content\":\"%s\"}}";
        return String.format(jsonMsg, openId,content);
    }
    
    /**
     * ��װͼƬ�ͷ���Ϣ
     * @param openId ��ͨ�û�openid
     * @param mediaId ���͵�ͼƬ��ý��ID
     * @return String
     */
    public static String makeImageCustomMessage(String openId,String mediaId) {
        String jsonMsg = "{\"touser\":\"%s\",\"msgtype\":\"image\",\"image\":" +
                "{\"media_id\":\"%s\"}}";
        return String.format(jsonMsg, openId,mediaId);
    }
    
    /**
     * ��װ�����ͷ���Ϣ
     * 
     * @param openId ��ͨ�û�openid
     * @param mediaId ���͵�������ý��ID
     * @return String
     */
    public static String makeVoiceCustomMessage(String openId,String mediaId) {
        String jsonMsg = "{\"touser\":\"%s\",\"msgtype\":\"voice\",\"voice\":" +
                "{\"media_id\":\"%s\"}}";
        return String.format(jsonMsg, openId,mediaId);
    }
    
    /**
     * ��װͼ�Ŀͷ���Ϣ
     * @param openId ��ͨ�û�openid
     * @param articleList ͼ����Ϣ�б�
     * @return String
     */
    public static String makeNewsCustomMessage(String openId,List<Article> articleList) {
        String jsonMsg = "{\"touser\":\"%s\",\"msgtype\":\"news\",\"news\":{\"articles\":%s}}";
        jsonMsg=String.format(jsonMsg, openId,JSONObject.toJSON(articleList).toString());
        return jsonMsg;
    }
    
    //��װͼ����Ϣ������
    // ͼƬ���ӣ�֧��JPG��PNG��ʽ���Ϻõ�Ч��Ϊ��ͼ640*320��Сͼ80*80������ͼƬ���ӵ�������Ҫ�뿪������д�Ļ��������е�Urlһ��   
   public static  List<Article> makeArticleList(){
       List<Article> articleList=new ArrayList<Article>();
       Article article=new Article();
       Article article1=new Article();
       Article article2=new Article();
       article.setDescription("");
       article.setPicurl("http://nxpp.wangdaidiandian.com/distribution_image/item1.jpg");
       article.setTitle("����һ�������õ�ͼ����Ϣ\n����");
       article.setUrl("http://www.puyueinfo.cn/");
       articleList.add(article);
       
       article1.setDescription("");
       article1.setPicurl("http://nxpp.wangdaidiandian.com/distribution_image/item2.jpg");
       article1.setTitle("ͼ����Ϣ����ʵ����\n��ӭ���ʲ���");
       article1.setUrl("www.baidu.com");
       articleList.add(article1);
       
       article2.setDescription("");
       article2.setPicurl("http://nxpp.wangdaidiandian.com/distribution_image/item2.jpg");
       article2.setTitle("��ͼ�Ŀͷ���Ϣ����ʵ����\n��ӭ���ʲ���");
       article2.setUrl("www.baidu.com");
       articleList.add(article2);
    return articleList;
       
   }
    
}
