package com.onway.web.controller.mng;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.onway.common.lang.DateUtils;
import com.onway.common.lang.Money;
import com.onway.common.lang.StringUtils;
import com.onway.makeploy.common.dal.dataobject.AccountDO;
import com.onway.makeploy.common.dal.dataobject.AgentDO;
import com.onway.makeploy.common.dal.dataobject.OrderDO;
import com.onway.makeploy.common.dal.dataobject.ShopDO;
import com.onway.makeploy.common.dal.dataobject.SysConfigDO;
import com.onway.makeploy.common.dal.dataobject.UserDO;
import com.onway.makeploy.core.localcache.enums.SysConfigCacheKeyEnum;
import com.onway.web.controller.AbstractController;
import com.onway.web.controller.UUIDHexGenerator;
import com.onway.web.controller.result.JsonResult;
import com.onway.web.controller.sendMessage.SendCustomMessage;
import com.onway.web.controller.template.ControllerCallBack;

@Controller
public class AgentMngController extends AbstractController {

    @RequestMapping("/agentMng.html")
    public String agent(HttpServletRequest request, ModelMap modelMap) {
        return "mng/agentMng";
    }

    @RequestMapping("searchIncome.do")
    @ResponseBody
    public Object searchIncome(HttpServletRequest request, ModelMap modelMap) {
        JsonResult result = new JsonResult(false);
        String cell = request.getParameter("cell");
        String start1 = request.getParameter("startDate");
//        String end1 = request.getParameter("endDate");
        Date start = null;
//        Date end = null;
        if (StringUtils.isNotBlank(start1)) {
            try {
                start = DateUtils.parseDate(start1, DateUtils.webFormat);
            } catch (ParseException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
//        if (StringUtils.isNotBlank(end1)) {
//            try {
//                end = DateUtils.parseDate(end1, DateUtils.webFormat);
//            } catch (ParseException e) {
//                // TODO Auto-generated catch block
//                e.printStackTrace();
//            }
//        }
        //区间查询
        AgentDO agentDo = agentDao.selectAll(null, cell, null, null, null);
        String province = agentDo.getProvince();
        String city = agentDo.getCity();
        String district = agentDo.getDistrict();
        List<ShopDO> shopList = shopDAO.selectAllShop();
        List<String> usefulShop = new ArrayList<String>();
        for (ShopDO tempshop : shopList) {
            String addr1 = tempshop.getShopAddr();// 浙江省-金华市-东阳市/湖莲西街88号
            if (null != addr1 || "".equals(addr1)) {
                String[] addr2 = addr1.split("/");// [0]浙江省-金华市-东阳市   [1]湖莲西街88号
                String[] addr3 = addr2[0].split("-");

                if (null != district) {
                    if (addr3[2].equals(district)) {
                        usefulShop.add(tempshop.getShopId());//区满足,表示是区代表
                    }
                } else if (null != city) {
                    if (addr3[1].equals(city)) {
                        usefulShop.add(tempshop.getShopId());//市满足,表示是市代表
                    }
                } else if (null != province) {
                    if (addr3[0].equals(city)) {
                        usefulShop.add(tempshop.getShopId());//市满足,表示是市代表
                    }
                }
            }

        }
        Money estimatedIncome = new Money(0.00);
        for (String shopId : usefulShop) {
            List<OrderDO> incomeList = orderDao.selectIncome(shopId,null, start, null);
            if (!incomeList.isEmpty()) {
                Money income = null;
                income = incomeList.get(0).getOrderPrice();
                for (int i = 1; i < incomeList.size(); i++) {
                    income = income.add(incomeList.get(i).getOrderPrice());
                }
                Double off = Double.parseDouble(sysConfigDAO.selectByKey("AGENT_INCOME_RATE")
                    .getSysValue());
                Double offIncome = Double.parseDouble(income.toString()) * (1 - off);
                estimatedIncome = estimatedIncome
                    .add(new Money(Math.round(offIncome * 100) / 100.0));
                result.setInformation(estimatedIncome + " 元");
                result.setBizSucc(true);
            } else {
                continue;
            }
        }
        /*		List<OrderDO> incomeList=orderDao.selectIncome(province, city, district, start, end);
        		if(incomeList.isEmpty()){
        			result.setBizSucc(false);
        			result.setInformation("未找到符合区间的收益");
        		}else{
        		Money income = null;
        		income=incomeList.get(0).getOrderPrice();
        		for(int i=1;i<incomeList.size();i++){
        			income=income.add(incomeList.get(i).getOrderPrice());
        			
        		 }
        		Double off=Double.parseDouble(sysConfigDAO.selectByKey("AGENT_INCOME_RATE").getSysValue());
        		Double offIncome=Double.parseDouble(income.toString())*(off-1)*-1;
        		result.setInformation(estimatedIncome+" 元");
        		result.setBizSucc(true);
        		}*/
        result.setInformation("为找到符合区间的收益");
        return result;
    }
    
	/**
	 * 店铺收入提现审核
	 * @param request
	 * @param modelMap
	 * @return
	 */
	@RequestMapping("outAreaMoney.html")
	public String outAreaMoney(HttpServletRequest request,ModelMap modelMap){
		
		final String userId = (String) request.getSession().getAttribute(
				"userId");
		
		UserDO userDO = userDao.selectUserInfoByUserId(userId);
		modelMap.put("userDO", userDO);
		
		AgentDO agentDO = agentDao.selectAll(userId, null, null, null, null);
		modelMap.put("agentDO", agentDO);
	    
	    // 提现费率
	 	String rate = sysConfigCacheManager
	 				.getConfigValue(SysConfigCacheKeyEnum.WITHDRAW_RATE);

	 	modelMap.put("rate", rate);
	    
		return "mng/outAreaMoney";
	}
	
	/**
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping("outAreaMoneyByUserId.do")
	@ResponseBody
	public Object outAreaMoneyByUserId(HttpServletRequest request){
		final String userId = (String) request.getSession().getAttribute(
				"userId");
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
				accountDO.setType("7");// 类型 1：充值  2:个人余额提现 3：分红 4:返现 5好友返利 6商铺收益提现 7联盟商家提现
				accountDO.setSubType("2");// 0：提现失败 1：提现成功 2:提现进行中
				
				int result1 = accountDao.outMoney(accountDO);

				// 修改区域代理收益
				AgentDO agentDO = agentDao.selectAll(userId, null, null, null, null);
				Money areaIncome = agentDO.getAreaIncome();
				if(areaIncome.lessThan(withdrawMoney)){
					jsonResult.setBizSucc(false);
					jsonResult.setErrMsg("区域收益额不足");
					return;
				}
				
				int result2 = agentDao.updateOutMoney(areaIncome.subtract(withdrawMoney), userId);

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
