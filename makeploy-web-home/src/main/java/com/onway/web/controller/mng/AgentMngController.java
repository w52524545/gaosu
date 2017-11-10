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
        //�����ѯ
        AgentDO agentDo = agentDao.selectAll(null, cell, null, null, null);
        String province = agentDo.getProvince();
        String city = agentDo.getCity();
        String district = agentDo.getDistrict();
        List<ShopDO> shopList = shopDAO.selectAllShop();
        List<String> usefulShop = new ArrayList<String>();
        for (ShopDO tempshop : shopList) {
            String addr1 = tempshop.getShopAddr();// �㽭ʡ-����-������/��������88��
            if (null != addr1 || "".equals(addr1)) {
                String[] addr2 = addr1.split("/");// [0]�㽭ʡ-����-������   [1]��������88��
                String[] addr3 = addr2[0].split("-");

                if (null != district) {
                    if (addr3[2].equals(district)) {
                        usefulShop.add(tempshop.getShopId());//������,��ʾ��������
                    }
                } else if (null != city) {
                    if (addr3[1].equals(city)) {
                        usefulShop.add(tempshop.getShopId());//������,��ʾ���д���
                    }
                } else if (null != province) {
                    if (addr3[0].equals(city)) {
                        usefulShop.add(tempshop.getShopId());//������,��ʾ���д���
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
                result.setInformation(estimatedIncome + " Ԫ");
                result.setBizSucc(true);
            } else {
                continue;
            }
        }
        /*		List<OrderDO> incomeList=orderDao.selectIncome(province, city, district, start, end);
        		if(incomeList.isEmpty()){
        			result.setBizSucc(false);
        			result.setInformation("δ�ҵ��������������");
        		}else{
        		Money income = null;
        		income=incomeList.get(0).getOrderPrice();
        		for(int i=1;i<incomeList.size();i++){
        			income=income.add(incomeList.get(i).getOrderPrice());
        			
        		 }
        		Double off=Double.parseDouble(sysConfigDAO.selectByKey("AGENT_INCOME_RATE").getSysValue());
        		Double offIncome=Double.parseDouble(income.toString())*(off-1)*-1;
        		result.setInformation(estimatedIncome+" Ԫ");
        		result.setBizSucc(true);
        		}*/
        result.setInformation("Ϊ�ҵ��������������");
        return result;
    }
    
	/**
	 * ���������������
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
	    
	    // ���ַ���
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
				accountDO.setType("7");// ���� 1����ֵ  2:����������� 3���ֺ� 4:���� 5���ѷ��� 6������������ 7�����̼�����
				accountDO.setSubType("2");// 0������ʧ�� 1�����ֳɹ� 2:���ֽ�����
				
				int result1 = accountDao.outMoney(accountDO);

				// �޸������������
				AgentDO agentDO = agentDao.selectAll(userId, null, null, null, null);
				Money areaIncome = agentDO.getAreaIncome();
				if(areaIncome.lessThan(withdrawMoney)){
					jsonResult.setBizSucc(false);
					jsonResult.setErrMsg("����������");
					return;
				}
				
				int result2 = agentDao.updateOutMoney(areaIncome.subtract(withdrawMoney), userId);

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
