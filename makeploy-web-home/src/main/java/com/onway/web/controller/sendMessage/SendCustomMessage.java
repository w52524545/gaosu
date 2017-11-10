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
* 项目名称：wechatapi  
* 类名称：SendCustomMessage  
* 类描述：发送客服消息工具 
* 创建人：wenqiang Wang  
* 创建时间:2016-10-10 上午10:37:08  
* @version       
*/
public class SendCustomMessage {
    
    public final static String GET_ACCESS_TOKEN_URL = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=APPID&secret=APPSECRET";   
    protected final static String SEND_CUSTOM_URL="https://api.weixin.qq.com/cgi-bin/message/custom/send?access_token=ACCESS_TOKEN";
    
    public static void main(String[] args) {
        // 获取接口访问凭证
        String accessToken;
        try {
            accessToken = getAccessToken("wx47708549abf6613d", "c1bffdd3fa3720f2b2c55c997f5b4351");
         // 组装文本客服消息
            String jsonTextMsg=makeTextCustomMessage("oStIev6lTvr2UR0QCoydmqYrhvSk", "你好，欢迎使用玫发现的微信客服消息测试，您接收的这是一条测试客服消息");
          //上传图片获取media-id
            String mediaFileUrl="http://nxpp.wangdaidiandian.com/distribution_image/item1.jpg";
            String mediaId=MediaUtil.uploadMedia(accessToken, "image", mediaFileUrl).getMediaId();
          //组装图片客服消息
            String jsonImageMsg=makeImageCustomMessage("oStIev6lTvr2UR0QCoydmqYrhvSk", mediaId);
            //组装语音客服消息
            String jsonVoiceMsg=makeVoiceCustomMessage("oStIev6lTvr2UR0QCoydmqYrhvSk", mediaId); 
            //组装图文客服消息
            List<Article> articleList=makeArticleList();
            String jsonNewsMsg=makeNewsCustomMessage("oStIev6lTvr2UR0QCoydmqYrhvSk", articleList); 
            // 发送客服消息
            sendCustomMessage(accessToken, jsonNewsMsg);
        } catch (HttpException e) {
            System.out.println(""+ e);
        } catch (IOException e) {
            System.out.println(""+ e);
        } 
    }
    
    /**
     * 发送客服消息方法
     * 
     * @param accessToken 接口访问凭证
     * @param jsonMsg json格式客服消息
     * @return true|false
     * @throws IOException 
     * @throws HttpException 
     */
    public static boolean sendCustomMessage(String accessToken,String jsonMsg) throws HttpException, IOException {
        System.out.println("消息内容：{}"+jsonMsg);
        boolean result=false;
        String requestUrl=SEND_CUSTOM_URL.replace("ACCESS_TOKEN", accessToken);
        // 发送客服消息
         String returnXml = HttpUtils.executePostMethod(requestUrl, "UTF-8", jsonMsg);
        if (null!=returnXml) {
            System.out.println("已经发送客服消息");
        }
        return result;
    }
    
    /**
     * 获取access_token
     *
     * @param appid 凭证
     * @param appsecret 密钥
     * @return AccessToken 返回接口凭证
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
    
    // 组装文本客服消息
    public static String makeTextCustomMessage(String openId,String content) {
        // 对消息内容中的双引号进行转义
        content=content.replace("\"", "\\\"");
        String jsonMsg = "{\"touser\":\"%s\",\"msgtype\":\"text\",\"text\":" +
                "{\"content\":\"%s\"}}";
        return String.format(jsonMsg, openId,content);
    }
    
    /**
     * 组装图片客服消息
     * @param openId 普通用户openid
     * @param mediaId 发送的图片的媒体ID
     * @return String
     */
    public static String makeImageCustomMessage(String openId,String mediaId) {
        String jsonMsg = "{\"touser\":\"%s\",\"msgtype\":\"image\",\"image\":" +
                "{\"media_id\":\"%s\"}}";
        return String.format(jsonMsg, openId,mediaId);
    }
    
    /**
     * 组装语音客服消息
     * 
     * @param openId 普通用户openid
     * @param mediaId 发送的语音的媒体ID
     * @return String
     */
    public static String makeVoiceCustomMessage(String openId,String mediaId) {
        String jsonMsg = "{\"touser\":\"%s\",\"msgtype\":\"voice\",\"voice\":" +
                "{\"media_id\":\"%s\"}}";
        return String.format(jsonMsg, openId,mediaId);
    }
    
    /**
     * 组装图文客服消息
     * @param openId 普通用户openid
     * @param articleList 图文消息列表
     * @return String
     */
    public static String makeNewsCustomMessage(String openId,List<Article> articleList) {
        String jsonMsg = "{\"touser\":\"%s\",\"msgtype\":\"news\",\"news\":{\"articles\":%s}}";
        jsonMsg=String.format(jsonMsg, openId,JSONObject.toJSON(articleList).toString());
        return jsonMsg;
    }
    
    //组装图文消息的文章
    // 图片链接，支持JPG、PNG格式，较好的效果为大图640*320，小图80*80，限制图片链接的域名需要与开发者填写的基本资料中的Url一致   
   public static  List<Article> makeArticleList(){
       List<Article> articleList=new ArrayList<Article>();
       Article article=new Article();
       Article article1=new Article();
       Article article2=new Article();
       article.setDescription("");
       article.setPicurl("http://nxpp.wangdaidiandian.com/distribution_image/item1.jpg");
       article.setTitle("这是一条测试用的图文消息\n引言");
       article.setUrl("http://www.puyueinfo.cn/");
       articleList.add(article);
       
       article1.setDescription("");
       article1.setPicurl("http://nxpp.wangdaidiandian.com/distribution_image/item2.jpg");
       article1.setTitle("图文消息功能实现啦\n欢迎访问测试");
       article1.setUrl("www.baidu.com");
       articleList.add(article1);
       
       article2.setDescription("");
       article2.setPicurl("http://nxpp.wangdaidiandian.com/distribution_image/item2.jpg");
       article2.setTitle("多图文客服消息功能实现啦\n欢迎访问测试");
       article2.setUrl("www.baidu.com");
       articleList.add(article2);
    return articleList;
       
   }
    
}
