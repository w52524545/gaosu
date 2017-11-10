/**
 * onway.com Inc.
 * Copyright (c) 2016-2016 All Rights Reserved.
 */
package com.onway.web.controller.result;

/**
 * 
 * 
 * @author guangdong.li
 * @version $Id: JsonPageResult.java, v 0.1 19 Feb 2016 18:30:19 guangdong.li Exp $
 */
public class JsonQueryResult<T> extends JsonResult {

    public JsonQueryResult(boolean bizSucc) {
        super(bizSucc);
    }

    public JsonQueryResult(boolean bizSucc, String errCode, String message) {
        super(bizSucc, errCode, message);
    }

    /**
     * serialVersionUID
     */
    private static final long serialVersionUID = 7641724788545434094L;

    private T                 obj;

    public T getObj() {
        return obj;
    }

    public void setObj(T obj) {
        this.obj = obj;
    }

}
