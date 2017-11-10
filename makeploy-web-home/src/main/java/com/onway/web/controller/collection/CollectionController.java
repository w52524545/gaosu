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
 * �ҵ��ղ�
 * @author Administrator
 *
 */
@Controller
public class CollectionController extends AbstractController{

	/**
	 * ��ת�ҵ��ղ��б�
	 * @param request
	 * @param modelMap
	 * @return
	 */
	@RequestMapping("/myCollection.html")
	public Object myCollection(final HttpServletRequest request,final ModelMap modelMap) {
        //String userId = (String) request.getSession().getAttribute("userId");
          final String userId ="13252";
          if (StringUtils.isEmpty(userId)) {
			throw new ErrorException("�û�δ��¼");
		  }
          //final String type=request.getParameter("type");
  		  //final Integer pageNum = adjustPageNo(request);
  		  //final Integer pageSize = adjustPageSize(request);
  		  
  		  //userId���е��ղ�
          int count = collectionDao.selectCollectionCount(userId);
          if(count >= 1){
        	    //��ѯ��¼��,��ҳ��
				//Integer startRow = (pageNum - 1) * pageSize;
        	  
				//int totalPages = (totalItem - 1) / pageSize + 1;

        	    //��ѯ���б����ղ���Ϣ
				List<CollectionDO> goodsList = collectionDao.selectCollectionInfoByUserIdAndTypeAndStatus(userId, "0", "0", null, null);
				
				
				List<ProductDO> productList=new ArrayList<ProductDO>();
				List<ShopDO> shopList=new ArrayList<ShopDO>();
				for (CollectionDO collectionDo : goodsList) {
					
					String shopId=collectionDo.getShopId();
					String productNo=collectionDo.getProductNo();
					
					ProductDO productDo=new ProductDO();
					//��ѯ��Ʒ��ϸ��Ϣ
					ProductDO product = productDao.selectShopCartProduct(shopId, productNo);
					
					productDo.setProductName(product.getProductName());
					productDo.setPrice(product.getPrice());
					productDo.setProductUrl(product.getProductUrl());
					productDo.setProductNo(productNo);
					productDo.setShopId(shopId);
					
					productList.add(productDo);
                    }
				
				
				    //��ѯ���е����ղ���Ϣ
				    List<CollectionDO> shopsList = collectionDao.selectCollectionInfoByUserIdAndTypeAndStatus(userId, "1", "0", null, null);
                    for (CollectionDO collectionDo : shopsList) {
					
					String shopId=collectionDo.getShopId();
					
					ShopDO shopDo=new ShopDO();
					//��ѯ������ϸ��Ϣ
					ShopDO shop = shopDAO.selectShopInfoByShopId(shopId);
					
					//��ѯ���̱�����
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
	 * ��ȡ�ҵ��ղ��б�
	 * 
	 * @param request
	 * @param userId     �û�Id
	 * @param type       �ղ����ͣ�0 �����ղ�/Ĭ��/��1 �����ղأ�
	 * @param status     ״̬  0:��ע 1:ȡ��
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
//				// ��ѯ��¼��,��ҳ��
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
//					//��ѯ��Ʒ��ϸ��Ϣ
//					ProductDO productDo = productDao.selectShopCartProduct(userId, collectionDo.getShopId(), collectionDo.getProductNo());
//					//��ѯ������ϸ��Ϣ
//					ShopDO shopDo = shopDAO.selectShopInfoByShopId(collectionDo.getShopId());
//					
//					//��ѯ���̱�����
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
	 * ��ת�ҵ��ղ��б�
	 * @param request
	 * @param modelMap
	 * @return
	 */
	@RequestMapping("/collection.html")
	public Object collection(final HttpServletRequest request,final ModelMap modelMap) {
        //String userId = (String) request.getSession().getAttribute("userId");
          final String userId ="13252";
          if (StringUtils.isEmpty(userId)) {
			throw new ErrorException("�û�δ��¼");
		  }
  		  //�ж�
          //�ղر�������
          int productCount = collectionDao.selectCollectionCountByUserIdAndType(userId, "0");
          //�ղص�������
          int shopCount = collectionDao.selectCollectionCountByUserIdAndType(userId, "1");
          if(productCount >= 1 || shopCount >= 1){
		      return "html/collect";
          }
          return "html/collect_no";
	}
	

	/**
	 * �����ղ�
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
					// ��ѯ��¼��,��ҳ��
					Integer startRow = (pageNum - 1) * pageSize;
					
					int totalItem = collectionDao.selectCollectionCountByUserIdAndType(userId, "0");
					int totalPages = (totalItem - 1) / pageSize + 1;
					
					List<CollectionDO> collectionList = collectionDao.selectCollectionInfoByUserIdAndTypeAndStatus(userId, "0", "0", startRow, pageSize);
					List<CollectionResult> list=new ArrayList<CollectionResult>();
					CollectionResult collectionResult=null;
					for (CollectionDO collectionDo : collectionList) {
						collectionResult= new CollectionResult(true, "", "");
						
						//��ѯ��Ʒ��ϸ��Ϣ
						ProductDO productDo = productDao.selectShopCartProduct(collectionDo.getShopId(), collectionDo.getProductNo());
						
						/*//��ѯ������ϸ��Ϣ
						ShopDO shopDo = shopDAO.selectShopInfoByShopId(collectionDo.getShopId());*/
						
						/*//��ѯ���̱�����
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
	 * ����ղ�
	 * @param request
	 * @param userId        �û�ID
	 * @param productNo     ��Ʒ�� ���ղص��ǵ���ʱ����Ҫ¼��˲�����
	 * @param shopId        ����ID
	 * @param type          ����  0����Ʒ 1������
	 * @param status        ״̬  0:��ע 1:ȡ��
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
				//����userId, productNo, shopId,��ѯ�ղر�(��Щ��֮ǰ�ղع�������status=��1��,���ʱ��update)���ж�
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
						jsonResult.setErrMsg("�Ѵ���");
					}
				}
			}
			
			@Override
			public void check() {
				AssertUtil.notBlank(userId, "����Ϊ�գ�userId");
				//AssertUtil.notBlank(productNo, "����Ϊ�գ�productNo");
				AssertUtil.notBlank(shopId, "����Ϊ�գ�shopId");
			}
		});
		return jsonResult;	
	}
	
	
	/**
	 * ȡ�������ղ���Ϣ
	 * @param request
	 * @param userId        �û�ID
	 * @param productNo     ��Ʒ��
	 * @param shopId        ����Id
	 * @param status        ״̬  0:��ע 1:ȡ��      
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
				AssertUtil.notBlank(userId, "����Ϊ�գ�userId");
				AssertUtil.notBlank(productNo, "����Ϊ�գ�productNo");
				AssertUtil.notBlank(shopId, "����Ϊ�գ�shopId");
			}
		});
		return jsonResult;	
	}
	
	/**
	 * ȡ�������ղ���Ϣ
	 * @param request
	 * @param userId        �û�ID
	 * @param productNo     ��Ʒ��
	 * @param shopId        ����Id
	 * @param status        ״̬  0:��ע 1:ȡ��      
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
				AssertUtil.notBlank(userId, "����Ϊ�գ�userId");
				AssertUtil.notBlank(shopId, "����Ϊ�գ�shopId");
			}
		});
		return jsonResult;	
	}
}
