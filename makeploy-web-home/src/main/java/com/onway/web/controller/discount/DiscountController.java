package com.onway.web.controller.discount;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.onway.common.lang.StringUtils;
import com.onway.makeploy.common.dal.dataobject.DiscountDO;
import com.onway.makeploy.common.dal.dataobject.ProductDO;
import com.onway.makeploy.common.service.exception.ErrorException;
import com.onway.platform.common.service.util.AssertUtil;
import com.onway.web.controller.AbstractController;
import com.onway.web.controller.result.DiscountResult;
import com.onway.web.controller.result.JsonPageResult;
import com.onway.web.controller.result.ProductResult;
import com.onway.web.controller.template.ControllerCallBack;

/**
 * 我的优惠券
 * 
 * @author Administrator
 *
 */
@Controller
public class DiscountController extends AbstractController {

	/**
	 * 获取优惠券列表
	 * 
	 * @param request
	 * @param userId
	 *            用户Id
	 * @param status
	 *            优惠券状态（使用，未使用）
	 * @return
	 */
	@RequestMapping("/discountList.do")
	@ResponseBody
	public Object discountList(final HttpServletRequest request) {
		// String userId = (String) request.getSession().getAttribute("userId");
		final String userId = "13252";
		final String status = "";// request.getParameter("status");
		final Integer pageNum = adjustPageNo(request);
		final Integer pageSize = adjustPageSize(request);

		final JsonPageResult<DiscountDO> result = new JsonPageResult<DiscountDO>(
				true);
		controllerTemplate.execute(result, new ControllerCallBack() {

			@Override
			public void executeService() {
				// 查询记录数,总页数
				Integer startRow = (pageNum - 1) * pageSize;

				int totalItem = discountDao.selectDiscountCountByStatusAndUserId(userId, status);
				int totalPages = (totalItem - 1) / pageSize + 1;

				List<DiscountDO> discountDoList = discountDao.selectDiscountInfoByUserIdAndStatus(userId, status,startRow, pageSize);
				result.setListObject(discountDoList);
				result.setTotalPages(totalPages);
				result.setNext(totalPages > pageNum ? true : false);
			}

			@Override
			public void check() {

				AssertUtil.notBlank(userId, "userId为空");
				// AssertUtil.notBlank(status, "status为空");
			}
		});
		return result;
	}

	/**
	 * 我的优惠券
	 * @param request
	 * @param modelMap
	 * @return
	 */
	@RequestMapping("/coupon.html")
	public Object productList(final HttpServletRequest request,
			final ModelMap modelMap) {
		// String userId = (String) request.getSession().getAttribute("userId");
		String userId = "13252";
		if (StringUtils.isEmpty(userId)) {
			throw new ErrorException("用户未登录");
		}

		//分页未考虑
		List<DiscountDO> discountList = discountDao.selectAllDiscountByUserId(userId, null, null);

		List<DiscountResult> list = new ArrayList<DiscountResult>();
		DiscountResult discountResult = null;
		for (DiscountDO discount : discountList) {
			    discountResult = new DiscountResult(true, "", "");
			    discountResult.setDiscountNo(discount.getDiscountNo());
			    discountResult.setStatus(discount.getStatus());

			    //判断日期
			    int i = compareDate(discount.getEndDate(), new Date());
			    discountResult.setTimeStatus(i);
			    
			    discountResult.setAccout(discount.getAccout());
			    discountResult.setLimitAccout(discount.getLimitAccout());
			    
				list.add(discountResult);
				
		}
		modelMap.put("discount", list);
		return "html/coupon";
	}
	
	
	/**
	 * 判断日期
	 * @param d1
	 * @param d2
	 * @return
	 */
	public int compareDate(Date d1,Date d2){
        if (d1.getTime() > d2.getTime()) {
            System.out.println("未过期");
            return 1;
        } else {
            System.out.println("已过期");
            return 0;
        } 
}
}
