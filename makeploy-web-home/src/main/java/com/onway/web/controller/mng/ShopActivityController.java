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
 * ��Ʒ�μӵĸ��ֻ�Ĺ���
 *
 */
/**
 * @author Administrator
 *
 */
@Controller
public class ShopActivityController extends AbstractController {
    /**
     * rec ��صĲ���
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
    	//�ж��Ƿ�Ϊ��Ӫ��
        int checkOperative = checkOperative(userId);
        modelMap.put("checkOperative", checkOperative);
    	
        return "mng/recMng";
    }

    /*����*/
    @RequestMapping("/selectRec.do")
    public Object recDo(HttpServletRequest request, ModelMap modelMap) {
    	String userId = (String)request.getSession().getAttribute("userId");
    	boolean checkRoleBoss = checkRoleBoss(userId);
    	if(checkRoleBoss == false){
    		return "mng/loginMng";
    	}
    	//�ж��Ƿ�Ϊ��Ӫ��
        int checkOperative = checkOperative(userId);
        modelMap.put("checkOperative", checkOperative);
        
        String shopId = request.getParameter("shopId");
        String productNo = request.getParameter("productNo");
        String flag = request.getParameter("selectedNew");
        String proName = request.getParameter("proName");
        if ("1".equals(flag)) {
            //��ѯ����
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
        //��ѯ������
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

    /*�޸�����  �Լ�ԭ��product���еı�ʶrecFlg*/
    @RequestMapping("/recEdit.do")
    @ResponseBody
    public Object recEdit(HttpServletRequest request, ModelMap modelMap) {
        JsonResult result = new JsonResult(false);
        String recFlag = request.getParameter("flag");//flag=1 ��Ҫ��������
                                                      //flag=2 ��Ҫ������
        String productNo = request.getParameter("productNo");
        String shopId = request.getParameter("shopId");
        if ("1".equals(recFlag)) {
            int i = productDao.updateRecommendFlgByShopIdAndProductNo("0", shopId, productNo);
            if (i > 0) {
                result.setBizSucc(true);
                result.setInformation("������ҳ�Ƽ��ɹ�!");
            }
        } else if ("2".equals(recFlag)) {
            long count = productDao.countRecommendFlg(null, null, "3", null);
            if (count == 3) {
                result.setInformation("��ҳ�Ƽ���Ʒ����!");
                result.setBizSucc(false);
            } else {
                int i = productDao.updateRecommendFlgByShopIdAndProductNo("3", shopId, productNo);
                if (i > 0) {
                    result.setBizSucc(true);
                    result.setInformation("������ҳ�Ƽ��ɹ�!");
                }
            }
        }
        return result;
    }

    /**
     * newproduct ��صĲ���
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
    	//�ж��Ƿ�Ϊ��Ӫ��
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
    	//�ж��Ƿ�Ϊ��Ӫ��
        int checkOperative = checkOperative(userId);
        modelMap.put("checkOperative", checkOperative);
    	
    	
        String shopId = request.getParameter("shopId");
        String productNo = request.getParameter("productNo");
        String flag = request.getParameter("selectedNew");
        String proName = request.getParameter("proName");
        if ("1".equals(flag)) {
            //��ѯ��Ʒ
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
        //��ѯ����Ʒ
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
        String Flag = request.getParameter("flag");//flag=1 ��Ҫ����
                                                   //flag=2 ��Ҫ�ϴ�
        String productNo = request.getParameter("productNo");
        String shopId = request.getParameter("shopId");
        if ("1".equals(Flag)) {
            int i = productDao.updateRecommendFlgByShopIdAndProductNo("0", shopId, productNo);
            if (i > 0) {
                result.setBizSucc(true);
                result.setInformation("������Ʒ�ɹ�!");
            }
        } else if ("2".equals(Flag)) {
            long count = productDao.countRecommendFlg(null, null, "1", null);
            if (count == 6) {
                result.setInformation("��Ʒ������!");
                result.setBizSucc(false);
            } else {
                int i = productDao.updateRecommendFlgByShopIdAndProductNo("1", shopId, productNo);
                if (i > 0) {
                    result.setBizSucc(true);
                    result.setInformation("������Ʒ�ɹ�!");
                }
            }
        }
        return result;
    }

    /**
     * teamBuy ��صĲ���
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
    	//�ж��Ƿ�Ϊ��Ӫ��
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
    	//�ж��Ƿ�Ϊ��Ӫ��
        int checkOperative = checkOperative(userId);
        modelMap.put("checkOperative", checkOperative);
    	
    	
        String shopId = request.getParameter("shopId");
        String productNo = request.getParameter("productNo");
        String flag = request.getParameter("selectedNew");
        String proName = request.getParameter("proName");
        if (flag.equals("1")) {
            //�Ź�����
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
        String rflag = request.getParameter("rflag");//����ʶ ���Ź�������Ҫ�ϡ����ơ�
                                                     //1 ��ʶ��Ҫ�����Ź�����
                                                     //2��ʶ��Ҫ��Ϊ�Ź�����
        String Flag = request.getParameter("flag");//flag=1 ��Ҫ����
                                                   //flag=2 ��Ҫ�ϴ�
        String price = request.getParameter("price");
        String productNo = request.getParameter("productNo");
        String shopId = request.getParameter("shopId");
        if ("1".equals(rflag)) {
            int i = productDao.updateRecommendFlgByShopIdAndProductNo("4", shopId, productNo);
            if (i > 0) {
                result.setBizSucc(true);
                result.setInformation("�����Ź����Ƴɹ�!");
            }
        } else if ("2".equals(rflag)) {
            if (productDao.countRecommendFlg(null, null, "5", null) == 2) {
                result.setBizSucc(false);
                result.setInformation("�Ѵ��������Ź�����!");
            } else {
                result.setBizSucc(true);
                productDao.updateRecommendFlgByShopIdAndProductNo("5", shopId, productNo);
                result.setInformation("����Ź����Ƴɹ�!");
            }

        } else {

            if ("1".equals(Flag)) {
                int i = productDao.updateRecommendFlgByShopIdAndProductNo("0", shopId, productNo);
                productDao.updateNeedpeople(0, productDao.selectByShopIdSearch(productNo)
                    .getOldPrice(), 1, shopId, productNo);
                if (i > 0) {
                    result.setBizSucc(true);
                    result.setInformation("�����Ź��ɹ�!");
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
                    result.setInformation("����Ź��ɹ�!");
                }
            }
        }
        return result;
    }

    /**
     * Ĭ��ũ�徭������Ʒ
     * @return
     */
    @RequestMapping("/defaultProMng.html")
    public String defaultProMng(HttpServletRequest request, ModelMap modelMap) {
    	String userId = (String)request.getSession().getAttribute("userId");
    	boolean checkRoleBoss = checkRoleBoss(userId);
    	if(checkRoleBoss == false){
    		return "mng/loginMng";
    	}
    	//�ж��Ƿ�Ϊ��Ӫ��
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
    	//�ж��Ƿ�Ϊ��Ӫ��
        int checkOperative = checkOperative(userId);
        modelMap.put("checkOperative", checkOperative);
        
        String shopId = request.getParameter("shopId");
        String productNo = request.getParameter("productNo");
        String flag = request.getParameter("selectedNew");
        String proName = request.getParameter("proName");
        if (flag.equals("1")) {
            //����Ĭ��ũ�徭����
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
            //δ��Ĭ��ũ�徭����
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
        JsonResult result = new JsonResult(false, null, "����ʧ��");
        String Flag = request.getParameter("flag");//flag=1 ��Ҫ����
                                                   //flag=2 ��Ҫ�ϴ�
        String productNo = request.getParameter("productNo");
        String shopId = request.getParameter("shopId");
        if (Flag.equals("1")) {
            productDao.updateRecommendFlgByShopIdAndProductNo("0", shopId, productNo);
            result.setErrMsg("�����ɹ�");
            result.setBizSucc(true);
        }
        if (Flag.equals("2")) {
            long count = productDao.countRecommendFlg(null, null, "6", null);
            if (count >= 16) {
                result.setErrMsg("Ĭ��ũ�徭������Ʒ����");
                result.setBizSucc(false);
            } else {
                productDao.updateRecommendFlgByShopIdAndProductNo("6", shopId, productNo);
                result.setErrMsg("���óɹ�");
                result.setBizSucc(true);
            }
        }

        return result;
    }

    /**
     * �ֺ���Ʒ
     * @return
     */
    @RequestMapping("/dividendProMng.html")
    public String dividendProMng(HttpServletRequest request, ModelMap modelMap) {
    	String userId = (String)request.getSession().getAttribute("userId");
    	boolean checkRoleBoss = checkRoleBoss(userId);
    	if(checkRoleBoss == false){
    		return "mng/loginMng";
    	}
    	//�ж��Ƿ�Ϊ��Ӫ��
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
    	//�ж��Ƿ�Ϊ��Ӫ��
        int checkOperative = checkOperative(userId);
        modelMap.put("checkOperative", checkOperative);
    	
        String shopId = request.getParameter("shopId");
        String productNo = request.getParameter("productNo");
        String flag = request.getParameter("selectedNew");
        String proName = request.getParameter("proName");
        if (flag.equals("1")) {
            //���Ƿֺ���Ʒ
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
            //δ�ڷֺ���Ʒ
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
        JsonResult result = new JsonResult(false, null, "����ʧ��");
        String Flag = request.getParameter("flag");//flag=1 ��Ҫ����
                                                   //flag=2 ��Ҫ�ϴ�
        String productNo = request.getParameter("productNo");
        String shopId = request.getParameter("shopId");
        if (Flag.equals("1")) {
            productDao.updateRecommendFlgByShopIdAndProductNo("0", shopId, productNo);
            result.setErrMsg("�����ɹ�");
            result.setBizSucc(true);
        }
        if (Flag.equals("2")) {

            productDao.updateRecommendFlgByShopIdAndProductNo("7", shopId, productNo);
            result.setErrMsg("���óɹ�");
            result.setBizSucc(true);

        }

        return result;
    }
}
