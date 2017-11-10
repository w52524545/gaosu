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
		// �ж��Ƿ�Ϊ��Ӫ��
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
		// �ж��Ƿ�Ϊ��Ӫ��
		int checkOperative = checkOperative(userId);
		map.put("checkOperative", checkOperative);

		String shopName = request.getParameter("shopName").replace("+", "");
		String shopCell = request.getParameter("shopCell").replace("+", "");
		// String cell=request.getParameter("cell").replace("+", "");
		String flag = request.getParameter("selectedNew");
		// ũ�徭��������
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
		JsonResult result = new JsonResult(false, "", "���ʧ��!");
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
//				int partnerNum= Integer.valueOf(shopCheckDO.getMemo());//��ʵ����������˳��
				int a = shopCheckDAO.updateCheckStatusByshopId("1", shopId);// �޸�check������״̬
				// ��partner���������
				List<OrderDO> orderDO = orderDao.partnerOrderByUserId(userId);
				Money PAccount = orderDO.get(orderDO.size() - 1)
						.getOrderPrice();
				PartnerDO partnerDO = new PartnerDO();
				partnerDO.setPartnerAccout(PAccount);
				partnerDO.setPartnerUserId(userId);
				partnerDAO.joinPartner(partnerDO);

				// ��shop������µĵ�������
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
				// ���ũ�徭��������Ĭ���е���Ʒ
				int x = addPartnerProduct(shopId, userId);
				// ��user������µ�ǰuserId��shopId
				int c = userDao.addShopByUserId(shopId, userId);
				// roleȨ�ޱ�����Ȩ��
				RoleDO role = new RoleDO();
				role.setUserId(userId);
				role.setRole("0");
				role.setCreater("�̻�");
				int d = roleDao.addRole(role);
				if (a > 0 && b > 0 && c > 0 && d > 0 && x > 0) {
					result.setBizSucc(true);
					result.setErrMsg("���ͨ��");
				} else {
					return result;
				}

//				Date now = new Date();
//				SimpleDateFormat dateFormat = new SimpleDateFormat(
//						"yyyy-MM-dd HH:mm");// ���Է�����޸����ڸ�ʽ
//
//				String checktime = dateFormat.format(now);
				ActivityDO shopCheckNotice = new ActivityDO();
				String userName = userDao.selectUserInfoByUserId(
						shopCheckDAO.selectByShopId(shopId).getUserId())
						.getNickName();
				// ��ҳ��Ϣ
				shopCheckNotice.setTitle("�ͻ�ũ�徭��������");
				// shopCheckNotice.setInfo(userName+"��"+checktime+"�����������Լ��ĵ��̣�");
				// shopCheckNotice.setInfo("��"+shopDAO.selectShopByShopId(shopId).getShopName()+"��"+"��ո�ͨ�������פƽ̨��");
				shopCheckNotice.setInfo(userName + "�ո�ͨ����˳�Ϊũ�徭���ˣ�");
				shopCheckNotice.setType("1");
				activityDao.resetStateByType("1");
				shopCheckNotice.setState("1");
				activityDao.addInfo(shopCheckNotice);
				try {
					// ���ںŷ�����ʾ��Ϣ
					SysConfigDO sysConfigDO = sysConfigDAO
							.selectByKey(SysConfigCacheKeyEnum.ACCESS_TOKEN
									.toString());
					String accessToken = sysConfigDO.getSysValue();
					// String accessToken = sysConfigCacheManager
					// .getConfigValue(SysConfigCacheKeyEnum.ACCESS_TOKEN);
					UserDO userdo = userDao.selectUserInfoByUserId(userId);
					String jsonTextMsg = "";
					String notice = "�װ����û�������ũ�徭��������Ѿ�ͨ��";
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
				// �ж�ͨ������;���������������̼�
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
					result.setErrMsg("���ͨ��");
				} else {
					// shopcheck����shopId
					shopCheckDAO.updateShopId(shopId, userId);
					shopCheckDAO.updateCheckStatusByshopId("1",shopId);
					// shop���� user����
					// ��shop������µĵ�������
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
						result.setErrMsg("���ͨ��");
					} else {
						return result;
					}
				}
				RoleDO roleDO = roleDao.selectShopRoleByUserId(userId);
				if (null == roleDO) {
					// roleȨ�ޱ�����Ȩ��
					RoleDO role = new RoleDO();
					role.setUserId(userId);
					role.setRole("0");
					role.setCreater("�̻�");
					roleDao.addRole(role);
				}

				// ��ҳ������Ϣ
				shopCheckNotice.setTitle("�ͻ������̼�����");
				// shopCheckNotice.setInfo(userName+"��"+checktime+"�����������Լ��ĵ��̣�");
				// shopCheckNotice.setInfo("��"+shopDAO.selectShopByShopId(shopId).getShopName()+"��"+"��ո�ͨ�������פƽ̨��");
				shopCheckNotice.setInfo(userName + "�ո�ͨ����˳�Ϊ�����̼ң�");
				shopCheckNotice.setType("1");
				activityDao.resetStateByType("1");
				shopCheckNotice.setState("1");
				activityDao.addInfo(shopCheckNotice);
				try {
					// ���ںŷ�����ʾ��Ϣ
					SysConfigDO sysConfigDO = sysConfigDAO
							.selectByKey(SysConfigCacheKeyEnum.ACCESS_TOKEN
									.toString());
					String accessToken = sysConfigDO.getSysValue();
					// String accessToken = sysConfigCacheManager
					// .getConfigValue(SysConfigCacheKeyEnum.ACCESS_TOKEN);
					UserDO userdo = userDao.selectUserInfoByUserId(userId);
					String jsonTextMsg = "";
					String notice = "�װ����û�������������������Ѿ�ͨ��";
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
				// ��ȡ�û���Ϣ
				UserDO userDO = userDao.selectUserInfoByUserId(userId);
				// ��ȡ�û���ʱ����ʱ�����ѵ�Ǯ
				List<OrderDO> orderDO = orderDao.partnerOrderByUserId(userId);
				Money PAccount = orderDO.get(orderDO.size() - 1)
						.getOrderPrice();
				Money balance = userDO.getBalance();
				// �����û�������,��ũ�徭���˷��÷��ظ����
				userDao.updateUserReturn(userDO.getPointAccout(),
						balance.add(PAccount), userDO.getWithdrawAccout(),
						userId);
				result.setBizSucc(false);
				result.setErrMsg("���δͨ��,���ҷ�����ʱ���������ѵ�" + PAccount);
				try {
					// ���ںŷ�����ʾ��Ϣ
					SysConfigDO sysConfigDO = sysConfigDAO
							.selectByKey(SysConfigCacheKeyEnum.ACCESS_TOKEN
									.toString());
					String accessToken = sysConfigDO.getSysValue();
					// String accessToken = sysConfigCacheManager
					// .getConfigValue(SysConfigCacheKeyEnum.ACCESS_TOKEN);
					String jsonTextMsg = "";
					String notice = "�װ����û�����������Ŀ�ͨũ�徭���˱��ؾ�,����" + PAccount
							+ "Ԫ�Ѿ�ת�������̳����";
					notice = new String(notice.getBytes("UTF-8"), "GB18030");
					jsonTextMsg = SendCustomMessage.makeTextCustomMessage(
							userDO.getWechatId(), notice);
					SendCustomMessage.sendCustomMessage(accessToken,
							jsonTextMsg);
				} catch (Exception e) {
					e.printStackTrace();
				}
			} else {
				// �ж�ͨ������;���������������̼�
				ShopCheckDO checkDO = shopCheckDAO.selectByUserIdApplyType(
						userId, null, null);
				if (null != checkDO) {
					shopCheckDAO.updateCheckStatusByUserId("2",
							checkDO.getUserId());
					// ��ȡ�û���Ϣ
					UserDO userDO = userDao.selectUserInfoByUserId(checkDO
							.getUserId());
					result.setBizSucc(false);
					result.setErrMsg("���δͨ��");
					try {
						// ���ںŷ�����ʾ��Ϣ
						SysConfigDO sysConfigDO = sysConfigDAO
								.selectByKey(SysConfigCacheKeyEnum.ACCESS_TOKEN
										.toString());
						String accessToken = sysConfigDO.getSysValue();
						// String accessToken = sysConfigCacheManager
						// .getConfigValue(SysConfigCacheKeyEnum.ACCESS_TOKEN);
						String jsonTextMsg = "";
						String notice = "�װ����û�����������Ŀ�ͨ�����̼��ѱ��ؾ�";
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
	 * ����
	 * @param request
	 * @return
	 */
	@RequestMapping("/outTheFirst.do")
	@ResponseBody
	public Object outTheFirst(HttpServletRequest request) {
		JsonResult result = new JsonResult(true, "", "�����ɹ�");
		
		Calendar calendar = Calendar.getInstance();//��ʱ��ӡ����ȡ����ϵͳ��ǰʱ��
        calendar.add(Calendar.DATE, 0);    //�õ�����     2017-5-21
        String  today= new SimpleDateFormat("yyyy-MM-dd").format(calendar.getTime());
        
        Calendar calendars = Calendar.getInstance();//��ʱ��ӡ����ȡ����ϵͳ��ǰʱ��
        calendars.add(Calendar.DATE, -1);    //�õ�ǰһ��   2017-5-20
        String  dayAgo= new SimpleDateFormat("yyyy-MM-dd").format(calendars.getTime());
		
		int outNum = Integer.valueOf(sysConfigDAO
				.selectByKey("OUT_NUM").getSysValue());//���γ���
		outNum = outNum -1;
		int numOfAll =Integer.valueOf(sysConfigDAO
				.selectByKey("NUM_OF_ALL").getSysValue());//����֮һ������
		
		int partnerCount = partnerRankDAO.count(dayAgo,today);//��������
		int allCount = partnerRankDAO.count(null,today);//������
		
		int outCount = partnerCount / numOfAll;//��ǰ�����ŵ��û���
		if(outCount < 1){
			result.setBizSucc(false);
			result.setErrMsg("��ǰ��������");
		}
		
		int leftCount = allCount - outCount;//ʣ������
		//�ȰѸò������ �����  �����޸�
		//���ź������  ��leftCount��  partnerNum - outCount
		List<PartnerRankDO> leftRankList = partnerRankDAO.selectAllDesc(null,today, 0, leftCount);
		int lastPartner = 0;
		if(leftRankList.size() > 0 ){
			lastPartner = leftRankList.get(0).getPartnerNum() - outCount;
//			sysConfigDAO.updateRate(leftRankList.get(leftRankList.size()-1).getOrderId(), "OUT_RANK_NO");
		}
		//��ǰ��  outCount ����  
		List<PartnerRankDO> outRankList = partnerRankDAO.selectAll(null,today, 0, outCount);
		
		for (PartnerRankDO partnerRankDO : leftRankList) {
			int partnerNum = partnerRankDO.getPartnerNum();
			partnerNum = partnerNum - outCount;
			partnerRankDAO.changePartnerNum(partnerNum, partnerRankDO.getId());
		}
//		sysConfigDAO.updateRate(outRankList.get(outRankList.size()-1).getOrderId(), "OUT_RANK_NO");
		
		for (PartnerRankDO partnerRankDO : outRankList) {
			//���ڵ���α��ų�ȥ��
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

	// ũ�徭���˵�Ĭ����Ʒ��� ����
	public int addPartnerProduct(String myShopId, String myUserId) {
		int result = 0;
		String shopId = "3320170116000180";
		// ��ѯ����ũ�徭������Ʒ
		List<ProductDO> partnerProduct = productDao
				.selectPartnerProduct(shopId);

		for (ProductDO productDO : partnerProduct) {

			String oldShopId = productDO.getShopId();
			String productNo = productDO.getProductNo();

			// ��ѯ�������Ʒ��Ϣ(makeploy_product��)
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

			// ��ѯ�������Ʒ��Ϣ(makeploy_product_parameters��)
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

			// ��ѯ�������Ʒ��Ϣ(makeploy_pro_image��)
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
		// �ж��Ƿ�Ϊ��Ӫ��
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
	 * �����̻�
	 * 
	 * @param userId
	 *            �û�Id
	 */
	@RequestMapping("/joinCommerce.do")
	@ResponseBody
	public Object joinCommerce(final HttpServletRequest request) {
		String commerceId = request.getParameter("commerceId");
		String userId = request.getParameter("userId");

		String flg = request.getParameter("flg");

		JsonResult result = new JsonResult(false, "���ʧ��", "���ʧ��");
		// ��ѯ���̻��Ƿ����
		CommerceDO commerceDO = commerceDAO
				.selectCommerceInfoByCommerceId(commerceId);
		UserDO userDO = userDao.selectUserInfoByUserId(userId);
		// ͨ��
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
					result.setErrMsg("ͨ���ɹ�");
				}
				
				StringBuffer info = new StringBuffer().append(
						userDO.getNickName()).append("�ոռ���").append(commerceDO.getCommerceName());
				String title = "�����";
				ActivityDO noticeInfo = new ActivityDO();
				noticeInfo.setTitle(title);
				noticeInfo.setInfo(info.toString());
				noticeInfo.setType("10");
				noticeInfo.setState("0");// �·����Ĺ���Ĭ���ö�
				activityDao.addInfo(noticeInfo);
				
				try {
					// ���ںŷ�����ʾ��Ϣ
					SysConfigDO sysConfigDO = sysConfigDAO
							.selectByKey(SysConfigCacheKeyEnum.ACCESS_TOKEN
									.toString());
					String accessToken = sysConfigDO.getSysValue();
					// String accessToken = sysConfigCacheManager
					// .getConfigValue(SysConfigCacheKeyEnum.ACCESS_TOKEN);
					String jsonTextMsg = "";
					String notice = "�װ����û�����ӭ����" + commerceDO.getCommerceName();
					jsonTextMsg = SendCustomMessage.makeTextCustomMessage(
							userDO.getWechatId(), notice);
					SendCustomMessage.sendCustomMessage(accessToken,
							jsonTextMsg);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
		// ��ͨ��
		if (StringUtils.equals(flg, "2")) {
			int j = commerceCheckDAO.updateOldCommerceCheck("2", userId,
					commerceId);
			if (j >= 1) {
				result.setBizSucc(true);
				result.setErrMsg("�ܾ��ɹ�");
			}
			try {
				// ���ںŷ�����ʾ��Ϣ
				SysConfigDO sysConfigDO = sysConfigDAO
						.selectByKey(SysConfigCacheKeyEnum.ACCESS_TOKEN
								.toString());
				String accessToken = sysConfigDO.getSysValue();
				// String accessToken = sysConfigCacheManager
				// .getConfigValue(SysConfigCacheKeyEnum.ACCESS_TOKEN);
				String jsonTextMsg = "";
				String notice = "�װ����û���������" + commerceDO.getCommerceName()
						+ "�������ѱ��ؾ�";
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
