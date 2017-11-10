package com.onway.web.controller.mng;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.onway.makeploy.common.dal.dataobject.ProductDO;
import com.onway.web.controller.AbstractController;
import com.onway.web.controller.result.JsonResult;

@Controller
public class JoinParenterProductController extends AbstractController {
    @RequestMapping("partnerMng.html")
    public String parenterJoin(HttpServletRequest request, ModelMap map) {
    	String userId = (String)request.getSession().getAttribute("userId");
    	boolean checkRoleBoss = checkRoleBoss(userId);
    	if(checkRoleBoss == false){
    		return "mng/loginMng";
    	}
    	//判断是否为运营者
        int checkOperative = checkOperative(userId);
        map.put("checkOperative", checkOperative);
        return "mng/partnerMng";
    }

    @RequestMapping("searchProduct.do")
    public Object searPro(HttpServletRequest request, ModelMap map) {
    	String userId = (String)request.getSession().getAttribute("userId");
    	boolean checkRoleBoss = checkRoleBoss(userId);
    	if(checkRoleBoss == false){
    		return "mng/loginMng";
    	}
    	//判断是否为运营者
        int checkOperative = checkOperative(userId);
        map.put("checkOperative", checkOperative);
    	
        String shopId = request.getParameter("shopId");
        String flg = request.getParameter("selectedNew");
        String productNo = request.getParameter("productNo");
        String proName = request.getParameter("proName");
        if ("1".equals(flg)) {
            int totalItems = (int) productDao.countRecommendFlg(shopId, productNo, "2", "%"
                                                                                        + proName
                                                                                        + "%");
            Map<String, Integer> pagedate = getPageData(request);
            List<ProductDO> list = productDao.selectByRecommendFlg(shopId, productNo, "2",
                pagedate.get(OFFSET) / 2, pagedate.get(PAGE_SIZE_STR) / 2, "%" + proName + "%");
            map.put("totalPages", calculatePage(totalItems, pagedate.get(PAGE_SIZE_STR) / 2));
            map.put(CURRENTPAGE, pagedate.get(CURRENTPAGE));
            map.put("totalItems", totalItems);
            map.put("productInfo", list);
            map.put("flag", "1");
            request.setAttribute("page", map);
            return "mng/partnerMng";
        } else {
            int totalItems = (int) productDao.countRecommendFlg(shopId, productNo, "0", "%"
                                                                                        + proName
                                                                                        + "%");
            //	int totalItems=(int) productDao.countRecommendFlg(shopId, productNo, "0","%"+proName+"%");
            Map<String, Integer> pagedate = getPageData(request);
            List<ProductDO> list = productDao.selectByRecommendFlg(shopId, productNo, "0",
                pagedate.get(OFFSET) / 2, pagedate.get(PAGE_SIZE_STR) / 2, "%" + proName + "%");
            map.put("totalPages", calculatePage(totalItems, pagedate.get(PAGE_SIZE_STR) / 2));
            map.put(CURRENTPAGE, pagedate.get(CURRENTPAGE));
            map.put("totalItems", totalItems);
            map.put("productInfo", list);
            map.put("flag", "0");
            request.setAttribute("page", map);
            return "mng/partnerMng";
        }

    }
    
    @RequestMapping("partnerProduct.html")
    public String partnerProduct(HttpServletRequest request, ModelMap map) {
    	String userId = (String)request.getSession().getAttribute("userId");
    	boolean checkRoleBoss = checkRoleBoss(userId);
    	if(checkRoleBoss == false){
    		return "mng/loginMng";
    	}
    	//判断是否为运营者
        int checkOperative = checkOperative(userId);
        map.put("checkOperative", checkOperative);
        return "mng/partnerProduct";
    }

    @RequestMapping("partnerProduct.do")
    public Object partnerProductDo(HttpServletRequest request, ModelMap map) {
    	String userId = (String)request.getSession().getAttribute("userId");
    	boolean checkRoleBoss = checkRoleBoss(userId);
    	if(checkRoleBoss == false){
    		return "mng/loginMng";
    	}
    	//判断是否为运营者
        int checkOperative = checkOperative(userId);
        map.put("checkOperative", checkOperative);
    	
        String shopId = request.getParameter("shopId");
        String flg = request.getParameter("selectedNew");
        String productNo = request.getParameter("productNo");
        String proName = request.getParameter("proName");
        if ("1".equals(flg)) {
            int totalItems = (int) productDao.countRecommendFlg(shopId, productNo, "8", "%"
                                                                                        + proName
                                                                                        + "%");
            Map<String, Integer> pagedate = getPageData(request);
            List<ProductDO> list = productDao.selectByRecommendFlg(shopId, productNo, "8",
                pagedate.get(OFFSET) / 2, pagedate.get(PAGE_SIZE_STR) / 2, "%" + proName + "%");
            map.put("totalPages", calculatePage(totalItems, pagedate.get(PAGE_SIZE_STR) / 2));
            map.put(CURRENTPAGE, pagedate.get(CURRENTPAGE));
            map.put("totalItems", totalItems);
            map.put("productInfo", list);
            map.put("flag", "1");
            request.setAttribute("page", map);
            return "mng/partnerProduct";
        } else {
            int totalItems = (int) productDao.countRecommendFlg(shopId, productNo, "0", "%"
                                                                                        + proName
                                                                                        + "%");
            //	int totalItems=(int) productDao.countRecommendFlg(shopId, productNo, "0","%"+proName+"%");
            Map<String, Integer> pagedate = getPageData(request);
            List<ProductDO> list = productDao.selectByRecommendFlg(shopId, productNo, "0",
                pagedate.get(OFFSET) / 2, pagedate.get(PAGE_SIZE_STR) / 2, "%" + proName + "%");
            map.put("totalPages", calculatePage(totalItems, pagedate.get(PAGE_SIZE_STR) / 2));
            map.put(CURRENTPAGE, pagedate.get(CURRENTPAGE));
            map.put("totalItems", totalItems);
            map.put("productInfo", list);
            map.put("flag", "0");
            request.setAttribute("page", map);
            return "mng/partnerProduct";
        }

    }

    @RequestMapping("productEdit.do")
    @ResponseBody
    public Object Edit(HttpServletRequest request) {
        JsonResult result = new JsonResult(false, "", "");
        String productNo = request.getParameter("productNo");
        String shopId = request.getParameter("shopId");
        String recommendFlg = request.getParameter("recommendFlg");
        if ("2".equals(recommendFlg)) {
            productDao.updateStatus("0", productNo, shopId);
            result.setBizSucc(true);
            return result;
        } else {
            int count = (int) productDao.countRecommendFlg(null, null, "2", null);
            if (count == 3) {
                result.setErrCode("农村经纪人商品达到上限3个");
                return result;
            } else {
                productDao.updateStatus("2", productNo, shopId);
                result.setBizSucc(true);
                return result;
            }
        }
    }
    
    @RequestMapping("partnerProEdit.do")
    @ResponseBody
    public Object partnerProEdit(HttpServletRequest request) {
        JsonResult result = new JsonResult(false, "", "");
        String productNo = request.getParameter("productNo");
        String shopId = request.getParameter("shopId");
        String recommendFlg = request.getParameter("recommendFlg");
        if ("8".equals(recommendFlg)) {
            productDao.updateStatus("0", productNo, shopId);
            result.setBizSucc(true);
            return result;
        } else {
            productDao.updateStatus("8", productNo, shopId);
            result.setBizSucc(true);
            return result;
        }
    }

}
