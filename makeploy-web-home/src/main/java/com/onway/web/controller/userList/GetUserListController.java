package com.onway.web.controller.userList;

import java.io.IOException;
import java.util.List;

import org.apache.commons.httpclient.HttpException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.onway.common.lang.HttpUtils;
import com.onway.makeploy.common.dal.dataobject.UserDO;
import com.onway.platform.common.enums.PlatformCodeEnum;
import com.onway.web.controller.AbstractController;
import com.onway.web.controller.sendMessage.TokenInfo;

/**
 * * �����ƣ�GetUserList  
 * ����������ȡ��ע���б� 
 * @author wenqiang.Wang
 * @version $Id: GetUserListTest.java, v 0.1 2016��10��28�� ����2:20:01 wenqiang.Wang Exp $
 */
@Controller
public class GetUserListController extends AbstractController {
    public final static String GET_ACCESS_TOKEN_URL = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=APPID&secret=APPSECRET";
    public final static String GET_USERLIST_URL     = "https://api.weixin.qq.com/cgi-bin/user/get?access_token=ACCESS_TOKEN&next_openid=NEXT_OPENID";
    public final static String GET_PERSONALINF_URL  = "https://api.weixin.qq.com/cgi-bin/user/info?access_token=ACCESS_TOKEN&openid=OPENID&lang=zh_CN";

    /**
     * ��ȡ��ע���б�
     * @param accessToken ���ýӿ�ƾ֤
     * @param nextOpenId ��һ����ȡ��OPENID������Ĭ�ϴ�ͷ��ʼ��ȡ
     * @return WeixinUserList
     */
    @SuppressWarnings({ "deprecation", "unchecked" })
    public static WeixinUserList getUserList(String accessToken, String nextOpenId) {
        WeixinUserList weixinUserList = null;
        if (null == nextOpenId) {
            nextOpenId = "";
        }
        String requestUrl = GET_USERLIST_URL.replace("ACCESS_TOKEN", accessToken).replace(
            "NEXT_OPENID", nextOpenId);
        // ��ȡ��˿�б�
        try {
            String resultStr = HttpUtils.executeGetMethod(requestUrl, "UTF-8", null);
            System.out.println("��ȡ���û�List�б�" + resultStr);
            weixinUserList = JSON.parseObject(resultStr, WeixinUserList.class);
        } catch (HttpException e) {
            System.out.println("" + e);
        } catch (IOException e) {
            System.out.println("" + e);
        }
        return weixinUserList;
    }

    /*
     * �÷�����Ϊ��ȡ�û��б�ʹ�ã�����Ϊ���ںŲ�Ʒ����ת���û�ʹ�ã�
     */
    @RequestMapping("/getUserList.do")
    @ResponseBody
    public void getUserList() throws HttpException, IOException {
        //��ȡ���ƾ֤����
        String appid = "wx106eb79cfabaa0b0";
        //                sysConfigCacheManager.getConfigValue(SysConfigCacheKeyEnum.WE_PAY_APP_ID);
        String appKey = "3dca4fcff041fa216544804a1fedf64f";
        //                sysConfigCacheManager
        //            .getConfigValue(SysConfigCacheKeyEnum.WE_PAY_APP_SECRET_ID);
        // ��ȡ�ӿڷ���ƾ֤
        String accessToken = getAccessToken(appid, appKey);
        WeixinUserList weixinUserList = getUserList(accessToken, "o3NcMw6GKmJbh2r3U4zWF0meMlzs");
        List<String> opids = weixinUserList.getData().getOpenid();
        //        getUserInfo(accessToken, opids.get(0));
        for (String openId : opids) {
            //��ȡ�û���Ϣ�����û���ӵ����ݿ�
            getUserInfo("", openId);

        }
        System.err.println("total:" + weixinUserList.getTotal());
        System.err.println("count:" + weixinUserList.getCount());
        //      System.err.println("openid:"+weixinUserList.getOpenIdList());
        System.err.println("next_openid:" + weixinUserList.getNextOpenId());
    }

    /*
     * ��ȡ΢���û������Ϣ�ӿ�
     */

    public void getUserInfo(String temp, String openid) throws HttpException, IOException {
        //��ȡ���ƾ֤����
        String appid = "wx106eb79cfabaa0b0";
        //                sysConfigCacheManager.getConfigValue(SysConfigCacheKeyEnum.WE_PAY_APP_ID);
        String appKey = "3dca4fcff041fa216544804a1fedf64f";
        //                sysConfigCacheManager
        //            .getConfigValue(SysConfigCacheKeyEnum.WE_PAY_APP_SECRET_ID);
        // ��ȡ�ӿڷ���ƾ֤
        String accessToken = getAccessToken(appid, appKey);
        String requestUrl = GET_PERSONALINF_URL.replace("ACCESS_TOKEN", accessToken).replace(
            "OPENID", openid);
        String resultStr = HttpUtils.executeGetMethod(requestUrl, "UTF-8", null);
        UserInfo userInfo = JSON.parseObject(resultStr, UserInfo.class);
        UserDO user = buildUserDO(userInfo, "");
        try {
            int insertId = userDao.addUserInfo(user);

            if (insertId > 0) {
                System.out.println("�û���Ϣ��ӳɹ���");
            }
        } catch (Exception e) {
            return;
        }

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
    public static String getAccessToken(String appid, String appsecret) throws HttpException,
                                                                       IOException {
        String requestUrl = GET_ACCESS_TOKEN_URL.replace("APPID", appid).replace("APPSECRET",
            appsecret);

        String resultStr = HttpUtils.executeGetMethod(requestUrl);
        TokenInfo tokenInfo = JSON.parseObject(resultStr, TokenInfo.class);

        String token = tokenInfo.getAccess_token();

        return token;
    }

    /*
     * �����û�ʵ�������
     */
    public UserDO buildUserDO(UserInfo userInfo, String promoteId) {
        UserDO user = new UserDO();
        //���������ƵĹ��ˣ�����emoji����
        String nickName = EmojiFilter.filterEmoji(userInfo.getNickname());
        String userId = codeGenerateComponent.nextCodeByType(PlatformCodeEnum.MALL_PLATFORM);
        int promoteIds = codeGenerateComponent.nextCode("PROMOTE");
        user.setUserId(userId);
        //        user.setWechatId(promoteId);
        user.setPromoteId(String.valueOf(promoteIds));
        user.setUserNum(userId);
        user.setPassWord("123456");
        user.setNickName(nickName);
        user.setWechatId(userInfo.getOpenid());
        user.setHeadUrl(userInfo.getHeadimgurl());
        user.setSex(userInfo.getSex());

        return user;
    }
}
