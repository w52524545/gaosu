package com.onway.web.controller.mng;


import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import com.onway.makeploy.common.dal.dataobject.ShopCommentDO;
import com.onway.web.controller.AbstractController;

/**
 * @author southRain
 *
 */

@Controller
public class ShopCommentMngController extends AbstractController {

//������Ʒ��Ϣ��ѯҳ��
	@RequestMapping("/shopCommentMng.html")
	public String shopMng(HttpServletRequest request, ModelMap modelMap) {
		String shopId = request.getParameter("shopId");
		String orderId = request.getParameter("ORDER_ID");
		Map<String,Integer> pageDate=getPageData(request);
		try {
			List<ShopCommentDO> list=shopCommentDAO.selectProductInfo(shopId, orderId, pageDate.get(OFFSET), pageDate.get(PAGE_SIZE_STR));
			int count=(int) shopCommentDAO.selectProductInfoCount(shopId, orderId);
		modelMap.put("totalPages",calculatePage(count, pageDate.get(PAGE_SIZE_STR))); // ������ҳ��
		modelMap.put(CURRENTPAGE, pageDate.get(CURRENTPAGE));//��ǰҳ��
		modelMap.put("totalItems", count);// ����������
		modelMap.put("productInfoList", list);
		modelMap.put("orderId", orderId);
		modelMap.put("shopId", shopId);
		request.setAttribute("page", modelMap);
		}catch (Exception e) {
			e.printStackTrace();
		}
		return "mng/shopCommentMng";
	}
	
	
}
