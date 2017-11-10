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
import com.onway.makeploy.common.dal.dataobject.CategoryDO;
import com.onway.makeploy.common.dal.dataobject.OrderDO;
import com.onway.makeploy.common.dal.dataobject.ProductDO;
import com.onway.makeploy.common.dal.dataobject.ProductParameterDO;
import com.onway.makeploy.common.dal.dataobject.ProductPromotionDO;
import com.onway.makeploy.common.dal.dataobject.ShopDO;
import com.onway.makeploy.common.dal.dataobject.ShopProInfoDO;
import com.onway.web.controller.AbstractController;
import com.onway.web.controller.result.JsonResult;

@Controller
public class ShopProInfoController extends AbstractController {

    @RequestMapping("/shopProInfo.html")
    public String shopProInfo(HttpServletRequest request, ModelMap map) {
    	String userId = (String)request.getSession().getAttribute("userId");
    	boolean checkRoleBoss = checkRoleBoss(userId);
    	if(checkRoleBoss == false){
    		return "mng/loginMng";
    	}
    	//判断是否为运营者
        int checkOperative = checkOperative(userId);
        map.put("checkOperative", checkOperative);
        String shopId = request.getParameter("shopId");
        String proNo = request.getParameter("productNo");
        String proName = request.getParameter("productName");
        String fatherType = request.getParameter("productType");
        String childrenType = request.getParameter("childrenType");
        int totalItems = (int) productDao.proInfoCount(shopId, proNo, proName, fatherType,
            childrenType);
        Map<String, Integer> pageData = getPageData(request);
        map.put("totalPages", calculatePage(totalItems, pageData.get(PAGE_SIZE_STR) / 2));
        map.put(CURRENTPAGE, pageData.get(CURRENTPAGE));
        map.put("totalItems", totalItems);

        List<String> allFathertype = categoryDAO.selectAllFather();

        List<ProductDO> tempList = productDao.selectShopProInfo(shopId, proNo, proName, fatherType,
            childrenType, pageData.get(OFFSET) / 2, pageData.get(PAGE_SIZE_STR) / 2);
        List<ShopProInfoDO> shopProInfoList = new ArrayList<ShopProInfoDO>();

        for (ProductDO temp : tempList) {
            List<ProductParameterDO> parameter = productParameterDAO
                .selectInfoByShopIdAndProductNo(temp.getProductNo(), temp.getShopId());
            if (parameter.isEmpty()) {
                ShopProInfoDO info = new ShopProInfoDO();
                info.setProImage(temp.getProductUrl());
                info.setProName(temp.getProductName());
                info.setFatherType(temp.getProductType());
                info.setChildrenType(temp.getChildren());
                info.setPrice(temp.getPrice());
                info.setrFlag(temp.getRecommendFlg());
                info.setStock(temp.getStock());
                info.setChildrenName("无");
                info.setShopId(temp.getShopId());
                info.setProductNo(temp.getProductNo());
                info.setProportionReturn(temp.getProportionReturn());//返现
                info.setProportionIntegral(temp.getProportionIntegral());//返积分
                info.setPraflag(null);
                shopProInfoList.add(info);
            } else {
                for (ProductParameterDO paratemp : parameter) {
                    ShopProInfoDO info = new ShopProInfoDO();
                    info.setProImage(temp.getProductUrl());
                    info.setProName(temp.getProductName());
                    info.setFatherType(temp.getProductType());
                    info.setChildrenType(temp.getChildren());
                    info.setPrice(temp.getPrice());
                    info.setrFlag(temp.getRecommendFlg());
                    info.setStock(paratemp.getStock());
                    info.setShopId(temp.getShopId());
                    info.setProductNo(temp.getProductNo());
                    info.setProportionReturn(temp.getProportionReturn());//返现
                    info.setProportionIntegral(temp.getProportionIntegral());//返积分
                    info.setPraflag(paratemp.getParflag());
                    String fatherName = paratemp.getFatherName();
                    String childName = paratemp.getChildrenName();
                    String tempFather = "";
                    String tempChild = "";
                    String[] a = fatherName.split("，");
                    for (int i = 0; i < a.length; i++) {
                        if (!a[i].equals("nk001")) {
                            tempFather += a[i] + "";
                        }
                    }
                    info.setFatherName(tempFather);
                    String[] b = childName.split("，");
                    for (int i = 0; i < b.length; i++) {
                        if (!b[i].equals("nk001")) {
                            tempChild += b[i] + "";
                        }
                    }
                    info.setChildrenName(tempChild);
                    shopProInfoList.add(info);

                }
            }

        }
        map.put("shopProInfoList", shopProInfoList);
        map.put("allFathertype", allFathertype);
        map.put("shopId", shopId);
        map.put("productNo", proNo);
        map.put("productName", proName);
        map.put("productType", fatherType);
        map.put("childrenType", childrenType);
        request.setAttribute("page", map);
        request.getSession().setAttribute("shopId", shopId);
        
        return "mng/shopProInfo";
    }

    @RequestMapping("selectShopProInfo.do")
    public String selectShopProInfo(HttpServletRequest request, ModelMap map) {
    	String shopId =(String) request.getSession().getAttribute("shopId");
//        String shopId = request.getParameter("shopId");
        String proNo = request.getParameter("productNo");
        String proName = request.getParameter("productName");
        String fatherType = request.getParameter("productType");
        String childrenType = request.getParameter("childrenType");
        int totalItems = (int) productDao.proInfoCount(shopId, proNo, proName, fatherType,
            childrenType);
        Map<String, Integer> pageData = getPageData(request);
        map.put("totalPages", calculatePage(totalItems, pageData.get(PAGE_SIZE_STR) / 2));
        map.put(CURRENTPAGE, pageData.get(CURRENTPAGE));
        map.put("totalItems", totalItems);

        List<String> allFathertype = categoryDAO.selectAllFather();

        List<ProductDO> tempList = productDao.selectShopProInfo(shopId, proNo, proName, fatherType,
            childrenType, pageData.get(OFFSET) / 2, pageData.get(PAGE_SIZE_STR) / 2);
        List<ShopProInfoDO> shopProInfoList = new ArrayList<ShopProInfoDO>();

        for (ProductDO temp : tempList) {

            List<ProductParameterDO> parameter = productParameterDAO
                .selectInfoByShopIdAndProductNo(temp.getProductNo(), temp.getShopId());
            if (parameter.isEmpty()) {
                ShopProInfoDO info = new ShopProInfoDO();
                info.setProImage(temp.getProductUrl());
                info.setProName(temp.getProductName());
                info.setFatherType(temp.getProductType());
                info.setChildrenType(temp.getChildren());
                info.setPrice(temp.getPrice());
                info.setrFlag(temp.getRecommendFlg());
                info.setStock(temp.getStock());
                info.setChildrenName("无");
                info.setShopId(temp.getShopId());
                info.setProductNo(temp.getProductNo());
                shopProInfoList.add(info);
            } else {
                for (ProductParameterDO paratemp : parameter) {
                    ShopProInfoDO info = new ShopProInfoDO();
                    info.setProImage(temp.getProductUrl());
                    info.setProName(temp.getProductName());
                    info.setFatherType(temp.getProductType());
                    info.setChildrenType(temp.getChildren());
                    info.setPrice(temp.getPrice());
                    info.setrFlag(temp.getRecommendFlg());
                    info.setStock(paratemp.getStock());
                    info.setShopId(temp.getShopId());
                    info.setProductNo(temp.getProductNo());
                    String fatherName = paratemp.getFatherName();
                    String childName = paratemp.getChildrenName();
                    String tempFather = "";
                    String tempChild = "";
                    String[] a = fatherName.split("，");
                    for (int i = 0; i < a.length; i++) {
                        if (!a[i].equals("nk001")) {
                            tempFather += a[i] + "";
                        }
                    }
                    info.setFatherName(tempFather);
                    String[] b = childName.split("，");
                    for (int i = 0; i < b.length; i++) {
                        if (!b[i].equals("nk001")) {
                            tempChild += b[i] + "";
                        }
                    }
                    info.setChildrenName(tempChild);
                    shopProInfoList.add(info);

                }
            }

        }
        map.put("shopProInfoList", shopProInfoList);
        map.put("allFathertype", allFathertype);
        map.put("shopId", shopId);
        map.put("productNo", proNo);
        map.put("productName", proName);
        map.put("productType", fatherType);
        map.put("childrenType", childrenType);
        request.setAttribute("page", map);
        return "mng/shopProInfo";
    }

    @RequestMapping("searchChildType.do")
    @ResponseBody
    public Object searchChildType(HttpServletRequest request, ModelMap map) {
        List<CategoryDO> childTypeList = new ArrayList<CategoryDO>();
        String fatherType = request.getParameter("fatherType");
        try {

            childTypeList = categoryDAO.selectByFather(fatherType);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return childTypeList;
    }
    
    @RequestMapping("productPromotion.html")
    public String productPromotion(HttpServletRequest request, ModelMap modelMap) {
    	String shopId = request.getParameter("shopId");
    	String productNo = request.getParameter("productNo");
    	
    	ProductPromotionDO promotionDO = productPromotionDAO.selectProPromotionByShopIdAndProductNo(shopId, productNo);
    	modelMap.put("promotionDO", promotionDO);
    	
    	ProductDO productDO = productDao.selectProductByProductNo(productNo, shopId);
    	ShopDO shopDO = shopDAO.selectShopByShopId(shopId);
    	modelMap.put("productDO", productDO);
    	modelMap.put("shopDO", shopDO);
    	String classification = sysConfigDAO.selectByKey("CLASSIFICATION_INDUSTRY").getSysValue();
        String[] claifi = classification.split(",");
        List<String> industry = new ArrayList<String>();
        for (int i = 0; i < claifi.length; i++) {
			String indu = claifi[i];
			industry.add(indu);
		}
        modelMap.put("industry", industry);
    	return "mng/productPromotion";
    }
    
	@RequestMapping("productPromotionChech.do")
    @ResponseBody
    public Object productPromotionChech(HttpServletRequest request, ModelMap map) {
    	JsonResult result = new JsonResult(false);
    	
    	String userId = (String)request.getSession().getAttribute("userId");
    	
    	String shopId = request.getParameter("shopId");
    	String productNo = request.getParameter("productNo");
    	//团购 限时秒杀 积分商城 赞助 其他
    	String type = request.getParameter("type");//类型
    	String price = request.getParameter("price");//输入后的价格
    	String oldPrice = request.getParameter("oldPrice");//原价格
    	String people = request.getParameter("needPeople");//
    	
    	String userName = request.getParameter("userName");
    	String cell = request.getParameter("cell");
    	String industry = request.getParameter("industry");
    	String postWay = request.getParameter("postWay");
    	String messages = request.getParameter("messages");
    	
    	
    	int needPeople = 0;
    	if(!StringUtils.isBlank(people)){
    	    needPeople = Integer.valueOf(people);
    	}
    	String typePro = null ;
    	if(StringUtils.equals(type, "团购")){
    		typePro = "1";
    	}else if(StringUtils.equals(type, "限时秒杀")){
    		typePro = "2";
    	}else if(StringUtils.equals(type, "积分商城")){
    		typePro = "3";
    	}else if(StringUtils.equals(type, "赞助")){
    		typePro = "4";
    	}else if(StringUtils.equals(type, "其他")){
    		typePro = "5";
    	}else{
    		typePro = null;
    	}
    	
    	//判断makeploy_product_promotion表是否有记录 (productNo , shopId)
    	ProductPromotionDO promotionDO = productPromotionDAO.selectProPromotionByShopIdAndProductNo(shopId, productNo);
    	if(null == promotionDO){
    		//没有 add
    		ProductDO productDO = productDao.selectProductByProductNo(productNo, shopId);
    		ShopDO shopDO = shopDAO.selectShopByShopId(shopId);
    		if(null != productDO && null != shopDO){
    			ProductPromotionDO productPromotionDO = new ProductPromotionDO();
    			productPromotionDO.setUserId(userId);
    			productPromotionDO.setShopId(shopId);
    			productPromotionDO.setShopName(shopDO.getShopName());
    			productPromotionDO.setProductNo(productNo);
    			productPromotionDO.setProductName(productDO.getProductName());
    			productPromotionDO.setStatus("0");
    			productPromotionDO.setType(typePro);
    			productPromotionDO.setPrice(new Money(price));
    			productPromotionDO.setOldPrice(new Money(oldPrice));
    			productPromotionDO.setSoleCount(productDO.getSoleCount());
    			productPromotionDO.setProductUrl(productDO.getProductUrl());
    			productPromotionDO.setNeedPeople(needPeople);
    			
    			productPromotionDO.setUserName(userName);
    			productPromotionDO.setCell(cell);
    			productPromotionDO.setIndustry(industry);
    			productPromotionDO.setPostWay(postWay);
    			productPromotionDO.setMessages(messages);
    			
    			int proRersult = productPromotionDAO.addProPromotion(productPromotionDO);
    			if(proRersult >= 1){
    				result.setBizSucc(true);
    				return result;
    			}
    		}
    	}else{
    		//有   update
    		int proRersult = productPromotionDAO.changeProPromotion("0", typePro, new Money(price), new Money(oldPrice), needPeople, userName, cell, industry, postWay, messages, productNo, shopId);
    		if(proRersult >= 1){
				result.setBizSucc(true);
				return result;
			}
    	}
    	
        return result;
    }
	
	@RequestMapping("productPromotionChech.html")
	public String productPromo(HttpServletRequest request, ModelMap modelMap) {
		
		String shopId = request.getParameter("shopId");
    	String productNo = request.getParameter("productNo");
		
		int totalItems = (int) productPromotionDAO.selectAllProPromotionCount(shopId, productNo);
		Map<String, Integer> pageData = getPageData(request);
		modelMap.put("totalPages",
				calculatePage(totalItems, pageData.get(PAGE_SIZE_STR) / 2));
		modelMap.put(CURRENTPAGE, pageData.get(CURRENTPAGE));
		modelMap.put("totalItems", totalItems);

		List<ProductPromotionDO> selectAllProPromotion = productPromotionDAO.selectAllProPromotion(shopId, productNo, pageData.get(OFFSET) / 2, pageData.get(PAGE_SIZE_STR) / 2);
		List<ProductPromotionResult> result = new ArrayList<ProductPromotionResult>();
		for (ProductPromotionDO productPromotionDO : selectAllProPromotion) {
			ProductPromotionResult productPromotionResult = new ProductPromotionResult();
			productPromotionResult.setProductPromotionDO(productPromotionDO);
			ShopDO shopDO = shopDAO.selectShopInfoByUserId(productPromotionDO.getUserId());
			if(null != shopDO){
				productPromotionResult.setShopDO(shopDO);
			}
			result.add(productPromotionResult);
		}
		modelMap.put("result", result);
		modelMap.put("promotionList", selectAllProPromotion);
		
		return "mng/productPromotionChech";
	}
	
	/**
	 * 通过或拒绝促销商品审核
	 * @param request
	 * @param map
	 * @return
	 */
	@RequestMapping("passOrReturnProductPromotion.do")
    @ResponseBody
    public Object passOrReturnProductPromotion(HttpServletRequest request, ModelMap map) {
    	JsonResult result = new JsonResult(true);
    	
    	String shopId = request.getParameter("shopId");
    	String productNo = request.getParameter("productNo");
    	
    	//判断是通过还是拒绝（pass 1   return 0）
    	String flag = request.getParameter("flag");
    	
    	int resultPromo = 0;
    	int resultUpdate = 0;
    	//通过审核
    	if(StringUtils.equals(flag, "1")){
    		//查询该信息状态
    		ProductPromotionDO promotionDO = productPromotionDAO.selectProPromotionByShopIdAndProductNo(shopId, productNo);
    		if(null != promotionDO){
    			String type = promotionDO.getType();
    			//判断申请活动类型
    	    	if(StringUtils.equals(type, "1")){
    	    		//1团购 （修改该商品的NEED_PEOPLE PRICE RECOMMEND_FLG）
    	    		resultUpdate = productDao.updateProPromotionTeamGo(promotionDO.getPrice(), promotionDO.getNeedPeople(), "4", shopId, productNo);
    	    		resultPromo = productPromotionDAO.passOrReturnProPromotion("2", productNo, shopId);
    	    		if(resultUpdate <= 0 || resultPromo <= 0){
    	    			result.setBizSucc(false);
    	    			result.setErrMsg("同意失败，请刷新页面重试");
    	    		}
    	    	}else if(StringUtils.equals(type, "2")){
    	    		//2限时秒杀（修改该商品的 PRICE RECOMMEND_FLG）
    	    		resultUpdate = productDao.updateProPromotionFirstTime(promotionDO.getPrice(), "3", shopId, productNo);
    	    		resultPromo = productPromotionDAO.passOrReturnProPromotion("1", productNo, shopId);
    	    		if(resultUpdate <= 0 || resultPromo <= 0){
    	    			result.setBizSucc(false);
    	    			result.setErrMsg("同意失败，请刷新页面重试");
    	    		}
    	    	}else if(StringUtils.equals(type, "3")){
    	    		//3积分商城
    	    	}else if(StringUtils.equals(type, "4")){
    	    		//4赞助
    	    	}else if(StringUtils.equals(type, "5")){
    	    		//5其他
    	    		resultPromo = productPromotionDAO.passOrReturnProPromotion("1", productNo, shopId);
    	    		if(resultPromo <= 0){
    	    			result.setBizSucc(false);
    	    			result.setErrMsg("同意失败，请刷新页面重试");
    	    		}
    	    	}else{
    	    		result.setBizSucc(false);
        			result.setErrMsg("商品申请活动不明，请通知该商户进行处理");
    	    	}
    		}
    	}
    	//拒绝审核
    	if(StringUtils.equals(flag, "0")){
    		//仅改变审核状态
    		resultPromo = productPromotionDAO.passOrReturnProPromotion("2", productNo, shopId);
    		if(resultPromo <= 0){
    			result.setBizSucc(false);
    			result.setErrMsg("拒绝失败，请刷新页面重试");
    		}
    	}
    	
        return result;
    }
	
	
	    //进入收入信息查询页面
		@RequestMapping("/productAllSell.html")
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
			String productNo = request.getParameter("productNo");
			String praflag = request.getParameter("praflag");
			
			String check = request.getParameter("check");
			
			ProductDO productDO = productDao.selectProductByProductNo(productNo, shopId);
			String startTime = request.getParameter("startTime");
			String endTime = request.getParameter("endTime");
			
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
				//查询时间段内的所有订单  selectMounthProductOrder   selectWeekProductOrder   selectProductIncome
				List<OrderDO> allIncome = new ArrayList<OrderDO>();
				if(StringUtils.equals(flag, "1") && StringUtils.equals(check, "1")){
					allIncome = orderDao.selectMounthProductOrder(productNo, shopId, praflag,"1");//月订单  微信支付
				}else if(StringUtils.equals(flag, "1") && StringUtils.equals(check, "2")){
					allIncome = orderDao.selectWeekProductOrder(productNo, shopId, praflag,"1");//周订单  微信支付
				}else if(StringUtils.equals(flag, "0") && StringUtils.equals(check, "1")){
					allIncome = orderDao.selectMounthProductOrder(productNo, shopId, praflag,null);//月订单 所有支付
				}else if(StringUtils.equals(flag, "0") && StringUtils.equals(check, "2")){
					allIncome = orderDao.selectWeekProductOrder(productNo, shopId, praflag,null);//周订单 所有支付
				}else if(StringUtils.equals(flag, "2") && StringUtils.equals(check, "1")){
					allIncome = orderDao.selectMounthProductOrder(productNo, shopId, praflag,"4");//月订单 分享币
				}else if(StringUtils.equals(flag, "2") && StringUtils.equals(check, "1")){
					allIncome = orderDao.selectWeekProductOrder(productNo, shopId, praflag,"4");//周订单 分享币
				}else if(StringUtils.equals(flag, "0") && StringUtils.isBlank(check)){
					allIncome = orderDao.selectProductIncome(productNo, shopId, praflag,null,startDate, endDate);
				}else if(StringUtils.equals(flag, "1") && StringUtils.isBlank(check)){
					allIncome = orderDao.selectProductIncome(productNo, shopId, praflag,"1",startDate, endDate);
				}else if(StringUtils.equals(flag, "2") && StringUtils.isBlank(check)){
					allIncome = orderDao.selectProductIncome(productNo, shopId, praflag,"4",startDate, endDate);
				}
//				if(StringUtils.equals(check, "1")){
//					allIncome = orderDao.selectMounthProductOrder(productNo, shopId, praflag,"");//月订单
//				}else if(StringUtils.equals(check, "2")){
//					allIncome = orderDao.selectWeekProductOrder(productNo, shopId, praflag,"");//周订单
//				}else{
//					allIncome = orderDao.selectProductIncome(productNo, shopId, praflag,"",startDate, endDate);
//				}
				Money orderPrice = new Money();
				for (OrderDO orderDO : allIncome) {
					orderPrice = orderPrice.add(orderDO.getOrderPrice());
				}
				
			modelMap.put("startDate", startDate);
			modelMap.put("endDate", endDateStr);
			modelMap.put("shopId", shopId);
			modelMap.put("productNo", productNo);
			modelMap.put("praflag", praflag);
			modelMap.put("productDO", productDO);
			modelMap.put("orderPrice", orderPrice);
			modelMap.put("checkTime", check);
			modelMap.put("flag", flag);
			}catch (Exception e){
				e.printStackTrace();
			}
			return "mng/productAllSell";
		}
	
	
}
