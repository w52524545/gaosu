package com.onway.web.controller.product;

import java.math.BigDecimal;
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
import com.onway.makeploy.common.service.exception.ErrorException;
import com.onway.platform.common.service.util.AssertUtil;
import com.onway.web.controller.AbstractController;
import com.onway.web.controller.result.ProductResult;
import com.onway.web.controller.template.ControllerCallBack;

/**
 * 我的商品
 * @author Administrator
 *
 */
@Controller
public class ProductController extends AbstractController {

    /**
     * 跳转我的商铺
     * @param request
     * @param modelMap
     * @return
     */
    @RequestMapping("/myProduct.html")
    public Object productList(final HttpServletRequest request, final ModelMap modelMap) {
        //        String userId = (String) request.getSession().getAttribute("userId");
        String userId = "13252";
        if (StringUtils.isEmpty(userId)) {
            throw new ErrorException("用户未登录");
        }

        List<ProductDO> productList = productDao.selectProductByUserId(userId, null, null);

        List<ProductResult> list = new ArrayList<ProductResult>();
        ProductResult productResult = null;
        for (ProductDO prod : productList) {
            if (prod.getShopId() != null && !prod.getDelFlg().equals("1")) {
                productResult = new ProductResult(true, "", "");
                productResult.setUserId(prod.getUserId());
                productResult.setId(prod.getId());
                productResult.setShopId(prod.getShopId());
                productResult.setStock(prod.getStock());
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
        modelMap.put("product", list);
        return "html/wares";
    }

    /**
     * 用户查询商品详细信息
     */
    /*	@RequestMapping("/queryProductDetail.do")
    	@ResponseBody
    	public Object queryProductDetail( HttpServletRequest request) {
    		
    		final String productNo = request.getParameter("productNo");

    		final ProductResult result = new ProductResult(true, "", "");
    		controllerTemplate.execute(result, new ControllerCallBack() {
    			@Override
    			public void check() {
    				AssertUtil.notBlank(productNo, "商品号为空");
    			}

    			@Override
    			public void executeService() {
    				// 根据商品编号查询商品的详细信息
    				ProductDO productDO = productDao.selectProductByProductNo(productNo,shopId);
    				if (null == productDO) {
    					result.setErrMsg("商品信息查询失败，没有该条记录");
    					result.setBizSucc(false);
    				}
                    result.setUserId(productDO.getUserId());
                    result.setId(productDO.getId());
                    result.setShopId(productDO.getShopId());
                    result.setStock(productDO.getStock());
                    result.setSoleCount(productDO.getSoleCount());
                    result.setDelFlg(productDO.getDelFlg());
                    result.setOldPrice(productDO.getOldPrice());
                    result.setPrice(productDO.getPrice());
                    result.setProductName(productDO.getProductName());
                    result.setProductNo(productDO.getProductNo());
                    result.setProductUrl(productDO.getProductUrl());
                    result.setProductType(productDO.getProductType());
                    result.setStatus(productDO.getStatus());
                    result.setRecommendFlg(productDO.getRecommendFlg());
    			}
    		});
    		return result;
    	}*/

    /**
     * 删除商品
     * 
     * @param request
     * @return
     */
    @RequestMapping("/deleteProduct.do")
    @ResponseBody
    public Object deleteProduct(HttpServletRequest request) {
        // final String userId = request.(String)
        // request.getSession().getAttribute("userId");
        final String userId = "13252";
        final String productNo = request.getParameter("productNo");

        final ProductResult result = new ProductResult(true, "", "");

        controllerTemplate.execute(result, new ControllerCallBack() {

            @Override
            public void check() {
                AssertUtil.notBlank(userId, "userId不能为空");
                AssertUtil.notBlank(productNo, "productNo不能为空");
            }

            @Override
            public void executeService() {
                String[] productno = productNo.split(",");
                int countLine = 0;
                for (int i = 0; i < productno.length; i++) {
                    String product = productno[i];
                    if (!StringUtils.isEmpty(product)) {
                        countLine += productDao.deleteProductDelFlg2Zero(userId, product);
                    }
                }
                if (countLine <= 0) {
                    throw new ErrorException("删除商品失败！");
                }
                result.setUserId(userId);
                result.setProductNo(productNo);
                result.setProductNo(productNo);
                result.setDelFlg("1");
            }
        });
        return result;
    }

    /**
     *  商品下架
     * @param request
     * @return
     */
    @RequestMapping("/offShelfProduct.do")
    @ResponseBody
    public Object offShelfProduct(HttpServletRequest request) {
        //final String userId = (String) request.getSession().getAttribute("userId");
        final String userId = "13252";
        final String productNo = request.getParameter("productNo");
        final String status = request.getParameter("status");//需要变成改状态

        final ProductResult result = new ProductResult(true, "", "");

        controllerTemplate.execute(result, new ControllerCallBack() {

            @Override
            public void check() {
                AssertUtil.notBlank(userId, "userId不能为空");
                AssertUtil.notBlank(productNo, "productNo不能为空");
                AssertUtil.notBlank(status, "商品上下架信息不能为空");
            }

            @Override
            public void executeService() {
                String[] productno = productNo.split(",");
                int countLine = 0;
                for (int i = 0; i < productno.length; i++) {
                    String product = productno[i];
                    if (!StringUtils.isEmpty(product)) {
                        countLine += productDao.OffTheShelfProduct(status, userId, product);
                    }
                }
                if (countLine <= 0) {
                    throw new ErrorException("商品上下架信息更新失败！");
                }
                result.setUserId(userId);
                result.setProductNo(productNo);
                if ("1".equals(status)) {
                    result.setStatus("0");
                } else {
                    result.setStatus("1");
                }
            }
        });
        return result;
    }

    /**
     *      商品数量更新
     * @param request
     * @return
     */
    @RequestMapping("/updateProductCount.do")
    @ResponseBody
    public Object updateProductCount(HttpServletRequest request) {
        //final String userId = request.(String) request.getSession().getAttribute("userId");
        final String userId = "13252";
        final String productNo = request.getParameter("productNo");
        final int count = Integer.parseInt(request.getParameter("count"));
        final ProductResult result = new ProductResult(true, "", "");
        controllerTemplate.execute(result, new ControllerCallBack() {

            @Override
            public void check() {
                AssertUtil.notBlank(userId, "userId不能为空");
                AssertUtil.notBlank(productNo, "productNo不能为空");
            }

            @Override
            public void executeService() {
                int countLine = productDao.updateProductCount(userId, count, productNo);
                if (countLine != 1) {
                    throw new ErrorException("商品数量信息更新失败！");
                }
                result.setUserId(userId);
                result.setProductNo(productNo);
                result.setStock(count);
            }
        });
        return result;
    }

    /**
     * 产品价格更新
     * @param request
     * @return
     */
    @RequestMapping("/updateProductPrice.do")
    @ResponseBody
    public Object updateProductPrice(HttpServletRequest request) {
        //final String userId = request.(String) request.getSession().getAttribute("userId");
        final String userId = "13252";
        final String shopId = request.getParameter("shopId");
        final String productNo = request.getParameter("productNo");
        final Money price = new Money(new BigDecimal(request.getParameter("price")));

        final ProductResult result = new ProductResult(true, "", "");
        controllerTemplate.execute(result, new ControllerCallBack() {

            @Override
            public void check() {
                AssertUtil.notBlank(userId, "userId不能为空");
                AssertUtil.notBlank(shopId, "shopId不能为空");
                AssertUtil.notBlank(productNo, "productNo不能为空");
            }

            @Override
            public void executeService() {
                int countLine = productDao.updateProductPrice(price, shopId, userId, productNo);
                if (countLine != 1) {
                    throw new ErrorException("商品数量信息更新失败！");
                }
                result.setUserId(userId);
                result.setProductNo(productNo);
                result.setPrice(price);
            }
        });
        return result;
    }

}
