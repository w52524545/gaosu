package com.onway.web.controller.user;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.onway.common.lang.StringUtils;
import com.onway.makeploy.common.dal.dataobject.AccountDO;
import com.onway.makeploy.common.dal.dataobject.ProductCommentDO;
import com.onway.makeploy.common.dal.dataobject.ShopDO;
import com.onway.makeploy.common.dal.dataobject.TeamIncomeDO;
import com.onway.makeploy.common.dal.dataobject.UserDO;
import com.onway.makeploy.common.service.exception.ErrorException;
import com.onway.platform.common.service.util.AssertUtil;
import com.onway.web.controller.AbstractController;
import com.onway.web.controller.result.AccountResult;
import com.onway.web.controller.result.JsonResult;
import com.onway.web.controller.result.ProductResult;
import com.onway.web.controller.result.UserCenterResult;
import com.onway.web.controller.template.ControllerCallBack;

/**
 * ��������
 * @author Administrator
 *
 */
@Controller
public class UserCenterController extends AbstractController{


	/**
	 * ��������
	 * 
	 * @param request
	 * @param userId     �û�Id
	 * @return
	 */
    @RequestMapping("/userCenter.html")
	public Object userCenter(final HttpServletRequest request,final ModelMap modelMap)
	{
		final UserCenterResult result = new UserCenterResult(true);
		// String userId = (String) request.getSession().getAttribute("userId");
		String userId = "13252";
		if (StringUtils.isEmpty(userId)) {
			throw new ErrorException("�û�δ��¼");
		}
		UserDO userDo = userDao.selectUserInfoByUserId(userId);
		result.setNickName(userDo.getNickName());
		result.setHeadUrl(userDo.getHeadUrl());
		result.setConsumeAccout(userDo.getConsumeAccout());
		result.setDividendAccout(userDo.getDividendAccout());
		result.setWithdrawAccout(userDo.getWithdrawAccout());
		result.setBalance(userDo.getBalance());
		modelMap.put("result", result);
		return "html/personal";
	}
    
    /**
	 * �����û�Id��ѯ �˻���� �������� �ۼ�����
	 * 
	 * @param request
	 * @param userId     �û�Id
	 * @return
	 */
    @RequestMapping("/balance.html")
	public Object balance(final HttpServletRequest request,final ModelMap modelMap)
	{
    	final UserCenterResult result = new UserCenterResult(true);
		// String userId = (String) request.getSession().getAttribute("userId");
		String userId = "13252";
		if (StringUtils.isEmpty(userId)) {
			throw new ErrorException("�û�δ��¼");
		}
		UserDO userDo = userDao.selectUserInfoByUserId(userId);
		result.setBalance(userDo.getBalance());
		result.setIncomeWeek(userDo.getIncomeWeek());
		result.setTotalBalance(userDo.getTotalBalance());
		modelMap.put("result", result);
		return "html/balance";
	}
    
    
    /**
	 * �޸��û�������Ϣ
	 * @param request
	 * @param userId        �û�ID
	 * @param userName      �û���
	 * @param nickName      �ǳ�
	 * @param cell          �ֻ���           
	 * @param headUrl       ͷ��
	 * @param shopName      ���̱��⣨��������
	 * @param shopDec       ��������
	 * @return
	 */
	@RequestMapping("/updateUserInfo.do")
	@ResponseBody
	public Object updateUserInfo(HttpServletRequest request)
	{
		//final String userId=(String) request.getSession().getAttribute("userId");
		final String userId="13252";
		final String userName=request.getParameter("userName");
		final String nickName=request.getParameter("nickName");
		final String cell=request.getParameter("cell");
		final String headUrl=request.getParameter("headUrl");
		final String shopName=request.getParameter("shopName");
		final String shopDec=request.getParameter("shopDec");
		
		final JsonResult jsonResult=new JsonResult(false);
		controllerTemplate.execute(jsonResult, new ControllerCallBack() {
			
			@Override
			public void executeService() {
				ShopDO shopDo=new ShopDO();
				shopDo.setUserId(userId);
				shopDo.setShopName(shopName);
				shopDo.setShopDec(shopDec);
				int shopResult = shopDAO.updateShopInfo(shopDo);
				
				UserDO userDo=new UserDO();
				userDo.setUserId(userId);
				userDo.setUserName(userName);
				userDo.setNickName(nickName);
				userDo.setCell(cell);
				userDo.setHeadUrl(headUrl);
				int userResult = userDao.updateUserInfo(userDo);
				
				if(shopResult >= 1 && userResult >= 1)
				{
					jsonResult.setBizSucc(true);
				}	
			}
			
			@Override
			public void check() {
				AssertUtil.notBlank(userId, "����Ϊ�գ�userId");
				AssertUtil.notBlank(userName, "����Ϊ�գ�userName");
				AssertUtil.notBlank(nickName, "����Ϊ�գ�nickName");
				AssertUtil.notBlank(cell, "����Ϊ�գ�cell");
				AssertUtil.notBlank(headUrl, "����Ϊ�գ�headUrl");
				AssertUtil.notBlank(shopName, "����Ϊ�գ�shopName");
				AssertUtil.notBlank(shopDec, "����Ϊ�գ�shopDec");
			}
		});
		return jsonResult;	
	}
	
	/**
	 * ��ѯ��ϸ
	 * 
	 * @param request
	 * @param userId     �û�Id
	 * @return
	 */
    @RequestMapping("/balance1.html")
	public Object balance1(final HttpServletRequest request,final ModelMap modelMap)
	{
		// String userId = (String) request.getSession().getAttribute("userId");
		String userId = "13252";
		if (StringUtils.isEmpty(userId)) {
			throw new ErrorException("�û�δ��¼");
		}
		List<AccountDO> accountList = accountDao.selectAccountInfoByUserId(userId);
		
		List<AccountResult> list = new ArrayList<AccountResult>();
		AccountResult accountResult = null;
		for (AccountDO accountDo : accountList) {
			accountResult = new AccountResult(true, "", "");
			accountResult.setAmount(accountDo.getAmount());
			list.add(accountResult);
		}
		modelMap.put("balance", list);
		return "html/balance1";
	}
    
    /**
	 * ��ת����
	 * 
	 */
    @RequestMapping("/balance2.html")
	public Object balance2(final HttpServletRequest request,final ModelMap modelMap)
	{
    	final UserCenterResult result = new UserCenterResult(true);
		// String userId = (String) request.getSession().getAttribute("userId");
		String userId = "13252";
		if (StringUtils.isEmpty(userId)) {
			throw new ErrorException("�û�δ��¼");
		}
		UserDO userDo = userDao.selectUserInfoByUserId(userId);
		//�˻���ҳ����ʾ��
		result.setBalance(userDo.getBalance());
		modelMap.put("result", result);
		return "html/balance2";
	}
    
    /**
	 * ��ת�ҵĻ���
	 * 
	 */
    @RequestMapping("/point.html")
	public Object point(final HttpServletRequest request,final ModelMap modelMap)
	{
		// String userId = (String) request.getSession().getAttribute("userId");
		String userId = "13252";
		if (StringUtils.isEmpty(userId)) {
			throw new ErrorException("�û�δ��¼");
		}
		List<AccountDO> accountList = accountDao.selectAccountInfoByUserId(userId);
		
		List<AccountResult> list = new ArrayList<AccountResult>();
		AccountResult accountResult = null;
		for (AccountDO accountDo : accountList) {
			accountResult = new AccountResult(true, "", "");
			accountResult.setPoint(accountDo.getPoint());
			list.add(accountResult);
		}
		modelMap.put("point", list);
		return "html/point";
	}
    
    /**
	 * ��ת�Ŷ�
	 * 
	 */
    @RequestMapping("/myTeam.html")
	public Object myTeam(final HttpServletRequest request,final ModelMap modelMap)
	{
    	final UserCenterResult result = new UserCenterResult(true);
		// String userId = (String) request.getSession().getAttribute("userId");
		String userId = "13252";
		if (StringUtils.isEmpty(userId)) {
			throw new ErrorException("�û�δ��¼");
		}
		UserDO userDo = userDao.selectUserInfoByUserId(userId);
		TeamIncomeDO incomeDo = teamIncomeDAO.selectTeamIncomeByUserId(userId);
		result.setCell(userDo.getCell());
		result.setNickName(userDo.getNickName());
		result.setHeadUrl(userDo.getHeadUrl());
		result.setIncomeCount(incomeDo.getIncomeAccout());
		result.setSellCount(incomeDo.getSellAccout());
		modelMap.put("result", result);
		return "html/myTeam";
	}
    
    /**
	 * ��ת�Ŷ��б�
	 * 
	 */
    @RequestMapping("/teamList.html")
	public Object teamList(final HttpServletRequest request,final ModelMap modelMap)
	{
//    	final UserCenterResult result = new UserCenterResult(true);
//		// String userId = (String) request.getSession().getAttribute("userId");
//		String userId = "13252";
//		if (StringUtils.isEmpty(userId)) {
//			throw new ErrorException("�û�δ��¼");
//		}
//		UserDO userDo = userDao.selectUserInfoByUserId(userId);
//		TeamIncomeDO incomeDo = teamIncomeDAO.selectTeamIncomeByUserId(userId);
//		result.setCell(userDo.getCell());
//		result.setNickName(userDo.getNickName());
//		result.setHeadUrl(userDo.getHeadUrl());
//		result.setIncomeCount(incomeDo.getIncomeAccout());
//		result.setSellCount(incomeDo.getSellAccout());
//		modelMap.put("result", result);
		return "html/teamList";
	}
    
    /**
     * ��ת�޸�����ҳ��
     * @param request
     * @param modelMap
     * @return
     */
    @RequestMapping("/mystore_edit.html")
   	public Object editMystore(final HttpServletRequest request,final ModelMap modelMap)
   	{
    	final UserCenterResult result = new UserCenterResult(true);
		// String userId = (String) request.getSession().getAttribute("userId");
		String userId = "13252";
		if (StringUtils.isEmpty(userId)) {
			throw new ErrorException("�û�δ��¼");
		}
		UserDO userDo = userDao.selectUserInfoByUserId(userId);
		result.setNickName(userDo.getNickName());
		result.setHeadUrl(userDo.getHeadUrl());
		result.setUserNum(userDo.getUserNum());
		modelMap.put("result", result);
		return "html/mystore_edit";
   	}
    
    /**
	 * �޸��ҵ���Ϣ
	 * @param request
	 * @param userId        �û�ID
	 * @param addressNo     �ջ���ַ���
	 * @param flg           ��ʶ 0��Ĭ��ѡ�� 1��Ĭ�ϲ�ѡ��      
	 * @return
	 */
	@RequestMapping("/editUser.do")
	@ResponseBody
	public Object editUser(HttpServletRequest request)
	{
		// String userId = (String) request.getSession().getAttribute("userId");
		final String userId="13252";
		final String cell=request.getParameter("cell");
		final String nickName=request.getParameter("nickName");
		final String headUrl=request.getParameter("headUrl");
		final String shopName=request.getParameter("shopName");
		final String shopDec=request.getParameter("shopDec");
		
		
		final JsonResult jsonResult=new JsonResult(false);
		controllerTemplate.execute(jsonResult, new ControllerCallBack() {
			
			@Override
			public void executeService() {
				//�޸ĸ���  user��
				UserDO userDo=new UserDO();
				userDo.setUserId(userId);
				userDo.setCell(cell);
				userDo.setNickName(nickName);
				userDo.setHeadUrl(headUrl);
				
				int userResult = userDao.updateUserInfo(userDo);
				
				//�޸�������Ϣ
				ShopDO shopDo=new ShopDO();
				shopDo.setUserId(userId);
				shopDo.setShopName(shopName);
				shopDo.setShopDec(shopDec);
				
				int shopResult = shopDAO.updateShopInfo(shopDo);
				
				if(userResult >= 1 && shopResult >= 1)
				{
					jsonResult.setBizSucc(true);
				}	
			}
			
			@Override
			public void check() {
				AssertUtil.notBlank(userId, "����Ϊ�գ�userId");
				AssertUtil.notBlank(cell, "����Ϊ�գ�cell");
				AssertUtil.notBlank(nickName, "����Ϊ�գ�nickName");
				AssertUtil.notBlank(headUrl, "����Ϊ�գ�headUrl");
				AssertUtil.notBlank(shopName, "����Ϊ�գ�shopName");
				AssertUtil.notBlank(shopDec, "����Ϊ�գ�shopDec");
			}
		});
		return jsonResult;	
	}
	
	/**
	 * ��ȡ���ﳵ�б�
	 * 
	 * @param request
	 * @param userId     �û�Id
	 * @return
	 */
	@RequestMapping("/comment.html")
	public Object shopcart(final HttpServletRequest request,final ModelMap modelMap) {
		
		final String productNo=request.getParameter("productNo");
		final String shopId=request.getParameter("shopId");

		//�жϹ��ﳵ�Ƿ�����Ʒ����ת��ͬҳ��
		int totalItem = productCommentDAO.selectCommentCountByShopIdAndProductNo(productNo, shopId);
		
		final Integer pageNum = adjustPageNo(request);
		final Integer pageSize = adjustPageSize(request);		
		
		Integer startRow = (pageNum - 1) * pageSize;

		int totalPages = (totalItem - 1) / pageSize + 1;

		List<ProductCommentDO> productCommentList= productCommentDAO.selectProductCommentByShopIdAndProductNo(shopId, productNo, startRow, pageSize);
		
		List<ProductResult> list = new ArrayList<ProductResult>();
		ProductResult productResult = null;
		for (ProductCommentDO productCommentDO: productCommentList) {
			productResult = new ProductResult(true, "", "");
			//��ѯ��������Ϣ
			UserDO commentUser = userDao.selectUserInfoByUserId(productCommentDO.getUserId());
			
			productResult.setComments(productCommentDO.getComments());
			//����ʱ��
			productResult.setCommentTime(productCommentDO.getGmtCreate());
			productResult.setCommentUserNum(commentUser.getUserNum());// �����߻�Ա���
			productResult.setCommentUserUrl(commentUser.getHeadUrl());// ������ͷ��
			
			list.add(productResult);
		}
		modelMap.put("comments", list);
		
		return "html/comment";
	}
}
