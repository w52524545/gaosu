package com.onway.makeploy.biz.task;


import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

import com.onway.makeploy.common.dal.daointerface.AccountDAO;
import com.onway.makeploy.common.dal.daointerface.OrderDAO;
import com.onway.makeploy.common.dal.daointerface.PartnerDAO;
import com.onway.makeploy.common.dal.daointerface.ProductDAO;
import com.onway.makeploy.common.dal.daointerface.ShopDAO;
import com.onway.makeploy.common.dal.daointerface.SysConfigDAO;
import com.onway.makeploy.common.dal.daointerface.UserDAO;
import com.onway.makeploy.common.dal.dataobject.OrderDO;
import com.onway.makeploy.core.service.code.CodeGenerateComponent;

public class OrderCancel extends AbstractTask {
	private OrderDAO orderDAO;
	private SysConfigDAO sysConfigDAO;
	private UserDAO userDAO;
	private AccountDAO accountDAO;
	private PartnerDAO partnerDAO;
	private ShopDAO shopDAO;
	private ProductDAO productDAO;

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
	
	public PartnerDAO getPartnerDAO() {
		return partnerDAO;
	}

	public void setPartnerDAO(PartnerDAO partnerDAO) {
		this.partnerDAO = partnerDAO;
	}

	public ShopDAO getShopDAO() {
		return shopDAO;
	}

	public void setShopDAO(ShopDAO shopDAO) {
		this.shopDAO = shopDAO;
	}

	public ProductDAO getProductDAO() {
		return productDAO;
	}

	public void setProductDAO(ProductDAO productDAO) {
		this.productDAO = productDAO;
	}

	@Override
	protected boolean canProcess() {
		// TODO Auto-generated method stub
		return true;
	}

	/* �Զ�ȡ������*/
	@Override
	protected void process() {
		
		//��ȡ30����ǰ��ʱ��
		int minute = -30;
		String minuteAgo = getTimeByMinute(minute);
		
		//��ѯ30����ǰδ֧������
 		String orderStatus = "2";
		
		List<OrderDO> orderList = orderDAO.selectCancle(minuteAgo);
		for (OrderDO orderDO : orderList) {
			String orderId = orderDO.getOrderId();
			int i = orderDAO.cancleOrder(orderStatus, orderId);
			if(i<=0){
        		return;
        	}
//			String userId = orderDO.getUserId();
//			UserDO userDO = userDAO.selectUserInfoByUserId(userId);
//			try {
//				// ���ںŷ�����ʾ��Ϣ
//				String accessToken = sysConfigDAO.selectByKey("ACCESS_TOKEN").getSysValue();
//					String jsonTextMsg = "";
//					jsonTextMsg = SendCustomMessage
//							.makeTextCustomMessage(
//									userDO.getWechatId(), "���Ķ���"+orderId+"��30������δ֧�������Զ�ȡ��");
//					SendCustomMessage.sendCustomMessage(accessToken,
//							jsonTextMsg);
//			} catch (Exception e) {
//				e.printStackTrace();
//			}
		}
	}
	
	public static String getTimeByMinute(int minute) {

        Calendar calendar = Calendar.getInstance();

        calendar.add(Calendar.MINUTE, minute);

        return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(calendar.getTime());

    }
}
