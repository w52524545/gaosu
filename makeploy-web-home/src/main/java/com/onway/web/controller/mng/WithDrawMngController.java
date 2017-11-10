package com.onway.web.controller.mng;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.security.KeyStore;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import javax.net.ssl.SSLContext;
import javax.servlet.http.HttpServletRequest;

import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLContexts;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.onway.cif.common.util.ParamsUtil;
import com.onway.common.lang.Money;
import com.onway.common.lang.StringUtils;
import com.onway.makeploy.common.dal.dataobject.AccountDO;
import com.onway.makeploy.common.dal.dataobject.ShopDO;
import com.onway.makeploy.common.dal.dataobject.UserDO;
import com.onway.makeploy.common.dal.dataobject.WithDrawDO;
import com.onway.makeploy.core.localcache.enums.SysConfigCacheKeyEnum;
import com.onway.web.controller.AbstractController;
import com.onway.web.controller.model.IpKit;
import com.onway.web.controller.model.PaymentKit;
import com.onway.web.controller.result.PurchaseResult;
import com.onway.web.controller.sendMessage.SendCustomMessage;

/**
 * @author jianyong.jiang 提现处理
 *
 */
@Controller
public class WithDrawMngController extends AbstractController {

	@RequestMapping("/withdrawMng.html")
	public String withdrawMng(HttpServletRequest request, ModelMap modelMap) {
		
		String userIds = (String)request.getSession().getAttribute("userId");
    	boolean checkRoleBoss = checkRoleBoss(userIds);
    	if(checkRoleBoss == false){
    		return "mng/loginMng";
    	}
    	//判断是否为运营者
        int checkOperative = checkOperative(userIds);
        modelMap.put("checkOperative", checkOperative);
		String userId = request.getParameter("userId");
		String orderNo = request.getParameter("orderNo");//accountNo
		List<WithDrawDO> withDrawList = new ArrayList<WithDrawDO>();
		/* page */
		int totalItems = (int) accountDao.countAccountNo(userId, orderNo);
		Map<String, Integer> pageDate = getPageData(request);
		List<AccountDO> accountList = accountDao
				.selectAccountInfoByUserIdAndAccountNo(userId, orderNo,
						pageDate.get(OFFSET), pageDate.get(PAGE_SIZE_STR));
		modelMap.put("totalPages",
				calculatePage(totalItems, pageDate.get(PAGE_SIZE_STR)));
		modelMap.put(CURRENTPAGE, pageDate.get(CURRENTPAGE));
		modelMap.put("totalItems", totalItems);
		request.setAttribute("page", modelMap);
		for (AccountDO accountDo : accountList) {
			String userName = userDao.selectUserInfoByUserId(
					accountDo.getUserId()).getUserName();
			if (null == userName || "".equals(userName)) {
				continue;
			}

			WithDrawDO withDrawDo = new WithDrawDO();
			withDrawDo.setId(accountDo.getId());
			withDrawDo.setAccountNo(accountDo.getAccountNo());
			withDrawDo.setUserId(accountDo.getUserId());
			Double A = 1.00;
			Money oldAmount = accountDo.getAmount().divide(
					(A - Double.valueOf((sysConfigDAO
							.selectByKey("WITHDRAW_RATE").getSysValue()))));
			if (accountDo.getSubType().equals("2")) {
				if (accountDo.getType().equals("2")) {
					// 2：个人余额提现
					withDrawDo.setBalance(userDao
							.selectUserInfoByUserId(accountDo.getUserId())
							.getBalance().add(oldAmount));
				} else if (accountDo.getType().equals("6")) {
					// 6：店铺收益提现
					withDrawDo.setBalance(shopIncomeDAO
							.selectShopIncomeInfoByUserIdAndShopId(
									accountDo.getUserId(), null)
							.getIncomeAccout().add(oldAmount));
				} else if (accountDo.getType().equals("7")) {
					// 7:区域代理提现
					withDrawDo.setBalance(agentDao
							.selectAll(accountDo.getUserId(), null, null, null,
									null).getAreaIncome().add(oldAmount));
				}
			} else {
				withDrawDo.setBalance(userDao.selectUserInfoByUserId(
						accountDo.getUserId()).getBalance());
			}
			withDrawDo.setType(accountDo.getType());
			withDrawDo.setAmount(accountDo.getAmount());
			withDrawDo.setUserName(userName);
			withDrawDo.setOrderNo(accountDo.getAccountNo());
			withDrawDo.setStatus(accountDo.getSubType());
			withDrawDo.setGmtCreate(accountDo.getGmtCreate());
			
			ShopDO shopDO = shopDAO.selectShopInfoByUserId(accountDo.getUserId());
			if(null != shopDO){
				withDrawDo.setShopName(shopDO.getShopName());
			}
			
			withDrawList.add(withDrawDo);
		}
		modelMap.addAttribute("withDrawList", withDrawList);
		modelMap.addAttribute("userId", userId);
		modelMap.addAttribute("orderNo", orderNo);
		return "mng/withdrawMng";
	}

	@RequestMapping("account.do")
	public String account(HttpServletRequest request, ModelMap modelMap) {
		String userId = request.getParameter("userId");
		String orderNo = request.getParameter("orderNo");//accountNo
		List<WithDrawDO> withDrawList = new ArrayList<WithDrawDO>();
		/* page */
		int totalItems = (int) accountDao.countAccountNo(userId, orderNo);
		Map<String, Integer> pageDate = getPageData(request);
		List<AccountDO> accountList = accountDao
				.selectAccountInfoByUserIdAndAccountNo(userId, orderNo,
						pageDate.get(OFFSET), pageDate.get(PAGE_SIZE_STR));
		modelMap.put("totalPages",
				calculatePage(totalItems, pageDate.get(PAGE_SIZE_STR)));
		modelMap.put(CURRENTPAGE, pageDate.get(CURRENTPAGE));
		modelMap.put("totalItems", totalItems);
		request.setAttribute("page", modelMap);
		for (AccountDO accountDo : accountList) {
			String userName = userDao.selectUserInfoByUserId(
					accountDo.getUserId()).getUserName();
			WithDrawDO withDrawDo = new WithDrawDO();
			withDrawDo.setId(accountDo.getId());
			withDrawDo.setUserId(accountDo.getUserId());
			
			Double A = 1.00;
			Money oldAmount = accountDo.getAmount().divide(
					(A - Double.valueOf((sysConfigDAO
							.selectByKey("WITHDRAW_RATE").getSysValue()))));
			if (accountDo.getSubType().equals("2")) {
				if (accountDo.getType().equals("2")) {
					// 2：个人余额提现
					withDrawDo.setBalance(userDao
							.selectUserInfoByUserId(accountDo.getUserId())
							.getBalance().add(oldAmount));
				} else if (accountDo.getType().equals("6")) {
					// 6：店铺收益提现
					withDrawDo.setBalance(shopIncomeDAO
							.selectShopIncomeInfoByUserIdAndShopId(
									accountDo.getUserId(), null)
							.getIncomeAccout().add(oldAmount));
				} else if (accountDo.getType().equals("7")) {
					// 7:区域代理提现
					withDrawDo.setBalance(agentDao
							.selectAll(accountDo.getUserId(), null, null, null,
									null).getAreaIncome().add(oldAmount));
				}
			} else {
				withDrawDo.setBalance(userDao.selectUserInfoByUserId(
						accountDo.getUserId()).getBalance());
			}
			withDrawDo.setType(accountDo.getType());
			withDrawDo.setAmount(accountDo.getAmount());
			withDrawDo.setUserName(userName);
			withDrawDo.setOrderNo(accountDo.getAccountNo());
			withDrawDo.setStatus(accountDo.getSubType());
			withDrawList.add(withDrawDo);
		}
		modelMap.addAttribute("withDrawList", withDrawList);
		modelMap.addAttribute("userId", userId);
		modelMap.addAttribute("orderNo", orderNo);
		return "mng/withdrawMng";
	}

	@RequestMapping("/withDraw.do")
	@ResponseBody
	public Object withDraw(final HttpServletRequest request,
			final ModelMap modelMap) {

		final PurchaseResult result = new PurchaseResult(false, "", "");
		// 微信提现证书校验
		try {
			KeyStore keyStore = KeyStore.getInstance("PKCS12");
			// 111
			String certPaht = System.getProperty("makeploymng.root")
					+ "WEB-INF/classes/cert/" + "apiclient_cert.p12";
			// String certPaht =
			// "makeploy/src/main/resources/cert/apiclient_cert.p12";
			// String mch_id="1338487101";
			String mch_id = sysConfigDAO.selectByKey("WE_PAY_MCH_ID")
					.getSysValue();
			FileInputStream instream = new FileInputStream(new File(certPaht));
			keyStore.load(instream, mch_id.toCharArray());

			// Trust own CA and all self-signed certs
			SSLContext sslcontext = SSLContexts.custom()
					.loadKeyMaterial(keyStore, mch_id.toCharArray()).build();
			// Allow TLSv1 protocol only
			SSLConnectionSocketFactory sslsf = new SSLConnectionSocketFactory(
					sslcontext,
					new String[] { "TLSv1" },
					null,
					SSLConnectionSocketFactory.BROWSER_COMPATIBLE_HOSTNAME_VERIFIER);
			CloseableHttpClient httpclient = HttpClients.custom()
					.setSSLSocketFactory(sslsf).build();

			// 微信提现所需一些参数
			String appid = sysConfigDAO.selectByKey("WE_PAY_APP_ID")
					.getSysValue();
			String appKey = sysConfigDAO.selectByKey("WE_PAY_APP_SECRET_ID")
					.getSysValue();
			String accessToken = sysConfigCacheManager
					.getConfigValue(SysConfigCacheKeyEnum.ACCESS_TOKEN);
			String userId = request.getParameter("userId");
			String accountNo = request.getParameter("accountNo");
			String orderNo = System.currentTimeMillis() + "";
			// WithDrawDO withdrawDO = new WithDrawDO();
			UserDO userdo = userDao.selectUserInfoByUserId(userId);
			String openId = "";
			if (null != userdo) {
				openId = userdo.getWechatId();
			}
			if (StringUtils.isBlank(openId)) {
				result.setBizSucc(false);
			}
			String nonce_str = getRandomString(12);
			String amount = request.getParameter("amount");
			// String amount = "199";
			// Money money = new Money(new BigDecimal(amount));
			// money.add(new Money(Integer.parseInt(amount) * 100));
			// withdrawDO.setUserId(userId);
			// withdrawDO.setAmount(money);
			// withdrawDO.setUserName(userdo.getUserName());

			Map<String, String> params = new HashMap<String, String>();

			params.put("mch_appid", appid);
			params.put("mchid", mch_id);
			// params.put("device_info", "WEB");
			params.put("nonce_str", nonce_str);
			params.put("partner_trade_no", orderNo);
			params.put("openid", openId);
			params.put("check_name", "NO_CHECK");
			// params.put("re_user_name", "张三");
			// params.put("amount", String.valueOf((1)));
			params.put("amount", "" + amount);
			String ip = IpKit.getRealIp(request);
			if (StringUtils.isBlank(ip)) {
				ip = "127.0.0.1";
			}
			params.put("desc", "withdraw");
			params.put("spbill_create_ip", ip);

			CloseableHttpResponse response = null;
			try {
				String sign = PaymentKit.createSign(params, appKey);
				params.put("sign", sign);
				String xml = PaymentKit.toXml(params);
				try {
					HttpPost post = new HttpPost(
							"https://api.mch.weixin.qq.com/mmpaymkttransfers/promotion/transfers");
					StringEntity stEntity = new StringEntity(xml);
					post.setHeader("Content-Type", "application/xml; charset="
							+ "UTF-8");
					post.setEntity(stEntity);
					response = httpclient.execute(post);
					HttpEntity entity = response.getEntity();
					if (entity != null) {
						logger.info("Response content length: "
								+ entity.getContentLength());
						BufferedReader bufferedReader = new BufferedReader(
								new InputStreamReader(entity.getContent()));
						String text = bufferedReader.readLine();
						StringBuilder aaa = new StringBuilder();
						while ((text = bufferedReader.readLine()) != null) {
							aaa.append(text);
							logger.info("返回结果:" + text);
						}

						ParamsUtil paramsUtil = new ParamsUtil();
						String resultStr = paramsUtil.getValue("result_code",
								aaa.toString());
						if (StringUtils.contains(resultStr, "SUCCESS")) {
							// withdrawDO.setStatus("1");
							int updateWithdraw = accountDao.updateStatusByAccountNo("1", accountNo);
							
							String jsonTextMsg = "";
							String notice = "亲爱的用户，您的提现申请成功 ";
							
							jsonTextMsg = SendCustomMessage
									.makeTextCustomMessage(
											userdo.getWechatId(), notice);
							SendCustomMessage.sendCustomMessage(accessToken,
									jsonTextMsg);

							if (updateWithdraw > 0) {
								result.setBizSucc(true);
							}
						} else {
							Money money = new Money(amount);
							Double A = 1.00;
							Money oldAmount = money.divide((A - Double
									.valueOf((sysConfigDAO
											.selectByKey("WITHDRAW_RATE")
											.getSysValue()))));
							//判断是哪种提现
							AccountDO accountDO = accountDao.selectAccountInfoByAccountNo(accountNo);
							
							String type = null ;
							Money account = new Money();
							if(null != accountDO){
								type = accountDO.getType();
								account = accountDO.getAmount();
							}
                            
							//余额
							if(StringUtils.equals(type, "2")){
								// 获取当前表里提现总额以及余额
								Money withdrawAccout = userDao
										.selectUserInfoByUserId(userId)
										.getWithdrawAccout();
								Money balance = userDao.selectUserInfoByUserId(
										userId).getBalance();
								// 回滚user表里的数据
								int i = userDao.updateWithDraw(
										withdrawAccout.subtract(oldAmount),
										balance.add(oldAmount), userId);
							}
							//店铺收益
							if(StringUtils.equals(type, "6")){
								// 获取当前表里的收益额
								Money withdrawAccout = shopIncomeDAO.selectShopIncomeInfoByUserIdAndShopId(userId, null).getIncomeAccout();
								shopIncomeDAO.changeShopIncome(withdrawAccout.add(oldAmount), userId, userdo.getShopId());
							}
							//区域代理
							if(StringUtils.equals(type, "7")){
								// 获取当前表里的收益额
								Money withdrawAccout = agentDao.selectAll(userId, null, null, null, null).getAreaIncome();
								agentDao.updateOutMoney(withdrawAccout.add(oldAmount), userId);
							}
							
							int updateWithdraw = accountDao.updateStatusByAccountNo("0", accountNo);
							
							try {
								String jsonTextMsg = "";
								String notice = "亲爱的用户，您的提现申请失败 ";
								// notice= new
								// String(notice.getBytes("UTF-8"),"GB18030");
								jsonTextMsg = SendCustomMessage
										.makeTextCustomMessage(
												userdo.getWechatId(), notice);
								SendCustomMessage.sendCustomMessage(
										accessToken, jsonTextMsg);
							} catch (Exception e) {
								// TODO: handle exception
							}
							// withdrawDO.setStatus("FAILED");
							result.setBizSucc(false);
						}
					}
					EntityUtils.consume(entity);

				} catch (Exception e) {
					logger.error("", e);
				} finally {
					response.close();
					httpclient.close();
				}

			} catch (Exception e) {
				logger.error("", e);
			}
		} catch (Exception e) {
			logger.error("微信证书加载异常", e);
		}

		return result;
	}

	@RequestMapping("/rejectWithDraw.do")
	@ResponseBody
	public boolean RejectWithDraw(final HttpServletRequest request,
			final ModelMap modelMap) {
		boolean withDraw = false;
		String accountNo = request.getParameter("accountNo");
		String userId = request.getParameter("userId");
		UserDO userdo = userDao.selectUserInfoByUserId(userId);
		if(null == userdo){
			return withDraw;
		}
		//判断是哪种提现
		AccountDO accountDO = accountDao.selectAccountInfoByAccountNo(accountNo);
		
		String type = null ;
		Money money = new Money();
		if(null != accountDO){
			type = accountDO.getType();
			money = accountDO.getAmount();
		}
		
		Double A = 1.00;
		Money oldAmount = money.divide((A - Double.valueOf((sysConfigDAO.selectByKey("WITHDRAW_RATE").getSysValue()))));
		
		int i = 0;
		int j = 0;
		int k = 0;
		//余额
		if(StringUtils.equals(type, "2")){
			// 获取当前表里提现总额以及余额
			Money withdrawAccout = userDao
					.selectUserInfoByUserId(userId)
					.getWithdrawAccout();
			Money balance = userDao.selectUserInfoByUserId(
					userId).getBalance();
			// 回滚user表里的数据
		   i = userDao.updateWithDraw(
					withdrawAccout.subtract(oldAmount),
					balance.add(oldAmount), userId);
		}
		//店铺收益
		if(StringUtils.equals(type, "6")){
			// 获取当前表里的收益额
			Money withdrawAccout = shopIncomeDAO.selectShopIncomeInfoByUserIdAndShopId(userId, null).getIncomeAccout();
			j = shopIncomeDAO.changeShopIncome(withdrawAccout.add(oldAmount), userId, userdo.getShopId());
		}
		//区域代理
		if(StringUtils.equals(type, "7")){
			// 获取当前表里的收益额
			Money withdrawAccout = agentDao.selectAll(userId, null, null, null, null).getAreaIncome();
			k = agentDao.updateOutMoney(withdrawAccout.add(oldAmount), userId);
		}
		
		if (i > 0 || j > 0 || k > 0) {
			withDraw = true;
			int updateWithdraw = accountDao.updateStatusByAccountNo("0",accountNo);
		}
		
		try {
			String accessToken = sysConfigCacheManager
					.getConfigValue(SysConfigCacheKeyEnum.ACCESS_TOKEN);
			String jsonTextMsg = "";
			String notice = "亲爱的用户，您的提现申请被拒绝 ";
			// notice= new String(notice.getBytes("UTF-8"),"GB18030");
			jsonTextMsg = SendCustomMessage.makeTextCustomMessage(
					userdo.getWechatId(), notice);
			SendCustomMessage.sendCustomMessage(accessToken, jsonTextMsg);
		} catch (Exception e) {
			// TODO: handle exception
		}
		return withDraw;
	}
	
	@RequestMapping("/withDrawOther.do")
	@ResponseBody
	public Object withDrawOther(final HttpServletRequest request,
			final ModelMap modelMap) {

		final PurchaseResult result = new PurchaseResult(false, "", "");
		String accessToken = sysConfigCacheManager.getConfigValue(SysConfigCacheKeyEnum.ACCESS_TOKEN);
		
		String userId = request.getParameter("userId");
		String accountNo = request.getParameter("accountNo");

		UserDO userdo = userDao.selectUserInfoByUserId(userId);
		String openId = "";
		if (null != userdo) {
			openId = userdo.getWechatId();
		}
		if (StringUtils.isBlank(openId)) {
			result.setBizSucc(false);
		}
		try {
			
			AccountDO accountDO = accountDao.selectAccountInfoByAccountNo(accountNo);
			int updateWithdraw = accountDao.updateStatusByAccountNo("1",accountNo);
			String notice = "";
			if(StringUtils.equals(accountDO.getType(), "6")){
				notice = "亲爱的用户，您的商铺收益提现申请成功 ";
			}else if(StringUtils.equals(accountDO.getType(), "7")){
				notice = "亲爱的用户，您的区域代理收益提现申请成功 ";
			}
			
			if (updateWithdraw > 0) {
				result.setBizSucc(true);
				String jsonTextMsg = "";

				jsonTextMsg = SendCustomMessage.makeTextCustomMessage(
						openId , notice);
				SendCustomMessage.sendCustomMessage(accessToken, jsonTextMsg);
			}

		} catch (Exception e) {
			logger.error("微信证书加载异常", e);
		}

		return result;
	}
	
	/**
	 * 充值记录
	 * @param request
	 * @param modelMap
	 * @return
	 */
	@RequestMapping("/accountMng.html")
	public String accountMng(HttpServletRequest request, ModelMap modelMap) {
		
		String userIds = (String)request.getSession().getAttribute("userId");
    	boolean checkRoleBoss = checkRoleBoss(userIds);
    	if(checkRoleBoss == false){
    		return "mng/loginMng";
    	}
    	//判断是否为运营者
        int checkOperative = checkOperative(userIds);
        modelMap.put("checkOperative", checkOperative);
    	List<AccountDO> accountList = accountDao.selectAccountInfoByType("1");
    	List<MoneyInResult> moneyIn = new ArrayList<MoneyInResult>();
    	for (AccountDO accountDO : accountList) {
    		MoneyInResult result = new MoneyInResult();
    		String userId = accountDO.getUserId();
    		UserDO userDO = userDao.selectUserInfoByUserId(userId);
    		if(null != userDO){
    			result.setUserName(userDO.getUserName());
    			result.setCell(userDO.getCell());
    			result.setAmount(accountDO.getAmount());
    			result.setPoint(accountDO.getPoint()/100);
    			result.setGmtCreate(accountDO.getGmtCreate());
    			moneyIn.add(result);
    		}
		}
		modelMap.put("moneyIn", moneyIn);
		return "mng/accountMng";
	}
	
	
	@RequestMapping("/accountShopMng.html")
	public String accountShopMng(HttpServletRequest request, ModelMap modelMap) {
		
		String userId = request.getParameter("userId");
		
		List<AccountDO> outMoneyInfo = accountDao.selectShopOutMoneyInfo(userId);
		modelMap.put("outMoneyInfo", outMoneyInfo);
		
		return "mng/accountShopMng";
	}

	/**
	 * 微信提现获取随机串
	 * 
	 * @param length
	 * @return
	 */
	private static String getRandomString(int length) {
		String str = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";// 含有字符和数字的字符串
		Random random = new Random();// 随机类初始化
		StringBuffer sb = new StringBuffer();// StringBuffer类生成，为了拼接字符串

		for (int i = 0; i < length; ++i) {
			int number = random.nextInt(62);// [0,62)
			sb.append(str.charAt(number));
		}
		return sb.toString();
	}
}
