/**
 * onway.com Inc.
 * Copyright (c) 2016-2016 All Rights Reserved.
 */
package com.onway.web.controller.result;

import java.util.List;

/**
 * 
 * 
 * @author guangdong.li
 * @version $Id: JsonPageResult.java, v 0.1 19 Feb 2016 18:30:19 guangdong.li Exp $
 */
public class JsonPageResult<E> extends JsonResult {

    public JsonPageResult(boolean bizSucc) {
        super(bizSucc);
    }

    public JsonPageResult(boolean bizSucc, String errCode, String message) {
        super(bizSucc, errCode, message);
    }

    /**
     * serialVersionUID
     */
    private static final long serialVersionUID = 7641724788545434094L;

    // ҳ��
    protected int             pageNum          = 1;

    // ��ҳ��
    protected int             totalPages;

    // �Ƿ�����һҳ
    protected boolean         next             = false;
    
    
    
    //---------------------------------------------------------------------
    private String father;  

	public String getFather() {
		return father;
	}

	public void setFather(String father) {
		this.father = father;
	}

	//------------------------------------------------------------------------
	private List<E>           listObject;

    public int getPageNum() {
        return pageNum;
    }

    public void setPageNum(int pageNum) {
        this.pageNum = pageNum;
    }

    public boolean isNext() {
        return next;
    }

    public void setNext(boolean next) {
        this.next = next;
    }

    public int getTotalPages() {
        return totalPages;
    }

    public void setTotalPages(int totalPages) {
        this.totalPages = totalPages;
    }

    public List<E> getListObject() {
        return listObject;
    }

    public void setListObject(List<E> listObject) {
        this.listObject = listObject;
    }
}
