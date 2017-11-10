package com.onway.web.controller.mng;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.onway.common.lang.Money;
import com.onway.makeploy.common.dal.dataobject.ProductDO;
import com.onway.web.controller.AbstractController;
import com.onway.web.controller.result.JsonResult;

/**
 * @author jianyong.jiang
 * 商品参加的各种活动的管理
 *
 */
/**
 * @author Administrator
 *
 */
@Controller
public class ShopActivityController extends AbstractController {
    /**
     * rec 相关的操作
     * @param request
     * @param modelMap
     * @return
     */
    @RequestMapping("/recMng.html")
    public String recMng(HttpServletRequest request, ModelMap modelMap) {
        //		List<ProductDO> recDoList=productDao.selectByRecommendFlg(null, null, "3",0,3);
        //		modelMap.addAttribute("recList", recDoList);

    	String userId = (String)request.getSession().getAttribute("userId");
    	boolean checkRoleBoss = checkRoleBoss(userId);
    	if(checkRoleBoss == false){
    		return "mng/loginMng";
    	}
    	//判断是否为运营者
        int checkOperative = checkOperative(userId);
        modelMap.put("checkOperative", checkOperative);
    	
        return "mng/recMng";
    }

    /*搜索*/
    @RequestMapping("/selectRec.do")
    public Object recDo(HttpServletRequest request, ModelMap modelMap) {
    	String userId = (String)request.getSession().getAttribute("userId");
    	boolean checkRoleBoss = checkRoleBoss(userId);
    	if(checkRoleBoss == false){
    		return "mng/loginMng";
    	}
    	//判断是否为运营者
        int checkOperative = checkOperative(userId);
        modelMap.put("checkOperative", checkOperative);
        
        String shopId = request.getParameter("shopId");
        String productNo = request.getParameter("productNo");
        String flag = request.getParameter("selectedNew");
        String proName = request.getParameter("proName");
        if ("1".equals(flag)) {
            //查询首推
            int totalItems = (int) productDao.countRecommendFlg(shopId, productNo, "3", "%"
                                                                                        + proName
                                                                                        + "%");
            Map<String, Integer> pageDate = getPageData(request);
            modelMap.put("totalPages", calculatePage(totalItems, pageDate.get(PAGE_SIZE_STR) / 2));
            modelMap.put(CURRENTPAGE, pageDate.get(CURRENTPAGE));
            modelMap.put("totalItems", totalItems);

            List<ProductDO> recList = productDao.selectByRecommendFlg(shopId, productNo, "3",
                pageDate.get(OFFSET) / 2, pageDate.get(PAGE_SIZE_STR) / 2, "%" + proName + "%");
            modelMap.addAttribute("recList", recList);
            modelMap.addAttribute("flag", flag);
            request.setAttribute("page", modelMap);
            modelMap.put("shopId", shopId);
            modelMap.put("proNo", productNo);
            modelMap.put("proName", proName);
            return "mng/recMng";
        }
        //查询非首推
        else if ("0".equals(flag)) {
            int totalItems = (int) productDao.countRecommendFlg(shopId, productNo, "0", "%"
                                                                                        + proName
                                                                                        + "%");
            Map<String, Integer> pageDate = getPageData(request);
            modelMap.put("totalPages", calculatePage(totalItems, pageDate.get(PAGE_SIZE_STR) / 2));
            modelMap.put(CURRENTPAGE, pageDate.get(CURRENTPAGE));
            modelMap.put("totalItems", totalItems);

            List<ProductDO> recList = productDao.selectByRecommendFlg(shopId, productNo, "0",
                pageDate.get(OFFSET) / 2, pageDate.get(PAGE_SIZE_STR) / 2, "%" + proName + "%");
            modelMap.addAttribute("recList", recList);
            modelMap.addAttribute("flag", flag);
            request.setAttribute("page", modelMap);
            modelMap.put("shopId", shopId);
            modelMap.put("proNo", productNo);
            modelMap.put("proName", proName);
            return "mng/recMng";
        }
        return "mng/recMng";
    }

    /*修改首推  以及原有product表中的标识recFlg*/
    @RequestMapping("/recEdit.do")
    @ResponseBody
    public Object recEdit(HttpServletRequest request, ModelMap modelMap) {
        JsonResult result = new JsonResult(false);
        String recFlag = request.getParameter("flag");//flag=1 需要撤消首推
                                                      //flag=2 需要上首推
        String productNo = request.getParameter("productNo");
        String shopId = request.getParameter("shopId");
        if ("1".equals(recFlag)) {
            int i = productDao.updateRecommendFlgByShopIdAndProductNo("0", shopId, productNo);
            if (i > 0) {
                result.setBizSucc(true);
                result.setInformation("撤消首页推荐成功!");
            }
        } else if ("2".equals(recFlag)) {
            long count = productDao.countRecommendFlg(null, null, "3", null);
            if (count == 3) {
                result.setInformation("首页推荐商品已满!");
                result.setBizSucc(false);
            } else {
                int i = productDao.updateRecommendFlgByShopIdAndProductNo("3", shopId, productNo);
                if (i > 0) {
                    result.setBizSucc(true);
                    result.setInformation("设置首页推荐成功!");
                }
            }
        }
        return result;
    }

    /**
     * newproduct 相关的操作
     * @param request
     * @param modelMap
     * @return
     */
    @RequestMapping("newproMng.html")
    public String newproMng(HttpServletRequest request, ModelMap modelMap) {
        //		List<ProductDO> newproList=productDao.selectByRecommendFlg(null, null, "1",0,6);
        //		modelMap.addAttribute("newproList",newproList);
    	String userId = (String)request.getSession().getAttribute("userId");
    	boolean checkRoleBoss = checkRoleBoss(userId);
    	if(checkRoleBoss == false){
    		return "mng/loginMng";
    	}
    	//判断是否为运营者
        int checkOperative = checkOperative(userId);
        modelMap.put("checkOperative", checkOperative);
        return "mng/newproMng";
    }

    @RequestMapping("/selectNewpro.do")
    public Object newproDo(HttpServletRequest request, ModelMap modelMap) {
    	
    	String userId = (String)request.getSession().getAttribute("userId");
    	boolean checkRoleBoss = checkRoleBoss(userId);
    	if(checkRoleBoss == false){
    		return "mng/loginMng";
    	}
    	//判断是否为运营者
        int checkOperative = checkOperative(userId);
        modelMap.put("checkOperative", checkOperative);
    	
    	
        String shopId = request.getParameter("shopId");
        String productNo = request.getParameter("productNo");
        String flag = request.getParameter("selectedNew");
        String proName = request.getParameter("proName");
        if ("1".equals(flag)) {
            //查询新品
            int totalItems = (int) productDao.countRecommendFlg(shopId, productNo, "1", "%"
                                                                                        + proName
                                                                                        + "%");
            Map<String, Integer> pageDate = getPageData(request);
            modelMap.put("totalPages", calculatePage(totalItems, pageDate.get(PAGE_SIZE_STR) / 2));
            modelMap.put(CURRENTPAGE, pageDate.get(CURRENTPAGE));
            modelMap.put("totalItems", totalItems);

            List<ProductDO> newproList = productDao.selectByRecommendFlg(shopId, productNo, "1",
                pageDate.get(OFFSET) / 2, pageDate.get(PAGE_SIZE_STR) / 2, "%" + proName + "%");
            modelMap.addAttribute("newproList", newproList);
            modelMap.addAttribute("flag", flag);
            request.setAttribute("page", modelMap);
            return "mng/newproMng";
        }
        //查询非新品
        else if ("0".equals(flag)) {
            int totalItems = (int) productDao.countRecommendFlg(shopId, productNo, "0", "%"
                                                                                        + proName
                                                                                        + "%");
            Map<String, Integer> pageDate = getPageData(request);
            modelMap.put("totalPages", calculatePage(totalItems, pageDate.get(PAGE_SIZE_STR) / 2));
            modelMap.put(CURRENTPAGE, pageDate.get(CURRENTPAGE));
            modelMap.put("totalItems", totalItems);

            List<ProductDO> newproList = productDao.selectByRecommendFlg(shopId, productNo, "0",
                pageDate.get(OFFSET) / 2, pageDate.get(PAGE_SIZE_STR) / 2, "%" + proName + "%");
            modelMap.addAttribute("newproList", newproList);
            modelMap.addAttribute("flag", flag);
            request.setAttribute("page", modelMap);
            return "mng/newproMng";
        }

        return "mng/newproMng";
    }

    @RequestMapping("/newproEdit.do")
    @ResponseBody
    public Object newproEdit(HttpServletRequest request, ModelMap modelMap) {
        JsonResult result = new JsonResult(false);
        String Flag = request.getParameter("flag");//flag=1 需要撤消
                                                   //flag=2 需要上传
        String productNo = request.getParameter("productNo");
        String shopId = request.getParameter("shopId");
        if ("1".equals(Flag)) {
            int i = productDao.updateRecommendFlgByShopIdAndProductNo("0", shopId, productNo);
            if (i > 0) {
                result.setBizSucc(true);
                result.setInformation("撤消新品成功!");
            }
        } else if ("2".equals(Flag)) {
            long count = productDao.countRecommendFlg(null, null, "1", null);
            if (count == 6) {
                result.setInformation("新品栏已满!");
                result.setBizSucc(false);
            } else {
                int i = productDao.updateRecommendFlgByShopIdAndProductNo("1", shopId, productNo);
                if (i > 0) {
                    result.setBizSucc(true);
                    result.setInformation("设置新品成功!");
                }
            }
        }
        return result;
    }

    /**
     * teamBuy 相关的操作
     * @param request
     * @param modelMap
     * @return
     */
    @RequestMapping("teamBuyMng.html")
    public String teamBuyMng(HttpServletRequest request, ModelMap modelMap) {
    	String userId = (String)request.getSession().getAttribute("userId");
    	boolean checkRoleBoss = checkRoleBoss(userId);
    	if(checkRoleBoss == false){
    		return "mng/loginMng";
    	}
    	//判断是否为运营者
        int checkOperative = checkOperative(userId);
        modelMap.put("checkOperative", checkOperative);
        return "mng/teamBuyMng";
    }

    @RequestMapping("/selectTeamBuy.do")
    public Object teamBuyDo(HttpServletRequest request, ModelMap modelMap) {
    	
    	String userId = (String)request.getSession().getAttribute("userId");
    	boolean checkRoleBoss = checkRoleBoss(userId);
    	if(checkRoleBoss == false){
    		return "mng/loginMng";
    	}
    	//判断是否为运营者
        int checkOperative = checkOperative(userId);
        modelMap.put("checkOperative", checkOperative);
    	
    	
        String shopId = request.getParameter("shopId");
        String productNo = request.getParameter("productNo");
        String flag = request.getParameter("selectedNew");
        String proName = request.getParameter("proName");
        if (flag.equals("1")) {
            //团购首推
            List<ProductDO> teamBuyinforec = productDao.selectByRecommendFlg(null, null, "5", 0, 5,
                "%" + proName + "%");
            if (!teamBuyinforec.isEmpty()) {
                modelMap.addAttribute("teamBuyinforec", teamBuyinforec);
                modelMap.addAttribute("haveTeamRec", "1");
            }
            //
            int totalItems = (int) productDao.countRecommendFlg(shopId, productNo, "4", "%"
                                                                                        + proName
                                                                                        + "%");
            Map<String, Integer> pageDate = getPageData(request);
            modelMap.put("totalPages", calculatePage(totalItems, pageDate.get(PAGE_SIZE_STR) / 2));
            modelMap.put(CURRENTPAGE, pageDate.get(CURRENTPAGE));
            modelMap.put("totalItems", totalItems);
            request.setAttribute("page", modelMap);
            List<ProductDO> teamBuyinfo = productDao.selectByRecommendFlg(shopId, productNo, "4",
                pageDate.get(OFFSET) / 2, pageDate.get(PAGE_SIZE_STR) / 2, "%" + proName + "%");
            modelMap.addAttribute("teamBuyinfo", teamBuyinfo);
            modelMap.addAttribute("flag", flag);
            return "mng/teamBuyMng";

        } else if (flag.equals("0")) {
            int totalItems = (int) productDao.countRecommendFlg(shopId, productNo, "0", "%"
                                                                                        + proName
                                                                                        + "%");
            Map<String, Integer> pageDate = getPageData(request);
            modelMap.put("totalPages", calculatePage(totalItems, pageDate.get(PAGE_SIZE_STR) / 2));
            modelMap.put(CURRENTPAGE, pageDate.get(CURRENTPAGE));
            modelMap.put("totalItems", totalItems);
            request.setAttribute("page", modelMap);
            List<ProductDO> teamBuyinfo = productDao.selectByRecommendFlg(shopId, productNo, "0",
                pageDate.get(OFFSET) / 2, pageDate.get(PAGE_SIZE_STR) / 2, "%" + proName + "%");
            modelMap.addAttribute("teamBuyinfo", teamBuyinfo);
            modelMap.addAttribute("flag", flag);
            return "mng/teamBuyMng";
        }
        return "mng/teamBuyMng";
    }

    @RequestMapping("/teamBuyEdit.do")
    @ResponseBody
    public Object teamBuyEdit(HttpServletRequest request, ModelMap modelMap) {
        JsonResult result = new JsonResult(false);
        String rflag = request.getParameter("rflag");//来标识 “团购”里需要上“首推”
                                                     //1 标识需要撤消团购首推
                                                     //2标识需要成为团购首推
        String Flag = request.getParameter("flag");//flag=1 需要撤消
                                                   //flag=2 需要上传
        String price = request.getParameter("price");
        String productNo = request.getParameter("productNo");
        String shopId = request.getParameter("shopId");
        if ("1".equals(rflag)) {
            int i = productDao.updateRecommendFlgByShopIdAndProductNo("4", shopId, productNo);
            if (i > 0) {
                result.setBizSucc(true);
                result.setInformation("撤消团购首推成功!");
            }
        } else if ("2".equals(rflag)) {
            if (productDao.countRecommendFlg(null, null, "5", null) == 2) {
                result.setBizSucc(false);
                result.setInformation("已存在两个团购首推!");
            } else {
                result.setBizSucc(true);
                productDao.updateRecommendFlgByShopIdAndProductNo("5", shopId, productNo);
                result.setInformation("添加团购首推成功!");
            }

        } else {

            if ("1".equals(Flag)) {
                int i = productDao.updateRecommendFlgByShopIdAndProductNo("0", shopId, productNo);
                productDao.updateNeedpeople(0, productDao.selectByShopIdSearch(productNo)
                    .getOldPrice(), 1, shopId, productNo);
                if (i > 0) {
                    result.setBizSucc(true);
                    result.setInformation("撤消团购成功!");
                }
            } else if ("2".equals(Flag)) {
                int count = Integer.parseInt(request.getParameter("count"));
                int i = productDao.updateRecommendFlgByShopIdAndProductNo("4", shopId, productNo);
                double off = 0;
                Money newprice = new Money(price);
                Money oldprice = productDao.selectByShopIdSearch(productNo).getOldPrice();
                off = Double.parseDouble(price) / oldprice.getAmount().doubleValue();
                off = Math.round(off * 100 / 100);
                productDao.updateNeedpeople(count, newprice, off, shopId, productNo);
                if (i > 0) {
                    result.setBizSucc(true);
                    result.setInformation("添加团购成功!");
                }
            }
        }
        return result;
    }

    /**
     * 默认农村经纪人商品
     * @return
     */
    @RequestMapping("/defaultProMng.html")
    public String defaultProMng(HttpServletRequest request, ModelMap modelMap) {
    	String userId = (String)request.getSession().getAttribute("userId");
    	boolean checkRoleBoss = checkRoleBoss(userId);
    	if(checkRoleBoss == false){
    		return "mng/loginMng";
    	}
    	//判断是否为运营者
        int checkOperative = checkOperative(userId);
        modelMap.put("checkOperative", checkOperative);
        return "mng/defaultProMng";
    }

    @RequestMapping("selectDefaultPro.do")
    public Object selectdefaultPro(HttpServletRequest request, ModelMap modelMap) {
    	
    	String userId = (String)request.getSession().getAttribute("userId");
    	boolean checkRoleBoss = checkRoleBoss(userId);
    	if(checkRoleBoss == false){
    		return "mng/loginMng";
    	}
    	//判断是否为运营者
        int checkOperative = checkOperative(userId);
        modelMap.put("checkOperative", checkOperative);
        
        String shopId = request.getParameter("shopId");
        String productNo = request.getParameter("productNo");
        String flag = request.getParameter("selectedNew");
        String proName = request.getParameter("proName");
        if (flag.equals("1")) {
            //已在默认农村经纪人
            int totalItems = (int) productDao.countRecommendFlg(shopId, productNo, "6", "%"
                                                                                        + proName
                                                                                        + "%");
            Map<String, Integer> pageDate = getPageData(request);
            modelMap.put("totalPages", calculatePage(totalItems, pageDate.get(PAGE_SIZE_STR) / 2));
            modelMap.put(CURRENTPAGE, pageDate.get(CURRENTPAGE));
            modelMap.put("totalItems", totalItems);
            request.setAttribute("page", modelMap);
            List<ProductDO> defaultPro = productDao.selectByRecommendFlg(shopId, productNo, "6",
                pageDate.get(OFFSET) / 2, pageDate.get(PAGE_SIZE_STR) / 2, "%" + proName + "%");
            modelMap.addAttribute("defaultPro", defaultPro);
            modelMap.addAttribute("flag", flag);
            return "mng/defaultProMng";

        } else if (flag.equals("0")) {
            //未在默认农村经纪人
            int totalItems = (int) productDao.countRecommendFlg(shopId, productNo, "0", "%"
                                                                                        + proName
                                                                                        + "%");
            Map<String, Integer> pageDate = getPageData(request);
            modelMap.put("totalPages", calculatePage(totalItems, pageDate.get(PAGE_SIZE_STR) / 2));
            modelMap.put(CURRENTPAGE, pageDate.get(CURRENTPAGE));
            modelMap.put("totalItems", totalItems);
            request.setAttribute("page", modelMap);
            List<ProductDO> defaultPro = productDao.selectByRecommendFlg(shopId, productNo, "0",
                pageDate.get(OFFSET) / 2, pageDate.get(PAGE_SIZE_STR) / 2, "%" + proName + "%");
            modelMap.addAttribute("defaultPro", defaultPro);
            modelMap.addAttribute("flag", flag);
            return "mng/defaultProMng";
        }
        return "mng/defaultProMng";
    }

    @RequestMapping("defaultPro.do")
    @ResponseBody
    public Object defaultPro(HttpServletRequest request, ModelMap map) {
        JsonResult result = new JsonResult(false, null, "操作失败");
        String Flag = request.getParameter("flag");//flag=1 需要撤消
                                                   //flag=2 需要上传
        String productNo = request.getParameter("productNo");
        String shopId = request.getParameter("shopId");
        if (Flag.equals("1")) {
            productDao.updateRecommendFlgByShopIdAndProductNo("0", shopId, productNo);
            result.setErrMsg("撤销成功");
            result.setBizSucc(true);
        }
        if (Flag.equals("2")) {
            long count = productDao.countRecommendFlg(null, null, "6", null);
            if (count >= 16) {
                result.setErrMsg("默认农村经纪人商品已满");
                result.setBizSucc(false);
            } else {
                productDao.updateRecommendFlgByShopIdAndProductNo("6", shopId, productNo);
                result.setErrMsg("设置成功");
                result.setBizSucc(true);
            }
        }

        return result;
    }

    /**
     * 分红商品
     * @return
     */
    @RequestMapping("/dividendProMng.html")
    public String dividendProMng(HttpServletRequest request, ModelMap modelMap) {
    	String userId = (String)request.getSession().getAttribute("userId");
    	boolean checkRoleBoss = checkRoleBoss(userId);
    	if(checkRoleBoss == false){
    		return "mng/loginMng";
    	}
    	//判断是否为运营者
        int checkOperative = checkOperative(userId);
        modelMap.put("checkOperative", checkOperative);
        return "mng/dividendProMng";
    }

    @RequestMapping("selectDividendPro.do")
    public Object selectDividendPro(HttpServletRequest request, ModelMap modelMap) {
    	
    	String userId = (String)request.getSession().getAttribute("userId");
    	boolean checkRoleBoss = checkRoleBoss(userId);
    	if(checkRoleBoss == false){
    		return "mng/loginMng";
    	}
    	//判断是否为运营者
        int checkOperative = checkOperative(userId);
        modelMap.put("checkOperative", checkOperative);
    	
        String shopId = request.getParameter("shopId");
        String productNo = request.getParameter("productNo");
        String flag = request.getParameter("selectedNew");
        String proName = request.getParameter("proName");
        if (flag.equals("1")) {
            //已是分红商品
            int totalItems = (int) productDao.countRecommendFlg(shopId, productNo, "7", "%"
                                                                                        + proName
                                                                                        + "%");
            Map<String, Integer> pageDate = getPageData(request);
            modelMap.put("totalPages", calculatePage(totalItems, pageDate.get(PAGE_SIZE_STR) / 2));
            modelMap.put(CURRENTPAGE, pageDate.get(CURRENTPAGE));
            modelMap.put("totalItems", totalItems);
            request.setAttribute("page", modelMap);
            List<ProductDO> defaultPro = productDao.selectByRecommendFlg(shopId, productNo, "7",
                pageDate.get(OFFSET) / 2, pageDate.get(PAGE_SIZE_STR) / 2, "%" + proName + "%");
            modelMap.addAttribute("defaultPro", defaultPro);
            modelMap.addAttribute("flag", flag);
            return "mng/dividendProMng";

        } else if (flag.equals("0")) {
            //未在分红商品
            int totalItems = (int) productDao.countRecommendFlg(shopId, productNo, "0", "%"
                                                                                        + proName
                                                                                        + "%");
            Map<String, Integer> pageDate = getPageData(request);
            modelMap.put("totalPages", calculatePage(totalItems, pageDate.get(PAGE_SIZE_STR) / 2));
            modelMap.put(CURRENTPAGE, pageDate.get(CURRENTPAGE));
            modelMap.put("totalItems", totalItems);
            request.setAttribute("page", modelMap);
            List<ProductDO> defaultPro = productDao.selectByRecommendFlg(shopId, productNo, "0",
                pageDate.get(OFFSET) / 2, pageDate.get(PAGE_SIZE_STR) / 2, "%" + proName + "%");
            modelMap.addAttribute("defaultPro", defaultPro);
            modelMap.addAttribute("flag", flag);
            return "mng/dividendProMng";
        }
        return "mng/dividendProMng";
    }

    @RequestMapping("dividendPro.do")
    @ResponseBody
    public Object dividendPro(HttpServletRequest request, ModelMap map) {
        JsonResult result = new JsonResult(false, null, "操作失败");
        String Flag = request.getParameter("flag");//flag=1 需要撤消
                                                   //flag=2 需要上传
        String productNo = request.getParameter("productNo");
        String shopId = request.getParameter("shopId");
        if (Flag.equals("1")) {
            productDao.updateRecommendFlgByShopIdAndProductNo("0", shopId, productNo);
            result.setErrMsg("撤销成功");
            result.setBizSucc(true);
        }
        if (Flag.equals("2")) {

            productDao.updateRecommendFlgByShopIdAndProductNo("7", shopId, productNo);
            result.setErrMsg("设置成功");
            result.setBizSucc(true);

        }

        return result;
    }
}
