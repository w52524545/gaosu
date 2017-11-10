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
	 * ��ת��ҳ
	 * @param request
	 * @param modelMap
	 * @return
	 */
	@RequestMapping("/index.html")
	public Object index(final HttpServletRequest request,final ModelMap modelMap){
	 
		 //��Ʒչʾ
		 List<ProductDO> productList = productDao.selectNewProduct();
         List<HomeProductResult> list = new ArrayList<HomeProductResult>();
         
         for( ProductDO prod : productList ) {
        	     HomeProductResult homeProductResult = new HomeProductResult();
        	     homeProductResult.setShopId(prod.getShopId());
        	     homeProductResult.setStock(prod.getStock());
        	     homeProductResult.setDelFlg(prod.getDelFlg());
        	     //ԭ��
        	     homeProductResult.setOldPrice(prod.getOldPrice());
        	     //�ۿ�productOff���ּ�Price��OldPrice*productOff����
                 double productoff=prod.getProductOff();
                 Money price=prod.getOldPrice().multiply(productoff);
                 homeProductResult.setPrice(price);
                 
                 int off=(int)(productoff*100);
                 homeProductResult.setProductOff(off);
                 
                 homeProductResult.setProductName(prod.getProductName());
                 homeProductResult.setProductNo(prod.getProductNo());
                 homeProductResult.setProductUrl(prod.getProductUrl());
                 //��Ʒ����
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
	 * ��ҳ�����̼�չʾ��limit 0,3��
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

				//�ж��Ƿ��������̼�
				int unionShopCount = shopDAO.selectUnionShopCount();
				if(unionShopCount<=0){
					result.setBizSucc(false);
					result.setErrMsg("��û�������̼�");
					throw new ErrorException("��û�������̼�");
				}
				//����ǰ��
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
	 * ��ҳ��Ʒչʾ     ����������ûʹ�ã���������
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

				//�ж��Ƿ��������̼�
				int newProductCount = productDao.selectNewProductCount();
				if(newProductCount<=0){
					result.setBizSucc(false);
					result.setErrMsg("��û����Ʒ�Ƽ�");
					throw new ErrorException("��û����Ʒ�Ƽ�");
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
	 * ��ҳ��ƴ��չʾ
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

				//�ж��Ƿ��а�ƴ����Ʒ
				int teamGoProductCount = productDao.selectTeamGoProductCount();
				if(teamGoProductCount<=0){
					result.setBizSucc(false);
					result.setErrMsg("��û�а�ƴ���Ƽ�");
					throw new ErrorException("��û�а�ƴ���Ƽ�");
				}
				
				//�������
				List<ProductDO> productDoList = productDao.selectTeamGoProduct();
				List<HomeProductResult> list=new ArrayList<HomeProductResult>();
				
				 for( ProductDO prod : productDoList ) {
					 HomeProductResult homeProductResult=new HomeProductResult();
					 homeProductResult.setShopId(prod.getShopId());
					 homeProductResult.setStock(prod.getStock());
					 homeProductResult.setDelFlg(prod.getDelFlg());
					 
					 //ԭ��
					 homeProductResult.setOldPrice(prod.getOldPrice());
	                 
	                 //�ۿ�productOff���ּ�Price��OldPrice*productOff����
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
	                 
	                 //�Ź�������ҳ��չʾ
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
	 * ���మƴ��չʾ
	 * @param request
	 * @param modelMap
	 * @return
	 */
	@RequestMapping("/group.html")
	public Object moreTeamGo(final HttpServletRequest request,final ModelMap modelMap){
	
		final Integer pageNum = adjustPageNo(request);
		final Integer pageSize = adjustPageSize(request);
		
		//�ж��Ƿ��а�ƴ����Ʒ
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
                
       	     homeProductResult.setOldPrice(prod.getOldPrice());//ԭ��
                
       	        //�ۿ�productOff���ּ�Price��OldPrice*productOff����
                double productoff=prod.getProductOff();
                Money price=prod.getOldPrice().multiply(productoff);
                homeProductResult.setPrice(price);//�ּ� 
                
                int off=(int)(productoff*100);
                homeProductResult.setProductOff(off);
                
                homeProductResult.setProductName(prod.getProductName());
                homeProductResult.setProductNo(prod.getProductNo());
                homeProductResult.setProductUrl(prod.getProductUrl());
                
                homeProductResult.setProductType(prod.getProductType());//��Ʒ����
                homeProductResult.setStatus(prod.getStatus());
                homeProductResult.setRecommendFlg(prod.getRecommendFlg());
                
                list.add(homeProductResult);
            
        }
		modelMap.put("TeamGo", productList);
		return "html/group";
	}
	
	/**
	 * ��ҳbannerչʾ
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
//				//�ж��Ƿ��������̼�
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
	 * ���������̼�չʾ
	 * @param request
	 * @param modelMap
	 * @return
	 */
	@RequestMapping("/store.html")
	public Object selectUnionShop(final HttpServletRequest request,final ModelMap modelMap){
	
		final Integer pageNum = adjustPageNo(request);
		final Integer pageSize = adjustPageSize(request);
		
		//�ж��Ƿ��������̼�
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
	 * �û��Ź���Ʒ��ϸ��Ϣ
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
			result.setErrMsg("��Ʒ��Ϣ��ѯʧ�ܣ�û�и�����¼");
			result.setBizSucc(false);
		}
		//������Ϣ
		ShopDO shopDo = shopDAO.selectShopInfoByShopId(shopId);
		
		//����Ʒ��������
		int commetCount = productCommentDAO.selectCommentCountByShopIdAndProductNo(productNo, shopId);
		
		if (commetCount >= 1) {
			// ��ѯ����Ʒ����һ��������Ϣ
			ProductCommentDO lastComment = productCommentDAO.selectNewProductComment(shopId, productNo);
			// ��ѯ��������Ϣ
			String commentUserId = lastComment.getUserId();
			UserDO commentUser = userDao.selectUserInfoByUserId(commentUserId);
			result.setComments(lastComment.getComments());
			result.setCommentsCount(commetCount);// ������
			result.setCommentUserNum(commentUser.getUserNum());// �����߻�Ա���
			result.setCommentUserUrl(commentUser.getHeadUrl());// ������ͷ��
		}
		
		List<Clothing> clothingList = new ArrayList<Clothing>();
		//productNo = productShow.getProductNo();
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
		
		result.setShopId(shopId);
		result.setShopName(shopDo.getShopName());
		result.setShopUrl(shopDo.getShopUrl());
		result.setStock(productDO.getStock());//���
		result.setSoleCount(productDO.getSoleCount());
		result.setDelFlg(productDO.getDelFlg());
		result.setOldPrice(productDO.getOldPrice());
		
		//�ۿ�productOff���ּ�Price��OldPrice*productOff����
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
	 * �������  �����Ź�
	 */
	@RequestMapping("/join.html")
	public Object join(final HttpServletRequest request,final ModelMap modelMap) {
		
		final String productNo = request.getParameter("productNo");
		final String shopId = request.getParameter("shopId");
		
	    final ProductResult result = new ProductResult(true, "", "");
		
		ProductDO productDO = productDao.selectShopCartProduct(shopId,
				productNo);
		if (null == productDO) {
			result.setErrMsg("��Ʒ��Ϣ��ѯʧ�ܣ�û�и�����¼");
			result.setBizSucc(false);
		}
		
		//�ۿ�productOff���ּ�Price��OldPrice*productOff����
        double productoff=productDO.getProductOff();
        Money price=productDO.getOldPrice().multiply(productoff); 
        
        int off=(int)(productoff*100);
        
		result.setPrice(price);
		result.setStock(productDO.getStock());//���
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
				if(productParameterDO.getFatherName().equals("��ɫ")){
					clothing.setColor(productParameterDO.getChildrenName());
				}
				if(productParameterDO.getFatherName().equals("�ߴ�")){
					clothing.setDimensions(productParameterDO.getChildrenName());
				}
				clothingList.add(clothing);
			}
		
		modelMap.put("clothingList", clothingList);
		modelMap.put("result", result);
		return "html/join";
	}
	
	/**
	 * ��������Ǽ�
	 * @param shopId
	 * @return
	 */
	public int shopStar(String shopId){
		//����shop������������shopId�����ۼ����ܺ�
		int allStarCount = shopCommentDAO.selectAllStarCount(shopId);
		int count = shopCommentDAO.selectShopCommentCount(shopId);
		if( 0 >= count){
			return 0;
		}
		//����ƽ��ֵ
		int shopStar=allStarCount/count;
		return shopStar;
	}
}
