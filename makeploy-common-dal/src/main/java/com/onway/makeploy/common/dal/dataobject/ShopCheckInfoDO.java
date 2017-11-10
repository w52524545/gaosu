package com.onway.makeploy.common.dal.dataobject;

import java.util.Date;

public class ShopCheckInfoDO {
		String userId;
		String shopId;
		String userName;
		String userCell;
		String userJob;
		String shopName;
		String shopCell;
		String shopDec;
		String shopAddr;
		String applyType;
		String checkStatus;
		private Date gmtCreate;
		String cardAddr;
		String nowAddr;
		String memo;
		
		public Date getGmtCreate() {
			return gmtCreate;
		}
		public void setGmtCreate(Date gmtCreate) {
			this.gmtCreate = gmtCreate;
		}
		public String getCheckStatus() {
			return checkStatus;
		}
		public void setCheckStatus(String checkStatus) {
			this.checkStatus = checkStatus;
		}
		public String getUserId() {
			return userId;
		}
		public void setUserId(String userId) {
			this.userId = userId;
		}
		public String getShopId() {
			return shopId;
		}
		public void setShopId(String shopId) {
			this.shopId = shopId;
		}
		public String getApplyType() {
			return applyType;
		}
		public void setApplyType(String applyType) {
			this.applyType = applyType;
		}
		public String getUserName() {
			return userName;
		}
		public void setUserName(String userName) {
			this.userName = userName;
		}
		public String getUserCell() {
			return userCell;
		}
		public void setUserCell(String userCell) {
			this.userCell = userCell;
		}
		public String getUserJob() {
			return userJob;
		}
		public void setUserJob(String userJob) {
			this.userJob = userJob;
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
		public String getCardAddr() {
			return cardAddr;
		}
		public void setCardAddr(String cardAddr) {
			this.cardAddr = cardAddr;
		}
		public String getNowAddr() {
			return nowAddr;
		}
		public void setNowAddr(String nowAddr) {
			this.nowAddr = nowAddr;
		}
		public String getMemo() {
			return memo;
		}
		public void setMemo(String memo) {
			this.memo = memo;
		}
		
		
}
