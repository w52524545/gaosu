package com.onway.web.controller.mng;


import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.onway.common.lang.DateUtils;
import com.onway.common.lang.Money;
import com.onway.common.lang.StringUtils;
import com.onway.makeploy.common.dal.dataobject.AccountDO;
import com.onway.makeploy.common.dal.dataobject.OrderDO;
import com.onway.makeploy.common.dal.dataobject.ShopDO;
import com.onway.makeploy.common.dal.dataobject.ShopIncomeDO;
import com.onway.makeploy.common.dal.dataobject.SysConfigDO;
import com.onway.makeploy.common.dal.dataobject.UserDO;
import com.onway.makeploy.core.localcache.enums.SysConfigCacheKeyEnum;
import com.onway.web.controller.AbstractController;
import com.onway.web.controller.UUIDHexGenerator;
import com.onway.web.controller.result.JsonResult;
import com.onway.web.controller.sendMessage.SendCustomMessage;
import com.onway.web.controller.template.ControllerCallBack;

/**
 * @author southRain
 *
 */

@Controller
public class ShopIncomeMngController extends AbstractController {

    //进入收入信息查询页面
	@RequestMapping("/shopIncomeMng.html")
	public String shopMng(HttpServletRequest request, ModelMap modelMap) {
		String userId = (String)request.getSession().getAttribute("userId");
    	boolean checkRoleBoss = checkRoleBoss(userId);
    	if(checkRoleBoss == false){
    		return "mng/loginMng";
    	}
    	//判断是否为运营者
        int checkOperative = checkOperative(userId);
        modelMap.put("checkOperative", checkOperative);
		
        String flag = request.getParameter("selectedNew");
        //flag 0所有支付  1微信支付 2分享币支付
        //check 1本月 2本周
        
		String shopId = request.getParameter("shopId");
		String check = request.getParameter("check");
		
		ShopDO shopDO = shopDAO.selectShopByShopId(shopId);
		String startTime = request.getParameter("startTime");
		String endTime = request.getParameter("endTime");
		Map<String,Integer> pageDate=getPageData(request);
		try {
			Date startDate = null;
			Date endDate = null;
			Date endDateStr = null;
			if(StringUtils.isNotBlank(startTime)){
				startDate = DateUtils.parseDate(startTime, DateUtils.webFormat);
			}
			if(StringUtils.isNotBlank(endTime)){
				endDate = DateUtils.parseDate(endTime,DateUtils.webFormat);
				endDateStr = endDate;
				/*
				 * 基于mysql数据库关于取时间区间不包含结束时间的bug,所以要在选定的结束时间后追加addDays方法进行追加一天
				 */
				endDate = DateUtils.addDays(endDate, (long)1);
			}
			List<ShopIncomeDO> list=shopIncomeDAO.selectIncomeInfo(shopId, startDate, endDate, pageDate.get(OFFSET), pageDate.get(PAGE_SIZE_STR));
			int count=(int) shopIncomeDAO.selectIncomeInfoCount(shopId, startDate, endDate);
			
			//查询时间段内的所有订单
			//flag 0所有支付  1微信支付 2分享币支付
	        //check 1本月 2本周
			List<OrderDO> allIncome = new ArrayList<OrderDO>();
			if(StringUtils.equals(flag, "1") && StringUtils.equals(check, "1")){
				allIncome = orderDao.selectMounthOrder(shopId,"1");//月订单  微信支付
			}else if(StringUtils.equals(flag, "1") && StringUtils.equals(check, "2")){
				allIncome = orderDao.selectWeekOrder(shopId,"1");//周订单  微信支付
			}else if(StringUtils.equals(flag, "0") && StringUtils.equals(check, "1")){
				allIncome = orderDao.selectMounthOrder(shopId,null);//月订单 所有支付
			}else if(StringUtils.equals(flag, "0") && StringUtils.equals(check, "2")){
				allIncome = orderDao.selectWeekOrder(shopId,null);//周订单 所有支付
			}else if(StringUtils.equals(flag, "2") && StringUtils.equals(check, "1")){
				allIncome = orderDao.selectMounthOrder(shopId,"4");//月订单 分享币
			}else if(StringUtils.equals(flag, "2") && StringUtils.equals(check, "1")){
				allIncome = orderDao.selectWeekOrder(shopId,"4");//周订单 分享币
			}else if(StringUtils.equals(flag, "0") && StringUtils.isBlank(check)){
				allIncome = orderDao.selectIncome(shopId, null,startDate, endDate);
			}else if(StringUtils.equals(flag, "1") && StringUtils.isBlank(check)){
				allIncome = orderDao.selectIncome(shopId, "1",startDate, endDate);
			}else if(StringUtils.equals(flag, "2") && StringUtils.isBlank(check)){
				allIncome = orderDao.selectIncome(shopId, "4",startDate, endDate);
			}
			String orderNo = null;
			Money orderPrice = new Money();
			for (OrderDO orderDO : allIncome) {
				String orderNo2 = orderDO.getOrderNo();
				orderPrice = orderPrice.add(orderDO.getOrderPrice());
				if(!StringUtils.equals(orderNo, orderNo2)){
					orderPrice = orderPrice.add(orderDO.getLuggage());
				}
				orderNo = orderNo2;
			}
			
		modelMap.put("totalPages",calculatePage(count, pageDate.get(PAGE_SIZE_STR))); // 传输总页数
		modelMap.put(CURRENTPAGE, pageDate.get(CURRENTPAGE));//当前页数
		modelMap.put("totalItems", count);// 传输总条数
		modelMap.put("incomeInfoList", list);
		modelMap.put("startTime", startTime);
		modelMap.put("endTime", endTime);
		modelMap.put("startDate", startDate);
		modelMap.put("endDate", endDateStr);
		modelMap.put("shopId", shopId);
		modelMap.put("shopDO", shopDO);
		modelMap.put("orderPrice", orderPrice);
		request.setAttribute("page", modelMap);
		modelMap.put("checkTime", check);
		modelMap.put("flag", flag);
		}catch (Exception e){
			e.printStackTrace();
		}
		return "mng/shopIncomeMng";
	}
	
	@RequestMapping("/mallIncomeMng.html")
	public String mallIncomeMng(HttpServletRequest request, ModelMap modelMap) {
		String userId = (String)request.getSession().getAttribute("userId");
    	boolean checkRoleBoss = checkRoleBoss(userId);
    	if(checkRoleBoss == false){
    		return "mng/loginMng";
    	}
    	//判断是否为运营者
        int checkOperative = checkOperative(userId);
        modelMap.put("checkOperative", checkOperative);
        
        String flag = request.getParameter("selectedNew");
        //flag 0所有支付  1微信支付 2分享币支付
        //check 1本月 2本周
        String check = request.getParameter("check");
		
		String startTime = request.getParameter("startTime");
		String endTime = request.getParameter("endTime");
		Map<String,Integer> pageDate=getPageData(request);
		try {
			Date startDate = null;
			Date endDate = null;
			Date endDateStr = null;
			if(StringUtils.isNotBlank(startTime)){
				startDate = DateUtils.parseDate(startTime, DateUtils.webFormat);
			}
			if(StringUtils.isNotBlank(endTime)){
				endDate = DateUtils.parseDate(endTime,DateUtils.webFormat);
				endDateStr = endDate;
				/*
				 * 基于mysql数据库关于取时间区间不包含结束时间的bug,所以要在选定的结束时间后追加addDays方法进行追加一天
				 */
				endDate = DateUtils.addDays(endDate, (long)1);
			}
//			List<ShopIncomeDO> list=shopIncomeDAO.selectIncomeInfo(null, startDate, endDate, pageDate.get(OFFSET), pageDate.get(PAGE_SIZE_STR));
			int count=(int) shopIncomeDAO.selectIncomeInfoCount(null, startDate, endDate);
			
			//查询时间段内的所有订单
			//PAY_WAY   1：微信 2：支付宝 3：银行卡 4:分享币支付
			//flag 0所有支付  1微信支付 2分享币支付
	        //check 1本月 2本周
			List<OrderDO> allIncome = new ArrayList<OrderDO>();
			if(StringUtils.equals(flag, "1") && StringUtils.equals(check, "1")){
				allIncome = orderDao.selectMounthOrder(null,"1");//月订单  微信支付
			}else if(StringUtils.equals(flag, "1") && StringUtils.equals(check, "2")){
				allIncome = orderDao.selectWeekOrder(null,"1");//周订单  微信支付
			}else if(StringUtils.equals(flag, "0") && StringUtils.equals(check, "1")){
				allIncome = orderDao.selectMounthOrder(null,null);//月订单 所有支付
			}else if(StringUtils.equals(flag, "0") && StringUtils.equals(check, "2")){
				allIncome = orderDao.selectWeekOrder(null,null);//周订单 所有支付
			}else if(StringUtils.equals(flag, "2") && StringUtils.equals(check, "1")){
				allIncome = orderDao.selectMounthOrder(null,"4");//月订单 分享币
			}else if(StringUtils.equals(flag, "2") && StringUtils.equals(check, "1")){
				allIncome = orderDao.selectWeekOrder(null,"4");//周订单 分享币
			}else if(StringUtils.equals(flag, "0") && StringUtils.isBlank(check)){
				allIncome = orderDao.selectIncome(null, null,startDate, endDate);
			}else if(StringUtils.equals(flag, "1") && StringUtils.isBlank(check)){
				allIncome = orderDao.selectIncome(null, "1",startDate, endDate);
			}else if(StringUtils.equals(flag, "2") && StringUtils.isBlank(check)){
				allIncome = orderDao.selectIncome(null, "4",startDate, endDate);
			}
//			else{
//				allIncome = orderDao.selectIncome(null, startDate, endDate);
//			}
			String orderNo = null;
			Money orderPrice = new Money();
			for (OrderDO orderDO : allIncome) {
				String orderNo2 = orderDO.getOrderNo();
				orderPrice = orderPrice.add(orderDO.getOrderPrice());
				if(!StringUtils.equals(orderNo, orderNo2)){
					orderPrice = orderPrice.add(orderDO.getLuggage());
				}
				orderNo = orderNo2;
			}
			
		modelMap.put("totalPages",calculatePage(count, pageDate.get(PAGE_SIZE_STR))); // 传输总页数
		modelMap.put(CURRENTPAGE, pageDate.get(CURRENTPAGE));//当前页数
		modelMap.put("totalItems", count);// 传输总条数
//		modelMap.put("incomeInfoList", list);
		modelMap.put("startTime", startTime);
		modelMap.put("endTime", endTime);
		modelMap.put("startDate", startDate);
		modelMap.put("endDate", endDateStr);
		modelMap.put("orderPrice", orderPrice);
		request.setAttribute("page", modelMap);
		modelMap.put("checkTime", check);
		modelMap.put("flag", flag);
		}catch (Exception e){
			e.printStackTrace();
		}
		return "mng/mallIncomeMng";
	}
	
	
	/**
	 * 店铺收入提现审核
	 * @param request
	 * @param modelMap
	 * @return
	 */
	@RequestMapping("outShopMoney.html")
	public String outShopMoney(HttpServletRequest request,ModelMap modelMap){
		String userId = request.getParameter("userId");
		String shopId = request.getParameter("shopId");
		
	    UserDO userDO = userDao.selectUserInfoByUserId(userId);
	    ShopIncomeDO shopIncomeDO = shopIncomeDAO.selectShopIncomeInfoByUserIdAndShopId(userId, shopId);
	    ShopDO shopDO = shopDAO.selectShopByShopId(shopId);
	    modelMap.put("userDO", userDO);
	    modelMap.put("shopIncomeDO", shopIncomeDO);
	    modelMap.put("shopDO", shopDO);
	    
	    // 提现费率
	 	String rate = sysConfigCacheManager
	 				.getConfigValue(SysConfigCacheKeyEnum.WITHDRAW_RATE);

	 	modelMap.put("rate", rate);
	    
		return "mng/outShopMoney";
	}
	
	/**
	 * 提交店铺收益申请
	 * @param request
	 * @return
	 */
	@RequestMapping("outShopMoneyByShopId.do")
	@ResponseBody
	public Object outShopMoneyByShopId(HttpServletRequest request){
		final String userId = (String) request.getSession().getAttribute(
				"userId");
		final String shopId = (String) request.getSession().getAttribute(
				"shopId");
		final String outMoney = request.getParameter("outMoney");
		final String rate = sysConfigCacheManager
 				.getConfigValue(SysConfigCacheKeyEnum.WITHDRAW_RATE);

		final JsonResult jsonResult = new JsonResult(true);
		controllerTemplate.execute(jsonResult, new ControllerCallBack() {

			@Override
			public void executeService() {
				
				AccountDO accountDO = new AccountDO();
				double realRate = Double.parseDouble(rate);
				Money withdrawMoney = new Money(outMoney);
				Money realAmount = withdrawMoney.subtract(withdrawMoney
						.multiply(realRate));
				accountDO.setUserId(userId);
				accountDO.setAmount(realAmount);
				accountDO.setAccountNo(UUIDHexGenerator.getNum());
				accountDO.setType("6");// 类型 1：充值  2:个人余额提现 3：分红 4:返现 5好友返利 6商铺收益提现 7联盟商家提现
				accountDO.setSubType("2");// 0：提现失败 1：提现成功 2:提现进行中
				
				int result1 = accountDao.outMoney(accountDO);

				// 修改店铺收入表
				ShopIncomeDO shopIncomeDO = shopIncomeDAO.selectShopIncomeInfoByUserIdAndShopId(userId, shopId);
				Money shopincomeAccout = shopIncomeDO.getIncomeAccout();
				if(shopincomeAccout.lessThan(withdrawMoney)){
					jsonResult.setBizSucc(false);
					jsonResult.setErrMsg("店铺收益额不足");
					return;
				}
                
				int result2 = shopIncomeDAO.changeShopIncome(shopincomeAccout.subtract(withdrawMoney), userId, shopId);

				if (result1 <= 0  && result2 <= 0) {
					jsonResult.setBizSucc(false);
					jsonResult.setErrMsg("提交失败");
					return;
				}

				try {
					// 公众号发送提示消息
					SysConfigDO sysConfigDO = sysConfigDAO.selectByKey(SysConfigCacheKeyEnum.ACCESS_TOKEN.toString());
					String accessToken = sysConfigDO.getSysValue();
//					String accessToken = sysConfigCacheManager
//							.getConfigValue(SysConfigCacheKeyEnum.ACCESS_TOKEN);

					UserDO userDO = userDao.selectUserInfoByUserId(userId);
					if (null != userDO) {
						String jsonTextMsg = "";
						jsonTextMsg = SendCustomMessage.makeTextCustomMessage(
								userDO.getWechatId(),
								"亲爱的贵宾，您的店铺收益提现申请已成功受理，正在审核，请耐心等待");
						SendCustomMessage.sendCustomMessage(accessToken,
								jsonTextMsg);
					}
					
				} catch (Exception e) {
					e.printStackTrace();
				}
			}

			@Override
			public void check() {
			}
		});
		return jsonResult;
	}
	
}
