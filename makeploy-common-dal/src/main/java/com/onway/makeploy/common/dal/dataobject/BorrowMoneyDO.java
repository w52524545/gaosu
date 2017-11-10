/**
 * yingyinglicai.com Inc.
 * Copyright (c) 2013-2014 All Rights Reserved.
 */
 package com.onway.makeploy.common.dal.dataobject;

import com.onway.makeploy.common.dal.dataobject.BaseDO;

import com.onway.common.lang.Money;
import java.util.Date;

/**
 * A data object class directly models database table <tt>makeploy_borrow_money</tt>.
 *
 * This file is generated by <tt>onway-gen-dal</tt>, a DAL (Data Access Layer)
 * code generation utility specially developed for <tt>onway</tt> project.
 * 
 * PLEASE DO NOT MODIFY THIS FILE MANUALLY, or else your modification may
 * be OVERWRITTEN by someone else. To modify the file, you should go to 
 * directory <tt>(project-home)/biz/dal/src/conf/dalgen</tt>, and 
 * find the corresponding configuration file (<tt>tables/makeploy_borrow_money.xml</tt>). 
 * Modify the configuration file according to your needs, then run <tt>onway-gen-dal</tt> 
 * to generate this file.
 *
 * @author guangdong.li
 * @version $Id: BorrowMoneyDO.java,v 1.0 2013/10/29 07:34:20 guangdong.li Exp $
 */
public class BorrowMoneyDO extends BaseDO {
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
	 * This property corresponds to db column <tt>BORROW_ID</tt>.
	 */
	private String borrowId;

	/**
	 * This property corresponds to db column <tt>BORROW_NAME</tt>.
	 */
	private String borrowName;

	/**
	 * This property corresponds to db column <tt>BORROW_CELL</tt>.
	 */
	private String borrowCell;

	/**
	 * This property corresponds to db column <tt>BORROW_CARD</tt>.
	 */
	private String borrowCard;

	/**
	 * This property corresponds to db column <tt>SPONSOR_NAME</tt>.
	 */
	private String sponsorName;

	/**
	 * This property corresponds to db column <tt>SPONSOR_CELL</tt>.
	 */
	private String sponsorCell;

	/**
	 * This property corresponds to db column <tt>SPONSOR_CARD</tt>.
	 */
	private String sponsorCard;

	/**
	 * This property corresponds to db column <tt>USE_WAY</tt>.
	 */
	private String useWay;

	/**
	 * This property corresponds to db column <tt>BORROW_STATE</tt>.
	 */
	private String borrowState;

	/**
	 * This property corresponds to db column <tt>BORROW_MONEY</tt>.
	 */
	private Money borrowMoney = new Money(0, 0);

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
     * Getter method for property <tt>borrowId</tt>.
     *
     * @return property value of borrowId
     */
	public String getBorrowId() {
		return borrowId;
	}
	
	/**
	 * Setter method for property <tt>borrowId</tt>.
	 * 
	 * @param borrowId value to be assigned to property borrowId
     */
	public void setBorrowId(String borrowId) {
		this.borrowId = borrowId;
	}

    /**
     * Getter method for property <tt>borrowName</tt>.
     *
     * @return property value of borrowName
     */
	public String getBorrowName() {
		return borrowName;
	}
	
	/**
	 * Setter method for property <tt>borrowName</tt>.
	 * 
	 * @param borrowName value to be assigned to property borrowName
     */
	public void setBorrowName(String borrowName) {
		this.borrowName = borrowName;
	}

    /**
     * Getter method for property <tt>borrowCell</tt>.
     *
     * @return property value of borrowCell
     */
	public String getBorrowCell() {
		return borrowCell;
	}
	
	/**
	 * Setter method for property <tt>borrowCell</tt>.
	 * 
	 * @param borrowCell value to be assigned to property borrowCell
     */
	public void setBorrowCell(String borrowCell) {
		this.borrowCell = borrowCell;
	}

    /**
     * Getter method for property <tt>borrowCard</tt>.
     *
     * @return property value of borrowCard
     */
	public String getBorrowCard() {
		return borrowCard;
	}
	
	/**
	 * Setter method for property <tt>borrowCard</tt>.
	 * 
	 * @param borrowCard value to be assigned to property borrowCard
     */
	public void setBorrowCard(String borrowCard) {
		this.borrowCard = borrowCard;
	}

    /**
     * Getter method for property <tt>sponsorName</tt>.
     *
     * @return property value of sponsorName
     */
	public String getSponsorName() {
		return sponsorName;
	}
	
	/**
	 * Setter method for property <tt>sponsorName</tt>.
	 * 
	 * @param sponsorName value to be assigned to property sponsorName
     */
	public void setSponsorName(String sponsorName) {
		this.sponsorName = sponsorName;
	}

    /**
     * Getter method for property <tt>sponsorCell</tt>.
     *
     * @return property value of sponsorCell
     */
	public String getSponsorCell() {
		return sponsorCell;
	}
	
	/**
	 * Setter method for property <tt>sponsorCell</tt>.
	 * 
	 * @param sponsorCell value to be assigned to property sponsorCell
     */
	public void setSponsorCell(String sponsorCell) {
		this.sponsorCell = sponsorCell;
	}

    /**
     * Getter method for property <tt>sponsorCard</tt>.
     *
     * @return property value of sponsorCard
     */
	public String getSponsorCard() {
		return sponsorCard;
	}
	
	/**
	 * Setter method for property <tt>sponsorCard</tt>.
	 * 
	 * @param sponsorCard value to be assigned to property sponsorCard
     */
	public void setSponsorCard(String sponsorCard) {
		this.sponsorCard = sponsorCard;
	}

    /**
     * Getter method for property <tt>useWay</tt>.
     *
     * @return property value of useWay
     */
	public String getUseWay() {
		return useWay;
	}
	
	/**
	 * Setter method for property <tt>useWay</tt>.
	 * 
	 * @param useWay value to be assigned to property useWay
     */
	public void setUseWay(String useWay) {
		this.useWay = useWay;
	}

    /**
     * Getter method for property <tt>borrowState</tt>.
     *
     * @return property value of borrowState
     */
	public String getBorrowState() {
		return borrowState;
	}
	
	/**
	 * Setter method for property <tt>borrowState</tt>.
	 * 
	 * @param borrowState value to be assigned to property borrowState
     */
	public void setBorrowState(String borrowState) {
		this.borrowState = borrowState;
	}

    /**
     * Getter method for property <tt>borrowMoney</tt>.
     *
     * @return property value of borrowMoney
     */
	public Money getBorrowMoney() {
		return borrowMoney;
	}
	
	/**
	 * Setter method for property <tt>borrowMoney</tt>.
	 * 
	 * @param borrowMoney value to be assigned to property borrowMoney
     */
	public void setBorrowMoney(Money borrowMoney) {
		if (borrowMoney == null) {
			this.borrowMoney = new Money(0, 0);
		} else {
			this.borrowMoney = borrowMoney;
		}
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