package com.onway.web.controller.mng;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.onway.common.lang.Money;
import com.onway.makeploy.common.dal.dataobject.AgentDO;
import com.onway.makeploy.common.dal.dataobject.AgentInfoDO;
import com.onway.makeploy.common.dal.dataobject.OrderDO;
import com.onway.makeploy.common.dal.dataobject.ShopDO;
import com.onway.web.controller.AbstractController;
import com.onway.web.controller.result.JsonResult;

@Controller
public class AgentInfoController extends AbstractController {
    @RequestMapping("/agentInfo.html")
    public String anentInfo(HttpServletRequest request, ModelMap modelMap) {
        //			List<AgentDO> agentInfoList=agentDao.selectAgentInfo(null, null, null, null, null);
        //			modelMap.addAttribute("agentInfoList", agentInfoList);
    	String userId = (String)request.getSession().getAttribute("userId");
    	boolean checkRoleBoss = checkRoleBoss(userId);
    	if(checkRoleBoss == false){
    		return "mng/loginMng";
    	}
    	
    	//判断是否为运营者
        int checkOperative = checkOperative(userId);
        modelMap.put("checkOperative", checkOperative);
        return "mng/agentInfo";
    }

    @RequestMapping("searchAgent.do")
    public String searchAgent(HttpServletRequest request, ModelMap modelMap) {
    	
    	String userIds = (String) request.getSession().getAttribute("userId");
		boolean checkRoleBoss = checkRoleBoss(userIds);
		if (checkRoleBoss == false) {
			return "mng/loginMng";
		}
		//判断是否为运营者
        int checkOperative = checkOperative(userIds);
        modelMap.put("checkOperative", checkOperative);
    	
        String userId = request.getParameter("userId");
        String cell = request.getParameter("cell");
        String province = request.getParameter("province");
        String city = request.getParameter("city");
        String district = request.getParameter("district");

        int totalItems = (int) agentDao.count(userId, cell, province, city, district);
        Map<String, Integer> pageDate = getPageData(request);

        List<AgentDO> agentInfoList = agentDao.selectAgentInfo(userId, cell, province, city,
            district, pageDate.get(OFFSET) / 2, pageDate.get(PAGE_SIZE_STR) / 2);

        modelMap.put("totalPages", calculatePage(totalItems, pageDate.get(PAGE_SIZE_STR) / 2));
        modelMap.put(CURRENTPAGE, pageDate.get(CURRENTPAGE));
        modelMap.put("totalItems", totalItems);
        request.setAttribute("page", modelMap);
        List<AgentInfoDO> newAgentInfoList = new ArrayList<AgentInfoDO>();
        for (AgentDO agent : agentInfoList) {
            Money areaIncome = null;
            Date start = agent.getCheckOut();
            province = agent.getProvince();
            city = agent.getCity();
            district = agent.getDistrict();
            areaIncome = agent.getAreaIncome();
            modelMap.addAttribute("areaIncome", areaIncome);
            //截止至今可得区域收入
            List<ShopDO> shopList = shopDAO.selectAllShop();
            List<String> usefulShop = new ArrayList<String>();
            for (ShopDO tempshop : shopList) {
                String addr1 = tempshop.getShopAddr();// 浙江省-金华市-东阳市/湖莲西街88号
                if (!StringUtils.isEmpty(addr1)) {
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
            //获取截止当前可取金额(运费)
            Money estimatedIncome = new Money(0.00);
            for (String shopId : usefulShop) {
                List<OrderDO> incomeList = orderDao.selectIncome(shopId, null, start, null);
                if (!incomeList.isEmpty()) {
                    Money income = null;
                    income = incomeList.get(0).getOrderPrice();
                    for (int i = 1; i < incomeList.size(); i++) {
                        income = income.add(incomeList.get(i).getOrderPrice().add(incomeList.get(i).getLuggage()));
                    }
                    Double off = Double.parseDouble(sysConfigDAO.selectByKey("AGENT_INCOME_RATE")
                        .getSysValue());
                    Double offIncome = Double.parseDouble(income.toString()) * (1 - off);
                    estimatedIncome = estimatedIncome.add(new Money(
                        Math.round(offIncome * 100) / 100.0));
                }
            }
            areaIncome = agentDao.selectAll(agent.getUserId(), agent.getCell(),
                agent.getProvince(), agent.getCity(), agent.getDistrict()).getAreaIncome();
            AgentInfoDO agentInfo = new AgentInfoDO();
            //建立新的 agentInfo  对象
            agentInfo.setUserId(agent.getUserId());
            agentInfo.setUserName(agent.getUserName());
            agentInfo.setCell(agent.getCell());
            agentInfo.setProvince(agent.getProvince());
            agentInfo.setCity(agent.getCity());
            agentInfo.setDistrict(agent.getDistrict());
            //累计收益，每一次查询都要更新一波
            agentInfo.setEstimatedIncome(estimatedIncome);
            agentInfo.setAreaIncome(areaIncome);
            agentInfo.setCheckOut(agent.getCheckOut());
            newAgentInfoList.add(agentInfo);//往新的集合里放新DO
        }

        modelMap.addAttribute("agentInfoList", newAgentInfoList);

        return "mng/agentInfo";
    }

    @RequestMapping("updateCheckout.do")
    @ResponseBody
    public Object updateCheckout(HttpServletRequest request, ModelMap modelmap) {
        JsonResult result = new JsonResult(false);
        String userId = request.getParameter("userId");
        String income = request.getParameter("income");
        Money inCome = new Money();
        if (StringUtils.isBlank(income)) {
            inCome = new Money("0");
        } else {
            inCome = new Money(income);//新提取的金额
        }
        Money areaIncome = new Money();
        areaIncome = agentDao.selectAll(userId, null, null, null, null).getAreaIncome();
        areaIncome = areaIncome.add(inCome);//提取后累计到areaIncome
        agentDao.updateCheckout(areaIncome, userId);
        result.setBizSucc(true);

        return result;
    }
}
