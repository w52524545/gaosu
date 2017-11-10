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
	 * ��������
	 * 
	 * @param request
	 * @param userId     �û�Id
	 * @return
	 */
	@RequestMapping("/orderPay.html")
	public Object orderPay(final HttpServletRequest request,final ModelMap modelMap) {
		// String userId = (String) request.getSession().getAttribute("userId");
	    final String userId = "13252";
		if (StringUtils.isEmpty(userId)) {
			throw new ErrorException("�û�δ��¼");
	    }
		final String shopId=request.getParameter("shopId");
		final String productNo=request.getParameter("productNo");
		final String payCount=request.getParameter("payCount");
		modelMap.put("payCount", payCount);
		
		//��ѯ�û���Ĭ�ϵ�ַ
		AddressDO defaultAddress = addressDao.selectDefaultAddress(userId, "0");
		modelMap.put("defaultAddress", defaultAddress);
		
		//��Ʒ��Ϣ
		ProductDO productDo=productDao.selectShopCartProduct(shopId, productNo);
		modelMap.put("productDo", productDo);
		
		//���㵥����Ʒ�ϼ�
		Money productMoney =productDo.getPrice();
		productMoney=productMoney.multiply(Integer.valueOf(payCount));
		modelMap.put("productMoney", productMoney);
		
		//���㶩���ܼ�
		
		ShopDO shopDo=shopDAO.selectShopByShopId(shopId);
		modelMap.put("shopDo", shopDo);
		
		return "html/orderPay";
	}
	
	/**
	 * �ύ����
	 * 
	 * @param request
	 * @param userId     �û�Id
	 * @return
	 */
	@RequestMapping("/payorder.html")
	public Object payOrder(final HttpServletRequest request,final ModelMap modelMap) {
		// String userId = (String) request.getSession().getAttribute("userId");
	    final String userId = "13252";
		if (StringUtils.isEmpty(userId)) {
			throw new ErrorException("�û�δ��¼");
	    }
		final String orderId=request.getParameter("orderId");
		
		//����orderId��ѯ������Ϣ��delflg=0 δɾ��
		ProductShow order = orderDao.selectOrderAllByOrderId(orderId, "0");
		Money money=new Money(order.getOrderPrice());
		money=money.divide(100);
		modelMap.put("money", money);
		modelMap.put("order", order);
		return "html/payorder";
	}
	
	/**
	 * ���ɶ���
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
				
				//�������    ϵͳ���ɶ������
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
					result.setErrMsg("��������ʧ��");
				}
				result.setInformation(orderId);
			}
			
			@Override
			public void check() {
				AssertUtil.notBlank(userId, "����Ϊ�գ�userId");
				AssertUtil.notBlank(productNo, "����Ϊ�գ�productNo");
				AssertUtil.notBlank(productCount, "����Ϊ�գ�productCount");
				AssertUtil.notBlank(orderPrice, "����Ϊ�գ�orderPrice");
				AssertUtil.notBlank(luggage, "����Ϊ�գ�luggage");
				AssertUtil.notBlank(provience, "����Ϊ�գ�provience");
				AssertUtil.notBlank(city, "����Ϊ�գ�city");
				AssertUtil.notBlank(district, "����Ϊ�գ�district");
			}
		});
		return result;	
	}
}
