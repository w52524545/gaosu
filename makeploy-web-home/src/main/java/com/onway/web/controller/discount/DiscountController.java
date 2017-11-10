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
 * �ҵ��Ż�ȯ
 * 
 * @author Administrator
 *
 */
@Controller
public class DiscountController extends AbstractController {

	/**
	 * ��ȡ�Ż�ȯ�б�
	 * 
	 * @param request
	 * @param userId
	 *            �û�Id
	 * @param status
	 *            �Ż�ȯ״̬��ʹ�ã�δʹ�ã�
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
				// ��ѯ��¼��,��ҳ��
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

				AssertUtil.notBlank(userId, "userIdΪ��");
				// AssertUtil.notBlank(status, "statusΪ��");
			}
		});
		return result;
	}

	/**
	 * �ҵ��Ż�ȯ
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
			throw new ErrorException("�û�δ��¼");
		}

		//��ҳδ����
		List<DiscountDO> discountList = discountDao.selectAllDiscountByUserId(userId, null, null);

		List<DiscountResult> list = new ArrayList<DiscountResult>();
		DiscountResult discountResult = null;
		for (DiscountDO discount : discountList) {
			    discountResult = new DiscountResult(true, "", "");
			    discountResult.setDiscountNo(discount.getDiscountNo());
			    discountResult.setStatus(discount.getStatus());

			    //�ж�����
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
	 * �ж�����
	 * @param d1
	 * @param d2
	 * @return
	 */
	public int compareDate(Date d1,Date d2){
        if (d1.getTime() > d2.getTime()) {
            System.out.println("δ����");
            return 1;
        } else {
            System.out.println("�ѹ���");
            return 0;
        } 
}
}
