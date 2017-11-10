package com.onway.web.controller.mng;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.onway.common.lang.Money;
import com.onway.makeploy.common.dal.dataobject.PartnerDO;
import com.onway.makeploy.common.dal.dataobject.ProductDO;
import com.onway.platform.common.enums.PlatformCodeEnum;
import com.onway.web.controller.AbstractController;
import com.onway.web.controller.result.JsonResult;

@Controller
public class ParenterMngController extends AbstractController {

	@RequestMapping("parenterMngSearch.html")
	public String searchUserId(HttpServletRequest request, ModelMap map) {
		String userId = request.getParameter("userId");
		List<ProductDO> list = productDao.searchShopId(userId);
		map.put("productInfo", list);
		request.setAttribute("", map);
		return "mng/parenterMng";

	}

	@RequestMapping("update.do")
	@ResponseBody
	public Object update(HttpServletRequest request) {
		JsonResult result = new JsonResult(false, "", "");
		String shopId = request.getParameter("shopId");
		String productNo = codeGenerateComponent
				.nextCodeByType(PlatformCodeEnum.MERCHANT_PLATFORM);
		String productName = request.getParameter("productName");
		String productType = request.getParameter("productType");
		String productDis = request.getParameter("productDis");
		String price = request.getParameter("price");
		String luggage = request.getParameter("luggage");
		String oldPrice = request.getParameter("oldPrice");
		Double productDis1 = Double.parseDouble(productDis);
		Money price1 = new Money(price);
		Money luggage1 = new Money(luggage);
		Money oldPrice1 = new Money(oldPrice);
		productDao.updateProduct(productNo, productName, productType, price1,
				productDis1, oldPrice1, luggage1, "2", shopId);
		return result;
	}
	
	/**
	 * 修改费用
	 * @param request
	 * @param modelMap
	 * @return
	 */
	@RequestMapping("changePartnerMoney.html")
	public String changePartnerMoney(HttpServletRequest request,ModelMap modelMap){
		String userIds = (String)request.getSession().getAttribute("userId");
    	boolean checkRoleBoss = checkRoleBoss(userIds);
    	if(checkRoleBoss == false){
    		return "mng/loginMng";
    	}
    	//判断是否为运营者
        int checkOperative = checkOperative(userIds);
        modelMap.put("checkOperative", checkOperative);
    	PartnerDO partnerDO = partnerDAO.selectPartnerMoney();
    	Money pAccout = partnerDO.getPartnerAccout();
    	
    	modelMap.put("pAccout", pAccout);
    	
		return "mng/changePartnerMoney";
	}
	
	/**
	 * 修改合伙人金额
	 * @param request
	 * @return
	 */
	@RequestMapping("changePartnerMoney.do")
    public ModelAndView partnerMoney(HttpServletRequest request, ModelMap modelMap){
		
		String money = request.getParameter("partnerMoney");
        Money partnerMoney = new Money(money);
        
        int flag1 = partnerDAO.updatePartnerMoney(partnerMoney,"1","1");
        modelMap.put("flag", flag1);
        
        
        return new ModelAndView("mng/partnerMoneyAddsuccess");
    }
	
	@RequestMapping("changePartnerSum.html")
	public String changePartnerSum(HttpServletRequest request, ModelMap map) {
		String userId = (String)request.getSession().getAttribute("userId");
    	boolean checkRoleBoss = checkRoleBoss(userId);
    	if(checkRoleBoss == false){
    		return "mng/loginMng";
    	}
    	//判断是否为运营者
        int checkOperative = checkOperative(userId);
        map.put("checkOperative", checkOperative);
        
        PartnerDO partnerDO = partnerDAO.selectPartnerMoney();
        
        int partnerCount = partnerDAO.selectPartnerCount();
        
        map.put("partnerDO", partnerDO);
        map.put("partnerCount", partnerCount);
        
		return "mng/changePartnerSum";
	}
	
	@RequestMapping("changePartnerSum.do")
    @ResponseBody
    public Object changePartnerSumdo(HttpServletRequest request) {
        JsonResult result = new JsonResult(false, "", "");
        String partnerSum = request.getParameter("partnerSum");
        
        int i = partnerDAO.changePartnerSum(partnerSum);
        if(i >= 1){
        	result.setBizSucc(true);
        }
        
		return result;
    }
	
}
