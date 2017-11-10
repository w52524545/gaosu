package com.onway.web.controller.order;


import java.util.ArrayList;
import java.util.List;














import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.onway.common.lang.Money;
import com.onway.makeploy.common.dal.dataobject.AddressDO;
import com.onway.makeploy.common.dal.dataobject.Clothing;
import com.onway.makeploy.common.dal.dataobject.OrderDO;
import com.onway.makeploy.common.dal.dataobject.ProductCommentDO;
import com.onway.makeploy.common.dal.dataobject.ProductParameterDO;
import com.onway.makeploy.common.dal.dataobject.ProductShow;
import com.onway.makeploy.common.dal.dataobject.ShopCommentDO;
import com.onway.makeploy.common.dal.dataobject.ShopDO;
import com.onway.makeploy.common.dal.dataobject.ShopShow;
import com.onway.platform.common.service.util.AssertUtil;
import com.onway.web.controller.AbstractController;
import com.onway.web.controller.result.JsonResult;
import com.onway.web.controller.result.OrderResult;
import com.onway.web.controller.template.ControllerCallBack;

/**
 * �ҵĶ���
 * 
 * @author Administrator
 *
 */
@Controller
public class OrderController extends AbstractController {

	/**
	 * ��ѯ���п���ʾ����
	 * @param 
	 * @param modelmap
	 * @return
	 */
	@RequestMapping("orderAll.html")
	public String queryOrder(HttpServletRequest request,ModelMap modelmap){
		 String userId = ""; //request.getSession().getAttribute("userId");
		 String productNo = "";
		 String sendGoods = "";
		 String payStatus = "";
		 String praflag = "";
		 String delflag = "0";
		 String delflg = "0";
		 //String orderStatus = "";
		 Integer pageNum = adjustPageNo(request);
		 Integer pageSize = adjustPageSize(request);
		 Integer startRow = (pageNum - 1) * pageSize;
		 
		 List<Clothing> clothingList = new ArrayList<Clothing>();
		
		try {
			List<ProductShow> showList = orderDao.selectOrderByProductNO(userId, delflag, "", sendGoods, payStatus, startRow, pageSize);
			 
			for (ProductShow productShow : showList) {
				Money money1 = new Money(productShow.getPrice());
				money1 = money1.divide(100);   //��Ʒ����
				
				Money money2 = new Money(productShow.getOrderPrice());
				money2 = money2.divide(100);
				int proCount = productShow.getProductCount();
				money2 = money1.multiply(proCount);  //��Ʒ�ܼ�
				
				Money money3 = new Money(productShow.getLuggage());
				money3 = money3.divide(100);      //�˷�
				
				Money money4 = new Money();
				money4 = money2.add(money3);      //������
				
				productShow.setMoney1(money1);
				productShow.setMoney2(money2);
				productShow.setMoney3(money3);
				productShow.setMoney4(money4);
				
				productNo = productShow.getProductNo();
				List<ProductParameterDO> parameterList = productParameterDAO.selectProductParametersById(productNo,praflag);
				Clothing clothing = new Clothing();
					for(ProductParameterDO productParameterDO : parameterList){
						clothing.setProductNo(productParameterDO.getProductNo());
						if(productParameterDO.getFatherName().equals("��ɫ")){
							clothing.setColor(productParameterDO.getChildrenName());
						}
						if(productParameterDO.getFatherName().equals("�ߴ�")){
							clothing.setDimensions(productParameterDO.getChildrenName());
						}
					}
				clothingList.add(clothing);
			}
		
			int totalItem = (int) orderDao.selectorderCountByProductNO(userId, delflag, "", sendGoods, payStatus);
			int totalPages = (totalItem - 1) / pageSize + 1;
			
			List<ShopShow> shopShowList = productDao.selectShop(delflg);
			
			
			modelmap.addAttribute("clothingList", clothingList);
			modelmap.addAttribute("showList", showList);
			modelmap.addAttribute("shopShowList", shopShowList);
			modelmap.addAttribute("totalItem", totalItem);
			modelmap.addAttribute("totalPages", totalPages);
			modelmap.addAttribute("startRow", startRow);
			modelmap.addAttribute("nextPage", totalPages > pageNum ? true : false);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "html/orderAll";
	}


	

	
	/**
	 * ȡ������
	 * @param request
	 * @param orderId    	������
	 * @param orderStatus	����״̬ 0�����׳ɹ� 1��ʧ�� 2��ȡ��
	 * @return
	 */
	@RequestMapping("/updateOrderStatusByOrderId.do")
	@ResponseBody
	public Object deleteOrderInfoByOrderid(final HttpServletRequest request){
		final OrderResult result = new OrderResult(true, "", "");
		final String orderId = request.getParameter("orderId");
		final String orderStatus = "2";
		controllerTemplate.execute(result,new ControllerCallBack() {
			
			@Override
			public void executeService() {
				int a  = orderDao.updateOrderStatusByOrderId(orderStatus, orderId);
				if(a == 0){
					result.setErrMsg("û�д˶�������ˢ������");
					result.setBizSucc(false);
				}
				result.setBizSucc(true);
			}
			
			@Override
			public void check() {
				AssertUtil.notBlank(orderId, "������Ϊ��");
				
			}
		});
		return result;
	}
	
	/**
	 * ȷ���ջ�
	 * @param request
	 * @param orderId    	������
	 * @param orderStatus	����״̬ 0�����׳ɹ� 1��ʧ�� 2��ȡ��
	 * @param sendGoods		����״̬  1:δ����    2���ѷ���     3���˻���  4�����
	 * @return
	 */
	@RequestMapping("/updateOrderStatusAndSendGoodsByOrderId.do")
	@ResponseBody
	public Object updateOrderSendGoodsByOrderId(HttpServletRequest request){
		final OrderResult result = new OrderResult(false, "", "");
		final String orderId = request.getParameter("orderId");
		final String orderStatus = "0";
		final String sendGoods = "4";
		controllerTemplate.execute(result, new ControllerCallBack() {
			
			@Override
			public void executeService() {
				int a = orderDao.updateOrderStatusAndOrderSendGoodsByOrderId(orderStatus, sendGoods, orderId);
				if(a==0){
					result.setErrMsg("û�д˶�������ˢ������");
					result.setBizSucc(false);
				}
				result.setBizSucc(true);
			}
			
			@Override
			public void check() {
				AssertUtil.notBlank(orderId, "������Ϊ��");
				
			}
		});
		return result;
	}
	
	/**
	 * ɾ������
	 * @param request
	 * @param orderId    	������
	 * @param delflag		��ɾ���Ķ���   0�� δɾ��  1�� ��ɾ��
	 * @return
	 */
	@RequestMapping("/updateOrderFlagByOrderId.do")
	@ResponseBody
	public Object updateOrderFlagByOrderId(HttpServletRequest request){
		final OrderResult result = new OrderResult(true, "", "");
		//final String orderId = "1315215";
		final String orderId = request.getParameter("orderId");
		final String delflag = "1";
		controllerTemplate.execute(result, new ControllerCallBack() {
			
			@Override
			public void executeService() {
				int a  = orderDao.updateDelFlagByOrderId(delflag, orderId);
				if(a==0){
					result.setErrMsg("û�д˶�������ˢ������");
					result.setBizSucc(false);
				}
				result.setBizSucc(true);
			}
			
			@Override
			public void check() {
				AssertUtil.notBlank(orderId, "������Ϊ��");
			}
		});
		return result;
	}
	
	/**
	 * ������Ʒ����ҳ��
	 * @return
	 */
	@RequestMapping("/orderOpinion.html")
	public String productOpinion (HttpServletRequest request,ModelMap modelmap){
		//String userId = (String) request.getSession().getAttribute("userId");
		String userId = request.getParameter("userId");
		String productNO = request.getParameter("productNO");
		String orderId = request.getParameter("orderId");
		String shopId = request.getParameter("shopId");
		modelmap.addAttribute("productNO", productNO);
		modelmap.addAttribute("userId", userId);
		modelmap.addAttribute("orderId", orderId);
		modelmap.addAttribute("shopId", shopId);
		return "html/orderOpinion";
	}
	
	
	
	/**
	 * ��Ʒ����
	 * @param request
	 * @return
	 */
	@RequestMapping("/comments.do")
	@ResponseBody
	public Object submitOrderComments(HttpServletRequest request){
		final OrderResult result = new OrderResult(true, "", "");
		final String userId = request.getParameter("userId");
		final String productNo = request.getParameter("productNO");
		final String orderId = request.getParameter("orderId");
		final String shopId = request.getParameter("shopId");
		final String comments = request.getParameter("text");
		final String star1 = request.getParameter("star1");
		final String star2 = request.getParameter("star2");
		final String star3 = request.getParameter("star3");
		controllerTemplate.execute(result, new ControllerCallBack() {
			
			@Override
			public void executeService() {
				ProductCommentDO productCommentDO = new ProductCommentDO();
				productCommentDO.setUserId(userId);
				productCommentDO.setProductNo(productNo);
				productCommentDO.setOrderId(orderId);
				productCommentDO.setShopId(shopId);
				productCommentDO.setComments(comments);
				productCommentDO.setStarCountOne(star1);
				productCommentDO.setStarCountTwo(star2);
				productCommentDO.setStarCountThree(star3);
				int shopStar = shopStar(star1,star2,star3);
				/*
				 * ����������۱���Ʒ����
				 */
				ShopCommentDO shopCommentDO = new ShopCommentDO();
				shopCommentDO.setProductStar(shopStar);
				shopCommentDO.setShopId(shopId);
				shopCommentDO.setUserId(userId);
				shopCommentDO.setProductNo(productNo);
				
				int a = productCommentDAO.insertProductComments(productCommentDO); 
				int b =shopCommentDAO.insertSinglComments(shopCommentDO);
				if(a<=0 && b<=0){
					result.setBizSucc(false);
					result.setErrMsg("����ʧ��");
				}
				
			}
			
			@Override
			public void check() {
				AssertUtil.notBlank(orderId, "������Ϊ��");
				AssertUtil.notBlank(userId, "�û�IdΪ��");
				AssertUtil.notBlank(productNo, "��Ʒ��Ϊ��");
				AssertUtil.notBlank(shopId, "���̺�Ϊ��");
				AssertUtil.notBlank(star1, "�Ǽ�һΪ��");
				AssertUtil.notBlank(star2, "�Ǽ���Ϊ��");
				AssertUtil.notBlank(star3, "�Ǽ���Ϊ��");
				
			}
		});
		return result;
	}
	
	

	
	/**
	 * ����ѷ����Ķ���
	 * @return
	 */
	@RequestMapping("/orderDelivered.html")
	public String onclickDelivered(HttpServletRequest request,ModelMap modelmap){
		//String userId = (String) request.getSession().getAttribute("userId");
		String userId = request.getParameter("userId");
		String orderId = request.getParameter("orderId");
		String productNo = request.getParameter("productNo");
		String shopId = request.getParameter("shopId");
		String flg = "0";
		String delflag = "0";
		try {
			AddressDO addressDO = addressDao.selectDefaultAddress(userId, flg);
			ProductShow productShow = orderDao.selectOrderAllByOrderId(orderId, delflag);
			Money money1 = new Money(productShow.getPrice());
			money1 = money1.divide(100);   //��Ʒ����
			
			Money money2 = new Money(productShow.getOrderPrice());
			money2 = money2.divide(100);
			int proCount = productShow.getProductCount();
			money2 = money1.multiply(proCount);  //��Ʒ�ܼ�
			
			Money money3 = new Money(productShow.getLuggage());
			money3 = money3.divide(100);      //�˷�
			
			Money money4 = new Money();
			money4 = money2.add(money3);      //������
			
			productShow.setMoney1(money1);
			productShow.setMoney2(money2);
			productShow.setMoney3(money3);
			productShow.setMoney4(money4);
			
			List<Clothing> clothingList = new ArrayList<Clothing>();
			productNo = productShow.getProductNo();
			List<ProductParameterDO> parameterList = productParameterDAO.selectProductParametersById(productNo,"");
			Clothing clothing = new Clothing();
				for(ProductParameterDO productParameterDO : parameterList){
					if(productParameterDO.getFatherName().equals("��ɫ")){
						clothing.setColor(productParameterDO.getChildrenName());
					}
					if(productParameterDO.getFatherName().equals("�ߴ�")){
						clothing.setDimensions(productParameterDO.getChildrenName());
					}
				}
			clothingList.add(clothing);
			ShopDO shopDO = shopDAO.selectShopByShopId(shopId);
			
			modelmap.addAttribute("shopDO", shopDO);
			modelmap.addAttribute("clothingList", clothingList);
			modelmap.addAttribute("addressDO", addressDO);
			modelmap.addAttribute("productShow", productShow);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "html/orderDelivered";
	}
	
	
	/**
	 * ������׹رյĶ���
	 * @param request
	 * @param modelmap
	 * @return
	 */
	@RequestMapping("/orderFail.html")
	public String onClickFail(HttpServletRequest request,ModelMap modelmap){
		//String userId = (String) request.getSession().getAttribute("userId");
				String userId = request.getParameter("userId");
				String orderId = request.getParameter("orderId");
				String productNo = request.getParameter("productNo");
				String shopId = request.getParameter("shopId");
				String flg = "0";
				String delflag = "0";
				try {
					AddressDO addressDO = addressDao.selectDefaultAddress(userId, flg);
					ProductShow productShow = orderDao.selectOrderAllByOrderId(orderId, delflag);
					Money money1 = new Money(productShow.getPrice());
					money1 = money1.divide(100);   //��Ʒ����
					
					Money money2 = new Money(productShow.getOrderPrice());
					money2 = money2.divide(100);
					int proCount = productShow.getProductCount();
					money2 = money1.multiply(proCount);  //��Ʒ�ܼ�
					
					Money money3 = new Money(productShow.getLuggage());
					money3 = money3.divide(100);      //�˷�
					
					Money money4 = new Money();
					money4 = money2.add(money3);      //������
					
					productShow.setMoney1(money1);
					productShow.setMoney2(money2);
					productShow.setMoney3(money3);
					productShow.setMoney4(money4);
					
					List<Clothing> clothingList = new ArrayList<Clothing>();
					productNo = productShow.getProductNo();
					List<ProductParameterDO> parameterList = productParameterDAO.selectProductParametersById(productNo,"");
					Clothing clothing = new Clothing();
						for(ProductParameterDO productParameterDO : parameterList){
							if(productParameterDO.getFatherName().equals("��ɫ")){
								clothing.setColor(productParameterDO.getChildrenName());
							}
							if(productParameterDO.getFatherName().equals("�ߴ�")){
								clothing.setDimensions(productParameterDO.getChildrenName());
							}
						}
					clothingList.add(clothing);
					ShopDO shopDO = shopDAO.selectShopByShopId(shopId);
					
					modelmap.addAttribute("shopDO", shopDO);
					modelmap.addAttribute("clothingList", clothingList);
					modelmap.addAttribute("addressDO", addressDO);
					modelmap.addAttribute("productShow", productShow);
				} catch (Exception e) {
					e.printStackTrace();
				}
		return "html/orderFail";
	}
	
	/**
	 * ����ȴ���Ҹ���Ķ���
	 * @param request
	 * @param modelmap
	 * @return
	 */
	@RequestMapping("/orderUnpaid.html")
	public String onClickUnpaid(HttpServletRequest request,ModelMap modelmap){
		//String userId = (String) request.getSession().getAttribute("userId");
		String userId = request.getParameter("userId");
		String orderId = request.getParameter("orderId");
		String productNo = request.getParameter("productNo");
		String shopId = request.getParameter("shopId");
		String flg = "0";
		String delflag = "0";
		try {
			AddressDO addressDO = addressDao.selectDefaultAddress(userId, flg);
			ProductShow productShow = orderDao.selectOrderAllByOrderId(orderId, delflag);
			Money money1 = new Money(productShow.getPrice());
			money1 = money1.divide(100);   //��Ʒ����
			
			Money money2 = new Money(productShow.getOrderPrice());
			money2 = money2.divide(100);
			int proCount = productShow.getProductCount();
			money2 = money1.multiply(proCount);  //��Ʒ�ܼ�
			
			Money money3 = new Money(productShow.getLuggage());
			money3 = money3.divide(100);      //�˷�
			
			Money money4 = new Money();
			money4 = money2.add(money3);      //������
			
			productShow.setMoney1(money1);
			productShow.setMoney2(money2);
			productShow.setMoney3(money3);
			productShow.setMoney4(money4);
			
			List<Clothing> clothingList = new ArrayList<Clothing>();
			productNo = productShow.getProductNo();
			List<ProductParameterDO> parameterList = productParameterDAO.selectProductParametersById(productNo,"");
			Clothing clothing = new Clothing();
				for(ProductParameterDO productParameterDO : parameterList){
					if(productParameterDO.getFatherName().equals("��ɫ")){
						clothing.setColor(productParameterDO.getChildrenName());
					}
					if(productParameterDO.getFatherName().equals("�ߴ�")){
						clothing.setDimensions(productParameterDO.getChildrenName());
					}
				}
			clothingList.add(clothing);
			ShopDO shopDO = shopDAO.selectShopByShopId(shopId);
			
			modelmap.addAttribute("shopDO", shopDO);
			modelmap.addAttribute("clothingList", clothingList);
			modelmap.addAttribute("addressDO", addressDO);
			modelmap.addAttribute("productShow", productShow);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "html/orderUnpaid";
	}
	
	/**
	 * ������׳ɹ��Ķ���
	 * @param request
	 * @param modelmap
	 * @return
	 */
	@RequestMapping("/orderSuccess.html")
	public String onClickSuccess(HttpServletRequest request,ModelMap modelmap){
		//String userId = (String) request.getSession().getAttribute("userId");
				String userId = request.getParameter("userId");
				String orderId = request.getParameter("orderId");
				String productNo = request.getParameter("productNo");
				String shopId = request.getParameter("shopId");
				String flg = "0";
				String delflag = "0";
				try {
					AddressDO addressDO = addressDao.selectDefaultAddress(userId, flg);
					ProductShow productShow = orderDao.selectOrderAllByOrderId(orderId, delflag);
					Money money1 = new Money(productShow.getPrice());
					money1 = money1.divide(100);   //��Ʒ����
					
					Money money2 = new Money(productShow.getOrderPrice());
					money2 = money2.divide(100);
					int proCount = productShow.getProductCount();
					money2 = money1.multiply(proCount);  //��Ʒ�ܼ�
					
					Money money3 = new Money(productShow.getLuggage());
					money3 = money3.divide(100);      //�˷�
					
					Money money4 = new Money();
					money4 = money2.add(money3);      //������
					
					productShow.setMoney1(money1);
					productShow.setMoney2(money2);
					productShow.setMoney3(money3);
					productShow.setMoney4(money4);
					
					List<Clothing> clothingList = new ArrayList<Clothing>();
					productNo = productShow.getProductNo();
					List<ProductParameterDO> parameterList = productParameterDAO.selectProductParametersById(productNo,"");
					Clothing clothing = new Clothing();
						for(ProductParameterDO productParameterDO : parameterList){
							if(productParameterDO.getFatherName().equals("��ɫ")){
								clothing.setColor(productParameterDO.getChildrenName());
							}
							if(productParameterDO.getFatherName().equals("�ߴ�")){
								clothing.setDimensions(productParameterDO.getChildrenName());
							}
						}
					clothingList.add(clothing);
					
					ShopDO shopDO = shopDAO.selectShopByShopId(shopId);
					
					modelmap.addAttribute("shopDO", shopDO);
					modelmap.addAttribute("clothingList", clothingList);
					modelmap.addAttribute("addressDO", addressDO);
					modelmap.addAttribute("productShow", productShow);
				} catch (Exception e) {
					e.printStackTrace();
				}
		return "html/orderSuccess";
	}
	
	/**
	 * ����ȴ������Ķ���
	 * @param request
	 * @param modelmap
	 * @return
	 */
	@RequestMapping("/orderUndelivered.html")
	public String onClickUndelivered(HttpServletRequest request,ModelMap modelmap){
		//String userId = (String) request.getSession().getAttribute("userId");
		String userId = request.getParameter("userId");
		String orderId = request.getParameter("orderId");
		String productNo = request.getParameter("productNo");
		String shopId = request.getParameter("shopId");
		String flg = "0";
		String delflag = "0";
		try {
			AddressDO addressDO = addressDao.selectDefaultAddress(userId, flg);
			ProductShow productShow = orderDao.selectOrderAllByOrderId(orderId, delflag);
			Money money1 = new Money(productShow.getPrice());
			money1 = money1.divide(100);   //��Ʒ����
			
			Money money2 = new Money(productShow.getOrderPrice());
			money2 = money2.divide(100);
			int proCount = productShow.getProductCount();
			money2 = money1.multiply(proCount);  //��Ʒ�ܼ�
			
			Money money3 = new Money(productShow.getLuggage());
			money3 = money3.divide(100);      //�˷�
			
			Money money4 = new Money();
			money4 = money2.add(money3);      //������
			
			productShow.setMoney1(money1);
			productShow.setMoney2(money2);
			productShow.setMoney3(money3);
			productShow.setMoney4(money4);
			
			List<Clothing> clothingList = new ArrayList<Clothing>();
			productNo = productShow.getProductNo();
			List<ProductParameterDO> parameterList = productParameterDAO.selectProductParametersById(productNo,"");
			Clothing clothing = new Clothing();
				for(ProductParameterDO productParameterDO : parameterList){
					if(productParameterDO.getFatherName().equals("��ɫ")){
						clothing.setColor(productParameterDO.getChildrenName());
					}
					if(productParameterDO.getFatherName().equals("�ߴ�")){
						clothing.setDimensions(productParameterDO.getChildrenName());
					}
				}
			clothingList.add(clothing);
			ShopDO shopDO = shopDAO.selectShopByShopId(shopId);
			
			modelmap.addAttribute("shopDO", shopDO);
			modelmap.addAttribute("clothingList", clothingList);
			modelmap.addAttribute("addressDO", addressDO);
			modelmap.addAttribute("productShow", productShow);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "html/orderUndelivered";
	}
	
	public int shopStar(String one,String two,String three){
		double oneStar=Integer.valueOf(one);
		double twoStar=Integer.valueOf(two);
		double threeStar=Integer.valueOf(three);
		double shopStar=(oneStar+twoStar+threeStar)/3;
		return Integer.valueOf((int)Math.ceil(shopStar));
	}


}
