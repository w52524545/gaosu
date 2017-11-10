package com.onway.web.controller.mng;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.onway.makeploy.common.dal.dataobject.CommerceDO;
import com.onway.makeploy.common.dal.dataobject.SysConfigDO;
import com.onway.platform.common.configration.ConfigrationFactory;
import com.onway.web.controller.AbstractController;
import com.onway.web.controller.ImageZipUtil;
import com.onway.web.controller.result.JsonResult;

@Controller
public class CounterFeeController extends AbstractController {

	private static final String IMAGE_FILE = ConfigrationFactory
			.getConfigration().getPropertyValue("user_img_upload_realpath");

	private static final String IMAGE_PATH = ConfigrationFactory
			.getConfigration().getPropertyValue("user_img_path");

	@RequestMapping("/counterFee.html")
	public String counterFee(ModelMap map, HttpServletRequest request) {

		String userId = (String) request.getSession().getAttribute("userId");
		boolean checkRoleBoss = checkRoleBoss(userId);
		if (checkRoleBoss == false) {
			return "mng/loginMng";
		}

		// 判断是否为运营者
		int checkOperative = checkOperative(userId);
		map.put("checkOperative", checkOperative);

		SysConfigDO withDrawRate = sysConfigDAO.selectByKey("WITHDRAW_RATE");
		SysConfigDO firstBuyReurnRate = sysConfigDAO
				.selectByKey("BUY_RETURN_FIRSTLEVEKL_RATE");// 一级消费反利率
		SysConfigDO secondBuyReurnRate = sysConfigDAO
				.selectByKey("BUY_RETURN_SECONDLEVEKL_RATE");// 二级消费反利率
		SysConfigDO partnerReturnRate = sysConfigDAO
				.selectByKey("PARTNER_RETURN_RATE");
		SysConfigDO partnerOffRate = sysConfigDAO
				.selectByKey("PARTNER_OFF_RATE");
		SysConfigDO agentIncomeRate = sysConfigDAO
				.selectByKey("AGENT_INCOME_RATE");
		SysConfigDO unionReturnPartner = sysConfigDAO
				.selectByKey("UNION_RETURN_PARTNER");
		SysConfigDO indexTitleName = sysConfigDAO
				.selectByKey("INDEX_TITLE_NAME");
		SysConfigDO outNum = sysConfigDAO
				.selectByKey("OUT_NUM");
		SysConfigDO numOfAll = sysConfigDAO
				.selectByKey("NUM_OF_ALL");
		SysConfigDO limitRank = sysConfigDAO
				.selectByKey("LIMIT_RANK");
		SysConfigDO returnTimes = sysConfigDAO
				.selectByKey("RETURN_TIMES");
		SysConfigDO accountReturnMenu = sysConfigDAO.selectByKey("ACCOUNT_RETURN_MENU");
		map.addAttribute("withDrawRate", withDrawRate.getSysValue());// 提现费率
		map.addAttribute("firstBuyReurnRate", firstBuyReurnRate.getSysValue());// 一级消费返利利率
		map.addAttribute("secondBuyReurnRate", secondBuyReurnRate.getSysValue());// 二级消费返利利率
		map.addAttribute("partnerReturnRate", partnerReturnRate.getSysValue());// 下线开通农村经纪人反利率
		map.addAttribute("partnerOffRate", partnerOffRate.getSysValue());// 农村经纪人折扣率
		map.addAttribute("agentIncomeRate", agentIncomeRate.getSysValue());// 区域代理手续费
		map.addAttribute("unionReturnPartner", unionReturnPartner.getSysValue());// 农村经纪人下级联盟商家返利比率
		map.addAttribute("indexTitleName", indexTitleName.getSysValue());// 手机端首页title展示
		map.addAttribute("outNum", outNum.getSysValue());
		map.addAttribute("numOfAll", numOfAll.getSysValue());
		map.addAttribute("limitRank", limitRank.getSysValue());
		map.addAttribute("returnTimes", returnTimes.getSysValue());
		map.addAttribute("accountReturnMenu", accountReturnMenu.getSysValue());
		return "mng/counterFee";
	}

	@RequestMapping("/editRate.do")
	@ResponseBody
	public Object edit(HttpServletRequest request, ModelMap map) {
		JsonResult result = new JsonResult(false);
		String flg = request.getParameter("flg");
		String newRate = request.getParameter("newRate");
		if ("1".equals(flg)) {
			sysConfigDAO.updateRate(newRate, "WITHDRAW_RATE");
			result.setBizSucc(true);
			return result;
		} else if ("2".equals(flg)) {
			sysConfigDAO.updateRate(newRate, "BUY_RETURN_FIRSTLEVEKL_RATE");
			result.setBizSucc(true);
			return result;
		} else if ("3".equals(flg)) {
			sysConfigDAO.updateRate(newRate, "PARTNER_RETURN_RATE");
			result.setBizSucc(true);
			return result;
		} else if ("4".equals(flg)) {
			sysConfigDAO.updateRate(newRate, "PARTNER_OFF_RATE");
			result.setBizSucc(true);
			return result;
		} else if ("5".equals(flg)) {
			sysConfigDAO.updateRate(newRate, "AGENT_INCOME_RATE");
			result.setBizSucc(true);
			return result;
		} else if ("6".equals(flg)) {
			sysConfigDAO.updateRate(newRate, "BUY_RETURN_SECONDLEVEKL_RATE");
			result.setBizSucc(true);
			return result;
		} else if ("7".equals(flg)) {
			sysConfigDAO.updateRate(newRate, "UNION_RETURN_PARTNER");
			result.setBizSucc(true);
			return result;
		}else if ("8".equals(flg)) {
			sysConfigDAO.updateRate(newRate, "INDEX_TITLE_NAME");
			result.setBizSucc(true);
			return result;
		}else if ("9".equals(flg)) {
			sysConfigDAO.updateRate(newRate, "OUT_NUM");
			result.setBizSucc(true);
			return result;
		}else if ("10".equals(flg)) {
			sysConfigDAO.updateRate(newRate, "NUM_OF_ALL");
			result.setBizSucc(true);
			return result;
		}else if ("11".equals(flg)) {
			sysConfigDAO.updateRate(newRate, "LIMIT_RANK");
			result.setBizSucc(true);
			return result;
		}else if ("12".equals(flg)) {
			sysConfigDAO.updateRate(newRate, "RETURN_TIMES");
			result.setBizSucc(true);
			return result;
		}else if ("13".equals(flg)) {
			if(StringUtils.equals(newRate, "OPEN")){
				newRate = "CLOSE";
			}else if(StringUtils.equals(newRate, "CLOSE")){
				newRate = "OPEN";
			}
			sysConfigDAO.updateRate(newRate, "ACCOUNT_RETURN_MENU");
			result.setBizSucc(true);
			return result;
		}
		return result;
	}

	/**
	 * 商会客服二维码
	 * 
	 * @param request
	 * @param modelMap
	 * @return
	 */
	@RequestMapping("changeQr.html")
	public String changeQr(HttpServletRequest request, ModelMap modelMap) {
//		String userIds = (String) request.getSession().getAttribute("userId");
//		boolean checkRoleBoss = checkRoleBoss(userIds);
//		if (checkRoleBoss == false) {
//			return "mng/loginMng";
//		}
//		// 判断是否为运营者
//		int checkOperative = checkOperative(userIds);
//		modelMap.put("checkOperative", checkOperative);
		
		String commerceId = request.getParameter("commerceId");
		CommerceDO commerceDO = commerceDAO.selectCommerceInfoByCommerceId(commerceId);
		modelMap.put("commerceDO", commerceDO);
		
//		SysConfigDO sysConfigDO = sysConfigDAO.selectByKey("COMMERCE_QR_URL");
//		String commerceQrUrl = sysConfigDO.getSysValue();
//
//		modelMap.put("commerceQrUrl", commerceQrUrl);
		return "mng/changeQr";
	}

	/**
	 * 商会客服二维码
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping("changeQr.do")
	public ModelAndView changeQrDo(HttpServletRequest request,ModelMap modelMap, @RequestParam MultipartFile image1)
			throws Exception {
		
		String commerceId = request.getParameter("commerceId");
		String imagePath;
		String imageRelativePath1 = new String();
		if (image1.getSize() > 0) {
			if (null == image1.getContentType()
					|| image1.getContentType().split("/") == null) {
				imageRelativePath1 = String.valueOf(System.currentTimeMillis())
						+ ".jpg";//
			} else {
				if (image1.getContentType().split("/").length > 1) {
					imageRelativePath1 = String.valueOf(System
							.currentTimeMillis())
							+ "."
							+ image1.getContentType().split("/")[1];//
				} else {
					imageRelativePath1 = String.valueOf(System
							.currentTimeMillis()) + ".jpg";//
				}
			}
			imagePath = IMAGE_FILE + imageRelativePath1;
			ImageZipUtil.compressPic(image1, imagePath);
			imageRelativePath1 = IMAGE_PATH + imageRelativePath1;
		}else{
//			SysConfigDO sysConfigDO = sysConfigDAO.selectByKey("COMMERCE_QR_URL");
			CommerceDO commerceDO = commerceDAO.selectCommerceInfoByCommerceId(commerceId);
			imageRelativePath1 = commerceDO.getCommerceQrurl();
		}

		int flag1 = commerceDAO.updateCommerceQR(imageRelativePath1, commerceId);
//		int flag1 = sysConfigDAO.updateRate(imageRelativePath1, "COMMERCE_QR_URL");
		modelMap.put("flag", flag1);

		return new ModelAndView("mng/commerceQrChangesuccess");
	}
}
