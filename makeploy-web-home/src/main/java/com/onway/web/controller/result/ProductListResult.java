package com.onway.web.controller.result;

public class ProductListResult {
	//标题 
    private String title;
    //商品名
    private String name;
    //商品类型
    private String type;
    //区服ID
    private String makeployId;
    //商品编号
    private String productNo;
    //商品价格
    private long price;
    //商品库存
    private String count;
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getmakeployId() {
		return makeployId;
	}
	public void setmakeployId(String makeployId) {
		this.makeployId = makeployId;
	}
	public String getProductNo() {
		return productNo;
	}
	public void setProductNo(String productNo) {
		this.productNo = productNo;
	}
	public long getPrice() {
		return price;
	}
	public void setPrice(long price) {
		this.price = price;
	}
	public String getCount() {
		return count;
	}
	public void setCount(String count) {
		this.count = count;
	}
    
}
