package com.onway.web.controller.mng;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.onway.makeploy.common.dal.dataobject.OrderInfo;
import com.onway.makeploy.common.dal.dataobject.ProductDO;
import com.onway.makeploy.common.dal.dataobject.ShopDO;
import com.onway.web.controller.AbstractController;
import com.onway.web.controller.result.JsonResult;
import com.onway.web.controller.result.ProImageResult;

@Controller
public class ProductCheckMngController extends AbstractController {

	@RequestMapping("/productCheckMng.html")
	public String loginMng(HttpServletRequest request, ModelMap modelMap) {

		String userId = (String) request.getSession().getAttribute("userId");
		boolean checkRoleBoss = checkRoleBoss(userId);
		if (checkRoleBoss == false) {
			return "mng/loginMng";
		}
		// 判断是否为运营者
		int checkOperative = checkOperative(userId);
		modelMap.put("checkOperative", checkOperative);

		Map<String, Integer> pageDate = getPageData(request);
		String shopId = getParameterCheck(request, "shopId");
		String productNo = getParameterCheck(request, "productNo");
		List<ProductDO> unCheckProduct = productDao.selectUncheckProduct(
				shopId, productNo, pageDate.get(OFFSET) / 2,
				pageDate.get(PAGE_SIZE_STR) / 2);
		int count = productDao.selectCountUncheckProduct(shopId, productNo);
		modelMap.put("shopId", shopId);
		modelMap.put("productNo", productNo);
		modelMap.put("totalPages",
				calculatePage(count, pageDate.get(PAGE_SIZE_STR) / 2));// 总页数
		modelMap.put("totalItems", count);// 总条数
		modelMap.put(CURRENTPAGE, pageDate.get(CURRENTPAGE));// 当前页数
		// 带有各类图片的unCheckPro
		List<OrderInfo> productList = new ArrayList<OrderInfo>();

		for (ProductDO pro : unCheckProduct) {
			OrderInfo order = new OrderInfo();
			ShopDO shopDO = shopDAO.selectShopByShopId(pro.getShopId());
			if (null != shopDO) {
				order.setShopName(shopDO.getShopName());
				order.setShopCell(shopDO.getCell());
				order.setShopId(pro.getShopId());
				order.setProductNo(pro.getProductNo());
				order.setProductName(pro.getProductName());
				order.setProductType(pro.getProductType());
				order.setChildren(pro.getChildren());
				// 存放3种图片
				order.setMoren(proImageDAO.selectProImageByProShopid(
						pro.getShopId(), pro.getProductNo(), "1"));
				order.setBanner(proImageDAO.selectProImageByProShopid(
						pro.getShopId(), pro.getProductNo(), "2"));
				order.setProDec(proImageDAO.selectProImageByProShopid(
						pro.getShopId(), pro.getProductNo(), "0"));
				// Map<String, List<ProImageDO>> map = new HashMap<String,
				// List<ProImageDO>>();
				// map.put("default",proImageDAO.selectProImageByProShopid(pro.getShopId(),
				// pro.getProductNo(), "1"));
				// map.put("banner",proImageDAO.selectProImageByProShopid(pro.getShopId(),
				// pro.getProductNo(), "2"));
				// map.put("proDec",proImageDAO.selectProImageByProShopid(pro.getShopId(),
				// pro.getProductNo(), "0"));
				// order.setMap(map);
				productList.add(order);
			}
		}
		request.setAttribute("page", modelMap);
		modelMap.put("productList", productList);
		return "mng/productCheckMng";
	}

	@RequestMapping("/productCheckSet.do")
	@ResponseBody
	public String productCheckSet(HttpServletRequest request, ModelMap modelMap) {
		String shoId = getParameterCheck(request, "shoId");
		String productNo = getParameterCheck(request, "productNo");
		String flag = getParameterCheck(request, "flag");
		String siz = "-1";
		int i = 0;
		if (flag.equals("1")) {

			String returnMoney = getParameterCheck(request, "returnMoney");
			String returnPoint = getParameterCheck(request, "returnPoint");
			if (null == returnMoney) {
				returnMoney = "0";
			}
			if (null == returnPoint) {
				returnPoint = "0";
			}
			Double DreturnMoney = new Double(returnMoney);
			Double DreturnPoint = new Double(returnPoint);
			i = productDao.updateProductCheck("1", "0", DreturnMoney,
					DreturnPoint, productNo, shoId);
			siz = "1";
		} else if (flag.equals("0")) {
			i = productDao
					.updateProductCheck("2", null, 0, 0, productNo, shoId);
			siz = "0";
		}
        System.out.println(i);
		return siz;

	}

	@RequestMapping("/productCheckChange.do")
	@ResponseBody
	public Object productCheckChange(HttpServletRequest request,
			ModelMap modelMap) {
		JsonResult result = new JsonResult(false);
		String shopId = getParameterCheck(request, "shopId");
		String productNo = getParameterCheck(request, "productNo");
		String returnMoney = getParameterCheck(request, "returnMoney");
		String returnPoint = getParameterCheck(request, "returnPoint");

		ProductDO productDO = productDao.selectProductByProductNo(productNo,
				shopId);

		if (null == returnMoney) {
			returnMoney = "0";
		}
		if (null == returnPoint) {
			returnPoint = "0";
		}
		Double DreturnMoney = new Double(returnMoney);
		Double DreturnPoint = new Double(returnPoint);
		int changeresult = productDao.updateProductCheck(
				productDO.getCheckStatus(), productDO.getStatus(),
				DreturnMoney, DreturnPoint, productNo, shopId);
		if (changeresult >= 1) {
			result.setBizSucc(true);
		}

		return result;
	}

	@RequestMapping("showPic.do")
	@ResponseBody
	public Object showPic(HttpServletRequest request, ModelMap map) {
		ProImageResult result = new ProImageResult();
		String shopId = request.getParameter("shopId");
		String proNo = request.getParameter("productNo");
		String flag = request.getParameter("flag");
		result.setResult((proImageDAO.selectProImageByProShopid(shopId, proNo,
				flag)));
		result.setFlag(flag);
		return result;
	}

}
