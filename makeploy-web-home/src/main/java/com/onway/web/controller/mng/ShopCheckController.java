package com.onway.web.controller.mng;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.onway.common.lang.Money;
import com.onway.makeploy.common.dal.dataobject.ActivityDO;
import com.onway.makeploy.common.dal.dataobject.CommerceCheckDO;
import com.onway.makeploy.common.dal.dataobject.CommerceDO;
import com.onway.makeploy.common.dal.dataobject.OrderDO;
import com.onway.makeploy.common.dal.dataobject.PartnerDO;
import com.onway.makeploy.common.dal.dataobject.PartnerRankDO;
import com.onway.makeploy.common.dal.dataobject.ProImageDO;
import com.onway.makeploy.common.dal.dataobject.ProductDO;
import com.onway.makeploy.common.dal.dataobject.ProductParameterDO;
import com.onway.makeploy.common.dal.dataobject.RoleDO;
import com.onway.makeploy.common.dal.dataobject.ShopCheckDO;
import com.onway.makeploy.common.dal.dataobject.ShopCheckInfoDO;
import com.onway.makeploy.common.dal.dataobject.ShopDO;
import com.onway.makeploy.common.dal.dataobject.SysConfigDO;
import com.onway.makeploy.common.dal.dataobject.UserDO;
import com.onway.makeploy.core.localcache.enums.SysConfigCacheKeyEnum;
import com.onway.platform.common.enums.PlatformCodeEnum;
import com.onway.web.controller.AbstractController;
import com.onway.web.controller.result.JsonResult;
import com.onway.web.controller.sendMessage.SendCustomMessage;

@Controller
public class ShopCheckController extends AbstractController {
	@RequestMapping("shopCheckMng.html")
	public String aa(HttpServletRequest request, ModelMap map) {
		String userId = (String) request.getSession().getAttribute("userId");
		boolean checkRoleBoss = checkRoleBoss(userId);
		if (checkRoleBoss == false) {
			return "mng/loginMng";
		}
		// 判断是否为运营者
		int checkOperative = checkOperative(userId);
		map.put("checkOperative", checkOperative);
		return "mng/shopCheckMng";
	}

	@RequestMapping("selectshop.do")
	public String selectShop(HttpServletRequest request, ModelMap map) {
		String userId = (String) request.getSession().getAttribute("userId");
		boolean checkRoleBoss = checkRoleBoss(userId);
		if (checkRoleBoss == false) {
			return "mng/loginMng";
		}
		// 判断是否为运营者
		int checkOperative = checkOperative(userId);
		map.put("checkOperative", checkOperative);

		String shopName = request.getParameter("shopName").replace("+", "");
		String shopCell = request.getParameter("shopCell").replace("+", "");
		// String cell=request.getParameter("cell").replace("+", "");
		String flag = request.getParameter("selectedNew");
		// 农村经纪人申请
		int totalItems = shopCheckDAO.countitmes(shopName, shopCell, flag);
		Map<String, Integer> pageDate = getPageData(request);
		List<ShopCheckDO> shoplist = shopCheckDAO.selectShopCheckStatus(
				shopName, shopCell, flag, pageDate.get(OFFSET) / 2,
				pageDate.get(PAGE_SIZE_STR) / 2);
		List<ShopCheckInfoDO> checkInfoList = new ArrayList<ShopCheckInfoDO>();
		for (ShopCheckDO ShopCheckDO : shoplist) {
			ShopCheckInfoDO shopCheckInfo = new ShopCheckInfoDO();
			shopCheckInfo.setUserId(ShopCheckDO.getUserId());
			shopCheckInfo.setShopId(ShopCheckDO.getShopId());
			// shopCheckInfo.setUserName(userDao.selectUserInfoByUserId(ShopCheckDO.getUserId())
			// .getUserName());
			shopCheckInfo.setUserName(ShopCheckDO.getUserName());
			// shopCheckInfo.setUserCell(userDao.selectUserInfoByUserId(ShopCheckDO.getUserId())
			// .getCell());
			shopCheckInfo.setUserCell(ShopCheckDO.getUserCell());
			shopCheckInfo.setUserJob(ShopCheckDO.getUserJob());
			shopCheckInfo.setShopName(ShopCheckDO.getShopName());
			shopCheckInfo.setShopCell(ShopCheckDO.getShopCell());
			shopCheckInfo.setShopAddr(ShopCheckDO.getShopAddr());
			shopCheckInfo.setShopDec(ShopCheckDO.getShopDec());
			shopCheckInfo.setApplyType(ShopCheckDO.getApplyType());
			shopCheckInfo.setCheckStatus(ShopCheckDO.getCheckStatus());
			shopCheckInfo.setGmtCreate(ShopCheckDO.getGmtCreate());
			shopCheckInfo.setCardAddr(ShopCheckDO.getCardAddr());
			shopCheckInfo.setNowAddr(ShopCheckDO.getNowAddr());
			shopCheckInfo.setMemo(ShopCheckDO.getMemo());
			checkInfoList.add(shopCheckInfo);

		}
		map.put("totalPages",
				calculatePage(totalItems, pageDate.get(PAGE_SIZE_STR) / 2));
		map.put(CURRENTPAGE, pageDate.get(CURRENTPAGE));
		map.put("totalItems", totalItems);
		map.put("checkInfoList", checkInfoList);
		map.put("shopName", shopName);
		map.put("shopCell", shopCell);
		map.put("flag", flag);
		request.setAttribute("page", map);
		return "mng/shopCheckMng";
	}

	@RequestMapping("shopEdit.do")
	@ResponseBody
	public Object shopEdit(HttpServletRequest request) {
		JsonResult result = new JsonResult(false, "", "审核失败!");
		String shopId = request.getParameter("shopId");
		if (StringUtils.isEmpty(shopId)) {
			shopId = codeGenerateComponent
					.nextCodeByType(PlatformCodeEnum.MALL_PLATFORM);
		}
		String userId = request.getParameter("userId");
		String flg = request.getParameter("flg");
		String applyType = request.getParameter("applyType");
		if ("1".equals(flg)) {
			if (applyType.equals("0")) {
				ShopCheckDO shopCheckDO = shopCheckDAO.selectByShopId(shopId);
//				int partnerNum= Integer.valueOf(shopCheckDO.getMemo());//真实排名（订单顺序）
				int a = shopCheckDAO.updateCheckStatusByshopId("1", shopId);// 修改check表的审核状态
				// 往partner表插入数据
				List<OrderDO> orderDO = orderDao.partnerOrderByUserId(userId);
				Money PAccount = orderDO.get(orderDO.size() - 1)
						.getOrderPrice();
				PartnerDO partnerDO = new PartnerDO();
				partnerDO.setPartnerAccout(PAccount);
				partnerDO.setPartnerUserId(userId);
				partnerDAO.joinPartner(partnerDO);

				// 往shop表插入新的店铺数据
				ShopDO shopDO = new ShopDO();
				shopDO.setShopId(shopId);
				shopDO.setUserId(shopCheckDO.getUserId());
				shopDO.setShopName(shopCheckDO.getShopName());
				shopDO.setCell(shopCheckDO.getShopCell());
				shopDO.setShopDec(shopCheckDO.getShopDec());
				shopDO.setShopAddr(shopCheckDO.getShopAddr());
				shopDO.setShopUrl("http://jzsc8888.com/image/1486950168906.jpeg");
				shopDO.setUnionFlg("0");
				shopDO.setDelFlag("1");
				int b = shopDAO.addCheckedShop(shopDO);
				// 添加农村经纪人商铺默认有的商品
				int x = addPartnerProduct(shopId, userId);
				// 往user表里更新当前userId的shopId
				int c = userDao.addShopByUserId(shopId, userId);
				// role权限表新增权限
				RoleDO role = new RoleDO();
				role.setUserId(userId);
				role.setRole("0");
				role.setCreater("商户");
				int d = roleDao.addRole(role);
				if (a > 0 && b > 0 && c > 0 && d > 0 && x > 0) {
					result.setBizSucc(true);
					result.setErrMsg("审核通过");
				} else {
					return result;
				}

//				Date now = new Date();
//				SimpleDateFormat dateFormat = new SimpleDateFormat(
//						"yyyy-MM-dd HH:mm");// 可以方便地修改日期格式
//
//				String checktime = dateFormat.format(now);
				ActivityDO shopCheckNotice = new ActivityDO();
				String userName = userDao.selectUserInfoByUserId(
						shopCheckDAO.selectByShopId(shopId).getUserId())
						.getNickName();
				// 首页消息
				shopCheckNotice.setTitle("客户农村经纪人申请");
				// shopCheckNotice.setInfo(userName+"在"+checktime+"申请了属于自己的店铺！");
				// shopCheckNotice.setInfo("“"+shopDAO.selectShopByShopId(shopId).getShopName()+"”"+"店刚刚通过审核入驻平台！");
				shopCheckNotice.setInfo(userName + "刚刚通过审核成为农村经纪人！");
				shopCheckNotice.setType("1");
				activityDao.resetStateByType("1");
				shopCheckNotice.setState("1");
				activityDao.addInfo(shopCheckNotice);
				try {
					// 公众号发送提示消息
					SysConfigDO sysConfigDO = sysConfigDAO
							.selectByKey(SysConfigCacheKeyEnum.ACCESS_TOKEN
									.toString());
					String accessToken = sysConfigDO.getSysValue();
					// String accessToken = sysConfigCacheManager
					// .getConfigValue(SysConfigCacheKeyEnum.ACCESS_TOKEN);
					UserDO userdo = userDao.selectUserInfoByUserId(userId);
					String jsonTextMsg = "";
					String notice = "亲爱的用户，您的农村经纪人审核已经通过";
					// notice= new String(notice.getBytes("UTF-8"),"GB18030");
					jsonTextMsg = SendCustomMessage.makeTextCustomMessage(
							userdo.getWechatId(), notice);
					SendCustomMessage.sendCustomMessage(accessToken,
							jsonTextMsg);
				} catch (Exception e) {
					e.printStackTrace();
				}
			} else {
				ActivityDO shopCheckNotice = new ActivityDO();
				// 判断通过哪种途径过来申请联盟商家
				ShopCheckDO checkDO = shopCheckDAO.selectByUserIdApplyType(
						userId, null, null);
				String userName = null;
				if (!StringUtils.isEmpty(checkDO.getShopId())) {
					userName = userDao.selectUserInfoByUserId(
							shopCheckDAO.selectByShopId(shopId).getUserId())
							.getNickName();
					shopDAO.updateUnion("1", shopId);
					shopCheckDAO.updateCheckStatusByshopId("1",shopId);
					result.setBizSucc(true);
					result.setErrMsg("审核通过");
				} else {
					// shopcheck更新shopId
					shopCheckDAO.updateShopId(shopId, userId);
					shopCheckDAO.updateCheckStatusByshopId("1",shopId);
					// shop插入 user插入
					// 往shop表插入新的店铺数据
					ShopDO shopDO = new ShopDO();
					shopDO.setShopId(shopId);
					shopDO.setUserId(checkDO.getUserId());
					shopDO.setShopName(checkDO.getShopName());
					shopDO.setCell(checkDO.getShopCell());
					shopDO.setShopDec(checkDO.getShopDec());
					shopDO.setShopAddr(checkDO.getShopAddr());
					shopDO.setShopUrl("http://jzsc8888.com/image/1486950168906.jpeg");
					shopDO.setUnionFlg("1");
					shopDO.setDelFlag("1");
					shopDO.setIndustry(checkDO.getUserJob());
					int b = shopDAO.addCheckedShop(shopDO);
					int c = userDao.addShopByUserId(shopId, userId);
					if (b > 0 && c > 0) {
						result.setBizSucc(true);
						result.setErrMsg("审核通过");
					} else {
						return result;
					}
				}
				RoleDO roleDO = roleDao.selectShopRoleByUserId(userId);
				if (null == roleDO) {
					// role权限表新增权限
					RoleDO role = new RoleDO();
					role.setUserId(userId);
					role.setRole("0");
					role.setCreater("商户");
					roleDao.addRole(role);
				}

				// 首页滚动消息
				shopCheckNotice.setTitle("客户联盟商家申请");
				// shopCheckNotice.setInfo(userName+"在"+checktime+"申请了属于自己的店铺！");
				// shopCheckNotice.setInfo("“"+shopDAO.selectShopByShopId(shopId).getShopName()+"”"+"店刚刚通过审核入驻平台！");
				shopCheckNotice.setInfo(userName + "刚刚通过审核成为联盟商家！");
				shopCheckNotice.setType("1");
				activityDao.resetStateByType("1");
				shopCheckNotice.setState("1");
				activityDao.addInfo(shopCheckNotice);
				try {
					// 公众号发送提示消息
					SysConfigDO sysConfigDO = sysConfigDAO
							.selectByKey(SysConfigCacheKeyEnum.ACCESS_TOKEN
									.toString());
					String accessToken = sysConfigDO.getSysValue();
					// String accessToken = sysConfigCacheManager
					// .getConfigValue(SysConfigCacheKeyEnum.ACCESS_TOKEN);
					UserDO userdo = userDao.selectUserInfoByUserId(userId);
					String jsonTextMsg = "";
					String notice = "亲爱的用户，您的联盟商铺审核已经通过";
					notice = new String(notice.getBytes("UTF-8"), "GB18030");
					jsonTextMsg = SendCustomMessage.makeTextCustomMessage(
							userdo.getWechatId(), notice);
					SendCustomMessage.sendCustomMessage(accessToken,
							jsonTextMsg);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}

		} else if ("2".equals(flg)) {
			if (applyType.equals("0")) {
				shopCheckDAO.updateCheckStatusByshopId("2", shopId);
				// 获取用户信息
				UserDO userDO = userDao.selectUserInfoByUserId(userId);
				// 获取用户当时申请时所花费的钱
				List<OrderDO> orderDO = orderDao.partnerOrderByUserId(userId);
				Money PAccount = orderDO.get(orderDO.size() - 1)
						.getOrderPrice();
				Money balance = userDO.getBalance();
				// 更新用户的数据,将农村经纪人费用返回给余额
				userDao.updateUserReturn(userDO.getPointAccout(),
						balance.add(PAccount), userDO.getWithdrawAccout(),
						userId);
				result.setBizSucc(false);
				result.setErrMsg("审核未通过,并且返还当时申请所花费的" + PAccount);
				try {
					// 公众号发送提示消息
					SysConfigDO sysConfigDO = sysConfigDAO
							.selectByKey(SysConfigCacheKeyEnum.ACCESS_TOKEN
									.toString());
					String accessToken = sysConfigDO.getSysValue();
					// String accessToken = sysConfigCacheManager
					// .getConfigValue(SysConfigCacheKeyEnum.ACCESS_TOKEN);
					String jsonTextMsg = "";
					String notice = "亲爱的用户，您所申请的开通农村经纪人被回绝,费用" + PAccount
							+ "元已经转入您的商城余额";
					notice = new String(notice.getBytes("UTF-8"), "GB18030");
					jsonTextMsg = SendCustomMessage.makeTextCustomMessage(
							userDO.getWechatId(), notice);
					SendCustomMessage.sendCustomMessage(accessToken,
							jsonTextMsg);
				} catch (Exception e) {
					e.printStackTrace();
				}
			} else {
				// 判断通过哪种途径过来申请联盟商家
				ShopCheckDO checkDO = shopCheckDAO.selectByUserIdApplyType(
						userId, null, null);
				if (null != checkDO) {
					shopCheckDAO.updateCheckStatusByUserId("2",
							checkDO.getUserId());
					// 获取用户信息
					UserDO userDO = userDao.selectUserInfoByUserId(checkDO
							.getUserId());
					result.setBizSucc(false);
					result.setErrMsg("审核未通过");
					try {
						// 公众号发送提示消息
						SysConfigDO sysConfigDO = sysConfigDAO
								.selectByKey(SysConfigCacheKeyEnum.ACCESS_TOKEN
										.toString());
						String accessToken = sysConfigDO.getSysValue();
						// String accessToken = sysConfigCacheManager
						// .getConfigValue(SysConfigCacheKeyEnum.ACCESS_TOKEN);
						String jsonTextMsg = "";
						String notice = "亲爱的用户，您所申请的开通联盟商家已被回绝";
						notice = new String(notice.getBytes("UTF-8"), "GB18030");
						jsonTextMsg = SendCustomMessage.makeTextCustomMessage(
								userDO.getWechatId(), notice);
						SendCustomMessage.sendCustomMessage(accessToken,
								jsonTextMsg);
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			}
		}
		return result;

	}
	
	/**
	 * 出局
	 * @param request
	 * @return
	 */
	@RequestMapping("/outTheFirst.do")
	@ResponseBody
	public Object outTheFirst(HttpServletRequest request) {
		JsonResult result = new JsonResult(true, "", "操作成功");
		
		Calendar calendar = Calendar.getInstance();//此时打印它获取的是系统当前时间
        calendar.add(Calendar.DATE, 0);    //得到当天     2017-5-21
        String  today= new SimpleDateFormat("yyyy-MM-dd").format(calendar.getTime());
        
        Calendar calendars = Calendar.getInstance();//此时打印它获取的是系统当前时间
        calendars.add(Calendar.DATE, -1);    //得到前一天   2017-5-20
        String  dayAgo= new SimpleDateFormat("yyyy-MM-dd").format(calendars.getTime());
		
		int outNum = Integer.valueOf(sysConfigDAO
				.selectByKey("OUT_NUM").getSysValue());//几次出局
		outNum = outNum -1;
		int numOfAll =Integer.valueOf(sysConfigDAO
				.selectByKey("NUM_OF_ALL").getSysValue());//几分之一往后排
		
		int partnerCount = partnerRankDAO.count(dayAgo,today);//当天人数
		int allCount = partnerRankDAO.count(null,today);//总人数
		
		int outCount = partnerCount / numOfAll;//当前往后排的用户数
		if(outCount < 1){
			result.setBizSucc(false);
			result.setErrMsg("当前人数不足");
		}
		
		int leftCount = allCount - outCount;//剩余人数
		//先把该查出来的 查出来  再做修改
		//先排后面的人  后leftCount人  partnerNum - outCount
		List<PartnerRankDO> leftRankList = partnerRankDAO.selectAllDesc(null,today, 0, leftCount);
		int lastPartner = 0;
		if(leftRankList.size() > 0 ){
			lastPartner = leftRankList.get(0).getPartnerNum() - outCount;
//			sysConfigDAO.updateRate(leftRankList.get(leftRankList.size()-1).getOrderId(), "OUT_RANK_NO");
		}
		//排前面  outCount 人数  
		List<PartnerRankDO> outRankList = partnerRankDAO.selectAll(null,today, 0, outCount);
		
		for (PartnerRankDO partnerRankDO : leftRankList) {
			int partnerNum = partnerRankDO.getPartnerNum();
			partnerNum = partnerNum - outCount;
			partnerRankDAO.changePartnerNum(partnerNum, partnerRankDO.getId());
		}
//		sysConfigDAO.updateRate(outRankList.get(outRankList.size()-1).getOrderId(), "OUT_RANK_NO");
		
		for (PartnerRankDO partnerRankDO : outRankList) {
			//存在第五次被排出去的
			int outNums = partnerRankDO.getOutNum();
			if(outNums == outNum){
				partnerRankDAO.delete( partnerRankDO.getId());
			}else{
				lastPartner = lastPartner + 1; 
				partnerRankDAO.changePartnerNum(lastPartner,  partnerRankDO.getId());
				partnerRankDAO.changeOutNum(outNums+1 ,  partnerRankDO.getId());
			}
		}
		return result;
	}

	// 农村经纪人的默认商品添加 方法
	public int addPartnerProduct(String myShopId, String myUserId) {
		int result = 0;
		String shopId = "3320170116000180";
		// 查询所有农村经纪人商品
		List<ProductDO> partnerProduct = productDao
				.selectPartnerProduct(shopId);

		for (ProductDO productDO : partnerProduct) {

			String oldShopId = productDO.getShopId();
			String productNo = productDO.getProductNo();

			// 查询购买的商品信息(makeploy_product表)
			ProductDO myProduct = new ProductDO();
			myProduct.setShopId(myShopId);
			myProduct.setUserId(myUserId);
			myProduct.setProductNo(productNo);
			myProduct.setProductName(productDO.getProductName());
			myProduct.setProductType(productDO.getProductType());
			myProduct.setChildren(productDO.getChildren());
			myProduct.setProductOff(productDO.getProductOff());
			myProduct.setTemplateId(productDO.getTemplateId());
			myProduct.setPrice(productDO.getPrice());
			myProduct.setLuggage(productDO.getLuggage());
			myProduct.setOldPrice(productDO.getOldPrice());
			myProduct.setSoleCount(0);
			myProduct.setCheckStatus("1");
			myProduct.setStatus("0");
			myProduct.setStock(productDO.getStock());
			myProduct.setProductUrl(productDO.getProductUrl());
			myProduct.setDelFlg("0");
			myProduct.setRecommendFlg("6");
			myProduct.setProportionReturn(productDO.getProportionReturn());
			myProduct.setProportionIntegral(productDO.getProportionIntegral());
			myProduct.setMemo(productDO.getMemo());

			result = productDao.addPartnerPro(myProduct);

			// 查询购买的商品信息(makeploy_product_parameters表)
			List<ProductParameterDO> productParameters = productParameterDAO
					.selectInfoByShopIdAndProductNo(productNo, oldShopId);
			for (ProductParameterDO productParameterDO : productParameters) {
				ProductParameterDO myproParameter = new ProductParameterDO();
				myproParameter.setShopId(myShopId);
				myproParameter.setProductNo(productNo);
				myproParameter
						.setFatherName(productParameterDO.getFatherName());
				myproParameter.setChildrenName(productParameterDO
						.getChildrenName());
				myproParameter.setStock(productParameterDO.getStock());
				myproParameter.setParflag(productParameterDO.getParflag());

				result += productParameterDAO.addPartnerPara(myproParameter);
			}

			// 查询购买的商品信息(makeploy_pro_image表)
			List<ProImageDO> selectAllProImage = proImageDAO.selectAllProImage(
					productNo, oldShopId);
			for (ProImageDO proImageDO : selectAllProImage) {
				ProImageDO myProImage = new ProImageDO();
				myProImage.setShopId(myShopId);
				myProImage.setProductNo(proImageDO.getProductNo());
				myProImage.setFlag(proImageDO.getFlag());
				myProImage.setImg(proImageDO.getImg());

				result += proImageDAO.addPartnerImage(myProImage);
			}
		}

		return result;
	}

	@RequestMapping("commerceCheckMng.html")
	public String commerceCheckMng(HttpServletRequest request, ModelMap map) {
		String userId = (String) request.getSession().getAttribute("userId");
		boolean checkRoleBoss = checkRoleBoss(userId);
		if (checkRoleBoss == false) {
			return "mng/loginMng";
		}
		// 判断是否为运营者
		int checkOperative = checkOperative(userId);
		map.put("checkOperative", checkOperative);
		Map<String, Integer> pageDate = getPageData(request);
		List<CommerceCheckDO> commerceCheckList = commerceCheckDAO
				.selectAllCommerceCheck(pageDate.get(OFFSET)*2,
						pageDate.get(PAGE_SIZE_STR)*2);
		List<CommerceResult> list = new ArrayList<CommerceResult>();
		for (CommerceCheckDO commerceCheckDO : commerceCheckList) {
			CommerceResult commerceResult = new CommerceResult();
			commerceResult.setCommerceCheckDO(commerceCheckDO);

			String commerceId = commerceCheckDO.getCommerceId();
			CommerceDO commerceDO = commerceDAO
					.selectCommerceInfoByCommerceId(commerceId);
			commerceResult.setCommerceDO(commerceDO);

			list.add(commerceResult);
		}
		int totalItems = commerceCheckDAO.countitmes();
		map.put("totalPages",
				calculatePage(totalItems, pageDate.get(PAGE_SIZE_STR)*2));
		map.put(CURRENTPAGE, pageDate.get(CURRENTPAGE));
		map.put("totalItems", totalItems);
		map.put("list", list);
		request.setAttribute("page", map);
		return "mng/commerceCheckMng";
	}

	/**
	 * 加入商会
	 * 
	 * @param userId
	 *            用户Id
	 */
	@RequestMapping("/joinCommerce.do")
	@ResponseBody
	public Object joinCommerce(final HttpServletRequest request) {
		String commerceId = request.getParameter("commerceId");
		String userId = request.getParameter("userId");

		String flg = request.getParameter("flg");

		JsonResult result = new JsonResult(false, "入会失败", "入会失败");
		// 查询该商会是否存在
		CommerceDO commerceDO = commerceDAO
				.selectCommerceInfoByCommerceId(commerceId);
		UserDO userDO = userDao.selectUserInfoByUserId(userId);
		// 通过
		if (StringUtils.equals(flg, "1")) {
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

				int k = commerceCheckDAO.updateOldCommerceCheck("1", userId,
						commerceId);

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
				
				try {
					// 公众号发送提示消息
					SysConfigDO sysConfigDO = sysConfigDAO
							.selectByKey(SysConfigCacheKeyEnum.ACCESS_TOKEN
									.toString());
					String accessToken = sysConfigDO.getSysValue();
					// String accessToken = sysConfigCacheManager
					// .getConfigValue(SysConfigCacheKeyEnum.ACCESS_TOKEN);
					String jsonTextMsg = "";
					String notice = "亲爱的用户，欢迎加入" + commerceDO.getCommerceName();
					jsonTextMsg = SendCustomMessage.makeTextCustomMessage(
							userDO.getWechatId(), notice);
					SendCustomMessage.sendCustomMessage(accessToken,
							jsonTextMsg);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
		// 不通过
		if (StringUtils.equals(flg, "2")) {
			int j = commerceCheckDAO.updateOldCommerceCheck("2", userId,
					commerceId);
			if (j >= 1) {
				result.setBizSucc(true);
				result.setErrMsg("拒绝成功");
			}
			try {
				// 公众号发送提示消息
				SysConfigDO sysConfigDO = sysConfigDAO
						.selectByKey(SysConfigCacheKeyEnum.ACCESS_TOKEN
								.toString());
				String accessToken = sysConfigDO.getSysValue();
				// String accessToken = sysConfigCacheManager
				// .getConfigValue(SysConfigCacheKeyEnum.ACCESS_TOKEN);
				String jsonTextMsg = "";
				String notice = "亲爱的用户，您加入" + commerceDO.getCommerceName()
						+ "的申请已被回绝";
				jsonTextMsg = SendCustomMessage.makeTextCustomMessage(
						userDO.getWechatId(), notice);
				SendCustomMessage.sendCustomMessage(accessToken, jsonTextMsg);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return result;
	}
}
