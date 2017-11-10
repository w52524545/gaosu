package com.onway.web.controller.result;

import com.onway.common.lang.Money;

public class UserCenterResult extends JsonResult {

	public UserCenterResult(boolean bizSucc) {
		super(bizSucc);
		// TODO Auto-generated constructor stub
	}
	//用户昵称
	private String nickName;
	
	//用户账号(手机号)
	private String cell;
	
	//用户头像
	private String headUrl;
	
	//会员编号
	private String userNum;
	
	//消费总额
	private Money consumeAccout = new Money(0, 0);

	//分红总额
	private Money dividendAccout = new Money(0, 0);

	//提现总额
	private Money withdrawAccout = new Money(0, 0);

    //账户余额
	private Money balance = new Money(0, 0);
	
	//近日收益
	private Money incomeWeek = new Money(0, 0);
	
	//累计收入
	private Money totalBalance = new Money(0, 0);
	
	//销售额
    private Money sellCount= new Money(0, 0);
		
	//收入额
    private Money incomeCount= new Money(0, 0);
	

    
    
	public String getUserNum() {
		return userNum;
	}

	public void setUserNum(String userNum) {
		this.userNum = userNum;
	}

	public String getCell() {
		return cell;
	}

	public void setCell(String cell) {
		this.cell = cell;
	}

	public Money getSellCount() {
		return sellCount;
	}

	public void setSellCount(Money sellCount) {
		this.sellCount = sellCount;
	}

	public Money getIncomeCount() {
		return incomeCount;
	}

	public void setIncomeCount(Money incomeCount) {
		this.incomeCount = incomeCount;
	}

	public Money getIncomeWeek() {
		return incomeWeek;
	}

	public void setIncomeWeek(Money incomeWeek) {
		this.incomeWeek = incomeWeek;
	}

	public Money getTotalBalance() {
		return totalBalance;
	}

	public void setTotalBalance(Money totalBalance) {
		this.totalBalance = totalBalance;
	}

	public String getNickName() {
		return nickName;
	}

	public void setNickName(String nickName) {
		this.nickName = nickName;
	}

	public String getHeadUrl() {
		return headUrl;
	}

	public void setHeadUrl(String headUrl) {
		this.headUrl = headUrl;
	}

	public Money getConsumeAccout() {
		return consumeAccout;
	}

	public void setConsumeAccout(Money consumeAccout) {
		this.consumeAccout = consumeAccout;
	}

	public Money getDividendAccout() {
		return dividendAccout;
	}

	public void setDividendAccout(Money dividendAccout) {
		this.dividendAccout = dividendAccout;
	}

	public Money getWithdrawAccout() {
		return withdrawAccout;
	}

	public void setWithdrawAccout(Money withdrawAccout) {
		this.withdrawAccout = withdrawAccout;
	}

	public Money getBalance() {
		return balance;
	}

	public void setBalance(Money balance) {
		this.balance = balance;
	}


}
