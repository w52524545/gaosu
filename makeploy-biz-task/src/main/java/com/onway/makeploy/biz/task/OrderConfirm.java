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

	/* 自动确认收货*/
	@Override
	protected void process() {
		try {
			//获得所有当前时间一周以外所有待确认收货订单
			Calendar calendar = Calendar.getInstance();//此时打印它获取的是系统当前时间
	        calendar.add(Calendar.DATE, -7);    //得到一周前
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
					// 判断用户是否有上线
					String highUserId = user.getHighUserId();
					String rate = null;
					// 如果有上级
					if (!StringUtils.isEmpty(highUserId)) {
						// 返现利率一级
						if (StringUtils.equals(orderDO.getOrderType(), "4")) {
							rate = sysConfigDAO.selectByKey("PARTNER_RETURN_RATE").getSysValue();
						} else {
							rate = sysConfigDAO.selectByKey("BUY_RETURN_FIRSTLEVEKL_RATE").getSysValue();
						}
						double realRate = Double.parseDouble(rate);

						// 判断上级是否为农村经纪人
						PartnerDO checkPartnerByPUserId = partnerDAO
								.checkPartnerByPUserId(highUserId);
						// 如果是农村经纪人
						if (null != checkPartnerByPUserId) {
							// 更新上级余额和累计收入
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

								// 加流水
								String accountNo = UUIDHexGenerator
										.getNum();
								AccountDO accountDO = new AccountDO();
								accountDO.setOrderNo(accountNo);
								accountDO.setUserId(high.getUserId());
								accountDO.setType("5");
								accountDO.setAmount(allMoney.multiply(realRate));
								accountDAO.outMoney(accountDO);
								
								try {
									// 公众号发送提示消息
									String accessToken = sysConfigDAO.selectByKey("ACCESS_TOKEN").getSysValue();
										String jsonTextMsg = "";
										jsonTextMsg = SendCustomMessage
												.makeTextCustomMessage(
														high.getWechatId(), "您的好友"+user.getNickName()+"刚刚完成了一笔订单，你获得"+allMoney.multiply(realRate)+"个分享币");
										SendCustomMessage.sendCustomMessage(accessToken,
												jsonTextMsg);
								} catch (Exception e) {
									e.printStackTrace();
								}
							}

							// 判断highUserId是否有上级 上上级
							UserDO highUser = userDAO
									.selectUserInfoByUserId(highUserId);
							if (null != highUser) {
								String moreHighUserId = highUser
										.getHighUserId();
								String moreRate = null;
								// 有上上级
								if (!StringUtils.isEmpty(moreHighUserId)) {
									// 返现利率二级
									if (StringUtils.equals(
											orderDO.getOrderType(), "4")) {
										moreRate = sysConfigDAO.selectByKey("PARTNER_RETURN_RATE").getSysValue();
									} else {
										moreRate = sysConfigDAO.selectByKey("BUY_RETURN_SECONDLEVEKL_RATE").getSysValue();
									}
									double moreRealRate = Double
											.parseDouble(moreRate);

									// 判断上级的上级是否为农村经纪人
									PartnerDO checkMorePartnerByPUserId = partnerDAO
											.checkPartnerByPUserId(moreHighUserId);
									if (null != checkMorePartnerByPUserId) {
										// 更新上级余额和累计收入
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

											// 加流水
											String accountNo = UUIDHexGenerator
													.getNum();
											AccountDO accountDO = new AccountDO();
											accountDO.setOrderNo(accountNo);
											accountDO.setUserId(moreHigh.getUserId());
											accountDO.setType("5");
											accountDO.setAmount(allMoney.multiply(moreRealRate));
											accountDAO.outMoney(accountDO);
											
											try {
												// 公众号发送提示消息
												String accessToken = sysConfigDAO.selectByKey("ACCESS_TOKEN").getSysValue();
													String jsonTextMsg = "";
													jsonTextMsg = SendCustomMessage
															.makeTextCustomMessage(
																	moreHigh.getWechatId(), "您好友的好友"+user.getNickName()+"刚刚完成了一笔订单，你获得"+allMoney.multiply(moreRealRate)+"个分享币");
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
	            
				//农村经纪人开发的联盟商家有0.2%的分红
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
						//判断店主是否有上级  上级是否为农村经纪人
						if(!StringUtils.isEmpty(shopDO.getHighUserId())){
							//店铺上级用户Id
							String highUserId = shopDO.getHighUserId();
							UserDO userDO = userDAO.selectUserInfoByUserId(highUserId);
							PartnerDO checkPartner = partnerDAO.checkPartnerByPUserId(highUserId);
							if(null != checkPartner && null != userDO){
								Money addMoney = allMoney.multiply(realUnionRate);
								//更新上级农村经纪人余额
								UserDO userDO2 = new UserDO();
								userDO2.setUserId(highUserId);
								userDO2.setBalance(userDO.getBalance().add(addMoney));
								userDO2.setTotalBalance(userDO.getTotalBalance().add(addMoney));
								userDAO.updateBalanceMoneyByUserId(userDO2);
								
								//加流水
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
					// 公众号发送提示消息
					String accessToken = sysConfigDAO.selectByKey("ACCESS_TOKEN").getSysValue();

					UserDO userdo = userDAO.selectUserInfoByUserId(orderDO.getUserId());
					ProductDO productDO = productDAO.selectProductByProductNo(productNo, shopId);
					if (null != userdo && null != productDO) {
						String jsonTextMsg = "";
						jsonTextMsg = SendCustomMessage
								.makeTextCustomMessage(
										userdo.getWechatId(),
										"亲，您的宝贝"
												+ productDO
														.getProductName()
												+ "产品已自动确认收货，祝您购物愉快，再次光临");
						SendCustomMessage.sendCustomMessage(accessToken,
								jsonTextMsg);
					}
				} catch (Exception e) {
					e.printStackTrace();
				}

				try {
					// 公众号发送提示消息
					String accessToken = sysConfigDAO.selectByKey("ACCESS_TOKEN").getSysValue();

					UserDO shop = userDAO.selectUserInfoByShopId(shopId);
					if (null != shop) {
						String jsonTextMsg = "";
						jsonTextMsg = SendCustomMessage
								.makeTextCustomMessage(shop.getWechatId(),
										"您的店铺订单" + orderDO.getOrderId()
												+ "，用户已成功自动确认收货");
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
