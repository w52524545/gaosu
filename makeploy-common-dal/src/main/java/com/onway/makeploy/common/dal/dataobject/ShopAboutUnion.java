package com.onway.makeploy.common.dal.dataobject;

import java.util.Date;

public class ShopAboutUnion {
    private int    id;
    private String shopId;
    private String userId;
    private String shopName;
    private String cell;
    private int    shopStar;
    private String shopDec;
    private String shopAddr;
    private String unionFlg;
    private double loactionX;
    private double loactionY;
    private String shopUrl;
    private int    sellCount;
    private int    attentionCount;
    private int    collectCount;
    private double comment;
    private String creater;
    private Date   gmtCreate;
    private String modifier;
    private Date   gmtModified;
    private String memo;
    private String homeUnion;
    private String shopUp;
    private String delFlag;

    public String getHomeUnion() {
        return homeUnion;
    }

    public void setHomeUnion(String homeUnion) {
        this.homeUnion = homeUnion;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getShopId() {
        return shopId;
    }

    public void setShopId(String shopId) {
        this.shopId = shopId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getShopName() {
        return shopName;
    }

    public void setShopName(String shopName) {
        this.shopName = shopName;
    }

    public String getCell() {
        return cell;
    }

    public void setCell(String cell) {
        this.cell = cell;
    }

    public int getShopStar() {
        return shopStar;
    }

    public void setShopStar(int shopStar) {
        this.shopStar = shopStar;
    }

    public String getShopDec() {
        return shopDec;
    }

    public void setShopDec(String shopDec) {
        this.shopDec = shopDec;
    }

    public String getShopAddr() {
        return shopAddr;
    }

    public void setShopAddr(String shopAddr) {
        this.shopAddr = shopAddr;
    }

    public String getUnionFlg() {
        return unionFlg;
    }

    public void setUnionFlg(String unionFlg) {
        this.unionFlg = unionFlg;
    }

    public double getLoactionX() {
        return loactionX;
    }

    public void setLoactionX(double loactionX) {
        this.loactionX = loactionX;
    }

    public double getLoactionY() {
        return loactionY;
    }

    public void setLoactionY(double loactionY) {
        this.loactionY = loactionY;
    }

    public String getShopUrl() {
        return shopUrl;
    }

    public void setShopUrl(String shopUrl) {
        this.shopUrl = shopUrl;
    }

    public int getSellCount() {
        return sellCount;
    }

    public void setSellCount(int sellCount) {
        this.sellCount = sellCount;
    }

    public int getAttentionCount() {
        return attentionCount;
    }

    public void setAttentionCount(int attentionCount) {
        this.attentionCount = attentionCount;
    }

    public int getCollectCount() {
        return collectCount;
    }

    public void setCollectCount(int collectCount) {
        this.collectCount = collectCount;
    }

    public double getComment() {
        return comment;
    }

    public void setComment(double comment) {
        this.comment = comment;
    }

    public String getCreater() {
        return creater;
    }

    public void setCreater(String creater) {
        this.creater = creater;
    }

    public Date getGmtCreate() {
        return gmtCreate;
    }

    public void setGmtCreate(Date gmtCreate) {
        this.gmtCreate = gmtCreate;
    }

    public String getModifier() {
        return modifier;
    }

    public void setModifier(String modifier) {
        this.modifier = modifier;
    }

    public Date getGmtModified() {
        return gmtModified;
    }

    public void setGmtModified(Date gmtModified) {
        this.gmtModified = gmtModified;
    }

    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo;
    }

	public String getShopUp() {
		return shopUp;
	}

	public void setShopUp(String shopUp) {
		this.shopUp = shopUp;
	}

	public String getDelFlag() {
		return delFlag;
	}

	public void setDelFlag(String delFlag) {
		this.delFlag = delFlag;
	}
    
}
