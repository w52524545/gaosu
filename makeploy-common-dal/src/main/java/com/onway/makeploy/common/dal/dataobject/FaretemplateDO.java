/**
 * yingyinglicai.com Inc.
 * Copyright (c) 2013-2014 All Rights Reserved.
 */
 package com.onway.makeploy.common.dal.dataobject;

import com.onway.makeploy.common.dal.dataobject.BaseDO;

import java.util.Date;

/**
 * A data object class directly models database table <tt>makeploy_faretemplate</tt>.
 *
 * This file is generated by <tt>onway-gen-dal</tt>, a DAL (Data Access Layer)
 * code generation utility specially developed for <tt>onway</tt> project.
 * 
 * PLEASE DO NOT MODIFY THIS FILE MANUALLY, or else your modification may
 * be OVERWRITTEN by someone else. To modify the file, you should go to 
 * directory <tt>(project-home)/biz/dal/src/conf/dalgen</tt>, and 
 * find the corresponding configuration file (<tt>tables/makeploy_faretemplate.xml</tt>). 
 * Modify the configuration file according to your needs, then run <tt>onway-gen-dal</tt> 
 * to generate this file.
 *
 * @author guangdong.li
 * @version $Id: FaretemplateDO.java,v 1.0 2013/10/29 07:34:20 guangdong.li Exp $
 */
public class FaretemplateDO extends BaseDO {
    private static final long serialVersionUID = 741231858441822688L;

    //========== properties ==========

	/**
	 * This property corresponds to db column <tt>ID</tt>.
	 */
	private int id;

	/**
	 * This property corresponds to db column <tt>TEMPLATE_ID</tt>.
	 */
	private String templateId;

	/**
	 * This property corresponds to db column <tt>SHOP_ID</tt>.
	 */
	private String shopId;

	/**
	 * This property corresponds to db column <tt>TEMPLATE_NAME</tt>.
	 */
	private String templateName;

	/**
	 * This property corresponds to db column <tt>GOODS_ADDRESS</tt>.
	 */
	private String goodsAddress;

	/**
	 * This property corresponds to db column <tt>SEND_TIME</tt>.
	 */
	private Date sendTime;

	/**
	 * This property corresponds to db column <tt>FREE_POST</tt>.
	 */
	private String freePost;

	/**
	 * This property corresponds to db column <tt>VALUATION_WAY</tt>.
	 */
	private String valuationWay;

	/**
	 * This property corresponds to db column <tt>DEL_FLAG</tt>.
	 */
	private String delFlag;

	/**
	 * This property corresponds to db column <tt>CONDITION_POST</tt>.
	 */
	private String conditionPost;

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
     * Getter method for property <tt>templateId</tt>.
     *
     * @return property value of templateId
     */
	public String getTemplateId() {
		return templateId;
	}
	
	/**
	 * Setter method for property <tt>templateId</tt>.
	 * 
	 * @param templateId value to be assigned to property templateId
     */
	public void setTemplateId(String templateId) {
		this.templateId = templateId;
	}

    /**
     * Getter method for property <tt>shopId</tt>.
     *
     * @return property value of shopId
     */
	public String getShopId() {
		return shopId;
	}
	
	/**
	 * Setter method for property <tt>shopId</tt>.
	 * 
	 * @param shopId value to be assigned to property shopId
     */
	public void setShopId(String shopId) {
		this.shopId = shopId;
	}

    /**
     * Getter method for property <tt>templateName</tt>.
     *
     * @return property value of templateName
     */
	public String getTemplateName() {
		return templateName;
	}
	
	/**
	 * Setter method for property <tt>templateName</tt>.
	 * 
	 * @param templateName value to be assigned to property templateName
     */
	public void setTemplateName(String templateName) {
		this.templateName = templateName;
	}

    /**
     * Getter method for property <tt>goodsAddress</tt>.
     *
     * @return property value of goodsAddress
     */
	public String getGoodsAddress() {
		return goodsAddress;
	}
	
	/**
	 * Setter method for property <tt>goodsAddress</tt>.
	 * 
	 * @param goodsAddress value to be assigned to property goodsAddress
     */
	public void setGoodsAddress(String goodsAddress) {
		this.goodsAddress = goodsAddress;
	}

    /**
     * Getter method for property <tt>sendTime</tt>.
     *
     * @return property value of sendTime
     */
	public Date getSendTime() {
		return sendTime;
	}
	
	/**
	 * Setter method for property <tt>sendTime</tt>.
	 * 
	 * @param sendTime value to be assigned to property sendTime
     */
	public void setSendTime(Date sendTime) {
		this.sendTime = sendTime;
	}

    /**
     * Getter method for property <tt>freePost</tt>.
     *
     * @return property value of freePost
     */
	public String getFreePost() {
		return freePost;
	}
	
	/**
	 * Setter method for property <tt>freePost</tt>.
	 * 
	 * @param freePost value to be assigned to property freePost
     */
	public void setFreePost(String freePost) {
		this.freePost = freePost;
	}

    /**
     * Getter method for property <tt>valuationWay</tt>.
     *
     * @return property value of valuationWay
     */
	public String getValuationWay() {
		return valuationWay;
	}
	
	/**
	 * Setter method for property <tt>valuationWay</tt>.
	 * 
	 * @param valuationWay value to be assigned to property valuationWay
     */
	public void setValuationWay(String valuationWay) {
		this.valuationWay = valuationWay;
	}

    /**
     * Getter method for property <tt>delFlag</tt>.
     *
     * @return property value of delFlag
     */
	public String getDelFlag() {
		return delFlag;
	}
	
	/**
	 * Setter method for property <tt>delFlag</tt>.
	 * 
	 * @param delFlag value to be assigned to property delFlag
     */
	public void setDelFlag(String delFlag) {
		this.delFlag = delFlag;
	}

    /**
     * Getter method for property <tt>conditionPost</tt>.
     *
     * @return property value of conditionPost
     */
	public String getConditionPost() {
		return conditionPost;
	}
	
	/**
	 * Setter method for property <tt>conditionPost</tt>.
	 * 
	 * @param conditionPost value to be assigned to property conditionPost
     */
	public void setConditionPost(String conditionPost) {
		this.conditionPost = conditionPost;
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
