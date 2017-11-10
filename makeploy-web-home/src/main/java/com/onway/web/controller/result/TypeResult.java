package com.onway.web.controller.result;

import java.io.Serializable;
import java.util.List;

import com.onway.makeploy.common.dal.dataobject.CategoryDO;

public class TypeResult implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 8021100753401718982L;
	private String type;
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	private List<String>  list;
	 public List<String> getList() {
		return list;
	}
	public void setList(List<String> list) {
		this.list = list;
	}
	private List<CategoryDO>  listObject;
	 private String fatherType;
	 private String fatherTypeIcon;
	
	public List<CategoryDO> getListObject() {
		return listObject;
	}
	public void setListObject(List<CategoryDO> listObject) {
		this.listObject = listObject;
	}
	public String getFatherType() {
		return fatherType;
	}
	public void setFatherType(String fatherType) {
		this.fatherType = fatherType;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	public String getFatherTypeIcon() {
		return fatherTypeIcon;
	}
	public void setFatherTypeIcon(String fatherTypeIcon) {
		this.fatherTypeIcon = fatherTypeIcon;
	}

}
