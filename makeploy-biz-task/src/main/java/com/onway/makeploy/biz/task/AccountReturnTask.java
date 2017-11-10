package com.onway.makeploy.biz.task;

import java.util.List;

import org.apache.commons.lang.StringUtils;

import com.onway.common.lang.Money;
import com.onway.makeploy.common.dal.daointerface.AccountDAO;
import com.onway.makeploy.common.dal.daointerface.OrderDAO;
import com.onway.makeploy.common.dal.daointerface.SysConfigDAO;
import com.onway.makeploy.common.dal.daointerface.UserDAO;
import com.onway.makeploy.common.dal.dataobject.AccountDO;
import com.onway.makeploy.common.dal.dataobject.OrderDO;
import com.onway.makeploy.common.dal.dataobject.UserDO;
import com.onway.makeploy.core.service.code.CodeGenerateComponent;

public class AccountReturnTask extends AbstractTask {
	private OrderDAO orderDAO;
	private SysConfigDAO sysConfigDAO;
	private UserDAO userDAO;
	private AccountDAO accountDAO;
	// 总行数
	private int rowCount;
	// 当前页
	private int currentPage;
	// 每页大小
	private int pageSize;
	// 页数
	private int pageCount;
	
	private int startCount;

	public CodeGenerateComponent getCodeGenerateComponent() {
		return codeGenerateComponent;
	}

	public void setCodeGenerateComponent(
			CodeGenerateComponent codeGenerateComponent) {
		this.codeGenerateComponent = codeGenerateComponent;
	}

	private CodeGenerateComponent codeGenerateComponent;

	public OrderDAO getOrderDAO() {
		return orderDAO;
	}

	public void setOrderDAO(OrderDAO orderDAO) {
		this.orderDAO = orderDAO;
	}

	public SysConfigDAO getSysConfigDAO() {
		return sysConfigDAO;
	}

	public void setSysConfigDAO(SysConfigDAO sysConfigDAO) {
		this.sysConfigDAO = sysConfigDAO;
	}

	public UserDAO getUserDAO() {
		return userDAO;
	}

	public void setUserDAO(UserDAO userDAO) {
		this.userDAO = userDAO;
	}

	public AccountDAO getAccountDAO() {
		return accountDAO;
	}

	public void setAccountDAO(AccountDAO accountDAO) {
		this.accountDAO = accountDAO;
	}

	@Override
	protected boolean canProcess() {
		// TODO Auto-generated method stub
		return true;
	}

	/* 自动返现,积分 */
	@Override
	protected void process() {
		String sysValue = sysConfigDAO.selectByKey("ACCOUNT_RETURN_MENU").getSysValue();
		if(StringUtils.equals(sysValue, "OPEN")){
			pageSize = 100;
			currentPage = 0;
			rowCount = orderDAO.selectSucceesCount();
			startCount = 0;
			// 时间
			int time = Integer.parseInt(sysConfigDAO.selectByKey("RETURN_TIMES")
					.getSysValue());// 获取返还期数
			// 总页数
			pageCount = rowCount / pageSize + 1;
			try {
				for (int i = 0; i < pageCount; i++) {
					// 已完成订单
					List<OrderDO> proList = orderDAO.selectSuccees(startCount,pageSize);
					runProcess(time, proList);
					currentPage = currentPage + 1;
					startCount = startCount + pageSize;
				}
	
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * 返现返积分
	 * @param time
	 * @param proList
	 */
	private void runProcess(int time, List<OrderDO> proList) {
		long expectIntegral;
		Money expectReturnCash;
		long actualIntegral;
		Money actualReturnCash;
		Money cash1, cash2 = new Money((long) 0, 1);
		long caculate1, caculate2;
		// 账户余额
		Money balance;
		// 可提现余额
		// Money withDrawAccount;
		// 积分
		long pointAccout;
		try {
			// 已完成订单
			for (int i = 0; i < proList.size(); i++) {
				OrderDO orderDO = new OrderDO();
				UserDO userDO = new UserDO();
				AccountDO accountDO = new AccountDO();
				orderDO = proList.get(i);
				// 查询用户信息
				userDO = userDAO.selectUserInfoByUserId(orderDO.getUserId());
				if (null == userDO) {
					continue;
				}
				// 余额
				balance = userDO.getBalance();
				// 可提现余额
				// withDrawAccount = userDO.getWithdrawAccout();
				// 用户现有积分
				pointAccout = userDO.getPointAccout();
				// 订单预期积分总额
				expectIntegral = orderDO.getExpectedIntegral();
				// 预期返现总额
				expectReturnCash = orderDO.getExpectedReturn();
				// 已返积分
				actualIntegral = orderDO.getActualIntegral();
				// 已反现金
				actualReturnCash = orderDO.getActualReturn();
				// 预期积分 - 已返积分
				caculate1 = expectIntegral - actualIntegral;// 余积分
				// 分期返现积分
				caculate2 = expectIntegral / time;// 每期积分
				caculate2 = caculate2 > 1 ? caculate2 : 1;
				cash1 = expectReturnCash.subtract(actualReturnCash);// 余额
				// 设置最小单位
				cash2 = new Money((long) 0, 1);// 设置1分 money
				// 每期返现金额
				cash2 = expectReturnCash.divideBy(time).greaterEqualThan(cash2) ? expectReturnCash
						: cash2;// 每期额
				// 用户ID设定
				accountDO.setUserId(orderDO.getUserId());
				if (cash2.lessThan(cash1)) {
					actualReturnCash.addTo(cash2);
					balance.addTo(cash2);
					// withDrawAccount.addTo(cash2);
					accountDO.setAmount(cash2);
					// 积分比较
					if (caculate1 > caculate2) {
						actualIntegral = actualIntegral + caculate2;
						pointAccout = pointAccout + caculate2;
						accountDO.setPoint((int) caculate2);
					} else if (caculate1 > 0) {
						actualIntegral = actualIntegral + caculate1;
						pointAccout = pointAccout + caculate1;
						accountDO.setPoint((int) caculate1);
					} else {
						accountDO.setPoint(0);
					}
				} else if (new Money((long) 0, 0).lessThan(cash1)) {
					actualReturnCash.addTo(cash1);
					balance.addTo(cash1);
					// withDrawAccount.addTo(cash1);
					accountDO.setAmount(cash1);
					if (caculate1 > caculate2) {
						actualIntegral = actualIntegral + caculate2;
						pointAccout = pointAccout + caculate2;
						accountDO.setPoint((int) caculate2);
					} else if (caculate1 > 0) {
						actualIntegral = actualIntegral + caculate1;
						pointAccout = pointAccout + caculate1;
						accountDO.setPoint((int) caculate1);
						orderDO.setReturnFlg("1");
					} else {
						orderDO.setReturnFlg("1");
						accountDO.setPoint(0);
					}
				} else {
					accountDO.setAmount(new Money(0, 0));
					if (caculate1 > caculate2) {
						actualIntegral = actualIntegral + caculate2;
						pointAccout = pointAccout + caculate2;
						accountDO.setPoint((int) caculate2);
					} else if (caculate1 > 0) {
						actualIntegral = actualIntegral + caculate1;
						pointAccout = pointAccout + caculate1;
						accountDO.setPoint((int) caculate1);
						orderDO.setReturnFlg("1");
					} else {
						orderDO.setReturnFlg("1");
						accountDO.setPoint(0);
					}
				}
				orderDAO.updateReturn(actualReturnCash, actualIntegral,
						orderDO.getReturnFlg(), orderDO.getOrderId(),
						orderDO.getOrderNo());
				userDAO.updateUserReturn((int) pointAccout, balance,
						userDO.getWithdrawAccout(), orderDO.getUserId());
				accountDO.setType("4");
				String a = UUIDHexGenerator.getAccountNo();
				accountDO.setOrderNo(a);
				accountDAO.insert(accountDO);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
