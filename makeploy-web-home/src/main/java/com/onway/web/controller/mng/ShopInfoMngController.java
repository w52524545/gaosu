package com.onway.web.controller.mng;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.onway.common.lang.Money;
import com.onway.common.lang.StringUtils;
import com.onway.makeploy.common.dal.dataobject.ActivityDO;
import com.onway.makeploy.common.dal.dataobject.CommerceCheckDO;
import com.onway.makeploy.common.dal.dataobject.CommerceDO;
import com.onway.makeploy.common.dal.dataobject.OrderDO;
import com.onway.makeploy.common.dal.dataobject.PartnerRankDO;
import com.onway.makeploy.common.dal.dataobject.RoleDO;
import com.onway.makeploy.common.dal.dataobject.ShopAboutUnion;
import com.onway.makeploy.common.dal.dataobject.ShopDO;
import com.onway.makeploy.common.dal.dataobject.UserDO;
import com.onway.makeploy.core.localcache.enums.SysConfigCacheKeyEnum;
import com.onway.platform.common.configration.ConfigrationFactory;
import com.onway.web.controller.AbstractController;
import com.onway.web.controller.ImageZipUtil;
import com.onway.web.controller.UUIDHexGenerator;
import com.onway.web.controller.result.JsonResult;
import com.onway.web.controller.template.ControllerCallBack;

/**
 * @author southRain
 *
 */

@Controller
public class ShopInfoMngController extends AbstractController {
	
	 private static final String IMAGE_FILE           = ConfigrationFactory.getConfigration()
             .getPropertyValue(
                 "user_img_upload_realpath");

     private static final String IMAGE_PATH           = ConfigrationFactory.getConfigration()
             .getPropertyValue("user_img_path");

    //进入店铺信息查询页面
    @RequestMapping("/shopInfoMng.html")
    public String shopMng(HttpServletRequest request, ModelMap modelMap) {
    	String userIds = (String)request.getSession().getAttribute("userId");
    	boolean checkRoleBoss = checkRoleBoss(userIds);
    	if(checkRoleBoss == false){
    		return "mng/loginMng";
    	}
    	//判断是否为运营者
        int checkOperative = checkOperative(userIds);
        modelMap.put("checkOperative", checkOperative);
        String shopId = request.getParameter("SHOP_ID");
        String userId = request.getParameter("USER_ID");
        String unionflag = request.getParameter("unionflag");
        String industries = request.getParameter("industry");
        if ("".equals(unionflag) || null == unionflag || "1".equals(unionflag)) {
            unionflag = "1";
        }
        Map<String, Integer> pageDate = getPageData(request);
        try {
            List<ShopDO> shop = shopDAO.selectShopInfo(shopId, userId, pageDate.get(OFFSET) / 2,
                pageDate.get(PAGE_SIZE_STR) / 2, unionflag, industries);
            List<ShopAboutUnion> shopList = new ArrayList<ShopAboutUnion>();
            for (ShopDO ss : shop) {
                ActivityDO upUnion = activityDao.selectByTitle(ss.getShopId());
                if (null == upUnion) {
                    ShopAboutUnion sau = new ShopAboutUnion();
                    sau.setShopId(ss.getShopId());
                    sau.setUserId(ss.getUserId());
                    sau.setShopName(ss.getShopName());
                    sau.setCell(ss.getCell());
                    sau.setShopStar(ss.getShopStar());
                    sau.setSellCount(ss.getSellCount());
                    sau.setAttentionCount(ss.getAttentionCount());
                    sau.setCollectCount(ss.getCollectCount());
                    sau.setShopAddr(ss.getShopAddr());
                    sau.setShopDec(ss.getShopDec());
                    sau.setUnionFlg(ss.getUnionFlg());
                    sau.setHomeUnion("0");
                    sau.setCreater(ss.getCreater());
                    sau.setShopUp(ss.getShopUp());
                    sau.setDelFlag(ss.getDelFlag());
                    shopList.add(sau);
                } else {
                    ShopAboutUnion sau = new ShopAboutUnion();
                    sau.setShopId(ss.getShopId());
                    sau.setUserId(ss.getUserId());
                    sau.setShopName(ss.getShopName());
                    sau.setCell(ss.getCell());
                    sau.setShopStar(ss.getShopStar());
                    sau.setSellCount(ss.getSellCount());
                    sau.setAttentionCount(ss.getAttentionCount());
                    sau.setCollectCount(ss.getCollectCount());
                    sau.setShopAddr(ss.getShopAddr());
                    sau.setShopDec(ss.getShopDec());
                    sau.setUnionFlg(ss.getUnionFlg());
                    sau.setHomeUnion("1");
                    sau.setCreater(ss.getCreater());
                    sau.setShopUp(ss.getShopUp());
                    sau.setDelFlag(ss.getDelFlag());
                    shopList.add(sau);
                }
            }

            int count = (int) shopDAO.selectShopInfoCount(shopId, userId, unionflag,industries);

            modelMap.put("totalPages", calculatePage(count, pageDate.get(PAGE_SIZE_STR) / 2)); // 传输总页数
            modelMap.put(CURRENTPAGE, pageDate.get(CURRENTPAGE));//当前页数
            modelMap.put("totalItems", count);// 传输总条数
            modelMap.put("shopInfoList", shopList);
            modelMap.put("shopId", shopId);
            modelMap.put("userId", userId);
            modelMap.put("unionflag", unionflag);
            modelMap.put("industries", industries);
            
            String classification = sysConfigCacheManager.getConfigValue(SysConfigCacheKeyEnum.CLASSIFICATION_INDUSTRY);
            String[] claifi = classification.split(",");
            List<String> industry = new ArrayList<String>();
            for (int i = 0; i < claifi.length; i++) {
    			String indu = claifi[i];
    			industry.add(indu);
    		}
            modelMap.put("industry", industry);
            
            request.setAttribute("page", modelMap);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "mng/shopInfoMng";
    }

    @RequestMapping("toUnion.do")
    @ResponseBody
    public Object toUnion(HttpServletRequest request, ModelMap map) {
        JsonResult result = new JsonResult(false, null, "升级为联盟商家失败!");
        String shopId = request.getParameter("shopId");
        int i = shopDAO.updateUnion("1", shopId);
        if (i > 0) {
            result.setErrMsg("升级为联盟商家成功!");
            result.setBizSucc(true);
        }

        return result;
    }

    @RequestMapping("toHome.do")
    @ResponseBody
    public Object toHome(HttpServletRequest request, ModelMap map) {
        JsonResult result = new JsonResult(false, null, "升级为联盟商家失败!");
        String shopId = request.getParameter("shopId");
        String flag = request.getParameter("flag");
        if (flag.equals("1")) {
            int count = activityDao.countUnion();
            if (count >= 3) {
                result.setErrCode("首页联盟商家已满3个");
                result.setBizSucc(false);
            } else {
                ActivityDO newUnion = new ActivityDO();
                newUnion.setTitle(shopId);
                newUnion.setType("8");
                newUnion.setState("1");
                activityDao.addInfo(newUnion);
                result.setErrMsg("置于首页成功!");
                result.setBizSucc(true);
            }
        } else {
            int i = activityDao.deleteHomeUnion(shopId);
            if (i > 0) {
                result.setErrMsg("撤离首页成功!");
                result.setBizSucc(true);
            }

        }

        return result;
    }
    
    @RequestMapping("toUp.do")
    @ResponseBody
    public Object toUp(HttpServletRequest request, ModelMap map) {
        JsonResult result = new JsonResult(false, null, "放置前列失败!");
        String shopId = request.getParameter("shopId");
        String flag = request.getParameter("flag");
        //放置前列
        if (flag.equals("1")) {
            int i = shopDAO.setShopUp("1", shopId);
            if(i >= 1){
            	result.setBizSucc(true);
            	result.setErrMsg("放置前列成功");
            }
        }
        //撤离前列
        else {
        	int i = shopDAO.setShopUp(null, shopId);
            if(i >= 1){
            	result.setBizSucc(true);
            	result.setErrMsg("撤离前列成功");
            }
        }

        return result;
    }
    
    @RequestMapping("delFlag.do")
    @ResponseBody
    public Object delFlag(HttpServletRequest request, ModelMap map) {
        JsonResult result = new JsonResult(false, null, "操作失败!");
        String shopId = request.getParameter("shopId");
        String flag = request.getParameter("flag");
        //激活
        if (flag.equals("1")) {
            int i = shopDAO.changeShopDelFlag("1", shopId);
            productDao.downProductOrderByShop("0", shopId);//上架商品
            if(i >= 1){
            	result.setBizSucc(true);
            	result.setErrMsg("操作成功");
            }
        }
        //撤销
        else {
        	int i = shopDAO.changeShopDelFlag("0", shopId);
        	productDao.downProductOrderByShop("1", shopId);//下架商品
            if(i >= 1){
            	result.setBizSucc(true);
            	result.setErrMsg("操作成功");
            }
        }

        return result;
    }
    
    //进入修改页面
    @RequestMapping("/changeShopLevel.html")
    public String changeShopLevel(HttpServletRequest request, ModelMap modelMap) {
    	String userIds = (String)request.getSession().getAttribute("userId");
    	boolean checkRoleBoss = checkRoleBoss(userIds);
    	if(checkRoleBoss == false){
    		return "mng/loginMng";
    	}
    	//判断是否为运营者
        int checkOperative = checkOperative(userIds);
        modelMap.put("checkOperative", checkOperative);
    	String shopId = request.getParameter("shopId");
    	ShopDO shopDO = shopDAO.selectShopByShopId(shopId);
    	modelMap.put("shopDO", shopDO);
        return "mng/changeShopLevel";
    }
    
    @RequestMapping("changeShopLevel.do")
    @ResponseBody
    public ModelAndView changeShopLevelDo(HttpServletRequest request, ModelMap map) {
        String shopId = request.getParameter("shopId");
        String shopLevel = request.getParameter("shopLevel");
        
        int levelResult = shopDAO.updateShopLevel(shopLevel, shopId);
        if(levelResult >= 1){
        	return new ModelAndView("mng/shopInfoMng");
        }

        return new ModelAndView("mng/changeShopLevel");
    }

    
    /**
     * 进入商会列表
     * @param request
     * @param modelMap
     * @return
     */
    @RequestMapping("/allcommerce.html")
    public String allcommerce(HttpServletRequest request, ModelMap modelMap) {
    	String userIds = (String)request.getSession().getAttribute("userId");
    	boolean checkRoleBoss = checkRoleBoss(userIds);
    	if(checkRoleBoss == false){
    		return "mng/loginMng";
    	}
    	//判断是否为运营者
        int checkOperative = checkOperative(userIds);
        modelMap.put("checkOperative", checkOperative);
        String commerceCell = request.getParameter("commerceCell");
        Map<String, Integer> pageDate = getPageData(request);
        try {
        	List<CommerceDO> allCommerceList = commerceDAO.selectAllCommerceByCell(commerceCell, pageDate.get(OFFSET) / 2,  pageDate.get(PAGE_SIZE_STR) / 2);
        	List<CommerceResult>  list = new ArrayList<CommerceResult>();
        	for (CommerceDO commerceDO : allCommerceList) {
        		CommerceResult result = new CommerceResult();
        		String commerceId = commerceDO.getCommerceId();
        		String leaderId = commerceDO.getCommerceLeader();//主席
    	    	String chargeId = commerceDO.getCommerceCharge();//负责人
    	    	String secretaryId = commerceDO.getCommerceSecretaryUser();//秘书长
    	    	
    	    	CommerceCheckDO leaderCheckUser = null;
    	    	CommerceCheckDO chargeCheckUser = null;
    	    	CommerceCheckDO secretaryCheckUser = null;
    	    	if(!StringUtils.isBlank(leaderId)){
    	    		leaderCheckUser = commerceCheckDAO.selectMyCommerceCheck(leaderId,commerceId);
    	    	}
    	    	if(!StringUtils.isBlank(chargeId)){
    	    		chargeCheckUser = commerceCheckDAO.selectMyCommerceCheck(chargeId,commerceId);
    	    	}
    	    	if(!StringUtils.isBlank(secretaryId)){
    	    		secretaryCheckUser = commerceCheckDAO.selectMyCommerceCheck(secretaryId,commerceId);
    	    	}
    	    	
        		result.setCommerceDO(commerceDO);
        		result.setLeaderCheckUser(leaderCheckUser);
        		result.setChargeCheckUser(chargeCheckUser);
        		result.setSecretaryCheckUser(secretaryCheckUser);
        		
        		String viceLeaderId = commerceDO.getCommerceViceLeader();//副主席
    	    	List<CommerceCheckDO> viceCheckLeader = new ArrayList<CommerceCheckDO>();
    	    	if(!StringUtils.isBlank(viceLeaderId)){
	    	    	String[] viceStr = viceLeaderId.split(",");
	    	    	for (String viceStrId : viceStr) {
	    				if(!StringUtils.isBlank(viceStrId)){
	    					CommerceCheckDO viceCheck = commerceCheckDAO.selectMyCommerceCheck(viceStrId,commerceId);
	    					viceCheckLeader.add(viceCheck);
	    				}
	    			}
    	    	}
    	    	result.setViceCheckLeader(viceCheckLeader);
    	    	
    	    	String executiveChairmanId = commerceDO.getCommerceExecutiveChairman();//执行会长COMMERCE_EXECUTIVE_CHAIRMAN
    	    	List<CommerceCheckDO> executiveChairmanCheckLeader = new ArrayList<CommerceCheckDO>();
    	    	if(!StringUtils.isBlank(executiveChairmanId)){
	    	    	String[] viceStr = executiveChairmanId.split(",");
	    	    	for (String viceStrId : viceStr) {
	    				if(!StringUtils.isBlank(viceStrId)){
	    					CommerceCheckDO executiveChairmanCheck = commerceCheckDAO.selectMyCommerceCheck(viceStrId,commerceId);
	    					executiveChairmanCheckLeader.add(executiveChairmanCheck);
	    				}
	    			}
    	    	}
    	    	result.setExecutiveChairmanCheckLeader(executiveChairmanCheckLeader);
    	    	
    	    	String standingPresidentId = commerceDO.getCommerceStandingPresident();//常务会长COMMERCE_STANDING_PRESIDENT
    	    	List<CommerceCheckDO> standingPresidentCheckLeader = new ArrayList<CommerceCheckDO>();
    	    	if(!StringUtils.isBlank(standingPresidentId)){
	    	    	String[] viceStr = standingPresidentId.split(",");
	    	    	for (String viceStrId : viceStr) {
	    				if(!StringUtils.isBlank(viceStrId)){
	    					CommerceCheckDO standingPresidentCheck = commerceCheckDAO.selectMyCommerceCheck(viceStrId,commerceId);
	    					standingPresidentCheckLeader.add(standingPresidentCheck);
	    				}
	    			}
    	    	}
    	    	result.setStandingPresidentCheckLeader(standingPresidentCheckLeader);
    	    	
    	    	String honoraryChairmanId = commerceDO.getCommerceHonoraryChairman();//名誉会长COMMERCE_HONORARY_CHAIRMAN
    	    	List<CommerceCheckDO> honoraryChairmanCheckLeader = new ArrayList<CommerceCheckDO>();
    	    	if(!StringUtils.isBlank(honoraryChairmanId)){
	    	    	String[] viceStr = honoraryChairmanId.split(",");
	    	    	for (String viceStrId : viceStr) {
	    				if(!StringUtils.isBlank(viceStrId)){
	    					CommerceCheckDO honoraryChairmanCheck = commerceCheckDAO.selectMyCommerceCheck(viceStrId,commerceId);
	    					honoraryChairmanCheckLeader.add(honoraryChairmanCheck);
	    				}
	    			}
    	    	}
    	    	result.setHonoraryChairmanCheckLeader(honoraryChairmanCheckLeader);
    	    	
    	    	String executiveDirectorId = commerceDO.getCommerceExecutiveDirector();//常务董事COMMERCE_EXECUTIVE_DIRECTOR
    	    	List<CommerceCheckDO> executiveDirectorCheckLeader = new ArrayList<CommerceCheckDO>();
    	    	if(!StringUtils.isBlank(executiveDirectorId)){
	    	    	String[] viceStr = executiveDirectorId.split(",");
	    	    	for (String viceStrId : viceStr) {
	    				if(!StringUtils.isBlank(viceStrId)){
	    					CommerceCheckDO executiveDirectorCheck = commerceCheckDAO.selectMyCommerceCheck(viceStrId,commerceId);
	    					executiveDirectorCheckLeader.add(executiveDirectorCheck);
	    				}
	    			}
    	    	}
    	    	result.setExecutiveDirectorCheckLeader(executiveDirectorCheckLeader);
    	    	
    	    	String managingDirectorId = commerceDO.getCommerceManagingDirector();//常务理事COMMERCE_MANAGING_DIRECTOR
    	    	List<CommerceCheckDO> managingDirectorCheckLeader = new ArrayList<CommerceCheckDO>();
    	    	if(!StringUtils.isBlank(managingDirectorId)){
	    	    	String[] viceStr = managingDirectorId.split(",");
	    	    	for (String viceStrId : viceStr) {
	    				if(!StringUtils.isBlank(viceStrId)){
	    					CommerceCheckDO managingDirectorCheck = commerceCheckDAO.selectMyCommerceCheck(viceStrId,commerceId);
	    					managingDirectorCheckLeader.add(managingDirectorCheck);
	    				}
	    			}
    	    	}
    	    	result.setManagingDirectorCheckLeader(managingDirectorCheckLeader);
    	    	
    	    	String executiveId = commerceDO.getCommerceExecutive();//执行董事COMMERCE_EXECUTIVE
    	    	List<CommerceCheckDO> executiveCheckLeader = new ArrayList<CommerceCheckDO>();
    	    	if(!StringUtils.isBlank(executiveId)){
	    	    	String[] viceStr = executiveId.split(",");
	    	    	for (String viceStrId : viceStr) {
	    				if(!StringUtils.isBlank(viceStrId)){
	    					CommerceCheckDO executiveCheck = commerceCheckDAO.selectMyCommerceCheck(viceStrId,commerceId);
	    					executiveCheckLeader.add(executiveCheck);
	    				}
	    			}
    	    	}
    	    	result.setExecutiveCheckLeader(executiveCheckLeader);
    	    	
    	    	String secretaryPartyId = commerceDO.getCommerceSecretaryParty();//党支部书记COMMERCE_SECRETARY_PARTY
    	    	List<CommerceCheckDO> secretaryPartyCheckLeader = new ArrayList<CommerceCheckDO>();
    	    	if(!StringUtils.isBlank(secretaryPartyId)){
	    	    	String[] viceStr = secretaryPartyId.split(",");
	    	    	for (String viceStrId : viceStr) {
	    				if(!StringUtils.isBlank(viceStrId)){
	    					CommerceCheckDO secretaryPartyCheck = commerceCheckDAO.selectMyCommerceCheck(viceStrId,commerceId);
	    					secretaryPartyCheckLeader.add(secretaryPartyCheck);
	    				}
	    			}
    	    	}
    	    	result.setSecretaryPartyCheckLeader(secretaryPartyCheckLeader);
    	    	
    	    	
        		list.add(result);
			}
        	
        	int count = commerceDAO.selectCommerceCountByCell(commerceCell);

            modelMap.put("totalPages", calculatePage(count, pageDate.get(PAGE_SIZE_STR) / 2)); // 传输总页数
            modelMap.put(CURRENTPAGE, pageDate.get(CURRENTPAGE));//当前页数
            modelMap.put("totalItems", count);// 传输总条数
            modelMap.put("allCommerceList", list);
            modelMap.put("commerceCell", commerceCell);
            
            request.setAttribute("page", modelMap);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "mng/allcommerce";
    }
    
	@RequestMapping("commerceUser.html")
	public String commerceUser(HttpServletRequest request, ModelMap map) {
		// String userIds = (String)request.getSession().getAttribute("userId");
		// boolean checkRoleBoss = checkRoleBoss(userIds);
		// if(checkRoleBoss == false){
		// return "mng/loginMng";
		// }
		// //判断是否为运营者
		// int checkOperative = checkOperative(userIds);
		// map.put("checkOperative", checkOperative);
		String commerceId = request.getParameter("commerceId");
		String check = request.getParameter("check");
		map.put("check", check);

		int totalItems = userDao.searchCommerceUserCount(commerceId);

		Map<String, Integer> pageData = getPageData(request);
		List<UserDO> commerceUser = userDao.searchCommerceUser(commerceId,
				pageData.get(OFFSET), pageData.get(PAGE_SIZE_STR));
		List<CommerceResult> list = new ArrayList<CommerceResult>();
		int checkLeaderUser = 0;
		int checkViceLeaderUser = 0;
		int checkChargeUser = 0;
		int checkSecretaryUser = 0;
		int checkExecutiveChairmanUser = 0;
		int checkStandingPresidentUser = 0;
		int checkHonoraryChairmanUser = 0;
		int checkExecutiveDirectorUser = 0;
		int checkManagingDirectorUser = 0;
		int checkExecutiveUser = 0;
		int checkSecretaryPartyUser = 0;
		for (UserDO userDO : commerceUser) {
			CommerceResult result = new CommerceResult();
			result.setUserDO(userDO);

			String userId = userDO.getUserId();

			CommerceCheckDO commerceCheckDO = commerceCheckDAO
					.selectMyCommerceCheck(userId, commerceId);
			result.setCommerceCheckDO(commerceCheckDO);

			// 判断是否为会长
			CommerceDO checkLeader = commerceDAO.checkLeader(userId, null,
					null, null, null, null, null, null, null, null, null,
					commerceId);
			if (null != checkLeader) {
				checkLeaderUser = 1;
				result.setCheckLeaderUser(checkLeaderUser);
			}
			// 判断是否为副会长
			CommerceDO checkViceLeader = commerceDAO.checkLeader(null, userId,
					null, null, null, null, null, null, null, null, null,
					commerceId);
			if (null != checkViceLeader) {
				checkViceLeaderUser = 1;
				result.setCheckViceLeaderUser(checkViceLeaderUser);
			}
			// 判断是否为负责人
			CommerceDO checkCharge = commerceDAO.checkLeader(null, null,
					userId, null, null, null, null, null, null, null, null,
					commerceId);
			if (null != checkCharge) {
				checkChargeUser = 1;
				result.setCheckChargeUser(checkChargeUser);
			}
			// 判断是否为秘书长
			CommerceDO checkSecretary = commerceDAO.checkLeader(null, null,
					null, userId, null, null, null, null, null, null, null,
					commerceId);
			if (null != checkSecretary) {
				checkSecretaryUser = 1;
				result.setCheckSecretaryUser(checkSecretaryUser);
			}
			// 判断是否为执行会长COMMERCE_EXECUTIVE_CHAIRMAN commerceExecutiveChairman
			CommerceDO checkExecutiveChairman = commerceDAO.checkLeader(null,
					null, null, null, userId, null, null, null, null, null,
					null, commerceId);
			if (null != checkExecutiveChairman) {
				checkExecutiveChairmanUser = 1;
				result.setCheckExecutiveChairmanUser(checkExecutiveChairmanUser);
			}
			// 判断是否为常务会长COMMERCE_STANDING_PRESIDENT commerceStandingPresident
			CommerceDO checkStandingPresident = commerceDAO.checkLeader(null,
					null, null, null, null, userId, null, null, null, null,
					null, commerceId);
			if (null != checkStandingPresident) {
				checkStandingPresidentUser = 1;
				result.setCheckStandingPresidentUser(checkStandingPresidentUser);
			}
			// 判断是否为名誉会长COMMERCE_HONORARY_CHAIRMAN commerceHonoraryChairman
			CommerceDO checkHonoraryChairman = commerceDAO.checkLeader(null,
					null, null, null, null, null, userId, null, null, null,
					null, commerceId);
			if (null != checkHonoraryChairman) {
				checkHonoraryChairmanUser = 1;
				result.setCheckHonoraryChairmanUser(checkHonoraryChairmanUser);
			}
			// 判断是否为常务董事COMMERCE_EXECUTIVE_DIRECTOR commerceExecutiveDirector
			CommerceDO checkExecutiveDirector = commerceDAO.checkLeader(null,
					null, null, null, null, null, null, userId, null, null,
					null, commerceId);
			if (null != checkExecutiveDirector) {
				checkExecutiveDirectorUser = 1;
				result.setCheckExecutiveDirectorUser(checkExecutiveDirectorUser);
			}
			// 判断是否为常务理事COMMERCE_MANAGING_DIRECTOR commerceManagingDirector
			CommerceDO checkManagingDirector = commerceDAO.checkLeader(null,
					null, null, null, null, null, null, null, userId, null,
					null, commerceId);
			if (null != checkManagingDirector) {
				checkManagingDirectorUser = 1;
				result.setCheckManagingDirectorUser(checkManagingDirectorUser);
			}
			// 判断是否为执行董事COMMERCE_EXECUTIVE commerceExecutive
			CommerceDO checkExecutive = commerceDAO.checkLeader(null, null,
					null, null, null, null, null, null, null, userId, null,
					commerceId);
			if (null != checkExecutive) {
				checkExecutiveUser = 1;
				result.setCheckExecutiveUser(checkExecutiveUser);
			}
			// 判断是否为党支部书记COMMERCE_SECRETARY_PARTY commerceSecretaryParty
			CommerceDO checkSecretaryParty = commerceDAO.checkLeader(null,
					null, null, null, null, null, null, null, null, null,
					userId, commerceId);
			if (null != checkSecretaryParty) {
				checkSecretaryPartyUser = 1;
				result.setCheckSecretaryPartyUser(checkSecretaryPartyUser);
			}
			list.add(result);
		}

		map.put("totalPages",
				calculatePage(totalItems, pageData.get(PAGE_SIZE_STR)));
		map.put(CURRENTPAGE, pageData.get(CURRENTPAGE));
		map.put("totalItems", totalItems);
		map.put("userInfo", list);

		map.put("commerceId", commerceId);

		request.setAttribute("page", map);
		return "mng/commerceUser";
	}
    
    /**
	 * 添加商会
	 * @param request
	 * @param modelMap
	 * @return
	 */
	@RequestMapping("addcommerce.html")
	public String addcommerce(HttpServletRequest request,ModelMap modelMap){
		String userIds = (String)request.getSession().getAttribute("userId");
    	boolean checkRoleBoss = checkRoleBoss(userIds);
    	if(checkRoleBoss == false){
    		return "mng/loginMng";
    	}
    	//判断是否为运营者
        int checkOperative = checkOperative(userIds);
        modelMap.put("checkOperative", checkOperative);
		
		return "mng/addcommerce";
	}
	
	/**
	 * 跳转会员录入商会页面
	 * @param request
	 * @param modelMap
	 * @return
	 */
	@RequestMapping("/addUserCommerce.html")
    public String addUserCommerce(HttpServletRequest request, ModelMap modelMap) {
    	String userIds = (String)request.getSession().getAttribute("userId");
    	boolean checkRoleBoss = checkRoleBoss(userIds);
    	if(checkRoleBoss == false){
    		return "mng/loginMng";
    	}
    	//判断是否为运营者
        int checkOperative = checkOperative(userIds);
        modelMap.put("checkOperative", checkOperative);
        
        String commerceCell = request.getParameter("commerceCell");
        
        //入会人Id
        String userId = request.getParameter("userIds");
        modelMap.put("userId", userId);
        
        Map<String, Integer> pageDate = getPageData(request);
        try {
        	List<CommerceDO> allCommerceList = commerceDAO.selectAllCommerceByCell(commerceCell, pageDate.get(OFFSET),  pageDate.get(PAGE_SIZE_STR));
        	List<CommerceResult> list = new ArrayList<CommerceResult>();
        	UserDO userDO = userDao.selectUserInfoByUserId(userId);
        	for (CommerceDO commerceDO : allCommerceList) {
        		CommerceResult commerceResult = new CommerceResult();
	        	if(null != userDO){
	        		String userCommerceId = userDO.getCommerceId();
	        		
	        		if(StringUtils.contains(userCommerceId, commerceDO.getCommerceId())){
	        			commerceResult.setCheckNewUser(1);
	        		}
	        		commerceResult.setCommerceDO(commerceDO);
	            }
	        	list.add(commerceResult);
        	}
        	
        	int count = commerceDAO.selectCommerceCountByCell(commerceCell);

            modelMap.put("totalPages", calculatePage(count, pageDate.get(PAGE_SIZE_STR))); // 传输总页数
            modelMap.put(CURRENTPAGE, pageDate.get(CURRENTPAGE));//当前页数
            modelMap.put("totalItems", count);// 传输总条数
            modelMap.put("allCommerceList", list);
            modelMap.put("commerceCell", commerceCell);
            
            request.setAttribute("page", modelMap);
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        return "mng/addUserCommerce";
    }
	
	/**
	 * 添加新商会
	 * @param request
	 * @return
	 */
	@RequestMapping("newcommerce.do")
    public ModelAndView newcommerce(HttpServletRequest request, ModelMap modelMap,
                                       @RequestParam MultipartFile image1) throws Exception {
        String imagePath;
        String commerceName  = request.getParameter("commerceName");
        String commerceCell  = request.getParameter("commerceCell");
        String commerceAddr = request.getParameter("commerceAddr");
        String commerceDesc = request.getParameter("commerceDesc");
        String message = request.getParameter("message");

        String imgNumber1 = getParameterCheck(request, "imgNumber1");// 接收首页图片数
        
        CommerceDO commerceDO = new CommerceDO();
        
        commerceDO.setCommerceId(UUIDHexGenerator.getNum());
        commerceDO.setCommerceName(commerceName);
        commerceDO.setCommerceCell(commerceCell);
        commerceDO.setCommerceAddr(commerceAddr);
        commerceDO.setCommerceDesc(commerceDesc);
        commerceDO.setMessage(message);
        commerceDO.setDelFlg("0");
        
        if (null != imgNumber1) {
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
            commerceDO.setCommerceUrl(imageRelativePath1);
        }
        
        int flag1 = commerceDAO.creatNewCommerce(commerceDO);
        modelMap.put("flag", flag1);
        
        return new ModelAndView("mng/commerceAddsuccess");
    }
	
	/**
	 * 后台自定义录入会员
	 * @param request
	 * @param modelMap
	 * @param image1
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("addNewCommerceUser.do")
    @ResponseBody
    public Object addNewCommerceUser(HttpServletRequest request, ModelMap map) {
        JsonResult result = new JsonResult(false, null, "录入失败!");
        String commerceId = request.getParameter("commerceId");
        String userId = request.getParameter("userId");
        
        // 查询该商会是否存在
        CommerceDO commerceDO = commerceDAO.selectCommerceInfoByCommerceId(commerceId);
     	UserDO userDO = userDao.selectUserInfoByUserId(userId);
     	if (null != commerceDO && null != userDO) {
			String userCommerceID = userDO.getCommerceId();
			int i = 0;
			if (StringUtils.isEmpty(userCommerceID)) {
				i = userDao.updateUserCommerce(commerceId, userId);
			} else {
				String newId = userCommerceID + "," + commerceId;
				i = userDao.updateUserCommerce(newId, userId);
			}
			int newpeople = commerceDO.getPeopleNum() + 1;
			int j = commerceDAO.updateCommercePeopleNum(newpeople,
					commerceId);
			int k = 0;
			//判断之前是否有过申请
			CommerceCheckDO checkDO = commerceCheckDAO.selectMyCommerceCheck(userId, commerceId);
			if(null == checkDO){
				CommerceCheckDO commerceCheckDO = new CommerceCheckDO();
				
				commerceCheckDO.setUserId(userId);
				commerceCheckDO.setCommerceId(commerceId);
				commerceCheckDO.setUserName(userDO.getUserName());
				commerceCheckDO.setUserCell(userDO.getCell());
				commerceCheckDO.setStatus("1");//审核通过
				
				k = commerceCheckDAO.addCommerceCheck(commerceCheckDO);
				
			}else{
                CommerceCheckDO commerceCheckDO = new CommerceCheckDO();
				
				commerceCheckDO.setUserId(userId);
				commerceCheckDO.setCommerceId(commerceId);
				commerceCheckDO.setStatus("1");//审核通过
				
				k = commerceCheckDAO.updateCommerceCheck(commerceCheckDO);
			}

			
			if (i >= 1 && j >= 1 && k >= 1) {
				result.setBizSucc(true);
				result.setErrMsg("通过成功");
			}
			
			StringBuffer info = new StringBuffer().append(
					userDO.getNickName()).append("刚刚加入").append(commerceDO.getCommerceName());
			String title = "新入会";
			ActivityDO noticeInfo = new ActivityDO();
			noticeInfo.setTitle(title);
			noticeInfo.setInfo(info.toString());
			noticeInfo.setType("10");
			noticeInfo.setState("0");// 新发布的公告默认置顶
			activityDao.addInfo(noticeInfo);
		}
     	
     	
        return result;
    }
	
	/**
	 * 修改
	 * @param request
	 * @param modelMap
	 * @return
	 */
	@RequestMapping("updateCommerce.html")
	public String updateCommerce(HttpServletRequest request,ModelMap modelMap){
//		String userIds = (String)request.getSession().getAttribute("userId");
//    	boolean checkRoleBoss = checkRoleBoss(userIds);
//    	if(checkRoleBoss == false){
//    		return "mng/loginMng";
//    	}
//    	//判断是否为运营者
//        int checkOperative = checkOperative(userIds);
//        modelMap.put("checkOperative", checkOperative);
        
        String commerceId = request.getParameter("commerceId");
        CommerceDO commerceDO = commerceDAO.selectCommerceInfoByCommerceId(commerceId);
        
        modelMap.put("commerceDO", commerceDO);
		
		return "mng/updateCommerce";
	}
	
	/**
	 * 修改商会
	 * @param request
	 * @return
	 */
	@RequestMapping("updateCommerce.do")
    public ModelAndView updateCommerceDo(HttpServletRequest request, ModelMap modelMap,
                                       @RequestParam MultipartFile image1) throws Exception {
        String imagePath;
        String commerceId  = request.getParameter("commerceId");
        String commerceName  = request.getParameter("commerceName");
        String commerceCell  = request.getParameter("commerceCell");
        String commerceAddr = request.getParameter("commerceAddr");
        String commerceDesc = request.getParameter("commerceDesc");
        String message = request.getParameter("message");
        
		String imageRelativePath1 = new String();
		if(image1.getSize() > 0){
			if (null == image1.getContentType()
					|| image1.getContentType().split("/") == null) {
				imageRelativePath1 = String.valueOf(System.currentTimeMillis())
						+ ".jpg";//
			} else {
				if (image1.getContentType().split("/").length > 1) {
					imageRelativePath1 = String.valueOf(System.currentTimeMillis())
							+ "." + image1.getContentType().split("/")[1];//
				} else {
					imageRelativePath1 = String.valueOf(System.currentTimeMillis())
							+ ".jpg";//
				}
			}
			imagePath = IMAGE_FILE + imageRelativePath1;
			ImageZipUtil.compressPic(image1, imagePath);
			imageRelativePath1 = IMAGE_PATH + imageRelativePath1;
		}else{
			CommerceDO commerceDO = commerceDAO.selectCommerceInfoByCommerceId(commerceId);
			if(null != commerceDO){
				imageRelativePath1 = commerceDO.getCommerceUrl();
			}
		}
        int flag1 = commerceDAO.updateCommerce(commerceName, imageRelativePath1, commerceCell, commerceAddr, commerceDesc, message, commerceId);
        modelMap.put("flag", flag1);
        
        return new ModelAndView("mng/commerceUpdatesuccess");
    }
	
	@RequestMapping("toleader.do")
	@ResponseBody
	public Object toleader(final HttpServletRequest request){
		JsonResult jsonResult = new JsonResult(false);
		
		String commerceId = request.getParameter("commerceId");
		String userId = request.getParameter("userId");
		String leadtype = request.getParameter("leadtype");
		int i = 0;
		
		CommerceDO commerceDO = commerceDAO.selectCommerceInfoByCommerceId(commerceId);
		if(null == commerceDO){
			return jsonResult;
		}
		//会长
		if(StringUtils.equals(leadtype, "1")){
			i = commerceDAO.changeLeader(userId, commerceId);
			//修改权限
			RoleDO roleDO = roleDao.checkCommerceResult(commerceId);
			if(null == roleDO){
				//加
				RoleDO role = new RoleDO();
				role.setUserId(userId);
				role.setRole("3");
				role.setMemo(commerceId);
				roleDao.addRole(role);
			}else{
				//改
				roleDao.changeComerceRole(userId, commerceId);
			}
		}
		
		//副会长(多个)
        if(StringUtils.equals(leadtype, "2")){
        	String oldViceLeader = commerceDO.getCommerceViceLeader();
        	String newViceLeader = oldViceLeader+","+userId;
        	i = commerceDAO.changeViceLeader(newViceLeader, commerceId);
		}
        
        //负责人
        if(StringUtils.equals(leadtype, "3")){
        	i = commerceDAO.changeCharge(userId, commerceId);
		}
        
        //秘书长
        if(StringUtils.equals(leadtype, "4")){
        	i = commerceDAO.changeSecretary(userId, commerceId);
		}
        
      //执行会长(多个) COMMERCE_EXECUTIVE_CHAIRMAN
        if(StringUtils.equals(leadtype, "5")){
        	String oldViceLeader = commerceDO.getCommerceExecutiveChairman();
        	String newViceLeader = oldViceLeader+","+userId;
        	i = commerceDAO.changeExecutiveChairman(newViceLeader, commerceId);
		}
        
      //常务会长(多个) COMMERCE_STANDING_PRESIDENT
        if(StringUtils.equals(leadtype, "6")){
        	String oldViceLeader = commerceDO.getCommerceStandingPresident();
        	String newViceLeader = oldViceLeader+","+userId;
        	i = commerceDAO.changeStandingPresident(newViceLeader, commerceId);
		}
        
      //名誉会长(多个) COMMERCE_HONORARY_CHAIRMAN
        if(StringUtils.equals(leadtype, "7")){
        	String oldViceLeader = commerceDO.getCommerceHonoraryChairman();
        	String newViceLeader = oldViceLeader+","+userId;
        	i = commerceDAO.changeHonoraryChairman(newViceLeader, commerceId);
		}
        
      //常务董事(多个) COMMERCE_EXECUTIVE_DIRECTOR
        if(StringUtils.equals(leadtype, "8")){
        	String oldViceLeader = commerceDO.getCommerceExecutiveDirector();
        	String newViceLeader = oldViceLeader+","+userId;
        	i = commerceDAO.changeExecutiveDirector(newViceLeader, commerceId);
		}
        
      //常务理事(多个) COMMERCE_MANAGING_DIRECTOR
        if(StringUtils.equals(leadtype, "9")){
        	String oldViceLeader = commerceDO.getCommerceManagingDirector();
        	String newViceLeader = oldViceLeader+","+userId;
        	i = commerceDAO.changeManagingDirector(newViceLeader, commerceId);
		}
        
      //执行董事(多个) COMMERCE_EXECUTIVE
        if(StringUtils.equals(leadtype, "10")){
        	String oldViceLeader = commerceDO.getCommerceExecutive();
        	String newViceLeader = oldViceLeader+","+userId;
        	i = commerceDAO.changeExecutive(newViceLeader, commerceId);
		}
        
      //党支部书记(多个) COMMERCE_SECRETARY_PARTY
        if(StringUtils.equals(leadtype, "11")){
        	String oldViceLeader = commerceDO.getCommerceSecretaryParty();
        	String newViceLeader = oldViceLeader+","+userId;
        	i = commerceDAO.changeSecretaryParty(newViceLeader, commerceId);
		}
        
        if(i >= 1){
        	jsonResult.setBizSucc(true);
        }
		
		return jsonResult;
	}
	
	@RequestMapping("deleteCommerceUser.do")
	@ResponseBody
	public Object deleteCommerceUser(final HttpServletRequest request){
		JsonResult jsonResult = new JsonResult(false);
		
		String commerceId = request.getParameter("commerceId");
		String userId = request.getParameter("userId");
		
		CommerceDO commerceDO = commerceDAO.selectCommerceInfoByCommerceId(commerceId);
		if(null == commerceDO){
			return jsonResult;
		}
		
		//判断该人是否有职位
		CommerceDO checkLeader = commerceDAO.checkLeader(userId, null, null, null,null,null,null,null,null,null,null, commerceId);
		if(null != checkLeader){
			commerceDAO.changeLeader(null, commerceId);
		}
		CommerceDO checkViceLeader = commerceDAO.checkLeader(null, userId, null, null, null,null,null,null,null,null,null, commerceId);
		if(null != checkViceLeader){
			String oldViceLeader = commerceDO.getCommerceViceLeader();
			String newViceLeader = oldViceLeader.replace(userId, "");
			commerceDAO.changeViceLeader(newViceLeader, commerceId);
		}
		CommerceDO checkCharge = commerceDAO.checkLeader(null, null, userId, null, null,null,null,null,null,null,null, commerceId);
		if(null != checkCharge){
			commerceDAO.changeCharge(null, commerceId);
		}
		CommerceDO checkSecretary = commerceDAO.checkLeader(null, null, userId, null, null,null,null,null,null,null,null, commerceId);
		if(null != checkSecretary){
			commerceDAO.changeSecretary(null, commerceId);
		}
		
		//判断是否为执行会长COMMERCE_EXECUTIVE_CHAIRMAN commerceExecutiveChairman
    	CommerceDO checkExecutiveChairman = commerceDAO.checkLeader(null,null,null,null,userId,null,null,null,null,null,null, commerceId);
        if(null != checkExecutiveChairman){
        	String oldViceLeader = commerceDO.getCommerceExecutiveChairman();
			String newViceLeader = oldViceLeader.replace(userId, "");
			commerceDAO.changeExecutiveChairman(newViceLeader, commerceId);
    	}
        //判断是否为常务会长COMMERCE_STANDING_PRESIDENT commerceStandingPresident
    	CommerceDO checkStandingPresident = commerceDAO.checkLeader(null,null,null,null,null,userId,null,null,null,null,null, commerceId);
        if(null != checkStandingPresident){
        	String oldViceLeader = commerceDO.getCommerceStandingPresident();
			String newViceLeader = oldViceLeader.replace(userId, "");
			commerceDAO.changeStandingPresident(newViceLeader, commerceId);
    	}
        //判断是否为名誉会长COMMERCE_HONORARY_CHAIRMAN commerceHonoraryChairman
    	CommerceDO checkHonoraryChairman = commerceDAO.checkLeader(null,null,null,null,null,null,userId,null,null,null,null, commerceId);
        if(null != checkHonoraryChairman){
        	String oldViceLeader = commerceDO.getCommerceHonoraryChairman();
			String newViceLeader = oldViceLeader.replace(userId, "");
			commerceDAO.changeHonoraryChairman(newViceLeader, commerceId);
    	}
        //判断是否为常务董事COMMERCE_EXECUTIVE_DIRECTOR commerceExecutiveDirector
    	CommerceDO checkExecutiveDirector = commerceDAO.checkLeader(null,null,null,null,null,null,null,userId,null,null,null, commerceId);
        if(null != checkExecutiveDirector){
        	String oldViceLeader = commerceDO.getCommerceExecutiveDirector();
			String newViceLeader = oldViceLeader.replace(userId, "");
			commerceDAO.changeExecutiveDirector(newViceLeader, commerceId);
    	}
        //判断是否为常务理事COMMERCE_MANAGING_DIRECTOR commerceManagingDirector
    	CommerceDO checkManagingDirector = commerceDAO.checkLeader(null,null,null,null,null,null,null,null,userId,null,null, commerceId);
        if(null != checkManagingDirector){
        	String oldViceLeader = commerceDO.getCommerceManagingDirector();
			String newViceLeader = oldViceLeader.replace(userId, "");
			commerceDAO.changeManagingDirector(newViceLeader, commerceId);
    	}
        //判断是否为执行董事COMMERCE_EXECUTIVE commerceExecutive
    	CommerceDO checkExecutive = commerceDAO.checkLeader(null,null,null,null,null,null,null,null,null,userId,null, commerceId);
        if(null != checkExecutive){
        	String oldViceLeader = commerceDO.getCommerceExecutive();
			String newViceLeader = oldViceLeader.replace(userId, "");
			commerceDAO.changeExecutive(newViceLeader, commerceId);
    	}
        //判断是否为党支部书记COMMERCE_SECRETARY_PARTY commerceSecretaryParty
    	CommerceDO checkSecretaryParty = commerceDAO.checkLeader(null,null,null,null,null,null,null,null,null,null,userId, commerceId);
        if(null != checkSecretaryParty){
        	String oldViceLeader = commerceDO.getCommerceSecretaryParty();
			String newViceLeader = oldViceLeader.replace(userId, "");
			commerceDAO.changeSecretaryParty(newViceLeader, commerceId);
    	}
		
		//人数减一
		int oldPeopleNum = commerceDO.getPeopleNum();
	    int newPeopleNum = oldPeopleNum - 1;
		commerceDAO.updateCommercePeopleNum(newPeopleNum, commerceId);
		
		UserDO userDO = userDao.selectUserInfoByUserId(userId); 
		if(null != userDO){
			String commerceIdStr = userDO.getCommerceId();
			String newCommerceId = commerceIdStr.replace(commerceId, "");
			int i = userDao.updateUserCommerce(newCommerceId, userId);
			if(i >= 1){
	        	jsonResult.setBizSucc(true);
	        }
		}
		return jsonResult;
	}
	
	@RequestMapping("changeDelFlag.do")
	@ResponseBody
	public Object changeDelFlag(final HttpServletRequest request){
		JsonResult jsonResult = new JsonResult(false);
		
		String commerceId = request.getParameter("commerceId");
		
		String leadtype = request.getParameter("leadtype");
		int i = 0;
		//激活
		if(StringUtils.equals(leadtype, "1")){
			i = commerceDAO.changeDelFlg("0", commerceId);
		}
		
		//删除
        if(StringUtils.equals(leadtype, "2")){
        	i = commerceDAO.changeDelFlg("1", commerceId);
		}
        
        if(i >= 1){
        	jsonResult.setBizSucc(true);
        }
		
		return jsonResult;
	}
	
	/**
	 * 撤离职位
	 * @param request
	 * @return
	 */
	@RequestMapping("removeLeader.do")
	@ResponseBody
	public Object removeLeader(final HttpServletRequest request){
        JsonResult jsonResult = new JsonResult(false);
		
		String commerceId = request.getParameter("commerceId");
		String userId = request.getParameter("userId");
		String leadtype = request.getParameter("leadtype");
		int i = 0;
		
		CommerceDO commerceDO = commerceDAO.selectCommerceInfoByCommerceId(commerceId);
		if(null == commerceDO){
			return jsonResult;
		}
		//会长
		if(StringUtils.equals(leadtype, "1")){
			i = commerceDAO.changeLeader(null, commerceId);
		}
		
		//副会长(多个)
        if(StringUtils.equals(leadtype, "2")){
        	String oldViceLeader = commerceDO.getCommerceViceLeader();
        	String newViceLeader = oldViceLeader.replace(userId, "");
        	i = commerceDAO.changeViceLeader(newViceLeader, commerceId);
		}
        
        //负责人
        if(StringUtils.equals(leadtype, "3")){
        	i = commerceDAO.changeCharge(null, commerceId);
		}
        
        //秘书长
        if(StringUtils.equals(leadtype, "4")){
        	i = commerceDAO.changeSecretary(null, commerceId);
		}
        
      //执行会长(多个) COMMERCE_EXECUTIVE_CHAIRMAN
        if(StringUtils.equals(leadtype, "5")){
        	String oldViceLeader = commerceDO.getCommerceExecutiveChairman();
        	String newViceLeader = oldViceLeader.replace(userId, "");
        	i = commerceDAO.changeExecutiveChairman(newViceLeader, commerceId);
		}
        
      //常务会长(多个) COMMERCE_STANDING_PRESIDENT
        if(StringUtils.equals(leadtype, "6")){
        	String oldViceLeader = commerceDO.getCommerceStandingPresident();
        	String newViceLeader = oldViceLeader.replace(userId, "");
        	i = commerceDAO.changeStandingPresident(newViceLeader, commerceId);
		}
        
      //名誉会长(多个) COMMERCE_HONORARY_CHAIRMAN
        if(StringUtils.equals(leadtype, "7")){
        	String oldViceLeader = commerceDO.getCommerceHonoraryChairman();
        	String newViceLeader = oldViceLeader.replace(userId, "");
        	i = commerceDAO.changeHonoraryChairman(newViceLeader, commerceId);
		}
        
      //常务董事(多个) COMMERCE_EXECUTIVE_DIRECTOR
        if(StringUtils.equals(leadtype, "8")){
        	String oldViceLeader = commerceDO.getCommerceExecutiveDirector();
        	String newViceLeader = oldViceLeader.replace(userId, "");
        	i = commerceDAO.changeExecutiveDirector(newViceLeader, commerceId);
		}
        
      //常务理事(多个) COMMERCE_MANAGING_DIRECTOR
        if(StringUtils.equals(leadtype, "9")){
        	String oldViceLeader = commerceDO.getCommerceManagingDirector();
        	String newViceLeader = oldViceLeader.replace(userId, "");
        	i = commerceDAO.changeManagingDirector(newViceLeader, commerceId);
		}
        
      //执行董事(多个) COMMERCE_EXECUTIVE
        if(StringUtils.equals(leadtype, "10")){
        	String oldViceLeader = commerceDO.getCommerceExecutive();
        	String newViceLeader = oldViceLeader.replace(userId, "");
        	i = commerceDAO.changeExecutive(newViceLeader, commerceId);
		}
        
      //党支部书记(多个) COMMERCE_SECRETARY_PARTY
        if(StringUtils.equals(leadtype, "11")){
        	String oldViceLeader = commerceDO.getCommerceSecretaryParty();
        	String newViceLeader = oldViceLeader.replace(userId, "");
        	i = commerceDAO.changeSecretaryParty(newViceLeader, commerceId);
		}
        
        if(i >= 1){
        	jsonResult.setBizSucc(true);
        }
		
		return jsonResult;
	}
	
	/**
	 * 修改商会公告
	 * @param request
	 * @param modelMap
	 * @return
	 */
	@RequestMapping("changeCommerceMemo.html")
	public String changeCommerceMemo(HttpServletRequest request,ModelMap modelMap){
//		String userIds = (String)request.getSession().getAttribute("userId");
//    	boolean checkRoleBoss = checkRoleBoss(userIds);
//    	if(checkRoleBoss == false){
//    		return "mng/loginMng";
//    	}
//    	//判断是否为运营者
//        int checkOperative = checkOperative(userIds);
//        modelMap.put("checkOperative", checkOperative);
        
        String commerceId = request.getParameter("commerceId");
        CommerceDO commerceDO = commerceDAO.selectCommerceInfoByCommerceId(commerceId);
        
        modelMap.put("commerceDO", commerceDO);
		
		return "mng/changeCommerceMemo";
	}
	
	/**
	 * 修改商会公告
	 * @param request
	 * @return
	 */
	@RequestMapping("changeCommerceMemo.do")
	@ResponseBody
	public Object changeCommerceMemo(HttpServletRequest request){
		final String memo = request.getParameter("memo");
		final String commerceId = request.getParameter("commerceId");
		
		final JsonResult jsonResult = new JsonResult(true);
		controllerTemplate.execute(jsonResult, new ControllerCallBack() {

			@Override
			public void executeService() {
				int result = commerceDAO.updateCommerceMemo(memo, commerceId);

				if (result <= 0 ) {
					jsonResult.setBizSucc(false);
					jsonResult.setErrMsg("提交失败");
					return;
				}
			}

			@Override
			public void check() {
			}
		});
		return jsonResult;
	}
	
	/**
	 * 添加商会
	 * @param request
	 * @param modelMap
	 * @return
	 */
	@RequestMapping("changeCommerceUserInfo.html")
	public String changeCommerceUserInfo(HttpServletRequest request,ModelMap modelMap){

		String userId = request.getParameter("userId");
		String commerceId = request.getParameter("commerceId");
		
		CommerceCheckDO commerceCheckDO = commerceCheckDAO.selectMyCommerceCheck(userId, commerceId);
		modelMap.put("commerceCheckDO", commerceCheckDO);
		
		return "mng/changeCommerceUserInfo";
	}
	
	/**
	 * 添加新商会
	 * @param request
	 * @return
	 */
	@RequestMapping("changeCommerceUserInfo.do")
    public ModelAndView changeCommerceUserInfoDo(HttpServletRequest request, ModelMap modelMap) throws Exception {
		final String userId = request.getParameter("userId");
		final String commerceId = request.getParameter("commerceId");
		final String userName = request.getParameter("userName");
		final String userCell = request.getParameter("userCell");
		final String userJob = request.getParameter("userJob");
		final String cardAddr = request.getParameter("cardAddr");
		final String nowAddr = request.getParameter("nowAddr");
		
		int flag1 = commerceCheckDAO.changeCommerceUserInfo(userName, userCell, userJob, cardAddr, nowAddr, userId, commerceId);

        modelMap.put("flag", flag1);
        
        return new ModelAndView("mng/changeCommerceUserInfosuccess");
    }
	
	/**
	 * 跳转手动录入会员信息
	 * @param request
	 * @param modelMap
	 * @return
	 */
	@RequestMapping("handCommerceUser.html")
	public String handCommerceUser(HttpServletRequest request,ModelMap modelMap){

		String commerceId = request.getParameter("commerceId");
		modelMap.put("commerceId", commerceId);
		
		return "mng/handCommerceUser";
	}
	
	/**
	 * 手动录入会员
	 * @param request
	 * @return
	 */
	@RequestMapping("handCommerceUser.do")
	@ResponseBody
	public Object handCommerceUser(HttpServletRequest request){
		final String commerceId = request.getParameter("commerceId");
		final String userName = request.getParameter("userName");
		final String nickName = request.getParameter("nickName");
		final String sex = request.getParameter("sex");
		final String userCell = request.getParameter("userCell");
		final String userJob = request.getParameter("userJob");
		final String cardAddr = request.getParameter("cardAddr");
		final String nowAddr = request.getParameter("nowAddr");
		
		final JsonResult jsonResult = new JsonResult(true);
		controllerTemplate.execute(jsonResult, new ControllerCallBack() {

			@Override
			public void executeService() {
				CommerceDO commerceDO = commerceDAO.selectCommerceInfoByCommerceId(commerceId);
				String headUrl = null;
				if(null != commerceDO){
					headUrl = commerceDO.getCommerceUrl();
				}
				//录入User表
				String userId = UUIDHexGenerator.getHandUserId();
				UserDO user = new UserDO();
				user.setUserId(userId);
		        user.setUserNum(userId);
		        user.setUserName(userName);
		        user.setNickName(nickName);
		        user.setSex(sex);
		        user.setHeadUrl(headUrl);
		        int i = userDao.addUserInfo(user);
				
				//加商会
		        // 查询该商会是否存在
		        
		     	UserDO userDO = userDao.selectUserInfoByUserId(userId);
		     	if (null != commerceDO && null != userDO) {
					String userCommerceID = userDO.getCommerceId();
					int j = 0;
					if (StringUtils.isEmpty(userCommerceID)) {
						j = userDao.updateUserCommerce(commerceId, userId);
					} else {
						String newId = userCommerceID + "," + commerceId;
						j = userDao.updateUserCommerce(newId, userId);
					}
					int newpeople = commerceDO.getPeopleNum() + 1;
					int k = commerceDAO.updateCommercePeopleNum(newpeople,
							commerceId);
					int m = 0;
					//判断之前是否有过申请
					CommerceCheckDO checkDO = commerceCheckDAO.selectMyCommerceCheck(userId, commerceId);
					if(null == checkDO){
						CommerceCheckDO commerceCheckDO = new CommerceCheckDO();
						
						commerceCheckDO.setUserId(userId);
						commerceCheckDO.setCommerceId(commerceId);
						commerceCheckDO.setUserName(userDO.getUserName());
						commerceCheckDO.setStatus("1");//审核通过
						commerceCheckDO.setUserJob(userJob);
						commerceCheckDO.setCardAddr(cardAddr);
						commerceCheckDO.setNowAddr(nowAddr);
						commerceCheckDO.setUserCell(userCell);
						
						m = commerceCheckDAO.addCommerceCheck(commerceCheckDO);
						
					}else{
		                CommerceCheckDO commerceCheckDO = new CommerceCheckDO();
						
		                commerceCheckDO.setUserId(userId);
						commerceCheckDO.setCommerceId(commerceId);
						commerceCheckDO.setUserName(userDO.getUserName());
						commerceCheckDO.setStatus("1");//审核通过
						commerceCheckDO.setUserJob(userJob);
						commerceCheckDO.setCardAddr(cardAddr);
						commerceCheckDO.setNowAddr(nowAddr);
						commerceCheckDO.setUserCell(userCell);
						
						m = commerceCheckDAO.updateCommerceCheck(commerceCheckDO);
					}

					
					if (i >= 1 && j >= 1 && k >= 1 && m >= 1) {
						jsonResult.setBizSucc(true);
						jsonResult.setErrMsg("录入成功");
					}
					
					StringBuffer info = new StringBuffer().append(
							userDO.getNickName()).append("刚刚加入").append(commerceDO.getCommerceName());
					String title = "新入会";
					ActivityDO noticeInfo = new ActivityDO();
					noticeInfo.setTitle(title);
					noticeInfo.setInfo(info.toString());
					noticeInfo.setType("10");
					noticeInfo.setState("0");// 新发布的公告默认置顶
					activityDao.addInfo(noticeInfo);
				}
				
				
			}

			@Override
			public void check() {
			}
		});
		return jsonResult;
	}
	
	/**
	 * 跳转手动录入会员信息
	 * @param request
	 * @param modelMap
	 * @return
	 */
	@RequestMapping("handNewUser.html")
	public String handNewUser(HttpServletRequest request,ModelMap modelMap){
		return "mng/handNewUser";
	}
	
	/**
	 * 手动录入排单
	 * @param request
	 * @return
	 */
	@RequestMapping("handNewUser.do")
	@ResponseBody
	public Object handNewUserDo(HttpServletRequest request){
		
		final String userName = request.getParameter("userName");
		final String userCell = request.getParameter("userCell");
		
		final JsonResult jsonResult = new JsonResult(false);
		controllerTemplate.execute(jsonResult, new ControllerCallBack() {

			@Override
			public void executeService() {
				
				//录入User表
				String userId = UUIDHexGenerator.getHandUserId();
				UserDO user = new UserDO();
				user.setUserId(userId);
		        user.setUserNum(userId);
		        user.setUserName(userName);
		        user.setNickName(userName);
		        user.setCell(userCell);
		        int i = userDao.addUserInfo(user);
				
		        //生成一个订单
		        OrderDO orderDo = new OrderDO();
		        String orderNo = UUIDHexGenerator.getOrderNo();
				orderDo.setUserId(userId);
				orderDo.setUserName(userName);
				orderDo.setCell(userCell);
				orderDo.setOrderNo(orderNo);
				orderDo.setOrderId(orderNo);
				orderDo.setLuggage(new Money());
				int orderNum = 1;
				
				//判断第几个订单
		        OrderDO lastPartnerOrder = orderDao.selectLastPartnerOrder();
		        if(null == lastPartnerOrder){
		        	orderNum = 1;
			    }else{
			    	int myRank;
				    int lastRank= lastPartnerOrder.getOrderNum();
				    myRank = lastRank + 1;
				    orderNum = myRank;
			    }
		        orderDo.setOrderNum(orderNum);
		        int j = orderDao.creatPartnerOrder(orderDo);
				
				//查询最后一个
				PartnerRankDO lastPartner = partnerRankDAO.selectLastPartner(null,0,1);
				int parNum = 1;
				if(null != lastPartner){
					parNum = lastPartner.getPartnerNum()+1;
				}
		        //排序表插数据
		        PartnerRankDO partnerRankDO = new PartnerRankDO();
		        partnerRankDO.setUserId(userId);
		        partnerRankDO.setPartnerNum(parNum);
		        partnerRankDO.setOrderNum(orderNum);
		        partnerRankDO.setOrderId(orderNo);
		        int k = partnerRankDAO.insert(partnerRankDO);
		        if(i>0 && j>0 && k>0){
		        	jsonResult.setBizSucc(true);
		        }
			}

			@Override
			public void check() {
			}
		});
		return jsonResult;
	}
}
