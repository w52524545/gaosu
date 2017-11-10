package com.onway.web.controller.shop;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.onway.common.lang.Money;
import com.onway.common.lang.StringUtils;
import com.onway.makeploy.common.dal.dataobject.ProductDO;
import com.onway.makeploy.common.dal.dataobject.ShopDO;
import com.onway.makeploy.common.dal.dataobject.UserDO;
import com.onway.makeploy.common.service.exception.ErrorException;
import com.onway.platform.common.service.util.AssertUtil;
import com.onway.web.controller.AbstractController;
import com.onway.web.controller.result.JsonPageResult;
import com.onway.web.controller.result.MyShopResult;
import com.onway.web.controller.result.ProductResult;
import com.onway.web.controller.result.ShopResult;
import com.onway.web.controller.template.ControllerCallBack;

/**
 * �ҵĵ���
 * 
 * @author Administrator
 *
 */
@Controller
public class ShopController extends AbstractController {

	/**
	 * ��ת�ҵĵ���
	 * 
	 * @param request
	 * @param modelMap
	 * @return
	 */
	@RequestMapping("/mystore.html")
	public Object queryMyShop(final HttpServletRequest request,
			final ModelMap modelMap) {
		// String userId = (String) request.getSession().getAttribute("userId");
		final String userId = "13252";
		if (StringUtils.isEmpty(userId)) {
			throw new ErrorException("�û�δ��¼");
		}
		MyShopResult result = new MyShopResult(true, "", "");
		UserDO userDo = userDao.selectUserInfoByUserId(userId);
		ShopDO shopDo = shopDAO.selectShopInfoByUserId(userId);
		result.setNickName(userDo.getNickName());
		result.setHeadUrl(userDo.getHeadUrl());
		result.setAttentionCount(shopDo.getAttentionCount());
		result.setCollectCount(shopDo.getCollectCount());
		modelMap.put("result", result);
		return "html/mystore";
	}

	/**
	 * Ԥ��������Ϣ
	 * 
	 * @param request
	 * @param modelMap
	 * @return
	 */
	@RequestMapping("/onlinestore.html")
	public Object onlinestore(final HttpServletRequest request,
			final ModelMap modelMap) {
		// String userId = (String) request.getSession().getAttribute("userId");
		final String userId = "13252";
		if (StringUtils.isEmpty(userId)) {
			throw new ErrorException("�û�δ��¼");
		}
		final String shopId=request.getParameter("shopId");
		MyShopResult result = new MyShopResult(true, "", "");
		
		// ��ת���ҵĵ���
		if (StringUtils.isEmpty(shopId)) {
			UserDO userDo = userDao.selectUserInfoByUserId(userId);
			ShopDO shopDo = shopDAO.selectShopInfoByUserId(userId);
			result.setShopName(shopDo.getShopName());
			result.setHeadUrl(userDo.getHeadUrl());
			result.setShopUrl(shopDo.getShopUrl());
			result.setAttentionCount(shopDo.getAttentionCount());
			result.setCollectCount(shopDo.getCollectCount());
			result.setQrUrl(shopDo.getQrUrl());
			result.setShopId(shopDo.getShopId());
			modelMap.put("result", result);
			return "html/onlinestore";
		}
		
		//��ת�������ҳ��
		ShopDO shops = shopDAO.selectShopInfoByShopId(shopId);
		result.setShopName(shops.getShopName());
		result.setShopUrl(shops.getShopUrl());
		result.setAttentionCount(shops.getAttentionCount());
		result.setCollectCount(shops.getCollectCount());
		result.setQrUrl(shops.getQrUrl());
		result.setShopId(shops.getShopId());
		modelMap.put("result", result);
		
		return "html/onlinestore";
	}
	
	/**
	 * �����ҵĵ���
	 * @param request
	 * @param modelMap
	 * @return
	 */
	@RequestMapping("/shareMystore.html")
	public Object shareMystore(final HttpServletRequest request,final ModelMap modelMap) {
		// String userId = (String) request.getSession().getAttribute("userId");
		final String userId = "13252";
		if (StringUtils.isEmpty(userId)) {
			throw new ErrorException("�û�δ��¼");
		}
		MyShopResult result = new MyShopResult(true, "", "");
		UserDO userDo = userDao.selectUserInfoByUserId(userId);
		ShopDO shopDo = shopDAO.selectShopInfoByUserId(userId);
		result.setNickName(userDo.getNickName());
		result.setHeadUrl(userDo.getHeadUrl());
		result.setQrUrl(shopDo.getQrUrl());
		result.setShopId(shopDo.getShopId());
		modelMap.put("result", result);
		return "html/code";
	}

	/**
	 * ��ȡ�ҵĵ�����Ʒ��Ϣ
	 * 
	 * @param userId  �û�Id
	 */
	@RequestMapping("/queryMyShop.do")
	@ResponseBody
	public Object queryMyShop(final HttpServletRequest request) {
		// String userId = (String) request.getSession().getAttribute("userId");
		final String userId = "13252";
		final Integer pageNum = adjustPageNo(request);
		final Integer pageSize = adjustPageSize(request);

		final JsonPageResult<ProductResult> result = new JsonPageResult<ProductResult>(
				true);
		controllerTemplate.execute(result, new ControllerCallBack() {

			@Override
			public void executeService() {
				// ��ѯ��¼��,��ҳ��
				Integer startRow = (pageNum - 1) * pageSize;

				int totalItem = productDao.selectProductCountByUserId(userId);
				int totalPages = (totalItem - 1) / pageSize + 1;

				List<ProductDO> productList = productDao.selectProductByUserId(
						userId, startRow, pageSize);

				List<ProductResult> list = new ArrayList<ProductResult>();
				ProductResult productResult = null;
				for (ProductDO prod : productList) {
					if (prod.getShopId() != null) {
						productResult = new ProductResult(true, "", "");
						productResult.setUserId(prod.getUserId());
						productResult.setId(prod.getId());
						productResult.setShopId(prod.getShopId());
						productResult.setStock(prod.getStock());
						productResult.setSoleCount(prod.getSoleCount());
						productResult.setDelFlg(prod.getDelFlg());
						productResult.setOldPrice(prod.getOldPrice());
						productResult.setPrice(prod.getPrice());
						productResult.setProductName(prod.getProductName());
						productResult.setProductNo(prod.getProductNo());
						productResult.setProductUrl(prod.getProductUrl());
						productResult.setProductType(prod.getProductType());
						productResult.setStatus(prod.getStatus());
						productResult.setRecommendFlg(prod.getRecommendFlg());
						list.add(productResult);
					}
				}

				result.setListObject(list);
				result.setTotalPages(totalPages);
				result.setNext(totalPages > pageNum ? true : false);
			}

			@Override
			public void check() {
				AssertUtil.notBlank(userId, "userIdΪ��");
			}
		});
		return result;
	}
	
	/**
	 * ��ȡ���������Ʒ��Ϣ
	 * 
	 * @param userId  �û�Id
	 */
	@RequestMapping("/queryAnyShop.do")
	@ResponseBody
	public Object queryAnyShop(final HttpServletRequest request) {
		final String shopId = request.getParameter("shopId");
		final Integer pageNum = adjustPageNo(request);
		final Integer pageSize = adjustPageSize(request);

		final JsonPageResult<ProductResult> result = new JsonPageResult<ProductResult>(
				true);
		controllerTemplate.execute(result, new ControllerCallBack() {

			@Override
			public void executeService() {
				// ��ѯ��¼��,��ҳ��
				Integer startRow = (pageNum - 1) * pageSize;

				int totalItem = productDao.selectGoodsCountByShopId(shopId);
				int totalPages = (totalItem - 1) / pageSize + 1;

				List<ProductDO> productList = productDao.selectProductByShopId(shopId, startRow, pageSize);

				List<ProductResult> list = new ArrayList<ProductResult>();
				ProductResult productResult = null;
				for (ProductDO prod : productList) {
					if (prod.getShopId() != null) {
						productResult = new ProductResult(true, "", "");
						productResult.setShopId(prod.getShopId());
						productResult.setStock(prod.getStock());
						productResult.setSoleCount(prod.getSoleCount());
						productResult.setDelFlg(prod.getDelFlg());
						productResult.setOldPrice(prod.getOldPrice());
						productResult.setPrice(prod.getPrice());
						productResult.setProductName(prod.getProductName());
						productResult.setProductNo(prod.getProductNo());
						productResult.setProductUrl(prod.getProductUrl());
						productResult.setProductType(prod.getProductType());
						productResult.setStatus(prod.getStatus());
						productResult.setRecommendFlg(prod.getRecommendFlg());
						list.add(productResult);
					}
				}

				result.setListObject(list);
				result.setTotalPages(totalPages);
				result.setNext(totalPages > pageNum ? true : false);
			}

			@Override
			public void check() {
				AssertUtil.notBlank(shopId, "shopIdΪ��");
			}
		});
		return result;
	}
	
	/**
	 * ��ȡ�ҵĵ�����Ϣ
	 * @param request
	 * @return
	 */
	@ResponseBody
	public Object queryMyShopInfo(final HttpServletRequest request) {
		// String userId = (String) request.getSession().getAttribute("userId");
		final String userId = "13252";
		final String shopId=request.getParameter("shopId");
		
		final ShopResult result=new ShopResult(true, "", "");
		controllerTemplate.execute(result, new ControllerCallBack() {

			@Override
			public void check() {
				
			}

			@Override
			public void executeService() {
				ShopDO shopDo = shopDAO.selectShopInfoByUserId(userId);
				if (null == shopDo) {
					result.setErrMsg("������Ϣ��ѯʧ�ܣ�û�и�����¼");
					result.setBizSucc(false);
                    throw new ErrorException("������Ϣ��ѯʧ�ܣ�û�и�����¼");
				}
				result.setShopDO(shopDo);
			}
			
		});
		return result;
	}
	
	/**
	 * �ҵĶ�ά��
	 * @param request
	 * @param modelMap
	 * @return
	 */
	@RequestMapping("/code.html")
	public Object code(final HttpServletRequest request,final ModelMap modelMap) {
		// String userId = (String) request.getSession().getAttribute("userId");
		final String userId = "13252";
		if (StringUtils.isEmpty(userId)) {
			throw new ErrorException("�û�δ��¼");
		}
		MyShopResult result = new MyShopResult(true, "", "");
		UserDO userDo = userDao.selectUserInfoByUserId(userId);
		ShopDO shopDo = shopDAO.selectShopInfoByUserId(userId);
		result.setNickName(userDo.getNickName());
		result.setHeadUrl(userDo.getHeadUrl());
		result.setAttentionCount(shopDo.getAttentionCount());
		result.setCollectCount(shopDo.getCollectCount());
		result.setQrUrl(shopDo.getQrUrl());
		result.setShopId(shopDo.getShopId());
		modelMap.put("result", result);
		return "html/code";
	}
}
