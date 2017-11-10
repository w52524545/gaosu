package com.onway.web.controller.result;

import java.util.Date;

import com.onway.common.lang.Money;

public class ProductResult extends JsonResult {
	private static final long serialVersionUID = 741231858441822688L;
    
    private int id;

    private String shopId;
    
    //������
    private String shopName;
    
    //����ͼ
    private String shopUrl;
    
    //��Ʒ����
    private String comments;
    
    //������
    private int commentsCount;

    //�����߻�Ա���
    private String commentUserNum;
    
    //������ͷ��
    private String commentUserUrl;
    
    //����ʱ��
    private Date commentTime;
    
    //��Ʒ�ۿ�
    private int productOff;

    //��Ʒ�˷�
    private Money luggage = new Money(0,0);

    //�ͷ��绰
    private String cell;
    
    //������Ҫ����
    private int needPeople;
    
    //�Ѿ���������
    private int nowPeople;
    
    //����Ҫ���ŵ�����
    private int leftPeople;

    public int getLeftPeople() {
		return leftPeople;
	}

	public void setLeftPeople(int leftPeople) {
		this.leftPeople = leftPeople;
	}

	private String userId;

    private String productNo;

    private String productName;

    private String productType;

    private Money price = new Money(0, 0);

    private Money oldPrice = new Money(0, 0);

    private String status;

    private int stock;

    private String productUrl;

    private String delFlg;

    private String recommendFlg;
    
    public String getCell() {
		return cell;
	}

	public void setCell(String cell) {
		this.cell = cell;
	}

	public int getProductOff() {
		return productOff;
	}

	public void setProductOff(int productOff) {
		this.productOff = productOff;
	}

	public Date getCommentTime() {
		return commentTime;
	}

	public void setCommentTime(Date commentTime) {
		this.commentTime = commentTime;
	}

	public int getSoleCount() {
		return soleCount;
	}

	public void setSoleCount(int soleCount) {
		this.soleCount = soleCount;
	}

	private int soleCount;

    public static long getSerialVersionUID() {
        return serialVersionUID;
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

    
    public String getShopName() {
		return shopName;
	}

	public void setShopName(String shopName) {
		this.shopName = shopName;
	}

	
	public String getShopUrl() {
		return shopUrl;
	}

	public void setShopUrl(String shopUrl) {
		this.shopUrl = shopUrl;
	}

	
	public String getComments() {
		return comments;
	}

	public void setComments(String comments) {
		this.comments = comments;
	}

	public int getCommentsCount() {
		return commentsCount;
	}

	public void setCommentsCount(int commentsCount) {
		this.commentsCount = commentsCount;
	}

	public String getCommentUserNum() {
		return commentUserNum;
	}

	public void setCommentUserNum(String commentUserNum) {
		this.commentUserNum = commentUserNum;
	}

	public String getCommentUserUrl() {
		return commentUserUrl;
	}

	public void setCommentUserUrl(String commentUserUrl) {
		this.commentUserUrl = commentUserUrl;
	}

	public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getProductNo() {
        return productNo;
    }

    public void setProductNo(String productNo) {
        this.productNo = productNo;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getProductType() {
        return productType;
    }

    public void setProductType(String productType) {
        this.productType = productType;
    }

    public Money getPrice() {
        return price;
    }

    public void setPrice(Money price) {
        this.price = price;
    }

    public Money getOldPrice() {
        return oldPrice;
    }

    public void setOldPrice(Money oldPrice) {
        this.oldPrice = oldPrice;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    public String getProductUrl() {
        return productUrl;
    }

    public void setProductUrl(String productUrl) {
        this.productUrl = productUrl;
    }

    public String getDelFlg() {
        return delFlg;
    }

    public void setDelFlg(String delFlg) {
        this.delFlg = delFlg;
    }

    public String getRecommendFlg() {
        return recommendFlg;
    }

    public void setRecommendFlg(String recommendFlg) {
        this.recommendFlg = recommendFlg;
    }
    
    public Money getLuggage() {
		return luggage;
	}
    
    public void setLuggage(Money luggage) {
		if (luggage == null) {
			this.luggage = new Money(0, 0);
		} else {
			this.luggage = luggage;
		}
	}
    
    

    public int getNeedPeople() {
		return needPeople;
	}

	public void setNeedPeople(int needPeople) {
		this.needPeople = needPeople;
	}

	public int getNowPeople() {
		return nowPeople;
	}

	public void setNowPeople(int nowPeople) {
		this.nowPeople = nowPeople;
	}

	/**
	 * @param bizSucc
	 * @param errCode
	 * @param message
	 */
	public ProductResult(boolean bizSucc, String errCode, String message) {
		super(bizSucc, message, message);
	}



}
