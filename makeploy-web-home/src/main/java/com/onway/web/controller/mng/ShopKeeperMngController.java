package com.onway.web.controller.mng;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.onway.common.lang.DateUtils;
import com.onway.common.lang.Money;
import com.onway.makeploy.common.dal.dataobject.CategoryDO;
import com.onway.makeploy.common.dal.dataobject.FaretemplateDO;
import com.onway.makeploy.common.dal.dataobject.OrderDO;
import com.onway.makeploy.common.dal.dataobject.OrderInfo;
import com.onway.makeploy.common.dal.dataobject.ProImageDO;
import com.onway.makeploy.common.dal.dataobject.ProductDO;
import com.onway.makeploy.common.dal.dataobject.ProductParameterDO;
import com.onway.makeploy.common.dal.dataobject.ShopDO;
import com.onway.makeploy.common.dal.dataobject.ShopIncomeDO;
import com.onway.makeploy.common.dal.dataobject.SysConfigDO;
import com.onway.makeploy.common.dal.dataobject.UserDO;
import com.onway.makeploy.core.localcache.enums.SysConfigCacheKeyEnum;
import com.onway.platform.common.configration.ConfigrationFactory;
import com.onway.web.controller.AbstractController;
import com.onway.web.controller.ImageZipUtil;
import com.onway.web.controller.UUIDHexGenerator;
import com.onway.web.controller.result.JsonPageResult;
import com.onway.web.controller.sendMessage.SendCustomMessage;

@Controller
public class ShopKeeperMngController extends AbstractController {
    private static final String IMAGE_FILE           = ConfigrationFactory.getConfigration()
                                                         .getPropertyValue(
                                                             "user_img_upload_realpath");

    private static final String IMAGE_PATH           = ConfigrationFactory.getConfigration()
                                                         .getPropertyValue("user_img_path");
//    private static final Double PROPORTION_INTERGRAL = 0.1;
//    private static final Double PROPORTION_RETURN    = 0.1;

    /**
     * �������,��Ʒ����
     * 
     * @param request
     * @param modelMap
     * @return
     */
    @RequestMapping("shopProductMng.html")
    public ModelAndView shopGoods(HttpServletRequest request, ModelMap modelMap) {

        try {
            List<String> fathertype = categoryDAO.selectAllFather();
            // List<CategoryDO> Size=categoryDAO.selectSize();

            modelMap.put("fathertype", fathertype);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ModelAndView("mng/shopProductMng");
    }

    /**
     * ��������������ҳ
     * 
     * @param request
     * @param modelMap
     * @return
     */
    @RequestMapping("shopOwerMng.html")
    public ModelAndView shopOwerMng(HttpServletRequest request, ModelMap modelMap) {

		String shopId = (String) request.getSession().getAttribute("shopId");
		String userId = (String) request.getSession().getAttribute("userId");
		int unPayOrderCount = orderDao.selectCountByShopID(shopId, null, "1",
				"1", "3");
		int unSendOrderCount = orderDao.selectCountByShopID(shopId, null, "1",
				"3", "3");
		int unReciveOrderCount = orderDao.selectCountByShopID(shopId, null,
				"2", "3", "3");
		int backOrderCount = orderDao.selectCountByShopID(shopId, null, "3",
				"3", "3");
		UserDO uesrDo = userDao.selectUserInfoByUserId(userId);
		ShopDO shopDo = shopDAO.selectShopByShopId(shopId);
		modelMap.put("unPayOrderCount", unPayOrderCount);
		modelMap.put("unSendOrderCount", unSendOrderCount);
		modelMap.put("unReciveOrderCount", unReciveOrderCount);
		modelMap.put("backOrderCount", backOrderCount);
		modelMap.put("uesrDo", uesrDo);
		modelMap.put("shopDo", shopDo);
		
		//��������
        ShopIncomeDO shopIncomeDO = shopIncomeDAO.selectShopIncomeInfoByUserIdAndShopId(userId, shopId);
        modelMap.put("shopIncomeDO", shopIncomeDO);
		
		if(StringUtils.equals(shopDo.getUnionFlg(), "1")){
		    return new ModelAndView("mng/shopOwerMng");
		}else{
			return new ModelAndView("mng/shopOwerMngPartner");
		}
    }

    /**
     * �����޸ĵ�����Ϣҳ��
     * 
     * @param request
     * @param modelMap
     * @return
     */
    @RequestMapping("changeShop.html")
    public String changeShop(HttpServletRequest request, ModelMap modelMap) {
        String shopId = request.getParameter("shopId");
        ShopDO shopDo = shopDAO.selectShopByShopId(shopId);
        String province = null;
        String city = null;
        String district = null;
        String addr0 = shopDo.getShopAddr();
        
        String classification = sysConfigCacheManager.getConfigValue(SysConfigCacheKeyEnum.CLASSIFICATION_INDUSTRY);
        String[] claifi = classification.split(",");
        List<String> industry = new ArrayList<String>();
        for (int i = 0; i < claifi.length; i++) {
			String indu = claifi[i];
			industry.add(indu);
		}
        modelMap.put("industry", industry);
        
        if (null == addr0) {
            String addr = null;
            modelMap.put("shopInfo", shopDo);
            modelMap.put("addr", addr);
            modelMap.put("province", province);
            modelMap.put("city", city);
            modelMap.put("district", district);
        } else {
            String[] addr1 = addr0.split("/");
            String addr = addr1[1];
            String[] addr2 = addr1[0].split("-");
            province = addr2[0];
            city = addr2[1];
            district = addr2[2];
            modelMap.put("shopInfo", shopDo);
            modelMap.put("addr", addr);
            modelMap.put("province", province);
            modelMap.put("city", city);
            modelMap.put("district", district);
        }

        if(StringUtils.equals(shopDo.getUnionFlg(), "1")){
        	return "mng/changeShop";
        }else{
        	return "mng/changeShopPartner";
        }
    }

    /**
     * ���յ����޸���Ϣ,�����̨
     * 
     * @param request
     * @param modelMap
     * @param image
     * @return
     * @throws Exception
     */
    @RequestMapping("ShopInfoEdit.do")
    public ModelAndView ShopInfoEdit(HttpServletRequest request, ModelMap modelMap,
                                     @RequestParam MultipartFile image) throws Exception {
        StringBuffer imageRelativePath = new StringBuffer();
        ShopDO shop = new ShopDO();
        String oldImg = request.getParameter("oldImg");
        if (null != image && image.getSize() > 0) {
            if (null == image.getContentType() || image.getContentType().split("/") == null) {
                imageRelativePath.append(String.valueOf(System.currentTimeMillis())).append(".jpg");
            } else {
                if (image.getContentType().split("/").length > 1) {
                    imageRelativePath.append(String.valueOf(System.currentTimeMillis()))
                        .append(".").append(image.getContentType().split("/")[1]);
                } else {
                    imageRelativePath.append(String.valueOf(System.currentTimeMillis())).append(
                        ".jpg");
                }
            }
            String imagePath = IMAGE_FILE + imageRelativePath;
            ImageZipUtil.compressPic(image, imagePath);
            String shopUrl = IMAGE_PATH + imageRelativePath + "";
            shop.setShopUrl(shopUrl);
        } else {
            shop.setShopUrl(oldImg);
        }
        String shopId = (String) request.getSession().getAttribute("shopId");
        String shopName = getParameterCheck(request, "shopName");
        String cell = getParameterCheck(request, "cell");
        String shopDec = getParameterCheck(request, "shopDec");
        String province = getParameterCheck(request, "province");
        String city = getParameterCheck(request, "city");
        String district = getParameterCheck(request, "district");
        String provinceOld = request.getParameter("province_old");
        String cityOld = request.getParameter("city_old");
        String districtOld = request.getParameter("district_old");
        String addr = getParameterCheck(request, "addr");
        String industry = request.getParameter("industry");
        String shopAddr;
        String lgAddr;
        if (null == province || null == city || null == district) {
            shopAddr = provinceOld + "-" + cityOld + "-" + districtOld + "/" + addr;
            lgAddr = provinceOld + cityOld + districtOld + addr;
        } else {
            shopAddr = province + "-" + city + "-" + district + "/" + addr;
            lgAddr = province + city + district + addr;
        }
        //����shopCheck��
        shopCheckDAO.updateInfo(shopName, cell, shopDec, shopAddr, shopId);
        //����shop��
        shop.setShopAddr(shopAddr);
        shop.setShopDec(shopDec);
        shop.setCell(cell);
        shop.setShopName(shopName);
        shop.setShopId(shopId);
        
        //������̾�γ��
        GetLatAndLngByBaidu gl = new GetLatAndLngByBaidu();
        Object[] coordinate = gl.getCoordinate(lgAddr);
		double loactionX =Double.parseDouble(coordinate[0].toString());
		double loactionY =Double.parseDouble(coordinate[1].toString());
        shop.setLoactionX(loactionX);
        shop.setLoactionY(loactionY);
        shop.setIndustry(industry);
        
        shopDAO.updateShopInfo(shop);
        return shopOwerMng(request, modelMap);
    }

    /**
     * �������,�����Ʒ ҳ��
     * 
     * @param request
     * @param modelMap
     * @return
     */
    @RequestMapping("shopProductAddMng.html")
    public ModelAndView shopProductAddMng(HttpServletRequest request, ModelMap modelMap) {
        String shopId = request.getSession().getAttribute("shopId").toString();
        try {
            List<String> fathertype = categoryDAO.selectAllFather();
            // List<CategoryDO> Size=categoryDAO.selectSize();
            ShopDO shop = shopDAO.selectShopByShopId(shopId);
            List<FaretemplateDO> faretemplateList = faretemplateDAO.selectAllFaretemplate(shopId);
            
            String industries = sysConfigCacheManager.getConfigValue(SysConfigCacheKeyEnum.CLASSIFICATION_INDUSTRY);
            String[] industry = industries.split(",");
            List<String> industryList = new ArrayList<String>();
            for (String string : industry) {
            	industryList.add(string);
			}
            modelMap.put("industryList", industryList);
            modelMap.put("faretemplateList", faretemplateList);
            modelMap.put("fathertype", fathertype);
            modelMap.put("shopDO", shop);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ModelAndView("mng/shopProductAddMng");
    }

    /**
     * �����Ʒҳ�� ��ȡ�ӷ���
     * 
     * @param request
     * @param modelMap
     * @return
     */
    @RequestMapping("getChildType.do")
    @ResponseBody
    public Object getChildType(HttpServletRequest request, ModelMap modelMap) {
        List<CategoryDO> categoryList = new ArrayList<CategoryDO>();
        String fatherType = request.getParameter("fatherType");
        try {

            categoryList = categoryDAO.selectTypeByFather(fatherType);

        } catch (Exception e) {
            e.printStackTrace();
        }

        return categoryList;

    }

    /**
     * �����Ʒҳ�� ��ȡ ��ʽ
     * 
     * @param request
     * @param modelMap
     * @return
     */
    @RequestMapping("getProductSize.do")
    @ResponseBody
    public Object getProductSize(HttpServletRequest request, ModelMap modelMap) {
        List<CategoryDO> categoryList = new ArrayList<CategoryDO>();
        try {

            categoryList = categoryDAO.selectSize();

        } catch (Exception e) {
            e.printStackTrace();
        }

        return categoryList;
    }

    /**
     * ��ȡ ��ʽ ����
     * 
     * @param request
     * @param modelMap
     * @return
     */
    @RequestMapping("getChildSize.do")
    @ResponseBody
    public Object getChildSize(HttpServletRequest request, ModelMap modelMap) {

        String fSize = request.getParameter("fSize");

        List<String> list = new ArrayList<String>();
        CategoryDO category = new CategoryDO();
        try {

            category = categoryDAO.selectParamenterBySize(fSize);
            if (null != category) {
                String[] paramenter = category.getFatherImg().split(",");
                for (int i = 0; i < paramenter.length; i++) {
                    if (null != paramenter[i] && paramenter[i].length() > 0) {
                        list.add(paramenter[i]);
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return list;
    }

    /**
     * ������Ʒ��Ϣ,���
     * 
     * @param request
     * @param modelMap
     * @param image
     * @return
     * @throws Exception
     */
    @RequestMapping("ShopAddProduct.do")
    public ModelAndView ShopAddProduct(HttpServletRequest request, ModelMap modelMap,
                                       @RequestParam MultipartFile image1,
                                       @RequestParam MultipartFile[] image2,
                                       @RequestParam MultipartFile[] image3) throws Exception {
        String imagePath;
        String productWeight  = request.getParameter("weight");
        String templateId  = request.getParameter("templateId");
        String s_fatherType = request.getParameter("s_fatherType");
        String s_childType = request.getParameter("s_childType");
        String productName = getParameterCheck(request, "productName");
        String Price = getParameterCheck(request, "price");
        String usePoint = getParameterCheck(request, "usePoint");
        String otherType = request.getParameter("otherType");
        if (StringUtils.isBlank(usePoint)) {
            usePoint = "0";
        } else {
            usePoint = usePoint.replace(" ", "");
        }

        if (null != Price) {
            if (Price.contains(" ")) {
                Price = Price.replace(" ", "");
            }
        }
        String shopId = (String) request.getSession().getAttribute("shopId");
        // ���ɲ��ظ�����Ʒ��
        String productNo = UUIDHexGenerator.getProNo();
        ProductDO product = new ProductDO();
        product.setProductNo(productNo);
        product.setShopId(shopId);
        product.setCheckStatus("0");// set checkstatus 0 equal unchecked
        product.setChildren(s_childType);// ���� ����
        product.setProductType(s_fatherType);// ���� ����
        product.setDelFlg("0");
        product.setUsePoint(new Money(usePoint));
        product.setOldPrice(new Money(Price));
        product.setPrice(new Money(Price));
        product.setProductOff(1);
        product.setProductName(productName);
        product.setOtherType(otherType);

        int intStockall = 0;

        String stock;
        int intStock;
        String flag = getParameterCheck(request, "flag");// ���� ��ʽflag 0 �޲��� 1
                                                         // ������ 2 ˫����
        String number = getParameterCheck(request, "number");// ������ʽ��
        String imgNumber1 = getParameterCheck(request, "imgNumber1");// ������ҳͼƬ��
        String imgNumber2 = getParameterCheck(request, "imgNumber2");// ����bannerͼƬ��
        String imgNumber3 = getParameterCheck(request, "imgNumber3");// ���շ�չʾͼƬ��
        ProductParameterDO productParameterDO = new ProductParameterDO();
        // ������Ʒ��ʽ
        // shopid productNo seam ,out of for(){}

        productParameterDO.setProductNo(productNo);
        productParameterDO.setShopId(shopId);
        if (null != number) {
            if (flag.equals("2")) {
                int index = Integer.parseInt(number);
                int par = 0;
                for (int i = 1; i <= index; i++) {
                    String fatherType = "fatherType" + i;
                    String childType = "childType" + i;
                    String fatherSize = "fatherSize" + i;
                    String childSize = "childSize" + i;
                    fatherType = getParameterCheck(request, fatherType);
                    childType = getParameterCheck(request, childType);
                    fatherSize = getParameterCheck(request, fatherSize);
                    childSize = getParameterCheck(request, childSize);
                    stock = getParameterCheck(request, String.valueOf(i));
                    String[] childSizes = childSize.split("/");
                    String[] stocks = stock.split("/");
                    if (null != stock) {
                        if (stock.contains(" ")) {
                            stock = stock.replace(" ", "");
                        }
                        for(int j = 0 ; j < childSizes.length ; j ++ ){
	                        intStock = Integer.parseInt(stocks[j]);
	                        intStockall += intStock;// �ۼ���Ʒ�ܿ��
	                        productParameterDO.setStock(intStock);
	                        productParameterDO.setFatherName(fatherType + "��" + fatherSize);
	                        productParameterDO.setChildrenName(childType+ "��" + childSizes[j]);
	                        productParameterDO.setParflag(String.valueOf(par));
	                        par = par+1;
	                        productParameterDAO.insert(productParameterDO);
                        }
                    }
                }
            } else if (flag.equals("1")) {
                int index = Integer.parseInt(number);
                int par = 0;
                for (int i = 1; i <= index; i++) {
                    String fatherType = "fatherType" + i;
                    String childType = "childType" + i;
                    String fatherSize = "��nk001";
                    // String childSize = "childSize" + i;
                    fatherType = getParameterCheck(request, fatherType);
                    childType = getParameterCheck(request, childType);
                    stock = getParameterCheck(request, String.valueOf(i));
                    String[] childTypes = childType.split("/");
                    String[] stocks = stock.split("/");
                    if (null != stock) {
                        if (stock.contains(" ")) {
                            stock = stock.replace(" ", "");
                        }
                        for(int j = 0 ; j < childTypes.length ; j ++ ){
	                        intStock = Integer.parseInt(stocks[j]);
	                        intStockall += intStock;// �ۼ���Ʒ�ܿ��
	                        productParameterDO.setStock(intStock);
	                        productParameterDO.setFatherName(fatherType + fatherSize);
	                        productParameterDO.setChildrenName(childTypes[j] + fatherSize);
	                        productParameterDO.setParflag(String.valueOf(par));
	                        par = par+1;
	                        productParameterDAO.insert(productParameterDO);
                        }
                    }
                }
            } else if (flag.equals("0")) {
                stock = getParameterCheck(request, "0");
                if (null != stock) {
                    if (stock.contains(" ")) {
                        stock = stock.replace(" ", "");
                    }
                    intStock = Integer.parseInt(stock);
                    intStockall += intStock;// �ۼ���Ʒ�ܿ��
                }
            }

        }
        // ������ƷͼƬ
        ProImageDO proImageDO = new ProImageDO();
        proImageDO.setShopId(shopId);
        proImageDO.setProductNo(productNo);
        if (null != imgNumber1) {
            if (null != imgNumber2) {
                int numb2 = Integer.parseInt(imgNumber2);
                String[] imageRelativePath2 = new String[numb2];
                for (int i = 0; i < image2.length; i++) {

                    if (null == image2[i].getContentType()
                        || image2[i].getContentType().split("/") == null) {
                        imageRelativePath2[i] = String.valueOf(System.currentTimeMillis()) + ".jpg";//
                    } else {
                        if (image2[i].getContentType().split("/").length > 1) {
                            imageRelativePath2[i] = String.valueOf(System.currentTimeMillis())
                                                    + "."
                                                    + image2[i].getContentType().split("/")[1];//
                        } else {
                            imageRelativePath2[i] = String.valueOf(System.currentTimeMillis())
                                                    + ".jpg";//
                        }
                    }
                    imagePath = IMAGE_FILE + imageRelativePath2[i];
                    ImageZipUtil.compressPic(image2[i], imagePath);
                    imageRelativePath2[i] = IMAGE_PATH + imageRelativePath2[i];
                    proImageDO.setImg(imageRelativePath2[i]);
                    proImageDO.setFlag("2");// banner

                    proImageDAO.insert(proImageDO);
                }
            }
            if (null != imgNumber3) {
                int numb3 = Integer.parseInt(imgNumber3);

                String[] imageRelativePath3 = new String[numb3];
                for (int i = 0; i < image3.length; i++) {

                    if (null == image3[i].getContentType()
                        || image3[i].getContentType().split("/") == null) {
                        imageRelativePath3[i] = String.valueOf(System.currentTimeMillis()) + ".jpg";//
                    } else {
                        if (image3[i].getContentType().split("/").length > 1) {
                            imageRelativePath3[i] = String.valueOf(System.currentTimeMillis())
                                                    + "."
                                                    + image3[i].getContentType().split("/")[1];//
                        } else {
                            imageRelativePath3[i] = String.valueOf(System.currentTimeMillis())
                                                    + ".jpg";//
                        }
                    }
                    imagePath = IMAGE_FILE + imageRelativePath3[i];
                    ImageZipUtil.compressPic(image3[i], imagePath);
                    imageRelativePath3[i] = IMAGE_PATH + imageRelativePath3[i];
                    proImageDO.setImg(imageRelativePath3[i]);
                    proImageDO.setFlag("0");// ����ҳչʾ

                    proImageDAO.insert(proImageDO);
                }
            }

            String imageRelativePath1 = new String();
            if (null == image1.getContentType() || image1.getContentType().split("/") == null) {
                imageRelativePath1 = String.valueOf(System.currentTimeMillis()) + ".jpg";//
            } else {
                if (image1.getContentType().split("/").length > 1) {
                    imageRelativePath1 = String.valueOf(System.currentTimeMillis()) + "."
                                         + image1.getContentType().split("/")[1];//
                } else {
                    imageRelativePath1 = String.valueOf(System.currentTimeMillis()) + ".jpg";//
                }
            }
            imagePath = IMAGE_FILE + imageRelativePath1;
            ImageZipUtil.compressPic(image1, imagePath);
            imageRelativePath1 = IMAGE_PATH + imageRelativePath1;
            proImageDO.setImg(imageRelativePath1);
            proImageDO.setFlag("1");// ��ҳչʾ

            proImageDAO.insert(proImageDO);
            product.setProductUrl(imageRelativePath1);
        }
        // �����ܿ��
        product.setStock(intStockall);
        // ������Ʒ����,���ֱ���
        // maybe ����Ʒ���ͨ��������
        product.setProportionIntegral(0);
        product.setProportionReturn(0);
        product.setUserId((String) request.getSession().getAttribute("userId"));
        product.setTemplateId(templateId);
        if(StringUtils.isBlank(productWeight)){
        	productWeight = "0";
        }
        product.setProductWeight(Double.valueOf(productWeight));
        int flag1 = productDao.insert(product);
        modelMap.put("flag", flag1);
        return new ModelAndView("mng/shopProductAddsuccess");

    }

    /**
     * ����༭��Ʒҳ��
     * 
     * @param request
     * @param modelMap
     * @return
     */
    @RequestMapping("shopProductEditMng.html")
    public String shopProductEditMng(HttpServletRequest request, ModelMap modelMap) {
        String productNo = getParameterCheck(request, "productNo");
        String shopId = (String) request.getSession().getAttribute("shopId");
        String imgUrl;
        try {
            List<String> fathertype = categoryDAO.selectAllFather();

            modelMap.put("fathertype", fathertype);
            ProductDO productDo = productDao.selectProductByProductNo(productNo, shopId);
            List<ProductParameterDO> sizeList = productParameterDAO.selectInfoByShopIdAndProductNo(
                productNo, shopId);
            String fatherName, childrenName;
            String[] size;
            if (sizeList.size() > 0) {
                if (sizeList.get(0).getFatherName().contains("nk001")) {// ��һ����
                    for (int i = 0; i < sizeList.size(); i++) {
                        fatherName = sizeList.get(i).getFatherName().replace("��nk001", "");
                        childrenName = sizeList.get(i).getChildrenName().replace("��nk001", "");
                        sizeList.get(i).setFatherName(fatherName);
                        sizeList.get(i).setChildrenName(childrenName);
                    }
                    modelMap.put("sizeList", sizeList);
                    modelMap.put("sizeFlag", "1");
                    modelMap.put("number", sizeList.size());
                } else {// ˫����
                    for (int i = 0; i < sizeList.size(); i++) {
                        size = sizeList.get(i).getFatherName().split("��");
                        fatherName = size[0];
                        childrenName = size[1];
                        sizeList.get(i).setShopId(fatherName);
                        sizeList.get(i).setProductNo(childrenName);
                        size = sizeList.get(i).getChildrenName().split("��");
                        fatherName = size[0];
                        childrenName = size[1];
                        sizeList.get(i).setFatherName(fatherName);
                        sizeList.get(i).setChildrenName(childrenName);
                    }
                    modelMap.put("sizeList", sizeList);
                    modelMap.put("sizeFlag", "2");
                    modelMap.put("number", sizeList.size());
                }
            } else {// �޲���
                modelMap.put("sizeFlag", "0");
                modelMap.put("number", "0");
            }

            // ͼƬ����
            List<ProImageDO> proImageList = proImageDAO.selectProImageByProShopid(shopId,
                productNo, null);
            if (proImageList.size() > 0) {
                imgUrl = proImageList.get(0).getImg();
                productDo.setProductUrl(imgUrl);
            }
            modelMap.put("proImageList", proImageList);
            
            List<FaretemplateDO> faretemplateList = faretemplateDAO.selectAllFaretemplate(shopId);
            
            int checksize = faretemplateList.size();
            modelMap.put("checksize", checksize);
            
            String templateId = productDo.getTemplateId();
            String[] templateIds = templateId.split(",");
            
//            List<FaretemplateDO> allTemplateIdStr = new ArrayList<FaretemplateDO>();//ʣ���ģ��
            List<FaretemplateDO> templateIdStr = new ArrayList<FaretemplateDO>();//��ӵ��ģ��
            
            for (FaretemplateDO faretemplateDO : faretemplateList) {
            	for (String string : templateIds) {
            		FaretemplateDO faretemplate = new FaretemplateDO();
            		faretemplate.setTemplateId(string);
            		faretemplate.setTemplateName(faretemplateDO.getTemplateName());
                	if(StringUtils.equals(faretemplateDO.getTemplateId(), string)){
                		templateIdStr.add(faretemplate);
                	}
    			}
			}
            for (FaretemplateDO temp : templateIdStr) {
            	for (int i = faretemplateList.size()-1; i >= 0; i--) {
            		if(StringUtils.equals(temp.getTemplateId(), faretemplateList.get(i).getTemplateId())){
            			faretemplateList.remove(i);
            		}
            	}
			}
            String checkPoint = "1";
            if(StringUtils.equals(shopId, "3320170215001717")){
            	checkPoint = "2";
            }
            modelMap.put("checkPoint", checkPoint);
            
            modelMap.put("faretemplateList", faretemplateList);
//            modelMap.put("allTemplateIdStr", allTemplateIdStr);
            modelMap.put("templateIdStr", templateIdStr);
            modelMap.put("product", productDo);
            modelMap.put("shopId", shopId);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return "mng/shopProductEditMng";
    }

    /**
     * �༭��Ʒ��̨
     * 
     * @param request
     * @param modelMap
     * @param image
     * @return
     * @throws Exception
     */
	@RequestMapping("ShopEditProduct.do")
    public Object ShopEditProduct(HttpServletRequest request, ModelMap modelMap,
    		@RequestParam MultipartFile image1,
            @RequestParam MultipartFile[] image2,
            @RequestParam MultipartFile[] image3) throws Exception {
    	String imagePath;
        String productUrl;
        String productWeight  = request.getParameter("weight");
        String templateId  = request.getParameter("templateId");
        String productType = getParameterCheck(request, "s_fatherType");
        String children = getParameterCheck(request, "s_childType");
//        String userId = (String) request.getSession().getAttribute("userId");
        String productNo = getParameterCheck(request, "productNo");
        String productName = getParameterCheck(request, "productName");
        Money price = new Money(getParameterCheck(request, "Price"));
        String shopId = (String) request.getSession().getAttribute("shopId");
        String usePoint = getParameterCheck(request, "usePoint");
        // ������ʽ����
        int stock = 0;
        String sizeFlag = getParameterCheck(request, "sizeFlag");
        if (null != sizeFlag) {
            if (sizeFlag.equals("0")) {
                stock = Integer.parseInt(getParameterCheck(request, "stock"));
            } else if (sizeFlag.equals("1") || sizeFlag.equals("2")) {
                String sizeNumber = getParameterCheck(request, "sizeNumber");// ��ȡ��ʽ��
                if (null != sizeNumber) {
                    for (int i = 0; i <= Integer.parseInt(sizeNumber)-1; i++) {
                        String sizeStock = getParameterCheck(request, String.valueOf(i));
                        // ������ʽ���
                        productParameterDAO.editProductParameterInfo(Integer.parseInt(sizeStock),
                            shopId, productNo, String.valueOf(i));
                        stock += Integer.parseInt(sizeStock);
                    }
                }
            }
        }
        String imgNumber1 = getParameterCheck(request, "imgNumber1");// ������ҳͼƬ��
        String imgNumber2 = getParameterCheck(request, "imgNumber2");// ����bannerͼƬ��
        String imgNumber3 = getParameterCheck(request, "imgNumber3");// ���շ�չʾͼƬ��
        
        // ������ƷͼƬ
        ProImageDO proImageDO = new ProImageDO();
        proImageDO.setShopId(shopId);
        proImageDO.setProductNo(productNo);
        if (null != imgNumber1) {
            proImageDAO.deleteProimage(shopId, productNo);
            if (null != imgNumber2) {
                int numb2 = Integer.parseInt(imgNumber2);
                String[] imageRelativePath2 = new String[numb2];
                for (int i = 0; i < image2.length; i++) {

                    if (null == image2[i].getContentType()
                        || image2[i].getContentType().split("/") == null) {
                        imageRelativePath2[i] = String.valueOf(System.currentTimeMillis()) + ".jpg";//
                    } else {
                        if (image2[i].getContentType().split("/").length > 1) {
                            imageRelativePath2[i] = String.valueOf(System.currentTimeMillis())
                                                    + "."
                                                    + image2[i].getContentType().split("/")[1];//
                        } else {
                            imageRelativePath2[i] = String.valueOf(System.currentTimeMillis())
                                                    + ".jpg";//
                        }
                    }
                    imagePath = IMAGE_FILE + imageRelativePath2[i];
                    ImageZipUtil.compressPic(image2[i], imagePath);
                    imageRelativePath2[i] = IMAGE_PATH + imageRelativePath2[i];
                    proImageDO.setImg(imageRelativePath2[i]);
                    proImageDO.setFlag("2");// banner

                    proImageDAO.insert(proImageDO);
                }
            }
            if (null != imgNumber3) {
                int numb3 = Integer.parseInt(imgNumber3);

                String[] imageRelativePath3 = new String[numb3];
                for (int i = 0; i < image3.length; i++) {

                    if (null == image3[i].getContentType()
                        || image3[i].getContentType().split("/") == null) {
                        imageRelativePath3[i] = String.valueOf(System.currentTimeMillis()) + ".jpg";//
                    } else {
                        if (image3[i].getContentType().split("/").length > 1) {
                            imageRelativePath3[i] = String.valueOf(System.currentTimeMillis())
                                                    + "."
                                                    + image3[i].getContentType().split("/")[1];//
                        } else {
                            imageRelativePath3[i] = String.valueOf(System.currentTimeMillis())
                                                    + ".jpg";//
                        }
                    }
                    imagePath = IMAGE_FILE + imageRelativePath3[i];
                    ImageZipUtil.compressPic(image3[i], imagePath);
                    imageRelativePath3[i] = IMAGE_PATH + imageRelativePath3[i];
                    proImageDO.setImg(imageRelativePath3[i]);
                    proImageDO.setFlag("0");// ����ҳչʾ

                    proImageDAO.insert(proImageDO);
                }
            }

            String imageRelativePath1 = new String();
            if (null == image1.getContentType() || image1.getContentType().split("/") == null) {
                imageRelativePath1 = String.valueOf(System.currentTimeMillis()) + ".jpg";//
            } else {
                if (image1.getContentType().split("/").length > 1) {
                    imageRelativePath1 = String.valueOf(System.currentTimeMillis()) + "."
                                         + image1.getContentType().split("/")[1];//
                } else {
                    imageRelativePath1 = String.valueOf(System.currentTimeMillis()) + ".jpg";//
                }
            }
            imagePath = IMAGE_FILE + imageRelativePath1;
            ImageZipUtil.compressPic(image1, imagePath);
            imageRelativePath1 = IMAGE_PATH + imageRelativePath1;
            proImageDO.setImg(imageRelativePath1);
            proImageDO.setFlag("1");

            proImageDAO.insert(proImageDO);
            productUrl = imageRelativePath1;
        }else{
        	productUrl = proImageDAO.selectProImageByProShopid(shopId, productNo, "1").get(0)
                    .getImg();
        }
        
        // ���� pro_image�� ͼƬ
        proImageDAO.updateProImageByProShopid(productUrl, shopId, productNo, "1");
        
        
        Money nowPrice = price.multiply(productDao.selectProductByProductNo(productNo, shopId)
            .getProductOff());
        double  long1 = 0;
        if(StringUtils.isNotBlank(productWeight)){
        	long1 = Double.valueOf(productWeight);
        }
        int flag = productDao.editProduct(productName, productType, children, nowPrice, price,
        		templateId, stock, productUrl,long1,new Money(usePoint),shopId, productNo);
        modelMap.put("flag", flag);
        
        return "mng/shopProductMng";
    }

    /**
     * ���붩������
     * 
     * @param request
     * @param modelMap
     * @return
     */
    @RequestMapping("shopOrderMng.html")
    public Object shopOrderMng(HttpServletRequest request, ModelMap modelMap) {
        return "mng/shopOrderMng";

    }

    /**
     * ���� �������̨
     * 
     * @param request
     * @param modelMap
     * @return
     */
    @RequestMapping("shopOrderSearchMng.do")
    @ResponseBody
    public Object shopOrderSearchMng(HttpServletRequest request, ModelMap modelMap) {
        JsonPageResult<OrderInfo> commonResult = new JsonPageResult<OrderInfo>(true);
        String shopId = (String) request.getSession().getAttribute("shopId");
        String tab_index = getParameterCheck(request, "tab_index");
        String orderId = getParameterCheck(request, "s_itemNo");
        String addTimeBegin = getParameterCheck(request, "s_addTime_begin");
        String addTimeEnd = getParameterCheck(request, "s_addTime_end");
        Date startDate = new Date();
        Date endDate = new Date();
        // ���ò���
        String delflag = null;
        String sendGoods = null;
        String payStatus = null;
        String orderStatus = null;
        if (null == tab_index || tab_index.equals("0")) {// ���ж���

        } else if (tab_index.equals("1")) {// ��֧��
            payStatus = "1";
            orderStatus = "3";
        } else if (tab_index.equals("2")) {// ������
            sendGoods = "1";
            payStatus = "3";
            orderStatus = "3";
        } else if (tab_index.equals("3")) {// ��ȷ���ջ�
            sendGoods = "2";
            payStatus = "3";
            orderStatus = "3";
        } else if (tab_index.equals("4")) {// �˻��˿�
            sendGoods = "3";
        } else if (tab_index.equals("5")) {// ��ɶ���
            orderStatus = "0";
            payStatus = "3";
            sendGoods = "4";
        }
        int orderCount = 0;
        List<OrderInfo> orderList = new ArrayList<OrderInfo>();
        Map<String, Integer> pageData = getPageData(request);
        try {
            if (null == addTimeBegin || addTimeBegin.isEmpty()) {
                startDate = null;
            } else {
                startDate = DateUtils.parseDate(addTimeBegin, DateUtils.webFormat);
            }
            if (null == addTimeEnd || addTimeEnd.isEmpty()) {
                endDate = null;
            } else {
                endDate = DateUtils.parseDate(addTimeEnd, DateUtils.webFormat);
                Calendar calendar = new GregorianCalendar();
                calendar.setTime(endDate);
                calendar.add(calendar.DATE, 1);// ��������������һ��.����������,������ǰ�ƶ�
                endDate = calendar.getTime();
            }
            orderList = orderDao.selectByShopIDSearchBox(shopId, orderId, delflag, sendGoods,
                payStatus, orderStatus, startDate, endDate, pageData.get(OFFSET),
                pageData.get(PAGE_SIZE_STR));

            orderCount = orderDao.selectCountByShopIDSearchBox(shopId, orderId, delflag, sendGoods,
                payStatus, orderStatus, startDate, endDate);
            for (int i = 0; i < orderList.size(); i++) {
            	orderList.get(i).setLuggage(
                        orderList.get(i).getLuggage()
                            .divide(new BigDecimal(100.00), 2, BigDecimal.ROUND_HALF_UP));//luggage
                orderList.get(i).setOrderPrice(
                    orderList.get(i).getOrderPrice()
                        .divide(new BigDecimal(100.00), 2, BigDecimal.ROUND_HALF_UP));//orderPrice
                if(!orderList.get(i).getOrderStatus().equals("2")){
	                if (orderList.get(i).getPayStatus().equals("1")) {
	                    orderList.get(i).setPayStatus("δ֧��");
	                } else if (orderList.get(i).getPayStatus().equals("2")) {
	                    orderList.get(i).setPayStatus("֧����");
	                } else if (orderList.get(i).getPayStatus().equals("3")
	                           && orderList.get(i).getSendGoods().equals("1")) {
	                    orderList.get(i).setPayStatus("������");
	                } else if (orderList.get(i).getPayStatus().equals("3")
	                           && orderList.get(i).getSendGoods().equals("2")) {
	                    orderList.get(i).setPayStatus("��ȷ���ջ�");
	                } else if (orderList.get(i).getPayStatus().equals("3")
	                           && orderList.get(i).getSendGoods().equals("3")) {
	                    orderList.get(i).setPayStatus("�˻�/�˿�");
	                } else if (orderList.get(i).getPayStatus().equals("3")
	                           && orderList.get(i).getSendGoods().equals("4")
	                           && orderList.get(i).getOrderStatus().equals("0")) {
	                    orderList.get(i).setPayStatus("�����");
	                }
                }else{
                	orderList.get(i).setPayStatus("��ȡ��");
                }
                orderList.get(i).setStringgmtCreat(
                    DateUtils.format(orderList.get(i).getGmtCreat(), "yyyy-MM-dd"));
                orderList.get(i).setShopId(orderList.get(i).getOrderPrice().toString());// �洢�����ܼ�

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        commonResult.setListObject(orderList);
        commonResult.setPageNum(pageData.get(CURRENTPAGE));
        commonResult.setTotalPages(calculatePage(orderCount, pageData.get(PAGE_SIZE_STR)));
        return commonResult;

    }

	@RequestMapping("shopOrderSend.do")
    @ResponseBody
    public Object shopOrderSend(HttpServletRequest request, ModelMap modelMap) {
        String orderId = getParameterCheck(request, "orderId");
        String transportNo = request.getParameter("transportNo");

        orderDao.updateOrderStatusAndOrderSendGoodsByOrderId("3", "2", orderId);
        orderDao.addTransportNoByOrderId(transportNo, orderId);
        try {
            // ���ںŷ�����ʾ���� ����������
        	SysConfigDO sysConfigDO = sysConfigDAO.selectByKey(SysConfigCacheKeyEnum.ACCESS_TOKEN.toString());
			String accessToken = sysConfigDO.getSysValue();
//            String accessToken = sysConfigCacheManager
//                .getConfigValue(SysConfigCacheKeyEnum.ACCESS_TOKEN);
            OrderDO orderDo = orderDao.selectOrdersByOrderId(orderId, "0");
            String productName = productDao.selectByShopIdSearch(orderDo.getProductNo())
                .getProductName();
            UserDO userdo = userDao.selectUserInfoByUserId(orderDo.getUserId());
            String jsonTextMsg = "";
            String notice = "�װ����û������������Ʒ��" + productName + "���Ѿ�����,������Ϊ:" + transportNo;
            //			notice= new String(notice.getBytes("UTF-8"),"GB18030");
            jsonTextMsg = SendCustomMessage.makeTextCustomMessage(userdo.getWechatId(), notice);
            SendCustomMessage.sendCustomMessage(accessToken, jsonTextMsg);
        } catch (Exception e) {
            e.printStackTrace();
        }
        // String flag=intflag>0?"1":"0";
        return "1";
    }

    /**
     * ��Ʒ�������̨
     * 
     * @param request
     * @param modelMap
     * @return
     */
    @RequestMapping("productsSelect.do")
    @ResponseBody
    public Object productsSelect(HttpServletRequest request, ModelMap modelMap) {
        JsonPageResult<ProductDO> commonResult = new JsonPageResult<ProductDO>(true);
        String shopId = (String) request.getSession().getAttribute("shopId");
        String tab_index = request.getParameter("tab_index");
        String productName = request.getParameter("s_itemName");
        String productType = request.getParameter("productType");
        String children = request.getParameter("children");
        String productNo = request.getParameter("s_itemNo");
        String s_price_min = request.getParameter("s_price_min");
        String s_price_max = request.getParameter("s_price_max");
        String s_isTeam = request.getParameter("s_isTeam");// �Ź���ʶ
        // String s_recommendFlg=request.getParameter("s_recommendFlg");//�Ƽ���ʶ
        if (s_isTeam.equals("on")) {
            s_isTeam = null;
        } else if (s_isTeam.equals("1")) {
            s_isTeam = "4";
        }
        Double priceMin;
        Double priceMax;
        if (null == s_price_min || s_price_min.equals("")) {
            priceMin = null;
        } else {
            priceMin = new Double(request.getParameter("s_price_min"));
            priceMin = priceMin * 100;
        }
        if (null == s_price_max || s_price_max.equals("")) {
            priceMax = null;
        } else {
            priceMax = new Double(request.getParameter("s_price_max"));
            priceMax = priceMax * 100;
        }

        String status = null;
        String checkStatus = null;
        if (null == tab_index || tab_index.equals("0")) {
            status = null;
        } else if (tab_index.equals("1")) {
            status = "0";
            checkStatus = "1";
        } else if (tab_index.equals("2")) {
            status = "1";
            checkStatus = "1";
        } else if (tab_index.equals("3")) {
            status = null;
            checkStatus = "0";
        }
        if (null == productName || productName.equals("")) {
            productName = null;
        }
        if (null == productType || productType.equals("0")) {
            productType = null;
        }
        if (null == children || children.equals("0")) {
            children = null;
        }
        if (null == productNo || productNo.equals("")) {
            productNo = null;
        }
        if (null == priceMin || priceMin.equals("")) {
            priceMin = null;
        }
        if (null == priceMax || priceMax.equals("")) {
            priceMax = null;
        }
        if (null == productName || productName.equals("")) {
            productName = null;
        }
        String addTimeBegin = request.getParameter("s_addTime_begin");
        String addTimeEnd = request.getParameter("s_addTime_end");
        Date startDate = new Date();
        Date endDate = new Date();
        Map<String, Integer> pageData = getPageData(request);
        int productCount = 0;
        List<ProductDO> productList = new ArrayList<ProductDO>();
        try {
            if (null == addTimeBegin || addTimeBegin.isEmpty()) {
                startDate = null;
            } else {
                startDate = DateUtils.parseDate(addTimeBegin, DateUtils.webFormat);
            }
            if (null == addTimeEnd || addTimeEnd.isEmpty()) {
                endDate = null;
            } else {
                endDate = DateUtils.parseDate(addTimeEnd, DateUtils.webFormat);
                Calendar calendar = new GregorianCalendar();
                calendar.setTime(endDate);
                calendar.add(calendar.DATE, 1);// ��������������һ��.����������,������ǰ�ƶ�
                endDate = calendar.getTime();
            }
            productList = productDao.selectProductByShopIdSearchBox(shopId, productName,
                productType, children, productNo, status, checkStatus, s_isTeam, priceMin,
                priceMax, startDate, endDate, pageData.get(OFFSET), pageData.get(PAGE_SIZE_STR));
            productCount = productDao.selectCountProductByShopIdSearchBox(shopId, productName,
                productType, children, productNo, status, checkStatus, s_isTeam, priceMin,
                priceMax, startDate, endDate);
//            String imgUrl;
            for (int i = 0; i < productList.size(); i++) {
                productList.get(i).setCreater(
                    DateUtils.format(productList.get(i).getGmtCreate(), "yyyy-MM-dd"));
                productList.get(i).setModifier(productList.get(i).getOldPrice().toString());
                if (productList.get(i).getCheckStatus().equals("0")) {
                    productList.get(i).setCheckStatus("δ���");
                } else if (productList.get(i).getCheckStatus().equals("1")) {
                    if (productList.get(i).getStatus().equals("0")) {
                        productList.get(i).setCheckStatus("������");
                    } else if (productList.get(i).getStatus().equals("1")) {
                        productList.get(i).setCheckStatus("���¼�");
                    } else {
                        productList.get(i).setCheckStatus("���¼�");// Ӧ�ò���������
                    }
                } else if (productList.get(i).getCheckStatus().equals("2")) {
                    productList.get(i).setCheckStatus("δͨ�����");
                } else {
                    productList.get(i).setCheckStatus("δͨ�����");// Ӧ�ò���������
                }
                /*
                 * List<ProImageDO> proImageList=
                 * proImageDAO.selectProImageByProShopid(shopId, productNo,
                 * "1");//productNo û�и�ֵ if(proImageList.size()>0){
                 * imgUrl=proImageList.get(0).getImg();
                 * productList.get(i).setProductUrl(imgUrl); }
                 */

            }
            /*
             * modelMap.put("totalPages", calculatePage(productCount,
             * pageData.get(PAGE_SIZE_STR)));//��ҳ�� modelMap.put("totalItems",
             * productCount);//������ modelMap.put(CURRENTPAGE,
             * pageData.get(CURRENTPAGE));//��ǰҳ��
             */
            request.setAttribute("page", modelMap);
        } catch (Exception e) {
            e.printStackTrace();
        }
        commonResult.setListObject(productList);
        commonResult.setPageNum(pageData.get(CURRENTPAGE));
        commonResult.setTotalPages(calculatePage(productCount, pageData.get(PAGE_SIZE_STR)));
        return commonResult;

    }

    /**
     * �ϼ��¼���Ʒ��̨
     * 
     * @param request
     * @param modelMap
     */
    @RequestMapping("productsUpDown.do")
    @ResponseBody
    public Object productsUpDown(HttpServletRequest request, ModelMap modelMap) {
        String productNo = getParameterCheck(request, "productNo");
        String shopId = (String) request.getSession().getAttribute("shopId");
        String flag = getParameterCheck(request, "flag");
        
        int UpDownflag = 0;
        if (flag.equals("1")) {
            UpDownflag = productDao.updateProductUpDownSTATUS("0", shopId, productNo);
        } else if (flag.equals("0")) {
        	//�¼�
            UpDownflag = productDao.updateProductUpDownSTATUS("1", shopId, productNo);
            //�ж���Ʒ�ǲ���ũ�徭������Ʒ
            List<ProductDO> products = productDao.selectProductByProNo(productNo);
            for (ProductDO productDO : products) {
            	if(StringUtils.endsWith(productDO.getRecommendFlg(), "6")){
            		UpDownflag = productDao.updateProductUpDownSTATUS("1", productDO.getShopId(), productDO.getProductNo());
            	}
    		}
        }
        modelMap.put("UpDownflag", UpDownflag);
        return UpDownflag;
    }

    /**
     * ɾ����Ʒ
     * 
     * @param request
     * @param modelMap
     * @return
     */
    @RequestMapping("productsDelect.do")
    @ResponseBody
    public Object productsDelect(HttpServletRequest request, ModelMap modelMap) {
        String productNo = getParameterCheck(request, "productNo");
        String shopId = (String) request.getSession().getAttribute("shopId");

        int deleteFlag = productDao.updateProductDelflag("1", shopId, productNo);

        modelMap.put("UpDownflag", deleteFlag);
        return deleteFlag;
    }
    
    

}
