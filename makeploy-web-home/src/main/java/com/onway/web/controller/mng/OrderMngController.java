package com.onway.web.controller.mng;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.onway.common.lang.DateUtils;
import com.onway.common.lang.Money;
import com.onway.common.lang.StringUtils;
import com.onway.makeploy.common.dal.dataobject.OrderDO;
import com.onway.makeploy.common.dal.dataobject.OrderInfo;
import com.onway.makeploy.common.dal.dataobject.PartnerRankDO;
import com.onway.makeploy.common.dal.dataobject.ProductDO;
import com.onway.makeploy.common.dal.dataobject.ShopDO;
import com.onway.web.controller.AbstractController;
import com.onway.web.controller.result.JsonResult;

@Controller
public class OrderMngController extends AbstractController {
	@RequestMapping("/orderMng.html")
	public String loginMng(HttpServletRequest request, ModelMap modelMap) {
		//selectMyTeam(request, modelMap);
		String userId = (String)request.getSession().getAttribute("userId");
    	boolean checkRoleBoss = checkRoleBoss(userId);
    	if(checkRoleBoss == false){
    		return "mng/loginMng";
    	}
    	//判断是否为运营者
        int checkOperative = checkOperative(userId);
        modelMap.put("checkOperative", checkOperative);
		return "mng/orderMng";
	}
    //搜索
	@RequestMapping("/selectOrderMng.do")
	public ModelAndView selectMyTeam(HttpServletRequest request,
			ModelMap modelmap) {
		
		String userIds = (String)request.getSession().getAttribute("userId");
    	boolean checkRoleBoss = checkRoleBoss(userIds);
    	if(checkRoleBoss == false){
    		return new ModelAndView("mng/loginMng");
    	}
    	//判断是否为运营者
        int checkOperative = checkOperative(userIds);
        modelmap.put("checkOperative", checkOperative);

		String userId = request.getParameter("USER_ID");
		// String userId = "13253";
		String payStatus = request.getParameter("PAY_STATUS");
		// String payStatus = "3";	
		String orderId = request.getParameter("ORDER_ID");
		String productNo = request.getParameter("PRODUCT_NO");
		String sendGoods = request.getParameter("SEND_DOODS");
		// String sendGoods = "4";
		String delflag = request.getParameter("DELFLAG");
		// String delflag = "0";
		String orderStatus = request.getParameter("ORDER_STATUS");
		Map<String, Integer> pageData = getPageData(request);

		try {
			    List<OrderResult> results  = new ArrayList<OrderResult>();
				List<OrderDO> list = orderDao.selectOrdersByStatusAndUserId(
						userId, orderId, productNo, payStatus, sendGoods, delflag,
						pageData.get(OFFSET), pageData.get(PAGE_SIZE_STR));
				int orderCount = (int) orderDao
						.selectOrdersCountByStatusAndUserId(userId, orderId, productNo, payStatus,
								sendGoods, delflag);
				for (OrderDO orderDO : list) {
					OrderResult orderResult = new OrderResult();
					orderResult.setOrderDO(orderDO);
					
					String shopId = orderDO.getShopId();
					String productno = orderDO.getProductNo();
					
					ShopDO shopDO = shopDAO.selectShopByShopId(shopId);
					orderResult.setShopDO(shopDO);
					
					ProductDO productDO = productDao.selectProductByProductNo(productno, shopId);
					orderResult.setProductDO(productDO);
					
					results.add(orderResult);
				}
				
				modelmap.put("results", results);
				
				modelmap.put("userId", userId);
				modelmap.put("orderId", orderId);
				modelmap.put("productNo", productNo);
				modelmap.put("payStatus", payStatus);
				modelmap.put("sendGoods", sendGoods);
				modelmap.put("orderStatus", orderStatus);
				modelmap.put("delflag", delflag);
				modelmap.put("totalPages",
						calculatePage(orderCount, pageData.get(PAGE_SIZE_STR))); // 传输总页数
				modelmap.put(CURRENTPAGE, pageData.get(CURRENTPAGE)); // 传输当前页数
				modelmap.put("orderList", list); // 传输数据库返回数据
				modelmap.put("totalItems", orderCount);
				request.setAttribute("page", modelmap);
			
		} catch (Exception e) {
			e.printStackTrace();
		}

		return new ModelAndView("mng/orderMng");

	}
	//取消订单
	@RequestMapping("/cancelOrder.do")
	@ResponseBody
	public Object cancelOrder(HttpServletRequest request, ModelMap modelMap) {
		JsonResult  jsonResult = new JsonResult(true);
		String orderId = request.getParameter("orderId");
		String change ="2";  //2为取消
		try {
			int a = orderDao.updateOrderStatusByOrderId(change, orderId);
			if(a>0){
				jsonResult.setBizSucc(true);
			}else{
				jsonResult.setBizSucc(false);
			}
		} catch (Exception e) {
			e.printStackTrace();;
		}
		jsonResult.setBizSucc(true);
		return jsonResult;
	}
	
	//取消订单
	@RequestMapping("/deleteOrder.do")
	@ResponseBody
	public Object deleteOrder(HttpServletRequest request, ModelMap modelMap){ 
		JsonResult  jsonResult = new JsonResult(true);
		String orderId = request.getParameter("orderId");
		String change ="1";  //1为已删除
		try {
			int b = orderDao.updateDelFlagByOrderId(change, orderId);
			if(b>0){
				jsonResult.setBizSucc(true);
			}else{
				jsonResult.setBizSucc(false);
			}
		} catch (Exception e) {
			e.printStackTrace();;
		}
		jsonResult.setBizSucc(true);
		return jsonResult;
	}
	//确认订单
	@RequestMapping("/confirmOrder.do")
	@ResponseBody
	public Object confirmOrder(HttpServletRequest request, ModelMap modelMap){ 
		JsonResult  jsonResult = new JsonResult(true);
		String orderId = request.getParameter("orderId");
		String changeOrderStatus ="0";  //0为交易成功
		String changeSendGoods="4";//4为完成
		try {
			int b = orderDao.updateOrderStatusAndOrderSendGoodsByOrderId(changeOrderStatus, changeSendGoods, orderId);
			if(b>0){
				jsonResult.setBizSucc(true);
			}else{
				jsonResult.setBizSucc(false);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		jsonResult.setBizSucc(true);
		return jsonResult;
	}
	/**
	 * 订单详细信息
	 * @param request
	 * @param modelMap
	 * @return
	 */
	@RequestMapping("/orderDetail.do")
	@ResponseBody
	public Object orderDetail(HttpServletRequest request, ModelMap modelMap){ 
		String orderId = getParameterCheck(request, "orderId");
		List<OrderInfo> orderList = orderDao.selectByShopIDSearchBox(null,
				orderId, null, null, null, null, null, null, 0, 1);
		if (orderList.size() > 0) {
			OrderInfo orderInfo = orderList.get(0);
			if (orderInfo.getPayStatus().equals("1")) {
				orderInfo.setPayStatus("未支付");
			} else if (orderInfo.getPayStatus().equals("2")) {
				orderInfo.setPayStatus("支付中");
			} else if (orderInfo.getPayStatus().equals("3")
					&& orderInfo.getSendGoods().equals("1")) {
				orderInfo.setPayStatus("待发货");
			} else if (orderInfo.getPayStatus().equals("3")
					&& orderInfo.getSendGoods().equals("2")) {
				orderInfo.setPayStatus("待确认收货");
			} else if (orderInfo.getPayStatus().equals("3")
					&& orderInfo.getSendGoods().equals("3")) {
				orderInfo.setPayStatus("退货/退款");
			} else if (orderInfo.getPayStatus().equals("3")
					&& orderInfo.getSendGoods().equals("4")
					&& orderInfo.getOrderStatus().equals("0")) {
				orderInfo.setPayStatus("已完成");

				orderInfo.setStringgmtCreat(DateUtils.format(
						orderInfo.getGmtCreat(), "yyyy-MM-dd"));
				orderInfo.setShopId(orderInfo.getOrderPrice().toString());// 存储订单总价

			}
			return orderInfo;
		}else{
			return "0";
		}
	}
	

	/**
	 * 修改代发货收货信息
	 * @param request
	 * @param modelMap
	 * @return
	 */
	@RequestMapping("modifyOrder.html")
	public String modifyOrder(HttpServletRequest request,ModelMap modelMap){
		String orderId = request.getParameter("orderId");
		OrderDO orderDO = orderDao.selectOrdersByOrderId(orderId, "0");
		modelMap.put("orderId", orderId);
		modelMap.put("orderDO", orderDO);
		return "mng/modifyOrder";
	}
	
	/**
	 * 更改订单信息
	 * @param request
	 * @return
	 */
	@RequestMapping("/orderModifyByOrderId.do")
	@ResponseBody
	public Object orderModifyByOrderId(HttpServletRequest request){
		JsonResult result = new JsonResult(false, "", "");
		String orderId = request.getParameter("orderId").trim();
		String userName = request.getParameter("userName").trim();
		String cell = request.getParameter("cell").trim();
		String provience = request.getParameter("provience").trim();
		String city = request.getParameter("city").trim();
		String district = request.getParameter("district").trim();
		String addr = request.getParameter("addr").trim();
		String orderPrice = request.getParameter("orderPrice");
		String luggage = request.getParameter("luggage");
		Money price = new Money(orderPrice);
		Money lugg = new Money(luggage);
		try {
			int a = orderDao.updateInfoBySendsGoods(userName, cell, provience, city, district, addr, price, lugg, orderId);
			if(a > 0){
				result.setBizSucc(true);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}
	
	@RequestMapping("/orderMngCheckOut.html")
	public String orderMngCheckOut(HttpServletRequest request, ModelMap modelMap) {
		return "mng/orderMngCheckOut";
	}
	
	@RequestMapping("/selectOrderMngCheck.do")
	public ModelAndView selectOrderMngCheck(HttpServletRequest request,
			ModelMap modelmap) {

		String shopId =(String) request.getSession().getAttribute("shopId");
		
		String userId = request.getParameter("USER_ID");
		// String userId = "13253";
		String payStatus = request.getParameter("PAY_STATUS");
		// String payStatus = "3";	
		String orderId = request.getParameter("ORDER_ID");
		String productNo = request.getParameter("PRODUCT_NO");
		String sendGoods = request.getParameter("SEND_DOODS");
		// String sendGoods = "4";
		String delflag = request.getParameter("DELFLAG");
		// String delflag = "0";
		String orderStatus = request.getParameter("ORDER_STATUS");
		Map<String, Integer> pageData = getPageData(request);

		try {
			    List<OrderResult> results  = new ArrayList<OrderResult>();
				List<OrderDO> list = orderDao.selectOrdersByStatusAndUserIdAndShopId(
						userId, orderId, productNo, payStatus, sendGoods, delflag,
						pageData.get(OFFSET), pageData.get(PAGE_SIZE_STR),shopId);
				for (OrderDO orderDO : list) {
					OrderResult orderResult = new OrderResult();
					orderResult.setOrderDO(orderDO);
					
					String productno = orderDO.getProductNo();
					
					ShopDO shopDO = shopDAO.selectShopByShopId(shopId);
					orderResult.setShopDO(shopDO);
					
					ProductDO productDO = productDao.selectProductByProductNo(productno, shopId);
					orderResult.setProductDO(productDO);
					
					results.add(orderResult);
				}
				
				modelmap.put("results", results);
				
				int orderCount = (int) orderDao
						.selectOrdersCountByStatusAndUserIdAndShopId(userId, orderId, productNo, payStatus,
								sendGoods, delflag,shopId);
				modelmap.put("shopId", shopId);
				modelmap.put("userId", userId);
				modelmap.put("orderId", orderId);
				modelmap.put("productNo", productNo);
				modelmap.put("payStatus", payStatus);
				modelmap.put("sendGoods", sendGoods);
				modelmap.put("orderStatus", orderStatus);
				modelmap.put("delflag", delflag);
				modelmap.put("totalPages",
						calculatePage(orderCount, pageData.get(PAGE_SIZE_STR))); // 传输总页数
				modelmap.put(CURRENTPAGE, pageData.get(CURRENTPAGE)); // 传输当前页数
				modelmap.put("orderList", list); // 传输数据库返回数据
				modelmap.put("totalItems", orderCount);
				request.setAttribute("page", modelmap);
			
		} catch (Exception e) {
			e.printStackTrace();
		}

		return new ModelAndView("mng/orderMngCheckOut");
	}
	
	/**
	 * 玫发现订单
	 * @param request
	 * @param modelMap
	 * @return
	 */
	@RequestMapping("/jzShopOrder.html")
	public String jzShopOrder(HttpServletRequest request, ModelMap modelMap) {
		String userIds = (String) request.getSession().getAttribute("userId");
		boolean checkRoleBoss = checkRoleBoss(userIds);
		if (checkRoleBoss == false) {
			return "mng/loginMng";
		}
		//判断是否为运营者
        int checkOperative = checkOperative(userIds);
        modelMap.put("checkOperative", checkOperative);
        
		String userId = request.getParameter("USER_ID");
		String shopId = "3320170116000180";
		String payStatus = request.getParameter("PAY_STATUS");
		String orderId = request.getParameter("ORDER_ID");
		String productNo = request.getParameter("PRODUCT_NO");
		String sendGoods = request.getParameter("SEND_DOODS");
		String delflag = request.getParameter("DELFLAG");
		Map<String, Integer> pageData = getPageData(request);
		
		List<OrderResult> resultList  = new ArrayList<OrderResult>();
		
		List<OrderResult> results  = new ArrayList<OrderResult>();
		List<OrderDO> list = orderDao.selectOrdersByStatusAndUserIdAndShopId(
				userId, orderId, productNo, payStatus, sendGoods, delflag,
				pageData.get(OFFSET), pageData.get(PAGE_SIZE_STR),shopId);
		int orderCount = (int) orderDao
				.selectOrdersCountByStatusAndUserIdAndShopId(userId, orderId, productNo, payStatus,
						sendGoods, delflag,shopId);
		String orderNo = null ;
		
		for (OrderDO orderDO : list) {
			String orderNo2 = orderDO.getOrderNo();
			
			OrderResult orderResult = new OrderResult();
			orderResult.setOrderDO(orderDO);
			
			String productno = orderDO.getProductNo();
			
			ShopDO shopDO = shopDAO.selectShopByShopId(shopId);
			orderResult.setShopDO(shopDO);
			
			ProductDO productDO = productDao.selectProductByProductNo(productno, shopId);
			orderResult.setProductDO(productDO);
			
            if(StringUtils.equals(orderNo, orderNo2)){
            	results.add(orderResult);
			}else{
				results = new ArrayList<OrderResult>();
				results.add(orderResult);
			}
			
			OrderResult orderResultList = new OrderResult();
			orderResultList.setResultList(results);
			if(!StringUtils.equals(orderNo, orderNo2)){
				resultList.add(orderResultList);
			}
			
			orderNo = orderNo2;
		}
		
		modelMap.put("results", results);
		modelMap.put("resultList", resultList);
		modelMap.put("userId", userId);
		modelMap.put("orderId", orderId);
		modelMap.put("productNo", productNo);
		modelMap.put("payStatus", payStatus);
		modelMap.put("sendGoods", sendGoods);
		modelMap.put("delflag", delflag);
		modelMap.put("totalPages",
				calculatePage(orderCount, pageData.get(PAGE_SIZE_STR))); // 传输总页数
		modelMap.put(CURRENTPAGE, pageData.get(CURRENTPAGE)); // 传输当前页数
		modelMap.put("orderList", list); // 传输数据库返回数据
		modelMap.put("totalItems", orderCount);
		request.setAttribute("page", modelMap);
		
		return "mng/jzShopOrder";
	}
	
	/**
	 * 积分订单
	 * @param request
	 * @param modelMap
	 * @return
	 */
	@RequestMapping("/jfShopOrder.html")
	public String jfShopOrder(HttpServletRequest request, ModelMap modelMap) {
		String userIds = (String) request.getSession().getAttribute("userId");
		boolean checkRoleBoss = checkRoleBoss(userIds);
		if (checkRoleBoss == false) {
			return "mng/loginMng";
		}
		//判断是否为运营者
        int checkOperative = checkOperative(userIds);
        modelMap.put("checkOperative", checkOperative);
		String userId = request.getParameter("USER_ID");
		String shopId = "3320170215001717";
		String payStatus = request.getParameter("PAY_STATUS");
		String orderId = request.getParameter("ORDER_ID");
		String productNo = request.getParameter("PRODUCT_NO");
		String sendGoods = request.getParameter("SEND_DOODS");
		String delflag = request.getParameter("DELFLAG");
		Map<String, Integer> pageData = getPageData(request);
		
		List<OrderResult> results  = new ArrayList<OrderResult>();
		List<OrderDO> list = orderDao.selectOrdersByStatusAndUserIdAndShopId(
				userId, orderId, productNo, payStatus, sendGoods, delflag,
				pageData.get(OFFSET), pageData.get(PAGE_SIZE_STR),shopId);
		int orderCount = (int) orderDao
				.selectOrdersCountByStatusAndUserIdAndShopId(userId, orderId, productNo, payStatus,
						sendGoods, delflag,shopId);
		for (OrderDO orderDO : list) {
			OrderResult orderResult = new OrderResult();
			orderResult.setOrderDO(orderDO);
			
			String productno = orderDO.getProductNo();
			
			ShopDO shopDO = shopDAO.selectShopByShopId(shopId);
			orderResult.setShopDO(shopDO);
			
			ProductDO productDO = productDao.selectProductByProductNo(productno, shopId);
			orderResult.setProductDO(productDO);
			
			results.add(orderResult);
		}
		
		modelMap.put("results", results);
		
		modelMap.put("userId", userId);
		modelMap.put("orderId", orderId);
		modelMap.put("productNo", productNo);
		modelMap.put("payStatus", payStatus);
		modelMap.put("sendGoods", sendGoods);
		modelMap.put("delflag", delflag);
		modelMap.put("totalPages",
				calculatePage(orderCount, pageData.get(PAGE_SIZE_STR))); // 传输总页数
		modelMap.put(CURRENTPAGE, pageData.get(CURRENTPAGE)); // 传输当前页数
		modelMap.put("orderList", list); // 传输数据库返回数据
		modelMap.put("totalItems", orderCount);
		request.setAttribute("page", modelMap);
		
		return "mng/jfShopOrder";
	}
	
	/**
	 * 团购订单
	 * @param request
	 * @param modelMap
	 * @return
	 */
	@RequestMapping("/teamGoOrder.html")
	public String teamGoOrder(HttpServletRequest request, ModelMap modelMap) {
		String userIds = (String)request.getSession().getAttribute("userId");
    	boolean checkRoleBoss = checkRoleBoss(userIds);
    	if(checkRoleBoss == false){
    		return "mng/loginMng";
    	}
    	//判断是否为运营者
        int checkOperative = checkOperative(userIds);
        modelMap.put("checkOperative", checkOperative);
    	
    	String userId = request.getParameter("USER_ID");
		String payStatus = request.getParameter("PAY_STATUS");
		String orderId = request.getParameter("ORDER_ID");
		String productNo = request.getParameter("PRODUCT_NO");
		String sendGoods = request.getParameter("SEND_DOODS");
		String delflag = request.getParameter("DELFLAG");
		Map<String, Integer> pageData = getPageData(request);
		
		List<OrderResult> results  = new ArrayList<OrderResult>();
		List<OrderDO> list = orderDao.selectTeamGoOrdersByStatusAndUserIdAndShopId(
				userId, orderId, productNo, payStatus, sendGoods, delflag,
				pageData.get(OFFSET), pageData.get(PAGE_SIZE_STR),null);
		int orderCount = (int) orderDao
				.selectTeamGoOrdersCountByStatusAndUserIdAndShopId(userId, orderId, productNo, payStatus,
						sendGoods, delflag,null);
		for (OrderDO orderDO : list) {
			OrderResult orderResult = new OrderResult();
			orderResult.setOrderDO(orderDO);
			
			String productno = orderDO.getProductNo();
			String shopId = orderDO.getShopId();
			ShopDO shopDO = shopDAO.selectShopByShopId(shopId);
			orderResult.setShopDO(shopDO);
			
			ProductDO productDO = productDao.selectProductByProductNo(productno, shopId);
			orderResult.setProductDO(productDO);
			
			results.add(orderResult);
		}
		
		modelMap.put("results", results);
		
		
		
		modelMap.put("userId", userId);
		modelMap.put("orderId", orderId);
		modelMap.put("productNo", productNo);
		modelMap.put("payStatus", payStatus);
		modelMap.put("sendGoods", sendGoods);
		modelMap.put("delflag", delflag);
		modelMap.put("totalPages",
				calculatePage(orderCount, pageData.get(PAGE_SIZE_STR))); // 传输总页数
		modelMap.put(CURRENTPAGE, pageData.get(CURRENTPAGE)); // 传输当前页数
		modelMap.put("orderList", list); // 传输数据库返回数据
		modelMap.put("totalItems", orderCount);
		request.setAttribute("page", modelMap);
    	
		return "mng/teamGoOrder";
	}
	
	/**
	 * 排单订单
	 * @param request
	 * @param modelMap
	 * @return
	 */
	@RequestMapping("/partnerRankOrder.html")
	public String partnerRankOrder(HttpServletRequest request, ModelMap modelMap) {
		String userIds = (String)request.getSession().getAttribute("userId");
    	boolean checkRoleBoss = checkRoleBoss(userIds);
    	if(checkRoleBoss == false){
    		return "mng/loginMng";
    	}
    	//判断是否为运营者
        int checkOperative = checkOperative(userIds);
        modelMap.put("checkOperative", checkOperative);
    	
    	String userId = request.getParameter("USER_ID");
		String orderId = request.getParameter("ORDER_ID");
		
		Map<String, Integer> pageData = getPageData(request);
		
		List<OrderResult> results  = new ArrayList<OrderResult>();
		
		List<OrderDO> list = orderDao.selectPartnerRankOrder(
				userId, orderId,pageData.get(OFFSET), pageData.get(PAGE_SIZE_STR));
		int orderCount = (int) orderDao.selectPartnerRankOrderCount(userId,orderId);
		
		for (OrderDO orderDO : list) {
			OrderResult orderResult = new OrderResult();
			orderResult.setOrderDO(orderDO);
			
			String productno = orderDO.getProductNo();
			String shopId = orderDO.getShopId();
			ShopDO shopDO = shopDAO.selectShopByShopId(shopId);
			orderResult.setShopDO(shopDO);
			
			ProductDO productDO = productDao.selectProductByProductNo(productno, shopId);
			orderResult.setProductDO(productDO);
			
			//判断是否出局订单
		    PartnerRankDO partnerRankDO = partnerRankDAO.selectByOrderId(orderDO.getOrderId());
		    if(null == partnerRankDO){
		    	orderResult.setMemo("已出局订单");
		    }else{
		    	orderResult.setMemo("正在排单");
		    }
			results.add(orderResult);
		}
		modelMap.put("results", results);
		modelMap.put("userId", userId);
		modelMap.put("orderId", orderId);
		modelMap.put("totalPages",
				calculatePage(orderCount, pageData.get(PAGE_SIZE_STR))); // 传输总页数
		modelMap.put(CURRENTPAGE, pageData.get(CURRENTPAGE)); // 传输当前页数
		modelMap.put("orderList", list); // 传输数据库返回数据
		modelMap.put("totalItems", orderCount);
		request.setAttribute("page", modelMap);
    	
		return "mng/partnerRankOrder";
	}
}
