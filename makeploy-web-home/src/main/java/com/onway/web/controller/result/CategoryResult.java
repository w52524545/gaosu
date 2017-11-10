package com.onway.web.controller.result;

public class CategoryResult  extends JsonResult{

	public CategoryResult(boolean bizSucc) {
		super(bizSucc);
		// TODO Auto-generated constructor stub
	}
	
	private String fatherName;
	public String getFatherName() {
		return fatherName;
	}
	public void setFatherName(String fatherName) {
		this.fatherName = fatherName;
	}
	public String getChildrenName() {
		return childrenName;
	}
	public void setChildrenName(String childrenName) {
		this.childrenName = childrenName;
	}

	private String childrenName;
	

}
