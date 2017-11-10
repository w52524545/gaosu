package com.onway.web.controller.userList;

/**
 * * 类名称：WeixinUserList 类描述：获取关注者列表
 * 
 * @author wenqiang.Wang
 * @version $Id: WeixinUserList.java, v 0.1 2016年10月28日 下午5:38:23 wenqiang.Wang
 *          Exp $
 */
public class WeixinUserList {
    // 公众账号的关注用户
    private int    total;
    // 获取的openID个数
    private int    count;
    // OpenID列表
    private Data   data;
    // 拉取列表后的用户的OpenID
    private String nextOpenId;

    public int getTotal() {
        return total;
    }

    public int getCount() {
        return count;
    }

    public Data getData() {
        return data;
    }

    public String getNextOpenId() {
        return nextOpenId;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public void setData(Data data) {
        this.data = data;
    }

    public void setNextOpenId(String nextOpenId) {
        this.nextOpenId = nextOpenId;
    }

}
