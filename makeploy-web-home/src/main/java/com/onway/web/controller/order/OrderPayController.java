package com.onway.web.controller.order;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.onway.common.lang.Money;
import com.onway.common.lang.StringUtils;
import com.onway.makeploy.common.dal.dataobject.AddressDO;
import com.onway.makeploy.common.dal.dataobject.OrderDO;
import com.onway.makeploy.common.dal.dataobject.ProductDO;
import com.onway.makeploy.common.dal.dataobject.ProductShow;
import com.onway.makeploy.common.dal.dataobject.ShopDO;
import com.onway.makeploy.common.service.exception.ErrorException;
import com.onway.platform.common.service.util.AssertUtil;
import com.onway.web.controller.AbstractController;
import com.onway.web.controller.result.JsonResult;
import com.onway.web.controller.template.ControllerCallBack;

@Controller
public class OrderPayController extends AbstractController{

	/**
	 * 立即购买
	 * 
	 * @param request
	 * @param userId     用户Id
	 * @return
	 */
	@RequestMapping("/orderPay.html")
	public Object orderPay(final HttpServletRequest request,final ModelMap modelMap) {
		// String userId = (String) request.getSession().getAttribute("userId");
	    final String userId = "13252";
		if (StringUtils.isEmpty(userId)) {
			throw new ErrorException("用户未登录");
	    }
		final String shopId=request.getParameter("shopId");
		final String productNo=request.getParameter("productNo");
		final String payCount=request.getParameter("payCount");
		modelMap.put("payCount", payCount);
		
		//查询用户的默认地址
		AddressDO defaultAddress = addressDao.selectDefaultAddress(userId, "0");
		modelMap.put("defaultAddress", defaultAddress);
		
		//商品信息
		ProductDO productDo=productDao.selectShopCartProduct(shopId, productNo);
		modelMap.put("productDo", productDo);
		
		//计算单件商品合计
		Money productMoney =productDo.getPrice();
		productMoney=productMoney.multiply(Integer.valueOf(payCount));
		modelMap.put("productMoney", productMoney);
		
		//计算订单总价
		
		ShopDO shopDo=shopDAO.selectShopByShopId(shopId);
		modelMap.put("shopDo", shopDo);
		
		return "html/orderPay";
	}
	
	/**
	 * 提交订单
	 * 
	 * @param request
	 * @param userId     用户Id
	 * @return
	 */
	@RequestMapping("/payorder.html")
	public Object payOrder(final HttpServletRequest request,final ModelMap modelMap) {
		// String userId = (String) request.getSession().getAttribute("userId");
	    final String userId = "13252";
		if (StringUtils.isEmpty(userId)) {
			throw new ErrorException("用户未登录");
	    }
		final String orderId=request.getParameter("orderId");
		
		//根据orderId查询订单信息，delflg=0 未删除
		ProductShow order = orderDao.selectOrderAllByOrderId(orderId, "0");
		Money money=new Money(order.getOrderPrice());
		money=money.divide(100);
		modelMap.put("money", money);
		modelMap.put("order", order);
		return "html/payorder";
	}
	
	/**
	 * 生成订单
	 * @param request
	 * @return
	 */
	@RequestMapping("/creatOrder.do")
	@ResponseBody
	public Object creatOrder(final HttpServletRequest request) {
		// String userId = (String) request.getSession().getAttribute("userId");
	    final String userId = "13252";
	    final String productNo = request.getParameter("productNo");
	    final String productCount = request.getParameter("productCount");
	    final String orderPrice = request.getParameter("orderPrice");
	    final String luggage=request.getParameter("luggage");
	    final String provience=request.getParameter("provience");
	    final String city=request.getParameter("city");
	    final String district=request.getParameter("district");
	    
	    final JsonResult result=new JsonResult(true);
	    controllerTemplate.execute(result, new ControllerCallBack() {
			
			@Override
			public void executeService() {
				OrderDO orderDo=new OrderDO();
				orderDo.setUserId(userId);
				
				//订单编号    系统生成订单编号
				String orderId="4";
				orderDo.setOrderId(orderId);
				
				orderDo.setProductNo(productNo);
				orderDo.setProductCount(Integer.valueOf(productCount));
				orderDo.setOrderPrice(new Money(orderPrice));
				orderDo.setLuggage(new Money(luggage));
				orderDo.setProvience(provience);
				orderDo.setCity(city);
				orderDo.setDistrict(district);
				
				int orderResult = orderDao.creatPartnerOrder(orderDo);
				if(orderResult <= 0){
					result.setBizSucc(false);
					result.setErrMsg("订单生成失败");
				}
				result.setInformation(orderId);
			}
			
			@Override
			public void check() {
				AssertUtil.notBlank(userId, "参数为空：userId");
				AssertUtil.notBlank(productNo, "参数为空：productNo");
				AssertUtil.notBlank(productCount, "参数为空：productCount");
				AssertUtil.notBlank(orderPrice, "参数为空：orderPrice");
				AssertUtil.notBlank(luggage, "参数为空：luggage");
				AssertUtil.notBlank(provience, "参数为空：provience");
				AssertUtil.notBlank(city, "参数为空：city");
				AssertUtil.notBlank(district, "参数为空：district");
			}
		});
		return result;	
	}
}
