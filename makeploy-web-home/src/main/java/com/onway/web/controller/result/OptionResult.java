package com.onway.web.controller.result;

/**
 * 用户意见反馈结果集
 * Created by bocai.huang
 */
public class OptionResult extends JsonResult {

    private Integer id;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    private String userId;
    private String title;
    private String message;

    public OptionResult(boolean bizSucc, String errCode, String errMsg) {
        super(bizSucc, errCode, errMsg);
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
