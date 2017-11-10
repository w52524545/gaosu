package com.onway.makeploy.biz.task;


import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import com.onway.common.lang.Money;
import com.onway.common.lang.StringUtils;
import com.onway.makeploy.common.dal.daointerface.AccountDAO;
import com.onway.makeploy.common.dal.daointerface.OrderDAO;
import com.onway.makeploy.common.dal.daointerface.PartnerDAO;
import com.onway.makeploy.common.dal.daointerface.ProductDAO;
import com.onway.makeploy.common.dal.daointerface.ShopDAO;
import com.onway.makeploy.common.dal.daointerface.SysConfigDAO;
import com.onway.makeploy.common.dal.daointerface.UserDAO;
import com.onway.makeploy.common.dal.dataobject.AccountDO;
import com.onway.makeploy.common.dal.dataobject.OrderDO;
import com.onway.makeploy.common.dal.dataobject.PartnerDO;
import com.onway.makeploy.common.dal.dataobject.ProductDO;
import com.onway.makeploy.common.dal.dataobject.ShopDO;
import com.onway.makeploy.common.dal.dataobject.UserDO;
import com.onway.makeploy.core.service.code.CodeGenerateComponent;

public class OrderConfirm extends AbstractTask {
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

	/* �Զ�ȷ���ջ�*/
	@Override
	protected void process() {
		try {
			//������е�ǰʱ��һ���������д�ȷ���ջ�����
			Calendar calendar = Calendar.getInstance();//��ʱ��ӡ����ȡ����ϵͳ��ǰʱ��
	        calendar.add(Calendar.DATE, -7);    //�õ�һ��ǰ
	        String  weekAgo= new SimpleDateFormat("yyyy-MM-dd").format(calendar.getTime());
	        System.out.println(weekAgo);
	        String orderStatus = "0";
			String sendGoods = "4";
	        List<OrderDO> confirmList = orderDAO.selectConfirm(weekAgo);
	        for (OrderDO orderDO : confirmList) {
	        	if(!StringUtils.equals(orderDO.getSendGoods(), "3")){
	        	String orderId = orderDO.getOrderId();
	        	int i = orderDAO.updateOrderStatusAndOrderSendGoodsByOrderId(orderStatus, sendGoods,orderId);
	        	if(i<=0){
	        		return;
	        	}
	        	
	        	String shopId = orderDO.getShopId();
				String productNo = orderDO.getProductNo();
				Money money = orderDO.getOrderPrice();
				Money luggage = orderDO.getLuggage();
				Money allMoney = money.add(luggage);

				UserDO user = userDAO.selectUserInfoByUserId(orderDO
						.getUserId());
				if (null != user) {
					// �ж��û��Ƿ�������
					String highUserId = user.getHighUserId();
					String rate = null;
					// ������ϼ�
					if (!StringUtils.isEmpty(highUserId)) {
						// ��������һ��
						if (StringUtils.equals(orderDO.getOrderType(), "4")) {
							rate = sysConfigDAO.selectByKey("PARTNER_RETURN_RATE").getSysValue();
						} else {
							rate = sysConfigDAO.selectByKey("BUY_RETURN_FIRSTLEVEKL_RATE").getSysValue();
						}
						double realRate = Double.parseDouble(rate);

						// �ж��ϼ��Ƿ�Ϊũ�徭����
						PartnerDO checkPartnerByPUserId = partnerDAO
								.checkPartnerByPUserId(highUserId);
						// �����ũ�徭����
						if (null != checkPartnerByPUserId) {
							// �����ϼ������ۼ�����
							UserDO us3 = new UserDO();
							UserDO high = userDAO
									.selectUserInfoByUserId(highUserId);
							if (null != high) {
								Money highbalance = high.getBalance().add(
										allMoney.multiply(realRate));
								us3.setUserId(high.getUserId());
								us3.setBalance(highbalance);
								us3.setTotalBalance(high.getTotalBalance()
										.add(allMoney.multiply(realRate)));
								userDAO.updateBalanceMoneyByUserId(us3);

								// ����ˮ
								String accountNo = UUIDHexGenerator
										.getNum();
								AccountDO accountDO = new AccountDO();
								accountDO.setOrderNo(accountNo);
								accountDO.setUserId(high.getUserId());
								accountDO.setType("5");
								accountDO.setAmount(allMoney.multiply(realRate));
								accountDAO.outMoney(accountDO);
								
								try {
									// ���ںŷ�����ʾ��Ϣ
									String accessToken = sysConfigDAO.selectByKey("ACCESS_TOKEN").getSysValue();
										String jsonTextMsg = "";
										jsonTextMsg = SendCustomMessage
												.makeTextCustomMessage(
														high.getWechatId(), "���ĺ���"+user.getNickName()+"�ո������һ�ʶ���������"+allMoney.multiply(realRate)+"�������");
										SendCustomMessage.sendCustomMessage(accessToken,
												jsonTextMsg);
								} catch (Exception e) {
									e.printStackTrace();
								}
							}

							// �ж�highUserId�Ƿ����ϼ� ���ϼ�
							UserDO highUser = userDAO
									.selectUserInfoByUserId(highUserId);
							if (null != highUser) {
								String moreHighUserId = highUser
										.getHighUserId();
								String moreRate = null;
								// �����ϼ�
								if (!StringUtils.isEmpty(moreHighUserId)) {
									// �������ʶ���
									if (StringUtils.equals(
											orderDO.getOrderType(), "4")) {
										moreRate = sysConfigDAO.selectByKey("PARTNER_RETURN_RATE").getSysValue();
									} else {
										moreRate = sysConfigDAO.selectByKey("BUY_RETURN_SECONDLEVEKL_RATE").getSysValue();
									}
									double moreRealRate = Double
											.parseDouble(moreRate);

									// �ж��ϼ����ϼ��Ƿ�Ϊũ�徭����
									PartnerDO checkMorePartnerByPUserId = partnerDAO
											.checkPartnerByPUserId(moreHighUserId);
									if (null != checkMorePartnerByPUserId) {
										// �����ϼ������ۼ�����
										UserDO us4 = new UserDO();
										UserDO moreHigh = userDAO
												.selectUserInfoByUserId(moreHighUserId);
										if (null != moreHigh) {
											Money moreHighbalance = moreHigh
													.getBalance()
													.add(allMoney
															.multiply(moreRealRate));
											us4.setUserId(moreHigh
													.getUserId());
											us4.setBalance(moreHighbalance);
											us4.setTotalBalance(moreHigh
													.getTotalBalance()
													.add(allMoney
															.multiply(moreRealRate)));
											userDAO.updateBalanceMoneyByUserId(us4);

											// ����ˮ
											String accountNo = UUIDHexGenerator
													.getNum();
											AccountDO accountDO = new AccountDO();
											accountDO.setOrderNo(accountNo);
											accountDO.setUserId(moreHigh.getUserId());
											accountDO.setType("5");
											accountDO.setAmount(allMoney.multiply(moreRealRate));
											accountDAO.outMoney(accountDO);
											
											try {
												// ���ںŷ�����ʾ��Ϣ
												String accessToken = sysConfigDAO.selectByKey("ACCESS_TOKEN").getSysValue();
													String jsonTextMsg = "";
													jsonTextMsg = SendCustomMessage
															.makeTextCustomMessage(
																	moreHigh.getWechatId(), "�����ѵĺ���"+user.getNickName()+"�ո������һ�ʶ���������"+allMoney.multiply(moreRealRate)+"�������");
													SendCustomMessage.sendCustomMessage(accessToken,
															jsonTextMsg);
											} catch (Exception e) {
												e.printStackTrace();
											}
										}
									}

								}
							}
						}
					}
				}
	            
				//ũ�徭���˿����������̼���0.2%�ķֺ�
				ShopDO checkshopUnion = shopDAO.selectShopInfoByShopId(shopId);
				String check = null;
				if(null != checkshopUnion){
					check = checkshopUnion.getUnionFlg();
				}
				UserDO shopDO = userDAO.selectUserInfoByShopId(shopId);
				String unionRate = sysConfigDAO.selectByKey("UNION_RETURN_PARTNER").getSysValue();
				double realUnionRate = Double.parseDouble(unionRate);
				if(StringUtils.equals(check, "1")){
					if(null != shopDO){
						//�жϵ����Ƿ����ϼ�  �ϼ��Ƿ�Ϊũ�徭����
						if(!StringUtils.isEmpty(shopDO.getHighUserId())){
							//�����ϼ��û�Id
							String highUserId = shopDO.getHighUserId();
							UserDO userDO = userDAO.selectUserInfoByUserId(highUserId);
							PartnerDO checkPartner = partnerDAO.checkPartnerByPUserId(highUserId);
							if(null != checkPartner && null != userDO){
								Money addMoney = allMoney.multiply(realUnionRate);
								//�����ϼ�ũ�徭�������
								UserDO userDO2 = new UserDO();
								userDO2.setUserId(highUserId);
								userDO2.setBalance(userDO.getBalance().add(addMoney));
								userDO2.setTotalBalance(userDO.getTotalBalance().add(addMoney));
								userDAO.updateBalanceMoneyByUserId(userDO2);
								
								//����ˮ
								String accountNo = UUIDHexGenerator
										.getNum();
								AccountDO accountDO = new AccountDO();
								accountDO.setOrderNo(accountNo);
								accountDO.setUserId(highUserId);
								accountDO.setType("8");
								accountDO.setAmount(addMoney);
								accountDAO.outMoney(accountDO);
								
							}
						}
					}
				}
				
				
				try {
					// ���ںŷ�����ʾ��Ϣ
					String accessToken = sysConfigDAO.selectByKey("ACCESS_TOKEN").getSysValue();

					UserDO userdo = userDAO.selectUserInfoByUserId(orderDO.getUserId());
					ProductDO productDO = productDAO.selectProductByProductNo(productNo, shopId);
					if (null != userdo && null != productDO) {
						String jsonTextMsg = "";
						jsonTextMsg = SendCustomMessage
								.makeTextCustomMessage(
										userdo.getWechatId(),
										"�ף����ı���"
												+ productDO
														.getProductName()
												+ "��Ʒ���Զ�ȷ���ջ���ף��������죬�ٴι���");
						SendCustomMessage.sendCustomMessage(accessToken,
								jsonTextMsg);
					}
				} catch (Exception e) {
					e.printStackTrace();
				}

				try {
					// ���ںŷ�����ʾ��Ϣ
					String accessToken = sysConfigDAO.selectByKey("ACCESS_TOKEN").getSysValue();

					UserDO shop = userDAO.selectUserInfoByShopId(shopId);
					if (null != shop) {
						String jsonTextMsg = "";
						jsonTextMsg = SendCustomMessage
								.makeTextCustomMessage(shop.getWechatId(),
										"���ĵ��̶���" + orderDO.getOrderId()
												+ "���û��ѳɹ��Զ�ȷ���ջ�");
						SendCustomMessage.sendCustomMessage(accessToken,
								jsonTextMsg);
					}
				} catch (Exception e) {
					
				}
	        	
			}
	    }
	        
		}catch(Exception e){
			e.printStackTrace();
		}
	}
}
