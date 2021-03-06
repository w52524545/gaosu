/**
 * yingyinglicai.com Inc.
 * Copyright (c) 2013-2014 All Rights Reserved.
 */
 package com.onway.makeploy.common.dal.dataobject;

import com.onway.makeploy.common.dal.dataobject.BaseDO;

import java.util.Date;

/**
 * A data object class directly models database table <tt>makeploy_activity</tt>.
 *
 * This file is generated by <tt>onway-gen-dal</tt>, a DAL (Data Access Layer)
 * code generation utility specially developed for <tt>onway</tt> project.
 * 
 * PLEASE DO NOT MODIFY THIS FILE MANUALLY, or else your modification may
 * be OVERWRITTEN by someone else. To modify the file, you should go to 
 * directory <tt>(project-home)/biz/dal/src/conf/dalgen</tt>, and 
 * find the corresponding configuration file (<tt>tables/makeploy_activity.xml</tt>). 
 * Modify the configuration file according to your needs, then run <tt>onway-gen-dal</tt> 
 * to generate this file.
 *
 * @author guangdong.li
 * @version $Id: ActivityDO.java,v 1.0 2013/10/29 07:34:20 guangdong.li Exp $
 */
public class ActivityDO extends BaseDO {
    private static final long serialVersionUID = 741231858441822688L;

    //========== properties ==========

	/**
	 * This property corresponds to db column <tt>ID</tt>.
	 */
	private int id;

	/**
	 * This property corresponds to db column <tt>TITLE</tt>.
	 */
	private String title;

	/**
	 * This property corresponds to db column <tt>INFO</tt>.
	 */
	private String info;

	/**
	 * This property corresponds to db column <tt>TYPE</tt>.
	 */
	private String type;

	/**
	 * This property corresponds to db column <tt>SUB_TYPE</tt>.
	 */
	private String subType;

	/**
	 * This property corresponds to db column <tt>POSTER</tt>.
	 */
	private String poster;

	/**
	 * This property corresponds to db column <tt>URL</tt>.
	 */
	private String url;

	/**
	 * This property corresponds to db column <tt>STATE</tt>.
	 */
	private String state;

	/**
	 * This property corresponds to db column <tt>NUM</tt>.
	 */
	private int num;

	/**
	 * This property corresponds to db column <tt>CREATER</tt>.
	 */
	private String creater;

	/**
	 * This property corresponds to db column <tt>GMT_CREATE</tt>.
	 */
	private Date gmtCreate;

	/**
	 * This property corresponds to db column <tt>MODIFIER</tt>.
	 */
	private String modifier;

	/**
	 * This property corresponds to db column <tt>GMT_MODIFIED</tt>.
	 */
	private Date gmtModified;

	/**
	 * This property corresponds to db column <tt>MEMO</tt>.
	 */
	private String memo;

    //========== getters and setters ==========

    /**
     * Getter method for property <tt>id</tt>.
     *
     * @return property value of id
     */
	public int getId() {
		return id;
	}
	
	/**
	 * Setter method for property <tt>id</tt>.
	 * 
	 * @param id value to be assigned to property id
     */
	public void setId(int id) {
		this.id = id;
	}

    /**
     * Getter method for property <tt>title</tt>.
     *
     * @return property value of title
     */
	public String getTitle() {
		return title;
	}
	
	/**
	 * Setter method for property <tt>title</tt>.
	 * 
	 * @param title value to be assigned to property title
     */
	public void setTitle(String title) {
		this.title = title;
	}

    /**
     * Getter method for property <tt>info</tt>.
     *
     * @return property value of info
     */
	public String getInfo() {
		return info;
	}
	
	/**
	 * Setter method for property <tt>info</tt>.
	 * 
	 * @param info value to be assigned to property info
     */
	public void setInfo(String info) {
		this.info = info;
	}

    /**
     * Getter method for property <tt>type</tt>.
     *
     * @return property value of type
     */
	public String getType() {
		return type;
	}
	
	/**
	 * Setter method for property <tt>type</tt>.
	 * 
	 * @param type value to be assigned to property type
     */
	public void setType(String type) {
		this.type = type;
	}

    /**
     * Getter method for property <tt>subType</tt>.
     *
     * @return property value of subType
     */
	public String getSubType() {
		return subType;
	}
	
	/**
	 * Setter method for property <tt>subType</tt>.
	 * 
	 * @param subType value to be assigned to property subType
     */
	public void setSubType(String subType) {
		this.subType = subType;
	}

    /**
     * Getter method for property <tt>poster</tt>.
     *
     * @return property value of poster
     */
	public String getPoster() {
		return poster;
	}
	
	/**
	 * Setter method for property <tt>poster</tt>.
	 * 
	 * @param poster value to be assigned to property poster
     */
	public void setPoster(String poster) {
		this.poster = poster;
	}

    /**
     * Getter method for property <tt>url</tt>.
     *
     * @return property value of url
     */
	public String getUrl() {
		return url;
	}
	
	/**
	 * Setter method for property <tt>url</tt>.
	 * 
	 * @param url value to be assigned to property url
     */
	public void setUrl(String url) {
		this.url = url;
	}

    /**
     * Getter method for property <tt>state</tt>.
     *
     * @return property value of state
     */
	public String getState() {
		return state;
	}
	
	/**
	 * Setter method for property <tt>state</tt>.
	 * 
	 * @param state value to be assigned to property state
     */
	public void setState(String state) {
		this.state = state;
	}

    /**
     * Getter method for property <tt>num</tt>.
     *
     * @return property value of num
     */
	public int getNum() {
		return num;
	}
	
	/**
	 * Setter method for property <tt>num</tt>.
	 * 
	 * @param num value to be assigned to property num
     */
	public void setNum(int num) {
		this.num = num;
	}

    /**
     * Getter method for property <tt>creater</tt>.
     *
     * @return property value of creater
     */
	public String getCreater() {
		return creater;
	}
	
	/**
	 * Setter method for property <tt>creater</tt>.
	 * 
	 * @param creater value to be assigned to property creater
     */
	public void setCreater(String creater) {
		this.creater = creater;
	}

    /**
     * Getter method for property <tt>gmtCreate</tt>.
     *
     * @return property value of gmtCreate
     */
	public Date getGmtCreate() {
		return gmtCreate;
	}
	
	/**
	 * Setter method for property <tt>gmtCreate</tt>.
	 * 
	 * @param gmtCreate value to be assigned to property gmtCreate
     */
	public void setGmtCreate(Date gmtCreate) {
		this.gmtCreate = gmtCreate;
	}

    /**
     * Getter method for property <tt>modifier</tt>.
     *
     * @return property value of modifier
     */
	public String getModifier() {
		return modifier;
	}
	
	/**
	 * Setter method for property <tt>modifier</tt>.
	 * 
	 * @param modifier value to be assigned to property modifier
     */
	public void setModifier(String modifier) {
		this.modifier = modifier;
	}

    /**
     * Getter method for property <tt>gmtModified</tt>.
     *
     * @return property value of gmtModified
     */
	public Date getGmtModified() {
		return gmtModified;
	}
	
	/**
	 * Setter method for property <tt>gmtModified</tt>.
	 * 
	 * @param gmtModified value to be assigned to property gmtModified
     */
	public void setGmtModified(Date gmtModified) {
		this.gmtModified = gmtModified;
	}

    /**
     * Getter method for property <tt>memo</tt>.
     *
     * @return property value of memo
     */
	public String getMemo() {
		return memo;
	}
	
	/**
	 * Setter method for property <tt>memo</tt>.
	 * 
	 * @param memo value to be assigned to property memo
     */
	public void setMemo(String memo) {
		this.memo = memo;
	}
}
