package com.onway.web.controller.result;

public class MyShopResult extends JsonResult{

	public MyShopResult(boolean bizSucc, String errCode, String message) {
		super(bizSucc, errCode, message);
		// TODO Auto-generated constructor stub
	}


	//用户昵称
	private String nickName;
	
	//店铺被关注数
	private int attentionCount;
	
	//店铺被收藏数
	private int collectCount;
	
	//店铺号
	private String shopId;
	
	//店铺二维码
	private String qrUrl;
	
	//用户头像
	private String headUrl;
	
	//店铺名称
    private String shopName;
    
    //店铺url
  	private String shopUrl;
	

	public String getHeadUrl() {
		return headUrl;
	}

	public void setHeadUrl(String headUrl) {
		this.headUrl = headUrl;
	}

	public String getShopId() {
		return shopId;
	}

	public void setShopId(String shopId) {
		this.shopId = shopId;
	}

	public String getQrUrl() {
		return qrUrl;
	}

	public void setQrUrl(String qrUrl) {
		this.qrUrl = qrUrl;
	}

	public String getNickName() {
		return nickName;
	}

	public void setNickName(String nickName) {
		this.nickName = nickName;
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
	
	
}
