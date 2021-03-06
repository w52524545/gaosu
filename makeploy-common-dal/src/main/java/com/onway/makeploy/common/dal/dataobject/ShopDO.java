/**
 * yingyinglicai.com Inc.
 * Copyright (c) 2013-2014 All Rights Reserved.
 */
 package com.onway.makeploy.common.dal.dataobject;

import com.onway.makeploy.common.dal.dataobject.BaseDO;

import java.util.Date;

/**
 * A data object class directly models database table <tt>makeploy_shop</tt>.
 *
 * This file is generated by <tt>onway-gen-dal</tt>, a DAL (Data Access Layer)
 * code generation utility specially developed for <tt>onway</tt> project.
 * 
 * PLEASE DO NOT MODIFY THIS FILE MANUALLY, or else your modification may
 * be OVERWRITTEN by someone else. To modify the file, you should go to 
 * directory <tt>(project-home)/biz/dal/src/conf/dalgen</tt>, and 
 * find the corresponding configuration file (<tt>tables/makeploy_shop.xml</tt>). 
 * Modify the configuration file according to your needs, then run <tt>onway-gen-dal</tt> 
 * to generate this file.
 *
 * @author guangdong.li
 * @version $Id: ShopDO.java,v 1.0 2013/10/29 07:34:20 guangdong.li Exp $
 */
public class ShopDO extends BaseDO {
    private static final long serialVersionUID = 741231858441822688L;

    //========== properties ==========

	/**
	 * This property corresponds to db column <tt>ID</tt>.
	 */
	private int id;

	/**
	 * This property corresponds to db column <tt>SHOP_ID</tt>.
	 */
	private String shopId;

	/**
	 * This property corresponds to db column <tt>USER_ID</tt>.
	 */
	private String userId;

	/**
	 * This property corresponds to db column <tt>SHOP_NAME</tt>.
	 */
	private String shopName;

	/**
	 * This property corresponds to db column <tt>CELL</tt>.
	 */
	private String cell;

	/**
	 * This property corresponds to db column <tt>SHOP_STAR</tt>.
	 */
	private int shopStar;

	/**
	 * This property corresponds to db column <tt>INDUSTRY</tt>.
	 */
	private String industry;

	/**
	 * This property corresponds to db column <tt>SHOP_DEC</tt>.
	 */
	private String shopDec;

	/**
	 * This property corresponds to db column <tt>SHOP_ADDR</tt>.
	 */
	private String shopAddr;

	/**
	 * This property corresponds to db column <tt>UNION_FLG</tt>.
	 */
	private String unionFlg;

	/**
	 * This property corresponds to db column <tt>LOACTION_X</tt>.
	 */
	private double loactionX;

	/**
	 * This property corresponds to db column <tt>LOACTION_Y</tt>.
	 */
	private double loactionY;

	/**
	 * This property corresponds to db column <tt>SHOP_URL</tt>.
	 */
	private String shopUrl;

	/**
	 * This property corresponds to db column <tt>SELL_COUNT</tt>.
	 */
	private int sellCount;

	/**
	 * This property corresponds to db column <tt>ATTENTION_COUNT</tt>.
	 */
	private int attentionCount;

	/**
	 * This property corresponds to db column <tt>COLLECT_COUNT</tt>.
	 */
	private int collectCount;

	/**
	 * This property corresponds to db column <tt>QR_URL</tt>.
	 */
	private String qrUrl;

	/**
	 * This property corresponds to db column <tt>SHOP_UP</tt>.
	 */
	private String shopUp;

	/**
	 * This property corresponds to db column <tt>DEL_FLAG</tt>.
	 */
	private String delFlag;

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
     * Getter method for property <tt>shopName</tt>.
     *
     * @return property value of shopName
     */
	public String getShopName() {
		return shopName;
	}
	
	/**
	 * Setter method for property <tt>shopName</tt>.
	 * 
	 * @param shopName value to be assigned to property shopName
     */
	public void setShopName(String shopName) {
		this.shopName = shopName;
	}

    /**
     * Getter method for property <tt>cell</tt>.
     *
     * @return property value of cell
     */
	public String getCell() {
		return cell;
	}
	
	/**
	 * Setter method for property <tt>cell</tt>.
	 * 
	 * @param cell value to be assigned to property cell
     */
	public void setCell(String cell) {
		this.cell = cell;
	}

    /**
     * Getter method for property <tt>shopStar</tt>.
     *
     * @return property value of shopStar
     */
	public int getShopStar() {
		return shopStar;
	}
	
	/**
	 * Setter method for property <tt>shopStar</tt>.
	 * 
	 * @param shopStar value to be assigned to property shopStar
     */
	public void setShopStar(int shopStar) {
		this.shopStar = shopStar;
	}

    /**
     * Getter method for property <tt>industry</tt>.
     *
     * @return property value of industry
     */
	public String getIndustry() {
		return industry;
	}
	
	/**
	 * Setter method for property <tt>industry</tt>.
	 * 
	 * @param industry value to be assigned to property industry
     */
	public void setIndustry(String industry) {
		this.industry = industry;
	}

    /**
     * Getter method for property <tt>shopDec</tt>.
     *
     * @return property value of shopDec
     */
	public String getShopDec() {
		return shopDec;
	}
	
	/**
	 * Setter method for property <tt>shopDec</tt>.
	 * 
	 * @param shopDec value to be assigned to property shopDec
     */
	public void setShopDec(String shopDec) {
		this.shopDec = shopDec;
	}

    /**
     * Getter method for property <tt>shopAddr</tt>.
     *
     * @return property value of shopAddr
     */
	public String getShopAddr() {
		return shopAddr;
	}
	
	/**
	 * Setter method for property <tt>shopAddr</tt>.
	 * 
	 * @param shopAddr value to be assigned to property shopAddr
     */
	public void setShopAddr(String shopAddr) {
		this.shopAddr = shopAddr;
	}

    /**
     * Getter method for property <tt>unionFlg</tt>.
     *
     * @return property value of unionFlg
     */
	public String getUnionFlg() {
		return unionFlg;
	}
	
	/**
	 * Setter method for property <tt>unionFlg</tt>.
	 * 
	 * @param unionFlg value to be assigned to property unionFlg
     */
	public void setUnionFlg(String unionFlg) {
		this.unionFlg = unionFlg;
	}

    /**
     * Getter method for property <tt>loactionX</tt>.
     *
     * @return property value of loactionX
     */
	public double getLoactionX() {
		return loactionX;
	}
	
	/**
	 * Setter method for property <tt>loactionX</tt>.
	 * 
	 * @param loactionX value to be assigned to property loactionX
     */
	public void setLoactionX(double loactionX) {
		this.loactionX = loactionX;
	}

    /**
     * Getter method for property <tt>loactionY</tt>.
     *
     * @return property value of loactionY
     */
	public double getLoactionY() {
		return loactionY;
	}
	
	/**
	 * Setter method for property <tt>loactionY</tt>.
	 * 
	 * @param loactionY value to be assigned to property loactionY
     */
	public void setLoactionY(double loactionY) {
		this.loactionY = loactionY;
	}

    /**
     * Getter method for property <tt>shopUrl</tt>.
     *
     * @return property value of shopUrl
     */
	public String getShopUrl() {
		return shopUrl;
	}
	
	/**
	 * Setter method for property <tt>shopUrl</tt>.
	 * 
	 * @param shopUrl value to be assigned to property shopUrl
     */
	public void setShopUrl(String shopUrl) {
		this.shopUrl = shopUrl;
	}

    /**
     * Getter method for property <tt>sellCount</tt>.
     *
     * @return property value of sellCount
     */
	public int getSellCount() {
		return sellCount;
	}
	
	/**
	 * Setter method for property <tt>sellCount</tt>.
	 * 
	 * @param sellCount value to be assigned to property sellCount
     */
	public void setSellCount(int sellCount) {
		this.sellCount = sellCount;
	}

    /**
     * Getter method for property <tt>attentionCount</tt>.
     *
     * @return property value of attentionCount
     */
	public int getAttentionCount() {
		return attentionCount;
	}
	
	/**
	 * Setter method for property <tt>attentionCount</tt>.
	 * 
	 * @param attentionCount value to be assigned to property attentionCount
     */
	public void setAttentionCount(int attentionCount) {
		this.attentionCount = attentionCount;
	}

    /**
     * Getter method for property <tt>collectCount</tt>.
     *
     * @return property value of collectCount
     */
	public int getCollectCount() {
		return collectCount;
	}
	
	/**
	 * Setter method for property <tt>collectCount</tt>.
	 * 
	 * @param collectCount value to be assigned to property collectCount
     */
	public void setCollectCount(int collectCount) {
		this.collectCount = collectCount;
	}

    /**
     * Getter method for property <tt>qrUrl</tt>.
     *
     * @return property value of qrUrl
     */
	public String getQrUrl() {
		return qrUrl;
	}
	
	/**
	 * Setter method for property <tt>qrUrl</tt>.
	 * 
	 * @param qrUrl value to be assigned to property qrUrl
     */
	public void setQrUrl(String qrUrl) {
		this.qrUrl = qrUrl;
	}

    /**
     * Getter method for property <tt>shopUp</tt>.
     *
     * @return property value of shopUp
     */
	public String getShopUp() {
		return shopUp;
	}
	
	/**
	 * Setter method for property <tt>shopUp</tt>.
	 * 
	 * @param shopUp value to be assigned to property shopUp
     */
	public void setShopUp(String shopUp) {
		this.shopUp = shopUp;
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
