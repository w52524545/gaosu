package com.onway.makeploy.common.dal.dataobject;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class OrderInfo extends BaseDO {
    private static final long serialVersionUID = 741231858441822688L;
    private String            userName;
    private String            nickName;
    private String            cell;
    private String            orderId;
    private String            orderNo;
    private String            transportNo;
    private String            parflag;
    private String            shopId;
    private String            shopName;
    private String            shopCell;
    private String            orderType;
    private String            provience;
    private String            city;
    private String            district;
    private String            addr;
    private String            productNo;
    private String            productName;
    private String            productSizeType;
    private String            eachproCount;
    private int               productCount;
    private BigDecimal        eachproPrice;
    private BigDecimal        orderPrice;
    private String            payStatus;
    private String            sendGoods;
    private Date              payTime;
    private String            payWay;
    private String            orderStatus;
    private BigDecimal        luggage;
    private String            delflag;
    private long              expectedReturn;
    private long              expectedIntegral;
    private Date              gmtCreat;
    private String            stringgmtCreat;
    private String            productType;
    private String            children;
    private String            memo;
    private List<ProImageDO>  moren            = new ArrayList<ProImageDO>();
    private List<ProImageDO>  banner           = new ArrayList<ProImageDO>();
    private List<ProImageDO>  proDec           = new ArrayList<ProImageDO>();

    public String getOrderType() {
        return orderType;
    }

    public void setOrderType(String orderType) {
        this.orderType = orderType;
    }

    public String getCell() {
        return cell;
    }

    public void setCell(String cell) {
        this.cell = cell;
    }

    public List<ProImageDO> getMoren() {
        return moren;
    }

    public void setMoren(List<ProImageDO> moren) {
        this.moren = moren;
    }

    public List<ProImageDO> getBanner() {
        return banner;
    }

    public void setBanner(List<ProImageDO> banner) {
        this.banner = banner;
    }

    public List<ProImageDO> getProDec() {
        return proDec;
    }

    public void setProDec(List<ProImageDO> proDec) {
        this.proDec = proDec;
    }

    private BigDecimal price;

    public String getProductType() {
        return productType;
    }

    public void setProductType(String productType) {
        this.productType = productType;
    }

    public String getChildren() {
        return children;
    }

    public void setChildren(String children) {
        this.children = children;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public String getUserName() {
        return userName;
    }

    public BigDecimal getEachproPrice() {
        return eachproPrice;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getProductSizeType() {
        return productSizeType;
    }

    public void setOrderPrice(BigDecimal orderPrice) {
        this.orderPrice = orderPrice;
    }

    public void setLuggage(BigDecimal luggage) {
        this.luggage = luggage;
    }

    public String getParflag() {
        return parflag;
    }

    public void setParflag(String parflag) {
        this.parflag = parflag;
    }

    public void setProductSizeType(String productSizeType) {
        this.productSizeType = productSizeType;
    }

    public void setEachproPrice(BigDecimal eachproPrice) {
        this.eachproPrice = eachproPrice;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getStringgmtCreat() {
        return stringgmtCreat;
    }

    public void setStringgmtCreat(String stringgmtCreat) {
        this.stringgmtCreat = stringgmtCreat;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getShopId() {
        return shopId;
    }

    public void setShopId(String shopId) {
        this.shopId = shopId;
    }

    public String getProvience() {
        return provience;
    }

    public void setProvience(String provience) {
        this.provience = provience;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    public String getAddr() {
        return addr;
    }

    public void setAddr(String addr) {
        this.addr = addr;
    }

    public String getProductNo() {
        return productNo;
    }

    public void setProductNo(String productNo) {
        this.productNo = productNo;
    }

    public String getEachproCount() {
        return eachproCount;
    }

    public void setEachproCount(String eachproCount) {
        this.eachproCount = eachproCount;
    }

    public int getProductCount() {
        return productCount;
    }

    public void setProductCount(int productCount) {
        this.productCount = productCount;
    }

    public String getPayStatus() {
        return payStatus;
    }

    public void setPayStatus(String payStatus) {
        this.payStatus = payStatus;
    }

    public String getSendGoods() {
        return sendGoods;
    }

    public void setSendGoods(String sendGoods) {
        this.sendGoods = sendGoods;
    }

    public Date getPayTime() {
        return payTime;
    }

    public void setPayTime(Date payTime) {
        this.payTime = payTime;
    }

    public String getPayWay() {
        return payWay;
    }

    public void setPayWay(String payWay) {
        this.payWay = payWay;
    }

    public String getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(String orderStatus) {
        this.orderStatus = orderStatus;
    }

    public BigDecimal getOrderPrice() {
        return orderPrice;
    }

    public BigDecimal getLuggage() {
        return luggage;
    }

    public String getDelflag() {
        return delflag;
    }

    public void setDelflag(String delflag) {
        this.delflag = delflag;
    }

    public long getExpectedReturn() {
        return expectedReturn;
    }

    public void setExpectedReturn(long expectedReturn) {
        this.expectedReturn = expectedReturn;
    }

    public long getExpectedIntegral() {
        return expectedIntegral;
    }

    public void setExpectedIntegral(long expectedIntegral) {
        this.expectedIntegral = expectedIntegral;
    }

    public Date getGmtCreat() {
        return gmtCreat;
    }

    public void setGmtCreat(Date gmtCreat) {
        this.gmtCreat = gmtCreat;
    }

	public String getMemo() {
		return memo;
	}

	public void setMemo(String memo) {
		this.memo = memo;
	}

	public String getShopName() {
		return shopName;
	}

	public void setShopName(String shopName) {
		this.shopName = shopName;
	}

	public String getShopCell() {
		return shopCell;
	}

	public void setShopCell(String shopCell) {
		this.shopCell = shopCell;
	}

	public String getOrderNo() {
		return orderNo;
	}

	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}

	public String getTransportNo() {
		return transportNo;
	}

	public void setTransportNo(String transportNo) {
		this.transportNo = transportNo;
	}
    
    

}
