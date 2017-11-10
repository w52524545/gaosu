package com.onway.makeploy.common.dal.dataobject;

import com.onway.common.lang.Money;

public class ShopProInfoDO {
    String proImage;
    String proName;
    String fatherType;
    String childrenType;
    Money  price;
    String soleCount;
    int    stock;
    String fatherName;
    String childrenName;
    String rFlag;
    String shopId;
    String productNo;
    String praflag;
    
    private double proportionReturn;
	private double proportionIntegral;

    public String getFatherName() {
        return fatherName;
    }

    public void setFatherName(String fatherName) {
        this.fatherName = fatherName;
    }

    public Money getPrice() {
        return price;
    }

    public void setPrice(Money price) {
        this.price = price;
    }

    public String getrFlag() {
        return rFlag;
    }

    public void setrFlag(String rFlag) {
        this.rFlag = rFlag;
    }

    public String getProImage() {
        return proImage;
    }

    public void setProImage(String proImage) {
        this.proImage = proImage;
    }

    public String getProName() {
        return proName;
    }

    public void setProName(String proName) {
        this.proName = proName;
    }

    public String getFatherType() {
        return fatherType;
    }

    public void setFatherType(String fatherType) {
        this.fatherType = fatherType;
    }

    public String getChildrenType() {
        return childrenType;
    }

    public void setChildrenType(String childrenType) {
        this.childrenType = childrenType;
    }

    public String getSoleCount() {
        return soleCount;
    }

    public void setSoleCount(String soleCount) {
        this.soleCount = soleCount;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    public String getChildrenName() {
        return childrenName;
    }

    public void setChildrenName(String childrenName) {
        this.childrenName = childrenName;
    }

	public String getShopId() {
		return shopId;
	}

	public void setShopId(String shopId) {
		this.shopId = shopId;
	}

	public String getProductNo() {
		return productNo;
	}

	public void setProductNo(String productNo) {
		this.productNo = productNo;
	}

	public double getProportionReturn() {
		return proportionReturn;
	}

	public void setProportionReturn(double proportionReturn) {
		this.proportionReturn = proportionReturn;
	}

	public double getProportionIntegral() {
		return proportionIntegral;
	}

	public void setProportionIntegral(double proportionIntegral) {
		this.proportionIntegral = proportionIntegral;
	}

	public String getPraflag() {
		return praflag;
	}

	public void setPraflag(String praflag) {
		this.praflag = praflag;
	}
    
    
}
