package com.onway.web.controller.mng;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import com.onway.common.lang.Money;
import com.onway.makeploy.common.dal.dataobject.AgentDO;
import com.onway.makeploy.common.dal.dataobject.CommerceCheckDO;
import com.onway.makeploy.common.dal.dataobject.CommerceDO;
import com.onway.makeploy.common.dal.dataobject.OrderDO;
import com.onway.makeploy.common.dal.dataobject.RoleDO;
import com.onway.makeploy.common.dal.dataobject.ShopDO;
import com.onway.makeploy.common.dal.dataobject.ShopIncomeDO;
import com.onway.makeploy.common.dal.dataobject.UserDO;
import com.onway.web.controller.AbstractController;

@Controller
public class LoginMngController extends AbstractController {
    /**
     * 进入登入页面
     * @param request
     * @param modelMap
     * @return
     */
    @RequestMapping("/loginMng.html")
    public String loginMng(HttpServletRequest request, ModelMap modelMap) {

        return "mng/loginMng";
    }
    
    @RequestMapping("/")
    public String loginMngDo(HttpServletRequest request, ModelMap modelMap) {

        return "mng/loginMng";
    }

    /**
     * 登入验证
     * @param request
     * @param modelMap
     * @return
     */
    @RequestMapping("/LoginMng.do")
    public String LoginMng(HttpServletRequest request, ModelMap modelMap) {
        String cell = request.getParameter("cell");
        String passWord = request.getParameter("password");
        String role = request.getParameter("identity");
        String result = userDao.login(cell, role);

        if (null == role) {
            modelMap.put("content", "*请选择！");
            return "mng/loginMng";
        } else {
            if ("" == result || null == result) {
                modelMap.put("activeType", role);
                modelMap.put("name", "*用户名或身份有误！");
                return "mng/loginMng";
            } else {
                // 管理员
                if ("1".equals(role)) {
                    if (result.equals(passWord)) {
                    	UserDO userdo = userDao.selectUserInfoByCell(cell);
                    	String userId = userdo.getUserId();
                    	request.getSession().setAttribute("userId", userId);
                        request.getSession().setAttribute("activeUser", "1");
                        //判断是否为运营者
                        int checkOperative = checkOperative(userId);
                        modelMap.put("checkOperative", checkOperative);
                        request.getSession().setAttribute("checkOperative", checkOperative);
                        return "mng/index";
                    } else {
                        modelMap.put("activeType", role);
                        modelMap.put("passw", "*密码或身份有误！");
                        return "mng/loginMng";
                    }
                }
                //商户
                else if ("0".equals(role)) {
                    if (result.equals(passWord)) {
                        try {
                            UserDO userdo = userDao.selectUserInfoByCell(cell);
                            String userId = userdo.getUserId();
                            String shopId = userdo.getShopId();
                            request.getSession().setAttribute("shopId", shopId);
                            request.getSession().setAttribute("userId", userId);//储存商户ID
                            request.getSession().setAttribute("activeUser", "0");
                            int unPayOrderCount = orderDao.selectCountByShopID(shopId, null, "1",
                                "1", "3");
                            int unSendOrderCount = orderDao.selectCountByShopID(shopId, null, "1",
                                "3", "3");
                            int unReciveOrderCount = orderDao.selectCountByShopID(shopId, null,
                                "2", "3", "3");
                            int backOrderCount = orderDao.selectCountByShopID(shopId, null, "3",
                                "3", "3");
                            UserDO uesrDo = userDao.selectUserInfoByUserId(userId);
                            ShopDO shopDo = shopDAO.selectShopByShopId(shopId);
                            modelMap.put("unPayOrderCount", unPayOrderCount);
                            modelMap.put("unSendOrderCount", unSendOrderCount);
                            modelMap.put("unReciveOrderCount", unReciveOrderCount);
                            modelMap.put("backOrderCount", backOrderCount);
                            modelMap.put("uesrDo", uesrDo);
                            modelMap.put("shopDo", shopDo);
                            
                            //店铺收入
                            ShopIncomeDO shopIncomeDO = shopIncomeDAO.selectShopIncomeInfoByUserIdAndShopId(userId, shopId);
                            modelMap.put("shopIncomeDO", shopIncomeDO);
                            //判断该登录账户是否联盟商家
                            
                            String unionFlg = shopDo.getUnionFlg();
                            if(StringUtils.equals(unionFlg, "1")){
                            	 return "mng/shopOwerMng";
                            }else{
                            	 return "mng/shopOwerMngPartner";
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    } else {
                        modelMap.put("activeType", role);
                        modelMap.put("passw", "*密码或身份有误！");
                        return "mng/loginMng";
                    }
                }
                //区域代理
                else if ("2".equals(role)) {
                    if (result.equals(passWord)) {
                        AgentDO agentDo = agentDao.selectAll(null, cell, null, null, null);
                        request.getSession().setAttribute("userId", agentDo.getUserId());
                        request.getSession().setAttribute("activeUser", "2");
                        modelMap.addAttribute("agent", agentDo);
                        Money areaIncome = null;
                        Date start = agentDo.getCheckOut();
                        String province = agentDo.getProvince();
                        String city = agentDo.getCity();
                        String district = agentDo.getDistrict();
                        areaIncome = agentDao.selectAll(null, cell, null, null, null)
                            .getAreaIncome();
                        modelMap.addAttribute("areaIncome", areaIncome);
                        //截止至今可得区域收入
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
                                    if (addr3[0].equals(province)) {
                                        usefulShop.add(tempshop.getShopId());//省满足,表示是市代表
                                    }
                                }
                            }

                        }
                        Money estimatedIncome = new Money(0.00);
                        for (String shopId : usefulShop) {
                            List<OrderDO> incomeList = orderDao.selectIncome(shopId, null,start, null);
                            if (!incomeList.isEmpty()) {
                                Money income = null;
                                income = incomeList.get(0).getOrderPrice();
                                for (int i = 1; i < incomeList.size(); i++) {
                                    income = income.add(incomeList.get(i).getOrderPrice());
                                }
                                Double off = Double.parseDouble(sysConfigDAO.selectByKey(
                                    "AGENT_INCOME_RATE").getSysValue());
                                Double offIncome = Double.parseDouble(income.toString())
                                                   * (1 - off);
                                estimatedIncome = estimatedIncome.add(new Money(Math
                                    .round(offIncome * 100) / 100.0));
                            }
                        }
                        modelMap.addAttribute("estimatedIncome", estimatedIncome);
                        return "mng/agentMng";
                    } 
                    else {
                        modelMap.put("activeType", role);
                        modelMap.put("passw", "*密码身份有误！");
                        return "mng/loginMng";
                    }
                }
              //商会
                else if ("3".equals(role)) {
                    if (result.equals(passWord)) {
                    	UserDO userdo = userDao.selectUserInfoByCell(cell);
                    	String userId = userdo.getUserId();
                    	request.getSession().setAttribute("userId", userId);
                        request.getSession().setAttribute("activeUser", "3");
                        
                        RoleDO roleDO = roleDao.searchCommerceId(userId);
                        String commerceId = roleDO.getMemo();
                        request.getSession().setAttribute("commerceId", commerceId);
                        
                        CommerceDO commerceDO = commerceDAO.selectCommerceInfoByCommerceId(commerceId);
                        modelMap.addAttribute("commerceDO", commerceDO);
                        
                        CommerceResult commerceResult = new CommerceResult();
                        
                		String leaderId = commerceDO.getCommerceLeader();//主席
            	    	String chargeId = commerceDO.getCommerceCharge();//负责人
            	    	String secretaryId = commerceDO.getCommerceSecretaryUser();//秘书长
            	    	CommerceCheckDO leaderCheckUser = null;
            	    	CommerceCheckDO chargeCheckUser = null;
            	    	CommerceCheckDO secretaryCheckUser = null;
            	    	if(!StringUtils.isBlank(leaderId)){
            	    		leaderCheckUser = commerceCheckDAO.selectMyCommerceCheck(leaderId,commerceId);
            	    	}
            	    	if(!StringUtils.isBlank(chargeId)){
            	    		chargeCheckUser = commerceCheckDAO.selectMyCommerceCheck(chargeId,commerceId);
            	    	}
            	    	if(!StringUtils.isBlank(secretaryId)){
            	    		secretaryCheckUser = commerceCheckDAO.selectMyCommerceCheck(secretaryId,commerceId);
            	    	}
            	    	
            	    	commerceResult.setCommerceDO(commerceDO);
            	    	commerceResult.setLeaderCheckUser(leaderCheckUser);
            	    	commerceResult.setChargeCheckUser(chargeCheckUser);
            	    	commerceResult.setSecretaryCheckUser(secretaryCheckUser);
                		
                		String viceLeaderId = commerceDO.getCommerceViceLeader();//副主席
                		List<CommerceCheckDO> viceCheckLeader = new ArrayList<CommerceCheckDO>();
            	    	if(!StringUtils.isBlank(viceLeaderId)){
        	    	    	String[] viceStr = viceLeaderId.split(",");
        	    	    	for (String viceStrId : viceStr) {
        	    				if(!StringUtils.isBlank(viceStrId)){
        	    					CommerceCheckDO viceCheck = commerceCheckDAO.selectMyCommerceCheck(viceStrId,commerceId);
        	    					viceCheckLeader.add(viceCheck);
        	    				}
        	    			}
            	    	}
            	    	commerceResult.setViceCheckLeader(viceCheckLeader);
            	    	
            	    	modelMap.put("commerceResult", commerceResult);
                        
                        return "mng/commerceIndex";
                    } else {
                        modelMap.put("activeType", role);
                        modelMap.put("passw", "*密码或身份有误！");
                        return "mng/loginMng";
                    }
                }
                return "mng/loginMng";
            }
        }
    }
    
    @RequestMapping("commerceIndex.html")
    public String commerceUser(HttpServletRequest request, ModelMap modelMap) {
        String commerceId = (String)request.getSession().getAttribute("commerceId");
        
        CommerceDO commerceDO = commerceDAO.selectCommerceInfoByCommerceId(commerceId);
        modelMap.put("commerceDO", commerceDO);
        
        CommerceResult commerceResult = new CommerceResult();
        
		String leaderId = commerceDO.getCommerceLeader();//主席
    	String chargeId = commerceDO.getCommerceCharge();//负责人
    	String secretaryId = commerceDO.getCommerceSecretaryUser();//秘书长
    	CommerceCheckDO leaderCheckUser = null;
    	CommerceCheckDO chargeCheckUser = null;
    	CommerceCheckDO secretaryCheckUser = null;
    	if(!StringUtils.isBlank(leaderId)){
    		leaderCheckUser = commerceCheckDAO.selectMyCommerceCheck(leaderId,commerceId);
    	}
    	if(!StringUtils.isBlank(chargeId)){
    		chargeCheckUser = commerceCheckDAO.selectMyCommerceCheck(chargeId,commerceId);
    	}
    	if(!StringUtils.isBlank(secretaryId)){
    		secretaryCheckUser = commerceCheckDAO.selectMyCommerceCheck(secretaryId,commerceId);
    	}
    	
    	commerceResult.setCommerceDO(commerceDO);
    	commerceResult.setLeaderCheckUser(leaderCheckUser);
    	commerceResult.setChargeCheckUser(chargeCheckUser);
    	commerceResult.setSecretaryCheckUser(secretaryCheckUser);
		
		String viceLeaderId = commerceDO.getCommerceViceLeader();//副主席
		List<CommerceCheckDO> viceCheckLeader = new ArrayList<CommerceCheckDO>();
    	if(!StringUtils.isBlank(viceLeaderId)){
	    	String[] viceStr = viceLeaderId.split(",");
	    	for (String viceStrId : viceStr) {
				if(!StringUtils.isBlank(viceStrId)){
					CommerceCheckDO viceCheck = commerceCheckDAO.selectMyCommerceCheck(viceStrId,commerceId);
					viceCheckLeader.add(viceCheck);
				}
			}
    	}
    	commerceResult.setViceCheckLeader(viceCheckLeader);
    	
    	modelMap.put("commerceResult", commerceResult);
        return "mng/commerceIndex";
    }
}
