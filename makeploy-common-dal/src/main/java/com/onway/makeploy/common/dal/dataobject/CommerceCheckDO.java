/**
 * yingyinglicai.com Inc.
 * Copyright (c) 2013-2014 All Rights Reserved.
 */
 package com.onway.makeploy.common.dal.dataobject;

import com.onway.makeploy.common.dal.dataobject.BaseDO;

import java.util.Date;

/**
 * A data object class directly models database table <tt>makeploy_commerce_check</tt>.
 *
 * This file is generated by <tt>onway-gen-dal</tt>, a DAL (Data Access Layer)
 * code generation utility specially developed for <tt>onway</tt> project.
 * 
 * PLEASE DO NOT MODIFY THIS FILE MANUALLY, or else your modification may
 * be OVERWRITTEN by someone else. To modify the file, you should go to 
 * directory <tt>(project-home)/biz/dal/src/conf/dalgen</tt>, and 
 * find the corresponding configuration file (<tt>tables/makeploy_commerce_check.xml</tt>). 
 * Modify the configuration file according to your needs, then run <tt>onway-gen-dal</tt> 
 * to generate this file.
 *
 * @author guangdong.li
 * @version $Id: CommerceCheckDO.java,v 1.0 2013/10/29 07:34:20 guangdong.li Exp $
 */
public class CommerceCheckDO extends BaseDO {
    private static final long serialVersionUID = 741231858441822688L;

    //========== properties ==========

	/**
	 * This property corresponds to db column <tt>ID</tt>.
	 */
	private int id;

	/**
	 * This property corresponds to db column <tt>USER_ID</tt>.
	 */
	private String userId;

	/**
	 * This property corresponds to db column <tt>COMMERCE_ID</tt>.
	 */
	private String commerceId;

	/**
	 * This property corresponds to db column <tt>USER_NAME</tt>.
	 */
	private String userName;

	/**
	 * This property corresponds to db column <tt>USER_CELL</tt>.
	 */
	private String userCell;

	/**
	 * This property corresponds to db column <tt>USER_JOB</tt>.
	 */
	private String userJob;

	/**
	 * This property corresponds to db column <tt>CARD_ADDR</tt>.
	 */
	private String cardAddr;

	/**
	 * This property corresponds to db column <tt>NOW_ADDR</tt>.
	 */
	private String nowAddr;

	/**
	 * This property corresponds to db column <tt>MESSAGES</tt>.
	 */
	private String messages;

	/**
	 * This property corresponds to db column <tt>STATUS</tt>.
	 */
	private String status;

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
     * Getter method for property <tt>userId</tt>.
     *
     * @return property value of userId
     */
	public String getUserId() {
		return userId;
	}
	
	/**
	 * Setter method for property <tt>userId</tt>.
	 * 
	 * @param userId value to be assigned to property userId
     */
	public void setUserId(String userId) {
		this.userId = userId;
	}

    /**
     * Getter method for property <tt>commerceId</tt>.
     *
     * @return property value of commerceId
     */
	public String getCommerceId() {
		return commerceId;
	}
	
	/**
	 * Setter method for property <tt>commerceId</tt>.
	 * 
	 * @param commerceId value to be assigned to property commerceId
     */
	public void setCommerceId(String commerceId) {
		this.commerceId = commerceId;
	}

    /**
     * Getter method for property <tt>userName</tt>.
     *
     * @return property value of userName
     */
	public String getUserName() {
		return userName;
	}
	
	/**
	 * Setter method for property <tt>userName</tt>.
	 * 
	 * @param userName value to be assigned to property userName
     */
	public void setUserName(String userName) {
		this.userName = userName;
	}

    /**
     * Getter method for property <tt>userCell</tt>.
     *
     * @return property value of userCell
     */
	public String getUserCell() {
		return userCell;
	}
	
	/**
	 * Setter method for property <tt>userCell</tt>.
	 * 
	 * @param userCell value to be assigned to property userCell
     */
	public void setUserCell(String userCell) {
		this.userCell = userCell;
	}

    /**
     * Getter method for property <tt>userJob</tt>.
     *
     * @return property value of userJob
     */
	public String getUserJob() {
		return userJob;
	}
	
	/**
	 * Setter method for property <tt>userJob</tt>.
	 * 
	 * @param userJob value to be assigned to property userJob
     */
	public void setUserJob(String userJob) {
		this.userJob = userJob;
	}

    /**
     * Getter method for property <tt>cardAddr</tt>.
     *
     * @return property value of cardAddr
     */
	public String getCardAddr() {
		return cardAddr;
	}
	
	/**
	 * Setter method for property <tt>cardAddr</tt>.
	 * 
	 * @param cardAddr value to be assigned to property cardAddr
     */
	public void setCardAddr(String cardAddr) {
		this.cardAddr = cardAddr;
	}

    /**
     * Getter method for property <tt>nowAddr</tt>.
     *
     * @return property value of nowAddr
     */
	public String getNowAddr() {
		return nowAddr;
	}
	
	/**
	 * Setter method for property <tt>nowAddr</tt>.
	 * 
	 * @param nowAddr value to be assigned to property nowAddr
     */
	public void setNowAddr(String nowAddr) {
		this.nowAddr = nowAddr;
	}

    /**
     * Getter method for property <tt>messages</tt>.
     *
     * @return property value of messages
     */
	public String getMessages() {
		return messages;
	}
	
	/**
	 * Setter method for property <tt>messages</tt>.
	 * 
	 * @param messages value to be assigned to property messages
     */
	public void setMessages(String messages) {
		this.messages = messages;
	}

    /**
     * Getter method for property <tt>status</tt>.
     *
     * @return property value of status
     */
	public String getStatus() {
		return status;
	}
	
	/**
	 * Setter method for property <tt>status</tt>.
	 * 
	 * @param status value to be assigned to property status
     */
	public void setStatus(String status) {
		this.status = status;
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
