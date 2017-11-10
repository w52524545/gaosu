package com.onway.web.controller.home;

import java.util.ArrayList;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import com.onway.common.lang.Money;
import com.onway.makeploy.common.dal.dataobject.Clothing;
import com.onway.makeploy.common.dal.dataobject.ProductCommentDO;
import com.onway.makeploy.common.dal.dataobject.ProductDO;
import com.onway.makeploy.common.dal.dataobject.ProductParameterDO;
import com.onway.makeploy.common.dal.dataobject.ShopDO;
import com.onway.makeploy.common.dal.dataobject.UserDO;
import com.onway.makeploy.common.service.exception.ErrorException;
import com.onway.web.controller.AbstractController;
import com.onway.web.controller.result.HomeProductResult;
import com.onway.web.controller.result.JsonPageResult;
import com.onway.web.controller.result.MyShopResult;
import com.onway.web.controller.result.ProductResult;
import com.onway.web.controller.template.ControllerCallBack;

@Controller
public class HomeController extends AbstractController{

	
	/**
	 * 跳转首页
	 * @param request
	 * @param modelMap
	 * @return
	 */
	@RequestMapping("/index.html")
	public Object index(final HttpServletRequest request,final ModelMap modelMap){
	 
		 //新品展示
		 List<ProductDO> productList = productDao.selectNewProduct();
         List<HomeProductResult> list = new ArrayList<HomeProductResult>();
         
         for( ProductDO prod : productList ) {
        	     HomeProductResult homeProductResult = new HomeProductResult();
        	     homeProductResult.setShopId(prod.getShopId());
        	     homeProductResult.setStock(prod.getStock());
        	     homeProductResult.setDelFlg(prod.getDelFlg());
        	     //原价
        	     homeProductResult.setOldPrice(prod.getOldPrice());
        	     //折扣productOff，现价Price由OldPrice*productOff计算
                 double productoff=prod.getProductOff();
                 Money price=prod.getOldPrice().multiply(productoff);
                 homeProductResult.setPrice(price);
                 
                 int off=(int)(productoff*100);
                 homeProductResult.setProductOff(off);
                 
                 homeProductResult.setProductName(prod.getProductName());
                 homeProductResult.setProductNo(prod.getProductNo());
                 homeProductResult.setProductUrl(prod.getProductUrl());
                 //商品类型
                 homeProductResult.setProductType(prod.getProductType());
                 homeProductResult.setStatus(prod.getStatus());
                 homeProductResult.setRecommendFlg(prod.getRecommendFlg());
                 
                 list.add(homeProductResult); 
         }
         
//         List<CategoryDO> categoryList=categoryDAO.selectAllFather();
//         modelMap.put("categoryList",categoryList);
         
         modelMap.put("newProduct",list);
		return "html/index";
	}
	
	/**
	 * 首页联盟商家展示（limit 0,3）
	 * @param request
	 * @param modelMap
	 * @return
	 */
	@RequestMapping("/selectUnionShopHome.do")
	@ResponseBody
	public Object selectUnionShopHome(final HttpServletRequest request){
	
		final JsonPageResult<ShopDO> result = new JsonPageResult<ShopDO>(true);
		controllerTemplate.execute(result, new ControllerCallBack() {

			@Override
			public void executeService() {

				//判断是否有联盟商家
				int unionShopCount = shopDAO.selectUnionShopCount();
				if(unionShopCount<=0){
					result.setBizSucc(false);
					result.setErrMsg("还没有联盟商家");
					throw new ErrorException("还没有联盟商家");
				}
				//倒序前三
				List<ShopDO> shopDoList = shopDAO.selectUnionShop(0,3);
				List<ShopDO> list=new ArrayList<ShopDO>();
				for (ShopDO shopDO : shopDoList) {
					ShopDO shop=new ShopDO();
					int shopStar = shopStar(shopDO.getShopId());
					shop.setShopStar(shopStar);
					shop.setShopName(shopDO.getShopName());
					shop.setShopId(shopDO.getShopId());
					shop.setShopUrl(shopDO.getShopUrl());
					shop.setShopAddr(shopDO.getShopAddr());
					shop.setSellCount(shopDO.getSellCount());
					list.add(shop);
				}
				result.setListObject(list);
			}

			@Override
			public void check() {
			}
		});
		return result;
	}
	
	/**
	 * 首页新品展示     ！！！！！没使用！！！！！
	 * @param request
	 * @param modelMap
	 * @return
	 */
	@RequestMapping("/selectNewProductHome.do")
	@ResponseBody
	public Object selectNewProductHome(final HttpServletRequest request){
	
		final JsonPageResult<ProductDO> result = new JsonPageResult<ProductDO>(true);
		controllerTemplate.execute(result, new ControllerCallBack() {

			@Override
			public void executeService() {

				//判断是否有联盟商家
				int newProductCount = productDao.selectNewProductCount();
				if(newProductCount<=0){
					result.setBizSucc(false);
					result.setErrMsg("还没有新品推荐");
					throw new ErrorException("还没有新品推荐");
				}
				
				List<ProductDO> productDoList = productDao.selectNewProduct();
				result.setListObject(productDoList);
			}

			@Override
			public void check() {
			}
		});
		return result;
	}
	
	/**
	 * 首页爱拼团展示
	 * @param request
	 * @param modelMap
	 * @return
	 */
	@RequestMapping("/selectTeamGoProduct.do")
	@ResponseBody
	public Object selectTeamGoProduct(final HttpServletRequest request){
	
		final JsonPageResult<HomeProductResult> result = new JsonPageResult<HomeProductResult>(true);
		controllerTemplate.execute(result, new ControllerCallBack() {

			@Override
			public void executeService() {

				//判断是否有爱拼团商品
				int teamGoProductCount = productDao.selectTeamGoProductCount();
				if(teamGoProductCount<=0){
					result.setBizSucc(false);
					result.setErrMsg("还没有爱拼团推荐");
					throw new ErrorException("还没有爱拼团推荐");
				}
				
				//随机三个
				List<ProductDO> productDoList = productDao.selectTeamGoProduct();
				List<HomeProductResult> list=new ArrayList<HomeProductResult>();
				
				 for( ProductDO prod : productDoList ) {
					 HomeProductResult homeProductResult=new HomeProductResult();
					 homeProductResult.setShopId(prod.getShopId());
					 homeProductResult.setStock(prod.getStock());
					 homeProductResult.setDelFlg(prod.getDelFlg());
					 
					 //原价
					 homeProductResult.setOldPrice(prod.getOldPrice());
	                 
	                 //折扣productOff，现价Price由OldPrice*productOff计算
	                 double productoff=prod.getProductOff();
	                 Money price=prod.getOldPrice().multiply(productoff);
	                 homeProductResult.setPrice(price); 
	                 
	                 int off=(int)(productoff*100);
	                 homeProductResult.setProductOff(off);
	                 
	                 homeProductResult.setProductName(prod.getProductName());
	                 homeProductResult.setProductNo(prod.getProductNo());
	                 homeProductResult.setProductUrl(prod.getProductUrl());
	                 
	                 homeProductResult.setProductType(prod.getProductType());
	                 homeProductResult.setStatus(prod.getStatus());
	                 homeProductResult.setRecommendFlg(prod.getRecommendFlg());
	                 
	                 //团购人数，页面展示
	                 homeProductResult.setNeedPeople(prod.getNeedPeople());
	                 homeProductResult.setNowPeople(prod.getNowPeople());
	                 
	                 list.add(homeProductResult);
	             
	         }
				result.setListObject(list);
			}

			@Override
			public void check() {
			}
		});
		return result;
	}
	
	/**
	 * 更多爱拼团展示
	 * @param request
	 * @param modelMap
	 * @return
	 */
	@RequestMapping("/group.html")
	public Object moreTeamGo(final HttpServletRequest request,final ModelMap modelMap){
	
		final Integer pageNum = adjustPageNo(request);
		final Integer pageSize = adjustPageSize(request);
		
		//判断是否有爱拼团商品
		int teamGoProductCount = productDao.selectTeamGoProductCount();
		if( teamGoProductCount <= 0)
		{
			return "html/index";
		}

		Integer startRow = (pageNum - 1) * pageSize;
		
		List<ProductDO> productList = productDao.selectTeamGo(startRow, pageSize);

        List<HomeProductResult> list = new ArrayList<HomeProductResult>();
        
        for( ProductDO prod : productList ) {
       	     HomeProductResult homeProductResult = new HomeProductResult();
       	     homeProductResult.setShopId(prod.getShopId());
       	     homeProductResult.setStock(prod.getStock());
       	     homeProductResult.setDelFlg(prod.getDelFlg());
                
       	     homeProductResult.setOldPrice(prod.getOldPrice());//原价
                
       	        //折扣productOff，现价Price由OldPrice*productOff计算
                double productoff=prod.getProductOff();
                Money price=prod.getOldPrice().multiply(productoff);
                homeProductResult.setPrice(price);//现价 
                
                int off=(int)(productoff*100);
                homeProductResult.setProductOff(off);
                
                homeProductResult.setProductName(prod.getProductName());
                homeProductResult.setProductNo(prod.getProductNo());
                homeProductResult.setProductUrl(prod.getProductUrl());
                
                homeProductResult.setProductType(prod.getProductType());//商品类型
                homeProductResult.setStatus(prod.getStatus());
                homeProductResult.setRecommendFlg(prod.getRecommendFlg());
                
                list.add(homeProductResult);
            
        }
		modelMap.put("TeamGo", productList);
		return "html/group";
	}
	
	/**
	 * 首页banner展示
	 * @param request
	 * @param modelMap
	 * @return
	 */
	@RequestMapping("/homeBanner.do")
	@ResponseBody
	public Object homeBanner(final HttpServletRequest request){
	
		final JsonPageResult<ProductDO> result = new JsonPageResult<ProductDO>(true);
//		controllerTemplate.execute(result, new ControllerCallBack() {
//
//			@Override
//			public void executeService() {
//
//				//判断是否有联盟商家
//				int newProductCount = productDao.selectNewProductCount();
//				if(newProductCount<=0){
//					result.setBizSucc(false);
//					result.setErrMsg("");
//					throw new ErrorException("");
//				}
//				
//				List<ProductDO> productDoList = productDao.selectNewProduct();
//				result.setListObject(productDoList);
//			}
//
//			@Override
//			public void check() {
//			}
//		});
		return result;
	}
	
	/**
	 * 更多联盟商家展示
	 * @param request
	 * @param modelMap
	 * @return
	 */
	@RequestMapping("/store.html")
	public Object selectUnionShop(final HttpServletRequest request,final ModelMap modelMap){
	
		final Integer pageNum = adjustPageNo(request);
		final Integer pageSize = adjustPageSize(request);
		
		//判断是否有联盟商家
		int unionShopCount = shopDAO.selectUnionShopCount();
		if( unionShopCount <= 0)
		{
			return "html/index";
		}

		Integer startRow = (pageNum - 1) * pageSize;
		
		List<ShopDO> unionShopList = shopDAO.selectUnionShop(startRow, pageSize);

		List<MyShopResult> list = new ArrayList<MyShopResult>();
		MyShopResult myShopResult = null;
		for (ShopDO ShopDO : unionShopList) {
			myShopResult = new MyShopResult(true, "", "");
			myShopResult.setShopName(ShopDO.getShopName());
			myShopResult.setShopUrl(ShopDO.getShopUrl());
			myShopResult.setShopId(ShopDO.getShopId());
			list.add(myShopResult);
		}
		modelMap.put("unionShop", list);
		return "html/store";
	}
	
	/**
	 * 用户团购商品详细信息
	 */
	@RequestMapping("/groupDetail.html")
	public Object groupDetail(final HttpServletRequest request,final ModelMap modelMap) {
		
		
		final String praflag ="";// request.getParameter("praflag");
		
		final String productNo = request.getParameter("productNo");
		final String shopId = request.getParameter("shopId");
		
	    final ProductResult result = new ProductResult(true, "", "");
		
		ProductDO productDO = productDao.selectShopCartProduct(shopId,
				productNo);
		if (null == productDO) {
			result.setErrMsg("商品信息查询失败，没有该条记录");
			result.setBizSucc(false);
		}
		//店铺信息
		ShopDO shopDo = shopDAO.selectShopInfoByShopId(shopId);
		
		//该商品总评价数
		int commetCount = productCommentDAO.selectCommentCountByShopIdAndProductNo(productNo, shopId);
		
		if (commetCount >= 1) {
			// 查询该商品最新一条评论信息
			ProductCommentDO lastComment = productCommentDAO.selectNewProductComment(shopId, productNo);
			// 查询评论者信息
			String commentUserId = lastComment.getUserId();
			UserDO commentUser = userDao.selectUserInfoByUserId(commentUserId);
			result.setComments(lastComment.getComments());
			result.setCommentsCount(commetCount);// 评价数
			result.setCommentUserNum(commentUser.getUserNum());// 评价者会员编号
			result.setCommentUserUrl(commentUser.getHeadUrl());// 评论者头像
		}
		
		List<Clothing> clothingList = new ArrayList<Clothing>();
		//productNo = productShow.getProductNo();
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
		
		result.setShopId(shopId);
		result.setShopName(shopDo.getShopName());
		result.setShopUrl(shopDo.getShopUrl());
		result.setStock(productDO.getStock());//库存
		result.setSoleCount(productDO.getSoleCount());
		result.setDelFlg(productDO.getDelFlg());
		result.setOldPrice(productDO.getOldPrice());
		
		//折扣productOff，现价Price由OldPrice*productOff计算
        double productoff=productDO.getProductOff();
        Money price=productDO.getOldPrice().multiply(productoff); 
        
        int off=(int)(productoff*100);
		result.setPrice(price);
		
		result.setLuggage(productDO.getLuggage());
		result.setProductOff(off);
		result.setProductName(productDO.getProductName());
		result.setProductNo(productNo);
		result.setProductUrl(productDO.getProductUrl());
		result.setProductType(productDO.getProductType());
		result.setStatus(productDO.getStatus());
		result.setRecommendFlg(productDO.getRecommendFlg());
		result.setNeedPeople(productDO.getNeedPeople());
		result.setNowPeople(productDO.getNowPeople());
		
		int leftPeople=productDO.getNeedPeople()-productDO.getNowPeople();
		result.setLeftPeople(leftPeople);

		
		modelMap.put("clothingList", clothingList);

		//result.setCell(shopDo.getCell());
		
		modelMap.put("result", result);
		modelMap.addAttribute("clothingList", clothingList);
		return "html/groupDetail";
	}
	
	/**
	 * 加入该团  加入团购
	 */
	@RequestMapping("/join.html")
	public Object join(final HttpServletRequest request,final ModelMap modelMap) {
		
		final String productNo = request.getParameter("productNo");
		final String shopId = request.getParameter("shopId");
		
	    final ProductResult result = new ProductResult(true, "", "");
		
		ProductDO productDO = productDao.selectShopCartProduct(shopId,
				productNo);
		if (null == productDO) {
			result.setErrMsg("商品信息查询失败，没有该条记录");
			result.setBizSucc(false);
		}
		
		//折扣productOff，现价Price由OldPrice*productOff计算
        double productoff=productDO.getProductOff();
        Money price=productDO.getOldPrice().multiply(productoff); 
        
        int off=(int)(productoff*100);
        
		result.setPrice(price);
		result.setStock(productDO.getStock());//库存
		result.setSoleCount(productDO.getSoleCount());
		result.setDelFlg(productDO.getDelFlg());
		result.setOldPrice(productDO.getOldPrice());
		result.setLuggage(productDO.getLuggage());
		result.setProductOff(off);
		result.setProductName(productDO.getProductName());
		result.setProductNo(productNo);
		result.setProductUrl(productDO.getProductUrl());
		result.setProductType(productDO.getProductType());
		result.setStatus(productDO.getStatus());
		result.setRecommendFlg(productDO.getRecommendFlg());
		result.setNeedPeople(productDO.getNeedPeople());
		result.setNowPeople(productDO.getNowPeople());
		
		int leftPeople=productDO.getNeedPeople()-productDO.getNowPeople();
		result.setLeftPeople(leftPeople);

		//result.setCell(shopDo.getCell());
		List<Clothing> clothingList = new ArrayList<Clothing>();
		//productNo = productShow.getProductNo();
		List<ProductParameterDO> parameterList = productParameterDAO.selectProductParametersById(productNo,"");
		Clothing clothing = new Clothing();
			for(ProductParameterDO productParameterDO : parameterList){
				if(productParameterDO.getFatherName().equals("颜色")){
					clothing.setColor(productParameterDO.getChildrenName());
				}
				if(productParameterDO.getFatherName().equals("尺寸")){
					clothing.setDimensions(productParameterDO.getChildrenName());
				}
				clothingList.add(clothing);
			}
		
		modelMap.put("clothingList", clothingList);
		modelMap.put("result", result);
		return "html/join";
	}
	
	/**
	 * 计算店铺星级
	 * @param shopId
	 * @return
	 */
	public int shopStar(String shopId){
		//计算shop评论里面所有shopId的评价级别总和
		int allStarCount = shopCommentDAO.selectAllStarCount(shopId);
		int count = shopCommentDAO.selectShopCommentCount(shopId);
		if( 0 >= count){
			return 0;
		}
		//计算平均值
		int shopStar=allStarCount/count;
		return shopStar;
	}
}
