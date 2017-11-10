package com.onway.web.controller.collection;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.onway.common.lang.StringUtils;
import com.onway.makeploy.common.dal.dataobject.CollectionDO;
import com.onway.makeploy.common.dal.dataobject.ProductDO;
import com.onway.makeploy.common.dal.dataobject.ShopDO;
import com.onway.makeploy.common.service.exception.ErrorException;
import com.onway.platform.common.service.util.AssertUtil;
import com.onway.web.controller.AbstractController;
import com.onway.web.controller.result.CollectionResult;
import com.onway.web.controller.result.JsonPageResult;
import com.onway.web.controller.result.JsonResult;
import com.onway.web.controller.template.ControllerCallBack;

/**
 * 我的收藏
 * @author Administrator
 *
 */
@Controller
public class CollectionController extends AbstractController{

	/**
	 * 跳转我的收藏列表
	 * @param request
	 * @param modelMap
	 * @return
	 */
	@RequestMapping("/myCollection.html")
	public Object myCollection(final HttpServletRequest request,final ModelMap modelMap) {
        //String userId = (String) request.getSession().getAttribute("userId");
          final String userId ="13252";
          if (StringUtils.isEmpty(userId)) {
			throw new ErrorException("用户未登录");
		  }
          //final String type=request.getParameter("type");
  		  //final Integer pageNum = adjustPageNo(request);
  		  //final Integer pageSize = adjustPageSize(request);
  		  
  		  //userId所有的收藏
          int count = collectionDao.selectCollectionCount(userId);
          if(count >= 1){
        	    //查询记录数,总页数
				//Integer startRow = (pageNum - 1) * pageSize;
        	  
				//int totalPages = (totalItem - 1) / pageSize + 1;

        	    //查询所有宝贝收藏信息
				List<CollectionDO> goodsList = collectionDao.selectCollectionInfoByUserIdAndTypeAndStatus(userId, "0", "0", null, null);
				
				
				List<ProductDO> productList=new ArrayList<ProductDO>();
				List<ShopDO> shopList=new ArrayList<ShopDO>();
				for (CollectionDO collectionDo : goodsList) {
					
					String shopId=collectionDo.getShopId();
					String productNo=collectionDo.getProductNo();
					
					ProductDO productDo=new ProductDO();
					//查询商品详细信息
					ProductDO product = productDao.selectShopCartProduct(shopId, productNo);
					
					productDo.setProductName(product.getProductName());
					productDo.setPrice(product.getPrice());
					productDo.setProductUrl(product.getProductUrl());
					productDo.setProductNo(productNo);
					productDo.setShopId(shopId);
					
					productList.add(productDo);
                    }
				
				
				    //查询所有店铺收藏信息
				    List<CollectionDO> shopsList = collectionDao.selectCollectionInfoByUserIdAndTypeAndStatus(userId, "1", "0", null, null);
                    for (CollectionDO collectionDo : shopsList) {
					
					String shopId=collectionDo.getShopId();
					
					ShopDO shopDo=new ShopDO();
					//查询店铺详细信息
					ShopDO shop = shopDAO.selectShopInfoByShopId(shopId);
					
					//查询店铺宝贝数
					int goodsCount = productDao.selectGoodsCountByShopId(shopId);
					
					shopDo.setShopId(shopId);
					shopDo.setShopName(shop.getShopName());
					shopDo.setShopUrl(shop.getShopUrl());
					shopDo.setSellCount(goodsCount);
					
					shopList.add(shopDo);
                    }
		      modelMap.put("productList", productList);
		      modelMap.put("shopList", shopList);
		      return "html/collect";
          }
          return "html/collect_no";
	}
	/**
	 * 获取我的收藏列表
	 * 
	 * @param request
	 * @param userId     用户Id
	 * @param type       收藏类型（0 宝贝收藏/默认/，1 店铺收藏）
	 * @param status     状态  0:关注 1:取消
	 * @return
	 */
//	@RequestMapping("/collectionList.do")
//	public Object collectionList(final HttpServletRequest request,final ModelMap modelMap) {
//		//String userId = (String) request.getSession().getAttribute("userId");
//		final String userId = "13252";
//		final String type=request.getParameter("type");
//		final Integer pageNum = adjustPageNo(request);
//		final Integer pageSize = adjustPageSize(request);
//		
//				// 查询记录数,总页数
//				Integer startRow = (pageNum - 1) * pageSize;
//				
//				int totalItem = collectionDao.selectCollectionCountByUserIdAndTypeAndStatus(userId, type, "0");
//				int totalPages = (totalItem - 1) / pageSize + 1;
//
//				List<CollectionDO> collectionDoList = collectionDao.selectCollectionInfoByUserIdAndTypeAndStatus(userId, type, "0", startRow, pageSize);
//				
//				List<CollectionResult> list=new ArrayList<CollectionResult>();
//				CollectionResult collectionResult=null;
//				for (CollectionDO collectionDo : collectionDoList) {
//					collectionResult= new CollectionResult(true, "", "");
//					
//					//查询商品详细信息
//					ProductDO productDo = productDao.selectShopCartProduct(userId, collectionDo.getShopId(), collectionDo.getProductNo());
//					//查询店铺详细信息
//					ShopDO shopDo = shopDAO.selectShopInfoByShopId(collectionDo.getShopId());
//					
//					//查询店铺宝贝数
//					int goodsCount = productDao.selectGoodsCountByShopId(collectionDo.getShopId());
//					
//					collectionResult.setProductName(productDo.getProductName());
//					collectionResult.setPrice(productDo.getPrice());
//					collectionResult.setShopName(shopDo.getShopName());
//					collectionResult.setGoodsCount(goodsCount);
//					list.add(collectionResult);
//				
//		return "html/collect";
//	}
	
	
	/**
	 * 跳转我的收藏列表
	 * @param request
	 * @param modelMap
	 * @return
	 */
	@RequestMapping("/collection.html")
	public Object collection(final HttpServletRequest request,final ModelMap modelMap) {
        //String userId = (String) request.getSession().getAttribute("userId");
          final String userId ="13252";
          if (StringUtils.isEmpty(userId)) {
			throw new ErrorException("用户未登录");
		  }
  		  //判断
          //收藏宝贝数量
          int productCount = collectionDao.selectCollectionCountByUserIdAndType(userId, "0");
          //收藏店铺数量
          int shopCount = collectionDao.selectCollectionCountByUserIdAndType(userId, "1");
          if(productCount >= 1 || shopCount >= 1){
		      return "html/collect";
          }
          return "html/collect_no";
	}
	

	/**
	 * 宝贝收藏
	 * @param request
	 * @param modelMap
	 * @return
	 */
	@RequestMapping("/collectGoods.do")
	@ResponseBody
	public Object collectionList(final HttpServletRequest request) {
		//String userId = (String) request.getSession().getAttribute("userId");
		final String userId = "13252";
		final Integer pageNum = adjustPageNo(request);
		final Integer pageSize = adjustPageSize(request);
		
		final JsonPageResult<CollectionResult> result=new JsonPageResult<CollectionResult>(false, "", "");
		controllerTemplate.execute(result, new ControllerCallBack() {
				
				@Override
				public void check() {
					
				}
				@Override
				public void executeService() {
					// 查询记录数,总页数
					Integer startRow = (pageNum - 1) * pageSize;
					
					int totalItem = collectionDao.selectCollectionCountByUserIdAndType(userId, "0");
					int totalPages = (totalItem - 1) / pageSize + 1;
					
					List<CollectionDO> collectionList = collectionDao.selectCollectionInfoByUserIdAndTypeAndStatus(userId, "0", "0", startRow, pageSize);
					List<CollectionResult> list=new ArrayList<CollectionResult>();
					CollectionResult collectionResult=null;
					for (CollectionDO collectionDo : collectionList) {
						collectionResult= new CollectionResult(true, "", "");
						
						//查询商品详细信息
						ProductDO productDo = productDao.selectShopCartProduct(collectionDo.getShopId(), collectionDo.getProductNo());
						
						/*//查询店铺详细信息
						ShopDO shopDo = shopDAO.selectShopInfoByShopId(collectionDo.getShopId());*/
						
						/*//查询店铺宝贝数
						int goodsCount = productDao.selectGoodsCountByShopId(collectionDo.getShopId());*/
						
						collectionResult.setProductName(productDo.getProductName());
						collectionResult.setPrice(productDo.getPrice());
						collectionResult.setProductUrl(productDo.getProductUrl());
						
						/*collectionResult.setShopName(shopDo.getShopName());
						collectionResult.setGoodsCount(goodsCount);
						collectionResult.setShopUrl(shopDo.getShopUrl());*/
						
						collectionResult.setProductNo(collectionDo.getProductNo());
						collectionResult.setShopId(collectionDo.getShopId());
						list.add(collectionResult);
	                    }
					result.setListObject(list);
					result.setTotalPages(totalPages);
				}
		});
				
		return result;
	}
	
	/**
	 * 添加收藏
	 * @param request
	 * @param userId        用户ID
	 * @param productNo     商品号 （收藏的是店铺时不需要录入此参数）
	 * @param shopId        店铺ID
	 * @param type          类型  0：商品 1：店铺
	 * @param status        状态  0:关注 1:取消
	 * @return
	 */
	@RequestMapping("/addCollection.do")
	@ResponseBody
	public Object addCollection(HttpServletRequest request)
	{
		//String userId = (String) request.getSession().getAttribute("userId");
		final String userId="13252";
		final String productNo=request.getParameter("productNo");
		final String shopId=request.getParameter("shopId");
		final String type=request.getParameter("type");
		
		final JsonResult jsonResult=new JsonResult(false);
		controllerTemplate.execute(jsonResult, new ControllerCallBack() {
			
			@Override
			public void executeService() {
				CollectionDO collectionDO=new CollectionDO();
				//根据userId, productNo, shopId,查询收藏表(有些事之前收藏过，但是status=“1”,这个时候update)，判断
				CollectionDO checkCollection = collectionDao.selectCollectionInfoByUserIdAndProductNoAndShopId(userId, productNo, shopId);
				
				if(null == checkCollection)
				{
				   collectionDO.setUserId(userId);
				   if(StringUtils.equals("0", type)){
				      collectionDO.setProductNo(productNo);
				   }
				   collectionDO.setShopId(shopId);
				   collectionDO.setType(type);
				   collectionDO.setStatus("0");
				
				   int result = collectionDao.addCollection(collectionDO);
				
				   if(result >= 0)
				   {
					jsonResult.setBizSucc(true);
				   }
				}
				else
				{
					if(StringUtils.equals("1", checkCollection.getStatus()))
					{
						collectionDO.setUserId(userId);
						collectionDO.setProductNo(productNo);
						collectionDO.setShopId(shopId);
						collectionDao.changeShopCollectionStatus(collectionDO);
						jsonResult.setBizSucc(true);
					}
					else
					{
						jsonResult.setErrMsg("已存在");
					}
				}
			}
			
			@Override
			public void check() {
				AssertUtil.notBlank(userId, "参数为空：userId");
				//AssertUtil.notBlank(productNo, "参数为空：productNo");
				AssertUtil.notBlank(shopId, "参数为空：shopId");
			}
		});
		return jsonResult;	
	}
	
	
	/**
	 * 取消宝贝收藏信息
	 * @param request
	 * @param userId        用户ID
	 * @param productNo     商品号
	 * @param shopId        店铺Id
	 * @param status        状态  0:关注 1:取消      
	 * @return
	 */
	@RequestMapping("/removeGoodsColl.do")
	@ResponseBody
	public Object removeGoodsColl(HttpServletRequest request)
	{
		//String userId = (String) request.getSession().getAttribute("userId");
		final String userId="13252";
		final String productNo=request.getParameter("productNo");
		final String shopId=request.getParameter("shopId");
		
		final JsonResult jsonResult=new JsonResult(false);
		controllerTemplate.execute(jsonResult, new ControllerCallBack() {
			
			@Override
			public void executeService() {
				CollectionDO collectionDo=new CollectionDO();
				collectionDo.setUserId(userId);
				if (StringUtils.isEmpty(productNo)) {
					collectionDo.setProductNo("");
				}
				collectionDo.setProductNo(productNo);
				collectionDo.setShopId(shopId);
				int result = collectionDao.changeGoodsCollectionStatus(collectionDo);
				
				if(result >= 1 )
				{
					jsonResult.setBizSucc(true);
				}	
			}
			
			@Override
			public void check() {
				AssertUtil.notBlank(userId, "参数为空：userId");
				AssertUtil.notBlank(productNo, "参数为空：productNo");
				AssertUtil.notBlank(shopId, "参数为空：shopId");
			}
		});
		return jsonResult;	
	}
	
	/**
	 * 取消店铺收藏信息
	 * @param request
	 * @param userId        用户ID
	 * @param productNo     商品号
	 * @param shopId        店铺Id
	 * @param status        状态  0:关注 1:取消      
	 * @return
	 */
	@RequestMapping("/removeShopColl.do")
	@ResponseBody
	public Object removeShopColl(HttpServletRequest request)
	{
		//String userId = (String) request.getSession().getAttribute("userId");
		final String userId="13252";
		final String shopId=request.getParameter("shopId");
		
		final JsonResult jsonResult=new JsonResult(false);
		controllerTemplate.execute(jsonResult, new ControllerCallBack() {
			
			@Override
			public void executeService() {
				CollectionDO collectionDo=new CollectionDO();
				collectionDo.setUserId(userId);
				collectionDo.setShopId(shopId);
				int result = collectionDao.changeShopCollectionStatus(collectionDo);
				
				if(result >= 1 )
				{
					jsonResult.setBizSucc(true);
				}	
			}
			
			@Override
			public void check() {
				AssertUtil.notBlank(userId, "参数为空：userId");
				AssertUtil.notBlank(shopId, "参数为空：shopId");
			}
		});
		return jsonResult;	
	}
}
