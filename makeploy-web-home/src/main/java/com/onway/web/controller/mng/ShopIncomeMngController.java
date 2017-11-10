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

    //����������Ϣ��ѯҳ��
	@RequestMapping("/shopIncomeMng.html")
	public String shopMng(HttpServletRequest request, ModelMap modelMap) {
		String userId = (String)request.getSession().getAttribute("userId");
    	boolean checkRoleBoss = checkRoleBoss(userId);
    	if(checkRoleBoss == false){
    		return "mng/loginMng";
    	}
    	//�ж��Ƿ�Ϊ��Ӫ��
        int checkOperative = checkOperative(userId);
        modelMap.put("checkOperative", checkOperative);
		
        String flag = request.getParameter("selectedNew");
        //flag 0����֧��  1΢��֧�� 2�����֧��
        //check 1���� 2����
        
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
				 * ����mysql���ݿ����ȡʱ�����䲻��������ʱ���bug,����Ҫ��ѡ���Ľ���ʱ���׷��addDays��������׷��һ��
				 */
				endDate = DateUtils.addDays(endDate, (long)1);
			}
			List<ShopIncomeDO> list=shopIncomeDAO.selectIncomeInfo(shopId, startDate, endDate, pageDate.get(OFFSET), pageDate.get(PAGE_SIZE_STR));
			int count=(int) shopIncomeDAO.selectIncomeInfoCount(shopId, startDate, endDate);
			
			//��ѯʱ����ڵ����ж���
			//flag 0����֧��  1΢��֧�� 2�����֧��
	        //check 1���� 2����
			List<OrderDO> allIncome = new ArrayList<OrderDO>();
			if(StringUtils.equals(flag, "1") && StringUtils.equals(check, "1")){
				allIncome = orderDao.selectMounthOrder(shopId,"1");//�¶���  ΢��֧��
			}else if(StringUtils.equals(flag, "1") && StringUtils.equals(check, "2")){
				allIncome = orderDao.selectWeekOrder(shopId,"1");//�ܶ���  ΢��֧��
			}else if(StringUtils.equals(flag, "0") && StringUtils.equals(check, "1")){
				allIncome = orderDao.selectMounthOrder(shopId,null);//�¶��� ����֧��
			}else if(StringUtils.equals(flag, "0") && StringUtils.equals(check, "2")){
				allIncome = orderDao.selectWeekOrder(shopId,null);//�ܶ��� ����֧��
			}else if(StringUtils.equals(flag, "2") && StringUtils.equals(check, "1")){
				allIncome = orderDao.selectMounthOrder(shopId,"4");//�¶��� �����
			}else if(StringUtils.equals(flag, "2") && StringUtils.equals(check, "1")){
				allIncome = orderDao.selectWeekOrder(shopId,"4");//�ܶ��� �����
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
			
		modelMap.put("totalPages",calculatePage(count, pageDate.get(PAGE_SIZE_STR))); // ������ҳ��
		modelMap.put(CURRENTPAGE, pageDate.get(CURRENTPAGE));//��ǰҳ��
		modelMap.put("totalItems", count);// ����������
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
    	//�ж��Ƿ�Ϊ��Ӫ��
        int checkOperative = checkOperative(userId);
        modelMap.put("checkOperative", checkOperative);
        
        String flag = request.getParameter("selectedNew");
        //flag 0����֧��  1΢��֧�� 2�����֧��
        //check 1���� 2����
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
				 * ����mysql���ݿ����ȡʱ�����䲻��������ʱ���bug,����Ҫ��ѡ���Ľ���ʱ���׷��addDays��������׷��һ��
				 */
				endDate = DateUtils.addDays(endDate, (long)1);
			}
//			List<ShopIncomeDO> list=shopIncomeDAO.selectIncomeInfo(null, startDate, endDate, pageDate.get(OFFSET), pageDate.get(PAGE_SIZE_STR));
			int count=(int) shopIncomeDAO.selectIncomeInfoCount(null, startDate, endDate);
			
			//��ѯʱ����ڵ����ж���
			//PAY_WAY   1��΢�� 2��֧���� 3�����п� 4:�����֧��
			//flag 0����֧��  1΢��֧�� 2�����֧��
	        //check 1���� 2����
			List<OrderDO> allIncome = new ArrayList<OrderDO>();
			if(StringUtils.equals(flag, "1") && StringUtils.equals(check, "1")){
				allIncome = orderDao.selectMounthOrder(null,"1");//�¶���  ΢��֧��
			}else if(StringUtils.equals(flag, "1") && StringUtils.equals(check, "2")){
				allIncome = orderDao.selectWeekOrder(null,"1");//�ܶ���  ΢��֧��
			}else if(StringUtils.equals(flag, "0") && StringUtils.equals(check, "1")){
				allIncome = orderDao.selectMounthOrder(null,null);//�¶��� ����֧��
			}else if(StringUtils.equals(flag, "0") && StringUtils.equals(check, "2")){
				allIncome = orderDao.selectWeekOrder(null,null);//�ܶ��� ����֧��
			}else if(StringUtils.equals(flag, "2") && StringUtils.equals(check, "1")){
				allIncome = orderDao.selectMounthOrder(null,"4");//�¶��� �����
			}else if(StringUtils.equals(flag, "2") && StringUtils.equals(check, "1")){
				allIncome = orderDao.selectWeekOrder(null,"4");//�ܶ��� �����
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
			
		modelMap.put("totalPages",calculatePage(count, pageDate.get(PAGE_SIZE_STR))); // ������ҳ��
		modelMap.put(CURRENTPAGE, pageDate.get(CURRENTPAGE));//��ǰҳ��
		modelMap.put("totalItems", count);// ����������
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
	 * ���������������
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
	    
	    // ���ַ���
	 	String rate = sysConfigCacheManager
	 				.getConfigValue(SysConfigCacheKeyEnum.WITHDRAW_RATE);

	 	modelMap.put("rate", rate);
	    
		return "mng/outShopMoney";
	}
	
	/**
	 * �ύ������������
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
				accountDO.setType("6");// ���� 1����ֵ  2:����������� 3���ֺ� 4:���� 5���ѷ��� 6������������ 7�����̼�����
				accountDO.setSubType("2");// 0������ʧ�� 1�����ֳɹ� 2:���ֽ�����
				
				int result1 = accountDao.outMoney(accountDO);

				// �޸ĵ��������
				ShopIncomeDO shopIncomeDO = shopIncomeDAO.selectShopIncomeInfoByUserIdAndShopId(userId, shopId);
				Money shopincomeAccout = shopIncomeDO.getIncomeAccout();
				if(shopincomeAccout.lessThan(withdrawMoney)){
					jsonResult.setBizSucc(false);
					jsonResult.setErrMsg("����������");
					return;
				}
                
				int result2 = shopIncomeDAO.changeShopIncome(shopincomeAccout.subtract(withdrawMoney), userId, shopId);

				if (result1 <= 0  && result2 <= 0) {
					jsonResult.setBizSucc(false);
					jsonResult.setErrMsg("�ύʧ��");
					return;
				}

				try {
					// ���ںŷ�����ʾ��Ϣ
					SysConfigDO sysConfigDO = sysConfigDAO.selectByKey(SysConfigCacheKeyEnum.ACCESS_TOKEN.toString());
					String accessToken = sysConfigDO.getSysValue();
//					String accessToken = sysConfigCacheManager
//							.getConfigValue(SysConfigCacheKeyEnum.ACCESS_TOKEN);

					UserDO userDO = userDao.selectUserInfoByUserId(userId);
					if (null != userDO) {
						String jsonTextMsg = "";
						jsonTextMsg = SendCustomMessage.makeTextCustomMessage(
								userDO.getWechatId(),
								"�װ��Ĺ�������ĵ����������������ѳɹ�����������ˣ������ĵȴ�");
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
