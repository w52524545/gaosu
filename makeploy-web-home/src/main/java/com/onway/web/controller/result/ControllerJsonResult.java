/**
 * BenchCode.com Inc.
 * Copyright (c) 2013-2015 All Rights Reserved.
 */
package com.onway.web.controller.result;

import java.io.Serializable;

/**
 * json调用默认结果
 * 
 * @author guangdong.li
 * @version $Id: JsonResult.java, v 0.1 2013-10-30 下午5:38:55 WJL Exp $
 */
public class ControllerJsonResult implements Serializable {

    /**
     * serialVersionUID
     */
    private static final long serialVersionUID = 1475348231900998033L;

    // 业务操作结果
    private boolean           bizSucc          = true;

    // 是否强制下线，账号已在另一设备登录
    //private boolean           isForceLogOut    = false;

    private String            errMsg           = "";

    // 错误码
    private String            errCode          = "";

    public ControllerJsonResult(boolean bizSucc) {
        this.bizSucc = bizSucc;
    }

    public ControllerJsonResult(boolean bizSucc, String errCode, String message) {
        this.bizSucc = bizSucc;
        this.errMsg = message;
        this.errCode = errCode;
    }

    public void markResult(boolean bizSucc, String errCode, String message) {
        this.bizSucc = bizSucc;
        this.errMsg = message;
        this.errCode = errCode;
    }

    public boolean isBizSucc() {
        return bizSucc;
    }

    public void setBizSucc(boolean bizSucc) {
        this.bizSucc = bizSucc;
    }

    public String getErrMsg() {
        return errMsg;
    }

    public void setErrMsg(String errMsg) {
        this.errMsg = errMsg;
    }

    public String getErrCode() {
        return errCode;
    }

    public void setErrCode(String errCode) {
        this.errCode = errCode;
    }

}
