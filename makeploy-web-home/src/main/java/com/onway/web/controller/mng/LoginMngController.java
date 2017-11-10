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
     * �������ҳ��
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
     * ������֤
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
            modelMap.put("content", "*��ѡ��");
            return "mng/loginMng";
        } else {
            if ("" == result || null == result) {
                modelMap.put("activeType", role);
                modelMap.put("name", "*�û������������");
                return "mng/loginMng";
            } else {
                // ����Ա
                if ("1".equals(role)) {
                    if (result.equals(passWord)) {
                    	UserDO userdo = userDao.selectUserInfoByCell(cell);
                    	String userId = userdo.getUserId();
                    	request.getSession().setAttribute("userId", userId);
                        request.getSession().setAttribute("activeUser", "1");
                        //�ж��Ƿ�Ϊ��Ӫ��
                        int checkOperative = checkOperative(userId);
                        modelMap.put("checkOperative", checkOperative);
                        request.getSession().setAttribute("checkOperative", checkOperative);
                        return "mng/index";
                    } else {
                        modelMap.put("activeType", role);
                        modelMap.put("passw", "*������������");
                        return "mng/loginMng";
                    }
                }
                //�̻�
                else if ("0".equals(role)) {
                    if (result.equals(passWord)) {
                        try {
                            UserDO userdo = userDao.selectUserInfoByCell(cell);
                            String userId = userdo.getUserId();
                            String shopId = userdo.getShopId();
                            request.getSession().setAttribute("shopId", shopId);
                            request.getSession().setAttribute("userId", userId);//�����̻�ID
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
                            
                            //��������
                            ShopIncomeDO shopIncomeDO = shopIncomeDAO.selectShopIncomeInfoByUserIdAndShopId(userId, shopId);
                            modelMap.put("shopIncomeDO", shopIncomeDO);
                            //�жϸõ�¼�˻��Ƿ������̼�
                            
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
                        modelMap.put("passw", "*������������");
                        return "mng/loginMng";
                    }
                }
                //�������
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
                        //��ֹ����ɵ���������
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
                                    if (addr3[0].equals(province)) {
                                        usefulShop.add(tempshop.getShopId());//ʡ����,��ʾ���д���
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
                        modelMap.put("passw", "*�����������");
                        return "mng/loginMng";
                    }
                }
              //�̻�
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
                        
                		String leaderId = commerceDO.getCommerceLeader();//��ϯ
            	    	String chargeId = commerceDO.getCommerceCharge();//������
            	    	String secretaryId = commerceDO.getCommerceSecretaryUser();//���鳤
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
                		
                		String viceLeaderId = commerceDO.getCommerceViceLeader();//����ϯ
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
                        modelMap.put("passw", "*������������");
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
        
		String leaderId = commerceDO.getCommerceLeader();//��ϯ
    	String chargeId = commerceDO.getCommerceCharge();//������
    	String secretaryId = commerceDO.getCommerceSecretaryUser();//���鳤
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
		
		String viceLeaderId = commerceDO.getCommerceViceLeader();//����ϯ
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
