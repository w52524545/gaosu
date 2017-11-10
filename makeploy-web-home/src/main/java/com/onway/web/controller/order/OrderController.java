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
 * 我的订单
 * 
 * @author Administrator
 *
 */
@Controller
public class OrderController extends AbstractController {

	/**
	 * 查询所有可显示订单
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
				money1 = money1.divide(100);   //商品单价
				
				Money money2 = new Money(productShow.getOrderPrice());
				money2 = money2.divide(100);
				int proCount = productShow.getProductCount();
				money2 = money1.multiply(proCount);  //商品总价
				
				Money money3 = new Money(productShow.getLuggage());
				money3 = money3.divide(100);      //运费
				
				Money money4 = new Money();
				money4 = money2.add(money3);      //订单价
				
				productShow.setMoney1(money1);
				productShow.setMoney2(money2);
				productShow.setMoney3(money3);
				productShow.setMoney4(money4);
				
				productNo = productShow.getProductNo();
				List<ProductParameterDO> parameterList = productParameterDAO.selectProductParametersById(productNo,praflag);
				Clothing clothing = new Clothing();
					for(ProductParameterDO productParameterDO : parameterList){
						clothing.setProductNo(productParameterDO.getProductNo());
						if(productParameterDO.getFatherName().equals("颜色")){
							clothing.setColor(productParameterDO.getChildrenName());
						}
						if(productParameterDO.getFatherName().equals("尺寸")){
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
	 * 取消订单
	 * @param request
	 * @param orderId    	订单号
	 * @param orderStatus	订单状态 0：交易成功 1：失败 2：取消
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
					result.setErrMsg("没有此订单，请刷新重试");
					result.setBizSucc(false);
				}
				result.setBizSucc(true);
			}
			
			@Override
			public void check() {
				AssertUtil.notBlank(orderId, "订单号为空");
				
			}
		});
		return result;
	}
	
	/**
	 * 确认收货
	 * @param request
	 * @param orderId    	订单号
	 * @param orderStatus	订单状态 0：交易成功 1：失败 2：取消
	 * @param sendGoods		发货状态  1:未发货    2：已发货     3：退货中  4：完成
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
					result.setErrMsg("没有此订单，请刷新重试");
					result.setBizSucc(false);
				}
				result.setBizSucc(true);
			}
			
			@Override
			public void check() {
				AssertUtil.notBlank(orderId, "订单号为空");
				
			}
		});
		return result;
	}
	
	/**
	 * 删除订单
	 * @param request
	 * @param orderId    	订单号
	 * @param delflag		被删除的订单   0： 未删除  1： 已删除
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
					result.setErrMsg("没有此订单，请刷新重试");
					result.setBizSucc(false);
				}
				result.setBizSucc(true);
			}
			
			@Override
			public void check() {
				AssertUtil.notBlank(orderId, "订单号为空");
			}
		});
		return result;
	}
	
	/**
	 * 进入商品评价页面
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
	 * 商品评价
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
				 * 插入店铺评论表商品总评
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
					result.setErrMsg("评价失败");
				}
				
			}
			
			@Override
			public void check() {
				AssertUtil.notBlank(orderId, "订单号为空");
				AssertUtil.notBlank(userId, "用户Id为空");
				AssertUtil.notBlank(productNo, "商品号为空");
				AssertUtil.notBlank(shopId, "店铺号为空");
				AssertUtil.notBlank(star1, "星级一为空");
				AssertUtil.notBlank(star2, "星级二为空");
				AssertUtil.notBlank(star3, "星级三为空");
				
			}
		});
		return result;
	}
	
	

	
	/**
	 * 点击已发货的订单
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
			money1 = money1.divide(100);   //商品单价
			
			Money money2 = new Money(productShow.getOrderPrice());
			money2 = money2.divide(100);
			int proCount = productShow.getProductCount();
			money2 = money1.multiply(proCount);  //商品总价
			
			Money money3 = new Money(productShow.getLuggage());
			money3 = money3.divide(100);      //运费
			
			Money money4 = new Money();
			money4 = money2.add(money3);      //订单价
			
			productShow.setMoney1(money1);
			productShow.setMoney2(money2);
			productShow.setMoney3(money3);
			productShow.setMoney4(money4);
			
			List<Clothing> clothingList = new ArrayList<Clothing>();
			productNo = productShow.getProductNo();
			List<ProductParameterDO> parameterList = productParameterDAO.selectProductParametersById(productNo,"");
			Clothing clothing = new Clothing();
				for(ProductParameterDO productParameterDO : parameterList){
					if(productParameterDO.getFatherName().equals("颜色")){
						clothing.setColor(productParameterDO.getChildrenName());
					}
					if(productParameterDO.getFatherName().equals("尺寸")){
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
	 * 点击交易关闭的订单
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
					money1 = money1.divide(100);   //商品单价
					
					Money money2 = new Money(productShow.getOrderPrice());
					money2 = money2.divide(100);
					int proCount = productShow.getProductCount();
					money2 = money1.multiply(proCount);  //商品总价
					
					Money money3 = new Money(productShow.getLuggage());
					money3 = money3.divide(100);      //运费
					
					Money money4 = new Money();
					money4 = money2.add(money3);      //订单价
					
					productShow.setMoney1(money1);
					productShow.setMoney2(money2);
					productShow.setMoney3(money3);
					productShow.setMoney4(money4);
					
					List<Clothing> clothingList = new ArrayList<Clothing>();
					productNo = productShow.getProductNo();
					List<ProductParameterDO> parameterList = productParameterDAO.selectProductParametersById(productNo,"");
					Clothing clothing = new Clothing();
						for(ProductParameterDO productParameterDO : parameterList){
							if(productParameterDO.getFatherName().equals("颜色")){
								clothing.setColor(productParameterDO.getChildrenName());
							}
							if(productParameterDO.getFatherName().equals("尺寸")){
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
	 * 点击等待买家付款的订单
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
			money1 = money1.divide(100);   //商品单价
			
			Money money2 = new Money(productShow.getOrderPrice());
			money2 = money2.divide(100);
			int proCount = productShow.getProductCount();
			money2 = money1.multiply(proCount);  //商品总价
			
			Money money3 = new Money(productShow.getLuggage());
			money3 = money3.divide(100);      //运费
			
			Money money4 = new Money();
			money4 = money2.add(money3);      //订单价
			
			productShow.setMoney1(money1);
			productShow.setMoney2(money2);
			productShow.setMoney3(money3);
			productShow.setMoney4(money4);
			
			List<Clothing> clothingList = new ArrayList<Clothing>();
			productNo = productShow.getProductNo();
			List<ProductParameterDO> parameterList = productParameterDAO.selectProductParametersById(productNo,"");
			Clothing clothing = new Clothing();
				for(ProductParameterDO productParameterDO : parameterList){
					if(productParameterDO.getFatherName().equals("颜色")){
						clothing.setColor(productParameterDO.getChildrenName());
					}
					if(productParameterDO.getFatherName().equals("尺寸")){
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
	 * 点击交易成功的订单
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
					money1 = money1.divide(100);   //商品单价
					
					Money money2 = new Money(productShow.getOrderPrice());
					money2 = money2.divide(100);
					int proCount = productShow.getProductCount();
					money2 = money1.multiply(proCount);  //商品总价
					
					Money money3 = new Money(productShow.getLuggage());
					money3 = money3.divide(100);      //运费
					
					Money money4 = new Money();
					money4 = money2.add(money3);      //订单价
					
					productShow.setMoney1(money1);
					productShow.setMoney2(money2);
					productShow.setMoney3(money3);
					productShow.setMoney4(money4);
					
					List<Clothing> clothingList = new ArrayList<Clothing>();
					productNo = productShow.getProductNo();
					List<ProductParameterDO> parameterList = productParameterDAO.selectProductParametersById(productNo,"");
					Clothing clothing = new Clothing();
						for(ProductParameterDO productParameterDO : parameterList){
							if(productParameterDO.getFatherName().equals("颜色")){
								clothing.setColor(productParameterDO.getChildrenName());
							}
							if(productParameterDO.getFatherName().equals("尺寸")){
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
	 * 点击等待发货的订单
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
			money1 = money1.divide(100);   //商品单价
			
			Money money2 = new Money(productShow.getOrderPrice());
			money2 = money2.divide(100);
			int proCount = productShow.getProductCount();
			money2 = money1.multiply(proCount);  //商品总价
			
			Money money3 = new Money(productShow.getLuggage());
			money3 = money3.divide(100);      //运费
			
			Money money4 = new Money();
			money4 = money2.add(money3);      //订单价
			
			productShow.setMoney1(money1);
			productShow.setMoney2(money2);
			productShow.setMoney3(money3);
			productShow.setMoney4(money4);
			
			List<Clothing> clothingList = new ArrayList<Clothing>();
			productNo = productShow.getProductNo();
			List<ProductParameterDO> parameterList = productParameterDAO.selectProductParametersById(productNo,"");
			Clothing clothing = new Clothing();
				for(ProductParameterDO productParameterDO : parameterList){
					if(productParameterDO.getFatherName().equals("颜色")){
						clothing.setColor(productParameterDO.getChildrenName());
					}
					if(productParameterDO.getFatherName().equals("尺寸")){
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
