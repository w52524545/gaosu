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
 * * 类名称：GetUserList  
 * 类描述：获取关注者列表 
 * @author wenqiang.Wang
 * @version $Id: GetUserListTest.java, v 0.1 2016年10月28日 下午2:20:01 wenqiang.Wang Exp $
 */
@Controller
public class GetUserListController extends AbstractController {
    public final static String GET_ACCESS_TOKEN_URL = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=APPID&secret=APPSECRET";
    public final static String GET_USERLIST_URL     = "https://api.weixin.qq.com/cgi-bin/user/get?access_token=ACCESS_TOKEN&next_openid=NEXT_OPENID";
    public final static String GET_PERSONALINF_URL  = "https://api.weixin.qq.com/cgi-bin/user/info?access_token=ACCESS_TOKEN&openid=OPENID&lang=zh_CN";

    /**
     * 获取关注者列表
     * @param accessToken 调用接口凭证
     * @param nextOpenId 第一个拉取的OPENID，不填默认从头开始拉取
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
        // 获取粉丝列表
        try {
            String resultStr = HttpUtils.executeGetMethod(requestUrl, "UTF-8", null);
            System.out.println("获取的用户List列表" + resultStr);
            weixinUserList = JSON.parseObject(resultStr, WeixinUserList.class);
        } catch (HttpException e) {
            System.out.println("" + e);
        } catch (IOException e) {
            System.out.println("" + e);
        }
        return weixinUserList;
    }

    /*
     * 该方法作为获取用户列表使用（仅作为公众号产品上线转移用户使用）
     */
    @RequestMapping("/getUserList.do")
    @ResponseBody
    public void getUserList() throws HttpException, IOException {
        //获取相关凭证参数
        String appid = "wx106eb79cfabaa0b0";
        //                sysConfigCacheManager.getConfigValue(SysConfigCacheKeyEnum.WE_PAY_APP_ID);
        String appKey = "3dca4fcff041fa216544804a1fedf64f";
        //                sysConfigCacheManager
        //            .getConfigValue(SysConfigCacheKeyEnum.WE_PAY_APP_SECRET_ID);
        // 获取接口访问凭证
        String accessToken = getAccessToken(appid, appKey);
        WeixinUserList weixinUserList = getUserList(accessToken, "o3NcMw6GKmJbh2r3U4zWF0meMlzs");
        List<String> opids = weixinUserList.getData().getOpenid();
        //        getUserInfo(accessToken, opids.get(0));
        for (String openId : opids) {
            //获取用户信息并将用户添加到数据库
            getUserInfo("", openId);

        }
        System.err.println("total:" + weixinUserList.getTotal());
        System.err.println("count:" + weixinUserList.getCount());
        //      System.err.println("openid:"+weixinUserList.getOpenIdList());
        System.err.println("next_openid:" + weixinUserList.getNextOpenId());
    }

    /*
     * 获取微信用户相关信息接口
     */

    public void getUserInfo(String temp, String openid) throws HttpException, IOException {
        //获取相关凭证参数
        String appid = "wx106eb79cfabaa0b0";
        //                sysConfigCacheManager.getConfigValue(SysConfigCacheKeyEnum.WE_PAY_APP_ID);
        String appKey = "3dca4fcff041fa216544804a1fedf64f";
        //                sysConfigCacheManager
        //            .getConfigValue(SysConfigCacheKeyEnum.WE_PAY_APP_SECRET_ID);
        // 获取接口访问凭证
        String accessToken = getAccessToken(appid, appKey);
        String requestUrl = GET_PERSONALINF_URL.replace("ACCESS_TOKEN", accessToken).replace(
            "OPENID", openid);
        String resultStr = HttpUtils.executeGetMethod(requestUrl, "UTF-8", null);
        UserInfo userInfo = JSON.parseObject(resultStr, UserInfo.class);
        UserDO user = buildUserDO(userInfo, "");
        try {
            int insertId = userDao.addUserInfo(user);

            if (insertId > 0) {
                System.out.println("用户信息添加成功！");
            }
        } catch (Exception e) {
            return;
        }

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
     * 创建用户实体类对象
     */
    public UserDO buildUserDO(UserInfo userInfo, String promoteId) {
        UserDO user = new UserDO();
        //对特殊名称的过滤，比如emoji表情
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
