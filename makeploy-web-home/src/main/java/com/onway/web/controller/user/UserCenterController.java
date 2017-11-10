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
 * 个人中心
 * @author Administrator
 *
 */
@Controller
public class UserCenterController extends AbstractController{


	/**
	 * 个人中心
	 * 
	 * @param request
	 * @param userId     用户Id
	 * @return
	 */
    @RequestMapping("/userCenter.html")
	public Object userCenter(final HttpServletRequest request,final ModelMap modelMap)
	{
		final UserCenterResult result = new UserCenterResult(true);
		// String userId = (String) request.getSession().getAttribute("userId");
		String userId = "13252";
		if (StringUtils.isEmpty(userId)) {
			throw new ErrorException("用户未登录");
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
	 * 根据用户Id查询 账户余额 近日收益 累计收入
	 * 
	 * @param request
	 * @param userId     用户Id
	 * @return
	 */
    @RequestMapping("/balance.html")
	public Object balance(final HttpServletRequest request,final ModelMap modelMap)
	{
    	final UserCenterResult result = new UserCenterResult(true);
		// String userId = (String) request.getSession().getAttribute("userId");
		String userId = "13252";
		if (StringUtils.isEmpty(userId)) {
			throw new ErrorException("用户未登录");
		}
		UserDO userDo = userDao.selectUserInfoByUserId(userId);
		result.setBalance(userDo.getBalance());
		result.setIncomeWeek(userDo.getIncomeWeek());
		result.setTotalBalance(userDo.getTotalBalance());
		modelMap.put("result", result);
		return "html/balance";
	}
    
    
    /**
	 * 修改用户个人信息
	 * @param request
	 * @param userId        用户ID
	 * @param userName      用户名
	 * @param nickName      昵称
	 * @param cell          手机号           
	 * @param headUrl       头像
	 * @param shopName      店铺标题（店铺名）
	 * @param shopDec       店铺描述
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
				AssertUtil.notBlank(userId, "参数为空：userId");
				AssertUtil.notBlank(userName, "参数为空：userName");
				AssertUtil.notBlank(nickName, "参数为空：nickName");
				AssertUtil.notBlank(cell, "参数为空：cell");
				AssertUtil.notBlank(headUrl, "参数为空：headUrl");
				AssertUtil.notBlank(shopName, "参数为空：shopName");
				AssertUtil.notBlank(shopDec, "参数为空：shopDec");
			}
		});
		return jsonResult;	
	}
	
	/**
	 * 查询明细
	 * 
	 * @param request
	 * @param userId     用户Id
	 * @return
	 */
    @RequestMapping("/balance1.html")
	public Object balance1(final HttpServletRequest request,final ModelMap modelMap)
	{
		// String userId = (String) request.getSession().getAttribute("userId");
		String userId = "13252";
		if (StringUtils.isEmpty(userId)) {
			throw new ErrorException("用户未登录");
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
	 * 跳转体现
	 * 
	 */
    @RequestMapping("/balance2.html")
	public Object balance2(final HttpServletRequest request,final ModelMap modelMap)
	{
    	final UserCenterResult result = new UserCenterResult(true);
		// String userId = (String) request.getSession().getAttribute("userId");
		String userId = "13252";
		if (StringUtils.isEmpty(userId)) {
			throw new ErrorException("用户未登录");
		}
		UserDO userDo = userDao.selectUserInfoByUserId(userId);
		//账户余额（页面显示）
		result.setBalance(userDo.getBalance());
		modelMap.put("result", result);
		return "html/balance2";
	}
    
    /**
	 * 跳转我的积分
	 * 
	 */
    @RequestMapping("/point.html")
	public Object point(final HttpServletRequest request,final ModelMap modelMap)
	{
		// String userId = (String) request.getSession().getAttribute("userId");
		String userId = "13252";
		if (StringUtils.isEmpty(userId)) {
			throw new ErrorException("用户未登录");
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
	 * 跳转团队
	 * 
	 */
    @RequestMapping("/myTeam.html")
	public Object myTeam(final HttpServletRequest request,final ModelMap modelMap)
	{
    	final UserCenterResult result = new UserCenterResult(true);
		// String userId = (String) request.getSession().getAttribute("userId");
		String userId = "13252";
		if (StringUtils.isEmpty(userId)) {
			throw new ErrorException("用户未登录");
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
	 * 跳转团队列表
	 * 
	 */
    @RequestMapping("/teamList.html")
	public Object teamList(final HttpServletRequest request,final ModelMap modelMap)
	{
//    	final UserCenterResult result = new UserCenterResult(true);
//		// String userId = (String) request.getSession().getAttribute("userId");
//		String userId = "13252";
//		if (StringUtils.isEmpty(userId)) {
//			throw new ErrorException("用户未登录");
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
     * 跳转修改资料页面
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
			throw new ErrorException("用户未登录");
		}
		UserDO userDo = userDao.selectUserInfoByUserId(userId);
		result.setNickName(userDo.getNickName());
		result.setHeadUrl(userDo.getHeadUrl());
		result.setUserNum(userDo.getUserNum());
		modelMap.put("result", result);
		return "html/mystore_edit";
   	}
    
    /**
	 * 修改我的信息
	 * @param request
	 * @param userId        用户ID
	 * @param addressNo     收货地址编号
	 * @param flg           标识 0：默认选中 1：默认不选中      
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
				//修改个人  user表
				UserDO userDo=new UserDO();
				userDo.setUserId(userId);
				userDo.setCell(cell);
				userDo.setNickName(nickName);
				userDo.setHeadUrl(headUrl);
				
				int userResult = userDao.updateUserInfo(userDo);
				
				//修改商铺信息
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
				AssertUtil.notBlank(userId, "参数为空：userId");
				AssertUtil.notBlank(cell, "参数为空：cell");
				AssertUtil.notBlank(nickName, "参数为空：nickName");
				AssertUtil.notBlank(headUrl, "参数为空：headUrl");
				AssertUtil.notBlank(shopName, "参数为空：shopName");
				AssertUtil.notBlank(shopDec, "参数为空：shopDec");
			}
		});
		return jsonResult;	
	}
	
	/**
	 * 获取购物车列表
	 * 
	 * @param request
	 * @param userId     用户Id
	 * @return
	 */
	@RequestMapping("/comment.html")
	public Object shopcart(final HttpServletRequest request,final ModelMap modelMap) {
		
		final String productNo=request.getParameter("productNo");
		final String shopId=request.getParameter("shopId");

		//判断购物车是否有商品，跳转不同页面
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
			//查询评论者信息
			UserDO commentUser = userDao.selectUserInfoByUserId(productCommentDO.getUserId());
			
			productResult.setComments(productCommentDO.getComments());
			//评论时间
			productResult.setCommentTime(productCommentDO.getGmtCreate());
			productResult.setCommentUserNum(commentUser.getUserNum());// 评价者会员编号
			productResult.setCommentUserUrl(commentUser.getHeadUrl());// 评论者头像
			
			list.add(productResult);
		}
		modelMap.put("comments", list);
		
		return "html/comment";
	}
}
